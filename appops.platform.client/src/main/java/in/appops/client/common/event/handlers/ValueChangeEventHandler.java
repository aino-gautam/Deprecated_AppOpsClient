package in.appops.client.common.event.handlers;

import in.appops.client.common.event.ValueChangeEvent;

import com.google.gwt.event.shared.EventHandler;

public interface ValueChangeEventHandler extends EventHandler{

	public void onValueChange(ValueChangeEvent event);
}
