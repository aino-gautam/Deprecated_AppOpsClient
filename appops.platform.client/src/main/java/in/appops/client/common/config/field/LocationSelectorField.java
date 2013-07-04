package in.appops.client.common.config.field;

import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;

/**
Field class to represent a {@link LocationSelectorField}
@author pallavi@ensarm.com

<p>
<h3>Configuration</h3>
<a href="LocationSelectorField.LocationSelectorFieldConstant.html">Available configurations</a>
</p>

<p>
<h3>Example</h3>
LocationSelectorField locationField = new LocationSelectorField();<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_ZOOMLEVEL, 8);<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.LOCFD_WIDTH, "400px");<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_HEIGHT, "200px");<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_LATITUDE, latitude);<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_LONGITUDE, longitude);<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_SHOWINPOPUP, false);<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_DONEBTN_CSS, "appops-Button");<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_LOCATION_IMG_CSS, "locationImage");<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_SEARCHBOX_PCLS, "locationSearchBox");<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_SEARCHBOX_DCLS, "fadeInRight");<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_ERRPOS, TextFieldConstant.BF_ERRINLINE);<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.LOCNFD_INVALID_LOCNMSG, "Invalid location");<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.SEARCHFD_EVENT, FieldEvent.WORDENTERED);<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.DONEBTN_EVENT, FieldEvent.LOCATION_CHANGED);<br>
configuration.setPropertyByName(LocationSelectorFieldConstant.LOCN_IMG_EVENT, FieldEvent.SHOW_MAP_IN_POPUP);<br>
locationField.setConfiguration(configuration);<br>
locationField.configure();<br>
locationField.create();<br>
</p>*/
public class LocationSelectorField extends BaseField implements FieldEventHandler {

	private LocationMapWidget mapWidget;
	private HorizontalPanel basePanel;
	private LabelField locationLabelField;
	private ImageField locationIconField ;
	private PopupPanel popupPanelForMap;
	
	public LocationSelectorField(){
		
	}
	
	/************************************************************************/
	@Override
	public void create() {
	  getBasePanel().add(basePanel, DockPanel.CENTER);
	}
	
	@Override
	public void configure() {
				
		mapWidget = new LocationMapWidget(getLatLang(),getZoomlevel(),getMapWidth(),getMapHeight(),getSearchFieldConf(),getDoneBtnConfiguration());
		
		mapWidget.createMap();
		
		basePanel = new HorizontalPanel();
		
		if(isMapInPopup()){
			
			locationIconField = new ImageField();
			locationIconField.setConfiguration(getImageFieldConfiguration());
			locationIconField.configure();
			locationIconField.create();
			
			locationLabelField = new LabelField();
			if(getCurrentAddress()!=null){
				locationLabelField.setConfiguration(getCurrentLocationLblConf(getCurrentLocationLblCss()));
				locationLabelField.setValue(getCurrentAddress());
			}else{
				Configuration conf = getCurrentLocationLblConf(getCurrentLocationLblCss());
				locationLabelField.setConfiguration(conf);
			}
			
			locationLabelField.configure();
			locationLabelField.create();
			
			basePanel.add(locationIconField);
			basePanel.add(locationLabelField);
		}else{
			basePanel.clear();
			mapWidget.setStylePrimaryName("locationMapWidget");
			basePanel.add(mapWidget);
		}
		
		if(getBaseFieldPrimCss()!=null)
			basePanel.setStylePrimaryName(getBaseFieldPrimCss());
		
		
		if(getBaseFieldCss()!=null)
			basePanel.setStylePrimaryName(getBaseFieldCss());
		
	
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
	}
	
	/***************************************** ************************************************************/
	
