package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.ImageField;
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
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author pallavi@ensarm.com
 *
 */
public class ConfPropertyEditor extends VerticalPanel implements FieldEventHandler,ConfigEventHandler{
	
	private final String COMPFORM_PANEL_CSS = "componentFormPanel";
	private static String SAVE_BTN_PCLS = "saveConfigButton";
	private final String SAVECONFIGURATION_BTN_ID = "saveConfigBtnId";
	
	private FlexTable propValuePanel;
	private TextField propNameField;
	private int valueRow = 0;
	private HashMap<Integer, PropertyValueEditor> propValueList;
	private Entity confTypeEnt;
		
	public ConfPropertyEditor(Entity componentDefEnt, EntityList componentDeflist) {
		
	}
	
	public void createUi(){
		try {
			propValuePanel = new FlexTable();
			
			propNameField = new TextField();
			propNameField.setConfiguration(getPropNameFieldConf(null));
			propNameField.configure();
			propNameField.create();
			
			ButtonField saveConfigBtn = new ButtonField();
			saveConfigBtn.setConfiguration(getSaveConfigurationBtnConf());
			saveConfigBtn.configure();
			saveConfigBtn.create();
			
			propValuePanel.setWidget(valueRow, 0, propNameField);
			
			propValuePanel.setWidget(valueRow+2, 0, saveConfigBtn);
			
			/*for(Entity confEnt:componentDeflist){
				PropertyValueEditor propValueEditor = new PropertyValueEditor(propValuePanel, valuePanelRow, confEnt);
				propValueEditor.createUi();
				valuePanelRow++;
				if(propValueList == null){
					propValueList = new ArrayList<PropertyValueEditor>();
				}
				propValueList.add(propValueEditor);
			}*/
			
			insertEmptyRecord();
			
			add(propValuePanel);
			setStylePrimaryName(COMPFORM_PANEL_CSS);
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
			AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
		} catch (Exception e) {
			
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
			PropertyValueEditor propValueEditor = new PropertyValueEditor(propNameField.getValue().toString(), propValuePanel, valueRow, null);
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
				if (eventSource instanceof ImageField) {
					ImageField imageField = (ImageField) eventSource;
					if(imageField.getBaseFieldId().equals(PropertyValueEditor.REMOVEPROP_IMGID)){
						//remove property.
					}if (eventSource instanceof ButtonField) {
						ButtonField btnField = (ButtonField) eventSource;
						if(btnField.getBaseFieldId().equals(SAVECONFIGURATION_BTN_ID)){
							saveConfTypeEntity();
						}
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
			Entity confTypeEntity = propValueList.get(valueRow).getPopulatedConfigTypeEntity();
			
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
					if(result!=null){
						Entity confEnt = result.getOperationResult();
						Window.alert("Property saved successfully");
						propValueList.get(valueRow).setConfTypeEntity(confEnt);
						insertEmptyRecord();
					}
				}
			});
		} catch (Exception e) {
		}
		
	}
	

	@Override
	public void onConfigEvent(ConfigEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case ConfigEvent.PROPERTYSELECTED: {
				if (eventSource instanceof ConfigurationListDisplayer) {
					Entity entity=  (Entity) event.getEventData(); 
					String name = entity.getPropertyByName("name");
					propNameField.setFieldValue(name);
				}
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Entity getConfTypeEnt() {
		return confTypeEnt;
	}

	public void setConfTypeEnt(Entity confTypeEnt) {
		this.confTypeEnt = confTypeEnt;
	}
}
