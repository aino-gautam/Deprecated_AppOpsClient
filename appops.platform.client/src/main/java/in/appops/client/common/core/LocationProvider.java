package in.appops.client.common.core;

import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.GeoLocation;

import com.google.code.gwt.geolocation.client.Coordinates;
import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionOptions;

public class LocationProvider {
	
	public static final String WEB = "Web";
	public static final String TOUCH = "Touch";
	private static LocationProvider locationProvider;
	private Coordinates coords1 ;
	
	private LocationProvider(){
		
	}
	
	public static LocationProvider getInstance(){
		if(locationProvider == null)
			locationProvider = new LocationProvider();
			
		return locationProvider;
	}

	public void getLocation(String locationType, EntityReceiver entityReceiver){
		if(locationType == LocationProvider.WEB){
			getWebLocation(entityReceiver);
		}else if(locationType == LocationProvider.TOUCH){
			getTouchLocation(entityReceiver);
		}
	}
	
	private void getWebLocation(final EntityReceiver entityReceiver){
			PositionOptions options = PositionOptions.create();
			options.setEnableHighAccuracy(true);
			options.setMaximumAge(10000);
			Geolocation.getGeolocation().watchPosition(new PositionCallback() {
				
				public void onSuccess(Position position) {
					
					Coordinates coords = position.getCoords();
					coords1 = coords;
					
					Entity entity = new Entity();
					
					GeoLocation geoLocation = new GeoLocation();
					geoLocation.setName("latitude");
					geoLocation.setLatitude(coords1.getLatitude());
					
					
					geoLocation.setName("longitude");
					geoLocation.setLongitude(coords1.getLongitude());
					
					
					entity.setPropertyByName("geolocation",geoLocation);
					AppEnviornment.setCurrentGeolocation(geoLocation);
					
					entityReceiver.onEntityReceived(entity);
									
				}
				
				@Override
				public void onFailure(com.google.code.gwt.geolocation.client.PositionError error) {
					System.out.println(" "+error.getMessage());
				}
			}, options);
	}
	
	private void getTouchLocation(EntityReceiver entityReceiver){
		//TODO make a call to fetch geo location and then call the onEntityReceived method on the entityReceiver
	}
}
