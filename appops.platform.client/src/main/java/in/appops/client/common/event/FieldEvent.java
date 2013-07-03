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
	public static final int WORDENTERED = 5;
	public static final int THREE_CHAR_ENTERED = 6;
	public static final int SUGGESTION_CLICKED = 7;
	public static final int EDITSUCCESS = 8;
	public static final int EVENTDATA = 9;
	public static final int REMINDERDATA = 10;
	public static final int TIMEONLY = 11;
	public static final int DATETIMEONLY = 12;
	public static final int DATEONLY = 13;
	public static final int LOCATION_RECIEVED = 14;
	public static final int CHECKBOX_SELECT = 15;
	public static final int CHECKBOX_DESELECT = 16;
	public static final int VALUECHANGED = 17;
	public static final int SUGGESTION_SELECTED = 18;
	public static final int SPN_SPINUP = 19;
	public static final int SPN_SPINDOWN = 20;
	public static final int SHOW_MAP_IN_POPUP = 21;
	public static final int LOCATION_CHANGED = 22;
	public static final int CHANGE_LOCATION = 23;

	
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
