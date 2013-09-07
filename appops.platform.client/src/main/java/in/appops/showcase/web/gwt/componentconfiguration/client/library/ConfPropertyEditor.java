package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.RadioButtonField;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author pallavi@ensarm.com
 *
 */
public class ConfPropertyEditor extends VerticalPanel implements FieldEventHandler,ConfigEventHandler, ClickHandler{
	
	private final String COMPFORM_PANEL_CSS = "componentFormPanel";
	private static String SAVE_BTN_PCLS = "saveConfigButton";
	private final String SAVECONFIGURATION_BTN_ID = "saveConfigBtnId";
	
	private FlexTable propValuePanel;
	private TextField propNameField;
	private int valueRow = 0;
	private HashMap<Integer, PropertyValueEditor> propValueList;
	private HashMap<Long, Entity> idVsConfigTypeEntity;
	private Entity parentConfTypeEnt;
	private int currentSeletedRow = -1;
	private Entity deletedConfigEntity = null;
		
	public ConfPropertyEditor() {
		
	}
	
	public void createUi(){
		try {
			HorizontalPanel horizontalPanel = new HorizontalPanel();
			propValuePanel = new FlexTable();
			
			propNameField = new TextField();
			propNameField.setConfiguration(getPropNameFieldConf(null));
			propNameField.configure();
			propNameField.create();
			
			horizontalPanel.add(propNameField);
			horizontalPanel.add(propValuePanel);		
					
			createNewRecord();
			
			ButtonField saveConfigBtn = new ButtonField();
			saveConfigBtn.setConfiguration(getSaveConfigurationBtnConf());
			saveConfigBtn.configure();
			saveConfigBtn.create();
			
			
			add(horizontalPanel);
			add(saveConfigBtn);
			setStylePrimaryName(COMPFORM_PANEL_CSS);
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
			AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
			
			propValuePanel.addClickHandler(this);
		} catch (Exception e) {
			
		}
	}
	
	private void createNewRecord() {
		try {
			PropertyValueEditor propValueEditor = new PropertyValueEditor(propValuePanel, valueRow, null);
			propValueEditor.createUi();
			if(propValueList == null){
				propValueList = new HashMap<Integer, PropertyValueEditor>();
			}
			propValueList.put(valueRow, propValueEditor);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Creates the save button configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getSaveConfigurationBtnConf(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Save configuration");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,SAVE_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, true);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, SAVECONFIGURATION_BTN_ID);
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
	/**
	 * Method creates the property name field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getPropNameFieldConf(String propName){
		Configuration configuration = new Configuration();
		
		try {
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter property name");
			
			if(propName!=null)
				configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, propName);
			
			configuration.setPropertyByName(TextFieldConstant.BF_ID, propName);
			
			configuration.setPropertyByName(TextFieldConstant.BF_BLANK_TEXT,"Property can't be empty");
			configuration.setPropertyByName(TextFieldConstant.BF_ALLOWBLNK,false);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS,TextFieldConstant.BF_ERRINLINE);
			
		} catch (Exception e) {
		}
		return configuration;
	}
	
	private void insertEmptyRecord(){
		
		try {
			valueRow++;
			PropertyValueEditor propValueEditor = new PropertyValueEditor(propValuePanel, valueRow, null);
			propValueEditor.createUi();
			if(propValueList == null){
				propValueList = new HashMap<Integer, PropertyValueEditor>();
			}
			propValueList.put(valueRow, propValueEditor);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case FieldEvent.CLICKED: {
				if (eventSource instanceof ButtonField) {
					ButtonField btnField = (ButtonField) eventSource;
					if (btnField.getBaseFieldId().equals(SAVECONFIGURATION_BTN_ID)) {
						saveConfTypeEntity();
					}
				}
				break;
			}case FieldEvent.EDITCOMPLETED: {
				if (eventSource instanceof RadioButtonField) {
					RadioButtonField radioBtnField = (RadioButtonField) eventSource;
					if(radioBtnField.getBaseFieldId().equals(PropertyValueEditor.ISDEF_RADIOBTN_ID)){
						saveConfTypeEntity();
					}
				}
				break;
			}default:
				
				break;
			}
		} catch (Exception e) {
			
		}
	}
		
	
	@SuppressWarnings("unchecked")
	private void saveConfTypeEntity() {
		try {
			Entity confTypeEntity = propValueList.get(valueRow).getPopulatedConfigTypeEntity(propNameField.getValue().toString());
			confTypeEntity.setProperty("parentId", parentConfTypeEnt);
			
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
						
			Map parameterMap = new HashMap();
			parameterMap.put("configTypeEnt", confTypeEntity);
			parameterMap.put("update", false);
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveConfigurationType", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<Entity> result) {
					if (result != null) {
						Entity confEnt = result.getOperationResult();
						Window.alert("Property saved successfully");
						
						propValueList.get(valueRow).setConfTypeEntity(confEnt);
						Key key = (Key) confEnt.getProperty("id").getValue();
						long id = (Long) key.getKeyValue();
						propValueList.get(valueRow).setEntityIdToCrossImage(id);
						if(idVsConfigTypeEntity==null)
							idVsConfigTypeEntity = new HashMap<Long, Entity>();
						idVsConfigTypeEntity.put(id, confEnt);
						insertEmptyRecord();
					}
				}
			});
		} catch (Exception e) {
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void deleteConfigType(Entity configTypeEnt ) {
		try {
						
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
						
			Map parameterMap = new HashMap();
			parameterMap.put("configTypeEnt", configTypeEnt);
					
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.deleteConfigurationType", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Property removing failed...");
				}

				@Override
				public void onSuccess(Result<Entity> result) {
					if(result!=null){
						Entity confEnt = result.getOperationResult();
						Window.alert("Property removed successfully");
						deletedConfigEntity = confEnt;
						deleteRowFromUi();
						
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void deleteRowFromUi(){
		if (currentSeletedRow != -1 && deletedConfigEntity != null) {
			propValuePanel.removeRow(currentSeletedRow);
			valueRow--;
			currentSeletedRow = -1;
			deletedConfigEntity= null;
		}
	}
	
	@Override
	public void onConfigEvent(ConfigEvent event) {
		try {
			int eventType = event.getEventType();
			switch (eventType) {
			case ConfigEvent.PROPERTYSELECTED: {
				Entity entity = (Entity) event.getEventData();
				String name = entity.getPropertyByName("name");
				propNameField.setFieldValue(name);
				break;
			}
			case ConfigEvent.PROPERTYREMOVED: {
				long entityId = (Long) event.getEventData();
				Entity configTypeEnt = idVsConfigTypeEntity.get(entityId);
				deleteConfigType(configTypeEnt);
				break;
			}case ConfigEvent.NEW_COMPONENT_SAVED: {
				HashMap<String, Entity> map = (HashMap<String, Entity>) event.getEventData();
				Entity configTypeEntity   = map.get("componentConfigType");
				parentConfTypeEnt = configTypeEntity;
			}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Entity getParentConfTypeEnt() {
		return parentConfTypeEnt;
	}

	public void setParentConfTypeEnt(Entity parentConfTypeEnt) {
		this.parentConfTypeEnt = parentConfTypeEnt;
	}

	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource() instanceof FlexTable){
			FlexTable flexTable = (FlexTable) event.getSource();
			 int cellIndex = flexTable.getCellForEvent(event).getCellIndex();
			 currentSeletedRow = flexTable.getCellForEvent(event).getRowIndex();
			 if(cellIndex==4 && currentSeletedRow!=valueRow){
				 deleteRowFromUi();
			 }
				 
		}
		
	}
	
}
