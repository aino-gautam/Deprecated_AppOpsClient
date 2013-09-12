/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;


import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.SelectedItem;
import in.appops.client.common.config.field.StateField;
import in.appops.client.common.config.field.StateField.StateFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.client.EntityContext;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com
 *
 */
public class ComponentRegistrationForm extends Composite implements FieldEventHandler, ConfigEventHandler{
	
	private VerticalPanel basePanel;
	private Entity libraryEntity;
	private StateField componentNameField;
	private ButtonField saveComponentBtnFld;
	private Entity compEntityToUpdate;
	private int componnetEntRow;
	private Logger logger = Logger.getLogger("ComponentRegistrationForm");
		
	/** CSS styles **/
	private final String SAVECOMP_BTN_PCLS = "saveCompBtnCss";
	private final String SAVECOMP_BTN_DCLS = "saveCompBtnDependentcss";
	private final String COMPFORM_PANEL_CSS = "componentFormPanel";
	private final String HEADERLBL_CSS = "componentSectionHeaderLbl";
	private final String POPUPGLASSPANELCSS = "popupGlassPanel";
	private final String POPUP_CSS = "popupCss";
	private final String POPUP_LBL_PCLS = "popupLbl";
	private final String SUGGESTIONBOX_PCLS = "componentNameSuggestionBox";
	
	/** Field ID **/
	private static String SAVECOMPONENT_BTN_ID = "saveCompBtnId";
	public final String COMPONENTTYPELISTBOX_ID = "componenttTypeListBoxId";
	
	public ComponentRegistrationForm(){
		initialize();
	}

	public void createUi() {
		try{
					
			VerticalPanel containerTable = new VerticalPanel();
			
			componentNameField = new StateField();
			Configuration stateFieldConfig = getComponentSuggestionFieldConf(false);
			componentNameField.setConfiguration(stateFieldConfig);
			componentNameField.configure();
			componentNameField.create();	
								
			saveComponentBtnFld = new ButtonField();
			Configuration savebTnConfig = getSaveBtnConfig(false);
			saveComponentBtnFld.setConfiguration(savebTnConfig);
			saveComponentBtnFld.configure();
			saveComponentBtnFld.create();
			
			containerTable.add(componentNameField);
			containerTable.add(saveComponentBtnFld);
						
			LabelField headerLbl = new LabelField();
			Configuration headerLblConfig = getHeaderLblConfig();
		
			headerLbl.setConfiguration(headerLblConfig);
			headerLbl.configure();
			headerLbl.create();
			
			basePanel.add(headerLbl);
			basePanel.setCellHorizontalAlignment(headerLbl, HorizontalPanel.ALIGN_LEFT);
			basePanel.add(containerTable);
			containerTable.addStyleName(COMPFORM_PANEL_CSS);
			
		}
		catch (Exception e) {	
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: createUi :: Exception", e);
		}
	}
		
