package in.appops.client.common.event.handlers;

import in.appops.client.common.event.CalendarAndReminderEvent;

import com.google.gwt.event.shared.EventHandler;

public interface CalendarAndReminderEventHandler extends EventHandler{

	public void onCalendarEvent(CalendarAndReminderEvent event);
}
