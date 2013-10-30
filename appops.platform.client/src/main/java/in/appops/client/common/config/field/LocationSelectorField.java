package in.appops.client.common.config.field;

import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.textfield.TextField.TextFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

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
	private Logger logger = Logger.getLogger(getClass().getName());
	private static String SHOWMAP_IMAGEID = "showMapInPopup";
	private static String CHANGE_LOCATION = "changeLocation";
	private static String SEARCH_LOCATION = "searchLocation";

	public LocationSelectorField(){
		
	}
	
	/************************************************************************/
	/**
	 * Method creates the location selector field.
	 */
	@Override
	public void create() {
	  try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In create  method ");
			getBasePanel().add(basePanel, DockPanel.CENTER);
	} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In create  method :"+e);

	}
	}
	
	/**
	 * Method configure the location selector field.
	 */
	@Override
	public void configure() {
				
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In configure  method ");
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
			
			
			if(getBaseFieldDependentCss()!=null)
				basePanel.setStylePrimaryName(getBaseFieldDependentCss());
			

			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In configure  method :"+e);

		}
	}
	
	/***************************************** ************************************************************/
	
	/**
	 * Returns whether map to be show in popup or not. Defaults to false.
	 * @return
	 */
	private Boolean isMapInPopup() {
		
		Boolean isMapInPopup = false;
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In isMapInPopup  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_SHOWINPOPUP) != null) {
				
				isMapInPopup = (Boolean) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_SHOWINPOPUP);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In isMapInPopup  method :"+e);

		}
		return isMapInPopup;
	}
	
	/**
	 * Method returns blobId for popup image icon.
	 * @return
	 */
	private String getImageBlobForMapPopup() {
		
		String isMapInPopup = "images/locationMarker1.png";
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getImageBlobForMapPopup  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_POPUP_ICON_BLOB) != null) {
				
				isMapInPopup = (String) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_POPUP_ICON_BLOB);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getImageBlobForMapPopup  method :"+e);

		}
		return isMapInPopup;
	}
	
	/**
	 * Method returns height for map. Defaults to 800px;
	 * @return
	 */
	private String getMapHeight() {
		
		String height = "800px";
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getMapHeight  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_HEIGHT) != null) {
				
				height = (String) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_HEIGHT);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getMapHeight  method :"+e);

		}
		return height;
	}
	
	/**
	 * Method returns search textField primary css ;
	 * @return
	 */
	private String getSearchFieldPrimaryCss() {
		
		String searchFieldcss = null;
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getSearchFieldPrimaryCss  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_SEARCHBOX_PCLS) != null) {
				
				searchFieldcss = (String) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_SEARCHBOX_PCLS);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getSearchFieldPrimaryCss  method :"+e);

		}
		return searchFieldcss;
	}
	
	/**
	 * Method returns search textField dependent css ;
	 * @return
	 */
	private String getSearchFieldDependentCss() {
		
		String dependentcss = null;
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getSearchFieldDependentCss  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_SEARCHBOX_DCLS) != null) {
				
				dependentcss = (String) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_SEARCHBOX_DCLS);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getSearchFieldDependentCss  method :"+e);

		}
		return dependentcss;
	}
	
	
	/**
	 * Method returns done button field css ;
	 * @return
	 */
	private String getDoneBtnCss() {
		
		String doneBtncss = null;
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getDoneBtnCss  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_DONEBTN_CSS) != null) {
				
				doneBtncss = (String) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_DONEBTN_CSS);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getDoneBtnCss  method :"+e);

		}
		return doneBtncss;
	}
	
	/**
	 * Method returns current location label css  ;
	 * @return
	 */
	private String getCurrentLocationLblCss() {
		
		String doneBtncss = null;
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getCurrentLocationLblCss  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_CURR_LOC_LBL_CSS) != null) {
				
				doneBtncss = (String) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_CURR_LOC_LBL_CSS);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getCurrentLocationLblCss  method :"+e);

		}
		return doneBtncss;
	}
	
	/**
	 * Method returns width for map. Defaults to 650px;
	 * @return
	 */
	private String getMapWidth() {
		
		String height = "650px";
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getMapWidth  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCFD_WIDTH) != null) {
				
				height = (String) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCFD_WIDTH);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getMapWidth  method :"+e);

		}
		return height;
	}
	
	/**
	 * Method returns zoomlevel for map. Defaults to 8;
	 * @return
	 */
	private Integer getZoomlevel() {
		
		Integer zoomLevel = 8;
		
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getZoomlevel  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_ZOOMLEVEL) != null) {
				
				zoomLevel = (Integer) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_ZOOMLEVEL);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getZoomlevel  method :"+e);

		}
		return zoomLevel;
	}
	
	/**
	 * Method returns the latitude. If not specified then returns null.
	 * @return
	 */
	private Double getLatitude() {
		
		Double latitude = null;
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getLatitude  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_LATITUDE) != null) {
				
				latitude = (Double) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_LATITUDE);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getLatitude  method :"+e);

		}
		return latitude;
	}
	
	/**
	 * Method returns the longitude. If not specified then returns null.
	 * @return
	 */
	private Double getLongitude() {
		
		Double longitude = null;
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getLongitude  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_LONGITUDE) != null) {
				
				longitude = (Double) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_LONGITUDE);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getLongitude  method :"+e);

		}
		return longitude;
	}
	
	/**
	 * Method returns the current address;
	 * @return
	 */
	private String getCurrentAddress() {
		
		String address = null;
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getCurrentAddress  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_CURRENT_ADDRESS) != null) {
				
				address = (String) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_CURRENT_ADDRESS);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getCurrentAddress  method :"+e);

		}
		return address;
	}
	
	/**
	 * Method returns the location image css;
	 * @return
	 */
	private String getLocationImageCss() {
		
		String imgCss = null;
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getLocationImageCss  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_LOCATION_IMG_CSS) != null) {
				
				imgCss = (String) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_LOCATION_IMG_CSS);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getLocationImageCss  method :"+e);

		}
		return imgCss;
	}
	
	/**
	 * Method returns the error position for the search text field ;
	 * @return
	 */
	private String getSearchFieldErrorPos() {
		
		String pos = null;
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getSearchFieldErrorPos  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_ERRPOS) != null) {
				
				pos = (String) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_ERRPOS);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getSearchFieldErrorPos  method :"+e);

		}
		return pos;
	}
	
	/**
	 * Method returns the error msg used for invalid location;
	 * @return
	 */
	private String getSearchInvalidMsg() {
		
		String pos = null;
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getSearchInvalidMsg  method ");
			if(viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_INVALID_LOCNMSG) != null) {
				
				pos = (String) viewConfiguration.getConfigurationValue(LocationSelectorFieldConstant.LOCNFD_INVALID_LOCNMSG);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getSearchInvalidMsg  method :"+e);

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
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In showMapInPopupPanel  method ");
			if(popupPanelForMap ==null){
				popupPanelForMap = new PopupPanel();
				popupPanelForMap.setAutoHideEnabled(true);
				popupPanelForMap.add(mapWidget);
				
			}
			popupPanelForMap.showRelativeTo(locationIconField);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In showMapInPopupPanel  method :"+e);

		}
		
	}
	
	/**
	 * Method creates the configuration for searchTexField used in LocationMapWidget from the configuration 
	 * which is passed to the LocationSelectorField and return.
	 * @return searchTextfield configuration.
	 */
	private Configuration getSearchFieldConf(){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getSearchFieldConf  method ");
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, getSearchFieldPrimaryCss());
			configuration.setPropertyByName(TextFieldConstant.BF_DCLS, getSearchFieldDependentCss());
			configuration.setPropertyByName(TextFieldConstant.BF_ID,SEARCH_LOCATION );
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter location");
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, getSearchFieldErrorPos());
			configuration.setPropertyByName(TextFieldConstant.BF_INVLDMSG, getSearchInvalidMsg());
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getSearchFieldConf  method :"+e);

		}
		return configuration;
	}
	
	/**
	 * Method creates the configuration for image field and return.
	 * @return popup imageField configuration.
	 */
	private Configuration getImageFieldConfiguration(){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getImageFieldConfiguration  method ");
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, getImageBlobForMapPopup());
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS,getLocationImageCss());
			configuration.setPropertyByName(ImageFieldConstant.BF_ID,SHOWMAP_IMAGEID);
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "Click here...");
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getImageFieldConfiguration  method :"+e);

		}
		return configuration;
	}
	
	/**
	 * Method creates the configuration for done button used in LocationMapWidget from the configuration 
	 * which is passed to the LocationSelectorField and return.
	 * @return doneButtonField configuration.
	 */
	private Configuration getDoneBtnConfiguration(){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getDoneBtnConfiguration  method ");
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Done");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,getDoneBtnCss());
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_TITLE, "Change location");
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, CHANGE_LOCATION);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getDoneBtnConfiguration  method :"+e);

		}
		return configuration;
		
	}
	
	private Configuration getCurrentLocationLblConf(String primaryCss){
		
		Configuration conf = new Configuration();
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In getCurrentLocationLblConf  method ");
			conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In getCurrentLocationLblConf  method :"+e);

		}
		
		return conf;
	}
	
	/********************************************************************************************/
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			logger.log(Level.INFO,"[LocationSelectorField]:: In onFieldEvent  method ");
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			
			switch (eventType) {
			case FieldEvent.CLICKED: {
				if(eventSource instanceof ImageField){
					ImageField imgField = (ImageField) eventSource;
					if(imgField.getBaseFieldId().equals(SHOWMAP_IMAGEID)){
						showMapInPopupPanel();
						break;
					}
				}
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
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LocationSelectorField]::Exception In onFieldEvent  method :"+e);

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
		
		public static final String LOCNFD_ERRPOS = "errorPosForInvalidLocation";

		public static final String LOCNFD_INVALID_LOCNMSG = "invalidMsg";
		
	}

}