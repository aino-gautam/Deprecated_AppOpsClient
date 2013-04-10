/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author mahesh@ensarm.com
 *
 */
public class MessengerEvent  extends GwtEvent<MessengerEventHandler> {

	private int eventType;
	private Object eventData = null;
	
	public static final int CONTACTUSERFOUND = 1;
	public static final int ONUSERMSGRECEIVED = 2;
	public static final int ONSPACEMSGRECIEVED = 3;
	
	public static Type<MessengerEventHandler> TYPE = new Type<MessengerEventHandler>();
	
	public MessengerEvent(int eventType , Object eventData){
		this.eventType = eventType;
		this.eventData = eventData;
	}
	
	@Override
	public void dispatch(MessengerEventHandler handler) {
		handler.onMessengerEvent(this);
	}

	@Override
	public Type<MessengerEventHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * @return the eventType
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * @return the eventData
	 */
	public Object getEventData() {
		return eventData;
	}

}