	private Configuration getComponentSuggestionFieldConf(Boolean isEnabled) {

		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(StateFieldConstant.IS_STATIC_BOX,false);
			configuration.setPropertyByName(StateFieldConstant.STFD_OPRTION,"appdefinition.AppDefinitionService.getEntityList");
			configuration.setPropertyByName(StateFieldConstant.STFD_QUERYNAME,"getAllComponentDefinition");
			configuration.setPropertyByName(StateFieldConstant.STFD_ENTPROP,"name");
			configuration.setPropertyByName(StateFieldConstant.STFD_QUERY_MAXRESULT,10);
			configuration.setPropertyByName(StateFieldConstant.IS_AUTOSUGGESTION,false);
			configuration.setPropertyByName(StateFieldConstant.BF_SUGGESTION_POS,StateFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(StateFieldConstant.BF_SUGGESTION_TEXT,"Component Name");
			configuration.setPropertyByName(StateFieldConstant.BF_PCLS,SUGGESTIONBOX_PCLS);
			configuration.setPropertyByName(StateFieldConstant.BF_ENABLED,isEnabled);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return configuration;
	}
	
	private Configuration getHeaderLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Register a component");
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
		}
		catch(Exception e){
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: getHeaderLblConfig :: Exception", e);
		}
		return configuration;
	}

	private Configuration getSaveBtnConfig(Boolean isEnabled) {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Register");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,SAVECOMP_BTN_PCLS);
			if(isEnabled)
				configuration.setPropertyByName(ButtonFieldConstant.BF_DCLS,SAVECOMP_BTN_DCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, SAVECOMPONENT_BTN_ID);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED,isEnabled);
		}
		catch(Exception e){
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: getSaveBtnConfig :: Exception", e);
		}
		return configuration;
	}

	private void initialize() {
		try{
			basePanel = new VerticalPanel();
			initWidget(basePanel);
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
			AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
		}
		catch (Exception e) {	
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: initialize :: Exception", e);
		}
	}
	@Override
	public void onConfigEvent(ConfigEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case ConfigEvent.COMPONENTSELECTED: {
				 HashMap<String,Object> rowVsCompEnt  = (HashMap<String, Object>) event.getEventData();
				Entity componentEnt = (Entity) rowVsCompEnt.get("component");
				this.compEntityToUpdate = componentEnt;
				this.componnetEntRow = (Integer) rowVsCompEnt.get("row");
				fillRegistrationForm();
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillRegistrationForm() {
		componentNameField.setValue(compEntityToUpdate.getPropertyByName("name").toString());
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try{
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			
			if (event.getEventType() == FieldEvent.CLICKED) {
				if (event.getEventSource() instanceof ButtonField){
					ButtonField saveCompBtnField = (ButtonField) eventSource;
					if (saveCompBtnField.getBaseFieldId().equals(SAVECOMPONENT_BTN_ID)) {
						
						if(compEntityToUpdate!=null){
							updateComponent();
						}else if(libraryEntity!=null){
							saveComponent();
						}else{
							showPopup("Please select a library");
						}
					}
				}
			}else if(eventType == FieldEvent.VALUECHANGED){
				if(eventSource instanceof ListBoxField){
					ListBoxField listBoxField = (ListBoxField) eventSource;
					SelectedItem selectedItem = (SelectedItem) event.getEventData();
					if(listBoxField.getBaseFieldId().equalsIgnoreCase(LibraryComponentManager.LIBRARYLISTBOX_ID)){
						if(selectedItem!=null){
							Entity libEntity = selectedItem.getAssociatedEntity();
							libraryEntity = libEntity;
							enableRegistrationForm(true);
						}else{
							libraryEntity = null;
							enableRegistrationForm(false);
						}
						
					}
				}
			}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: onFieldEvent :: Exception", e);
		}
	}
	
	private void enableRegistrationForm(Boolean isEnable){
		componentNameField.removeRegisteredHandlers();
		Configuration stateFieldConfig = getComponentSuggestionFieldConf(isEnable);
		componentNameField.setConfiguration(stateFieldConfig);
		componentNameField.configure();
		componentNameField.create();	
							
		saveComponentBtnFld.removeRegisteredHandlers();
		Configuration savebTnConfig = getSaveBtnConfig(isEnable);
		saveComponentBtnFld.setConfiguration(savebTnConfig);
		saveComponentBtnFld.configure();
		saveComponentBtnFld.create();
	}

	@SuppressWarnings("unchecked")
	private void saveComponent() {
		try{
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Entity componentDefEnt = getPopulatedEntity();
			Map parameterMap = new HashMap();
			parameterMap.put("componentDefinition", componentDefEnt);
			parameterMap.put("library", libraryEntity);
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveComponentDefinition", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<HashMap<String, Entity>>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<HashMap<String, Entity>> result) {
					if(result!=null){
						HashMap<String, Entity> map = result.getOperationResult();
						Entity compEntity   = map.get("component");
						if(compEntity!=null){
							showPopup(compEntity.getPropertyByName("name").toString()+" saved succesfully...");
							componentNameField.clear();
							
							ConfigEvent configEvent = new ConfigEvent(ConfigEvent.NEW_COMPONENT_REGISTERED, map,this);
							AppUtils.EVENT_BUS.fireEvent(configEvent);
						}
					}
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: saveComponent :: Exception", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void updateComponent() {
		try{
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Entity componentDefEnt = getUpdatedComponentEntity();
			Map parameterMap = new HashMap();
			parameterMap.put("componentDefinition", componentDefEnt);
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.updateComponentDefinition", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<Entity> result) {
					if(result!=null){
						Entity compEntity = result.getOperationResult();
						if(compEntity!=null){
							showPopup(compEntity.getPropertyByName("name").toString()+" updated successfully...");
							componentNameField.clear();
							compEntityToUpdate = null;
							HashMap<String,Object> rowVsCompEnt = new HashMap<String,Object>();
					        rowVsCompEnt.put("row", componnetEntRow);
					        rowVsCompEnt.put("component", compEntity);
					        
					        ConfigEvent configEvent = new ConfigEvent(ConfigEvent.UPDATECOMPONENT_FROM_LIST,rowVsCompEnt , this);
							configEvent.setEventSource(this);
							AppUtils.EVENT_BUS.fireEvent(configEvent);
							
						}
					}
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: updateComponent :: Exception", e);
		}
	}

	private Entity getPopulatedEntity() {
		
		try{
			Entity compEntity = new Entity();
			compEntity.setType(new MetaType("Componentdefinition"));
			compEntity.setPropertyByName("name", componentNameField.getValue().toString());
			return compEntity;
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: getPopulatedEntity :: Exception", e);
		}
		return null;
		
	}
	
	private Entity getUpdatedComponentEntity() {
		try{
			compEntityToUpdate.setPropertyByName("name", componentNameField.getValue().toString());
			return compEntityToUpdate;
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: getPopulatedEntity :: Exception", e);
		}
		return null;
		
	}

	public Entity getLibraryEntity() {
		return libraryEntity;
	}

	public void setLibraryEntity(Entity libraryEntity) {
		this.libraryEntity = libraryEntity;
	}
	
	/**
	 * Used to show popup at perticular position.
	 * @param popuplabel
	 */
	private void showPopup(String popuplabel){
		try {
			LabelField popupLbl = new LabelField();
			popupLbl.setConfiguration(getLabelFieldConf(popuplabel,POPUP_LBL_PCLS,null,null));
			popupLbl.configure();
			popupLbl.create();
					
			PopupPanel popup = new PopupPanel();
			popup.setAnimationEnabled(true);
			popup.setAutoHideEnabled(true);
			popup.setGlassEnabled(true);
			popup.setGlassStyleName(POPUPGLASSPANELCSS);
			popup.setStylePrimaryName(POPUP_CSS);
			popup.add(popupLbl);
			popup.setPopupPosition(542, 70);
			popup.show();
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Creates the table name label field configuration object and return.
	 * @param displayText
	 * @param primaryCss
	 * @param dependentCss
	 * @param propEditorLblPanelCss
	 * @return Configuration instance
	 */
	private Configuration getLabelFieldConf(String displayText , String primaryCss , String dependentCss ,String propEditorLblPanelCss){
		Configuration conf = new Configuration();
		try {
			conf.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
			conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
			conf.setPropertyByName(LabelFieldConstant.BF_DCLS, dependentCss);
			conf.setPropertyByName(LabelFieldConstant.BF_BASEPANEL_PCLS, propEditorLblPanelCss);
		} catch (Exception e) {
			
		}
		return conf;
	}

}
