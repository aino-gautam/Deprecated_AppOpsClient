package in.appops.showcase.web.gwt.componentconfiguration.client.page;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.GroupField;
import in.appops.client.common.config.field.GroupField.GroupFieldConstant;
import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.config.field.RadioButtonField;
import in.appops.client.common.config.field.RadioButtonField.RadionButtonFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.client.EntityContext;
import in.appops.platform.client.EntityContextGenerator;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.configuration.constant.ConfigTypeConstant;
import in.appops.showcase.web.gwt.componentconfiguration.client.library.ComponentInstanceMVPEditor;
import in.appops.showcase.web.gwt.componentconfiguration.client.library.ConfigurationInstanceMVPEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PageConfiguration extends Composite implements ConfigEventHandler,FieldEventHandler {
	
	private VerticalPanel basePanel;
	private VerticalPanel addConfigPanel;
	private ListBoxField spanListbox;
	private VerticalPanel updateConfigurationPanel;
	
	private GroupField isTransformWidgetGrField;
	private GroupField isUpdateConfigGrField;
	private TextField eventNametextField;
	private ListBoxField transformToListbox;
	private Entity parentEventEntity;
	
	private int index;
	private Map<String, TextField> configMap;
	private TextField transformInstanceTextField;
	private Map<String, Map<String, Object>> containerCompoInstMap;
	private Entity interestedEventEntity;
	private Entity pageComponentInstEntity;
	private Entity pageEntity;
	private boolean isNew;
	private Entity updateConfigEntity;
	private Entity transWgtCompDefEnt;
	
	private HorizontalPanel isTransformWidgetPanel;
	private HorizontalPanel transformTypePanel;
	private HorizontalPanel transformToPanel;
	private HorizontalPanel transformInstancePanel;
	private HorizontalPanel isUpdateConfigPanel;
	private HorizontalPanel updateConfigurationBasePanel;
	private HorizontalPanel buttonPanel;
	
	private ArrayList<TextField> updateConfigTextFieldList;
	private ButtonField configureButton;
	private ImageField plusIconField;
	private ImageField minusIconField;
	private ListBoxField transformTypeListbox;
	private Map<String, Entity> configInstanceEntityMap;
	private boolean isUpdateConfigurationEntity;
	
	
	private final String COMPONENT = "Component";
	private final String SNIPPET = "HtmlSnippet";
	
	public PageConfiguration() {
		initialize();
		createUI();
		initWidget(basePanel);
		AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}

	private void initialize() {
		basePanel = new VerticalPanel();
		addConfigPanel = new VerticalPanel();
		updateConfigurationPanel = new VerticalPanel();
		configMap = new HashMap<String, TextField>();
	}
	
	public void createUI() {
		try {
			basePanel.clear();
			
			LabelField titleLabelField = new LabelField();
			titleLabelField.setConfiguration(getConfigTitleLabelConfig());
			titleLabelField.configure();
			titleLabelField.create();
			
			basePanel.add(titleLabelField);
			
			HorizontalPanel spanSelectionPanel = createSpanSelectionPanel();
			basePanel.add(spanSelectionPanel);
			
			basePanel.add(addConfigPanel);
			basePanel.setStylePrimaryName(PAGE_CONFIGURATION_BASEPANEL_CSS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private HorizontalPanel createSpanSelectionPanel() {
		try {
			HorizontalPanel spanSelectionPanel = new HorizontalPanel();
			
			LabelField selectSpanLabelField = new LabelField();
			selectSpanLabelField.setConfiguration(getSelectSpanLabelConfig());
			selectSpanLabelField.configure();
			selectSpanLabelField.create();
			spanSelectionPanel.add(selectSpanLabelField);
			
			spanListbox = new ListBoxField();
			spanListbox.setConfiguration(getSpanListBoxConfiguration(null));
			spanListbox.configure();
			spanListbox.create();
			spanSelectionPanel.add(spanListbox);
			
			spanSelectionPanel.setCellWidth(selectSpanLabelField, "20%");
			spanSelectionPanel.setCellVerticalAlignment(selectSpanLabelField, HasVerticalAlignment.ALIGN_MIDDLE);
			spanSelectionPanel.setStylePrimaryName(SPAN_SELECTION_PANEL_CSS);
			return spanSelectionPanel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates the select span Label configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getSelectSpanLabelConfig() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Select a span element");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, SPAN_SELECTION_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates the Config Title Label configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getConfigTitleLabelConfig() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Page Configurations");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, CONFIG_TITLE_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the Span Listbox configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getSpanListBoxConfiguration(ArrayList<String> configList) {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,SPAN_LISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--Select span--");
			if(configList != null) {
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,configList);
			}
			return configuration;
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
		spanListbox.setConfiguration(getSpanListBoxConfiguration(configList));
		spanListbox.configure();
		spanListbox.create();
		containerCompoInstMap = new HashMap<String, Map<String,Object>>();
	}
	
	public void createPropertyConfigUI() {
		updateConfigTextFieldList = new ArrayList<TextField>();
		configInstanceEntityMap = new HashMap<String, Entity>();
		addConfigPanel.clear();
		
		HorizontalPanel titlePanel = createTitlePanel();
		addConfigPanel.add(titlePanel);
		
		HorizontalPanel eventNamePanel = createEventNamePanel();
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
		addConfigPanel.setStylePrimaryName(ADD_PAGE_CONFIGURATION_BASEPANEL_CSS);
	}
	
	private HorizontalPanel createTitlePanel() {
		HorizontalPanel titlePanel = new HorizontalPanel();
		
		LabelField propConfigTitleLabel = new LabelField();
		propConfigTitleLabel.setConfiguration(getPropConfigTitleLabelConfiguration());
		propConfigTitleLabel.configure();
		propConfigTitleLabel.create();
		titlePanel.add(propConfigTitleLabel);
		
		plusIconField = new ImageField();
		plusIconField.setConfiguration(getPlusIconFieldConfiguration());
		plusIconField.configure();
		plusIconField.create();
		titlePanel.add(plusIconField);
		
		titlePanel.setCellWidth(propConfigTitleLabel, "150px");
		titlePanel.setCellHorizontalAlignment(plusIconField, HasHorizontalAlignment.ALIGN_RIGHT);
		titlePanel.setWidth("100%");
		return titlePanel;
	}

	/**
	 * Creates the plus Image Field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getPlusIconFieldConfiguration(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ImageFieldConstant.BF_ID, PLUS_ICONFIELD_ID);
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, "images/plus-icon.png");
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS, PLUS_ICONFIELD_CSS);
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "Add new");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}

	public HorizontalPanel createButtonPanel() {
		HorizontalPanel buttonPanel = new HorizontalPanel();
		
		configureButton = new ButtonField();
		configureButton.setConfiguration(getConfigureButtonConfiguration());
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
		transformToLabel.setConfiguration(getTransformToLabelConfiguration());
		transformToLabel.configure();
		transformToLabel.create();
		transformToPanel.add(transformToLabel);
		
		transformToListbox = new ListBoxField();
		transformToListbox.setConfiguration(getTransformToListBoxConfiguration(null,false));
		transformToListbox.configure();
		transformToListbox.create();
		transformToPanel.add(transformToListbox);
		
		transformToPanel.setStylePrimaryName(TRANSFORM_TO_PANEL_CSS);
		transformToPanel.setCellWidth(transformToLabel, "172px");
		transformToPanel.setCellVerticalAlignment(transformToLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		return transformToPanel;
	}

	/**
	 * Creates the Transform To Listbox configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getTransformToListBoxConfiguration(HashMap<String, Object> paramMap, boolean isHtmlSnippet) {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,TRANSFORM_TO_LISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--Select transform to--");
			
			if(paramMap != null) {
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"appdefinition.AppDefinitionService.getEntityList");
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERY_RESTRICTION,paramMap);
				if(isHtmlSnippet) {
					configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getComponentDefinationForHtmlSnipp");
				} else {
					configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getComponentDefinationForComponent");
				}
			} else {
				ArrayList<String> items = new ArrayList<String>();
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,items);
			}
			
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates the transform to Label configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getTransformToLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Transform To");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, TRANSFORM_TO_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private HorizontalPanel createtransformTypePanel() {
		HorizontalPanel transformTypePanel = new HorizontalPanel();
		
		LabelField transformTypeLabel = new LabelField();
		transformTypeLabel.setConfiguration(getTransformTypeLabelConfiguration());
		transformTypeLabel.configure();
		transformTypeLabel.create();
		transformTypePanel.add(transformTypeLabel);
		
		transformTypeListbox = new ListBoxField();
		transformTypeListbox.setConfiguration(getTransformTypeListBoxConfiguration());
		transformTypeListbox.configure();
		transformTypeListbox.create();
		transformTypePanel.add(transformTypeListbox);
		
		transformTypePanel.setStylePrimaryName(TRANSFORM_TYPE_PANEL_CSS);
		transformTypePanel.setCellWidth(transformTypeLabel, "172px");
		transformTypePanel.setCellVerticalAlignment(transformTypeLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		return transformTypePanel;
	}

	/**
	 * Creates the Transform Type Listbox configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getTransformTypeListBoxConfiguration() {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,TRANSFORM_TYPE_LISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--Select type--");
			ArrayList<String> configList = new ArrayList<String>();
			configList.add(COMPONENT);
			configList.add(SNIPPET);
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,configList);
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates the transform type Label configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getTransformTypeLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Transform Type");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, TRANSFORM_TYPE_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private HorizontalPanel createtransformInstancePanel() {
		HorizontalPanel transformInstancePanel = new HorizontalPanel();
		
		LabelField transformInstanceLabel = new LabelField();
		transformInstanceLabel.setConfiguration(getTransformInstanceLabelConfiguration());
		transformInstanceLabel.configure();
		transformInstanceLabel.create();
		transformInstancePanel.add(transformInstanceLabel);
		
		transformInstanceTextField = new TextField();
		transformInstanceTextField.setConfiguration(getTextFieldConfiguration(TRANSFORM_INSTANCE_TEXTFIELD_ID));
		transformInstanceTextField.configure();
		transformInstanceTextField.create();
		transformInstancePanel.add(transformInstanceTextField);
		
		transformInstancePanel.setStylePrimaryName(TRANSFORM_INSTANCE_PANEL_CSS);
		transformInstancePanel.setCellWidth(transformInstanceLabel, "172px");
		transformInstancePanel.setCellVerticalAlignment(transformInstanceLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		return transformInstancePanel;
	}
	
	/**
	 * Creates the transform instance Label configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getTransformInstanceLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Transform Instance");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, TRANSFORM_INSTANCE_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private HorizontalPanel createIsTransformWidgetPanel() {
		HorizontalPanel isTransformWidgetPanel = new HorizontalPanel();
		
		LabelField isTransformWidgetLabel = new LabelField();
		isTransformWidgetLabel.setConfiguration(getIsTransformWidgetLabelConfiguration());
		isTransformWidgetLabel.configure();
		isTransformWidgetLabel.create();
		isTransformWidgetPanel.add(isTransformWidgetLabel);
		
		isTransformWidgetGrField = createGroupField(IS_TRANSFORM_WIDGET_GROUP_ID);
		isTransformWidgetPanel.add(isTransformWidgetGrField);
		
		isTransformWidgetPanel.setStylePrimaryName(IS_TRANSFORM_WIDGET_PANEL_CSS);
		isTransformWidgetPanel.setCellWidth(isTransformWidgetLabel, "165px");
		isTransformWidgetPanel.setCellVerticalAlignment(isTransformWidgetLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		return isTransformWidgetPanel;
	}

	/**
	 * Creates the is Transform Widget Label configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getIsTransformWidgetLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Is Transform Widget");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, IS_UPDATE_CONFIG_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private HorizontalPanel createUpdateConfigPanel() {
		isNew = true;
		HorizontalPanel updateConfigurationBasePanel = new HorizontalPanel();
		VerticalPanel updateConfigPanelWithButton = new VerticalPanel();
		
		LabelField updateConfigLabel = new LabelField();
		updateConfigLabel.setConfiguration(getUpdateConfigLabelConfiguration());
		updateConfigLabel.configure();
		updateConfigLabel.create();
		updateConfigurationBasePanel.add(updateConfigLabel);
		
		updateConfigurationPanel.clear();
		createUpdateConfigRow(false);
		updateConfigPanelWithButton.add(updateConfigurationPanel);
		updateConfigurationBasePanel.add(updateConfigPanelWithButton);
		
		updateConfigPanelWithButton.setWidth("100%");
		
		updateConfigurationPanel.setStylePrimaryName(UPDATE_CONFIGURATION_PANEL_CSS);
		updateConfigurationBasePanel.setStylePrimaryName(UPDATE_CONFIGURATION_BASEPANEL_CSS);
		updateConfigurationBasePanel.setCellWidth(updateConfigLabel, "172px");
		return updateConfigurationBasePanel;
	}

	private void createUpdateConfigRow(boolean isSetFocus) {
		HorizontalPanel innerUpdateConfigPanel = new HorizontalPanel();
		
		TextField updateConfigKeyTextField = new TextField();
		updateConfigKeyTextField.setConfiguration(getTextFieldConfiguration(UPDATE_CONFIG_KEY_TEXTFIELD_ID));
		updateConfigKeyTextField.configure();
		updateConfigKeyTextField.create();
		innerUpdateConfigPanel.add(updateConfigKeyTextField);
		
		if(isSetFocus) {
			updateConfigKeyTextField.setFocus();
		}
		
		TextField updateConfigValueTextField = new TextField();
		updateConfigValueTextField.setConfiguration(getTextFieldConfiguration(UPDATE_CONFIG_VALUE_TEXTFIELD_ID));
		updateConfigValueTextField.configure();
		updateConfigValueTextField.create();
		innerUpdateConfigPanel.add(updateConfigValueTextField);
		((TextBox) updateConfigValueTextField.getWidget()).setName(UPDATE_CONFIG_VALUE_TEXTFIELD_ID + index);
		
		minusIconField = new ImageField();
		minusIconField.setConfiguration(getMinusIconFieldConfiguration());
		minusIconField.configure();
		minusIconField.create();
		innerUpdateConfigPanel.add(minusIconField);
		
		innerUpdateConfigPanel.setStylePrimaryName(INNER_UPDATE_CONFIGURATION_PANEL_CSS);
		updateConfigurationPanel.add(innerUpdateConfigPanel);
		
		configMap.put(UPDATE_CONFIG_VALUE_TEXTFIELD_ID + index, updateConfigKeyTextField);
		index++;
		updateConfigTextFieldList.add(updateConfigValueTextField);
	}

	/**
	 * Creates the Configure button configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getConfigureButtonConfiguration() {
		try {
			Configuration configuration = new Configuration();
			try {
				configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Configure transform widget");
				configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,CONFIGURE_BUTTON_CSS);
				configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, true);
				configuration.setPropertyByName(ButtonFieldConstant.BF_ID, CONFIGURE_BUTTON_ID);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the Image Field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getMinusIconFieldConfiguration(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, "images/minus-icon.jpg");
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS, MINUS_ICONFIELD_CSS);
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "Delete");
			configuration.setPropertyByName(ImageFieldConstant.BF_ID, MINUS_ICONFIELD_ID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}

	/**
	 * Creates the Update Config Label configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getUpdateConfigLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Update Configuration");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, IS_UPDATE_CONFIG_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private HorizontalPanel createIsUpdateConfigPanel() {
		HorizontalPanel isUpdateConfigPanel = new HorizontalPanel();
		
		LabelField isUpdateConfigLabel = new LabelField();
		isUpdateConfigLabel.setConfiguration(getIsUpdateConfigLabelConfiguration());
		isUpdateConfigLabel.configure();
		isUpdateConfigLabel.create();
		isUpdateConfigPanel.add(isUpdateConfigLabel);
		
		isUpdateConfigGrField = createGroupField(IS_UPDATE_CONFIG_GROUP_ID);
		isUpdateConfigPanel.add(isUpdateConfigGrField);
		
		isUpdateConfigPanel.setStylePrimaryName(IS_UPDATE_CONFIG_PANEL_CSS);
		isUpdateConfigPanel.setCellWidth(isUpdateConfigLabel, "165px");
		isUpdateConfigPanel.setCellVerticalAlignment(isUpdateConfigLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		return isUpdateConfigPanel;
	}

	private GroupField createGroupField(String groupId) {
		try {
			GroupField groupField = new GroupField();
			Configuration groupFieldConfig = new Configuration();
			groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_ID,groupId);
			groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_TYPE,GroupFieldConstant.GFTYPE_SINGLE_SELECT);
			groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_ALIGNMENT,GroupFieldConstant.GF_ALIGN_HORIZONTAL);
			groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_LIMIT,2);
			
			ArrayList<String> listOfItems = new ArrayList<String>();
			listOfItems.add("radio1");
			listOfItems.add("radio2");
			groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_LIST_OF_ITEMS,listOfItems);
			
			Configuration childConfig1 = new Configuration();
			childConfig1.setPropertyByName(RadionButtonFieldConstant.BF_PCLS, "appops-CheckBoxField");
			childConfig1.setPropertyByName(RadionButtonFieldConstant.RF_DISPLAYTEXT, "true");
			//childConfig1.setPropertyByName(RadionButtonFieldConstant.RF_CHECKED, true);
			
			Configuration childConfig2 = new Configuration();
			childConfig2.setPropertyByName(RadionButtonFieldConstant.BF_PCLS, "appops-CheckBoxField");
			childConfig2.setPropertyByName(RadionButtonFieldConstant.RF_DISPLAYTEXT, "false");
			
			groupFieldConfig.setPropertyByName("radio1",childConfig1);
			groupFieldConfig.setPropertyByName("radio2",childConfig2);
			
			groupField.setConfiguration(groupFieldConfig);
			groupField.configure();
			groupField.create();
			return groupField;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates the is Update Config Label configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getIsUpdateConfigLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Is Update Configuration");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, IS_UPDATE_CONFIG_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private HorizontalPanel createEventNamePanel() {
		HorizontalPanel eventNamePanel = new HorizontalPanel();
		
		LabelField eventNameLabel = new LabelField();
		eventNameLabel.setConfiguration(getEventNameLabelConfiguration());
		eventNameLabel.configure();
		eventNameLabel.create();
		eventNamePanel.add(eventNameLabel);
		
		eventNametextField = new TextField();
		eventNametextField.setConfiguration(getTextFieldConfiguration(EVENT_NAME_TEXTFIELD_ID));
		eventNametextField.configure();
		eventNametextField.create();
		eventNamePanel.add(eventNametextField);
		
		eventNamePanel.setStylePrimaryName(EVENT_NAME_PANEL_CSS);
		eventNamePanel.setCellWidth(eventNameLabel, "172px");
		eventNamePanel.setCellVerticalAlignment(eventNameLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		return eventNamePanel;
	}

	/**
	 * Creates the Event Name Label configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getEventNameLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Event Name");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, EVENT_NAME_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates the Property Config Title Label configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getPropConfigTitleLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Interested Event");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, PROP_CONFIG_TITLE_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the TextField configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getTextFieldConfiguration(String textFieldId) {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(TextFieldConstant.BF_ID, textFieldId);
			configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, 1);
			configuration.setPropertyByName(TextFieldConstant.BF_READONLY, false);
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_VALIDATEONCHANGE, true);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, TextFieldConstant.BF_SIDE);
			configuration.setPropertyByName(TextFieldConstant.TF_MAXLENGTH, 100);
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			int eventType = event.getEventType();
			switch (eventType) {
			case FieldEvent.TAB_KEY_PRESSED: {
				if(event.getEventSource() instanceof TextField) {
					TextField source = (TextField) event.getEventSource();
					String fieldId = source.getBaseFieldId();
					if(source.equals(eventNametextField)) {
						if(!eventNametextField.getFieldValue().trim().equals("")) {
							Entity entity = null;
							boolean isUpdate;
							if(configInstanceEntityMap.containsKey(eventNametextField.getFieldValue())) {
								entity = configInstanceEntityMap.get(eventNametextField.getFieldValue());
								entity.setPropertyByName("instancename", eventNametextField.getFieldValue());
								entity.setPropertyByName("configkeyname", eventNametextField.getFieldValue());
								isUpdate = true;
							} else {
								isUpdate = false;
								entity = getConfiginstanceEntity(eventNametextField.getFieldValue(), eventNametextField.getFieldValue(), null, interestedEventEntity, 4L);
							}
							
							EntityContext context = getEntityContext(null, interestedEventEntity);
							
							Entity containerEnt = (Entity) interestedEventEntity.getProperty("configinstance");
							EntityContext context1 = getEntityContext(context, containerEnt);

							Entity pageInstEnt = (Entity) containerEnt.getProperty("configinstance");
							EntityContext context2 = getEntityContext(context1, pageInstEnt);

							EntityContext context3 = getEntityContext(context2, pageEntity);
							
							EntityContext context4 = getEntityContext(context3, AppEnviornment.CURRENTAPP);
							EntityContext context5 = getEntityContext(context4, AppEnviornment.CURRENTSERVICE);
							
							saveConfigInstance(entity, true, null, isUpdate, context);
						}
					} else if(fieldId.equals(UPDATE_CONFIG_VALUE_TEXTFIELD_ID)) {
						if(updateConfigTextFieldList.contains(source)) {
							String value = (String) event.getEventData();
							if(!value.trim().equals("")) {
								if(isNew) {
									isNew = false;
									createUpdateConfigRow(true);
									EntityContext context = getEntityContextForEventChild(null);
									saveConfigInstance(getConfiginstanceEntity("UpdateConfiguration", "UpdateConfiguration", null, parentEventEntity, 5L), false, source, false, context);
								} else {
									Entity entity = createEntityWithInfo(source);
									createUpdateConfigRow(true);
									if(entity != null) {
										EntityContext context = getEntityContext(null, updateConfigEntity);
										context = getEntityContextForEventChild(context);
										saveConfigInstance(entity, false, null, isUpdateConfigurationEntity, context);
									}
								}
							}
						}
					} else if(source.equals(transformInstanceTextField)) {
						String value = (String) event.getEventData();
						if(!value.trim().equals("")) {
							Entity entity = null;
							boolean isUpdate;
							if(configInstanceEntityMap.containsKey("transformInstance")) {
								entity = configInstanceEntityMap.get("transformInstance");
								entity.setPropertyByName("instancevalue", value);
								isUpdate = true;
							} else {
								isUpdate = false;
								entity = getConfiginstanceEntity("transformInstance", "transformInstance", value, parentEventEntity, 6L);
							}
							
							EntityContext context = getEntityContextForEventChild(null);
							saveConfigInstance(entity, false, null, isUpdate, context);
						}
					}
				} else if(event.getEventSource() instanceof GroupField) {
					GroupField source = (GroupField) event.getEventSource();
					String fieldId = source.getBaseFieldId();
					if(fieldId.equals(IS_TRANSFORM_WIDGET_GROUP_ID)) {
						
					} else if(fieldId.equals(IS_UPDATE_CONFIG_GROUP_ID)) {
						
					}
				}
				break;
			}
			case FieldEvent.CLICKED: {
				if(event.getEventSource() instanceof ButtonField) {
					ButtonField source = (ButtonField) event.getEventSource();
					if(source.equals(configureButton)) {
//						ConfigEvent configEvent = new ConfigEvent(ConfigEvent.CONFIGURATION_COMPLETED, transformMap, this);
//						AppUtils.EVENT_BUS.fireEvent(configEvent);
						String value = (String) transformToListbox.getValue();
						String defaultValue = transformToListbox.getSuggestionValueForListBox();
						if(!transformInstanceTextField.getFieldValue().trim().equals("") && !value.equals(defaultValue)) {
							saveTransformWidgetInstance();
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
						if(configInstanceEntityMap.containsKey("isTransformWidget")) {
							entity = configInstanceEntityMap.get("isTransformWidget");
							entity.setPropertyByName("instancevalue", text);
							isUpdate = true;
						} else {
							isUpdate = false;
							entity = getConfiginstanceEntity("isTransformWidget", "isTransformWidget", text, parentEventEntity, 9L);
						}
						
						EntityContext context = getEntityContextForEventChild(null);
						saveConfigInstance(entity, false, null, isUpdate, context);
						
						if(text.equals("true")) {
							showTranformWidgetUI();
						} else {
							hideTranformWidgetUI();
						}
					} else if(source.equals(isUpdateConfigGrField)) {
						Entity entity = null;
						boolean isUpdate;
						if(configInstanceEntityMap.containsKey("isUpdateConfiguration")) {
							entity = configInstanceEntityMap.get("isUpdateConfiguration");
							entity.setPropertyByName("instancevalue", text);
							isUpdate = true;
						} else {
							isUpdate = false;
							entity = getConfiginstanceEntity("isUpdateConfiguration", "isUpdateConfiguration", text, parentEventEntity, 10L);
						}
						EntityContext context = getEntityContextForEventChild(null);
						saveConfigInstance(entity, false, null, isUpdate, context);
						
						if(text.equals("true")) {
							showUpdateConfigUI();
						} else {
							hideUpdateConfigUI();
						}
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
							instanceValue = String.valueOf(1);
						} else if(value.equals("HtmlSnippet")) {
							instanceValue = String.valueOf(2);
						}
						
						Entity entity = null;
						boolean isUpdate;
						if(configInstanceEntityMap.containsKey("transformType")) {
							entity = configInstanceEntityMap.get("transformType");
							entity.setPropertyByName("instancevalue", instanceValue);
							isUpdate = true;
						} else {
							isUpdate = false;
							entity = getConfiginstanceEntity("transformType", "transformType", instanceValue, parentEventEntity, 8L);
						}
						EntityContext context = getEntityContextForEventChild(null);
						saveConfigInstance(entity, false, null, isUpdate, context);
					} else if(source.equals(transformToListbox)) {
						String value = (String) source.getValue();
						Entity entity = null;
						boolean isUpdate;
						if(configInstanceEntityMap.containsKey("transformTo")) {
							entity = configInstanceEntityMap.get("transformTo");
							entity.setPropertyByName("instancevalue", value);
							isUpdate = true;
						} else {
							isUpdate = false;
							entity = getConfiginstanceEntity("transformTo", "transformTo", value, parentEventEntity, 7L);
						}
						EntityContext context = getEntityContextForEventChild(null);
						saveConfigInstance(entity, false, null, isUpdate, context);
						transWgtCompDefEnt = source.getAssociatedEntity(value);
						//transformMap.put(transformInstanceTextField.getFieldValue(), entity);
					} else if(source.equals(spanListbox)) {
						String value = (String) source.getValue();
						if(value.equals(source.getSuggestionValueForListBox())) {
							addConfigPanel.clear();
						} else {
							if(!containerCompoInstMap.containsKey(value)) {
								Entity entity = createComponentInstance(value);
								saveComponentInstance(entity);
							} else {
								initializeEditor(containerCompoInstMap.get(value));
							}
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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

	private Entity createEntityWithInfo(TextField source) {
		Entity entity = null;
		String name = ((TextBox) source.getWidget()).getName();
		TextField textField = configMap.get(name);
		if(!textField.getFieldValue().trim().equals("")) {
			if(configInstanceEntityMap.containsKey(textField.getFieldValue())) {
				isUpdateConfigurationEntity = true;
				entity = configInstanceEntityMap.get(textField.getFieldValue());
				entity.setPropertyByName("instancename", textField.getFieldValue());
				entity.setPropertyByName("configkeyname", textField.getFieldValue());
				entity.setPropertyByName("instancevalue", source.getFieldValue());
			} else {
				isUpdateConfigurationEntity = false;
				entity = getConfiginstanceEntity(textField.getFieldValue(), textField.getFieldValue(), source.getFieldValue(), updateConfigEntity, null);
			}
		}
		return entity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void saveConfigInstance(final Entity configinstanceEntity, final boolean isParentInstance, final TextField source, boolean isUpdate, EntityContext context) {

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
							configInstanceEntityMap.put(name, ent);
							if(isParentInstance) {
								parentEventEntity = ent;
								showPanels();
							}
							if(source != null) {
								updateConfigEntity = ent;
								Entity entity = createEntityWithInfo(source);
								if(entity != null) {
									EntityContext context = getEntityContext(null, updateConfigEntity);
									context = getEntityContextForEventChild(context);
									saveConfigInstance(entity, false, null, isUpdateConfigurationEntity, context);
								}
							}
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void fetchTransformToList(String value) {
		
		HashMap map = new HashMap();
		if(value.equals("Component")) {
			byte isMvp = 1;
			map.put("isMvp", isMvp);
			map.put("typeId", 159L);
			transformToListbox.setConfiguration(getTransformToListBoxConfiguration(map,false));
		} else if(value.equals("HtmlSnippet")) {
			map.put("typeId", 159L);
			transformToListbox.setConfiguration(getTransformToListBoxConfiguration(map,true));
		}
		
		transformToListbox.configure();
		transformToListbox.create();
	}

	public Entity getConfiginstanceEntity(String instanceName, String configKeyName, String instanceValue, Entity parent, Long configTypeId) {
		try{
			Entity configInstEntity = new Entity();
			configInstEntity.setType(new MetaType("Configinstance"));
			configInstEntity.setPropertyByName("instancename", instanceName);
			configInstEntity.setPropertyByName("configkeyname", configKeyName);
			if(instanceValue != null) {
				configInstEntity.setPropertyByName("instancevalue", instanceValue);
			}
			if(parent != null) {
				configInstEntity.setProperty("configinstance", parent);
			}
			if(configTypeId != null) {
				configInstEntity.setProperty("configtype", getConfigType(configTypeId));
			}
			return configInstEntity;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//TODO : Hardcoded entity has been set
	private Entity getConfigType(Long configTypeId) {
		Entity configTypeEntity = new Entity();
		configTypeEntity.setType(new MetaType("Configtype"));
		Key<Long> key = new Key<Long>(configTypeId);
		Property<Key<Long>> keyProp = new Property<Key<Long>>(key);
		configTypeEntity.setProperty(ConfigTypeConstant.ID, keyProp);
		return configTypeEntity;
	}

	public Entity createComponentInstance(String value) {
		try{
			Entity configInstEntity = new Entity();
			configInstEntity.setType(new MetaType("Componentinstance"));
			configInstEntity.setPropertyByName("instancename", value);
			configInstEntity.setProperty("componentdefinition", getCompoDefEnt());
			configInstEntity.setProperty("componentinstance", this.pageComponentInstEntity);
			return configInstEntity;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//TODO : Hardcoded entity has been set
	private Entity getCompoDefEnt() {
		try{
			Entity compoDefEntity = new Entity();
			compoDefEntity.setType(new MetaType("Componentdefinition"));
			Key<Long> key = new Key<Long>(78L);
			Property<Key<Long>> keyProp = new Property<Key<Long>>(key);
			compoDefEntity.setProperty("id", keyProp);
			compoDefEntity.setPropertyByName("name", "Container");
			compoDefEntity.setPropertyByName("typeId", 189L);
			
			//TODO : hardcoded entity
			compoDefEntity.setPropertyByName("configtypeId", 134L);
			compoDefEntity.setPropertyByName("isMvp", 0);
			return compoDefEntity;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setPageComponentInstEntity(Entity entity) {
		this.pageComponentInstEntity = entity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void saveTransformWidgetInstance() {
		try{
			Entity compInstEntity = new Entity();
			compInstEntity.setType(new MetaType("Componentinstance"));
			compInstEntity.setPropertyByName("instancename", transformInstanceTextField.getFieldValue());
			compInstEntity.setProperty("componentdefinition", transWgtCompDefEnt);
			compInstEntity.setProperty("componentinstance", this.pageComponentInstEntity);
			
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Map parameterMap = new HashMap();
			parameterMap.put("componentInstEnt", compInstEntity);
			parameterMap.put("isUpdate", false);
			
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

			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveComponentInstance", parameterMap);
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
							
							if(transformTypeListbox.getValue().toString().equals(SNIPPET)){
								ConfigurationInstanceMVPEditor instanceMVPEditor = new ConfigurationInstanceMVPEditor();
								instanceMVPEditor.setPageEntity(pageEntity);
								instanceMVPEditor.setConfigInstEnt(configInst);
								instanceMVPEditor.setViewInstanceEnt(viewInstanceEnt);
								instanceMVPEditor.setModelInstanceEnt(modelInstanceEnt);
								instanceMVPEditor.createUi();
								addConfigPanel.add(instanceMVPEditor);
							}
							else if((transformTypeListbox.getValue().toString().equals(COMPONENT))){
								ComponentInstanceMVPEditor instanceMVPEditor = new ComponentInstanceMVPEditor();
								instanceMVPEditor.setPageEntity(pageEntity);
								instanceMVPEditor.setConfigInstEnt(configInst);
								instanceMVPEditor.setViewInstanceEnt(viewInstanceEnt);
								instanceMVPEditor.setModelInstanceEnt(modelInstanceEnt);
								instanceMVPEditor.setPageCompInstEntity(pageComponentInstEntity);
								instanceMVPEditor.createUi();
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


	private final String SPAN_LISTBOX_ID = "spanListBoxFieldId";
	private final String TRANSFORM_TO_LISTBOX_ID = "transformToListboxId";
	private final String TRANSFORM_TYPE_LISTBOX_ID = "transformTypeListboxId";
	private final String TRANSFORM_INSTANCE_TEXTFIELD_ID = "transformInstanceTextFieldId";
	private final String UPDATE_CONFIG_KEY_TEXTFIELD_ID = "updateConfigKeyTextFieldId";
	private final String UPDATE_CONFIG_VALUE_TEXTFIELD_ID = "updateConfigValueTextFieldId";
	private final String EVENT_NAME_TEXTFIELD_ID = "eventNameTextFieldId";
	private final String CONFIGURE_BUTTON_ID = "configureButtonId";
	private final String IS_UPDATE_CONFIG_GROUP_ID = "isUpdateConfigGroupId";
	private final String IS_TRANSFORM_WIDGET_GROUP_ID = "isTransformWidgetGroupId";
	private final String PLUS_ICONFIELD_ID = "plusIconFieldId";
	private final String MINUS_ICONFIELD_ID = "minusIconFieldId";
	
	private final String CONFIG_TITLE_LABEL_CSS = "configTitleLabel";
	private final String SPAN_SELECTION_LABEL_CSS = "spanSelectionLabel";
	private final String SPAN_SELECTION_PANEL_CSS = "spanSelectionPanel";
	private final String PAGE_CONFIGURATION_BASEPANEL_CSS = "pageConfigurationBasePanel";
	private final String PROP_CONFIG_TITLE_LABEL_CSS = "propConfigTitleLabel";
	private final String EVENT_NAME_LABEL_CSS = "eventNameLabel";
	private final String EVENT_NAME_PANEL_CSS = "eventNamePanel";
	private final String IS_UPDATE_CONFIG_LABEL_CSS = "isUpdateConfigLabel";
	private final String IS_UPDATE_CONFIG_PANEL_CSS = "isUpdateConfigPanel";
	private final String UPDATE_CONFIGURATION_BASEPANEL_CSS = "updateConfigurationBasePanel";
	private final String UPDATE_CONFIGURATION_PANEL_CSS = "updateConfigurationPanel";
	private final String MINUS_ICONFIELD_CSS = "minusIconField";
	private final String INNER_UPDATE_CONFIGURATION_PANEL_CSS = "innerUpdateConfigurationPanel";
	private final String IS_TRANSFORM_WIDGET_PANEL_CSS = "isTransformWidgetPanel";
	private final String TRANSFORM_INSTANCE_PANEL_CSS = "transformInstancePanel";
	private final String TRANSFORM_INSTANCE_LABEL_CSS = "transformInstanceLabel";
	private final String TRANSFORM_TYPE_PANEL_CSS = "transformTypePanel";
	private final String TRANSFORM_TYPE_LABEL_CSS = "transformTypeLabel";
	private final String TRANSFORM_TO_PANEL_CSS = "transformToPanel";
	private final String TRANSFORM_TO_LABEL_CSS = "transformToLabel";
	private final String ADD_PAGE_CONFIGURATION_BASEPANEL_CSS = "addPageConfigBasePanel";
	private final String CONFIGURE_BUTTON_CSS = "configureButton";
	private final String PLUS_ICONFIELD_CSS = "plusIconField";
}