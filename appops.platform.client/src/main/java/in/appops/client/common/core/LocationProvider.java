package in.appops.client.common.core;

import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.GeoLocation;

import com.google.code.gwt.geolocation.client.Coordinates;
import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.code.gwt.geolocation.client.PositionOptions;

public class LocationProvider {
	
	public static final String WEB = "Web";
	public static final String TOUCH = "Touch";
	private static LocationProvider locationProvider;
	private double latitude;
	private double longitude;
	
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
		try{
			PositionOptions options = PositionOptions.create();
			options.setEnableHighAccuracy(true);
			//options.setMaximumAge(900000);
			//options.setTimeout(10 * 1000 * 1000);
			Geolocation.getGeolocation().watchPosition(new PositionCallback() {
				
				public void onSuccess(Position position) {
					
					Coordinates coords = position.getCoords();
					
					double distance = calculateDistance(latitude, longitude, coords.getLatitude(), coords.getLongitude());
					
					if(distance > 20.0) {
						latitude = coords.getLatitude();
						longitude = coords.getLongitude();
						
						Entity entity = new Entity();
						
						GeoLocation geoLocation = new GeoLocation();
						geoLocation.setName("latitude");
						geoLocation.setLatitude(latitude);
						
						
						geoLocation.setName("longitude");
						geoLocation.setLongitude(longitude);
						
						
						entity.setPropertyByName("geolocation",geoLocation);
						AppEnviornment.setCurrentGeolocation(geoLocation);
						
						entityReceiver.onEntityReceived(entity);
										
					}
				}
				
				@Override
				public void onFailure(PositionError error) {
					System.out.println(" "+error.getMessage());
				}
			}, options);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getTouchLocation(EntityReceiver entityReceiver){
		//TODO make a call to fetch geo location and then call the onEntityReceived method on the entityReceiver
	}

	private double calculateDistance(double fromLat, double fromLon, double toLat, double toLon) {
	  double R = 6371;
	  double dLat = Math.toRadians(toLat - fromLat);
	  double dLon = Math.toRadians(toLon - fromLon); 
	  double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
	          Math.cos(Math.toRadians(fromLat)) * Math.cos(Math.toRadians(toLat)) * 
	          Math.sin(dLon / 2) * Math.sin(dLon / 2); 
	  double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); 
	  double d = R * c;
	  return d*1000;
	}
	
	
}
