package in.appops.client.common.event;

import in.appops.client.common.event.handlers.SelectionEventHandler;

import com.google.gwt.event.shared.GwtEvent;

public class SelectionEvent extends GwtEvent<SelectionEventHandler> {

	protected int eventType;
	protected Object eventData = null;
	
	public static final int SELECTED = 1;
	public static final int DESELECTED = 2;
	public static final int DATARECEIVED = 3;
	
	public static Type<SelectionEventHandler> TYPE = new Type<SelectionEventHandler>();
	
	@Override
	public Type<SelectionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SelectionEventHandler handler) {
		handler.onSelection(this);
	}
	
	public int getEventType() {
		return eventType;
	}
	
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	
	public Object getEventData() {
		return eventData;
	}
	
	public void setEventData(Object eventData) {
		this.eventData = eventData;
	}
}
