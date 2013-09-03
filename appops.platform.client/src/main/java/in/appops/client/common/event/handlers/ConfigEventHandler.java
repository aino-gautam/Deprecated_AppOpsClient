package in.appops.client.common.event.handlers;

import in.appops.client.common.event.ConfigEvent;

import com.google.gwt.event.shared.EventHandler;

public interface ConfigEventHandler extends EventHandler {
	
	public void onConfigEvent(ConfigEvent event);

}

