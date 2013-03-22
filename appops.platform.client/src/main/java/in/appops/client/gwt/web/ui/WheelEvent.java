/**
 * 
 */
package in.appops.client.gwt.web.ui;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Debasish Padhy Created it on 14-Mar-2013
 *
 */
public class WheelEvent extends GwtEvent<WheelEventHandler> {

	public static Type<WheelEventHandler> TYPE = new Type<WheelEventHandler>();
	protected int eventType;
	protected Object eventData = null;
	

	@Override
	protected void dispatch(WheelEventHandler wheelEventHandler) {
		wheelEventHandler.onWheelEvent(this);
		
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<WheelEventHandler> getAssociatedType() {
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
