/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author mahesh@ensarm.com
 *
 */
public interface MessengerEventHandler extends EventHandler {
	
	public void onMessengerEvent(MessengerEvent event);

}
