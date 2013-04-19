package in.appops.client.common.event.handlers;

import com.google.gwt.event.shared.EventHandler;
import in.appops.client.common.event.SearchEvent;
/**
 * 
 * @author milind@ensarm.com
 *
 */
public interface SearchEventHandler extends EventHandler {
	
	public void onSearchEvent(SearchEvent event);

}
