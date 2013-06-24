package in.appops.showcase.web.gwt.fields.client;

import in.appops.client.common.components.LocationHomeSelector;
import in.appops.client.common.components.MediaAttachWidget;
import in.appops.client.common.components.WebMediaAttachWidget;
import in.appops.client.common.config.field.BaseField.BaseFieldConstant;
import in.appops.client.common.config.field.date.DatePickerField;
import in.appops.client.common.config.field.spinner.SpinnerField;
import in.appops.client.common.config.field.spinner.SpinnerField.SpinnerFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.CheckboxField.CheckBoxFieldConstant;
import in.appops.client.common.fields.DateTimeField;
import in.appops.client.common.fields.GroupField;
import in.appops.client.common.fields.GroupField.GroupFieldConstant;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.LinkField;
import in.appops.client.common.fields.LocationSelector;
import in.appops.client.common.fields.NumberField;
import in.appops.client.common.fields.StateField;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.client.common.fields.slider.NumericRangeSliderFieldComponent;
import in.appops.client.common.fields.slider.StringRangeSliderFieldComponent;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.operation.ResponseActionContext;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.spacemanagement.constants.SpaceTypeConstants;
import in.appops.showcase.web.gwt.holder.client.ShowcaseComponentHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.code.gwt.geolocation.client.Coordinates;
import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FieldsShowCase implements EntryPoint, FieldEventHandler, ChangeHandler {

	private Label numberfieldErrorLabel;
	private ListBox listBox;
	private VerticalPanel innerPanel;
	private ShowcaseComponentHolder componentHolder;
	
	public static final String TEXTBOX = "Text Box";
	public static final String PASSWORDTEXTBOX = "Password Textbox";
	public static final String EMAILBOX = "Email Textbox";
	public static final String TEXTAREA = "Text Area";
	public static final String NUMERICBOX = "Numeric Textbox";
	public static final String CHECKBOXGROUPMULTISELECT = "CheckboxGroupField";
	public static final String CHECKBOXFIELD = "CheckboxField";
	public static final String STATEFIELD = "StateField";
	public static final String TIME_PICKER = "Time Picker";
	public static final String TIME_PICKER_HOUR = "Time Picker(Short_Hours )";
	public static final String TIME_PICKER_MINUTE = "Time Picker(Short_Minute )";
	public static final String TIME_PICKER_SEC = "Time Picker(Short_Sec )";
	public static final String DATE_PICKER = "Date Picker";
	public static final String DATETIME_PICKER = "Date Time Picker";
	public static final String LOCATIONSELECTOR = "Location Selector";
	public static final String NUMBERRANGE_SLIDER = "NumericRangeSlider";
	public static final String STRINGRANGE_SLIDER = "StringRangeSlider";
	public static final String NUM_SPINNER = "Numeric Spinner";
	public static final String LIST_SPINNER = "List Spinner";
	public static final String NUMBERFIELD = "NumberField";
	public static final String MEDIA_UPLOAD = "Media Uploader";
	public static final String GROUPFIELD = "Group Field (Check Box)";
	public static final String GROUPFIELDRADIO = "Group Field (Radio Button)";
	
	private Image loaderImage;
	public FieldsShowCase() {
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}
	
	@Override
	public void onModuleLoad() {
		
		VerticalPanel basePanel = getFieldsShowcaseUi();
		componentHolder = new ShowcaseComponentHolder();
		componentHolder.add(basePanel,DockPanel.CENTER);
		RootPanel.get().add(componentHolder);
	}
	
	private VerticalPanel getFieldsShowcaseUi(){
		loaderImage = new Image("images/opptinLoader.gif");
		loaderImage.setStylePrimaryName("appops-intelliThoughtActionImage");
		loaderImage.setVisible(false);
		
		VerticalPanel basePanel = new VerticalPanel();
		innerPanel = new VerticalPanel();
		innerPanel.setWidth("100%");

		HorizontalPanel listBoxPanel = new HorizontalPanel();
		Label titleLabel = new Label("Select a field");
		
		listBox = new ListBox();
		listBox.addItem("--Select--");
		listBox.addItem(TEXTBOX);
		listBox.addItem(PASSWORDTEXTBOX);
		listBox.addItem(EMAILBOX);
		listBox.addItem(TEXTAREA);
		listBox.addItem(NUMERICBOX);
		/*listBox.addItem(CHECKBOXGROUPMULTISELECT);
		listBox.addItem(CHECKBOXFIELD);
		listBox.addItem(STATEFIELD);
		listBox.addItem(TIME_PICKER);
		listBox.addItem(TIME_PICKER_HOUR);
		listBox.addItem(TIME_PICKER_MINUTE);
		listBox.addItem(TIME_PICKER_SEC); */
		listBox.addItem(DATE_PICKER);
		/*listBox.addItem(DATETIME_PICKER);
		listBox.addItem(LOCATIONSELECTOR);
		listBox.addItem(NUMBERRANGE_SLIDER);
		listBox.addItem(STRINGRANGE_SLIDER); */
		listBox.addItem(NUM_SPINNER);
		listBox.addItem(LIST_SPINNER);
		/*listBox.addItem(MEDIA_UPLOAD);*/
		listBox.addItem(GROUPFIELD);
		listBox.addItem(GROUPFIELDRADIO);
		
		listBox.addChangeHandler(this);
		listBox.setStylePrimaryName("fieldShowcaseBasePanel");
		listBoxPanel.add(titleLabel);
		listBoxPanel.add(listBox);
		listBoxPanel.setCellVerticalAlignment(titleLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		basePanel.add(listBoxPanel);
		basePanel.add(innerPanel);
		basePanel.setSpacing(20);
		basePanel.setStylePrimaryName("fieldShowcaseBasePanel");
		return basePanel;
		
	}
	
	
	private Configuration getDateTimeFieldConfiguration(String modeSelection,String datetimefieldTimeonly, String modeTimeValue) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(DateTimeField.DATETIMEFIELD_MODE, modeSelection);
		configuration.setPropertyByName(DateTimeField.DATETIMEFIELD_TYPE, datetimefieldTimeonly);
		if(modeTimeValue!=null)
		  configuration.setPropertyByName(modeTimeValue, modeTimeValue);
		
		return configuration;
	}
	
	private Configuration getLocationSelectorConfForFalse() {
		Configuration configuration = new Configuration();
		
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE, "images/locationMarker1.png");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CURRENT_LOCATION_TEXTFIELD, "images/locationMarker1.png");
		configuration.setPropertyByName(TextFieldConstant.TF_PRIMARYCSS, "appops-TextField");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CHOOSE_LOCATION_BTN, "chooseLocationBtn");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_POPUPPANEL, "currentLocationField");
		configuration.setPropertyByName(LocationSelector.MAP_ZOOM, "8");
		configuration.setPropertyByName(LocationSelector.CHANGE_LOCATION_IMAGE_URL, "images/iconEdit.png");
		configuration.setPropertyByName(LocationSelector.DONE_SELECTION_IMAGE_URL, "images/iconTickBlackCircle.png");
		configuration.setPropertyByName(LocationSelector.MAP_WIDTH, "600px");
		configuration.setPropertyByName(LocationSelector.MAP_HEIGHT, "400px");
		
		return configuration;
	}
	
	private Configuration getLocationSelectorConf() {
		Configuration configuration = new Configuration();
		
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE, "images/locationMarker1.png");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CURRENT_LOCATION_TEXTFIELD, "images/locationMarker1.png");
		configuration.setPropertyByName(TextFieldConstant.TF_PRIMARYCSS, "appops-TextField");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CHOOSE_LOCATION_BTN, "chooseLocationBtn");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_POPUPPANEL, "currentLocationField");
		configuration.setPropertyByName(LocationSelector.MAP_ZOOM, "8");
		configuration.setPropertyByName(LocationSelector.CHANGE_LOCATION_IMAGE_URL, "images/iconEdit.png");
		configuration.setPropertyByName(LocationSelector.DONE_SELECTION_IMAGE_URL, "images/iconTickBlackCircle.png");
		configuration.setPropertyByName(LocationSelector.MAP_WIDTH, "300px");
		configuration.setPropertyByName(LocationSelector.MAP_HEIGHT, "200px");
		
		return configuration;
	}
	
	/**
	 * creates the configuration object for a {@link}LabelField
	 * @param allowWordWrap boolean true / false
	 * @param primaryCss String the primary css style name to be applied to the field as defined in the css file
	 * @param secondaryCss (optional) String the dependent css style name to be applied to the field as defined in the css file
	 * @param debugId (optional) String the debug id for the {@link}LabelField
	 * @return
	 */
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return configuration;
	}
	
	/**
	 * creates the configuration object for a {@link}TextField
	 * @param visibleLines int - the number of visibles lines ( 1 if textbox / passwordtextbox. For textarea > 1)
	 * @param readOnly boolean - true / false
	 * @param textFieldType - type of the textField ( textbox / passwordtextbox / textarea )
	 * @param primaryCss - String the primary css style name to be applied to the field as defined in the css file
	 * @param secondaryCss - (optional) String the dependent css style name to be applied to the field as defined in the css file
	 * @param debugId - (optional) String the debug id for the {@link}TextField
	 * @return
	 */
	private Configuration getTextFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_VISIBLELINES, visibleLines);
		configuration.setPropertyByName(TextFieldConstant.TF_READONLY, readOnly);
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
		configuration.setPropertyByName(TextFieldConstant.TF_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(TextFieldConstant.TF_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(TextFieldConstant.TF_SUGGESTION_STYLE, TextFieldConstant.SUGGESTIONSTYLE_INLINE);
		configuration.setPropertyByName(TextFieldConstant.TF_SUGGESTION_TEXT, "Enter field value");
		configuration.setPropertyByName(TextFieldConstant.VALIDATION_EVENT, TextFieldConstant.ICONIC_STYLE);
		configuration.setPropertyByName(TextFieldConstant.ICONIC_STYLE, TextFieldConstant.ICONICSTYLE_ICON_WITH_ERROR_MSG);
		configuration.setPropertyByName(TextFieldConstant.ERROR_MSG_POSITION, TextFieldConstant.SIDE);
		configuration.setPropertyByName(TextFieldConstant.TF_ERROR_TEXT, "Data entered is not valid..");
		configuration.setPropertyByName(TextFieldConstant.TF_MAXLENGTH, 10);
		return configuration;
	}
	
	private Configuration getNumericFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_VISIBLELINES, visibleLines);
		configuration.setPropertyByName(TextFieldConstant.TF_READONLY, readOnly);
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
		configuration.setPropertyByName(TextFieldConstant.TF_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(TextFieldConstant.TF_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(TextFieldConstant.TF_SUGGESTION_STYLE, TextFieldConstant.SUGGESTION_ON_TOP);
		configuration.setPropertyByName(TextFieldConstant.TF_SUGGESTION_TEXT, "Enter decimal number");
		configuration.setPropertyByName(TextFieldConstant.NUMFIELD_TYPE, TextFieldConstant.NUMFIELD_INT);
		configuration.setPropertyByName(TextFieldConstant.NUMFIELD_NEGATIVE, false);
		configuration.setPropertyByName(TextFieldConstant.DEC_PRECISION, 3);
		configuration.setPropertyByName(TextFieldConstant.ERROR_STYLE, TextFieldConstant.ONLY_MSG);
		configuration.setPropertyByName(TextFieldConstant.TF_ERROR_TEXT, "Invalid value");
		configuration.setPropertyByName(TextFieldConstant.TF_VALIDVALUE_TEXT, "OK");
		//configuration.setPropertyByName(TextFieldConstant.TEXTFIELD_MAXLENGTH, 10);
		
		return configuration;
	}
	
	private GroupField getCheckBoxGroupField(){
		
		GroupField groupField = new GroupField();
		
		Configuration groupFieldConfig = new Configuration();
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_TYPE,GroupFieldConstant.GFTYPE_SINGLE_SELECT);
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_ALIGNMENT,GroupFieldConstant.GF_ALIGN_HORIZONTAL);
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_LIMIT,3);
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_TYPE,GroupFieldConstant.GFTYPE_SINGLE_SELECT);
		
		ArrayList<String> listOfItems = new ArrayList<String>();
		listOfItems.add("chk1");
		listOfItems.add("chk2");
		listOfItems.add("chk3");
		listOfItems.add("chk4");
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_LIST_OF_ITEMS,listOfItems);
		
		Configuration childConfig1 = new Configuration();
		childConfig1.setPropertyByName(CheckBoxFieldConstant.CF_PRIMARYCSS, "appops-CheckBoxField");
		childConfig1.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "cssStyle");
		childConfig1.setPropertyByName(CheckBoxFieldConstant.CF_CHECKED, true);
		
		Configuration childConfig2 = new Configuration();
		childConfig2.setPropertyByName(CheckBoxFieldConstant.CF_PRIMARYCSS, "appops-CheckBoxField");
		childConfig2.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "configuration");
		
		Configuration childConfig3 = new Configuration();
		childConfig3.setPropertyByName(CheckBoxFieldConstant.CF_PRIMARYCSS, "appops-CheckBoxField");
		childConfig3.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "layout");
		
		Configuration childConfig4 = new Configuration();
		childConfig4.setPropertyByName(CheckBoxFieldConstant.CF_PRIMARYCSS, "appops-CheckBoxField");
		childConfig4.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "description");
		
		groupFieldConfig.setPropertyByName("chk1",childConfig1);
		groupFieldConfig.setPropertyByName("chk2",childConfig2);
		groupFieldConfig.setPropertyByName("chk3",childConfig3);
		groupFieldConfig.setPropertyByName("chk4",childConfig4);
		
		groupField.setConfiguration(groupFieldConfig);
		groupField.configure();
		try {
			groupField.create();
		} catch (AppOpsException e) {
		}
		
		return groupField;
	}

	private GroupField getRadioButtonGroupField(){
		
		GroupField groupField = new GroupField();
		
		Configuration groupFieldConfig = new Configuration();
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_TYPE,GroupFieldConstant.GFTYPE_MULTISELECT);
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_ALIGNMENT,GroupFieldConstant.GF_ALIGN_VERTICAL);
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_LIMIT,3);
		
		ArrayList<String> listOfItems = new ArrayList<String>();
		listOfItems.add("radio1");
		listOfItems.add("radio2");
		listOfItems.add("radio3");
		listOfItems.add("radio4");
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_LIST_OF_ITEMS,listOfItems);
		
		Configuration childConfig1 = new Configuration();
		childConfig1.setPropertyByName(CheckBoxFieldConstant.CF_PRIMARYCSS, "appops-CheckBoxField");
		childConfig1.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "cssStyle");
		childConfig1.setPropertyByName(CheckBoxFieldConstant.CF_CHECKED, true);
		
		Configuration childConfig2 = new Configuration();
		childConfig2.setPropertyByName(CheckBoxFieldConstant.CF_PRIMARYCSS, "appops-CheckBoxField");
		childConfig2.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "configuration");
		
		Configuration childConfig3 = new Configuration();
		childConfig3.setPropertyByName(CheckBoxFieldConstant.CF_PRIMARYCSS, "appops-CheckBoxField");
		childConfig3.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "layout");
		
		Configuration childConfig4 = new Configuration();
		childConfig4.setPropertyByName(CheckBoxFieldConstant.CF_PRIMARYCSS, "appops-CheckBoxField");
		childConfig4.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "description");
		
		groupFieldConfig.setPropertyByName("radio1",childConfig1);
		groupFieldConfig.setPropertyByName("radio2",childConfig2);
		groupFieldConfig.setPropertyByName("radio3",childConfig3);
		groupFieldConfig.setPropertyByName("radio4",childConfig4);
		
		groupField.setConfiguration(groupFieldConfig);
		groupField.configure();
		try {
			groupField.create();
		} catch (AppOpsException e) {
		}
		
		return groupField;
	}
	
	private Configuration getTextAreaConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_VISIBLELINES, visibleLines);
		configuration.setPropertyByName(TextFieldConstant.TF_READONLY, readOnly);
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
		configuration.setPropertyByName(TextFieldConstant.TF_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(TextFieldConstant.TF_DEPENDENTCSS, secondaryCss);
		//configuration.setPropertyByName(TextFieldConstant.TEXTFIELD_SUGGESTION_STYLE, TextFieldConstant.INLINE_SUGGESTION);
		configuration.setPropertyByName(TextFieldConstant.TF_SUGGESTION_TEXT, "Enter field value");
		configuration.setPropertyByName(TextFieldConstant.VALIDATION_EVENT, TextFieldConstant.ICONIC_STYLE);
		//configuration.setPropertyByName(TextFieldConstant.ERROR_STYLE, TextFieldConstant.OUTLINE_ICONIC_ERROR_STYLE);
		//configuration.setPropertyByName(TextFieldConstant.ERROR_STYLE, TextFieldConstant.ERROR_TITLE_STYLE);
		//configuration.setPropertyByName(TextFieldConstant.ERROR_TITLE_STYLE, TextFieldConstant.ERROR_IN_BOTTOM);
		configuration.setPropertyByName(TextFieldConstant.TF_ERROR_TEXT, "Data entered is not valid..");
		configuration.setPropertyByName(TextFieldConstant.TF_MAXLENGTH, 10);
		configuration.setPropertyByName(TextFieldConstant.TF_CHARWIDTH, 70);
		
		return configuration;
	}
	
	private Configuration getEmailFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_VISIBLELINES, visibleLines);
		configuration.setPropertyByName(TextFieldConstant.TF_READONLY, readOnly);
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
		configuration.setPropertyByName(TextFieldConstant.TF_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(TextFieldConstant.TF_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(TextFieldConstant.TF_SUGGESTION_STYLE, TextFieldConstant.SUGGESTION_IN_BOTTOM);
		configuration.setPropertyByName(TextFieldConstant.TF_SUGGESTION_TEXT, "Enter email");
		//configuration.setPropertyByName(TextFieldConstant.ERROR_STYLE, TextFieldConstant.INLINE_ICONIC_ERROR_STYLE);
		configuration.setPropertyByName(TextFieldConstant.VALIDATION_EVENT, TextFieldConstant.ONLY_MSG);
		//configuration.setPropertyByName(TextFieldConstant.ERROR_STYLE, TextFieldConstant.ERROR_TITLE_STYLE);
		//configuration.setPropertyByName(TextFieldConstant.ERROR_TITLE_STYLE, TextFieldConstant.ERROR_IN_BOTTOM);
		configuration.setPropertyByName(TextFieldConstant.TF_ERROR_TEXT, "Data entered is not valid..");
		configuration.setPropertyByName(TextFieldConstant.VALIDATION_EVENT, TextFieldConstant.VALIDATE_ON_CHANGE);
		
		return configuration;
	}
	
		
	private Configuration getLinkFieldConfiguration(String linkFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LinkField.LINKFIELD_TYPE, linkFieldType);
		configuration.setPropertyByName(LinkField.LINKFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LinkField.LINKFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LinkField.LINKFIELD_DEBUGID, debugId);
		return configuration;
	}
	
	private Configuration getStateFieldConfiguration(String stateFieldType, String qname, String operationName, String displayText) {

		Configuration configuration = new Configuration();
		configuration.setPropertyByName(StateField.STATEFIELD_MODE, stateFieldType);
		configuration.setPropertyByName(StateField.STATEFIELD_QUERY, qname);
		configuration.setPropertyByName(StateField.STATEFIELD_OPERATION, operationName);
		configuration.setPropertyByName(StateField.STATEFIELD_PROPERTY_TO_DISPLAY, displayText);
		return configuration;
	}
	
	public Configuration getCheckboxFieldConfiguration(String text) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, text);
		return configuration;
	}
	
	protected Configuration getSpinnerFieldConfiguration(String spinnerfieldMode) {
		Configuration config = new Configuration();
		//config.setPropertyByName(SpinnerField.SPINNERFIELDMODE, spinnerfieldMode);
		return config;
	}
	
	protected Configuration getNumberFieldConfiguration() {
		Configuration config = new Configuration();
		return config;
	}
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		switch (eventType) {
		case FieldEvent.EDITFAIL: {
			numberfieldErrorLabel.setVisible(true);
			break;
		}
		case FieldEvent.EDITSUCCESS: {
			numberfieldErrorLabel.setVisible(false);
			break;
		}
		default:
			break;
		}
	}

	@Override
	public void onChange(ChangeEvent event) {
		int index = listBox.getSelectedIndex();
		String fieldName = listBox.getValue(index);
		initializeField(fieldName);
	}

	private void initializeField(String fieldName) {
		
		try {
			innerPanel.clear();
			if(fieldName.equals(TEXTBOX)) {
				TextField textFieldTB = new TextField();
				textFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, "appops-TextField", null, null));
				textFieldTB.configure();
				textFieldTB.create();
				innerPanel.add(textFieldTB);
				innerPanel.setCellHorizontalAlignment(textFieldTB,HorizontalPanel.ALIGN_CENTER);
				
			} else if(fieldName.equals(PASSWORDTEXTBOX)) {
				TextField textFieldPTB = new TextField();
				//textFieldPTB.setFieldValue("Password");
				textFieldPTB.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_PSWBOX, "appops-TextField", null, null));
				textFieldPTB.configure();
				textFieldPTB.create();
				innerPanel.add(textFieldPTB);
				innerPanel.setCellHorizontalAlignment(textFieldPTB,HorizontalPanel.ALIGN_CENTER);
				
			}else if(fieldName.equals(EMAILBOX)) {
				TextField textFieldTB = new TextField();
				textFieldTB.setConfiguration(getEmailFieldConfiguration(1, false, TextFieldConstant.TFTYPE_EMAILBOX, "appops-TextField", null, null));
				textFieldTB.configure();
				textFieldTB.create();
				innerPanel.add(textFieldTB);
				innerPanel.setCellHorizontalAlignment(textFieldTB,HorizontalPanel.ALIGN_CENTER);
				
			} else if(fieldName.equals(TEXTAREA)) {
				TextField textFieldTA = new TextField();
				textFieldTA.setConfiguration(getTextAreaConfiguration(10, false, TextFieldConstant.TFTTYPE_TXTAREA, null, null, null));
				textFieldTA.configure();
				textFieldTA.create();
				innerPanel.add(textFieldTA);
				innerPanel.setCellHorizontalAlignment(textFieldTA,HorizontalPanel.ALIGN_CENTER);

			}else if(fieldName.equals(NUMERICBOX)) {
				TextField textFieldTB = new TextField();
				//textFieldTB.setFieldValue("");
				textFieldTB.setConfiguration(getNumericFieldConfiguration(1, false, TextFieldConstant.TFTYPE_NUMERIC, "appops-TextField", null, null));
				textFieldTB.configure();
				textFieldTB.create();
				innerPanel.add(textFieldTB);
				innerPanel.setCellHorizontalAlignment(textFieldTB,HorizontalPanel.ALIGN_CENTER);
				
			}else if(fieldName.equals(GROUPFIELD)) {
				GroupField groupField = getCheckBoxGroupField();
				innerPanel.add(groupField);
				innerPanel.setCellHorizontalAlignment(groupField,HorizontalPanel.ALIGN_CENTER);
			} else if(fieldName.equals(GROUPFIELDRADIO)) {
				GroupField groupField = getRadioButtonGroupField();
				innerPanel.add(groupField);
				innerPanel.setCellHorizontalAlignment(groupField,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(CHECKBOXGROUPMULTISELECT)) {
				GroupCheckboxWidget multiSelectCheckbox = new GroupCheckboxWidget();
				innerPanel.add(multiSelectCheckbox);
				innerPanel.setCellHorizontalAlignment(multiSelectCheckbox,HorizontalPanel.ALIGN_CENTER);
			} else if(fieldName.equals(CHECKBOXFIELD)) {
				CheckboxWidget checkbox = new CheckboxWidget();
				innerPanel.add(checkbox);
			} else if(fieldName.equals(STATEFIELD)) {
				StateField stateField = new StateField();
				Configuration stateFieldConfig = getStateFieldConfiguration(StateField.STATEFIELDMODE_SUGGESTIVE, "getSpaceTypesWithName", "spacemanagement.SpaceManagementService.getEntityList", SpaceTypeConstants.NAME);
				stateField.setConfiguration(stateFieldConfig);
				stateField.create();
				innerPanel.add(stateField);
				innerPanel.setCellHorizontalAlignment(stateField,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(TIME_PICKER)) {
				DateTimeField dateTimeField = new DateTimeField();
				dateTimeField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_VIEW,DateTimeField.DATETIMEFIELD_TIMEONLY,DateTimeField.Full_Time));
				dateTimeField.create();
				innerPanel.add(dateTimeField);
				innerPanel.setCellHorizontalAlignment(dateTimeField,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(TIME_PICKER_HOUR)) {
				DateTimeField dateTimeShortHoursField = new DateTimeField();
				dateTimeShortHoursField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_VIEW,DateTimeField.DATETIMEFIELD_TIMEONLY,DateTimeField.SHORT_HOURS));
				dateTimeShortHoursField.create();
				innerPanel.add(dateTimeShortHoursField);
				innerPanel.setCellHorizontalAlignment(dateTimeShortHoursField,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(TIME_PICKER_MINUTE)) {
				DateTimeField dateTimeShortMinuteField = new DateTimeField();
				dateTimeShortMinuteField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_VIEW,DateTimeField.DATETIMEFIELD_TIMEONLY,DateTimeField.SHORT_MINUTE));
				dateTimeShortMinuteField.create();
				innerPanel.add(dateTimeShortMinuteField);
				innerPanel.setCellHorizontalAlignment(dateTimeShortMinuteField,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(TIME_PICKER_SEC)) {
				DateTimeField dateTimeShortSecField = new DateTimeField();
				dateTimeShortSecField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_VIEW,DateTimeField.DATETIMEFIELD_TIMEONLY,DateTimeField.SHORT_SECONDS));
				dateTimeShortSecField.create();
				innerPanel.add(dateTimeShortSecField);
				innerPanel.setCellHorizontalAlignment(dateTimeShortSecField,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(DATE_PICKER)) {

				DatePickerField dtPicker = new DatePickerField();
				dtPicker.create();
				innerPanel.add(dtPicker);
				innerPanel.setCellHorizontalAlignment(dtPicker,HorizontalPanel.ALIGN_CENTER);
			} else if(fieldName.equals(DATETIME_PICKER)) {
				DateTimeField dateTimeOnlyField = new DateTimeField();
				dateTimeOnlyField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_VIEW,DateTimeField.DATETIMEFIELD_DATETIMEONLY,null));
				dateTimeOnlyField.create();
				innerPanel.add(dateTimeOnlyField);
				innerPanel.setCellHorizontalAlignment(dateTimeOnlyField,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(LOCATIONSELECTOR)) {
				if (Geolocation.isSupported()) {
					Geolocation.getGeolocation().getCurrentPosition(new PositionCallback() {
						
						public void onSuccess(Position position) {
							
							Coordinates coords = position.getCoords();
							LatLng latLng = new LatLng(coords.getLatitude(), coords.getLongitude());
											
							LocationHomeSelector homeSelector = new LocationHomeSelector();
							homeSelector.setCoords(coords);
							homeSelector.createUi();
							innerPanel.add(homeSelector);
							innerPanel.setCellHorizontalAlignment(homeSelector,HorizontalPanel.ALIGN_CENTER);

						}

						@Override
						public void onFailure(com.google.code.gwt.geolocation.client.PositionError error) {
							System.out.println(" "+error.getMessage());

						}
					});
				}
			} else if(fieldName.equals(NUMBERRANGE_SLIDER)) {
				NumericRangeSliderFieldComponent numericRangeSliderFieldComponent = new NumericRangeSliderFieldComponent();
				numericRangeSliderFieldComponent.creatUI();
				innerPanel.setWidth("100%");
				innerPanel.add(numericRangeSliderFieldComponent);
				innerPanel.setCellHorizontalAlignment(numericRangeSliderFieldComponent, HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(STRINGRANGE_SLIDER)) {
				StringRangeSliderFieldComponent stringRangeSliderFieldComponent = new StringRangeSliderFieldComponent();
				stringRangeSliderFieldComponent.creatUI();
				innerPanel.setWidth("100%");
				innerPanel.add(stringRangeSliderFieldComponent);
				innerPanel.setCellHorizontalAlignment(stringRangeSliderFieldComponent, HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(NUM_SPINNER)) {
				Configuration configuration = new Configuration();
				configuration.setPropertyByName(SpinnerFieldConstant.SP_STEP, 3);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_UNIT, "%");
				configuration.setPropertyByName(SpinnerFieldConstant.SP_MAXVAL, 23F);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_MINVAL, -3F);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_CIRCULAR, true);
				configuration.setPropertyByName(SpinnerFieldConstant.BF_DEFVAL, 3F);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_TYPE, SpinnerFieldConstant.SP_TYPENUMERIC);
				configuration.setPropertyByName(SpinnerFieldConstant.BF_ERRPOS, SpinnerFieldConstant.BF_ERRTOP);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_DECPRECISION, 0);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_ALLOWDEC, false);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_VALIDATEONCHANGE, false);
				
				SpinnerField valueSpinner = new SpinnerField();
				valueSpinner.setConfiguration(configuration);
				valueSpinner.configure();
				valueSpinner.create();
				innerPanel.add(valueSpinner);
				innerPanel.setCellHorizontalAlignment(valueSpinner,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(LIST_SPINNER)) {
				ArrayList<String> days = new ArrayList<String>();
				days.add("Sunday");
				days.add("Monday");
				days.add("Tuesday");
				days.add("Wednesday");
				days.add("Thursday");
				days.add("Friday");
				days.add("Saturday");
				
				Configuration configuration = new Configuration();
				configuration.setPropertyByName(SpinnerFieldConstant.SP_TYPE, SpinnerFieldConstant.SP_TYPELIST);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_VALUELIST, days);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_VALUEIDX, 0);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_CIRCULAR, true);
				
				SpinnerField listSpinner = new SpinnerField();
				listSpinner.setConfiguration(configuration);
				listSpinner.configure();
				listSpinner.create();
				innerPanel.add(listSpinner);
				innerPanel.setCellHorizontalAlignment(listSpinner,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(NUMBERFIELD)) {
				NumberField numberField = new NumberField();
				numberField.setFieldValue("3");
				Configuration numberConfig = getNumberFieldConfiguration();
				numberField.setConfiguration(numberConfig);
				VerticalPanel numberFieldPanel = new VerticalPanel();
				numberfieldErrorLabel = new Label("Only Numbers and not the characters are allowed");
				numberFieldPanel.add(numberField);
				numberFieldPanel.add(numberfieldErrorLabel);
				numberfieldErrorLabel.setVisible(false);
				numberfieldErrorLabel.setStylePrimaryName("errorLabel");
				numberField.create();
				innerPanel.add(numberFieldPanel);

			} else if(fieldName.equals(MEDIA_UPLOAD)) {

				innerPanel.add(loaderImage);
				innerPanel.setCellHorizontalAlignment(loaderImage,HorizontalPanel.ALIGN_CENTER);
				loaderImage.setVisible(true);	
				addMediaUploaderField();
			}
			
			componentHolder.setPackageName(getPackageNameOfSelectedField(fieldName));
			
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
	}

	public MediaAttachWidget createMediaField() {
		MediaAttachWidget mediaWidget = new WebMediaAttachWidget();
		mediaWidget.isFadeUpEffect(false);
		mediaWidget.createUi();
		mediaWidget.setVisible(true);
		mediaWidget.setWidth("100%");
		mediaWidget.createAttachmentUi();
		mediaWidget.isMediaImageVisible(false);
		mediaWidget.expand();
		return mediaWidget;
	}
	
	private void addMediaUploaderField() {
		Map parameters = new HashMap();
		parameters.put("emailId", "nitish@ensarm.com");
		parameters.put("password", "nitish123");
		
		StandardAction action = new StandardAction(EntityList.class, "useraccount.LoginService.validateUser", parameters);
		
		DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
		DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);

		ResponseActionContext actionContext = new ResponseActionContext();
		actionContext.setEmbeddedAction(action);
		
		dispatch.executeContextAction(actionContext, new AsyncCallback<Result>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result result) {
				innerPanel.clear();
				Label mediaUploadLabel = new Label("Media Uploader");
				MediaAttachWidget mediaAttachWidget = createMediaField();
				innerPanel.add(mediaAttachWidget);
				innerPanel.setCellHorizontalAlignment(mediaAttachWidget,HorizontalPanel.ALIGN_CENTER);
			}
		});
	}
	
	private String getPackageNameOfSelectedField(String fieldName){
				
			if(fieldName.equals(TEXTBOX) || fieldName.equals(PASSWORDTEXTBOX) || fieldName.equals(EMAILBOX) || 	fieldName.equals(TEXTAREA) || fieldName.equals(NUMERICBOX)	) {
				return TextField.class.getName();
			} else if(fieldName.equals(GROUPFIELD) || fieldName.equals(GROUPFIELDRADIO)) {
				return GroupField.class.getName();
			}else if(fieldName.equals(STATEFIELD)) {
				return StateField.class.getName();
			} else if(fieldName.equals(TIME_PICKER) || fieldName.equals(TIME_PICKER_HOUR) || fieldName.equals(TIME_PICKER_MINUTE) || fieldName.equals(TIME_PICKER_SEC) || fieldName.equals(DATETIME_PICKER)) {
				return DateTimeField.class.getName();
			} else if(fieldName.equals(DATE_PICKER)) {
				//return DatePickerField.class.getName();
			} else if(fieldName.equals(LOCATIONSELECTOR)) {
				return LocationHomeSelector.class.getName();
			} else if(fieldName.equals(NUMBERRANGE_SLIDER)) {
				return NumericRangeSliderFieldComponent.class.getName();
			} else if(fieldName.equals(STRINGRANGE_SLIDER)) {
				return StringRangeSliderFieldComponent.class.getName();
			} else if(fieldName.equals(NUM_SPINNER)) {
				return SpinnerField.class.getName();
			} else if(fieldName.equals(LIST_SPINNER)) {
				return SpinnerField.class.getName();
			} else if(fieldName.equals(NUMBERFIELD)) {
				return NumberField.class.getName();
			} else if(fieldName.equals(MEDIA_UPLOAD)) {
				return MediaAttachWidget.class.getName();
			}
			
		return null;
		
	}
	
}