package in.appops.showcase.web.gwt.fields.client;

import in.appops.client.common.components.MediaAttachWidget;
import in.appops.client.common.components.WebMediaAttachWidget;
import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.CheckboxField.CheckBoxFieldConstant;
import in.appops.client.common.config.field.Field;
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
import in.appops.client.common.config.field.ToggleImageField;
import in.appops.client.common.config.field.ToggleImageField.ToggleImageFieldConstant;
import in.appops.client.common.config.field.date.DateLabelField;
import in.appops.client.common.config.field.date.DateLabelField.DateLabelFieldConstant;
import in.appops.client.common.config.field.date.DatePickerField;
import in.appops.client.common.config.field.date.DatePickerField.DatePickerConstant;
import in.appops.client.common.config.field.date.DateTimePickerField;
import in.appops.client.common.config.field.date.DateTimePickerField.DateTimePickerFieldConstant;
import in.appops.client.common.config.field.date.TimePickerField;
import in.appops.client.common.config.field.date.TimePickerField.TimePickerFieldConstant;
import in.appops.client.common.config.field.intellithought.IntelliThoughtField;
import in.appops.client.common.config.field.intellithought.IntelliThoughtField.IntelliThoughtFieldConstant;
import in.appops.client.common.config.field.media.MediaField;
import in.appops.client.common.config.field.media.MediaField.MediaFieldConstant;
import in.appops.client.common.config.field.querythought.QueryThoughtField;
import in.appops.client.common.config.field.querythought.QueryThoughtField.QueryThoughtFieldConstant;
import in.appops.client.common.config.field.rangeslider.RangeSliderField;
import in.appops.client.common.config.field.rangeslider.RangeSliderField.RangeSliderFieldConstant;
import in.appops.client.common.config.field.spinner.SpinnerField;
import in.appops.client.common.config.field.spinner.SpinnerField.SpinnerFieldConstant;
import in.appops.client.common.config.field.textfield.TextField;
import in.appops.client.common.config.field.textfield.TextField.TextFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.DateTimeField;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.ResponseActionContext;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.schema.SchemaDefinition;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.shared.Service;
import in.appops.platform.core.util.EntityList;
import in.appops.showcase.web.gwt.holder.client.ShowcaseComponentHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private Field selectedField = null;
	
	public static final String TEXTBOX = "Text Box";
	public static final String PASSWORDTEXTBOX = "Password Textbox";
	public static final String EMAILBOX = "Email Textbox";
	public static final String TEXTAREA = "Text Area";
	public static final String NUMERICBOX = "Numeric Textbox";
	public static final String CHECKBOXGROUPMULTISELECT = "CheckboxGroupField";
	public static final String CHECKBOXFIELD = "CheckboxField";
	public static final String STATEFIELD_WITH_QUERY = "StateField(Query Result)";
	public static final String STATEFIELD_STATIC = "StateField(Static)";
	public static final String TIME_PICKER = "Time Picker";
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
	public static final String  TOGGLEIMAGEFIELD= "ToggleImageField";
	public static final String LISTBOX_STATIC = "ListBox(Static list)";
	public static final String LISTBOX_WITH_QUERY = "ListBox(Query Result)";
	public static final String LOCATIONSELECTOR = "Location Selector";
	public static final String HTMLEDITOR = "html Editor";
	public static final String  DATELABEL_WITH_TIMESTAMP= "DateLable(TimeStamp)";
	public static final String  DATELABEL_WITH_DATETIME= "DateLable(DateTime)";
	public static final String  INTELLITHOUGHTFIELD= "Intellithought";
	public static final String  QUERYTHOUGHTFIELD= "QueryThought";
	
	private Logger logger = Logger.getLogger(getClass().getName());

	
	private Image loaderImage;
	public FieldsShowCase() {
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}
	
	@Override
	public void onModuleLoad() {
		
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In onModuleLoad  method ");
			VerticalPanel basePanel = getFieldsShowcaseUi();
			componentHolder = new ShowcaseComponentHolder();
			componentHolder.add(basePanel,DockPanel.CENTER);
			RootPanel.get().add(componentHolder);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In onModuleLoad  method :"+e);

		}
	}
	
	private VerticalPanel getFieldsShowcaseUi(){
		VerticalPanel basePanel = null;
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getFieldsShowcaseUi  method ");
			loaderImage = new Image("images/opptinLoader.gif");
			loaderImage.setStylePrimaryName("appops-intelliThoughtActionImage");
			loaderImage.setVisible(false);
			
			basePanel = new VerticalPanel();
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
			
			listBox.addItem(NUM_SPINNER);
			listBox.addItem(LIST_SPINNER);
			
			listBox.addItem(GROUPFIELD);
			listBox.addItem(GROUPFIELDRADIO);
			
			listBox.addItem(LABELFIELD);
			listBox.addItem(LINKFIELDHYPERLINK);
			listBox.addItem(LINKFIELDANCHOR);
			listBox.addItem(BUTTONFIELD);
			listBox.addItem(IMAGEFIELD);
			listBox.addItem(TOGGLEIMAGEFIELD);
			
			listBox.addItem(LOCATIONSELECTOR);
			listBox.addItem(NUMBERRANGE_SLIDER);
			listBox.addItem(STRINGRANGE_SLIDER); 
			
			listBox.addItem(LISTBOX_STATIC);
			listBox.addItem(LISTBOX_WITH_QUERY);
			listBox.addItem(STATEFIELD_STATIC);
			listBox.addItem(STATEFIELD_WITH_QUERY);
			
			listBox.addItem(DATE_PICKER);
			listBox.addItem(TIME_PICKER);
			listBox.addItem(DATETIME_PICKER);
			
			listBox.addItem(HTMLEDITOR);
			listBox.addItem(MEDIA_UPLOAD);
			
			listBox.addItem(DATELABEL_WITH_TIMESTAMP);
			listBox.addItem(DATELABEL_WITH_DATETIME);
			listBox.addItem(INTELLITHOUGHTFIELD);
			listBox.addItem(QUERYTHOUGHTFIELD);
			
			listBox.addChangeHandler(this);
			listBox.setStylePrimaryName("fieldShowcaseBasePanel");
			listBoxPanel.add(titleLabel);
			listBoxPanel.add(listBox);
			listBoxPanel.setCellVerticalAlignment(titleLabel, HasVerticalAlignment.ALIGN_MIDDLE);
			basePanel.add(listBoxPanel);
			basePanel.add(innerPanel);
			basePanel.setSpacing(20);
			basePanel.setStylePrimaryName("fieldShowcaseBasePanel");
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getFieldsShowcaseUi  method :"+e);
		}
		return basePanel;
		
	}
	
	private Configuration getLocationSelectorConf(double latitude, double longitude) {
		
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getLocationSelectorConf  method ");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getLocationSelectorConf  method :"+e);
		}
		
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
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getLabelFieldConfiguration  method ");
			conf.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, allowWordWrap);
			conf.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
			conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
			conf.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getLabelFieldConfiguration  method :"+e);
		}
		
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
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getTextFieldConfiguration  method ");
			configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, visibleLines);
			configuration.setPropertyByName(TextFieldConstant.BF_READONLY, readOnly);
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, primaryCss);
			configuration.setPropertyByName(TextFieldConstant.BF_DCLS, secondaryCss);
			//configuration.setPropertyByName(TextFieldConstant.BF_ALLOWBLNK, false);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter field value");
			configuration.setPropertyByName(TextFieldConstant.BF_VALIDATEONCHANGE, true);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, TextFieldConstant.BF_SIDE);
		//	configuration.setPropertyByName(TextFieldConstant.TF_MAXLENGTH, 10);
			configuration.setPropertyByName(TextFieldConstant.BF_VALIDATEFIELD, true);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getTextFieldConfiguration  method :"+e);;
		}
		return configuration;
	}
	
	private Configuration getNumericFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getNumericFieldConfiguration  method ");
			configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, visibleLines);
			configuration.setPropertyByName(TextFieldConstant.BF_READONLY, readOnly);
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, primaryCss);
			configuration.setPropertyByName(TextFieldConstant.BF_DCLS, secondaryCss);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter Number");
		//	configuration.setPropertyByName(TextFieldConstant.MINVALUE,0);
			configuration.setPropertyByName(TextFieldConstant.ALLOWDEC,true);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, TextFieldConstant.BF_SIDE);
			configuration.setPropertyByName(TextFieldConstant.BF_VALIDATEFIELD, true);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getNumericFieldConfiguration  method :"+e);
		}
		
		return configuration;
	}
	
	private GroupField getCheckBoxGroupField(){
		
		GroupField groupField = new GroupField();
		
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getCheckBoxGroupField  method ");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getCheckBoxGroupField  method :"+e);
		}
		
		return groupField;
	}

	private GroupField getRadioButtonGroupField(){
		
		GroupField groupField = new GroupField();
		
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getRadioButtonGroupField  method ");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getRadioButtonGroupField  method :"+e);
		}
		
		return groupField;
	}
	
	private Configuration getTextAreaConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getTextAreaConfiguration  method ");
			configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, visibleLines);
			configuration.setPropertyByName(TextFieldConstant.BF_READONLY, readOnly);
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, primaryCss);
			configuration.setPropertyByName(TextFieldConstant.BF_DCLS, secondaryCss);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter field value");
			configuration.setPropertyByName(TextFieldConstant.BF_VALIDATEONCHANGE, true);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, TextFieldConstant.BF_SIDE);
			//configuration.setPropertyByName(TextFieldConstant.TF_MAXLENGTH, 10);
			configuration.setPropertyByName(TextFieldConstant.TF_CHARWIDTH, 70);
			configuration.setPropertyByName(TextFieldConstant.BF_VALIDATEFIELD, false);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getTextAreaConfiguration  method :"+e);
		}
		
		return configuration;
	}
	
	private Configuration getEmailFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getEmailFieldConfiguration  method ");
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
			configuration.setPropertyByName(TextFieldConstant.BF_VALIDATEFIELD, true);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getEmailFieldConfiguration  method :"+e);
		}
		
		return configuration;
	}
	
		
	private Configuration getHyperLinkConfiguration(){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getHyperLinkConfiguration  method ");
			configuration.setPropertyByName(LinkFieldConstant.LNK_TYPE, LinkFieldConstant.LNKTYPE_HYPERLINK);
			configuration.setPropertyByName(LinkFieldConstant.BF_PCLS,"appops-LinkField");
			configuration.setPropertyByName(LinkFieldConstant.LNK_DISPLAYTEXT, "Hyperlink");
			configuration.setPropertyByName(LinkFieldConstant.LNK_HISTORYTOKEN, "historyToken");
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getHyperLinkConfiguration  method :"+e);
		}
		return configuration;
	}
	
	private Configuration getAnchorConfiguration(){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getAnchorConfiguration  method ");
			configuration.setPropertyByName(LinkFieldConstant.LNK_TYPE, LinkFieldConstant.LNKTYPE_ANCHOR);
			configuration.setPropertyByName(LinkFieldConstant.BF_PCLS,"appops-LinkField");
			configuration.setPropertyByName(LinkFieldConstant.LNK_DISPLAYTEXT, "Anchor");
			//configuration.setPropertyByName(LinkFieldConstant.LNK_TARGET_FRAME, "_self");
			configuration.setPropertyByName(LinkFieldConstant.LNK_TITLE, "Anchor with configurations");
			//configuration.setPropertyByName(LinkFieldConstant.LNK_HREF, componentHolder.getHelpHtml(getPackageNameOfSelectedField(LINKFIELDANCHOR)));
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getAnchorConfiguration  method :"+e);
		}
		return configuration;
	}
	
	private Configuration getButtonConfiguration(){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getButtonConfiguration  method ");
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Configure");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,"appops-Button");
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_TITLE, "Configurable Button");
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getButtonConfiguration  method :"+e);
		}
		return configuration;
	}
	
	private Configuration getImageConfiguration(){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getImageConfiguration  method ");
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, "images/test2.jpg");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,"showcaseImage");
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_TITLE, "Configurable Image");
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getImageConfiguration  method :"+e);
		}
		return configuration;
	}
	
	private Configuration getToggleImageFieldConf(){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getToggleImageFieldConf  method ");
			configuration.setPropertyByName(ToggleImageFieldConstant.TIMGFD_UPSTATE_URL, "images/pinkHeart.jpg");
			configuration.setPropertyByName(ToggleImageFieldConstant.TIMGFD_UPSTATE_TITLE,"unlike");
			configuration.setPropertyByName(ToggleImageFieldConstant.TIMGFD_DWNSTATE_URL, "images/grayHeart.jpg");
			configuration.setPropertyByName(ToggleImageFieldConstant.TIMGFD_DWNSTATE_TITLE,"like");
			configuration.setPropertyByName(ToggleImageFieldConstant.TIMGFD_STATEIMG_PCLS,"toggleImageCss");
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getToggleImageFieldConf  method :"+e);
		}
		return configuration;
	}
	
	private Configuration getStaticListBoxConfiguration() {
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getStaticListBoxConfiguration  method ");
			//configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_VISIBLE_ITEM_CNT, 3);
			ArrayList<String> items = new ArrayList<String>();
			items.add("Private access");
			items.add("Public");
			items.add("Restricted");
			items.add("Me");
			
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"Public");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,items);
			
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getStaticListBoxConfiguration  method :"+e);
		}
		
		return configuration;
	}
	
	private Configuration getDynamicListBoxConfiguration() {
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getDynamicListBoxConfiguration  method ");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"spacemanagement.SpaceManagementService.getEntityList");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getAllSpaces");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERY_MAXRESULT,10);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getDynamicListBoxConfiguration  method :"+e);;
		}
		
		return configuration;
	}

	
	
	private Configuration getStaticStateFieldConf() {

		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getStaticStateFieldConf  method ");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getStaticStateFieldConf  method :"+e);
		}
		
		return configuration;
	}
	
	private Configuration getDynamicStateFieldConf() {

		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getDynamicStateFieldConf  method ");
			configuration.setPropertyByName(StateFieldConstant.IS_STATIC_BOX,false);
			configuration.setPropertyByName(StateFieldConstant.STFD_OPRTION,"contact.ContactService.getEntityList");
			configuration.setPropertyByName(StateFieldConstant.STFD_QUERYNAME,"getContactForChat");
			configuration.setPropertyByName(StateFieldConstant.STFD_ENTPROP,"name");
			configuration.setPropertyByName(StateFieldConstant.STFD_QUERY_MAXRESULT,10);
			configuration.setPropertyByName(StateFieldConstant.IS_AUTOSUGGESTION,false);
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("spaceId", 1);
			//configuration.setPropertyByName(StateFieldConstant.STFD_QUERY_RESTRICTION,paramMap);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getDynamicStateFieldConf  method :"+e);
		}
		
		return configuration;
	}
	
	public Configuration getCheckboxFieldConfiguration(String text) {
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getCheckboxFieldConfiguration  method ");
			configuration.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, text);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getCheckboxFieldConfiguration  method :"+e);
		}
		return configuration;
	}
	
	protected Configuration getSpinnerFieldConfiguration(String spinnerfieldMode) {
		
		logger.log(Level.INFO,"[FieldsShowCase]:: In getSpinnerFieldConfiguration  method ");
		Configuration config = new Configuration();
		//config.setPropertyByName(SpinnerField.SPINNERFIELDMODE, spinnerfieldMode);
		return config;
	}
	
	protected Configuration getNumberFieldConfiguration() {
		logger.log(Level.INFO,"[FieldsShowCase]:: In getNumberFieldConfiguration  method ");
		Configuration config = new Configuration();
		return config;
	}
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In onFieldEvent  method ");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In onFieldEvent  method :"+e);
		}
	}

	@Override
	public void onChange(ChangeEvent event) {
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In onChange  method ");
			int index = listBox.getSelectedIndex();
			String fieldName = listBox.getValue(index);
			initializeField(fieldName);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In onChange  method :"+e);
		}
	}

	private void initializeField(String fieldName) {
		
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In initializeField  method ");
			innerPanel.clear();
			
			if(fieldName.equals(TEXTBOX)) {
				TextField textFieldTB = new TextField();
				textFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, "appops-TextField", null, null));
				textFieldTB.configure();
				textFieldTB.create();
				selectedField = textFieldTB;
				innerPanel.add(textFieldTB);
				innerPanel.setCellHorizontalAlignment(textFieldTB,HorizontalPanel.ALIGN_CENTER);
			} else if(fieldName.equals(PASSWORDTEXTBOX)) {
				TextField textFieldPTB = new TextField();
				textFieldPTB.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_PSWBOX, "appops-TextField", null, null));
				textFieldPTB.configure();
				textFieldPTB.create();
				selectedField = textFieldPTB;
				innerPanel.add(textFieldPTB);
				innerPanel.setCellHorizontalAlignment(textFieldPTB,HorizontalPanel.ALIGN_CENTER);
				
			}else if(fieldName.equals(EMAILBOX)) {
				TextField textFieldTB = new TextField();
				textFieldTB.setConfiguration(getEmailFieldConfiguration(1, false, TextFieldConstant.TFTYPE_EMAILBOX, "appops-TextField", null, null));
				textFieldTB.configure();
				textFieldTB.create();
				selectedField = textFieldTB;
				innerPanel.add(textFieldTB);
				innerPanel.setCellHorizontalAlignment(textFieldTB,HorizontalPanel.ALIGN_CENTER);
				
			} else if(fieldName.equals(TEXTAREA)) {
				TextField textFieldTA = new TextField();
				textFieldTA.setConfiguration(getTextAreaConfiguration(10, false, TextFieldConstant.TFTTYPE_TXTAREA, null, null, null));
				textFieldTA.configure();
				textFieldTA.create();
				selectedField = textFieldTA;
				innerPanel.add(textFieldTA);
				innerPanel.setCellHorizontalAlignment(textFieldTA,HorizontalPanel.ALIGN_CENTER);

			}else if(fieldName.equals(NUMERICBOX)) {
				TextField textFieldTB = new TextField();
				textFieldTB.setConfiguration(getNumericFieldConfiguration(1, false, TextFieldConstant.TFTYPE_NUMERIC, "appops-TextField", null, null));
				textFieldTB.configure();
				textFieldTB.create();
				selectedField = textFieldTB;
				innerPanel.add(textFieldTB);
				innerPanel.setCellHorizontalAlignment(textFieldTB,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(GROUPFIELD)) {
				GroupField groupField = getCheckBoxGroupField();
				innerPanel.add(groupField);
				selectedField = groupField;
				innerPanel.setCellHorizontalAlignment(groupField,HorizontalPanel.ALIGN_CENTER);
			} else if(fieldName.equals(GROUPFIELDRADIO)) {
				GroupField groupField = getRadioButtonGroupField();
				innerPanel.add(groupField);
				selectedField = groupField;
				innerPanel.setCellHorizontalAlignment(groupField,HorizontalPanel.ALIGN_CENTER);
			} else if(fieldName.equals(LABELFIELD)) {
				final LabelField labelField = new LabelField();
				labelField.setConfiguration(getLabelFieldConfiguration(true,"appops-LabelField","dateTimelabel","Label with configuration"));
				labelField.configure();
				labelField.create();
				selectedField = labelField;
				innerPanel.add(labelField);
				/*Button btn = new Button("configure");
				btn.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						labelField .getConfiguration().setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "after conf set");
						labelField.configure();
						labelField.create();
						
					}
				});
				innerPanel.add(btn);*/
				innerPanel.setCellHorizontalAlignment(labelField,HorizontalPanel.ALIGN_CENTER);
			} else if(fieldName.equals(LINKFIELDHYPERLINK)) {
				LinkField hyperLinkField = new LinkField();
				hyperLinkField.setConfiguration(getHyperLinkConfiguration());
				hyperLinkField.configure();
				hyperLinkField.create();
				selectedField = hyperLinkField;
				innerPanel.add(hyperLinkField);
				innerPanel.setCellHorizontalAlignment(hyperLinkField,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(LINKFIELDANCHOR)) {
				LinkField anchorLinkField = new LinkField();
				anchorLinkField.setConfiguration(getAnchorConfiguration());
				anchorLinkField.configure();
				anchorLinkField.create();
				selectedField = anchorLinkField;
				innerPanel.add(anchorLinkField);
				innerPanel.setCellHorizontalAlignment(anchorLinkField,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(BUTTONFIELD)) {
				ButtonField btnField = new ButtonField();
				btnField.setConfiguration(getButtonConfiguration());
				btnField.configure();
				btnField.create();
				selectedField = btnField;
				innerPanel.add(btnField);
				innerPanel.setCellHorizontalAlignment(btnField,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(IMAGEFIELD)) {
				ImageField imageField = new ImageField();
				imageField.setConfiguration(getImageConfiguration());
				imageField.configure();
				imageField.create();
				selectedField = imageField;
				innerPanel.add(imageField);
				innerPanel.setCellHorizontalAlignment(imageField,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(TOGGLEIMAGEFIELD)) {
				ToggleImageField toggleImageField = new ToggleImageField();
				toggleImageField.setConfiguration(getToggleImageFieldConf());
				toggleImageField.configure();
				toggleImageField.create();
				selectedField = toggleImageField;
				innerPanel.add(toggleImageField);
				
				innerPanel.setCellHorizontalAlignment(toggleImageField,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(LISTBOX_WITH_QUERY)) {
				ListBoxField staticListBox = new ListBoxField();
				staticListBox.setConfiguration(getDynamicListBoxConfiguration());
				staticListBox.configure();
				staticListBox.create();
				selectedField = staticListBox;
				innerPanel.add(staticListBox);
				innerPanel.setCellHorizontalAlignment(staticListBox,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(LISTBOX_STATIC)) {
				ListBoxField staticListBox = new ListBoxField();
				staticListBox.setConfiguration(getStaticListBoxConfiguration());
				staticListBox.configure();
				staticListBox.create();
				selectedField = staticListBox;
				innerPanel.add(staticListBox);
				innerPanel.setCellHorizontalAlignment(staticListBox,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(STATEFIELD_STATIC)) {
				StateField stateField = new StateField();
				Configuration stateFieldConfig = getStaticStateFieldConf();
				stateField.setConfiguration(stateFieldConfig);
				stateField.configure();
				stateField.create();
				selectedField = stateField;
				innerPanel.add(stateField);
				innerPanel.setCellHorizontalAlignment(stateField,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(STATEFIELD_WITH_QUERY)) {
				StateField stateField = new StateField();
				Configuration stateFieldConfig = getDynamicStateFieldConf();
				stateField.setConfiguration(stateFieldConfig);
				stateField.configure();
				stateField.create();
				selectedField = stateField;
				innerPanel.add(stateField);
				innerPanel.setCellHorizontalAlignment(stateField,HorizontalPanel.ALIGN_CENTER);

			}else if(fieldName.equals(TIME_PICKER)) {
				TimePickerField dateTimeField = new TimePickerField();
				Configuration configuration = new Configuration();
				configuration.setPropertyByName(TimePickerFieldConstant.TIME_FORMAT, TimePickerFieldConstant.AMPMFORMAT_WITH_SECONDS);
				dateTimeField.setConfiguration(configuration);
				dateTimeField.configure();
				dateTimeField.create();
				selectedField = dateTimeField;
				innerPanel.add(dateTimeField);
				innerPanel.setCellHorizontalAlignment(dateTimeField,HorizontalPanel.ALIGN_CENTER);

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
				selectedField = selectedField;
				innerPanel.add(dtPicker);
				innerPanel.setCellHorizontalAlignment(dtPicker,HorizontalPanel.ALIGN_CENTER);
			} else if(fieldName.equals(DATETIME_PICKER)) {
				DateTimePickerField dateTimePicker = new DateTimePickerField();
				dateTimePicker.setConfiguration(getDateTimePickerFieldConfiguration());
				dateTimePicker.configure();
				dateTimePicker.create();
				selectedField = dateTimePicker;
				innerPanel.add(dateTimePicker);
				innerPanel.setCellHorizontalAlignment(dateTimePicker,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(LOCATIONSELECTOR)) {
				if (Geolocation.isSupported()) {
					Geolocation.getGeolocation().getCurrentPosition(new PositionCallback() {
						public void onSuccess(Position position) {
							Coordinates coords = position.getCoords();
								if(locationField==null)	{		
									locationField = new LocationSelectorField();
									locationField.setConfiguration(getLocationSelectorConf(coords.getLatitude(), coords.getLongitude()));
									locationField.configure();
									locationField.create();
									selectedField = locationField;
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
				selectedField = rangeSliderField;
				innerPanel.setWidth("100%");
				innerPanel.add(rangeSliderField);
				innerPanel.setCellHorizontalAlignment(rangeSliderField, HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(STRINGRANGE_SLIDER)) {
				RangeSliderField rangeSliderField = new RangeSliderField();
				rangeSliderField.setConfiguration(getStringSliderConf());
				rangeSliderField.configure();
				rangeSliderField.create();
				selectedField = rangeSliderField;
				innerPanel.setWidth("100%");
				innerPanel.add(rangeSliderField);
				innerPanel.setCellHorizontalAlignment(rangeSliderField, HorizontalPanel.ALIGN_CENTER);

			}  else if(fieldName.equals(NUM_SPINNER)) {
				Configuration configuration = new Configuration();
				configuration.setPropertyByName(SpinnerFieldConstant.SP_STEP, 3);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_UNIT, "%");
				//configuration.setPropertyByName(SpinnerFieldConstant.SP_MAXVAL, 23F);
				//configuration.setPropertyByName(SpinnerFieldConstant.SP_MINVAL, -3F);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_CIRCULAR, true);
				configuration.setPropertyByName(SpinnerFieldConstant.BF_DEFVAL, 3F);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_TYPE, SpinnerFieldConstant.SP_TYPENUMERIC);
				configuration.setPropertyByName(SpinnerFieldConstant.BF_ERRPOS, SpinnerFieldConstant.BF_BOTTOM);
				//configuration.setPropertyByName(SpinnerFieldConstant.SP_DECPRECISION, 0);
				configuration.setPropertyByName(SpinnerFieldConstant.SP_ALLOWDEC, false);
				configuration.setPropertyByName(SpinnerFieldConstant.BF_VALIDATEONCHANGE, true);
				
				SpinnerField valueSpinner = new SpinnerField();
				valueSpinner.setConfiguration(configuration);
				valueSpinner.configure();
				valueSpinner.create();
				selectedField = valueSpinner;
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
				selectedField = listSpinner;
				innerPanel.add(listSpinner);
				innerPanel.setCellHorizontalAlignment(listSpinner,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(MEDIA_UPLOAD)) {
				
				innerPanel.add(loaderImage);
				innerPanel.setCellHorizontalAlignment(loaderImage,HorizontalPanel.ALIGN_CENTER);
				loaderImage.setVisible(true);	
				addMediaUploaderField();
			}else if(fieldName.equals(HTMLEDITOR)) {
				
				/*Configuration configuration = new Configuration();
				configuration.setPropertyByName(HtmlEditorFieldConstant.FIELD_MODE, HtmlEditorFieldConstant.FIELD_EDIT_MODE);
				configuration.setPropertyByName(HtmlEditorFieldConstant.FIELD_HEIGHT, "250px");
				configuration.setPropertyByName(HtmlEditorFieldConstant.FIELD_WIDTH, "500px");
				configuration.setPropertyByName(HtmlEditorFieldConstant.FIELD_RESIZE_ENABLE, false);
				
				final HtmlEditorField editorField = new HtmlEditorField();
				editorField.setConfiguration(configuration);
				editorField.configure();
				editorField.create();
				selectedField = editorField;
				innerPanel.add(editorField);*/
			}else if(fieldName.equals(DATELABEL_WITH_TIMESTAMP)) {
				DateLabelField dateLabelField = new DateLabelField();
				dateLabelField.setConfiguration(getDateLabelTimeStampConf(DateLabelFieldConstant.LIVETIMESTAMP_DSPLY));
				dateLabelField.configure();
				dateLabelField.create();
				selectedField = dateLabelField;
				innerPanel.add(dateLabelField);
				innerPanel.setCellHorizontalAlignment(dateLabelField,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(DATELABEL_WITH_DATETIME)) {
				DateLabelField dateLabelField = new DateLabelField();
				dateLabelField.setConfiguration(getDateLabelTimeStampConf(DateLabelFieldConstant.DATETIME_DSPLY));
				dateLabelField.configure();
				dateLabelField.create();
				selectedField = dateLabelField;
				innerPanel.add(dateLabelField);
				innerPanel.setCellHorizontalAlignment(dateLabelField,HorizontalPanel.ALIGN_CENTER);
			}else if(fieldName.equals(INTELLITHOUGHTFIELD)) {
				innerPanel.add(loaderImage);
				innerPanel.setCellHorizontalAlignment(loaderImage,HorizontalPanel.ALIGN_CENTER);
				loaderImage.setVisible(true);	
				addIntellithoughtField();
			}else if(fieldName.equals(QUERYTHOUGHTFIELD)) {
				
				innerPanel.add(loaderImage);
				innerPanel.setCellHorizontalAlignment(loaderImage,HorizontalPanel.ALIGN_CENTER);
				loaderImage.setVisible(true);	
				addQuerythoughtField();
			}
			
			componentHolder.setPackageName(getPackageNameOfSelectedField(fieldName));
			componentHolder.showConfigurationEditor(selectedField);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In initializeField  method :"+e);
		}
	}
	
	private Configuration getIntellithoughtFieldConf() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(IntelliThoughtFieldConstant.BF_PCLS, "intelliThoughtField");
		configuration.setPropertyByName(IntelliThoughtFieldConstant.BF_SUGGESTION_TEXT, "Any thoughts");
		configuration.setPropertyByName(IntelliThoughtFieldConstant.FIRE_EDITINITIATED_EVENT, true);
		configuration.setPropertyByName(IntelliThoughtFieldConstant.FIRE_THREECHARENTERED_EVENT, true);
		configuration.setPropertyByName(IntelliThoughtFieldConstant.FIRE_WORDENTERED_EVENT, true);
		configuration.setPropertyByName(IntelliThoughtFieldConstant.INTLTHT_ENTPROP, "name");
		configuration.setPropertyByName(IntelliThoughtFieldConstant.INTLTHT_OPRTION, "spacemanagement.SpaceManagementService.getLinkSuggestions");
		configuration.setPropertyByName(IntelliThoughtFieldConstant.INTLTHT_MAXCHARLEN, 3);
		configuration.setPropertyByName(IntelliThoughtFieldConstant.INTLTHT_QUERY_MAXRESULT, 10);
		configuration.setPropertyByName(IntelliThoughtFieldConstant.BF_ID, "intelliTextField");
		configuration.setPropertyByName(IntelliThoughtFieldConstant.INTLTHT_SUGGESTIONLBL_PCLS, "appops-intelliThoughtSuggestionLabel");
		configuration.setPropertyByName(IntelliThoughtFieldConstant.INTLTHT_SUGGESTIONPOPUP_PCLS, "appops-intelliThoughtLinkedSuggestionPopup");
		return configuration;
	}
	
	private Configuration getQuerythoughtFieldConf() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(QueryThoughtFieldConstant.BF_PCLS, "intelliThoughtField");
		configuration.setPropertyByName(QueryThoughtFieldConstant.BF_SUGGESTION_TEXT, "Enter query");
		configuration.setPropertyByName(QueryThoughtFieldConstant.FIRE_EDITINITIATED_EVENT, true);
		configuration.setPropertyByName(QueryThoughtFieldConstant.FIRE_THREECHARENTERED_EVENT, true);
		configuration.setPropertyByName(QueryThoughtFieldConstant.FIRE_WORDENTERED_EVENT, true);
		configuration.setPropertyByName(QueryThoughtFieldConstant.INTLTHT_ENTPROP, "name");
		configuration.setPropertyByName(QueryThoughtFieldConstant.INTLTHT_OPRTION, "spacemanagement.SpaceManagementService.getLinkSuggestions");
		configuration.setPropertyByName(QueryThoughtFieldConstant.INTLTHT_MAXCHARLEN, 3);
		configuration.setPropertyByName(QueryThoughtFieldConstant.INTLTHT_QUERY_MAXRESULT, 10);
		configuration.setPropertyByName(QueryThoughtFieldConstant.BF_ID, "intelliTextField");
		configuration.setPropertyByName(QueryThoughtFieldConstant.INTLTHT_SUGGESTIONLBL_PCLS, "appops-intelliThoughtSuggestionLabel");
		configuration.setPropertyByName(QueryThoughtFieldConstant.INTLTHT_SUGGESTIONPOPUP_PCLS, "appops-intelliThoughtLinkedSuggestionPopup");
		
		Entity serviceEnt = new Entity();
		serviceEnt.setType(new MetaType(Service.class));
		serviceEnt.setPropertyByName("id", new Key<Long>(27L));
		 
		configuration.setPropertyByName(QueryThoughtFieldConstant.QRYTHOUGHT_SERVICE, serviceEnt);
		
		Entity schemaEnt = new Entity();
		schemaEnt.setType(new MetaType(SchemaDefinition.class));
		schemaEnt.setPropertyByName("id", new Key<Long>(93L));
		
		configuration.setPropertyByName(QueryThoughtFieldConstant.QRYTHOUGHT_SCHEMA, schemaEnt);
		return configuration;
	}

	private Configuration getDateLabelTimeStampConf(String displayFormat) {
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getDateLabelTimeStampConf  method ");
			configuration.setPropertyByName(DateLabelFieldConstant.DTLBL_DSPLY_FORM, displayFormat);
			configuration.setPropertyByName(DateLabelFieldConstant.DATETIME_FORMAT, "MMM dd ''yy 'at' HH:mm");
			configuration.setPropertyByName(DateLabelFieldConstant.DATETIME_TO_DISPLAY, new Date());
			configuration.setPropertyByName(DateLabelFieldConstant.BF_PCLS, "dateTimelabel");
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getDateLabelTimeStampConf  method :"+e);
		}
		
		return configuration;
	}

	private Configuration getMediaFieldConfiguration() {
		
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getMediaFieldConfiguration  method ");
			configuration.setPropertyByName(MediaFieldConstant.MF_MEDIAIMG_BLOB, "images/Media.png");
			configuration.setPropertyByName(MediaFieldConstant.MF_MEDIAIMG_PCLS, "mediaImage");
			configuration.setPropertyByName(MediaFieldConstant.MF_MEDIAIMG_DCLS, "fadeInUp");
			configuration.setPropertyByName(MediaFieldConstant.MF_ISPROFILE_IMG, true);
			configuration.setPropertyByName(MediaFieldConstant.MF_FILEUPLOADER_CLS, "appops-webMediaAttachment");
			
			ArrayList<String> extensions = new ArrayList<String>();
			extensions.add("jpg");
			extensions.add("jpeg");
			extensions.add("gif");
			extensions.add("png");
			configuration.setPropertyByName(MediaFieldConstant.MF_VALIDEXTEXNSION_LIST, extensions);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getMediaFieldConfiguration  method :"+e);
		}
		
		return configuration;
	}

	private Configuration getDateTimePickerFieldConfiguration(){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getDateTimePickerFieldConfiguration  method ");
			configuration.setPropertyByName(DateTimePickerFieldConstant.DATE_DEFVAL, "01.07.2013");
			configuration.setPropertyByName(DatePickerConstant.DP_MAXDATE, "03.08.2013");
			configuration.setPropertyByName(DatePickerConstant.DP_MINDATE, "05.06.2013");
			configuration.setPropertyByName(DatePickerConstant.DP_FORMAT, "dd.MM.yyyy");
			configuration.setPropertyByName(DatePickerConstant.DP_ALLOWBLNK, false);
			configuration.setPropertyByName(DatePickerConstant.BF_ERRPOS, DatePickerConstant.BF_BOTTOM);
			configuration.setPropertyByName(TimePickerFieldConstant.TIME_FORMAT, TimePickerFieldConstant.AMPMFORMAT_WITH_SECONDS);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getDateTimePickerFieldConfiguration  method :"+e);
		}
		return configuration;
	}
	
	private Configuration getNumericSliderConf() {
		Configuration conf = new Configuration();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getNumericSliderConf  method ");
			conf.setPropertyByName(RangeSliderFieldConstant.SLIDER_MODE,RangeSliderFieldConstant.NUM_SLIDER);
			conf.setPropertyByName(RangeSliderFieldConstant.BF_PCLS,"sliderPanel");
			conf.setPropertyByName(RangeSliderFieldConstant.MINVAL,100d);
			conf.setPropertyByName(RangeSliderFieldConstant.MAXVAL,200d);
			conf.setPropertyByName(RangeSliderFieldConstant.STEPVAL,50d);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getNumericSliderConf  method :"+e);
		}
		return conf;
	}
	
	private Configuration getStringSliderConf() {
		Configuration conf = new Configuration();	
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In getStringSliderConf  method ");
			ArrayList<String> optionList = new ArrayList<String>();
			optionList.add("Me");
			optionList.add("Public");
			optionList.add("Private");
			optionList.add("Restricted");
			
			
			conf.setPropertyByName(RangeSliderFieldConstant.SLIDER_MODE,RangeSliderFieldConstant.STRING_SLIDER);
			conf.setPropertyByName(RangeSliderFieldConstant.BF_PCLS,"sliderPanel");
			conf.setPropertyByName(RangeSliderFieldConstant.ITEMS_LIST,optionList);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In getStringSliderConf  method :"+e);
		}
		return conf;
	}

	public MediaAttachWidget createMediaField() {
		MediaAttachWidget mediaWidget = new WebMediaAttachWidget();
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In createMediaField  method ");
			mediaWidget.isFadeUpEffect(false);
			mediaWidget.createUi();
			mediaWidget.setVisible(true);
			mediaWidget.setWidth("100%");
			mediaWidget.createAttachmentUi();
			mediaWidget.isMediaImageVisible(false);
			mediaWidget.expand();
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In createMediaField  method :"+e);
		}
		return mediaWidget;
	}
	
	private void addMediaUploaderField() {
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In addMediaUploaderField  method ");
			Map parameters = new HashMap();
			parameters.put("emailId", "pallavi@ensarm.com");
			parameters.put("password", "pallavi123");
			
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
					MediaField mediaField = new MediaField();
					mediaField.setConfiguration(getMediaFieldConfiguration());
					mediaField.configure();
					mediaField.create();
					selectedField = mediaField;
					innerPanel.add(mediaField);
					innerPanel.setCellHorizontalAlignment(mediaField,HorizontalPanel.ALIGN_CENTER);
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In addMediaUploaderField  method :"+e);
		}
	}
	
	
	private void addIntellithoughtField() {
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In addIntellithoughtField  method ");
			Map parameters = new HashMap();
			parameters.put("emailId", "pallavi@ensarm.com");
			parameters.put("password", "pallavi123");
			
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
					IntelliThoughtField intellithoughtField = new IntelliThoughtField();
					intellithoughtField.setConfiguration(getIntellithoughtFieldConf());
					intellithoughtField.configure();
					intellithoughtField.create();
					selectedField = intellithoughtField;
					innerPanel.add(intellithoughtField);
					innerPanel.setCellHorizontalAlignment(intellithoughtField,HorizontalPanel.ALIGN_CENTER);
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In addIntellithoughtField  method :"+e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void addQuerythoughtField() {
		try {
			logger.log(Level.INFO,"[FieldsShowCase]:: In addIntellithoughtField  method ");
			Map parameters = new HashMap();
			parameters.put("emailId", "pallavi@ensarm.com");
			parameters.put("password", "pallavi123");
			
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
					QueryThoughtField querythoughtField = new QueryThoughtField();
					querythoughtField.setConfiguration(getQuerythoughtFieldConf());
					querythoughtField.configure();
					querythoughtField.create();
					selectedField = querythoughtField;
					innerPanel.add(querythoughtField);
					innerPanel.setCellHorizontalAlignment(querythoughtField,HorizontalPanel.ALIGN_CENTER);
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[FieldsShowCase]::Exception In addIntellithoughtField  method :"+e);
		}
	}
	
	private String getPackageNameOfSelectedField(String fieldName){
				
			if(fieldName.equals(TEXTBOX) || fieldName.equals(PASSWORDTEXTBOX) || fieldName.equals(EMAILBOX) || 	fieldName.equals(TEXTAREA) || fieldName.equals(NUMERICBOX)	) {
				return TextField.class.getName();
			} else if(fieldName.equals(GROUPFIELD) || fieldName.equals(GROUPFIELDRADIO)) {
				return GroupField.class.getName();
			}else if(fieldName.equals(STATEFIELD_STATIC) || fieldName.equals(STATEFIELD_WITH_QUERY)) {
				return StateField.class.getName();
			} else if(fieldName.equals(TIME_PICKER) || fieldName.equals(DATETIME_PICKER)) {
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
				return MediaField.class.getName();
			}else if(fieldName.equals(LABELFIELD)) {
				return LabelField.class.getName();
			}else if(fieldName.equals(LINKFIELDHYPERLINK) || fieldName.equals(LINKFIELDANCHOR)) {
				return LinkField.class.getName();
			}else if(fieldName.equals(IMAGEFIELD)) {
				return ImageField.class.getName();
			}else if(fieldName.equals(TOGGLEIMAGEFIELD)) {
				return ToggleImageField.class.getName();
			}else if(fieldName.equals(LISTBOX_WITH_QUERY) || fieldName.equals(LISTBOX_STATIC)) {
				return ListBoxField.class.getName();
			}else if(fieldName.equals(BUTTONFIELD)) {
				return ButtonField.class.getName();
			}else if(fieldName.equals(LOCATIONSELECTOR)) {
				return LocationSelectorField.class.getName();
			}else if(fieldName.equals(HTMLEDITOR)) {
//				return HtmlEditorField.class.getName();
			}else if(fieldName.equals(DATELABEL_WITH_DATETIME) || fieldName.equals(DATELABEL_WITH_TIMESTAMP)) {
				return DateLabelField.class.getName();
			}else if(fieldName.equals(INTELLITHOUGHTFIELD)) {
				return IntelliThoughtField.class.getName();
			}else if(fieldName.equals(QUERYTHOUGHTFIELD)) {
				return QueryThoughtField.class.getName();
			}
			
		return null;
		
	}
	
}