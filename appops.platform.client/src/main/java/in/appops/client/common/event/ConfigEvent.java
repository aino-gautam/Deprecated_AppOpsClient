package in.appops.client.common.event;

import in.appops.client.common.event.handlers.ConfigEventHandler;

import com.google.gwt.event.shared.GwtEvent;

public class ConfigEvent extends GwtEvent<ConfigEventHandler> {
	
	protected int eventType;
	protected Object eventData = null;
	protected Object eventSource = null;
	public static Type<ConfigEventHandler> TYPE = new Type<ConfigEventHandler>();
	
	public static final int ADDCOMPONENTTOLIST = 1;
	public static final int COMPONENTSELECTED = 2;
	public static final int PROPERTYSELECTED = 3;
	
	public ConfigEvent(int type, Object data ,Object eventSource) {
		this.eventType = type;
		this.eventData = data;
		this.eventSource = eventSource;
	}
	
	@Override
	public void dispatch(ConfigEventHandler handler) {
		handler.onConfigEvent(this);
	}

	@Override
	public Type<ConfigEventHandler> getAssociatedType() {
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