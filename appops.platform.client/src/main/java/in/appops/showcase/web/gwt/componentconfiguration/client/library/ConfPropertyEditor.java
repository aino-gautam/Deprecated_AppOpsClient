package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.CheckboxField;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.textfield.TextField;
import in.appops.client.common.config.field.textfield.TextField.TextFieldConstant;
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
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author pallavi@ensarm.com
 *
 */
public class ConfPropertyEditor extends VerticalPanel implements FieldEventHandler,ConfigEventHandler, ClickHandler{
	
	/**CSS styles **/
	private final String COMPFORM_PANEL_CSS = "componentFormPanel";
	private static String SAVE_BTN_PCLS = "saveConfigButton";
	private final String PROPNAMEFIELD_PCLS = "propertNameField";
	private final String POPUPGLASSPANELCSS = "popupGlassPanel";
	private final String POPUP_CSS = "popupCss";
	private final String POPUP_LBL_PCLS = "popupLbl";
	
	/**Field ID **/
	private final String SAVECONFIGURATION_BTN_ID = "saveConfigBtnId";
	private final String PROPNAME_FIELD_ID = "propeNameFieldId";
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
	private boolean updateConfiguration = false;
	private Entity confTypeEntToRemove = null;
	private boolean isDefaultSelected = false;
	private ArrayList<CheckboxField> selectedCheckBoxes  = null;
	private ArrayList<PropertyValueEditor> propertyValueEditorList  = null;
	private Widget parentContainer = null;
	
	private HandlerRegistration configEventhandler = null;
	private HandlerRegistration fieldEventhandler = null;
	
	public ConfPropertyEditor(Widget parentContainer) {
		
		this.parentContainer = parentContainer;
		
		if(fieldEventhandler == null)
			fieldEventhandler = AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		
		if(configEventhandler == null)
			configEventhandler = AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
	}
	
	public void deregisterHandler(){
		try {
			configEventhandler.removeHandler();
			fieldEventhandler.removeHandler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createUi(){
		try {
			clear();
			HorizontalPanel horizontalPanel = new HorizontalPanel();
			propValuePanel = new FlexTable();
			
			propNameField = new TextField();
			propNameField.removeRegisteredHandlers();
			propNameField.setConfiguration(getPropNameFieldConf(null));
			propNameField.configure();
			propNameField.create();
			
			horizontalPanel.add(propNameField);
			horizontalPanel.add(propValuePanel);		
			if(propertyValueEditorList == null)
					propertyValueEditorList = new ArrayList<PropertyValueEditor>();
			createNewRecord();
			
			ButtonField saveConfigBtn = new ButtonField();
			saveConfigBtn.setConfiguration(getSaveConfigurationBtnConf());
			saveConfigBtn.configure();
			saveConfigBtn.create();
			
			
			add(horizontalPanel);
			add(saveConfigBtn);
			setStylePrimaryName(COMPFORM_PANEL_CSS);
						
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
			propertyValueEditorList.add(propValueEditor);
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
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_TITLE, "Updates configurations and clears the editor.");
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
			
			configuration.setPropertyByName(TextFieldConstant.BF_ID, PROPNAME_FIELD_ID);
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
						
						if(isDefaultSelected && updateConfiguration){
							saveConfigTypeList();
						} else if(isDefaultSelected && !updateConfiguration){
							//configTypeList
							
							if(configTypeList!=null){
								
								PropertyValueEditor valEdit = propValueList.get(propValueList.size()-1);
								 if(valEdit!=null){
									if(!valEdit.checkIfRecordIsEmpty()){
										saveConfTypeEntity(true);
									}else{
										fireConfigEvent(ConfigEvent.UPDATEDCONFIGENTITYLIST);
										clearPropertyValueFields(false);
										//saveConfigTypeList();
									}
								 }else{
									 
									 valEdit = propValueList.get(propValueList.size());
										if(valEdit!=null){
											if(!valEdit.checkIfRecordIsEmpty()){
												saveConfTypeEntity(true);
											}else{
												
												fireConfigEvent(ConfigEvent.UPDATEDCONFIGENTITYLIST);
												clearPropertyValueFields(false);
												//saveConfigTypeList();
											}
										}
								 }
							}else{
								saveConfTypeEntity(true);
							}
							
						}else{
							showPopup("Please select atleast one default value");
						}
					}
				}
				break;
			}case FieldEvent.TAB_KEY_PRESSED: {
				if (eventSource instanceof CheckboxField) {
					CheckboxField chkField = (CheckboxField) eventSource;
					//if(chkField.getBaseFieldId().equals(propValueList.get(valueRow).getIsDefaultValueField().getBaseFieldId())){
						saveConfTypeEntity(false);
					//}
				}
				break;
			}case FieldEvent.EDITINPROGRESS: {
				if (eventSource instanceof TextField) {
					TextField listboxField = (TextField) eventSource;
					if(listboxField.getBaseFieldId().equals(PROPNAME_FIELD_ID)){
						if(configTypeList!=null && configTypeList.size()!=0){
							updateConfiguration = true;
						}
					}
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
		

	private void clearPropertyValueFields(boolean isEdit) {
		try {
			
				propNameField.clear();
				propValuePanel.clear();
				
			   if(idVsConfigTypeEntity!=null)
				 idVsConfigTypeEntity.clear();
				
				valueRow=0;
				propValueList.clear();
				
				if(configTypeList!=null)
				   configTypeList.clear();
				
				
				if(!isEdit){
					
					createNewRecord();
			    }
				deregisterPropertyValueEditorHandler();
				if(propertyValueEditorList!=null)
					propertyValueEditorList.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deregisterPropertyValueEditorHandler() {
		for(PropertyValueEditor propertyValueEditor : propertyValueEditorList){
			 propertyValueEditor.deregisterHandler();
		}
		
	}

	@SuppressWarnings("unchecked")
	private void saveConfTypeEntity(final boolean isDisplayInGrid) {
		try {
			Entity confTypeEntity = propValueList.get(valueRow).getPopulatedConfigTypeEntity(propNameField.getValue().toString());
			confTypeEntity.setProperty("configtype", parentConfTypeEnt);
						
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
						
			Map parameterMap = new HashMap();
			parameterMap.put("configTypeEnt", confTypeEntity);
			parameterMap.put("update", false);
			
			//need to change the immediate context id.
			//EntityContext context  = EntityContextGenerator.defineContext(confTypeEntity, 1L);
			//EntityContext context  = getEntityContext(null, parentConfTypeEnt);
			EntityContext context  = new EntityContext();
			parameterMap.put("context", context);
			
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
						
						configTypeList.add(confEnt);
						
						propValueList.get(valueRow).showCheckImage(valueRow);
						insertEmptyRecord();
						if(isDisplayInGrid){
							fireConfigEvent(ConfigEvent.UPDATEDCONFIGENTITYLIST);
							clearPropertyValueFields(false);
						}
					}
				}
			});
		} catch (Exception e) {
			if(e instanceof AppOpsException){
				AppOpsException ex = (AppOpsException) e;
				showPopup(ex.getMsg());
			}
		}
		
	}
	
