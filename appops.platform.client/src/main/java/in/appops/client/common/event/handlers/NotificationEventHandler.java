package in.appops.client.common.event.handlers;

import in.appops.client.common.event.NotificationEvent;

import com.google.gwt.event.shared.EventHandler;

public interface NotificationEventHandler extends EventHandler {

	public void onNotificationEvent(NotificationEvent event);
}
