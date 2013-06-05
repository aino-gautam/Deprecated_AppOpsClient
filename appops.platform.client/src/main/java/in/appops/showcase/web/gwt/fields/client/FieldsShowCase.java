package in.appops.showcase.web.gwt.fields.client;

import in.appops.client.common.components.LocationHomeSelector;
import in.appops.client.common.components.MediaAttachWidget;
import in.appops.client.common.components.WebMediaAttachWidget;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.CheckboxField;
import in.appops.client.common.fields.DateTimeField;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.LinkField;
import in.appops.client.common.fields.LocationSelector;
import in.appops.client.common.fields.NumberField;
import in.appops.client.common.fields.SpinnerField;
import in.appops.client.common.fields.StateField;
import in.appops.client.common.fields.TextField;
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
	
	public static final String TEXTBOX = "Text Box";
	public static final String PASSWORDTEXTBOX = "Password Textbox";
	public static final String TEXTAREA = "Text Area";
	public static final String CHECKBOXGROUPMULTISELECT = "CheckboxGroupField - MultiSelect";
	public static final String CHECKBOXGROUPSINGLESELECT = "CheckboxGroupField - SingleSelect";
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
	public static final String SPINNERFIELD = "SpinnerField";
	public static final String SPINNERFIELD_PERCENT = "SpinnerField(Percent)";
	public static final String NUMBERFIELD = "NumberField";
	public static final String MEDIA_UPLOAD = "Media Uploader";
	private Image loaderImage;
	public FieldsShowCase() {
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}
	
	@Override
	public void onModuleLoad() {
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
		listBox.addItem(TEXTAREA);
		listBox.addItem(CHECKBOXGROUPMULTISELECT);
		listBox.addItem(CHECKBOXGROUPSINGLESELECT);
		listBox.addItem(CHECKBOXFIELD);
		listBox.addItem(STATEFIELD);
		listBox.addItem(TIME_PICKER);
		listBox.addItem(TIME_PICKER_HOUR);
		listBox.addItem(TIME_PICKER_MINUTE);
		listBox.addItem(TIME_PICKER_SEC);
		listBox.addItem(DATE_PICKER);
		listBox.addItem(DATETIME_PICKER);
		listBox.addItem(LOCATIONSELECTOR);
		listBox.addItem(NUMBERRANGE_SLIDER);
		listBox.addItem(STRINGRANGE_SLIDER);
		listBox.addItem(SPINNERFIELD);
		listBox.addItem(SPINNERFIELD_PERCENT);
		listBox.addItem(NUMBERFIELD);
		listBox.addItem(MEDIA_UPLOAD);
		
		listBox.addChangeHandler(this);
		listBox.setStylePrimaryName("fieldShowcaseBasePanel");
		listBoxPanel.add(titleLabel);
		listBoxPanel.add(listBox);
		listBoxPanel.setCellVerticalAlignment(titleLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		basePanel.add(listBoxPanel);
		basePanel.add(innerPanel);
		basePanel.setSpacing(20);
		basePanel.setStylePrimaryName("fieldShowcaseBasePanel");
		RootPanel.get().add(basePanel);
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
		configuration.setPropertyByName(TextField.TEXTFIELD_PRIMARYCSS, "appops-TextField");
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
		configuration.setPropertyByName(TextField.TEXTFIELD_PRIMARYCSS, "appops-TextField");
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
		configuration.setPropertyByName(TextField.TEXTFIELD_VISIBLELINES, visibleLines);
		configuration.setPropertyByName(TextField.TEXTFIELD_READONLY, readOnly);
		configuration.setPropertyByName(TextField.TEXTFIELD_TYPE, textFieldType);
		configuration.setPropertyByName(TextField.TEXTFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(TextField.TEXTFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(TextField.TEXTFIELD_DEBUGID, debugId);
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
		configuration.setPropertyByName(CheckboxField.CHECKBOXFIELD_DISPLAYTEXT, text);
		return configuration;
	}
	
	protected Configuration getSpinnerFieldConfiguration(String spinnerfieldMode) {
		Configuration config = new Configuration();
		config.setPropertyByName(SpinnerField.SPINNERFIELDMODE, spinnerfieldMode);
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
				textFieldTB.setFieldValue("");
				textFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextField.TEXTFIELDTYPE_TEXTBOX, "appops-TextField", null, null));
				textFieldTB.createField();
				innerPanel.add(textFieldTB);
				innerPanel.setCellHorizontalAlignment(textFieldTB,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(PASSWORDTEXTBOX)) {
				TextField textFieldPTB = new TextField();
				textFieldPTB.setFieldValue("Password");
				textFieldPTB.setConfiguration(getTextFieldConfiguration(1, false, TextField.TEXTFIELDTYPE_PASSWORDTEXTBOX, "appops-TextField", null, null));
				textFieldPTB.createField();
				innerPanel.add(textFieldPTB);
				innerPanel.setCellHorizontalAlignment(textFieldPTB,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(TEXTAREA)) {
				TextField textFieldTA = new TextField();
				textFieldTA.setFieldValue("");
				textFieldTA.setConfiguration(getTextFieldConfiguration(10, false, TextField.TEXTFIELDTYPE_TEXTAREA, "appops-TextField", null, null));
				textFieldTA.createField();
				innerPanel.add(textFieldTA);
				innerPanel.setCellHorizontalAlignment(textFieldTA,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(CHECKBOXGROUPMULTISELECT)) {
				GroupCheckboxWidget multiSelectCheckbox = new GroupCheckboxWidget();
				multiSelectCheckbox.createMultiSelectCheckbox();
				innerPanel.add(multiSelectCheckbox);
				innerPanel.setCellHorizontalAlignment(multiSelectCheckbox,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(CHECKBOXGROUPSINGLESELECT)) {
				GroupCheckboxWidget singleSelectCheckbox = new GroupCheckboxWidget();
				singleSelectCheckbox.createSingleSelectCheckbox();
				innerPanel.add(singleSelectCheckbox);
				innerPanel.setCellHorizontalAlignment(singleSelectCheckbox,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(CHECKBOXFIELD)) {
				CheckboxWidget checkbox = new CheckboxWidget();
				innerPanel.add(checkbox);
				innerPanel.setCellHorizontalAlignment(checkbox,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(STATEFIELD)) {
				StateField stateField = new StateField();
				//stateField.setFieldValue("Suggestion");
				Configuration stateFieldConfig = getStateFieldConfiguration(StateField.STATEFIELDMODE_SUGGESTIVE, "getSpaceTypesWithName", "spacemanagement.SpaceManagementService.getEntityList", SpaceTypeConstants.NAME);
				stateField.setConfiguration(stateFieldConfig);
				stateField.createField();
				innerPanel.add(stateField);
				innerPanel.setCellHorizontalAlignment(stateField,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(TIME_PICKER)) {
				DateTimeField dateTimeField = new DateTimeField();
				dateTimeField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_VIEW,DateTimeField.DATETIMEFIELD_TIMEONLY,DateTimeField.Full_Time));
				dateTimeField.createField();
				innerPanel.add(dateTimeField);
				innerPanel.setCellHorizontalAlignment(dateTimeField,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(TIME_PICKER_HOUR)) {
				DateTimeField dateTimeShortHoursField = new DateTimeField();
				dateTimeShortHoursField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_VIEW,DateTimeField.DATETIMEFIELD_TIMEONLY,DateTimeField.SHORT_HOURS));
				dateTimeShortHoursField.createField();
				innerPanel.add(dateTimeShortHoursField);
				innerPanel.setCellHorizontalAlignment(dateTimeShortHoursField,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(TIME_PICKER_MINUTE)) {
				DateTimeField dateTimeShortMinuteField = new DateTimeField();
				dateTimeShortMinuteField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_VIEW,DateTimeField.DATETIMEFIELD_TIMEONLY,DateTimeField.SHORT_MINUTE));
				dateTimeShortMinuteField.createField();
				innerPanel.add(dateTimeShortMinuteField);
				innerPanel.setCellHorizontalAlignment(dateTimeShortMinuteField,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(TIME_PICKER_SEC)) {
				DateTimeField dateTimeShortSecField = new DateTimeField();
				dateTimeShortSecField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_VIEW,DateTimeField.DATETIMEFIELD_TIMEONLY,DateTimeField.SHORT_SECONDS));
				dateTimeShortSecField.createField();
				innerPanel.add(dateTimeShortSecField);
				innerPanel.setCellHorizontalAlignment(dateTimeShortSecField,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(DATE_PICKER)) {
				DateTimeField dateField = new DateTimeField();
				dateField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_VIEW,DateTimeField.DATETIMEFIELD_DATEONLY,null));
				dateField.createField();
				innerPanel.add(dateField);
				innerPanel.setCellHorizontalAlignment(dateField,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(DATETIME_PICKER)) {
				DateTimeField dateTimeOnlyField = new DateTimeField();
				dateTimeOnlyField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_VIEW,DateTimeField.DATETIMEFIELD_DATETIMEONLY,null));
				dateTimeOnlyField.createField();
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

			} else if(fieldName.equals(SPINNERFIELD)) {
				SpinnerWidget valueSpinner = new SpinnerWidget();
				valueSpinner.createValueSpinner();
				innerPanel.add(valueSpinner);
				innerPanel.setCellHorizontalAlignment(valueSpinner,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(SPINNERFIELD_PERCENT)) {
				SpinnerWidget percentSpinner = new SpinnerWidget();
				percentSpinner.createPercentSpinner();
				innerPanel.add(percentSpinner);
				innerPanel.setCellHorizontalAlignment(percentSpinner,HorizontalPanel.ALIGN_CENTER);

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
				numberField.createField();
				innerPanel.add(numberFieldPanel);
				innerPanel.setCellHorizontalAlignment(numberFieldPanel,HorizontalPanel.ALIGN_CENTER);

			} else if(fieldName.equals(MEDIA_UPLOAD)) {

				innerPanel.add(loaderImage);
				innerPanel.setCellHorizontalAlignment(loaderImage,HorizontalPanel.ALIGN_CENTER);
				loaderImage.setVisible(true);	
				addMediaUploaderField();
			}
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
}
