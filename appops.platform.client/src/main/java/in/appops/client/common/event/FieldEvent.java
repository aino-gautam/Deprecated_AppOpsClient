package in.appops.client.common.event;

import in.appops.client.common.event.handlers.AppopsBaseEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;

import com.google.gwt.event.shared.GwtEvent.Type;

public class FieldEvent extends AppopsEvent{

	public static final int EDITINITIATED = 1;
	public static final int EDITINPROGRESS = 2;
	public static final int EDITCOMPLETED = 3;
	
	public static Type<FieldEventHandler> TYPE = new Type<FieldEventHandler>();
	
	public FieldEvent(Object source, int type, Object data) {
		super(source, type, data);
	}

	public FieldEvent(){
		
	}
	
	@Override
	public void dispatch(AppopsBaseEventHandler handler) {
		((FieldEventHandler)handler).onFieldEvent(this);
		//handler.onEvent(this);
	}
	
	/*@Override
	public com.google.gwt.event.shared.GwtEvent.Type<FieldEventHandler> getAssociatedType() {
		return TYPE;
	}*/

}
