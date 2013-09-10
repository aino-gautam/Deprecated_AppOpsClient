package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
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
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author pallavi@ensarm.com
 *
 */
public class ConfPropertyEditor extends VerticalPanel implements FieldEventHandler,ConfigEventHandler, ClickHandler{
	
	/**CSS styles **/
	private final String COMPFORM_PANEL_CSS = "componentFormPanel";
	private static String SAVE_BTN_PCLS = "saveConfigButton";
	private final String SAVECONFIGURATION_BTN_ID = "saveConfigBtnId";
	private final String PROPNAMEFIELD_PCLS = "propertNameField";
	private final String POPUPGLASSPANELCSS = "popupGlassPanel";
	private final String POPUP_CSS = "popupCss";
	private final String POPUP_LBL_PCLS = "popupLbl";
	
	private FlexTable propValuePanel;
	private TextField propNameField;
	private int valueRow = 0;
	private HashMap<Integer, PropertyValueEditor> propValueList;
	private HashMap<Long, Entity> idVsConfigTypeEntity;
	private Entity parentConfTypeEnt;
	private int currentSeletedRow = -1;
	private Entity deletedConfigEntity = null;
	private Entity confTypeEnt;	
	private EntityList configTypeList ;
	
	public ConfPropertyEditor(Entity configType) {
		this.parentConfTypeEnt = configType;
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
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Add Configuration");
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
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter the property name");
			
			if(propName!=null)
				configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, propName);
			
			configuration.setPropertyByName(TextFieldConstant.BF_ID, propName);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, PROPNAMEFIELD_PCLS);
			
			/*configuration.setPropertyByName(TextFieldConstant.BF_BLANK_TEXT,"Property can't be empty");
			configuration.setPropertyByName(TextFieldConstant.BF_ALLOWBLNK,false);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS,TextFieldConstant.BF_ERRINLINE);*/
			
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
						clearPropertyValueFields();
						updateConfypeEntity();
					}
				}
				break;
			}case FieldEvent.TAB_KEY_PRESSED: {
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
		
	
	private void updateConfypeEntity() {
		// TODO Auto-generated method stub
		
	}

	private void clearPropertyValueFields() {
		try {
			propNameField.clear();
			propValuePanel.clear();
			createNewRecord();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void saveConfTypeEntity() {
		try {
			Entity confTypeEntity = propValueList.get(valueRow).getPopulatedConfigTypeEntity(propNameField.getValue().toString());
			confTypeEntity.setProperty("configtype", parentConfTypeEnt);
			
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
						propValueList.get(valueRow).setConfTypeEntity(confEnt);
						Key key = (Key) confEnt.getProperty("id").getValue();
						long id = (Long) key.getKeyValue();
						propValueList.get(valueRow).setEntityIdToCrossImage(id);
						if(idVsConfigTypeEntity==null)
							idVsConfigTypeEntity = new HashMap<Long, Entity>();
						idVsConfigTypeEntity.put(id, confEnt);
						
						if(configTypeList == null)
							configTypeList = new EntityList();
						
						propValueList.get(valueRow).showCheckImage();
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
						showPopup(confEnt.getPropertyByName("keyvalue").toString()+ " removed successfully");
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
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case ConfigEvent.PROPERTYSELECTED: {
				if (eventSource instanceof ConfigurationListDisplayer) {
					HashMap<String, EntityList> configTypeHashMap=  (HashMap<String, EntityList>) event.getEventData(); 
					Iterator iterator = configTypeHashMap.entrySet().iterator();
					while(iterator.hasNext()){
						
						Map.Entry mapEntry = (Map.Entry) iterator.next();
						String keyname= (String) mapEntry.getKey();
						
						
						EntityList typesList = (EntityList) mapEntry.getValue();
						propNameField.setFieldValue(keyname);
						propValuePanel.clear();
						for(Entity entity : typesList){
							confTypeEnt = entity;
							
							populateConfFormEditor();
							valueRow++;
						}
						valueRow=0;
					}
				}
				break;
			}
			case ConfigEvent.PROPERTYREMOVED: {
				long entityId = (Long) event.getEventData();
				Entity configTypeEnt = idVsConfigTypeEntity.get(entityId);
				deleteConfigType(configTypeEnt);
				break;
			}case ConfigEvent.NEW_COMPONENT_REGISTERED: {
				HashMap<String, Entity> map = (HashMap<String, Entity>) event.getEventData();
				Entity configTypeEntity   = map.get("componentConfigType");
				parentConfTypeEnt = configTypeEntity;
			}case ConfigEvent.COMPONENTSELECTED: {
				HashMap<String, Object> map = (HashMap<String, Object>) event.getEventData();
				Entity componentEntity   = (Entity) map.get("component");
				Entity configTypeEnt = new Entity();
				configTypeEnt.setType(new MetaType("Configtype"));
				
				Key key = (Key)componentEntity.getPropertyByName("id");
				Property<Key<Long>> keyProp = new Property<Key<Long>>();
				keyProp.setValue(key);
				configTypeEnt.setProperty("id", keyProp);
				
				parentConfTypeEnt = configTypeEnt;
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
	public void populateConfFormEditor(){
		try {
			PropertyValueEditor propValueEditor;
			if(confTypeEnt!=null){
				 propValueEditor = new PropertyValueEditor(propValuePanel, valueRow, confTypeEnt);
				propValueEditor.createUi();
			}else{
				 propValueEditor = new PropertyValueEditor(propValuePanel, valueRow, null);
				propValueEditor.createUi();
			}
			
			/*PropertyValueEditor propValueEditor = new PropertyValueEditor(propNameField.getValue().toString(), propValuePanel, valueRow, null);
			propValueEditor.createUi();*/
			if(propValueList == null){
				propValueList = new HashMap<Integer, PropertyValueEditor>();
			}
			propValueList.put(valueRow, propValueEditor);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
}
