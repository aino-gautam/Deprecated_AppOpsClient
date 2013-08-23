package in.appops.client.common.config.field;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.event.Event;
import com.google.gwt.maps.client.event.HasMouseEvent;
import com.google.gwt.maps.client.event.MouseEventCallback;
import com.google.gwt.maps.client.geocoder.Geocoder;
import com.google.gwt.maps.client.geocoder.GeocoderCallback;
import com.google.gwt.maps.client.geocoder.GeocoderRequest;
import com.google.gwt.maps.client.geocoder.HasAddressComponent;
import com.google.gwt.maps.client.geocoder.HasGeocoder;
import com.google.gwt.maps.client.geocoder.HasGeocoderRequest;
import com.google.gwt.maps.client.geocoder.HasGeocoderResult;
import com.google.gwt.maps.client.overlay.HasMarker;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LocationMapWidget  extends Composite implements FieldEventHandler{

	private VerticalPanel mainPanel;
	private MapWidget mapWidget;	
	private HasMarker marker;
	private LatLng latLng;
	private String mapWidth;
	private String mapHeight;
	private Integer mapZoomLevel;
	private TextField searchTextField;
	private Configuration searchTextFieldConf;
	private Configuration doneBtnFieldConf;
	private String currentAddress = null;
	private Logger logger = Logger.getLogger(getClass().getName());
	private static String CHANGE_LOCATION = "changeLocation";
	private static String SEARCH_LOCATION = "searchLocation";
	
	public LocationMapWidget() {
		
	}
	
	public LocationMapWidget(LatLng latLng, Integer mapZoomLevel , String width , String height 
			,Configuration searchTextFieldConf, Configuration doneBtnFieldConf) {
		super();
		this.latLng = latLng;
		this.mapZoomLevel = mapZoomLevel;
		this.mapWidth = width;
		this.mapHeight = height;
		this.searchTextFieldConf  = searchTextFieldConf;
		this.doneBtnFieldConf = doneBtnFieldConf;
		
	}
	
	/**
	 * Method creates the map UI
	 */
	
	public void createMap(){
		
		try {
			logger.log(Level.INFO, "[LocationMapWidget] ::In createMap method ");
			mainPanel = new VerticalPanel();
			
			final MapOptions options = new MapOptions();
			options.setZoom(getMapZoomLevel());
			options.setCenter(getLatLng());
			options.setMapTypeId(new MapTypeId().getRoadmap());
			options.setDraggable(true);
			options.setNavigationControl(true);
			options.setMapTypeControl(true);
			mapWidget = new MapWidget(options);
			marker = new Marker();
   
			marker.setPosition(getLatLng());
			
			mapWidget.setSize(getMapWidth(), getMapHeight());
			
			marker.setMap(mapWidget.getMap());
			
			getAddressFromLatlang(latLng);
			
			searchTextField = new TextField();
			searchTextField.setConfiguration(getSearchTextFieldConf());
			searchTextField.configure();
			searchTextField.create();
			   
			ButtonField doneBtnField = new ButtonField();
			doneBtnField.setConfiguration(getDoneBtnFieldConf());
			doneBtnField.configure();
			doneBtnField.create();
				    	    
			mainPanel.add(searchTextField);
			mainPanel.add(mapWidget);
			mainPanel.add(doneBtnField);
			
			MouseEventCallback mapClickCallback = new MouseEventCallback() {
			      
				@Override
					public void callback(HasMouseEvent event) {
						
					 marker.setPosition(event.getLatLng());
					 mapWidget.getMap().panTo(event.getLatLng());
					 searchTextField.clearError();
					 getAddressAndSet(event.getLatLng());
					 
					}
			      };
			Event.addListener(mapWidget.getMap(), "click", mapClickCallback);
			
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
			
			initWidget(mainPanel);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LocationMapWidget] ::Exception in createMap method :"+e); 
		}
		
	}
	
	/**
	 * Method get the address from lattitude and longitude and set the current address also set it in search text field.
	 * @param latLng
	 */
	public void getAddressAndSet(HasLatLng latLng) {

		try {
			logger.log(Level.INFO, "[LocationMapWidget] ::In getAddressAndSet method ");
			HasGeocoderRequest gRequest = new GeocoderRequest();
			gRequest.setLatLng(latLng);

			HasGeocoder geocoder = new Geocoder();
			geocoder.geocode(gRequest, new GeocoderCallback() {

				@Override
				public void callback(List<HasGeocoderResult> responses,	String status) {

					String address = "";
					if (status.equals("OK")) {
						HasGeocoderResult result = responses.get(0);
						List<HasAddressComponent> addCompList = result.getAddressComponents();
						for (int i = 0; i < addCompList.size(); i++) {
							if (i == 0) {
								address = addCompList.get(i).getLongName();
							} else if (i != addCompList.size())
								address = address + " , "+ addCompList.get(i).getLongName();
							else
								address = address + " "+ addCompList.get(i).getLongName();
							
						}
						currentAddress = address;
						searchTextField.setValue(currentAddress);
											
					} else {
						setInvalidLocationError();
					}
					
				}
				
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LocationMapWidget] ::Exception in getAddressAndSet method :"+e); 
		}

	}
	
	/**
	 * Display the searched address in the map.
	 * @param address
	 */
	public void displaySearchedAddress(String address){
		try {
			logger.log(Level.INFO, "[LocationMapWidget] ::In displaySearchedAddress method ");
			final HasGeocoderRequest gRequest = new GeocoderRequest();
			gRequest.setAddress(address);
			
			final HasGeocoder geocoder = new Geocoder();
			geocoder.geocode(gRequest, new GeocoderCallback() {
			  
			  @Override
			  public void callback(List<HasGeocoderResult> responses, String status) {
			    if (status.equals("OK")) {
			      final HasGeocoderResult gResult = responses.get(0);
			      final HasLatLng gLatLng = gResult.getGeometry().getLocation();
			      latLng = (LatLng) gLatLng; 
			        getMarker().setPosition(latLng);
					getMapWidget().getMap().panTo(latLng);
					getAddressAndSet(gLatLng);
					 Timer timer = new Timer() {
							
							@Override
							public void run() {
								searchTextField.clearError();
								searchTextField.setValue(getCurrentAddress());
								
							}
						};timer.schedule(1000);
					 
					
			    } else {
			    	setInvalidLocationError();
			    }
			  }
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LocationMapWidget] ::Exception in displaySearchedAddress method :"+e); 
		}
	}
	
	/**
	 * Method gets the address from latlang also fires FieldEvent.LOCATION_RECIEVED event after location is received.
	 * @param latLng
	 */
	public void getAddressFromLatlang(LatLng latLng) {

		try {
			logger.log(Level.INFO, "[LocationMapWidget] ::In getAddressFromLatlang method ");
			HasGeocoderRequest gRequest = new GeocoderRequest();
			gRequest.setLatLng(latLng);

			HasGeocoder geocoder = new Geocoder();
			geocoder.geocode(gRequest, new GeocoderCallback() {
				@Override
				public void callback(List<HasGeocoderResult> responses,String status) {
					String address = "";
					if (status.equals("OK")) {
						HasGeocoderResult result = responses.get(0);
						List<HasAddressComponent> addCompList = result.getAddressComponents();
						for (int i = 0; i < addCompList.size(); i++) {
							if (i == 0) {
								address = addCompList.get(i).getLongName();
							} else if (i != addCompList.size())
								address = address + " , "+ addCompList.get(i).getLongName();
							else
								address = address + " "+ addCompList.get(i).getLongName();
						}
						currentAddress = address;
						searchTextField.setValue(currentAddress);
						
						FieldEvent fieldEvent = new FieldEvent();
						fieldEvent.setEventType(FieldEvent.LOCATION_RECIEVED);
						fieldEvent.setEventData(address);	
						AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, this);
						
					} else {
						setInvalidLocationError();
					}
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LocationMapWidget] ::Exception in getAddressFromLatlang method :"+e); 
		}

	}
	
	/**
	 * Method set the error message when user enters invalid location in seach text field.
	 */
	private void setInvalidLocationError(){
		try {
			logger.log(Level.INFO, "[LocationMapWidget] ::In setInvalidLocationError method ");
			ArrayList<String> errors = new ArrayList<String>();
			errors.add(searchTextField.getInvalidMsg());
			searchTextField.markInvalid(errors);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LocationMapWidget] ::Exception in setInvalidLocationError method :"+e); 
		}
	}

	public MapWidget getMapWidget() {
		return mapWidget;
	}

	public void setMapWidget(MapWidget mapWidget) {
		this.mapWidget = mapWidget;
	}

	public HasMarker getMarker() {
		return marker;
	}

	public void setMarker(HasMarker marker) {
		this.marker = marker;
	}

	public String getMapWidth() {
		return mapWidth;
	}
    /**
     * Set Width like "10px" or "100%" etc
     * @param mapWidth
     */
	public void setMapWidth(String mapWidth) {
		this.mapWidth = mapWidth;
	}

	public String getMapHeight() {
		return mapHeight;
	}

	/**
	 * Set Height like "10px" or "100%"
	 * @param mapHeight
	 */
	public void setMapHeight(String mapHeight) {
		this.mapHeight = mapHeight;
	}

	public LatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

	public Integer getMapZoomLevel() {
		return mapZoomLevel;
	}

	public void setMapZoomLevel(Integer mapZoomLevel) {
		this.mapZoomLevel = mapZoomLevel;
	}
	 
	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			logger.log(Level.INFO, "[LocationMapWidget] ::In onFieldEvent method ");
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			
			switch (eventType) {
			case FieldEvent.ENTERED_HIT: {
				if(eventSource instanceof TextField){
					TextField textField = (TextField) eventSource;
					if(textField.getBaseFieldId().equals(SEARCH_LOCATION)){
						displaySearchedAddress(searchTextField.getValue().toString());
						break;
					}
				}
			}case FieldEvent.CLICKED:{
				if(eventSource instanceof ButtonField){
					ButtonField btn = (ButtonField) eventSource;
					if(btn.getBaseFieldId().equals(CHANGE_LOCATION)){
						FieldEvent changeLocationEvent = new FieldEvent();
						changeLocationEvent.setEventType(FieldEvent.CHANGE_LOCATION);
						changeLocationEvent.setEventData(getCurrentAddress());
						AppUtils.EVENT_BUS.fireEvent(changeLocationEvent);
						break;
					}
					
				}
				
			}
			default:
				break;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LocationMapWidget] ::Exception in onFieldEvent method :"+e); 
		}
	}

	public Configuration getDoneBtnFieldConf() {
		return doneBtnFieldConf;
	}

	public void setDoneBtnFieldConf(Configuration doneBtnFieldConf) {
		this.doneBtnFieldConf = doneBtnFieldConf;
	}
	
	public Configuration getSearchTextFieldConf() {
		return searchTextFieldConf;
	}

	public void setSearchTextFieldConf(Configuration searchTextFieldConf) {
		this.searchTextFieldConf = searchTextFieldConf;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
}
