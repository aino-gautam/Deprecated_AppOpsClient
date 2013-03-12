package in.appops.client.common.event;

import in.appops.client.common.event.handlers.AppopsBaseEventHandler;

import com.google.gwt.event.shared.GwtEvent;

public class AppopsEvent extends GwtEvent<AppopsBaseEventHandler>{

	protected int eventType;
	protected Object eventData = null;
	protected Object source = null;
	
	public static Type<AppopsBaseEventHandler> TYPE = new Type<AppopsBaseEventHandler>();
	
	public AppopsEvent(){
		
	}
	
	public AppopsEvent(Object source, int type, Object data){
		this.source =  source;
		this.eventType = type;
		this.eventData = data;
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

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AppopsBaseEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AppopsBaseEventHandler handler) {
		
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

}