	private void fireConfigEvent(int eventType){
		try {
			
			EntityList typeList = new EntityList();
			typeList.addAll(configTypeList);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("parentContainer",parentContainer);
			map.put("configTypeList", typeList);
			map.put("parentConfTypeEnt", parentConfTypeEnt);
			
			ConfigEvent configEvent = new ConfigEvent( eventType,map , this);
			configEvent.setEventSource(this);
			AppUtils.EVENT_BUS.fireEvent(configEvent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void deleteConfigType(Entity configTypeEnt ) {
		try {
			confTypeEntToRemove =configTypeEnt;			
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
			propValueList.remove(currentSeletedRow);
			Key key = (Key) deletedConfigEntity.getProperty("id").getValue();
			long id = (Long) key.getKeyValue();
						
			if(confTypeEntToRemove!=null){
				configTypeList.remove(confTypeEntToRemove);
				confTypeEntToRemove = null;
			}
			
			idVsConfigTypeEntity.remove(id);
			//valueRow--;
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
					System.out.println("Property selected------------");
					HashMap<String, EntityList> configTypeHashMap=  (HashMap<String, EntityList>) event.getEventData(); 
					Iterator iterator = configTypeHashMap.entrySet().iterator();
					while(iterator.hasNext()){
						clearPropertyValueFields(true);
						Map.Entry mapEntry = (Map.Entry) iterator.next();
						String keyname= (String) mapEntry.getKey();
						
						EntityList typeList = (EntityList) mapEntry.getValue();
						propNameField.setValue(keyname);
						if(configTypeList == null)
						    configTypeList = new EntityList(); 
						configTypeList.addAll(typeList);
						
						
																	
						for(Entity entity : configTypeList){
							confTypeEnt = entity;
							Key key = (Key) confTypeEnt.getProperty("id").getValue();
							long id = (Long) key.getKeyValue();
							
							
							if(idVsConfigTypeEntity==null)
								idVsConfigTypeEntity = new HashMap<Long, Entity>();
							idVsConfigTypeEntity.put(id, confTypeEnt);
							populateConfFormEditor();
							valueRow++;
						}
						confTypeEnt = null;
						populateConfFormEditor();
						
					}
				}
				break;
			}
			case ConfigEvent.PROPERTYREMOVED: {
				long entityId = (Long) event.getEventData();
				Entity configTypeEnt = idVsConfigTypeEntity.get(entityId);
				PropertyValueEditor valEditor = (PropertyValueEditor)event.getEventSource();
				boolean isSelected  = Boolean.parseBoolean(valEditor.getIsDefaultValueField().getValue().toString());
				
				if(isSelected){
					if(selectedCheckBoxes!=null){
						selectedCheckBoxes.remove(valEditor.getIsDefaultValueField());
						isDefaultSelected = false;
					}
				}
				
				deleteConfigType(configTypeEnt);
				
				break;
			}case ConfigEvent.NEW_COMPONENT_REGISTERED: {
				HashMap<String, Entity> map = (HashMap<String, Entity>) event.getEventData();
				Entity configTypeEntity   = map.get("componentConfigType");
				parentConfTypeEnt = configTypeEntity;
				break;
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
				break;
			}
			case ConfigEvent.DEFAULT_PROP_SELECTED: {
				HashMap<String, Object> map = (HashMap<String, Object>) event.getEventData();
				CheckboxField chkField = (CheckboxField) map.get("checkboxField");
				
				boolean isUpdated = Boolean.valueOf(map.get("updated").toString());
				if(isUpdated){
					updateConfiguration = true;
				}

				if (!isDefaultSelected) {
					isDefaultSelected = true;

					if (selectedCheckBoxes == null)
						selectedCheckBoxes = new ArrayList<CheckboxField>();

					selectedCheckBoxes.add(chkField);

				} else {
					for (int i = 0; i < selectedCheckBoxes.size(); i++) {
						selectedCheckBoxes.get(i).setValue(false);
					}
					isDefaultSelected = true;
					chkField.setValue(true);
					selectedCheckBoxes.add(chkField);
				}

				break;
			}case ConfigEvent.DEFAULT_PROP_DESELECTED: {
				
				HashMap<String, Object> map = (HashMap<String, Object>) event.getEventData();
				CheckboxField chkField = (CheckboxField) map.get("checkboxField");
				
				boolean isUpdated = Boolean.valueOf(map.get("updated").toString());
				if(isUpdated){
					updateConfiguration = true;
				}
				
				selectedCheckBoxes.remove(chkField);
				if (selectedCheckBoxes.isEmpty()) {
					isDefaultSelected = false;
					//showPopup("Please select atleast one default value");
				}
				break;
			}case ConfigEvent.CONFIGTYPE_UPDATED: {
				updateConfiguration = true;
			}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void saveConfigTypeList() {
		try {
			
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
						
			EntityList list = new EntityList();
			
			int size  = 0 ;
						
			PropertyValueEditor valEdit = propValueList.get(propValueList.size()-1);
			if(valEdit!=null){
				if(valEdit.checkIfRecordIsEmpty()){
					size = propValueList.size()-1;
				}else{
					size = propValueList.size();
				}
			}else{
				valEdit = propValueList.get(propValueList.size());
				if(valEdit!=null){
					if(valEdit.checkIfRecordIsEmpty()){
						size = propValueList.size()-1;
					}else{
						size = propValueList.size();
					}
				}
					
			}
			for(int i =0 ;i<size ; i++){
				PropertyValueEditor valEditor = propValueList.get(i);
				if(valEditor!=null){
					Entity confEnt = valEditor.getPopulatedConfigTypeEntity(propNameField.getValue().toString());
					if(confEnt!=null && confEnt.getPropertyByName("keyname")!=null ){
						EntityContext context  = new EntityContext();
						confEnt.setPropertyByName("context", context);
						confEnt.setProperty("configtype", parentConfTypeEnt);
						list.add(confEnt);
					}
				}
				
			}
								
			Map parameterMap = new HashMap();
			parameterMap.put("configurationtypeList", list);
			
			StandardAction action = new StandardAction(EntityList.class, "appdefinition.AppDefinitionService.saveConfigurationTypeList", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

				@Override
				public void onFailure(Throwable caught) {
					
				}

				@Override
				public void onSuccess(Result<EntityList> result) {
					if(result!=null){
						configTypeList = result.getOperationResult();
						showPopup("Configurations updated successfully");
						fireConfigEvent(ConfigEvent.UPDATEDCONFIGENTITYLIST);
						clearPropertyValueFields(false);
					}
				}
			});
		} catch (Exception e) {
			if(e instanceof AppOpsException){
				AppOpsException ex = (AppOpsException) e;
				showPopup(ex.getMsg());
			}
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
	 * Used to show popup at particular position.
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
			popup.setStylePrimaryName(POPUP_CSS);
			popup.add(popupLbl);
			popup.center();
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
				boolean isDefault = Boolean.valueOf(confTypeEnt.getPropertyByName("isdefault").toString());
				if(isDefault){
					isDefaultSelected = true;
					if (selectedCheckBoxes == null)
						selectedCheckBoxes = new ArrayList<CheckboxField>();
					selectedCheckBoxes.add(propValueEditor.getIsDefaultValueField());
				}
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
			propertyValueEditorList.add(propValueEditor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
}
