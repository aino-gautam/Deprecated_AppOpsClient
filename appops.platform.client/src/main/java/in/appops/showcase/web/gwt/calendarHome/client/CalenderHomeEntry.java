package in.appops.showcase.web.gwt.calendarHome.client;

import in.appops.client.common.components.CreateCalendarEntryScreen;
import in.appops.client.common.snippet.CalendarServiceHomeSnippet;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class CalenderHomeEntry implements EntryPoint{

	@Override
	public void onModuleLoad() {
		CalendarServiceHomeSnippet calendarServiceHomeSnippet = new CalendarServiceHomeSnippet();
		calendarServiceHomeSnippet.setConfiguration(getConfiguration());
		calendarServiceHomeSnippet.initialize();
		//calendarServiceHomeSnippet.createUi();
		RootPanel.get().add(calendarServiceHomeSnippet);
		RootPanel.get().setWidgetPosition(calendarServiceHomeSnippet,300,50);
	}

	private Configuration getConfiguration() {
		Configuration configuration = new Configuration();
		//configuration.setPropertyByName(CreateCalendarEntryScreen.REMINDER_MODE, CreateCalendarEntryScreen.REMINDER_NEW);
		configuration.setPropertyByName(CreateCalendarEntryScreen.SCREEN_TYPE, CreateCalendarEntryScreen.CREATE_EVENT);
		return configuration;
	}

}
