package in.appops.client.common.event;

import in.appops.client.common.event.handlers.AttachmentEventHandler;

import com.google.gwt.event.shared.GwtEvent;

public class AttachmentEvent extends GwtEvent<AttachmentEventHandler> {

	protected int eventType;
	protected Object eventData = null;
	
	public static final int ATTACHMENTINITIATED = 1;
	public static final int ATTACHMENTCOMPLETED = 2;
	public static final int ATTACHMENTCANCELLED = 3;
	public static Type<AttachmentEventHandler> TYPE = new Type<AttachmentEventHandler>();
	
	public AttachmentEvent(){
	}

	public AttachmentEvent(int type, Object data){
		this.eventType = type;
		this.eventData = data;
	}
	
	@Override
	protected void dispatch(AttachmentEventHandler handler) {
		handler.onAttachmentEvent(this);
	}
	
	@Override
	public Type<AttachmentEventHandler> getAssociatedType() {
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
