package in.appops.client.common.event.handlers;

import in.appops.client.common.event.SelectionEvent;

import com.google.gwt.event.shared.EventHandler;

public interface SelectionEventHandler extends EventHandler{

	public void onSelection(SelectionEvent event);
}
