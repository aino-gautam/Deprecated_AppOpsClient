package in.appops.client.common.fields.date;

import com.google.gwt.user.datepicker.client.CalendarModel;
import com.google.gwt.user.datepicker.client.CalendarView;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.user.datepicker.client.DefaultCalendarView;

public class AppopsDatePicker extends DatePicker {
	public AppopsDatePicker() {
		super(new AppopsMonthSelector(), new DefaultCalendarView(),	new CalendarModel());
		AppopsMonthSelector monthSelector = (AppopsMonthSelector) this.getMonthSelector();
		monthSelector.setPicker(this);
	}

	public void refreshComponents() {
		super.refreshAll();
	}

    public CalendarView getCalendarView() {
        return getView();
    }

}

