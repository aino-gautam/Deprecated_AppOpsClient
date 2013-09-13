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
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com
 *
 */
public class ViewConfigurationEditor extends Composite implements FieldEventHandler {

	private VerticalPanel basePanel;
	private final String REGULARLBL_CSS = "regularLabel";
	private Logger logger = Logger.getLogger("ViewConfigurationEditor");
	private ListBoxField spanListBox;
	private Entity viewConfigTypeEntity;
	
	public ViewConfigurationEditor(){
		initialize();
	}

	private void initialize() {
		try{
			basePanel = new VerticalPanel();
			spanListBox = new ListBoxField();
			initWidget(basePanel);
			
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * creates the UI for view configuration editing
	 */
	public void createUI(){
		HorizontalPanel hpSpanSelection = new HorizontalPanel();
		
		LabelField subHeaderLbl = new LabelField();
		Configuration subHeaderLblConfig = getLblConfig(" Select a span element ");
		subHeaderLbl.setConfiguration(subHeaderLblConfig);
		subHeaderLbl.configure();
		subHeaderLbl.create();
		
		hpSpanSelection.add(subHeaderLbl);
		hpSpanSelection.add(spanListBox);
		
		basePanel.add(hpSpanSelection);
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
			logger.log(Level.SEVERE, "SnippetManager :: getLblConfig :: Exception", e);
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
			logger.log(Level.SEVERE, "SnippetManager :: getSpansListBoxConfiguration :: Exception", e);
		}
		return configuration;
	}

	private void showConfigurator(Entity configEntity, ArrayList<Entity> configList){
		ConfigurationEditor configEditor = new ConfigurationEditor();
		configEditor.createUi(configEntity, configList);
		basePanel.add(configEditor);
		
	}

	private Entity getConfigTypeEntity(String configName){
		try{
			Entity compEntity = new Entity();
			compEntity.setType(new MetaType("Configtype"));
			compEntity.setPropertyByName("keyname", configName);
			compEntity.setPropertyByName("parentId", getViewConfigTypeEntity()); // ems typeId for html snipppet
			return compEntity;
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "SnippetManager :: getComponentDefinitionEntity :: Exception", e);
		}
		return null;
	}
	
	private void fetchComponentConfig(Entity configTypeEntity, String typeName){
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
							showConfigurator(propertyConfig, configList);
						}
					}
					
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: saveComponent :: Exception", e);
		}
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
					Entity configTypeEnt  = getConfigTypeEntity(configName);
					fetchComponentConfig(configTypeEnt, selectedStr);
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

}