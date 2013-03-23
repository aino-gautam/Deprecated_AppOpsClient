package in.appops.client.common.event;

import in.appops.client.common.event.handlers.FieldEventHandler;

import com.google.gwt.event.shared.GwtEvent;

public class FieldEvent extends GwtEvent<FieldEventHandler> {

	protected int eventType;
	protected Object eventData = null;
	
	public static final int EDITINITIATED = 1;
	public static final int EDITINPROGRESS = 2;
	public static final int EDITCOMPLETED = 3;
	public static final int EDITFAIL = 4;
	
	public static Type<FieldEventHandler> TYPE = new Type<FieldEventHandler>();
	
	
	public FieldEvent(){
		
	}

	public FieldEvent(int type, Object data){
		this.eventType = type;
		this.eventData = data;
	}
	
	
	@Override
	public void dispatch(FieldEventHandler handler) {
		handler.onFieldEvent(this);
	}

	@Override
	public Type<FieldEventHandler> getAssociatedType() {
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

}
