package in.appops.client.common.components;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.CheckboxField;
import in.appops.client.common.fields.CheckboxField.CheckBoxFieldConstant;
import in.appops.client.common.fields.CheckboxGroupField;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.LocationSelector;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.core.entity.GeoLocation;
import in.appops.platform.core.shared.Configuration;

import com.google.code.gwt.geolocation.client.Coordinates;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LocationHomeSelector extends Composite implements FieldEventHandler,ClickHandler {

	private VerticalPanel mainPanel;
	private HorizontalPanel upperHorizontalPanel;
	private HorizontalPanel errorHorizontalPanel;
	private VerticalPanel lowerVerticalPanel;
	private LocationSelector locationSelector ;
	private Coordinates coords;
	private CheckboxField mapFalseModeCheckBoxField ;
	private CheckboxField mapTrueeModeCheckBoxField ;
	private FlexTable mapModeFalseTable;
	private FlexTable mapModeTrueTable;
	private TextField widthMapTrueTextFieldTB; 
	private TextField heightMapTrueTextFieldTB;
	private TextField zoomLevelMapTrueTextFieldTB;
	private TextField widthMapFalseTextFieldTB;
	private TextField heightMapFalseTextFieldTB ;
	private TextField zoomLevelMapFalseTextFieldTB;
	private Button doneButton;
	private CheckboxGroupField groupCheckbox ;
	private VerticalPanel configurationMappanel ;
	private String selectedFieldText ;
	
	public LocationHomeSelector() {
		initialize();
		initWidget(mainPanel);
	}
	
	public void initialize(){
		mainPanel = new VerticalPanel(); 
		upperHorizontalPanel = new HorizontalPanel();
		lowerVerticalPanel = new VerticalPanel();
		locationSelector = new LocationSelector();
		mapModeTrueTable = new FlexTable();
		mapModeFalseTable = new FlexTable();
		errorHorizontalPanel = new HorizontalPanel(); 
		
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}
	
	public void createUi(){
		try{
			VerticalPanel configurationSelectionpanel = new VerticalPanel();
			configurationMappanel = new VerticalPanel();
			
			locationSelector.setConfiguration(getLocationSelectorConfForFalse());
			locationSelector.setMapMode(false);
			GeoLocation geoLocation = new GeoLocation();
			geoLocation.setLatitude(coords.getLatitude());
			geoLocation.setLongitude(coords.getLongitude());
			AppEnviornment.setCurrentGeolocation(geoLocation);
			
			
			groupCheckbox = new CheckboxGroupField();
			Configuration configuration = getCheckboxGroupFieldConfiguration(CheckboxGroupField.CHECKBOX_SINGLESELECT,CheckboxGroupField.CHECKBOX_HORIZONTALBASEPANEL);
			groupCheckbox.setConfiguration(configuration);
			groupCheckbox.create();
			groupCheckbox.addCheckItem("True map mode",true);
			groupCheckbox.addCheckItem("False map mode",false);
			configurationSelectionpanel.add(groupCheckbox);
			selectedFieldText = "True map mode";
			
			createUserConfigurationFieldsForTrueMode();
			
			configurationMappanel.add(mapModeTrueTable);
			locationSelector.create();
			
			upperHorizontalPanel.add(locationSelector);
			lowerVerticalPanel.add(configurationSelectionpanel);
			lowerVerticalPanel.add(configurationMappanel);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		mainPanel.add(upperHorizontalPanel);
		mainPanel.setCellHeight(upperHorizontalPanel, "60%");
		
		mainPanel.add(lowerVerticalPanel);
		mainPanel.setCellHeight(lowerVerticalPanel, "40%");
	}

	private void createUserConfigurationFieldsForTrueMode() {
		mapModeTrueTable.clear();
		
		try{
			LabelField mapWidthLabel = new LabelField();
			mapWidthLabel.setFieldValue("Map width :");
			mapWidthLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
			mapWidthLabel.create();
			
			widthMapTrueTextFieldTB = new TextField();
			widthMapTrueTextFieldTB.setFieldValue("");
			widthMapTrueTextFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, "appops-TextField", null, null));
			widthMapTrueTextFieldTB.create();
			
			LabelField mapHeightLabel = new LabelField();
			mapHeightLabel.setFieldValue("Map height :");
			mapHeightLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
			mapHeightLabel.create();
			
			heightMapTrueTextFieldTB = new TextField();
			heightMapTrueTextFieldTB.setFieldValue("");
			heightMapTrueTextFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, "appops-TextField", null, null));
			heightMapTrueTextFieldTB.create();
			
			
			LabelField mapZommLevelLabel = new LabelField();
			mapZommLevelLabel.setFieldValue("Map zoom level :");
			mapZommLevelLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
			mapZommLevelLabel.create();
			
			zoomLevelMapTrueTextFieldTB = new TextField();
			zoomLevelMapTrueTextFieldTB.setFieldValue("");
			zoomLevelMapTrueTextFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, "appops-TextField", null, null));
			zoomLevelMapTrueTextFieldTB.create();
			
			doneButton = new Button("Apply");
			doneButton.addClickHandler(this);
			doneButton.setStylePrimaryName("appops-Button");
			
			mapModeTrueTable.setWidget(0, 0, mapWidthLabel);
			mapModeTrueTable.setWidget(0, 2, widthMapTrueTextFieldTB);
			
			mapModeTrueTable.setWidget(1, 0, mapHeightLabel);
			mapModeTrueTable.setWidget(1, 2, heightMapTrueTextFieldTB);
			
			mapModeTrueTable.setWidget(2, 0, mapZommLevelLabel);
			mapModeTrueTable.setWidget(2, 2, zoomLevelMapTrueTextFieldTB);
			
			mapModeTrueTable.setWidget(3, 0, doneButton);
		}catch(Exception e){
			e.printStackTrace();
		}
		
				
	}

	private void createUserConfigurationFieldsForFalseMode() {
		mapModeFalseTable.clear();
		
		try{
			LabelField mapWidthLabel = new LabelField();
			mapWidthLabel.setFieldValue("Map width :");
			mapWidthLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
			mapWidthLabel.create();
			
			widthMapFalseTextFieldTB = new TextField();
			widthMapFalseTextFieldTB.setFieldValue("");
			widthMapFalseTextFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, "appops-TextField", null, null));
			widthMapFalseTextFieldTB.create();
			
			LabelField mapHeightLabel = new LabelField();
			mapHeightLabel.setFieldValue("Map height :");
			mapHeightLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
			mapHeightLabel.create();
			
			heightMapFalseTextFieldTB = new TextField();
			heightMapFalseTextFieldTB.setFieldValue("");
			heightMapFalseTextFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, "appops-TextField", null, null));
			heightMapFalseTextFieldTB.create();
			
			
			LabelField mapZommLevelLabel = new LabelField();
			mapZommLevelLabel.setFieldValue("Map zoom level :");
			mapZommLevelLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
			mapZommLevelLabel.create();
			
			zoomLevelMapFalseTextFieldTB = new TextField();
			zoomLevelMapFalseTextFieldTB.setFieldValue("");
			zoomLevelMapFalseTextFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, "appops-TextField", null, null));
			zoomLevelMapFalseTextFieldTB.create();
			
			doneButton = new Button("Apply");
			doneButton.addClickHandler(this);
			doneButton.setStylePrimaryName("appops-Button");
			
			mapModeFalseTable.setWidget(0, 0, mapWidthLabel);
			mapModeFalseTable.setWidget(0, 2, widthMapFalseTextFieldTB);
			
			mapModeFalseTable.setWidget(1, 0, mapHeightLabel);
			mapModeFalseTable.setWidget(1, 2, heightMapFalseTextFieldTB);
			
			mapModeFalseTable.setWidget(2, 0, mapZommLevelLabel);
			mapModeFalseTable.setWidget(2, 2, zoomLevelMapFalseTextFieldTB);
			
			mapModeFalseTable.setWidget(3, 0, doneButton);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
	}

	private Configuration getCheckboxGroupFieldConfiguration(String selectMode, String basePanel) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(CheckboxGroupField.CHECKBOX_SELECT_MODE, selectMode);
		configuration.setPropertyByName(CheckboxGroupField.CHECKBOX_BASEPANEL, basePanel);
		return configuration;
	}
	
	private Configuration getTextFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, visibleLines);
		configuration.setPropertyByName(TextFieldConstant.BF_READONLY, readOnly);
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(TextFieldConstant.BF_DCLS, secondaryCss);
		return configuration;
	}
	
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return configuration;
	}
	
	public Configuration getCheckboxFieldConfiguration(String text) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, text);
		return configuration;
	}
	private Configuration getLocationSelectorConf() {
		Configuration configuration = new Configuration();
		
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE, "images/locationMarker1.png");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CURRENT_LOCATION_TEXTFIELD, "images/locationMarker1.png");
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, "appops-TextField");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CHOOSE_LOCATION_BTN, "chooseLocationBtn");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_POPUPPANEL, "currentLocationField");
		configuration.setPropertyByName(LocationSelector.MAP_ZOOM, "8");
		configuration.setPropertyByName(LocationSelector.CHANGE_LOCATION_IMAGE_URL, "images/iconEdit.png");
		configuration.setPropertyByName(LocationSelector.DONE_SELECTION_IMAGE_URL, "images/iconTickBlackCircle.png");
		configuration.setPropertyByName(LocationSelector.MAP_WIDTH, "300px");
		configuration.setPropertyByName(LocationSelector.MAP_HEIGHT, "200px");
		
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
	public Coordinates getCoords() {
		return coords;
	}

	public void setCoords(Coordinates coords) {
		this.coords = coords;
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		 if(sender instanceof Button){
			errorHorizontalPanel.clear();
			if(selectedFieldText.equals("True map mode")){
				 
				 if(!widthMapTrueTextFieldTB.getFieldText().equals("") && !heightMapTrueTextFieldTB.getFieldText().equals("") && !zoomLevelMapTrueTextFieldTB.getFieldText().equals("")){
					 upperHorizontalPanel.clear();
						 		
				
					 locationSelector.setConfiguration(getLocationSelectorConfForFalse(widthMapTrueTextFieldTB.getFieldText(),heightMapTrueTextFieldTB.getFieldText(),zoomLevelMapTrueTextFieldTB.getFieldText()));
					 locationSelector.setMapMode(false);
					 GeoLocation geoLocation = new GeoLocation();
					 geoLocation.setLatitude(coords.getLatitude());
					 geoLocation.setLongitude(coords.getLongitude());
					 AppEnviornment.setCurrentGeolocation(geoLocation);
					 locationSelector.create();
					 upperHorizontalPanel.add(locationSelector);
				 }else{
					 try{
					    LabelField errorLabel = new LabelField();
						errorLabel.setFieldValue("*Please enter all details");
						errorLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-errorLabel", null, null));
						errorLabel.create();
						errorHorizontalPanel.add(errorLabel);
						mapModeTrueTable.setWidget(3, 2, errorHorizontalPanel);
					 }catch(Exception e){
						 e.printStackTrace();
					 }
				 }
			}else if(selectedFieldText.endsWith("False map mode")){
					
				if(!widthMapFalseTextFieldTB.getFieldText().equals("") && !heightMapFalseTextFieldTB.getFieldText().equals("") && !zoomLevelMapFalseTextFieldTB.getFieldText().equals("")){
					upperHorizontalPanel.clear();
					 
				
					locationSelector.setConfiguration(getLocationSelectorConf(widthMapFalseTextFieldTB.getFieldText(),heightMapFalseTextFieldTB.getFieldText(),zoomLevelMapFalseTextFieldTB.getFieldText()));
					locationSelector.setMapMode(true);
					/*locationSelector.setMapHeight("400px");
					locationSelector.setMapWidth("600px");*/
					GeoLocation geoLocation = new GeoLocation();
					geoLocation.setLatitude(coords.getLatitude());
					geoLocation.setLongitude(coords.getLongitude());
					AppEnviornment.setCurrentGeolocation(geoLocation);
					locationSelector.setCoordinates(coords);
					locationSelector.create();
					upperHorizontalPanel.add(locationSelector);
				}else{
					try{
					    LabelField errorLabel = new LabelField();
						errorLabel.setFieldValue("*Please enter all details");
						errorLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-errorLabel", null, null));
						errorLabel.create();
						errorHorizontalPanel.add(errorLabel);
						mapModeFalseTable.setWidget(3, 2, errorHorizontalPanel);
					 }catch(Exception e){
						 e.printStackTrace();
					 }
				}
			}
		}
	}

	private Configuration getLocationSelectorConf(String mapWidth, String mapHeight,
			String mapZoomLevel) {
		Configuration configuration = new Configuration();
		
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE, "images/locationMarker1.png");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CURRENT_LOCATION_TEXTFIELD, "images/locationMarker1.png");
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, "appops-TextField");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CHOOSE_LOCATION_BTN, "chooseLocationBtn");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_POPUPPANEL, "currentLocationField");
		configuration.setPropertyByName(LocationSelector.MAP_ZOOM, mapZoomLevel);
		configuration.setPropertyByName(LocationSelector.CHANGE_LOCATION_IMAGE_URL, "images/iconEdit.png");
		configuration.setPropertyByName(LocationSelector.DONE_SELECTION_IMAGE_URL, "images/iconTickBlackCircle.png");
		configuration.setPropertyByName(LocationSelector.MAP_WIDTH, mapWidth+"px");
		configuration.setPropertyByName(LocationSelector.MAP_HEIGHT, mapHeight+"px");
		
		return configuration;
	}

	private Configuration getLocationSelectorConfForFalse(String mapWidth,
			String mapHeight, String mapZoomLevel) {
		Configuration configuration = new Configuration();
		
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE, "images/locationMarker1.png");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CURRENT_LOCATION_TEXTFIELD, "images/locationMarker1.png");
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, "appops-TextField");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_CHOOSE_LOCATION_BTN, "chooseLocationBtn");
		configuration.setPropertyByName(LocationSelector.LOCATION_SELECTOR_POPUPPANEL, "currentLocationField");
		configuration.setPropertyByName(LocationSelector.MAP_ZOOM, mapZoomLevel);
		configuration.setPropertyByName(LocationSelector.CHANGE_LOCATION_IMAGE_URL, "images/iconEdit.png");
		configuration.setPropertyByName(LocationSelector.DONE_SELECTION_IMAGE_URL, "images/iconTickBlackCircle.png");
		configuration.setPropertyByName(LocationSelector.MAP_WIDTH, mapWidth+"px");
		configuration.setPropertyByName(LocationSelector.MAP_HEIGHT, mapHeight+"px");
		
		return configuration;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		switch (eventType) {
		case FieldEvent.CHECKBOX_SELECT: {
			CheckBox checkbox = (CheckBox) event.getEventData();
			 selectedFieldText = checkbox.getText();
			if(selectedFieldText.equals("True map mode")) {
				upperHorizontalPanel.clear();
				configurationMappanel.clear();
				
			    /*mapFalseModeCheckBoxField.setFieldValue("true");
			    mapFalseModeCheckBoxField.resetField();
				mapModeTrueTable.clear();
				createUserConfigurationFieldsForFalseMode();
				mapTrueeModeCheckBoxField.setFieldValue("false");
			    mapTrueeModeCheckBoxField.resetField();*/
				createUserConfigurationFieldsForTrueMode();
			    configurationMappanel.add(mapModeTrueTable);
			    
			    locationSelector.setConfiguration(getLocationSelectorConfForFalse());
				locationSelector.setMapMode(false);
				GeoLocation geoLocation = new GeoLocation();
				geoLocation.setLatitude(coords.getLatitude());
				geoLocation.setLongitude(coords.getLongitude());
				AppEnviornment.setCurrentGeolocation(geoLocation);
				locationSelector.create();
				upperHorizontalPanel.add(locationSelector);
			} else if(selectedFieldText.equals("False map mode")) {
				upperHorizontalPanel.clear();
				configurationMappanel.clear();
				
			    /*mapTrueeModeCheckBoxField.setFieldValue("true");
			    mapTrueeModeCheckBoxField.resetField();
				mapModeFalseTable.clear();
				createUserConfigurationFieldsForTrueMode();
				mapFalseModeCheckBoxField.setFieldValue("false");
				mapFalseModeCheckBoxField.resetField();*/
				createUserConfigurationFieldsForFalseMode();
				configurationMappanel.add(mapModeFalseTable);
				
				locationSelector.setConfiguration(getLocationSelectorConf());
					locationSelector.setMapMode(true);
					/*locationSelector.setMapHeight("400px");
					locationSelector.setMapWidth("600px");*/
					GeoLocation geoLocation = new GeoLocation();
					geoLocation.setLatitude(coords.getLatitude());
					geoLocation.setLongitude(coords.getLongitude());
					AppEnviornment.setCurrentGeolocation(geoLocation);
					locationSelector.setCoordinates(coords);
					locationSelector.create();
					upperHorizontalPanel.add(locationSelector);
			}
			break;
		}
		case FieldEvent.CHECKBOX_DESELECT: {
			CheckBox checkbox = (CheckBox) event.getEventData();
			String text = checkbox.getText();
			if(text.equals("True map mode")) {
				
			} else if(text.equals("False map mode")) {
				
			}
			
			break;
		}
		default:
			break;
		}
	}
}