	/**
	 * Method returns whether map to be shown in popup or not. Defaults to false.
	 * @return
	 */
	private Boolean isMapInPopup() {
		
		Boolean isMapInPopup = false;
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_SHOWINPOPUP) != null) {
			
			isMapInPopup = (Boolean) getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_SHOWINPOPUP);
		}
		return isMapInPopup;
	}
	
	/**
	 * Method returns blobId for popup image icon.
	 * @return
	 */
	private String getImageBlobForMapPopup() {
		
		String isMapInPopup = "images/locationMarker1.png";
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_POPUP_ICON_BLOB) != null) {
			
			isMapInPopup = (String) getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_POPUP_ICON_BLOB);
		}
		return isMapInPopup;
	}
	
	/**
	 * Method returns height for map. Defaults to 800px;
	 * @return
	 */
	private String getMapHeight() {
		
		String height = "800px";
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_HEIGHT) != null) {
			
			height = (String) getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_HEIGHT);
		}
		return height;
	}
	
	/**
	 * Method returns search textField primary css ;
	 * @return
	 */
	private String getSearchFieldPrimaryCss() {
		
		String searchFieldcss = null;
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_SEARCHBOX_PCLS) != null) {
			
			searchFieldcss = (String) getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_SEARCHBOX_PCLS);
		}
		return searchFieldcss;
	}
	
	/**
	 * Method returns search textField dependent css ;
	 * @return
	 */
	private String getSearchFieldDependentCss() {
		
		String dependentcss = null;
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_SEARCHBOX_DCLS) != null) {
			
			dependentcss = (String) getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_SEARCHBOX_DCLS);
		}
		return dependentcss;
	}
	
	
	/**
	 * Method returns done button field css ;
	 * @return
	 */
	private String getDoneBtnCss() {
		
		String doneBtncss = null;
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_DONEBTN_CSS) != null) {
			
			doneBtncss = (String) getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_DONEBTN_CSS);
		}
		return doneBtncss;
	}
	
	/**
	 * Method returns current location label css  ;
	 * @return
	 */
	private String getCurrentLocationLblCss() {
		
		String doneBtncss = null;
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_CURR_LOC_LBL_CSS) != null) {
			
			doneBtncss = (String) getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_CURR_LOC_LBL_CSS);
		}
		return doneBtncss;
	}
	
	/**
	 * Method returns width for map. Defaults to 650px;
	 * @return
	 */
	private String getMapWidth() {
		
		String height = "650px";
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCFD_WIDTH) != null) {
			
			height = (String) getConfigurationValue(LocationSelectorFieldConstant.LOCFD_WIDTH);
		}
		return height;
	}
	
	/**
	 * Method returns zoomlevel for map. Defaults to 8;
	 * @return
	 */
	private Integer getZoomlevel() {
		
		Integer zoomLevel = 8;
		
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_ZOOMLEVEL) != null) {
			
			zoomLevel = (Integer) getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_ZOOMLEVEL);
		}
		return zoomLevel;
	}
	
	/**
	 * Method returns the latitude. If not specified then returns null.
	 * @return
	 */
	private Double getLatitude() {
		
		Double latitude = null;
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_LATITUDE) != null) {
			
			latitude = (Double) getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_LATITUDE);
		}
		return latitude;
	}
	
	/**
	 * Method returns the longitude. If not specified then returns null.
	 * @return
	 */
	private Double getLongitude() {
		
		Double longitude = null;
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_LONGITUDE) != null) {
			
			longitude = (Double) getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_LONGITUDE);
		}
		return longitude;
	}
	
	/**
	 * Method returns the current address;
	 * @return
	 */
	private String getCurrentAddress() {
		
		String address = null;
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_CURRENT_ADDRESS) != null) {
			
			address = (String) getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_CURRENT_ADDRESS);
		}
		return address;
	}
	
	/**
	 * Method returns the location image css;
	 * @return
	 */
	private String getLocationImageCss() {
		
		String imgCss = null;
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_LOCATION_IMG_CSS) != null) {
			
			imgCss = (String) getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_LOCATION_IMG_CSS);
		}
		return imgCss;
	}
	
	/**
	 * Method returns the event that will be fired when user clicks on the location image;
	 * @return
	 */
	private Integer getLocationImageClickEvent() {
		
		Integer eventType = null;
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCN_IMG_EVENT) != null) {
			
			eventType = (Integer) getConfigurationValue(LocationSelectorFieldConstant.LOCN_IMG_EVENT);
		}
		return eventType;
	}
	
	/**
	 * Method returns the event that will be fired when user clicks on the location image;
	 * @return
	 */
	private Integer getSearchFieldEnteredEvent() {
		
		Integer eventType = null;
		if(getConfigurationValue(LocationSelectorFieldConstant.SEARCHFD_EVENT) != null) {
			
			eventType = (Integer) getConfigurationValue(LocationSelectorFieldConstant.SEARCHFD_EVENT);
		}
		return eventType;
	}
	
	/**
	 * Method returns the event that will be fired when user clicks on the location image;
	 * @return
	 */
	private Integer getDoneBtnEvent() {
		
		Integer event = null;
		if(getConfigurationValue(LocationSelectorFieldConstant.DONEBTN_EVENT) != null) {
			
			event = (Integer) getConfigurationValue(LocationSelectorFieldConstant.DONEBTN_EVENT);
		}
		return event;
	}
	
	/**
	 * Method returns the error position for the search text field ;
	 * @return
	 */
	private String getSearchFieldErrorPos() {
		
		String pos = null;
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_ERRPOS) != null) {
			
			pos = (String) getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_ERRPOS);
		}
		return pos;
	}
	
	/**
	 * Method returns the error msg used for invalid location;
	 * @return
	 */
	private String getSearchInvalidMsg() {
		
		String pos = null;
		if(getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_INVALID_LOCNMSG) != null) {
			
			pos = (String) getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_INVALID_LOCNMSG);
		}
		return pos;
	}
	
	
	/******************************* *****************************************************************/
	
	
	/**
	 * Method returns the latLang object.
	 * @return
	 */
	private LatLng getLatLang() {
		
		LatLng latLng = new LatLng(getLatitude(),getLongitude());
		
		return latLng;
	}
	
	/**
	 * Method shows the map in popup panel.
	 */
	private void showMapInPopupPanel(){
		if(popupPanelForMap ==null){
			popupPanelForMap = new PopupPanel();
			popupPanelForMap.setAutoHideEnabled(true);
			popupPanelForMap.add(mapWidget);
			
	}
		popupPanelForMap.showRelativeTo(locationIconField);
		
	}
	
	/**
	 * Method creates the configuration for searchTexField used in LocationMapWidget from the configuration 
	 * which is passed to the LocationSelectorField and return.
	 * @return searchTextfield configuration.
	 */
	private Configuration getSearchFieldConf(){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, getSearchFieldPrimaryCss());
		configuration.setPropertyByName(TextFieldConstant.BF_DCLS, getSearchFieldDependentCss());
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter location");
		configuration.setPropertyByName(TextFieldConstant.TF_VALUE_ENTERED_EVENT, getSearchFieldEnteredEvent());
		configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, getSearchFieldErrorPos());
		configuration.setPropertyByName(TextFieldConstant.BF_INVLDMSG, getSearchInvalidMsg());
		return configuration;
	}
	
	/**
	 * Method creates the configuration for image field and return.
	 * @return popup imageField configuration.
	 */
	private Configuration getImageFieldConfiguration(){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, getImageBlobForMapPopup());
		configuration.setPropertyByName(ImageFieldConstant.IMGFD_CLICK_EVENT, getLocationImageClickEvent());
		configuration.setPropertyByName(ImageFieldConstant.BF_PCLS,getLocationImageCss());
		configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "Click here...");
		return configuration;
	}
	
	/**
	 * Method creates the configuration for done button used in LocationMapWidget from the configuration 
	 * which is passed to the LocationSelectorField and return.
	 * @return doneButtonField configuration.
	 */
	private Configuration getDoneBtnConfiguration(){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Done");
		configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,getDoneBtnCss());
		configuration.setPropertyByName(ButtonFieldConstant.BTNFD_CLICK_EVENT,getDoneBtnEvent());
		configuration.setPropertyByName(ButtonFieldConstant.BTNFD_TITLE, "Change location");
		return configuration;
		
	}
	
	private Configuration getCurrentLocationLblConf(String primaryCss){
		
		Configuration conf = new Configuration();
		conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		
		return conf;
	}
	
	/********************************************************************************************/
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		switch (eventType) {
		case FieldEvent.SHOW_MAP_IN_POPUP: {
			showMapInPopupPanel();
			break;
		}case FieldEvent.CHANGE_LOCATION:{
			locationLabelField.setValue(event.getEventData().toString());
			popupPanelForMap.hide();
			break;
		}case FieldEvent.LOCATION_RECIEVED:{
			locationLabelField.setValue(event.getEventData().toString());
			break;
		}
		default:
			break;
		}
	}
	
	
	/************************************** *******************************************************/
	
	public interface LocationSelectorFieldConstant extends BaseFieldConstant{
		
		/** Specifies location will be displayed in popup or not. **/
		public static final String LOCNFD_SHOWINPOPUP = "isMapInPopup";
		
		/** Specifies blobId for  **/
		public static final String LOCNFD_POPUP_ICON_BLOB = "blobId";
		
		/** Specifies height for map field **/
		public static final String LOCNFD_HEIGHT = "height";
		
		/** Specifies width for map field **/
		public static final String LOCFD_WIDTH = "width";
		
		/** Specifies zoom level for mapfield **/
		public static final String LOCNFD_ZOOMLEVEL = "zoomlevel";
		
		/** Specifies the latitude **/
		public static final String LOCNFD_LATITUDE = "latitude";
		
		/** Specifies the longitude **/
		public static final String LOCNFD_LONGITUDE = "longitude";
		
		/** Specifies the current address **/
		public static final String LOCNFD_CURRENT_ADDRESS = "currentAddress";
		
		/** Specifies the search textfield primary css **/
		public static final String LOCNFD_SEARCHBOX_PCLS = "searchBoxPrimarycss";
		
		/** Specifies the search textfield dependant css **/
		public static final String LOCNFD_SEARCHBOX_DCLS = "searchBoxDependantcss";
		
		/** Specifies the done button css **/
		public static final String LOCNFD_DONEBTN_CSS = "doneBtnCss";
		
		/** Specifies the current location label css **/
		public static final String LOCNFD_CURR_LOC_LBL_CSS = "currentLocationLabelCss";
		
		/** Specifies the location image css **/
		public static final String LOCNFD_LOCATION_IMG_CSS = "locationImageCss";
		
		/** Specifies the event which will be fired on click of location image **/
		public static final String LOCN_IMG_EVENT = "locationImageEvent";
		
		/** Specifies the event which will be fired when user enters location to search **/
		public static final String SEARCHFD_EVENT = "searchFieldEvent";
		
		/** Specifies the event which will be fired when user clicks on done button **/
		public static final String DONEBTN_EVENT = "doneBtnEvent";

		public static final String LOCNFD_ERRPOS = "errorPosForInvalidLocation";

		public static final String LOCNFD_INVALID_LOCNMSG = "invalidMsg";
		
	}

}