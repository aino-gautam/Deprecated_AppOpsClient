package in.appops.showcase.web.gwt.fields.client;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.CheckboxField;
import in.appops.client.common.fields.CheckboxGroupField;
import in.appops.client.common.fields.DateTimeField;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.LinkField;
import in.appops.client.common.fields.LocationSelector;
import in.appops.client.common.fields.NumberField;
import in.appops.client.common.fields.SpinnerField;
import in.appops.client.common.fields.StateField;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.slider.field.NumericRangeSliderField;
import in.appops.client.common.fields.slider.field.StringRangeSliderField;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.code.gwt.geolocation.client.Coordinates;
import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FieldsShowCase implements EntryPoint, FieldEventHandler {

	private FlexTable flex = new FlexTable();
	private LocationSelector locationSelector = new LocationSelector();
	private LocationSelector locationSelectorForFalseMode = new LocationSelector();
	private Label numberfieldErrorLabel;
	
	public FieldsShowCase() {
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}
	
	@Override
	public void onModuleLoad() {
		
		if (Geolocation.isSupported()) {
			Geolocation.getGeolocation().getCurrentPosition(new PositionCallback() {
				
				public void onSuccess(Position position) {
					
					Coordinates coords = position.getCoords();
					LatLng latLng = new LatLng(coords.getLatitude(), coords.getLongitude());
														

					locationSelector.setConfiguration(getLocationSelectorConf());
					locationSelector.setMapMode(true);
					
					//locationSelector.setLatLong(latLng);
					locationSelector.setCoordinates(coords);
					
					
					locationSelectorForFalseMode.setConfiguration(getLocationSelectorConf());
					locationSelectorForFalseMode.setMapMode(false);
					locationSelectorForFalseMode.setMapHeight("400px");
					locationSelectorForFalseMode.setMapWidth("600px");
					//locationSelector.setLatLong(latLng);
					locationSelectorForFalseMode.setCoordinates(coords);
					

					LabelField labelFieldTB = new LabelField();
					labelFieldTB.setFieldValue("Text Box");
					labelFieldTB.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));

					TextField textFieldTB = new TextField();
					textFieldTB.setFieldValue("");
					textFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextField.TEXTFIELDTYPE_TEXTBOX, "appops-TextField", null, null));

					LabelField labelFieldPTB = new LabelField();
					labelFieldPTB.setFieldValue("Password Textbox");
					labelFieldPTB.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));

					TextField textFieldPTB = new TextField();
					textFieldPTB.setFieldValue("Password");
					textFieldPTB.setConfiguration(getTextFieldConfiguration(1, false, TextField.TEXTFIELDTYPE_PASSWORDTEXTBOX, "appops-TextField", null, null));
					VerticalPanel verticalPanel = new VerticalPanel();
					LabelField labelFieldTA = new LabelField();
					labelFieldTA.setFieldValue("Text Area");
					labelFieldTA.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));

					TextField textFieldTA = new TextField();
					textFieldTA.setFieldValue("");
					textFieldTA.setConfiguration(getTextFieldConfiguration(10, false, TextField.TEXTFIELDTYPE_TEXTAREA, "appops-TextField", null, null));

					LinkField hyperlink = new LinkField();
					hyperlink.setFieldValue("Hyperlink");
					hyperlink.setConfiguration(getLinkFieldConfiguration(LinkField.LINKFIELDTYPE_HYPERLINK, "appops-LinkField", null, null));

					LinkField anchor = new LinkField();
					anchor.setFieldValue("Anchor");
					anchor.setConfiguration(getLinkFieldConfiguration(LinkField.LINKFIELDTYPE_HYPERLINK, "appops-LinkField", null, null));

					LabelField stateFieldLabel = new LabelField();
					stateFieldLabel.setFieldValue("StateField");
					stateFieldLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));

					StateField stateField = new StateField();
					//stateField.setFieldValue("Suggestion");
					Configuration stateFieldConfig = getStateFieldConfiguration(StateField.STATEFIELDMODE_SUGGESTIVE, "getSpaceTypesWithName", "spacemanagement.SpaceManagementService.getEntityList");
					stateField.setConfiguration(stateFieldConfig);

					LabelField CheckboxFieldLabel = new LabelField();
					CheckboxFieldLabel.setFieldValue("CheckboxField");
					CheckboxFieldLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));

					CheckboxField checkboxfield = new CheckboxField();
					Configuration config = getCheckboxFieldConfiguration("Allow permissions");
					checkboxfield.setFieldValue("true");
					checkboxfield.setConfiguration(config);

					LabelField CheckboxGroupFieldLabel = new LabelField();
					CheckboxGroupFieldLabel.setFieldValue("CheckboxGroupField - MultiSelect");
					CheckboxGroupFieldLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));

					CheckboxGroupField checkboxGroupField = new CheckboxGroupField();
					Configuration configuration = getCheckboxGroupFieldConfiguration(CheckboxGroupField.CHECKBOX_MULTISELECT,CheckboxGroupField.CHECKBOX_VERTICALBASEPANEL);
					checkboxGroupField.setConfiguration(configuration);

					LabelField singleSelectCheckboxFieldLabel = new LabelField();
					singleSelectCheckboxFieldLabel.setFieldValue("CheckboxGroupField - SingleSelect");
					singleSelectCheckboxFieldLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));

					CheckboxGroupField singleSelectCheckboxGroupField = new CheckboxGroupField();
					Configuration singleSelectionConfiguration = getCheckboxGroupFieldConfiguration(CheckboxGroupField.CHECKBOX_SINGLESELECT,CheckboxGroupField.CHECKBOX_VERTICALBASEPANEL);
					singleSelectCheckboxGroupField.setConfiguration(singleSelectionConfiguration);

					LabelField labelFieldDT = new LabelField();
					labelFieldDT.setFieldValue("Time Picker");
					labelFieldDT.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));


					DateTimeField dateTimeField = new DateTimeField();
					dateTimeField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_SELECTION,DateTimeField.DATETIMEFIELD_TIMEONLY,DateTimeField.Full_Time));
					
					LabelField labelFieldDT_ShortHours = new LabelField();
					labelFieldDT_ShortHours.setFieldValue("Time Picker(Short_Hours )");
					labelFieldDT_ShortHours.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));


					DateTimeField dateTimeShortHoursField = new DateTimeField();
					dateTimeShortHoursField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_SELECTION,DateTimeField.DATETIMEFIELD_TIMEONLY,DateTimeField.SHORT_HOURS));
					
					
					LabelField labelFieldDT_ShortMinute = new LabelField();
					labelFieldDT_ShortMinute.setFieldValue("Time Picker(Short_Minute )");
					labelFieldDT_ShortMinute.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));


					DateTimeField dateTimeShortMinuteField = new DateTimeField();
					dateTimeShortMinuteField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_SELECTION,DateTimeField.DATETIMEFIELD_TIMEONLY,DateTimeField.SHORT_MINUTE));
					
					LabelField labelFieldDT_ShortSec = new LabelField();
					labelFieldDT_ShortSec.setFieldValue("Time Picker(Short_Sec )");
					labelFieldDT_ShortSec.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));


					DateTimeField dateTimeShortSecField = new DateTimeField();
					dateTimeShortSecField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_SELECTION,DateTimeField.DATETIMEFIELD_TIMEONLY,DateTimeField.SHORT_SECONDS));
					

					LabelField labelFieldD = new LabelField();
					labelFieldD.setFieldValue("Date Picker");
					labelFieldD.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));


					DateTimeField dateField = new DateTimeField();
					dateField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_SELECTION,DateTimeField.DATETIMEFIELD_DATEONLY,null));


					LabelField labelFieldDTF = new LabelField();
					labelFieldDTF.setFieldValue("Date Time Picker");
					labelFieldDTF.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));


					DateTimeField dateTimeOnlyField = new DateTimeField();
					dateTimeOnlyField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_SELECTION,DateTimeField.DATETIMEFIELD_DATETIMEONLY,null));

					LabelField labelFieldLocation = new LabelField();
					labelFieldLocation.setFieldValue("Location Selector(true)");
					labelFieldLocation.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
					
					LabelField labelFieldLocationForFalseLabelField = new LabelField();
					labelFieldLocationForFalseLabelField.setFieldValue("Location Selector(false)");
					labelFieldLocationForFalseLabelField.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
					

					LabelField spinnerFieldLabel = new LabelField();
					spinnerFieldLabel.setFieldValue("SpinnerField");
					spinnerFieldLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
					
					SpinnerField spinnerField = new SpinnerField();
					spinnerField.setFieldValue("3");
					Configuration spinnerConfig = getSpinnerFieldConfiguration(SpinnerField.SPINNERFIELD_VALUESPINNER);
					spinnerField.setConfiguration(spinnerConfig);

					LabelField spinnerPercentLabel = new LabelField();
					spinnerPercentLabel.setFieldValue("SpinnerField(Percent)");
					spinnerPercentLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
					
					SpinnerField percentSpinnerField = new SpinnerField();
					percentSpinnerField.setFieldValue("3");
					Configuration percentSpinnerConfig = getSpinnerFieldConfiguration(SpinnerField.SPINNERFIELD_PERCENTSPINNER);
					percentSpinnerField.setConfiguration(percentSpinnerConfig);
					
					LabelField numberFieldLabel = new LabelField();
					numberFieldLabel.setFieldValue("NumberField");
					numberFieldLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
					
					NumberField numberField = new NumberField();
					numberField.setFieldValue("3");
					Configuration numberConfig = getNumberFieldConfiguration();
					numberField.setConfiguration(numberConfig);
					VerticalPanel numberFieldPanel = new VerticalPanel();
					numberfieldErrorLabel = new Label("Can't contain characters");
					numberFieldPanel.add(numberField);
					numberFieldPanel.add(numberfieldErrorLabel);
					numberfieldErrorLabel.setVisible(false);
					numberfieldErrorLabel.setStylePrimaryName("errorLabel");
					
					try {
						labelFieldTB.createField();
						textFieldTB.createField();

						labelFieldPTB.createField();
						textFieldPTB.createField();

						labelFieldTA.createField();
						textFieldTA.createField();

						hyperlink.createField();
						anchor.createField();

						stateFieldLabel.createField();
						stateField.createField();

						CheckboxFieldLabel.createField();
						checkboxfield.createField();

						CheckboxGroupFieldLabel.createField();
						checkboxGroupField.createField();

						singleSelectCheckboxFieldLabel.createField();
						singleSelectCheckboxGroupField.createField();

						labelFieldDT.createField();
						dateTimeField.createField();
						
						labelFieldDT_ShortHours.createField();
						dateTimeShortHoursField.createField();
						
						labelFieldDT_ShortMinute.createField();
						dateTimeShortMinuteField.createField();
						
						labelFieldDT_ShortSec.createField();
						dateTimeShortSecField.createField();

						labelFieldD.createField();
						dateField.createField();

						labelFieldDTF.createField();
						dateTimeOnlyField.createField();

						labelFieldLocation.createField();
						locationSelector.createField();
						
						labelFieldLocationForFalseLabelField.createField();
						locationSelectorForFalseMode.createField();
						
						
						spinnerFieldLabel.createField();
						spinnerField.createField();
						
						spinnerPercentLabel.createField();
						percentSpinnerField.createField();
						
						numberFieldLabel.createField();
						numberField.createField();


					} catch (AppOpsException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					checkboxGroupField.addCheckItem("Red");
					checkboxGroupField.addCheckItem("Green");
					checkboxGroupField.addCheckItem("Blue");

					singleSelectCheckboxGroupField.addCheckItem("Red");
					singleSelectCheckboxGroupField.addCheckItem("Green");
					singleSelectCheckboxGroupField.addCheckItem("Blue");

					Label numericRangeSliderLbl = new Label("NumericRangeSlider");
					StateField numericRangeSlider = new StateField();
					Configuration numericRangeSliderConfig = getNumericRangeSliderFieldConfiguration();
					numericRangeSlider.setConfiguration(numericRangeSliderConfig);
					try {
						numericRangeSlider.createField();
						numericRangeSlider.setStylePrimaryName("mainPanel");
					} catch (AppOpsException e) {
						e.printStackTrace();
					}

					Label stringRangeSliderLbl = new Label("StringRangeSlider");
					StateField stringRangeSlider = new StateField();
					Configuration stringRangeSliderConfig = getStringRangeSliderFieldConfiguration();
					stringRangeSlider.setConfiguration(stringRangeSliderConfig);
					try {
						stringRangeSlider.createField();
						stringRangeSlider.setStylePrimaryName("mainPanel");
					} catch (AppOpsException e) {
						e.printStackTrace();
					}

					flex.setWidget(0, 0, labelFieldTB);
					flex.setWidget(0, 1, textFieldTB);

					flex.setWidget(1, 0, labelFieldPTB);
					flex.setWidget(1, 1, textFieldPTB);

					flex.setWidget(2, 0, labelFieldTA);
					flex.setWidget(2, 1, textFieldTA);

					//flex.setWidget(3, 0, hyperlink);
					//flex.setWidget(3, 1, anchor);

					flex.setWidget(4, 0, CheckboxGroupFieldLabel);
					flex.setWidget(4, 1, checkboxGroupField);

					flex.setWidget(5, 0, singleSelectCheckboxFieldLabel);
					flex.setWidget(5, 1, singleSelectCheckboxGroupField);

					flex.setWidget(6, 0, CheckboxFieldLabel);
					flex.setWidget(6, 1, checkboxfield);

					flex.setWidget(7, 0, stateFieldLabel);
					flex.setWidget(7, 1, stateField);

					flex.setWidget(8, 0, labelFieldDT);
					flex.setWidget(8, 1, dateTimeField);
					
					flex.setWidget(9, 0, labelFieldDT_ShortHours);
					flex.setWidget(9, 1, dateTimeShortHoursField);
					
					flex.setWidget(10, 0, labelFieldDT_ShortMinute);
					flex.setWidget(10, 1, dateTimeShortMinuteField);
					
					flex.setWidget(11, 0, labelFieldDT_ShortSec);
					flex.setWidget(11, 1, dateTimeShortSecField);
					
					

					flex.setWidget(13, 0, labelFieldD);
					flex.setWidget(13, 1, dateField);

					flex.setWidget(14, 0, labelFieldDTF);
					flex.setWidget(14, 1, dateTimeOnlyField);

					flex.setWidget(15, 0, labelFieldLocation);
					flex.setWidget(15, 1, locationSelector);
					
					flex.setWidget(18, 0, labelFieldLocationForFalseLabelField);
					flex.setWidget(18, 1, locationSelectorForFalseMode);
					

					flex.setWidget(19, 0, numericRangeSliderLbl);
					flex.setWidget(19, 1, numericRangeSlider);

					flex.setWidget(20, 0, stringRangeSliderLbl);
					flex.setWidget(20, 1, stringRangeSlider);
					
					flex.setWidget(25, 0, spinnerFieldLabel);
					flex.setWidget(25, 1, spinnerField);
					
					flex.setWidget(26, 0, spinnerPercentLabel);
					flex.setWidget(26, 1, percentSpinnerField);
					
					flex.setWidget(27, 0, numberFieldLabel);
					flex.setWidget(27, 1, numberFieldPanel);

					RootPanel.get().add(flex);

				}

				@Override
				public void onFailure(com.google.code.gwt.geolocation.client.PositionError error) {
					System.out.println(" "+error.getMessage());

				}
			});
		}

	}
	
	private Configuration getStringRangeSliderFieldConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(StateField.STATEFIELD_MODE, StateField.STATEFIELDMODE_ENUM);
		configuration.setPropertyByName(StateField.STATEFIELD_TYPE, StateField.STATEFIELDTYPE_STRINGRANGE);
		configuration.setPropertyByName(StringRangeSliderField.STRING_RANGESLIDER_OPTIONLIST, "Me,Private,Restricted,Public");
		return configuration;
	}
	
	private Configuration getNumericRangeSliderFieldConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(StateField.STATEFIELD_MODE, StateField.STATEFIELDMODE_ENUM);
		configuration.setPropertyByName(StateField.STATEFIELD_TYPE, StateField.STATEFIELDTYPE_NUMERICRANGE);
		configuration.setPropertyByName(NumericRangeSliderField.NUMERIC_RANGESLIDER_MAXVALUE, 500);
		configuration.setPropertyByName(NumericRangeSliderField.NUMERIC_RANGESLIDER_MINVALUE, 100);
		configuration.setPropertyByName(NumericRangeSliderField.NUMERIC_RANGESLIDER_STEPVALUE, 100);
		return configuration;
	}
	
	private Configuration getDateTimeFieldConfiguration(String modeSelection,String datetimefieldTimeonly, String modeTimeValue) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(DateTimeField.DATETIMEFIELD_MODE, modeSelection);
		configuration.setPropertyByName(DateTimeField.DATETIMEFIELD_TYPE, datetimefieldTimeonly);
		if(modeTimeValue!=null)
		  configuration.setPropertyByName(modeTimeValue, modeTimeValue);
		
		return configuration;
	}
	
	private Configuration getLocationSelectorConf() {
		Configuration configuration = new Configuration();
		
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE, "imgaes/locationMarker1.png");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CURRENT_LOCATION_TEXTFIELD, "imgaes/locationMarker1.png");
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
	
	private Configuration getStateFieldConfiguration(String stateFieldType, String qname, String operationName) {

		Configuration configuration = new Configuration();
		configuration.setPropertyByName(StateField.STATEFIELD_MODE, stateFieldType);
		configuration.setPropertyByName(StateField.STATEFIELD_QUERY, qname);
		configuration.setPropertyByName(StateField.STATEFIELD_OPERATION, operationName);
		return configuration;
	}
	
	public Configuration getCheckboxFieldConfiguration(String text) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(CheckboxField.CHECKBOXFIELD_DISPLAYTEXT, text);
		return configuration;
	}
	
	private Configuration getCheckboxGroupFieldConfiguration(String selectMode, String basePanel) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(CheckboxGroupField.CHECKBOX_SELECT_MODE, selectMode);
		configuration.setPropertyByName(CheckboxGroupField.CHECKBOX_BASEPANEL, basePanel);
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
}
