/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.SelectedItem;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author pallavi@ensarm.com
 *
 */
public class ComponentListDisplayer extends Composite implements FieldEventHandler,ConfigEventHandler,ClickHandler{

	private VerticalPanel basePanel;
	private FlexTable compListPanel;
	private int componentRow = 0;
	private Entity libraryEntity;
	private Logger logger = Logger.getLogger("ComponentListDisplayer");
	
	/** CSS styles **/
	private final String HEADERLBL_CSS = "componentSectionHeaderLbl";
	private final String LISTPANEL = "componentFormPanel";
	private final String COMPLISTLBL_CSS = "compRegisterLabelCss";
	private final String COMPLISTROW_CSS = "componentListRow";
	
	private HashMap<Integer, Entity> componentList ;
	
	public ComponentListDisplayer(){
		
	}

	public void createUi() {
		try {
			basePanel = new VerticalPanel();
						
			compListPanel = new FlexTable();
			LabelField compListLbl = new LabelField();
			Configuration compListLblConfig = getCompListLblConfig();

			compListLbl.setConfiguration(compListLblConfig);
			compListLbl.configure();
			compListLbl.create();
			
			LabelField nameLbl = new LabelField();
			Configuration headerLblConfig = getHeaderLblConfig("Name");
		
			nameLbl.setConfiguration(headerLblConfig);
			nameLbl.configure();
			nameLbl.create();
			
			LabelField descLbl = new LabelField();
			Configuration descLblConfig = getHeaderLblConfig("Description");
		
			descLbl.setConfiguration(descLblConfig);
			descLbl.configure();
			descLbl.create();
			
			compListPanel.setWidget(componentRow, 0, nameLbl);
			compListPanel.setWidget(componentRow, 1, descLbl);
			
			compListPanel.getRowFormatter().setStylePrimaryName(componentRow, COMPLISTROW_CSS);
			
			componentRow++;
			
			basePanel.add(compListLbl);
			basePanel.add(compListPanel);
			
			basePanel.setCellHorizontalAlignment(compListLbl, HorizontalPanel.ALIGN_CENTER);
			compListPanel.setStylePrimaryName(LISTPANEL);
			
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
			AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
			compListPanel.addClickHandler(this);
			
			initWidget(basePanel);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentListDisplayer :: createUi :: Exception", e);
		}
	}
	
	private Configuration getHeaderLblConfig(String displayTxt) {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayTxt);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}
	
	private Configuration getCompListLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Components");
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
		}
		catch(Exception e){
			logger.log(Level.SEVERE, "ComponentListDisplayer :: getCompListLblConfig :: Exception", e);
		}
		return configuration;
	}
	
	@SuppressWarnings("unchecked")
	private void populateComponents() {
		try {
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Long libId = ((Key<Long>)libraryEntity.getPropertyByName("id")).getKeyValue();
									
			Map parameterMap = new HashMap();
			parameterMap.put("libraryId", libId);
			
			StandardAction action = new StandardAction(EntityList.class, "appdefinition.AppDefinitionService.listComponentDefinitions", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<EntityList> result) {
					if(result!=null){
						EntityList componentlist   = result.getOperationResult();
						if(!componentlist.isEmpty()){
							populate(componentlist);
						}
					}
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentListDisplayer :: populateComponents :: Exception", e);
		}
	}
	
	private void populate(EntityList componentlist) {
		try {
			for(Entity comp:componentlist){
				//ComponentPanel componentPanel = new ComponentPanel(comp);
				//componentPanel.createUi();
				//compListPanel.setWidget(componentRow, 0, componentPanel);
				//componentRow++;
				createComponentRow(comp);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentListDisplayer :: populate :: Exception", e);
		}
	}
	
	public void createComponentRow(Entity componentEntity){
		
		LabelField nameLbl = new LabelField();
		Configuration headerLblConfig = getLblConfig("name",componentEntity);
	
		nameLbl.setConfiguration(headerLblConfig);
		nameLbl.configure();
		nameLbl.create();
		
		LabelField descLbl = new LabelField();
		Configuration descLblConfig = getLblConfig("htmldescription",componentEntity);
	
		descLbl.setConfiguration(descLblConfig);
		descLbl.configure();
		descLbl.create();
		
		if(componentList == null)
			componentList = new HashMap<Integer, Entity>();
		
		compListPanel.setWidget(componentRow, 0, nameLbl);
		compListPanel.setWidget(componentRow, 1, descLbl);
		
		componentList.put(componentRow,componentEntity);
		compListPanel.getRowFormatter().setStylePrimaryName(componentRow, COMPLISTROW_CSS);
		
		componentRow++;
			
	}
	
	private Configuration getLblConfig(String propertyName, Entity componentEntity) {
		Configuration configuration = null;	
		try{
			String displayText = "Description not available";
			configuration = new Configuration();
			if(componentEntity.getPropertyByName(propertyName)!=null){
				displayText = componentEntity.getPropertyByName(propertyName).toString();
			}else{
				
			}
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, COMPLISTLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}
	
	public void addComponent(Entity component) {
		try {
			//ComponentPanel componentPanel = new ComponentPanel(component);
			//componentPanel.createUi();
			//compListPanel.setWidget(componentRow, 0, componentPanel);
			//componentRow++;
			createComponentRow(component);
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentListDisplayer :: addComponent :: Exception", e);
		}
	}
	
	private EntityList getDummyList(){
		Entity labelField = new Entity();
		labelField.setPropertyByName("name","LabelField");
		labelField.setPropertyByName("htmldescription","   Display text");
		
		Entity dateLabelField = new Entity();
		dateLabelField.setPropertyByName("name","DateLabelField");
		dateLabelField.setPropertyByName("htmldescription","   Display date in different formats");
		
		EntityList list = new EntityList();
		list.add(labelField);
		list.add(dateLabelField);
		return list;
	}

	public Entity getLibraryEntity() {
		return libraryEntity;
	}

	public void setLibraryEntity(Entity libraryEntity) {
		this.libraryEntity = libraryEntity;
	}
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		try{
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			
			if(eventType == FieldEvent.VALUECHANGED){
				if(eventSource instanceof ListBoxField){
					ListBoxField listBoxField = (ListBoxField) eventSource;
					if(listBoxField.getBaseFieldId().equalsIgnoreCase(LibraryComponentManager.LIBRARYLISTBOX_ID)){
						SelectedItem selectedItem = (SelectedItem) event.getEventData();
						Entity libEntity = selectedItem.getAssociatedEntity();
						libraryEntity = libEntity;
						populateComponents();
					}
				}
			}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentListDisplayer :: onFieldEvent :: Exception", e);
		}
	}

	@Override
	public void onConfigEvent(ConfigEvent event) {
		try{
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			
			if(eventType == ConfigEvent.ADDCOMPONENTTOLIST){
				Entity componentEntity = (Entity) event.getEventData();
				addComponent(componentEntity);
			}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentListDisplayer :: onConfigEvent :: Exception", e);
		}
		
	}

	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource() instanceof FlexTable){
			FlexTable flex = (FlexTable) event.getSource();
			int cellIndex = flex.getCellForEvent(event).getCellIndex();
	        int rowIndex = flex.getCellForEvent(event).getRowIndex();
	        
	        ConfigEvent configEvent = new ConfigEvent(ConfigEvent.COMPONENTSELECTED, componentList.get(rowIndex), this);
			configEvent.setEventSource(this);
			AppUtils.EVENT_BUS.fireEvent(configEvent);
		}
	}
}