package in.appops.client.common.event.handlers;

import com.google.gwt.event.shared.EventHandler;

import in.appops.client.common.event.FieldEvent;

public interface FieldEventHandler extends EventHandler {
	
	public void onFieldEvent(FieldEvent event);

}
