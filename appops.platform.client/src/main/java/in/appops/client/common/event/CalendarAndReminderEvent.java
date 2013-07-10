package in.appops.client.common.event;

import in.appops.client.common.event.handlers.CalendarAndReminderEventHandler;

import com.google.gwt.event.shared.GwtEvent;

public class CalendarAndReminderEvent extends GwtEvent<CalendarAndReminderEventHandler>{

	protected int eventType;
	protected Object eventData = null;
	
	public static final int COMPLETION = 1;
	
		
	public static Type<CalendarAndReminderEventHandler> TYPE = new Type<CalendarAndReminderEventHandler>();
	
	@Override
	public Type<CalendarAndReminderEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CalendarAndReminderEventHandler handler) {
		handler.onCalendarEvent(this);
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
