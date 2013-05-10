package in.appops.client.common.util;

import com.mvp4g.client.event.EventBusWithLookup;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.GeoLocation;

/**
 * @author milind@ensarm.com
 */
public class AppEnviornment {

	public static Entity CURRENTUSER = null;
	
	public static Entity CURRENTSPACE = null;
	
	public static boolean ISUSERLOGGEDIN = false;

	public static Entity CURRENTUSERHUBENTITY = null;
	
	public static EventBusWithLookup EVENTBUS = null;
	
	private static GeoLocation CURRENT_GEOLOCATION = null;
	
	public static Entity getCurrentUser() {
		return CURRENTUSER;
	}

	public static void setCurrentUser(Entity user) {
		CURRENTUSER = user;
	}

	public static Entity getCurrentSpace() {
		return CURRENTSPACE;
	}

	public static void setCurrentSpace(Entity space) {
		CURRENTSPACE = space;
	}
	
	public static void setUserLoggedIn(boolean loggedIn) {
		ISUSERLOGGEDIN = loggedIn;
	}
	
	public static boolean getUserLoggedIn() {
		return ISUSERLOGGEDIN;
	}
	
	public static Entity getCurrentUserHubSpace() {
		return CURRENTUSERHUBENTITY;
	}

	public static void setCurrentUserHubSpace(Entity userHubEnt) {
		CURRENTUSERHUBENTITY = userHubEnt;
	}
	
	public static EventBusWithLookup getEventBus() {
		return EVENTBUS;
	}

	public static void setEventBus(EventBusWithLookup eventBus) {
		EVENTBUS = eventBus;
	}

	public static GeoLocation getCurrentGeolocation() {
		return CURRENT_GEOLOCATION;
	}

	public static void setCurrentGeolocation(GeoLocation currentGeolocation) {
		CURRENT_GEOLOCATION = currentGeolocation;
	}
}
