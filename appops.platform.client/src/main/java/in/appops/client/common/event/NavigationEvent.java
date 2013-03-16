package in.appops.client.common.event;

import in.appops.client.common.event.handlers.NavigationEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class NavigationEvent extends GwtEvent<NavigationEventHandler> {

	protected int eventType;
	protected Object eventData = null;
	
	public static final int GONEXT = 1;
	public static final int GOPREVIOUS = 2;

	
	public static Type<NavigationEventHandler> TYPE = new Type<NavigationEventHandler>();
	
	
	public NavigationEvent(){
		
	}

	public NavigationEvent(int type, Object data){
		this.eventType = type;
		this.eventData = data;
	}
	
	
	@Override
	public void dispatch(NavigationEventHandler handler) {
		handler.onNavigation(this);
	}

	@Override
	public Type<NavigationEventHandler> getAssociatedType() {
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
 
