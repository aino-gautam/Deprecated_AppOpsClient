package in.appops.client.common.fields;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;

public class DateSelectorField extends Composite{

	private HorizontalPanel basePanel;
	private ListBox dayListBox;
	private ListBox monthListBox;
	private ListBox yearListBox;
	
	public DateSelectorField() {
		initialize();
		createUI();
		initWidget(basePanel);
	}

	private void initialize() {
		basePanel = new HorizontalPanel();
	}
	
	private void createUI() {
		dayListBox = createDayListBox();
		monthListBox = createMonthListBox();
		yearListBox = createYearListBox();
		
		basePanel.add(dayListBox);
		dayListBox.setVisible(false);
		basePanel.add(monthListBox);
		basePanel.add(yearListBox);
		basePanel.setSpacing(5);
	}
	
	private ListBox createYearListBox() {
		ListBox yearListBox = new ListBox();
		Date currentDate = new Date();
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy");
		String currYear = dateFormat.format(currentDate);
		yearListBox.addItem(currYear);
		
		for(int i = 1; i <= 15; i++) {
			int year = Integer.valueOf(currYear);
			year = year + i;
			yearListBox.addItem(String.valueOf(year));
		}
		return yearListBox;
	}

	private ListBox createMonthListBox() {
		ListBox monthListBox = new ListBox();
		monthListBox.addItem("January");
		monthListBox.addItem("February");
		monthListBox.addItem("March");
		monthListBox.addItem("April");
		monthListBox.addItem("May");
		monthListBox.addItem("June");
		monthListBox.addItem("July");
		monthListBox.addItem("August");
		monthListBox.addItem("September");
		monthListBox.addItem("October");
		monthListBox.addItem("November");
		monthListBox.addItem("December");
		return monthListBox;
	}

	private ListBox createDayListBox() {
		ListBox dayListBox = new ListBox();
		return dayListBox;
	}

	public void isDayEnabled(boolean isVisible) {
		dayListBox.setVisible(isVisible);
	}
	
	public void isMonthEnabled(boolean isVisible) {
		monthListBox.setVisible(isVisible);
	}

	public void isYearEnabled(boolean isVisible) {
		yearListBox.setVisible(isVisible);
	}
}
