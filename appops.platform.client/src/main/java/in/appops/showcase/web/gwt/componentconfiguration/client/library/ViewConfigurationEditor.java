/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;


import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.client.EntityContext;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.configuration.constant.ConfigTypeConstant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com
 */
public class ViewConfigurationEditor extends Composite implements FieldEventHandler {

	private VerticalPanel basePanel;
	private VerticalPanel compConatinerHolder;
	private final String REGULARLBL_CSS = "regularLabel";
	private Logger logger = Logger.getLogger("ViewConfigurationEditor");
	private ListBoxField spanListBox;
	private Entity viewConfigTypeEntity;
	//private HashMap<Entity, ArrayList<Entity>> configTypeEntityMap;
	private HashMap<String, Entity> configTypeEntityMap;
	
	private LabelField subHeaderLbl ;
	
	private HandlerRegistration fieldEventHandler = null;
	
	public ViewConfigurationEditor(){
		initialize();
	}

	private void initialize() {
		try{
			basePanel = new VerticalPanel();
			compConatinerHolder = new VerticalPanel();
			spanListBox = new ListBoxField();
			//configTypeEntityMap = new HashMap<Entity, ArrayList<Entity>>();
			configTypeEntityMap = new HashMap<String, Entity>();
			subHeaderLbl = new LabelField();
			initWidget(basePanel);
		
			if(fieldEventHandler == null)
				fieldEventHandler = AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deregisterHandler(){
		fieldEventHandler.removeHandler();
	}
	
	/**
	 * creates the UI for view configuration editing
	 */
	public void createUI(){
		compConatinerHolder.clear();
		HorizontalPanel hpSpanSelection = new HorizontalPanel();
		
		Configuration subHeaderLblConfig = getLblConfig(" Select a span element ");
		subHeaderLbl.setConfiguration(subHeaderLblConfig);
		subHeaderLbl.configure();
		subHeaderLbl.create();
		
		hpSpanSelection.add(subHeaderLbl);
		hpSpanSelection.add(spanListBox);
		
		basePanel.add(hpSpanSelection);
		basePanel.add(compConatinerHolder);
	}
	
	public void populateSpansListBox(ArrayList<Element> spansList){
		ArrayList<String> widgetList = new ArrayList<String>();
		for(Element ele : spansList){
			String widgetType = ele.getAttribute("widgetType");
			String configName = ele.getAttribute("data-config");
			widgetList.add(widgetType + ":" + configName);
		}

		Configuration conf = getSpansListBoxConfiguration();
		conf.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS, widgetList);
			
		spanListBox.setConfiguration(conf);
		spanListBox.configure();
		spanListBox.create();
	}
	
	/**
	 * settings for configuration of label fields used to display normal text
	 * @param labelText
	 * @return
	 */
	private Configuration getLblConfig(String labelText) {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, labelText);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, REGULARLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
			logger.log(Level.SEVERE, "ViewConfigurationEditor :: getLblConfig :: Exception", e);
		}
		return configuration;
	}
	

	/**
	 * method for configuration settings of the list box displaying the span elements fetched after parsing the snippet html
	 * @return
	 */
	private Configuration getSpansListBoxConfiguration() {
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,"spanListBoxField");
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"Select a span element");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ViewConfigurationEditor :: getSpansListBoxConfiguration :: Exception", e);
		}
		return configuration;
	}

	private void showConfigurator(Entity configEntity, ArrayList<Entity> configList){
		compConatinerHolder.clear();
		ConfigurationEditor configEditor = new ConfigurationEditor(this);
		configEditor.createUi(configEntity, configList);
		compConatinerHolder.add(configEditor);
		
	}

	private Entity getConfigTypeEntity(String configName){
		try{
			Entity compEntity = new Entity();
			compEntity.setType(new MetaType("Configtype"));
			compEntity.setPropertyByName(ConfigTypeConstant.KEYNAME, configName);
			compEntity.setProperty(ConfigTypeConstant.PARENTCONFIGTYPE, getViewConfigTypeEntity());
			return compEntity;
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "ViewConfigurationEditor :: getConfigTypeEntity :: Exception", e);
		}
		return null;
	}
	
	private boolean fetchSnippetComponentConfigType(String configTypeKeyName){
		
		if(configTypeEntityMap.containsKey(configTypeKeyName)){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private void saveSnippetComponentConfigType(Entity configTypeEntity, String typeName){
		try{
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			EntityContext entityContext = new EntityContext();
			
			Map parameterMap = new HashMap<String, String>();
			parameterMap.put("confTypeEnt", configTypeEntity);
			parameterMap.put("typeName", typeName);
			parameterMap.put("entityContext", entityContext);
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveSnippetComponentConfigTypes", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<HashMap<String, Object>>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					
				}

				@Override
				public void onSuccess(Result<HashMap<String, Object>> result) {
					if(result!=null){
						HashMap<String, Object> map = result.getOperationResult();
						Entity propertyConfig   = (Entity)map.get("configType");
						ArrayList<Entity> configList =  (ArrayList<Entity>)map.get("childConfigTypeList");
						if(propertyConfig!=null){
							//configTypeEntityMap.put(propertyConfig, configList);
							String val = propertyConfig.getPropertyByName(ConfigTypeConstant.KEYNAME).toString();
							configTypeEntityMap.put(val , propertyConfig);
							showConfigurator(propertyConfig, configList);
						}
					}
					
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ViewConfigurationEditor :: fetchComponentConfig :: Exception", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public EntityList getConfigTypeList(Entity entity){
		
		try {
			Property<Serializable> configTypeProperty = (Property<Serializable>) entity.getProperty("id");
			Key<Serializable> key  = (Key<Serializable>) configTypeProperty.getValue();
			
			
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
						
			Map parameterMap = new HashMap();
			parameterMap.put("configTypeId", key.getKeyValue());
			
			StandardAction action = new StandardAction(Entity.class, "configuration.ConfigurationService.getConfigTypeFromKey", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					
				}

				@Override
				public void onSuccess(Result<Entity> result) {
					if(result!=null){
						Entity propertyConfig   = result.getOperationResult();
						if(propertyConfig!=null){
							
							if(propertyConfig.getPropertyByName("configtypes")!=null){
								ArrayList<Entity> configList = propertyConfig.getPropertyByName("configtypes");
								showConfigurator(propertyConfig, configList);
							}
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case FieldEvent.VALUECHANGED:{
				if(eventSource instanceof ListBoxField){
					ListBoxField listBoxField = (ListBoxField) eventSource;
					String selectedStr = listBoxField.getSelectedValue().toString().split(":")[0];
					String configName = listBoxField.getSelectedValue().toString().split(":")[1];
					
					boolean entityExists = fetchSnippetComponentConfigType(configName);
					if(!entityExists){
						Entity configTypeEnt  = getConfigTypeEntity(configName);
						saveSnippetComponentConfigType(configTypeEnt, selectedStr);
					}else{
						getConfigTypeList(configTypeEntityMap.get(configName));
					}
				}
			}
			default:
				break;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ViewConfigurationEditor :: onFieldEvent :: Exception", e);
		}
		
	}

	/**
	 * @return the viewConfigTypeEntity
	 */
	public Entity getViewConfigTypeEntity() {
		return viewConfigTypeEntity;
	}

	/**
	 * @param viewConfigTypeEntity the viewConfigTypeEntity to set
	 */
	public void setViewConfigTypeEntity(Entity viewConfigTypeEntity) {
		this.viewConfigTypeEntity = viewConfigTypeEntity;
	}

	/*private void updateConfigurationList(EntityList newEntityList,Entity parentConfTypeEnt){
		try {
			if(newEntityList!=null && !newEntityList.isEmpty()){
				Key key = (Key) parentConfTypeEnt.getPropertyByName("id");
				long parentConfigTypeId = (Long) key.getKeyValue();
				
				 Iterator it = configTypeEntityMap.entrySet().iterator();
				    while (it.hasNext()) {
				        Map.Entry pairs = (Map.Entry)it.next();
				        Entity parentConfigTypeFromMap = (Entity) pairs.getKey();
				        
				        ArrayList<Entity> existingList = (ArrayList<Entity>) pairs.getValue();
				        Key configTypeEntKey = (Key)parentConfigTypeFromMap.getProperty("id").getValue();
						long configTypeIdFromMap = (Long) configTypeEntKey.getKeyValue();
						if(parentConfigTypeId == configTypeIdFromMap){
							
							 boolean isConfPropertyExist = checkIfPropertyAlreadyExist(existingList, newEntityList.get(0));
							 if(!isConfPropertyExist){
								 newEntityList.addAll(existingList);
							 }else{
								 newEntityList = getUpdatedList(newEntityList,existingList);
							 }
							 configTypeEntityMap.put(parentConfigTypeFromMap, newEntityList);
							 break;
						}
				    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	
	
	
/*	private EntityList getUpdatedList(EntityList newEntityList,ArrayList<Entity> existingEntityList){
		
		for (int i = 0; i < newEntityList.size(); i++) {
			
			Key key = (Key) newEntityList.get(i).getPropertyByName("id");
			long id = (Long) key.getKeyValue();
			int index = getIndexOfEntityToRemove(existingEntityList, id);
			if(index!=-1)
				existingEntityList.remove(index);
		}
		newEntityList.addAll(existingEntityList);
		
		return newEntityList;
	}*/
	
/*	private int getIndexOfEntityToRemove(ArrayList<Entity> existingEntityList ,long id){
		
		for (int index = 0; index < existingEntityList.size(); index++) {
			Key key1 = (Key) existingEntityList.get(index).getPropertyByName("id");
			long id1 = (Long) key1.getKeyValue();
			if(id == id1){
				return index;
			}
		}
		return -1;
	}
	*/
/*	private boolean checkIfPropertyAlreadyExist(ArrayList<Entity> existingList, Entity newEntity){
		
		String newKeyName = newEntity.getPropertyByName("keyname");
		
		for(int i=0; i< existingList.size(); i++){
			Entity existingEnt  = existingList.get(i);
			String existingKeyName = existingEnt.getPropertyByName("keyname");
			if(newKeyName.equalsIgnoreCase(existingKeyName)){
				return true;
			}
		}
		return false;
		
	}
*/
}