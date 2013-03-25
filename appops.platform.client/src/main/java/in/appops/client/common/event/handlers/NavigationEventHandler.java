package in.appops.client.common.event.handlers;

import in.appops.client.common.event.NavigationEvent;

import com.google.gwt.event.shared.EventHandler;

public interface NavigationEventHandler extends EventHandler {
	
	public void onNavigation(NavigationEvent event);

}
