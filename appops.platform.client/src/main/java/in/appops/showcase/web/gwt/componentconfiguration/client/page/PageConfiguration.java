package in.appops.showcase.web.gwt.componentconfiguration.client.page;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.GroupField;
import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.RadioButtonField;
import in.appops.client.common.config.field.textfield.TextField;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.ConfigInstanceEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.client.common.event.handlers.ConfigInstanceEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.client.EntityContext;
import in.appops.platform.client.EntityContextGenerator;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;
import in.appops.showcase.web.gwt.componentconfiguration.client.library.ComponentInstanceMVPEditor;
import in.appops.showcase.web.gwt.componentconfiguration.client.library.ConfigurationInstanceMVPEditor;
import in.appops.showcase.web.gwt.componentconfiguration.client.library.SnippetPropValueEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PageConfiguration extends Composite implements ConfigEventHandler,FieldEventHandler, ConfigInstanceEventHandler {
	
	public PageConfiguration() {
		initialize();
		createUI();
		initWidget(basePanel);
		
		if(configEventHandler == null)
			AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
		
		if(fieldEventHandler == null)
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}
	
	public void deregisterHandler(){
		fieldEventHandler.removeHandler();
		configEventHandler.removeHandler();
		
		if(instanceMVPEditorsList != null)
			deregisterPreviousInstances();
		AppUtils.EVENT_BUS.addHandler(ConfigInstanceEvent.TYPE, this);
	}

	private void initialize() {
		basePanel = new VerticalPanel();
		addConfigPanel = new VerticalPanel();
		updateConfigurationPanel = new VerticalPanel();
		instanceProvider = new ConfigurationInstanceProvider();
		entityInstanceProvider = new EntityInstanceProvider();
	}
	
	public void createUI() {
		try {
			basePanel.clear();
			
			LabelField titleLabelField = new LabelField();
			titleLabelField.setConfiguration(instanceProvider.getConfigTitleLabelConfig());
			titleLabelField.configure();
			titleLabelField.create();
			
			basePanel.add(titleLabelField);
			
			HorizontalPanel spanSelectionPanel = createSpanSelectionPanel();
			basePanel.add(spanSelectionPanel);
			
			basePanel.add(addConfigPanel);
			basePanel.setStylePrimaryName(PageConfigurationContant.PAGE_CONFIGURATION_BASEPANEL_CSS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private HorizontalPanel createSpanSelectionPanel() {
		try {
			HorizontalPanel spanSelectionPanel = new HorizontalPanel();
			
			LabelField selectSpanLabelField = new LabelField();
			selectSpanLabelField.setConfiguration(instanceProvider.getSelectSpanLabelConfig());
			selectSpanLabelField.configure();
			selectSpanLabelField.create();
			spanSelectionPanel.add(selectSpanLabelField);
			
			spanListbox = new ListBoxField();
			spanListbox.setConfiguration(instanceProvider.getSpanListBoxConfiguration(null));
			spanListbox.configure();
			spanListbox.create();
			spanSelectionPanel.add(spanListbox);
			
			spanSelectionPanel.setCellWidth(selectSpanLabelField, "20%");
			spanSelectionPanel.setCellVerticalAlignment(selectSpanLabelField, HasVerticalAlignment.ALIGN_MIDDLE);
			spanSelectionPanel.setStylePrimaryName(PageConfigurationContant.SPAN_SELECTION_PANEL_CSS);
			return spanSelectionPanel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setAddConfigPanel(Widget widget) {
		try {
			addConfigPanel.clear();
			addConfigPanel.add(widget);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onConfigEvent(ConfigEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case ConfigEvent.POPULATESPANS: {
				if (eventSource instanceof PageCreation) {
					ArrayList<Element> appopsFieldList =  (ArrayList<Element>) event.getEventData();
					populateSpansListBox(appopsFieldList);
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
	
	public void populateSpansListBox(ArrayList<Element> spansList){
		ArrayList<String> configList = new ArrayList<String>();
		for(Element element : spansList){
			String dataConfig = element.getAttribute("data-config");
			configList.add(dataConfig);
		}
		spanListbox.setConfiguration(instanceProvider.getSpanListBoxConfiguration(configList));
		spanListbox.configure();
		spanListbox.create();
		containerCompoInstMap = new HashMap<String, HashMap<String,Object>>();
	}
	
	public void createPropertyConfigUI() {
		configInstanceEntityMap = new HashMap<String, Object>();
		addConfigPanel.clear();
		
		titlePanel = createTitlePanel();
		addConfigPanel.add(titlePanel);
		
		eventNamePanel = createEventNamePanel();
		addConfigPanel.add(eventNamePanel);
		
		isTransformWidgetPanel = createIsTransformWidgetPanel();
		addConfigPanel.add(isTransformWidgetPanel);
		
		transformTypePanel = createtransformTypePanel();
		addConfigPanel.add(transformTypePanel);
		
		transformToPanel = createtransformToPanel();
		addConfigPanel.add(transformToPanel);
		
		transformInstancePanel = createtransformInstancePanel();
		addConfigPanel.add(transformInstancePanel);
		
		isUpdateConfigPanel = createIsUpdateConfigPanel();
		addConfigPanel.add(isUpdateConfigPanel);
		
		updateConfigurationBasePanel = createUpdateConfigPanel();
		addConfigPanel.add(updateConfigurationBasePanel);
		
		buttonPanel = createButtonPanel();
		addConfigPanel.add(buttonPanel);

		hidePanels();
		addConfigPanel.setStylePrimaryName(PageConfigurationContant.ADD_PAGE_CONFIGURATION_BASEPANEL_CSS);
	}
	
	private HorizontalPanel createTitlePanel() {
		HorizontalPanel titlePanel = new HorizontalPanel();
		
		LabelField propConfigTitleLabel = new LabelField();
		propConfigTitleLabel.setConfiguration(instanceProvider.getPropConfigTitleLabelConfiguration());
		propConfigTitleLabel.configure();
		propConfigTitleLabel.create();
		titlePanel.add(propConfigTitleLabel);
		
		plusIconField = new ImageField();
		plusIconField.setConfiguration(instanceProvider.getPlusIconFieldConfiguration());
		plusIconField.configure();
		plusIconField.create();
		titlePanel.add(plusIconField);
		
		titlePanel.setCellWidth(propConfigTitleLabel, "140px");
		titlePanel.setCellVerticalAlignment(propConfigTitleLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		titlePanel.setCellHorizontalAlignment(plusIconField, HasHorizontalAlignment.ALIGN_RIGHT);
		titlePanel.setWidth("100%");
		return titlePanel;
	}

	public HorizontalPanel createButtonPanel() {
		HorizontalPanel buttonPanel = new HorizontalPanel();
		
		configureButton = new ButtonField();
		configureButton.setConfiguration(instanceProvider.getConfigureButtonConfiguration());
		configureButton.configure();
		configureButton.create();
		buttonPanel.add(configureButton);
		
		buttonPanel.setWidth("100%");
		buttonPanel.setCellHorizontalAlignment(configureButton, HasHorizontalAlignment.ALIGN_RIGHT);
		return buttonPanel;
	}
	
	private HorizontalPanel createtransformToPanel() {
		HorizontalPanel transformToPanel = new HorizontalPanel();
		
		LabelField transformToLabel = new LabelField();
		transformToLabel.setConfiguration(instanceProvider.getTransformToLabelConfiguration());
		transformToLabel.configure();
		transformToLabel.create();
		transformToPanel.add(transformToLabel);
		
		transformToListbox = new ListBoxField();
		transformToListbox.setConfiguration(instanceProvider.getTransformToListBoxConfiguration(null,false));
		transformToListbox.configure();
		transformToListbox.create();
		transformToPanel.add(transformToListbox);
		
		transformToPanel.setStylePrimaryName(PageConfigurationContant.TRANSFORM_TO_PANEL_CSS);
		transformToPanel.setCellWidth(transformToLabel, "172px");
		transformToPanel.setCellVerticalAlignment(transformToLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		return transformToPanel;
	}

	private HorizontalPanel createtransformTypePanel() {
		HorizontalPanel transformTypePanel = new HorizontalPanel();
		
		LabelField transformTypeLabel = new LabelField();
		transformTypeLabel.setConfiguration(instanceProvider.getTransformTypeLabelConfiguration());
		transformTypeLabel.configure();
		transformTypeLabel.create();
		transformTypePanel.add(transformTypeLabel);
		
		transformTypeListbox = new ListBoxField();
		transformTypeListbox.setConfiguration(instanceProvider.getTransformTypeListBoxConfiguration());
		transformTypeListbox.configure();
		transformTypeListbox.create();
		transformTypePanel.add(transformTypeListbox);
		
		transformTypePanel.setStylePrimaryName(PageConfigurationContant.TRANSFORM_TYPE_PANEL_CSS);
		transformTypePanel.setCellWidth(transformTypeLabel, "172px");
		transformTypePanel.setCellVerticalAlignment(transformTypeLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		return transformTypePanel;
	}

	private HorizontalPanel createtransformInstancePanel() {
		HorizontalPanel transformInstancePanel = new HorizontalPanel();
		
		LabelField transformInstanceLabel = new LabelField();
		transformInstanceLabel.setConfiguration(instanceProvider.getTransformInstanceLabelConfiguration());
		transformInstanceLabel.configure();
		transformInstanceLabel.create();
		transformInstancePanel.add(transformInstanceLabel);
		
		transformInstanceTextField = new TextField();
		transformInstanceTextField.setConfiguration(instanceProvider.getTextFieldConfiguration(PageConfigurationContant.TRANSFORM_INSTANCE_TEXTFIELD_ID));
		transformInstanceTextField.configure();
		transformInstanceTextField.create();
		transformInstancePanel.add(transformInstanceTextField);
		
		transformInstancePanel.setStylePrimaryName(PageConfigurationContant.TRANSFORM_INSTANCE_PANEL_CSS);
		transformInstancePanel.setCellWidth(transformInstanceLabel, "172px");
		transformInstancePanel.setCellVerticalAlignment(transformInstanceLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		return transformInstancePanel;
	}

	private HorizontalPanel createIsTransformWidgetPanel() {
		HorizontalPanel isTransformWidgetPanel = new HorizontalPanel();
		
		LabelField isTransformWidgetLabel = new LabelField();
		isTransformWidgetLabel.setConfiguration(instanceProvider.getIsTransformWidgetLabelConfiguration());
		isTransformWidgetLabel.configure();
		isTransformWidgetLabel.create();
		isTransformWidgetPanel.add(isTransformWidgetLabel);
		
		isTransformWidgetGrField = instanceProvider.createGroupField(PageConfigurationContant.IS_TRANSFORM_WIDGET_GROUP_ID);
		isTransformWidgetPanel.add(isTransformWidgetGrField);
		
		isTransformWidgetPanel.setStylePrimaryName(PageConfigurationContant.IS_TRANSFORM_WIDGET_PANEL_CSS);
		isTransformWidgetPanel.setCellWidth(isTransformWidgetLabel, "165px");
		isTransformWidgetPanel.setCellVerticalAlignment(isTransformWidgetLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		return isTransformWidgetPanel;
	}

	private HorizontalPanel createUpdateConfigPanel() {
		SnippetPropValueEditorList = new ArrayList<SnippetPropValueEditor>();
		HorizontalPanel updateConfigurationBasePanel = new HorizontalPanel();
		VerticalPanel updateConfigPanelWithButton = new VerticalPanel();
		
		LabelField updateConfigLabel = new LabelField();
		updateConfigLabel.setConfiguration(instanceProvider.getUpdateConfigLabelConfiguration());
		updateConfigLabel.configure();
		updateConfigLabel.create();
		updateConfigurationBasePanel.add(updateConfigLabel);
		
		updateConfigurationPanel.clear();
		updateConfigPanelWithButton.add(updateConfigurationPanel);
		updateConfigurationBasePanel.add(updateConfigPanelWithButton);
		
		updateConfigPanelWithButton.setWidth("100%");
		
		updateConfigurationPanel.setStylePrimaryName(PageConfigurationContant.UPDATE_CONFIGURATION_PANEL_CSS);
		updateConfigurationBasePanel.setStylePrimaryName(PageConfigurationContant.UPDATE_CONFIGURATION_BASEPANEL_CSS);
		updateConfigurationBasePanel.setCellWidth(updateConfigLabel, "172px");
		return updateConfigurationBasePanel;
	}

	private HorizontalPanel createIsUpdateConfigPanel() {
		HorizontalPanel isUpdateConfigPanel = new HorizontalPanel();
		
		LabelField isUpdateConfigLabel = new LabelField();
		isUpdateConfigLabel.setConfiguration(instanceProvider.getIsUpdateConfigLabelConfiguration());
		isUpdateConfigLabel.configure();
		isUpdateConfigLabel.create();
		isUpdateConfigPanel.add(isUpdateConfigLabel);
		
		isUpdateConfigGrField = instanceProvider.createGroupField(PageConfigurationContant.IS_UPDATE_CONFIG_GROUP_ID);
		isUpdateConfigPanel.add(isUpdateConfigGrField);
		
		isUpdateConfigPanel.setStylePrimaryName(PageConfigurationContant.IS_UPDATE_CONFIG_PANEL_CSS);
		isUpdateConfigPanel.setCellWidth(isUpdateConfigLabel, "165px");
		isUpdateConfigPanel.setCellVerticalAlignment(isUpdateConfigLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		return isUpdateConfigPanel;
	}

	private HorizontalPanel createEventNamePanel() {
		HorizontalPanel eventNamePanel = new HorizontalPanel();
		
		LabelField eventNameLabel = new LabelField();
		eventNameLabel.setConfiguration(instanceProvider.getEventNameLabelConfiguration());
		eventNameLabel.configure();
		eventNameLabel.create();
		eventNamePanel.add(eventNameLabel);
		
		eventNametextField = new TextField();
		eventNametextField.setConfiguration(instanceProvider.getTextFieldConfiguration(PageConfigurationContant.EVENT_NAME_TEXTFIELD_ID));
		eventNametextField.configure();
		eventNametextField.create();
		eventNamePanel.add(eventNametextField);
		
		eventNamePanel.setStylePrimaryName(PageConfigurationContant.EVENT_NAME_PANEL_CSS);
		eventNamePanel.setCellWidth(eventNameLabel, "172px");
		eventNamePanel.setCellVerticalAlignment(eventNameLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		return eventNamePanel;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			int eventType = event.getEventType();
			switch (eventType) {
			case FieldEvent.TAB_KEY_PRESSED: {
				if(event.getEventSource() instanceof TextField) {
					TextField source = (TextField) event.getEventSource();
					if(source.equals(eventNametextField)) {
						if(!eventNametextField.getFieldValue().trim().equals("")) {
							Entity entity = null;
							boolean isUpdate;
							if(configInstanceEntityMap.containsKey(PageConfigurationContant.EVENTNAME)) {
								entity = (Entity) configInstanceEntityMap.get(PageConfigurationContant.EVENTNAME);
								entity.setPropertyByName("instancename", eventNametextField.getFieldValue());
								entity.setPropertyByName("configkeyname", eventNametextField.getFieldValue());
								isUpdate = true;
								updateTempHashMap(PageConfigurationContant.EVENTNAME, entity);
							} else {
								isUpdate = false;
								entity = entityInstanceProvider.getConfiginstanceEntity(eventNametextField.getFieldValue(), eventNametextField.getFieldValue(), null, interestedEventEntity, 4L);
							}
							
							EntityContext context = getEntityContext(null, interestedEventEntity);
							
							Entity containerEnt = (Entity) interestedEventEntity.getProperty("configinstance");
							EntityContext context1 = getEntityContext(context, containerEnt);

							Entity pageInstEnt = (Entity) containerEnt.getProperty("configinstance");
							EntityContext context2 = getEntityContext(context1, pageInstEnt);

							EntityContext context3 = getEntityContext(context2, pageEntity);
							
							EntityContext context4 = getEntityContext(context3, AppEnviornment.CURRENTAPP);
							EntityContext context5 = getEntityContext(context4, AppEnviornment.CURRENTSERVICE);
							
							saveConfigInstance(entity, true, false, isUpdate, context);
						}
					} else if(source.equals(transformInstanceTextField)) {
						String value = (String) event.getEventData();
						if(!value.trim().equals("")) {
							Entity entity = null;
							boolean isUpdate;
							if(configInstanceEntityMap.containsKey(PageConfigurationContant.TRANSFORM_INSTANCE)) {
								entity = (Entity) configInstanceEntityMap.get(PageConfigurationContant.TRANSFORM_INSTANCE);
								entity.setPropertyByName("instancevalue", value);
								isUpdate = true;
								updateTempHashMap(PageConfigurationContant.TRANSFORM_INSTANCE, entity);
							} else {
								isUpdate = false;
								entity = entityInstanceProvider.getConfiginstanceEntity(PageConfigurationContant.TRANSFORM_INSTANCE, PageConfigurationContant.TRANSFORM_INSTANCE, value, parentEventEntity, 6L);
							}
							
							EntityContext context = getEntityContextForEventChild(null);
							saveConfigInstance(entity, false, false, isUpdate, context);
						}
					}
				} else if(event.getEventSource() instanceof GroupField) {
					GroupField source = (GroupField) event.getEventSource();
					String fieldId = source.getBaseFieldId();
					if(fieldId.equals(PageConfigurationContant.IS_TRANSFORM_WIDGET_GROUP_ID)) {
						
					} else if(fieldId.equals(PageConfigurationContant.IS_UPDATE_CONFIG_GROUP_ID)) {
						
					}
				}
				break;
			}
			case FieldEvent.CLICKED: {
				if(event.getEventSource() instanceof ButtonField) {
					ButtonField source = (ButtonField) event.getEventSource();
					if(source.equals(configureButton)) {
						String value = (String) transformToListbox.getValue();
						String defaultValue = transformToListbox.getSuggestionValueForListBox();
						if(!transformInstanceTextField.getFieldValue().trim().equals("") && !value.equals(defaultValue)) {
							boolean isUpdate = checkIsUpdate();
							/*if(isUpdate) {
								updateTransformWidgetInstance();
							} else {*/
								saveTransformWidgetInstance(isUpdate);
							//}
						}
					}
				} else if(event.getEventSource() instanceof ImageField) {
					ImageField source = (ImageField) event.getEventSource();
					if(source.equals(plusIconField)) {
						createPropertyConfigUI();
					} else if(source.equals(minusIconField)) {
						Widget panel = source.getParent();
						panel.removeFromParent();
					}
				}
				break;
			}
			case FieldEvent.VALUE_SELECTED: {
				if(event.getEventSource() instanceof GroupField) {
					GroupField source = (GroupField) event.getEventSource();
					ArrayList<Widget> selectedItem = (ArrayList<Widget>) source.getValue();
					RadioButtonField radioButton = (RadioButtonField) selectedItem.get(0);
					String text = radioButton.getDisplayText();
					if(source.equals(isTransformWidgetGrField)) {
						
						Entity entity = null;
						boolean isUpdate;
						if(configInstanceEntityMap.containsKey(PageConfigurationContant.IS_TRANSFORM_WIDGET)) {
							entity = (Entity) configInstanceEntityMap.get(PageConfigurationContant.IS_TRANSFORM_WIDGET);
							entity.setPropertyByName("instancevalue", text);
							isUpdate = true;
							updateTempHashMap(PageConfigurationContant.IS_TRANSFORM_WIDGET, entity);
						} else {
							isUpdate = false;
							entity = entityInstanceProvider.getConfiginstanceEntity(PageConfigurationContant.IS_TRANSFORM_WIDGET, PageConfigurationContant.IS_TRANSFORM_WIDGET, text, parentEventEntity, 9L);
						}
						
						EntityContext context = getEntityContextForEventChild(null);
						saveConfigInstance(entity, false, false, isUpdate, context);
						
						if(text.equals("true")) {
							showTranformWidgetUI();
						} else {
							hideTranformWidgetUI();
						}
					} else if(source.equals(isUpdateConfigGrField)) {
						Entity entity = null;
						boolean isUpdate;
						if(configInstanceEntityMap.containsKey(PageConfigurationContant.IS_UPDATE_CONFIGURATION)) {
							entity = (Entity) configInstanceEntityMap.get(PageConfigurationContant.IS_UPDATE_CONFIGURATION);
							entity.setPropertyByName("instancevalue", text);
							isUpdate = true;
							updateTempHashMap(PageConfigurationContant.IS_UPDATE_CONFIGURATION, entity);
						} else {
							isUpdate = false;
							entity = entityInstanceProvider.getConfiginstanceEntity(PageConfigurationContant.IS_UPDATE_CONFIGURATION, PageConfigurationContant.IS_UPDATE_CONFIGURATION, text, parentEventEntity, 10L);
						}
						EntityContext context = getEntityContextForEventChild(null);
						saveConfigInstance(entity, false, false, isUpdate, context);
					}
				}
				break;
			}
			case FieldEvent.VALUECHANGED: {
				if(event.getEventSource() instanceof ListBoxField) {
					ListBoxField source = (ListBoxField) event.getEventSource();
					if(source.equals(transformTypeListbox)) {
						String value = (String) source.getValue();
						fetchTransformToList(value);
						
						String instanceValue = null;
						if(value.equals("Component")) {
							instanceValue = String.valueOf(2);
						} else if(value.equals("HtmlSnippet")) {
							instanceValue = String.valueOf(1);
						}
						
						Entity entity = null;
						boolean isUpdate;
						if(configInstanceEntityMap.containsKey(PageConfigurationContant.TRANSFORM_TYPE)) {
							entity = (Entity) configInstanceEntityMap.get(PageConfigurationContant.TRANSFORM_TYPE);
							entity.setPropertyByName("instancevalue", instanceValue);
							isUpdate = true;
							updateTempHashMap(PageConfigurationContant.TRANSFORM_TYPE, entity);
						} else {
							isUpdate = false;
							entity = entityInstanceProvider.getConfiginstanceEntity(PageConfigurationContant.TRANSFORM_TYPE, PageConfigurationContant.TRANSFORM_TYPE, instanceValue, parentEventEntity, 8L);
						}
						EntityContext context = getEntityContextForEventChild(null);
						saveConfigInstance(entity, false, false, isUpdate, context);
					} else if(source.equals(transformToListbox)) {
						String value = (String) source.getValue();
						Entity entity = null;
						boolean isUpdate;
						if(configInstanceEntityMap.containsKey(PageConfigurationContant.TRANSFORM_TO)) {
							entity = (Entity) configInstanceEntityMap.get(PageConfigurationContant.TRANSFORM_TO);
							entity.setPropertyByName("instancevalue", value);
							isUpdate = true;
							updateTempHashMap(PageConfigurationContant.TRANSFORM_TO, entity);
						} else {
							isUpdate = false;
							entity = entityInstanceProvider.getConfiginstanceEntity(PageConfigurationContant.TRANSFORM_TO, PageConfigurationContant.TRANSFORM_TO, value, parentEventEntity, 7L);
						}
						EntityContext context = getEntityContextForEventChild(null);
						saveConfigInstance(entity, false, false, isUpdate, context);
						transWgtCompDefEnt = source.getAssociatedEntity(value);
						//transformMap.put(transformInstanceTextField.getFieldValue(), entity);
					} else if(source.equals(spanListbox)) {
						containerName = (String) source.getValue();
						if(containerName.equals(source.getSuggestionValueForListBox())) {
							addConfigPanel.clear();
						} else {
							if(!containerCompoInstMap.containsKey(containerName)) {
								Entity entity = createComponentInstance(containerName);
								saveComponentInstance(entity);
							} else {
								initializeEditor(containerCompoInstMap.get(containerName));
								populateEventNames(containerName);
							}
						}
					} else if(source.equals(eventNameListbox)) {
						String value = (String) source.getValue();
						if(value.equals(source.getSuggestionValueForListBox())) {
							eventNamePanel.setVisible(false);
							hidePanels();
						} else {
							fetchAllConfigInst(value);
							eventNamePanel.setVisible(true);
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
	
	private void updateTransformWidgetInstance() {
		Entity compInstEntity = getCompInstEntity();
	}

	private boolean checkIsUpdate() {
		if(modelViewConfigInstMap != null) {
			Entity componentInst = (Entity) modelViewConfigInstMap.get("componentInstEnt");
			Entity compoDefEnt = (Entity) componentInst.getProperty("componentdefinition");
			
			transWgtCompDefEnt = transformToListbox.getAssociatedEntity((String)transformToListbox.getValue());
			
			Long transWgtCompDefId = ((Key<Long>) transWgtCompDefEnt.getPropertyByName("id")).getKeyValue();
			Long compoDefEntId = ((Key<Long>) compoDefEnt.getPropertyByName("id")).getKeyValue();
			
			if(!transWgtCompDefId.toString().equals(compoDefEntId.toString())) {
				isComponentDefUpdate = true;
			}
			return true;
		} else {
			return false;
		}
	}

	private void updateTempHashMap(String eventname, Entity entity) {
		if(tempHashMap != null) {
			if(eventNameList.contains(tempHashMap)) {
				eventNameList.remove(tempHashMap);
				tempHashMap.put(eventname, entity);
				eventNameList.add(tempHashMap);
				map.put(interestedEvtConfInstId.toString(), eventNameList);
				containerCompoInstMap.put(containerName + PageConfigurationContant.INTERESTEDEVENT_CHILDCONFIGINST_MAP, map);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void setContainerInstInMap() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		eventNameList = new ArrayList<HashMap<String,Object>>();
		interestedEvtConfInstId = ((Key<Long>) interestedEventEntity.getPropertyByName("id")).getKeyValue();
		map.put(interestedEvtConfInstId.toString(), eventNameList);
		containerCompoInstMap.put(containerName + PageConfigurationContant.INTERESTEDEVENT_CHILDCONFIGINST_MAP, map);
	}
	
	public void addToEventNameList() {
		if(eventNameList.contains(configInstanceEntityMap)) {
			eventNameList.remove(configInstanceEntityMap);
		}
		eventNameList.add(configInstanceEntityMap);
	}
	
	@SuppressWarnings("unchecked")
	private void populateEventNames(String value) {
		if(containerCompoInstMap != null && !containerCompoInstMap.isEmpty()) {
			map = containerCompoInstMap.get(value + PageConfigurationContant.INTERESTEDEVENT_CHILDCONFIGINST_MAP);
			interestedEvtConfInstId = ((Key<Long>) interestedEventEntity.getPropertyByName("id")).getKeyValue();
			eventNameList = (ArrayList<HashMap<String, Object>>) map.get(interestedEvtConfInstId.toString());
			
			eventsList = new ArrayList<String>();
			Iterator<HashMap<String, Object>> iteratorForListbox = eventNameList.iterator();
			while(iteratorForListbox.hasNext()) {
				HashMap<String, Object> hashMap = iteratorForListbox.next();
				Entity entity = (Entity) hashMap.get(PageConfigurationContant.EVENTNAME);
				String instancename = entity.getPropertyByName("instancename").toString();
				eventsList.add(instancename);
			}
			if(!eventsList.isEmpty()) {
				eventNameListbox.setConfiguration(instanceProvider.getEventNameListBoxConfiguration(eventsList));
				eventNameListbox.configure();
				eventNameListbox.create();
				titlePanel.insert(eventNameListbox, 1);
			}
		}
	}
	
	private void fetchAllConfigInst(String value) {

		if(containerCompoInstMap != null && !containerCompoInstMap.isEmpty()) {
			if(eventNameList != null && !eventNameList.isEmpty()) {
				Iterator<HashMap<String, Object>> iterator = eventNameList.iterator();
				configInstanceEntityMap.clear();
				while(iterator.hasNext()) {
					tempHashMap = iterator.next();
					if(tempHashMap.containsKey(PageConfigurationContant.EVENTNAME)) {
						Entity eventNameEnt = (Entity) tempHashMap.get(PageConfigurationContant.EVENTNAME);
						String eventName = eventNameEnt.getPropertyByName("instancename").toString();
						if(eventName.equals(value)) {
							Set<String> keySet = tempHashMap.keySet();
							Iterator<String> iteratorSet = keySet.iterator();
							while(iteratorSet.hasNext()) {
								String key = iteratorSet.next();
								if(key.equals(PageConfigurationContant.UPDATE_CONFIGURATION)) {
									EntityList childList = (EntityList) tempHashMap.get(key);
									createListOfSnippetPropValueEditor(childList);
								} else {
									Entity entity = (Entity) tempHashMap.get(key);
									String instancename = entity.getPropertyByName("instancename").toString();
									String instancevalue = null;
									if(key.equals(PageConfigurationContant.EVENTNAME)) {
										instancevalue = instancename;
										instancename = key;
									} else {
										instancevalue = entity.getPropertyByName("instancevalue");
									}
									configInstanceEntityMap.put(instancename, entity);
									populateDataInFields(instancename,instancevalue);
								}
							}
						}
					}
				}
			}
		}
	}

	private void populateDataInFields(String instancename, String instancevalue) {
		if(instancename.equals(PageConfigurationContant.EVENTNAME)) {
			eventNametextField.setValue(instancevalue);
		} else if(instancename.equals(PageConfigurationContant.IS_TRANSFORM_WIDGET)) {
			showPanels();
			isTransformWidgetGrField.setFieldValue(instancevalue); 
			if(instancevalue.equals("true")) {
				showTranformWidgetUI();
			}
		} else if(instancename.equals(PageConfigurationContant.IS_UPDATE_CONFIGURATION)) {
			showPanels();
			isUpdateConfigGrField.setFieldValue(instancevalue);
			if(instancevalue.equals("true")) {
				showUpdateConfigUI();
			}
		} else if(instancename.equals(PageConfigurationContant.TRANSFORM_TYPE)) {
			if(instancevalue.equals("1")) {
				instancevalue = PageConfigurationContant.SNIPPET;
			} else if(instancevalue.equals("2")) {
				instancevalue = PageConfigurationContant.COMPONENT;
			}
			transformTypeListbox.setValue(instancevalue);
			fetchTransformToList(instancevalue);
		} else if(instancename.equals(PageConfigurationContant.TRANSFORM_TO)) {
			transformToListbox.setValue(instancevalue);
			transformToListbox.setSelectDefaultValue(instancevalue);
		}  else if(instancename.equals(PageConfigurationContant.TRANSFORM_INSTANCE)) {
			transformInstanceTextField.setValue(instancevalue);
			fetchInstancesForTransformInst(instancevalue);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void fetchInstancesForTransformInst(String instancevalue) {
		try{
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Map parameterMap = new HashMap();
			
			Query query = new Query();
			query.setQueryName("getComponentInstByName");
			HashMap<String, Object> queryParamMap = new HashMap<String, Object>();
			queryParamMap.put("instancename", instancevalue);
			query.setQueryParameterMap(queryParamMap);
			
			parameterMap.put("query", query);
			
			String operationName = null;
			if(transformTypeListbox.getValue().toString().equals(PageConfigurationContant.SNIPPET)) {
				operationName = "appdefinition.AppDefinitionService.getModelViewAndChildConfigInstForSnippet";
			} else {
				operationName = "appdefinition.AppDefinitionService.getModelViewAndChildConfigInstForComponent";
			}
			
			StandardAction action = new StandardAction(HashMap.class, operationName, parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<HashMap<String, Object>>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<HashMap<String, Object>> result) {
					if(result!=null){
						modelViewConfigInstMap = result.getOperationResult();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createListOfSnippetPropValueEditor(EntityList childList) {
		try {
			if(childList != null && !childList.isEmpty()) {
				Iterator<Entity> iterator = childList.iterator();
				while(iterator.hasNext()) {
					Entity configInstEntity = iterator.next();
					SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor("queryMode");
					snipPropValEditor.setDeletable(false);
					snipPropValEditor.setInstanceMode(true);
					snipPropValEditor.setParentConfInstanceEntity(updateConfigEntity);
					snipPropValEditor.setConfInstanceParamValEnt(configInstEntity);
					snipPropValEditor.setConfigTypeListboxVisible(true);
					snipPropValEditor.createUi();
					updateConfigurationPanel.add(snipPropValEditor);
					SnippetPropValueEditorList.add(snipPropValEditor);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private void saveComponentInstance(final Entity entity) {

		try{
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Map parameterMap = new HashMap();
			parameterMap.put("componentInstEnt", entity);
			
			EntityContext context = getEntityContext(null, pageComponentInstEntity);
			EntityContext context1 = getEntityContext(context, pageEntity);
			EntityContext context2 = getEntityContext(context1, AppEnviornment.CURRENTAPP);
			EntityContext context3 = getEntityContext(context2, AppEnviornment.CURRENTSERVICE);

			parameterMap.put("entityContext", context);
			
			parameterMap.put("isUpdate", false);
			
			StandardAction action = new StandardAction(HashMap.class, "appdefinition.AppDefinitionService.saveComponentInstance", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<HashMap<String, Object>>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<HashMap<String, Object>> result) {
					if(result!=null){
						HashMap<String, Object> map = result.getOperationResult();
						String instanceName = entity.getPropertyByName("instancename");
						containerCompoInstMap.put(instanceName, map);
						initializeEditor(map);
						setContainerInstInMap();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	protected void initializeEditor(Map<String, Object> map) {
		ArrayList<Entity> childList = (ArrayList<Entity>) map.get("childConfigInstanceList");
		Iterator<Entity> iterator = childList.iterator();
		if(iterator.hasNext()) {
			Entity entity = iterator.next();
			String name = entity.getPropertyByName("instancename").toString();
			if(name.equals("interestedEvents")) {
				interestedEventEntity = entity;
			}
		}
		createPropertyConfigUI();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void saveConfigInstance(final Entity configinstanceEntity, final boolean isParentInstance, final boolean isUpdateConfigEntSaved, boolean isUpdate, EntityContext context) {

		try{
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Map parameterMap = new HashMap();
			parameterMap.put("confInstEnt", configinstanceEntity);
			
			parameterMap.put("isUpdate", isUpdate);
			parameterMap.put("entityContext", context);
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveConfigurationInstance", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<Entity> result) {
					if(result!=null){
						Entity ent = result.getOperationResult();
						if(ent != null) {
							String name = configinstanceEntity.getPropertyByName("instancename").toString();
							
							if(parentEventEntity != null) {
								String eventname = parentEventEntity.getPropertyByName("instancename").toString();
								updateEntityListBox(eventname, name);
							}
							
							if(isParentInstance) {
								parentEventEntity = ent;
								name = PageConfigurationContant.EVENTNAME;
								showPanels();
							}
							configInstanceEntityMap.put(name, ent);
							if(isUpdateConfigEntSaved) {
								updateConfigEntity = ent;
								showUpdateConfigUI();
								createNewSnippetPropValueEditor();
							}
							if(name.equals(PageConfigurationContant.IS_UPDATE_CONFIGURATION)) {
								String instancevalue = configinstanceEntity.getPropertyByName("instancevalue").toString();
								checkUpdateValue(instancevalue);
							}
							addToEventNameList();
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void updateEntityListBox(String prevEventname, String newEventname) {
		if(eventsList != null && !eventsList.isEmpty()) {
			if(eventsList.contains(prevEventname)) {
				eventsList.remove(prevEventname);
				eventsList.add(newEventname);
				eventNameListbox.setConfiguration(instanceProvider.getEventNameListBoxConfiguration(eventsList));
				eventNameListbox.configure();
				eventNameListbox.create();
				eventNameListbox.setValue(newEventname);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void fetchTransformToList(String value) {
		
		HashMap map = new HashMap();
		if(value.equals("Component")) {
			byte isMvp = 1;
			map.put("isMvp", isMvp);
			map.put("typeId", 159L);
			transformToListbox.setConfiguration(instanceProvider.getTransformToListBoxConfiguration(map,false));
		} else if(value.equals("HtmlSnippet")) {
			map.put("typeId", 159L);
			transformToListbox.setConfiguration(instanceProvider.getTransformToListBoxConfiguration(map,true));
		}
		
		transformToListbox.configure();
		transformToListbox.create();
	}

	public Entity createComponentInstance(String value) {
		try{
			Entity configInstEntity = new Entity();
			configInstEntity.setType(new MetaType("Componentinstance"));
			configInstEntity.setPropertyByName("instancename", value);
			configInstEntity.setProperty("componentdefinition", entityInstanceProvider.getCompoDefEnt());
			configInstEntity.setProperty("componentinstance", this.pageComponentInstEntity);
			return configInstEntity;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setPageComponentInstEntity(Entity entity) {
		this.pageComponentInstEntity = entity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private void saveTransformWidgetInstance(final boolean isUpdate) {
		try{
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Map parameterMap = new HashMap();
			Entity compInstEntity = null;
			String operationName = null;
			
			if(isUpdate) {
				compInstEntity = getCompInstEntity();
				
				Entity configInstEnt = getConfigInstEnt();
				HashMap<String, Object> instanceMap = new HashMap<String, Object>();
				instanceMap.put("ComponentInstEnt", compInstEntity);
				instanceMap.put("ConfigInstEnt", configInstEnt);
				
				parameterMap.put("instanceMap", instanceMap);
				
				operationName = "appdefinition.AppDefinitionService.updateModelViewComponentInst";
				
			} else {
				compInstEntity = new Entity();
				compInstEntity.setType(new MetaType("Componentinstance"));
				compInstEntity.setPropertyByName("instancename", transformInstanceTextField.getFieldValue());
				compInstEntity.setProperty("componentdefinition", transWgtCompDefEnt);
				compInstEntity.setProperty("componentinstance", this.pageComponentInstEntity);
				
				parameterMap.put("componentInstEnt", compInstEntity);
				parameterMap.put("isUpdate", isUpdate);
				
				Key<Long> compKey = pageComponentInstEntity.getPropertyByName("id");
				Long compId = compKey.getKeyValue();
				EntityContext compContext  = EntityContextGenerator.defineContext(null, compId);

				Key<Long> pageEntKey = pageEntity.getPropertyByName("id");
				Long pageEntId = pageEntKey.getKeyValue();
				EntityContext pageEntContext  = compContext.defineContext(pageEntId);

				Key<Long> appKey = AppEnviornment.CURRENTAPP.getPropertyByName("id");
				Long appId = appKey.getKeyValue();
				EntityContext appContext  = pageEntContext.defineContext(appId);

				Key<Long> serviceKey = AppEnviornment.CURRENTSERVICE.getPropertyByName("id");
				Long serviceId = serviceKey.getKeyValue();
				EntityContext context  = appContext.defineContext(serviceId);
				
				parameterMap.put("entityContext", compContext);
				
				operationName = "appdefinition.AppDefinitionService.saveComponentInstance";
			}

			StandardAction action = new StandardAction(Entity.class, operationName, parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<HashMap<String, Object>>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<HashMap<String, Object>> result) {
					if(result!=null){
						HashMap<String, Object> resMap = result.getOperationResult();
						
						if(resMap != null && !resMap.isEmpty()) {
							Entity configInst = (Entity) resMap.get("configInstance");
							Entity viewInstanceEnt = null;
							Entity modelInstanceEnt = null;
							
							ArrayList<Entity> childList = (ArrayList<Entity>) resMap.get("childConfigInstanceList");
							Iterator<Entity> iterator = childList.iterator();
							while(iterator.hasNext()) {
								Entity entity = iterator.next();
								String name = entity.getPropertyByName("instancename").toString();
								if(name.equals("view")) {
									viewInstanceEnt = entity;
								} else {
									modelInstanceEnt = entity;
								}
							}
							
							if(instanceMVPEditorsList ==null)
								instanceMVPEditorsList = new ArrayList<Widget>();
							
							if(transformTypeListbox.getValue().toString().equals(PageConfigurationContant.SNIPPET)){
								ConfigurationInstanceMVPEditor instanceMVPEditor = new ConfigurationInstanceMVPEditor();
								instanceMVPEditor.setPageEntity(pageEntity);
								instanceMVPEditor.setConfigInstEnt(configInst);
								instanceMVPEditor.setViewInstanceEnt(viewInstanceEnt);
								instanceMVPEditor.setModelInstanceEnt(modelInstanceEnt);
								
								if(isUpdate) {
									if(!isComponentDefUpdate) {
										instanceMVPEditor.setModelViewChildConfigInstMap(modelViewConfigInstMap);
									}
								}
								
								instanceMVPEditor.createUi();
								deregisterPreviousInstances();
								instanceMVPEditorsList.add(instanceMVPEditor);
								addConfigPanel.add(instanceMVPEditor);
							}
							else if((transformTypeListbox.getValue().toString().equals(PageConfigurationContant.COMPONENT))){
								ComponentInstanceMVPEditor instanceMVPEditor = new ComponentInstanceMVPEditor();
								instanceMVPEditor.setPageEntity(pageEntity);
								instanceMVPEditor.setConfigInstEnt(configInst);
								instanceMVPEditor.setViewInstanceEnt(viewInstanceEnt);
								instanceMVPEditor.setModelInstanceEnt(modelInstanceEnt);
								instanceMVPEditor.setPageCompInstEntity(pageComponentInstEntity);
								
								if(isUpdate) {
									if(!isComponentDefUpdate) {
										instanceMVPEditor.setModelViewChildConfigInstMap(modelViewConfigInstMap);
									}
								}
								
								instanceMVPEditor.createUi();
								deregisterPreviousInstances();
								instanceMVPEditorsList.add(instanceMVPEditor);
								addConfigPanel.add(instanceMVPEditor);
							}
						}
						
					}
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Entity getConfigInstEnt() {
		Entity configInstEnt = (Entity) modelViewConfigInstMap.get("configInstEnt");
		
		configInstEnt.setPropertyByName("instancename", transformInstanceTextField.getFieldValue());
		
		if(isComponentDefUpdate) {
			Long configtypeId = Long.valueOf(transWgtCompDefEnt.getPropertyByName("configtypeId").toString());
			Entity configTypeEnt = entityInstanceProvider.getConfigType(configtypeId);
			configInstEnt.setProperty("configtype", configTypeEnt);
		}
		return configInstEnt;
	}

	private Entity getCompInstEntity() {
		Entity componentInst = (Entity) modelViewConfigInstMap.get("componentInstEnt");
		String newInstName = transformInstanceTextField.getFieldValue();
		String prevName = componentInst.getPropertyByName("instancename").toString();
		Entity compoDefEnt = (Entity) componentInst.getProperty("componentdefinition");
		
		if(!prevName.equals(newInstName) || !transWgtCompDefEnt.equals(compoDefEnt)) {
			componentInst.setPropertyByName("instancename", transformInstanceTextField.getFieldValue());
			componentInst.setProperty("componentdefinition", transWgtCompDefEnt);
		}
		return componentInst;
	}

	private void deregisterPreviousInstances(){
		for(Widget mvpInstance :instanceMVPEditorsList){
			if(mvpInstance instanceof ConfigurationInstanceMVPEditor){
				((ConfigurationInstanceMVPEditor)mvpInstance).deregisterHandler();
			}else if(mvpInstance instanceof ComponentInstanceMVPEditor){
				((ComponentInstanceMVPEditor)mvpInstance).deregisterHandler();
			}
		}
		instanceMVPEditorsList.clear();
	}
	
	private void hidePanels() {
		isTransformWidgetPanel.setVisible(false);
		transformTypePanel.setVisible(false);
		transformToPanel.setVisible(false);
		transformInstancePanel.setVisible(false);
		isUpdateConfigPanel.setVisible(false);
		updateConfigurationBasePanel.setVisible(false);
		buttonPanel.setVisible(false);
	}
	
	private void showPanels() {
		isTransformWidgetPanel.setVisible(true);
		isUpdateConfigPanel.setVisible(true);
	}
	
	private void showTranformWidgetUI() {
		transformTypePanel.setVisible(true);
		transformToPanel.setVisible(true);
		transformInstancePanel.setVisible(true);
		buttonPanel.setVisible(true);
	}
	
	private void hideTranformWidgetUI() {
		transformTypePanel.setVisible(false);
		transformToPanel.setVisible(false);
		transformInstancePanel.setVisible(false);
		buttonPanel.setVisible(false);
	}
	
	private void showUpdateConfigUI() {
		updateConfigurationBasePanel.setVisible(true);
	}
	
	private void hideUpdateConfigUI() {
		updateConfigurationBasePanel.setVisible(false);
	}
	
	private EntityContext getEntityContext(EntityContext context, Entity entity) {
		Key<Long> key = entity.getPropertyByName("id");
		Long id = key.getKeyValue();

		if(context == null) {
			context  = EntityContextGenerator.defineContext(null, id);
		} else {
			context = context.defineContext(id);;
		}
		return context;
	}
	
	/**
	 * To be optimized
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unused")
	private EntityContext getEntityContextForEventChild(EntityContext context) {
		
		if(context == null) {
			context = getEntityContext(context, parentEventEntity);
			EntityContext context1 = getEntityContext(context, interestedEventEntity);
			
			Entity containerEnt = (Entity) interestedEventEntity.getProperty("configinstance");
			EntityContext context2 = getEntityContext(context1, containerEnt);
	
			Entity pageInstEnt = (Entity) containerEnt.getProperty("configinstance");
			EntityContext context3 = getEntityContext(context2, pageInstEnt);

			EntityContext context4 = getEntityContext(context3, pageEntity);

			EntityContext context5 = getEntityContext(context4, AppEnviornment.CURRENTAPP);
			EntityContext context6 = getEntityContext(context5, AppEnviornment.CURRENTSERVICE);
			
			return context;
		} else {
			EntityContext extracontext = getEntityContext(context, parentEventEntity);
			EntityContext context1 = getEntityContext(extracontext, interestedEventEntity);
			
			Entity containerEnt = (Entity) interestedEventEntity.getProperty("configinstance");
			EntityContext context2 = getEntityContext(context1, containerEnt);
	
			Entity pageInstEnt = (Entity) containerEnt.getProperty("configinstance");
			EntityContext context3 = getEntityContext(context2, pageInstEnt);
			
			EntityContext context4 = getEntityContext(context3, pageEntity);

			EntityContext context5 = getEntityContext(context4, AppEnviornment.CURRENTAPP);
			EntityContext context6 = getEntityContext(context5, AppEnviornment.CURRENTSERVICE);
			
			return context;
			
		}
	}

	public Entity getPageEntity() {
		return pageEntity;
	}

	public void setPageEntity(Entity pageEntity) {
		this.pageEntity = pageEntity;
	}
	
	private void checkUpdateValue(String instancevalue) {
		try {
			if(instancevalue.equals("true")) {
				if(!configInstanceEntityMap.containsKey(PageConfigurationContant.UPDATE_CONFIGURATION)) {
					EntityContext updateContext = getEntityContextForEventChild(null);
					saveConfigInstance(entityInstanceProvider.getConfiginstanceEntity(PageConfigurationContant.UPDATE_CONFIGURATION, PageConfigurationContant.UPDATE_CONFIGURATION, null, parentEventEntity, 5L), false, true, false, updateContext);
				} else {
					showUpdateConfigUI();
				}
			} else {
				hideUpdateConfigUI();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createNewSnippetPropValueEditor() {
		try {
			SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor("queryMode");
			snipPropValEditor.setDeletable(false);
			snipPropValEditor.setInstanceMode(true);
			snipPropValEditor.setParentConfInstanceEntity(updateConfigEntity);
			snipPropValEditor.setConfigTypeListboxVisible(true);
			snipPropValEditor.createUi();
			updateConfigurationPanel.add(snipPropValEditor);
			SnippetPropValueEditorList.add(snipPropValEditor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onConfigInstanceEvent(ConfigInstanceEvent event) {
		try {
			if(event.getEventType() == ConfigEvent.SAVEPROPVALUEADDWIDGET){
				if(event.getEventSource() instanceof SnippetPropValueEditor) {
					SnippetPropValueEditor snipPropValEditorSelected = (SnippetPropValueEditor) event.getEventSource();
					if(SnippetPropValueEditorList != null) {
						if(SnippetPropValueEditorList.contains(snipPropValEditorSelected)) {
							boolean isUpdate = snipPropValEditorSelected.isUpdate();
							Entity entity = snipPropValEditorSelected.getConfInstanceParamValEnt();
							EntityContext context = getEntityContext(null, updateConfigEntity);
							context = getEntityContextForEventChild(context);
							saveConfigInstance(entity, false, false, isUpdate, context);
							createNewSnippetPropValueEditor();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, HashMap<String, Object>> getContainerCompoInstMap() {
		return containerCompoInstMap;
	}

	public void setContainerCompoInstMap(
			HashMap<String, HashMap<String, Object>> containerCompoInstMap) {
		this.containerCompoInstMap = containerCompoInstMap;
	}

	private boolean isComponentDefUpdate;
	
	private VerticalPanel basePanel;
	private VerticalPanel addConfigPanel;
	private ListBoxField spanListbox;
	private VerticalPanel updateConfigurationPanel;
	
	private GroupField isTransformWidgetGrField;
	private GroupField isUpdateConfigGrField;
	private TextField eventNametextField;
	private ListBoxField transformToListbox;
	private Entity parentEventEntity;
	
	private TextField transformInstanceTextField;
	private HashMap<String, HashMap<String, Object>> containerCompoInstMap;
	private Entity interestedEventEntity;
	private Entity pageComponentInstEntity;
	private Entity pageEntity;
	private Entity updateConfigEntity;
	private Entity transWgtCompDefEnt;
	
	private HorizontalPanel titlePanel;
	private HorizontalPanel eventNamePanel;
	private HorizontalPanel isTransformWidgetPanel;
	private HorizontalPanel transformTypePanel;
	private HorizontalPanel transformToPanel;
	private HorizontalPanel transformInstancePanel;
	private HorizontalPanel isUpdateConfigPanel;
	private HorizontalPanel updateConfigurationBasePanel;
	private HorizontalPanel buttonPanel;
	
	private ButtonField configureButton;
	private ImageField plusIconField;
	private ImageField minusIconField;
	private ListBoxField transformTypeListbox;
	private HashMap<String, Object> configInstanceEntityMap;
	private ArrayList<SnippetPropValueEditor> SnippetPropValueEditorList;
	private ListBoxField eventNameListbox = new ListBoxField();
	private ArrayList<HashMap<String, Object>> eventNameList;
	
	private String containerName;
	private HashMap<String, Object> map;
	private Long interestedEvtConfInstId;
	private HashMap<String, Object> tempHashMap;
	private ArrayList<String> eventsList;
	
	private HandlerRegistration fieldEventHandler = null;
	private HandlerRegistration configEventHandler = null;
	private ArrayList<Widget> instanceMVPEditorsList = null;
	private ConfigurationInstanceProvider instanceProvider;
	private EntityInstanceProvider entityInstanceProvider;
	
	private HashMap<String, Object> modelViewConfigInstMap;
}