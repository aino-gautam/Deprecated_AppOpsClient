package in.appops.client.common.event;

import in.appops.client.common.event.handlers.NotificationEventHandler;

import com.google.gwt.event.shared.GwtEvent;

public class NotificationEvent extends GwtEvent<NotificationEventHandler> {

	protected int eventType;
	protected Object eventData = null;
	protected Object eventSource = null;
	
	public static final int YES_BUTTON_CLICKED = 1;
	public static final int NO_BUTTON_CLICKED = 2;
	public static final int OK_BUTTON_CLICKED = 3;
	
	public static Type<NotificationEventHandler> TYPE = new Type<NotificationEventHandler>();
	
	public NotificationEvent(){
		
	}

	public NotificationEvent(int type, Object data){
		this.eventType = type;
		this.eventData = data;
	}
	
	
	@Override
	public void dispatch(NotificationEventHandler handler) {
		handler.onNotificationEvent(this);
	}

	@Override
	public Type<NotificationEventHandler> getAssociatedType() {
		return TYPE;
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

	public Object getEventSource() {
		return eventSource;
	}

	public void setEventSource(Object eventSource) {
		this.eventSource = eventSource;
	}
}
