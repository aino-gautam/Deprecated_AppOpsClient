package in.appops.client.common.config.field;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;

import java.util.List;

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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;

public class MapField  extends BaseField {

	private MapWidget mapWidget;	
	private HasMarker marker;
	private String choosenAddress;
	private LatLng latLng;
	private String mapWidth;
	private String mapHeight;
	private int mapZoomParameter;
	
	public MapField(LatLng latLng) {
		this.latLng = latLng;
		
	}
	
	public void createMapUi(){
		
		final MapOptions options = new MapOptions();
	    // Zoom level. Required
	    options.setZoom(mapZoomParameter);
	    // Open a map centered on current location. 
	    options.setCenter(latLng);
	    // Map type. Required.
	    options.setMapTypeId(new MapTypeId().getRoadmap());
	    
	    // Enable maps drag feature. Disabled by default.
	    options.setDraggable(true);
	    // Enable and add default navigation control. Disabled by default.
	    options.setNavigationControl(true);
	    // Enable and add map type control. Disabled by default.
	    options.setMapTypeControl(true);
	    mapWidget = new MapWidget(options);
	    marker = new Marker();
	   
		marker.setPosition(latLng);
		
	   // mapWidget.setSize("800px", "650px");
	    mapWidget.setSize(mapWidth, mapHeight);
	    
	    marker.setMap(mapWidget.getMap());
	    
	    MouseEventCallback mapClickCallback = new MouseEventCallback() {
	          
			@Override
				public void callback(HasMouseEvent event) {
					
				 marker.setPosition(event.getLatLng());
				 mapWidget.getMap().panTo(event.getLatLng());
				
				 getAddressAndSet(event.getLatLng());
				 
				}
		      };
		Event.addListener(mapWidget.getMap(), "click", mapClickCallback);
	}
	
	public void getAddressAndSet(HasLatLng latLng) {

		HasGeocoderRequest gRequest = new GeocoderRequest();
		gRequest.setLatLng(latLng);

		HasGeocoder geocoder = new Geocoder();
		geocoder.geocode(gRequest, new GeocoderCallback() {

			@Override
			public void callback(List<HasGeocoderResult> responses,
					String status) {

				String address = "";
				if (status.equals("OK")) {
					HasGeocoderResult result = responses.get(0);
					List<HasAddressComponent> addCompList = result
							.getAddressComponents();
					for (int i = 0; i < addCompList.size(); i++) {
						if (i == 0) {
							address = addCompList.get(i).getLongName();
						} else if (i != addCompList.size())
							address = address + " , "
									+ addCompList.get(i).getLongName();
						else
							address = address + " "
									+ addCompList.get(i).getLongName();
						
					}
					
					choosenAddress = address;
					FieldEvent fieldEvent = new FieldEvent();
					fieldEvent.setEventType(FieldEvent.LOCATION_IN_MAP);
					fieldEvent.setEventData(address);	
					AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, MapField.this);
				} else {
					Window.alert("Error in Geocoding : " + status);
				}
				
			}
			
		});

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

	public String getChoosenAddress() {
		return choosenAddress;
	}

	public void setChoosenAddress(String choosenAddress) {
		this.choosenAddress = choosenAddress;
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

	public int getMapZoomParameter() {
		return mapZoomParameter;
	}

	public void setMapZoomParameter(int mapZoomParameter) {
		this.mapZoomParameter = mapZoomParameter;
	}

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		getBasePanel().add(mapWidget,DockPanel.CENTER);
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFieldValue() {
		return choosenAddress;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		choosenAddress = fieldValue;
		
	}
	public void addHandle(FieldEventHandler handler) {
		AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, this, handler);
		
	}

	@Override
	public void configure() {
		
		
	}
}
