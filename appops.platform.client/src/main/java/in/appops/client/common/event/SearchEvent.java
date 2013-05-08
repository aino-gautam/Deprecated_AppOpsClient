package in.appops.client.common.event;

import in.appops.client.common.event.handlers.SearchEventHandler;
import com.google.gwt.event.shared.GwtEvent;
/**
 * @author milind@ensarm.com
 */
public class SearchEvent extends GwtEvent<SearchEventHandler> {

	protected int eventType;
	protected Object eventData = null;
	
	public static final int SEARCHFIRED = 1;
	public static final int SEARCHCOMPLETE = 2;
	public static final int BACKTOSPACEHOME = 3;
	
	public static Type<SearchEventHandler> TYPE = new Type<SearchEventHandler>();
	
	public SearchEvent(){
	}

	public SearchEvent(int type, Object data){
		this.eventType = type;
		this.eventData = data;
	}
	
	@Override
	protected void dispatch(SearchEventHandler handler) {
		handler.onSearchEvent(this);
	}
	
	@Override
	public Type<SearchEventHandler> getAssociatedType() {
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
