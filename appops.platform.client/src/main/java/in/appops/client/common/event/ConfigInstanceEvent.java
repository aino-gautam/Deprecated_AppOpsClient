package in.appops.client.common.event;

import in.appops.client.common.event.handlers.ConfigInstanceEventHandler;

import com.google.gwt.event.shared.GwtEvent;

public class ConfigInstanceEvent extends GwtEvent<ConfigInstanceEventHandler> {
	protected int eventType;
	protected Object eventData = null;
	protected Object eventSource = null;
	public static Type<ConfigInstanceEventHandler> TYPE = new Type<ConfigInstanceEventHandler>();
	

	public static final int SAVEPROPVALUEADDWIDGET = 1;

	public ConfigInstanceEvent() {
		// TODO Auto-generated constructor stub
	}

	public ConfigInstanceEvent(int type, Object data ,Object eventSource) {
		this.eventType = type;
		this.eventData = data;
		this.eventSource = eventSource;
	}
	
	public ConfigInstanceEvent(int type,Object eventSource) {
		this.eventType = type;
		this.eventSource = eventSource;	
	}

	@Override
	public void dispatch(ConfigInstanceEventHandler handler) {
		handler.onConfigInstanceEvent(this);
	}

	@Override
	public Type<ConfigInstanceEventHandler> getAssociatedType() {
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
