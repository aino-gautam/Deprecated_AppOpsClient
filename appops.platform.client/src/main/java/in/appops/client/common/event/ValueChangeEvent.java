package in.appops.client.common.event;

import in.appops.client.common.event.handlers.ValueChangeEventHandler;

import com.google.gwt.event.shared.GwtEvent;

public class ValueChangeEvent extends GwtEvent<ValueChangeEventHandler> {

	protected int eventType;
	protected Object eventData = null;
	
	public static final int VALUECHANGED = 1;
	
	public static Type<ValueChangeEventHandler> TYPE = new Type<ValueChangeEventHandler>();
	
	@Override
	public Type<ValueChangeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ValueChangeEventHandler handler) {
		handler.onValueChange(this);
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
