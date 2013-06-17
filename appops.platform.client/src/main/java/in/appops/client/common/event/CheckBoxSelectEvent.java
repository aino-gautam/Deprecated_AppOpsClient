package in.appops.client.common.event;

import in.appops.client.common.event.handlers.CheckBoxSelectEventHandler;

import com.google.gwt.event.shared.GwtEvent;

public class CheckBoxSelectEvent extends GwtEvent<CheckBoxSelectEventHandler> {

	protected int eventType;
	protected Object eventData = null;
	
	public static final int SELECTED = 1;
	public static final int DESELECTED = 2;
	
	public static Type<CheckBoxSelectEventHandler> TYPE = new Type<CheckBoxSelectEventHandler>();
	
	@Override
	public Type<CheckBoxSelectEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CheckBoxSelectEventHandler handler) {
		handler.onSelect(this);
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
