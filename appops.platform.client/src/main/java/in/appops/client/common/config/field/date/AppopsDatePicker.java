package in.appops.client.common.config.field.date;

import com.google.gwt.user.datepicker.client.CalendarModel;
import com.google.gwt.user.datepicker.client.CalendarView;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.user.datepicker.client.DefaultCalendarView;

/**
 * Old Raw Code for test. Code to be committed.
 * @author nitish@ensatm.com 
 *
 */
public class AppopsDatePicker extends DatePicker {
	public AppopsDatePicker() {
		super(new AppopsMonthSelector(), new DefaultCalendarView(),	new CalendarModel());
		AppopsMonthSelector monthSelector = (AppopsMonthSelector) this.getMonthSelector();
		monthSelector.setPicker(this);
		monthSelector.setModel(this.getModel());
	}

	public void refreshComponents() {
		super.refreshAll();
	}

    public CalendarView getCalendarView() {
        return getView();
    }

}

