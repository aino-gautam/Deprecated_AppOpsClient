package in.appops.showcase.web.gwt.fields.client;

import in.appops.client.common.components.MediaAttachWidget;
import in.appops.client.common.components.WebMediaAttachWidget;
import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.CheckboxField.CheckBoxFieldConstant;
import in.appops.client.common.config.field.GroupField;
import in.appops.client.common.config.field.GroupField.GroupFieldConstant;
import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.LinkField;
import in.appops.client.common.config.field.LinkField.LinkFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.config.field.LocationSelectorField;
import in.appops.client.common.config.field.LocationSelectorField.LocationSelectorFieldConstant;
import in.appops.client.common.config.field.RadioButtonField.RadionButtonFieldConstant;
import in.appops.client.common.config.field.StateField;
import in.appops.client.common.config.field.StateField.StateFieldConstant;
import in.appops.client.common.config.field.date.DatePickerField;
import in.appops.client.common.config.field.date.DatePickerField.DatePickerConstant;
import in.appops.client.common.config.field.rangeslider.RangeSliderField;
import in.appops.client.common.config.field.rangeslider.RangeSliderField.RangeSliderFieldConstant;
import in.appops.client.common.config.field.spinner.SpinnerField;
import in.appops.client.common.config.field.spinner.SpinnerField.SpinnerFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.DateTimeField;
import in.appops.client.common.fields.LocationSelector;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.client.common.fields.slider.NumericRangeSliderFieldComponent;
import in.appops.client.common.fields.slider.StringRangeSliderFieldComponent;
import in.appops.client.common.fields.slider.field.NumericRangeSliderField;
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
	private LocationSelectorField locationField;
	
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
	public static final String NUMBERRANGE_SLIDER = "NumericRangeSlider";
	public static final String STRINGRANGE_SLIDER = "StringRangeSlider";
	public static final String NUM_SPINNER = "Numeric Spinner";
	public static final String LIST_SPINNER = "List Spinner";
	public static final String NUMBERFIELD = "NumberField";
	public static final String MEDIA_UPLOAD = "Media Uploader";
	public static final String GROUPFIELD = "Group Field (Check Box)";
	public static final String GROUPFIELDRADIO = "Group Field (Radio Button)";
	public static final String LABELFIELD = "LabelField";
	public static final String LINKFIELDHYPERLINK = "LinkField(HyperLink)";
	public static final String LINKFIELDANCHOR = "LinkField(Anchor)";
	public static final String BUTTONFIELD = "ButtonField";
	public static final String IMAGEFIELD = "ImageField";
	public static final String LISTBOX = "ListBox(Static list)";
	public static final String LOCATIONSELECTOR = "Location Selector";
	
	
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
		
		listBox.addItem(TIME_PICKER);
		listBox.addItem(TIME_PICKER_HOUR);
		listBox.addItem(TIME_PICKER_MINUTE);
		listBox.addItem(TIME_PICKER_SEC); */
		listBox.addItem(DATE_PICKER);
		/*listBox.addItem(DATETIME_PICKER);
		listBox.addItem(NUMBERRANGE_SLIDER);
		listBox.addItem(STRINGRANGE_SLIDER); */
		listBox.addItem(NUM_SPINNER);
		listBox.addItem(LIST_SPINNER);
		/*listBox.addItem(MEDIA_UPLOAD);*/
		listBox.addItem(GROUPFIELD);
		listBox.addItem(GROUPFIELDRADIO);
		listBox.addItem(LABELFIELD);
		listBox.addItem(LINKFIELDHYPERLINK);
		listBox.addItem(LINKFIELDANCHOR);
		listBox.addItem(BUTTONFIELD);
		listBox.addItem(IMAGEFIELD);
		listBox.addItem(LISTBOX);
		listBox.addItem(LOCATIONSELECTOR);
		listBox.addItem(NUMBERRANGE_SLIDER);
		listBox.addItem(STRINGRANGE_SLIDER); 
		listBox.addItem(STATEFIELD);
		
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
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, "appops-TextField");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CHOOSE_LOCATION_BTN, "chooseLocationBtn");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_POPUPPANEL, "currentLocationField");
		configuration.setPropertyByName(LocationSelector.MAP_ZOOM, "8");
		configuration.setPropertyByName(LocationSelector.CHANGE_LOCATION_IMAGE_URL, "images/iconEdit.png");
		configuration.setPropertyByName(LocationSelector.DONE_SELECTION_IMAGE_URL, "images/iconTickBlackCircle.png");
		configuration.setPropertyByName(LocationSelector.MAP_WIDTH, "600px");
		configuration.setPropertyByName(LocationSelector.MAP_HEIGHT, "400px");
		
		return configuration;
	}
	
	private Configuration getLocationSelectorConf(double latitude, double longitude) {
		
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_ZOOMLEVEL, 8);
		configuration.setPropertyByName(LocationSelectorFieldConstant.LOCFD_WIDTH, "400px");
		configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_HEIGHT, "200px");
		configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_LATITUDE, latitude);
		configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_LONGITUDE, longitude);
		configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_SHOWINPOPUP, true);
		configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_DONEBTN_CSS, "appops-Button");
		configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_LOCATION_IMG_CSS, "locationImage");
		configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_SEARCHBOX_PCLS, "locationSearchBox");
		configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_SEARCHBOX_DCLS, "fadeInRight");
		configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_ERRPOS, TextFieldConstant.BF_ERRINLINE);
		configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_INVALID_LOCNMSG, "Invalid location");
		configuration.setPropertyByName(LocationSelectorFieldConstant.SEARCHFD_EVENT, FieldEvent.WORDENTERED);
		configuration.setPropertyByName(LocationSelectorFieldConstant.DONEBTN_EVENT, FieldEvent.LOCATION_CHANGED);
		configuration.setPropertyByName(LocationSelectorFieldConstant.LOCN_IMG_EVENT, FieldEvent.SHOW_MAP_IN_POPUP);
		
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
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String displayText){
		
		
		Configuration conf = new Configuration();
		conf.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, allowWordWrap);
		conf.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
		conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		conf.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		
		return conf;
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
		configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, visibleLines);
		configuration.setPropertyByName(TextFieldConstant.BF_READONLY, readOnly);
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(TextFieldConstant.BF_DCLS, secondaryCss);
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter field value");
		configuration.setPropertyByName(TextFieldConstant.BF_VALIDATEONCHANGE, true);
		configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, TextFieldConstant.BF_SIDE);
		configuration.setPropertyByName(TextFieldConstant.TF_MAXLENGTH, 10);
		return configuration;
	}
	
	private Configuration getNumericFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, visibleLines);
		configuration.setPropertyByName(TextFieldConstant.BF_READONLY, readOnly);
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(TextFieldConstant.BF_DCLS, secondaryCss);
		//configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_TOP);
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter Number");
		configuration.setPropertyByName(TextFieldConstant.MINVALUE,0);
		configuration.setPropertyByName(TextFieldConstant.ALLOWDEC,true);
		configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, TextFieldConstant.BF_SIDE);
		
		return configuration;
	}
	
	private GroupField getCheckBoxGroupField(){
		
		GroupField groupField = new GroupField();
		
		Configuration groupFieldConfig = new Configuration();
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_TYPE,GroupFieldConstant.GFTYPE_MULTISELECT);
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_ALIGNMENT,GroupFieldConstant.GF_ALIGN_HORIZONTAL);
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_LIMIT,3);
		
		ArrayList<String> listOfItems = new ArrayList<String>();
		listOfItems.add("chk1");
		listOfItems.add("chk2");
		listOfItems.add("chk3");
		listOfItems.add("chk4");
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_LIST_OF_ITEMS,listOfItems);
		
		Configuration childConfig1 = new Configuration();
		childConfig1.setPropertyByName(CheckBoxFieldConstant.BF_PCLS, "appops-CheckBoxField");
		childConfig1.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "cssStyle");
		childConfig1.setPropertyByName(CheckBoxFieldConstant.CF_CHECKED, true);
		
		Configuration childConfig2 = new Configuration();
		childConfig2.setPropertyByName(CheckBoxFieldConstant.BF_PCLS, "appops-CheckBoxField");
		childConfig2.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "configuration");
		
		Configuration childConfig3 = new Configuration();
		childConfig3.setPropertyByName(CheckBoxFieldConstant.BF_PCLS, "appops-CheckBoxField");
		childConfig3.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "layout");
		
		Configuration childConfig4 = new Configuration();
		childConfig4.setPropertyByName(CheckBoxFieldConstant.BF_PCLS, "appops-CheckBoxField");
		childConfig4.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "description");
		
		groupFieldConfig.setPropertyByName("chk1",childConfig1);
		groupFieldConfig.setPropertyByName("chk2",childConfig2);
		groupFieldConfig.setPropertyByName("chk3",childConfig3);
		groupFieldConfig.setPropertyByName("chk4",childConfig4);
		
		groupField.setConfiguration(groupFieldConfig);
		groupField.configure();
		groupField.create();
		
		return groupField;
	}

	private GroupField getRadioButtonGroupField(){
		
		GroupField groupField = new GroupField();
		
		Configuration groupFieldConfig = new Configuration();
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_TYPE,GroupFieldConstant.GFTYPE_SINGLE_SELECT);
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_ALIGNMENT,GroupFieldConstant.GF_ALIGN_VERTICAL);
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_LIMIT,3);
		
		ArrayList<String> listOfItems = new ArrayList<String>();
		listOfItems.add("radio1");
		listOfItems.add("radio2");
		listOfItems.add("radio3");
		listOfItems.add("radio4");
		groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_LIST_OF_ITEMS,listOfItems);
		
		Configuration childConfig1 = new Configuration();
		childConfig1.setPropertyByName(RadionButtonFieldConstant.BF_PCLS, "appops-CheckBoxField");
		childConfig1.setPropertyByName(RadionButtonFieldConstant.RF_DISPLAYTEXT, "cssStyle");
		childConfig1.setPropertyByName(RadionButtonFieldConstant.RF_CHECKED, true);
		
		Configuration childConfig2 = new Configuration();
		childConfig2.setPropertyByName(RadionButtonFieldConstant.BF_PCLS, "appops-CheckBoxField");
		childConfig2.setPropertyByName(RadionButtonFieldConstant.RF_DISPLAYTEXT, "configuration");
		
		Configuration childConfig3 = new Configuration();
		childConfig3.setPropertyByName(RadionButtonFieldConstant.BF_PCLS, "appops-CheckBoxField");
		childConfig3.setPropertyByName(RadionButtonFieldConstant.RF_DISPLAYTEXT, "layout");
		
		Configuration childConfig4 = new Configuration();
		childConfig4.setPropertyByName(RadionButtonFieldConstant.BF_PCLS, "appops-CheckBoxField");
		childConfig4.setPropertyByName(RadionButtonFieldConstant.RF_DISPLAYTEXT, "description");
		
		groupFieldConfig.setPropertyByName("radio1",childConfig1);
		groupFieldConfig.setPropertyByName("radio2",childConfig2);
		groupFieldConfig.setPropertyByName("radio3",childConfig3);
		groupFieldConfig.setPropertyByName("radio4",childConfig4);
		
		groupField.setConfiguration(groupFieldConfig);
		groupField.configure();
		groupField.create();
		
		return groupField;
	}
	
	private Configuration getTextAreaConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, visibleLines);
		configuration.setPropertyByName(TextFieldConstant.BF_READONLY, readOnly);
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(TextFieldConstant.BF_DCLS, secondaryCss);
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter field value");
		configuration.setPropertyByName(TextFieldConstant.BF_VALIDATEONCHANGE, true);
		configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, TextFieldConstant.BF_SIDE);
		configuration.setPropertyByName(TextFieldConstant.TF_MAXLENGTH, 10);
		configuration.setPropertyByName(TextFieldConstant.TF_CHARWIDTH, 70);
		
		return configuration;
	}
	
	private Configuration getEmailFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, visibleLines);
		configuration.setPropertyByName(TextFieldConstant.BF_READONLY, readOnly);
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(TextFieldConstant.BF_DCLS, secondaryCss);
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter email");
		configuration.setPropertyByName(TextFieldConstant.BF_SHOW_VALID_FIELD,true);
		configuration.setPropertyByName(TextFieldConstant.BF_VALIDATEONCHANGE, true);
		configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, TextFieldConstant.BF_SIDE);
		
		return configuration;
	}
	
		
	private Configuration getHyperLinkConfiguration(){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LinkFieldConstant.LNK_TYPE, LinkFieldConstant.LNKTYPE_HYPERLINK);
		configuration.setPropertyByName(LinkFieldConstant.BF_PCLS,"appops-LinkField");
		configuration.setPropertyByName(LinkFieldConstant.LNK_DISPLAYTEXT, "Hyperlink");
		configuration.setPropertyByName(LinkFieldConstant.LNK_HISTORYTOKEN, "historyToken");
		return configuration;
	}
	
	private Configuration getAnchorConfiguration(){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LinkFieldConstant.LNK_TYPE, LinkFieldConstant.LNKTYPE_ANCHOR);
		configuration.setPropertyByName(LinkFieldConstant.BF_PCLS,"appops-LinkField");
		configuration.setPropertyByName(LinkFieldConstant.LNK_DISPLAYTEXT, "Anchor");
		//configuration.setPropertyByName(LinkFieldConstant.LNK_TARGET_FRAME, "_self");
		configuration.setPropertyByName(LinkFieldConstant.LNK_TITLE, "Anchor with configurations");
		//configuration.setPropertyByName(LinkFieldConstant.LNK_HREF, componentHolder.getHelpHtml(getPackageNameOfSelectedField(LINKFIELDANCHOR)));
		return configuration;
	}
	
	private Configuration getButtonConfiguration(){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Configure");
		configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,"appops-Button");
		configuration.setPropertyByName(ButtonFieldConstant.BTNFD_TITLE, "Configurable Button");
		return configuration;
	}
	
	private Configuration getImageConfiguration(){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, "images/test2.jpg");
		configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,"showcaseImage");
		configuration.setPropertyByName(ButtonFieldConstant.BTNFD_TITLE, "Configurable Image");
		return configuration;
	}
	
	private Configuration getStaticListBoxConfiguration() {
		Configuration configuration = new Configuration();
		//configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_VISIBLE_ITEM_CNT, 3);
		ArrayList<String> items = new ArrayList<String>();
		items.add("Private access");
		items.add("Public");
		items.add("Restricted");
		items.add("Me");
		
		configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,items);
		
		return configuration;
	}

	
	
	private Configuration getStateFieldConfiguration() {

		Configuration configuration = new Configuration();
		configuration.setPropertyByName(StateFieldConstant.IS_STATIC_BOX, true);
		
		ArrayList<String> days = new ArrayList<String>();
		days.add("Sunday");
		days.add("Monday");
		days.add("Tuesday");
		days.add("Wednesday");
		days.add("Thursday");
		days.add("Friday");
		days.add("Saturday");
		configuration.setPropertyByName(StateFieldConstant.ITEMS_LIST, days);
		
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
			} else if(fieldName.equals(LABELFIELD)) {
				LabelField labelField = new LabelField();
				labelField.setConfiguration(getLabelFieldConfiguration(true,"appops-LabelField",null,"Label with configuration"));
				labelField.configure();
				labelField.create();
				innerPanel.add(labelField);
				innerPanel.setCellHorizontalAlignment(labelField,HorizontalPanel.ALIGN_CENTER);
			} else if(fieldName.equals(LINKFIELDHYPERLINK)) {
				LinkField hyperLinkField = new LinkField();
				hyperLinkField.setConfiguration(getHyperLinkConfiguration());
				hyperLinkField.configure();
				hyperLinkField.create();
				innerPanel.add(hyperLinkField);
				innerPanel.setCellHorizontalAlignment(hyperLinkField,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(LINKFIELDANCHOR)) {
				LinkField anchorLinkField = new LinkField();
				anchorLinkField.setConfiguration(getAnchorConfiguration());
				anchorLinkField.configure();
				anchorLinkField.create();
				innerPanel.add(anchorLinkField);
				innerPanel.setCellHorizontalAlignment(anchorLinkField,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(BUTTONFIELD)) {
				ButtonField btnField = new ButtonField();
				btnField.setConfiguration(getButtonConfiguration());
				btnField.configure();
				btnField.create();
				innerPanel.add(btnField);
				innerPanel.setCellHorizontalAlignment(btnField,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(IMAGEFIELD)) {
				ImageField imageField = new ImageField();
				imageField.setConfiguration(getImageConfiguration());
				imageField.configure();
				imageField.create();
				innerPanel.add(imageField);
				innerPanel.setCellHorizontalAlignment(imageField,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(LISTBOX)) {
				ListBoxField staticListBox = new ListBoxField();
				staticListBox.setConfiguration(getStaticListBoxConfiguration());
				staticListBox.configure();
				staticListBox.create();
				innerPanel.add(staticListBox);
				innerPanel.setCellHorizontalAlignment(staticListBox,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(STATEFIELD)) {
				StateField stateField = new StateField();
				Configuration stateFieldConfig = getStateFieldConfiguration();
				stateField.setConfiguration(stateFieldConfig);
				stateField.configure();
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
				Configuration configuration = new Configuration();
				configuration.setPropertyByName(DatePickerConstant.BF_DEFVAL, "01.07.2013");
				configuration.setPropertyByName(DatePickerConstant.DP_MAXDATE, "03.08.2013");
				configuration.setPropertyByName(DatePickerConstant.DP_MINDATE, "05.06.2013");
				configuration.setPropertyByName(DatePickerConstant.DP_FORMAT, "dd.MM.yyyy");
				configuration.setPropertyByName(DatePickerConstant.DP_ALLOWBLNK, false);
				configuration.setPropertyByName(DatePickerConstant.BF_ERRPOS, DatePickerConstant.BF_BOTTOM);
				
				dtPicker.setConfiguration(configuration);
				dtPicker.configure();
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
							//LatLng latLng = new LatLng(coords.getLatitude(), coords.getLongitude());
								if(locationField==null)	{		
									locationField = new LocationSelectorField();
									locationField.setConfiguration(getLocationSelectorConf(coords.getLatitude(), coords.getLongitude()));
									locationField.configure();
									locationField.create();
								}
							innerPanel.add(locationField);
							innerPanel.setCellHorizontalAlignment(locationField,HorizontalPanel.ALIGN_CENTER);
						}

						@Override
						public void onFailure(com.google.code.gwt.geolocation.client.PositionError error) {
							System.out.println(" "+error.getMessage());

						}
					});
				}
			} else if(fieldName.equals(NUMBERRANGE_SLIDER)) {
				
				RangeSliderField rangeSliderField = new RangeSliderField();
				rangeSliderField.setConfiguration(getNumericSliderConf());
				rangeSliderField.configure();
				rangeSliderField.create();
							
				innerPanel.setWidth("100%");
				innerPanel.add(rangeSliderField);
				innerPanel.setCellHorizontalAlignment(rangeSliderField, HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(STRINGRANGE_SLIDER)) {
				RangeSliderField rangeSliderField = new RangeSliderField();
				rangeSliderField.setConfiguration(getStringSliderConf());
				rangeSliderField.configure();
				rangeSliderField.create();
							
				innerPanel.setWidth("100%");
				innerPanel.add(rangeSliderField);
				innerPanel.setCellHorizontalAlignment(rangeSliderField, HorizontalPanel.ALIGN_CENTER);

			}  else if(fieldName.equals(NUM_SPINNER)) {
				Configuration configuration = new Configuration();
				configuration.setPropertyByName(SpinnerFieldConstant.SP_STEP, 3);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_UNIT, "%");
				configuration.setPropertyByName(SpinnerFieldConstant.SP_MAXVAL, 23F);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_MINVAL, -3F);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_CIRCULAR, true);
				configuration.setPropertyByName(SpinnerFieldConstant.BF_DEFVAL, 3F);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_TYPE, SpinnerFieldConstant.SP_TYPENUMERIC);
				configuration.setPropertyByName(SpinnerFieldConstant.BF_ERRPOS, SpinnerFieldConstant.BF_BOTTOM);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_DECPRECISION, 0);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_ALLOWDEC, false);
				configuration.setPropertyByName(SpinnerFieldConstant.BF_VALIDATEONCHANGE, true);
				
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
	
	private Configuration getNumericSliderConf() {
		Configuration conf = new Configuration();
		conf.setPropertyByName(RangeSliderFieldConstant.SLIDER_MODE,RangeSliderFieldConstant.NUM_SLIDER);
		conf.setPropertyByName(RangeSliderFieldConstant.BF_PCLS,"sliderPanel");
		conf.setPropertyByName(RangeSliderFieldConstant.MINVAL,100d);
		conf.setPropertyByName(RangeSliderFieldConstant.MAXVAL,200d);
		conf.setPropertyByName(RangeSliderFieldConstant.STEPVAL,50d);
		return conf;
	}
	
	private Configuration getStringSliderConf() {
			
		ArrayList<String> optionList = new ArrayList<String>();
		optionList.add("Me");
		optionList.add("Public");
		optionList.add("Private");
		optionList.add("Restricted");
		
		Configuration conf = new Configuration();
		conf.setPropertyByName(RangeSliderFieldConstant.SLIDER_MODE,RangeSliderFieldConstant.STRING_SLIDER);
		conf.setPropertyByName(RangeSliderFieldConstant.BF_PCLS,"sliderPanel");
		conf.setPropertyByName(RangeSliderFieldConstant.ITEMS_LIST,optionList);
		return conf;
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
				return DatePickerField.class.getName();
			} else if(fieldName.equals(NUMBERRANGE_SLIDER)) {
				return RangeSliderField.class.getName();
			} else if(fieldName.equals(STRINGRANGE_SLIDER)) {
				return RangeSliderField.class.getName();
			} else if(fieldName.equals(NUM_SPINNER)) {
				return SpinnerField.class.getName();
			} else if(fieldName.equals(LIST_SPINNER)) {
				return SpinnerField.class.getName();
			} else if(fieldName.equals(MEDIA_UPLOAD)) {
				return MediaAttachWidget.class.getName();
			}else if(fieldName.equals(LABELFIELD)) {
				return LabelField.class.getName();
			}else if(fieldName.equals(LINKFIELDHYPERLINK) || fieldName.equals(LINKFIELDANCHOR)) {
				return LinkField.class.getName();
			}else if(fieldName.equals(IMAGEFIELD)) {
				return ImageField.class.getName();
			}else if(fieldName.equals(LISTBOX)) {
				return ListBoxField.class.getName();
			}else if(fieldName.equals(BUTTONFIELD)) {
				return ButtonField.class.getName();
			}else if(fieldName.equals(LOCATIONSELECTOR)) {
				return LocationSelectorField.class.getName();
			}
			
		return null;
		
	}
	
}