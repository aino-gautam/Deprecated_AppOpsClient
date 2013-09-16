package in.appops.client.common.event.handlers;

import in.appops.client.common.event.ConfigInstanceEvent;

import com.google.gwt.event.shared.EventHandler;

public interface ConfigInstanceEventHandler extends EventHandler {
	
	public void onConfigInstanceEvent(ConfigInstanceEvent event);

}
