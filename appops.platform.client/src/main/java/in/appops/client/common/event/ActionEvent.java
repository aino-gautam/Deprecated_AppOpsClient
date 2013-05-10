package in.appops.client.common.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author nitish@ensarm.com
 */
public class ActionEvent extends GwtEvent<ActionEvent.ActionEventHandler> {
	public interface ActionEventHandler extends EventHandler {
		public void onActionEvent(ActionEvent event);
	}
	
	private int eventType;
	private Object eventData;

	public static Type<ActionEventHandler> TYPE = new Type<ActionEventHandler>();
	
	public static final int TRANSFORMWIDGET = 1;
	public static final int EXECUTEOPERATION = 2;
	public static final int BINDQUERY = 3;
	public static final int LOADENTITYHOME = 4;
	public static final int IN = 5;
	public static final int MESSAGE = 6;
	public static final int REQUESTACCESS = 7;
	
	public ActionEvent(){ }

	public ActionEvent(int eventType, Object eventData){
		this.eventType = eventType;
		this.eventData = eventData;
	}
	
	@Override
	public void dispatch(ActionEventHandler handler) {
		handler.onActionEvent(this);
	}

	@Override
	public Type<ActionEventHandler> getAssociatedType() {
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
