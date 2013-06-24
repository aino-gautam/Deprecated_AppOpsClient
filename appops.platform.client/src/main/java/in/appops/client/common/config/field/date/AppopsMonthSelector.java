package in.appops.client.common.config.field.date;

import in.appops.client.common.config.field.spinner.ListModel;
import in.appops.client.common.config.field.spinner.NumericModel;
import in.appops.client.common.config.field.spinner.SpinnerField;
import in.appops.client.common.config.field.spinner.SpinnerField.SpinnerFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.datepicker.client.CalendarModel;
import com.google.gwt.user.datepicker.client.MonthSelector;

/**
 * Old Raw Code for test. Code to be committed.
 * @author nitish@ensatm.com 
 *
 */
public class AppopsMonthSelector extends MonthSelector implements FieldEventHandler{

	private static String BASE_NAME = "datePicker";
	  private PushButton backwards; 
	  private PushButton forwards; 
	  private PushButton backwardsYear; 
	  private PushButton forwardsYear; 
	private Grid grid;

	private CalendarModel model;
	private AppopsDatePicker picker;

	private SpinnerField monthSpinner;
	private ArrayList<String> monthAbbrList;
	
	private SpinnerField yearSpinner;

	public void setModel(CalendarModel model) {
		this.model = model;
	}

	public void setPicker(AppopsDatePicker picker) {
		this.picker = picker;
	}

	@Override
	protected void refresh() {
		String formattedMonth =  DateTimeFormat.getFormat(PredefinedFormat.MONTH_ABBR).format(getModel().getCurrentMonth());
		ListModel listModel = (ListModel)monthSpinner.getModel();
		listModel.setCurrentIndex(listModel.getValueList().indexOf(formattedMonth));
		monthSpinner.setSpinnerValue();
		
		String formattedYear =  DateTimeFormat.getFormat(PredefinedFormat.YEAR).format(getModel().getCurrentMonth());
		NumericModel numericModel = (NumericModel)yearSpinner.getModel();
		numericModel.setValue(numericModel.parseValue(formattedYear));
		yearSpinner.setSpinnerValue();
	}

	@Override
	protected void setup() {
		
	   monthSpinner = new SpinnerField();
	   monthAbbrList = new ArrayList<String>();
	   
	   DateTimeFormat dtf = DateTimeFormat.getFormat(PredefinedFormat.MONTH_ABBR);
	   for (int i = 1; i <= 12; i++) {
		   String mon = i + "";
		   if(i <= 9) {
			   mon = "0" + i;
		   }
		   Date startDt = DateTimeFormat.getFormat("dd/M/yyyy").parse("01/"+ mon +"/2000");
		   String monthName = dtf.format(startDt);        
		   monthAbbrList.add(monthName);
	   }

	   Configuration configuration = new Configuration();
       configuration.setPropertyByName(SpinnerFieldConstant.SP_VALUELIST, monthAbbrList);
	   configuration.setPropertyByName(SpinnerFieldConstant.SP_VALUEIDX, 0);
	   configuration.setPropertyByName(SpinnerFieldConstant.SP_CIRCULAR, true);
	   configuration.setPropertyByName(SpinnerFieldConstant.SP_TYPE, SpinnerFieldConstant.SP_TYPELIST);
	   configuration.setPropertyByName(SpinnerFieldConstant.BF_PCLS, "appops-DpMonYrSpin");
	   
		monthSpinner.setConfiguration(configuration);
		monthSpinner.configure();
		monthSpinner.create();
		
		yearSpinner = new SpinnerField();
		
		 Configuration confNs = new Configuration();
		 confNs.setPropertyByName(SpinnerFieldConstant.SP_STEP, 1);
		 confNs.setPropertyByName(SpinnerFieldConstant.SP_MAXVAL, 2099L);
		 confNs.setPropertyByName(SpinnerFieldConstant.SP_MINVAL, 1900L);
		 confNs.setPropertyByName(SpinnerFieldConstant.SP_CIRCULAR, true);
		 confNs.setPropertyByName(SpinnerFieldConstant.BF_PCLS, "appops-DpMonYrSpin");
		 configuration.setPropertyByName(SpinnerFieldConstant.SP_TYPE, SpinnerFieldConstant.SP_TYPENUMERIC);
			
			yearSpinner.setConfiguration(confNs);
			yearSpinner.configure();
			yearSpinner.create();
		
		AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, monthSpinner, this);
		AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, yearSpinner, this);
		
		backwards = new PushButton();
		backwards.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addMonths(-1);
			}
		});

		backwards.getUpFace().setHTML("&lsaquo;");
		backwards.setStyleName(BASE_NAME + "PreviousButton");

		forwards = new PushButton();
		forwards.getUpFace().setHTML("&rsaquo;");
		forwards.setStyleName(BASE_NAME + "NextButton");
		forwards.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addMonths(+1);
			}
		});

		// Set up backwards year
		backwardsYear = new PushButton();
		backwardsYear.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addMonths(-12);
			}
		});

		backwardsYear.getUpFace().setHTML("&laquo;");
		backwardsYear.setStyleName(BASE_NAME + "PreviousButton");

		forwardsYear = new PushButton();
		forwardsYear.getUpFace().setHTML("&raquo;");
		forwardsYear.setStyleName(BASE_NAME + "NextButton");
		forwardsYear.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addMonths(+12);
			}
		});

		// Set up grid.
		grid = new Grid(1, 2);
		grid.setWidget(0, 0 , monthSpinner);
		grid.setWidget(0, 1 , yearSpinner );
		/*grid.setWidget(0, previousMonthColumn, backwards);
		grid.setWidget(0, nextMonthColumn, forwards);
		grid.setWidget(0, nextYearColumn, forwardsYear);*/

		CellFormatter formatter = grid.getCellFormatter();
		/*formatter.setStyleName(0, monthColumn, BASE_NAME + "Month");
		formatter.setWidth(0, previousYearColumn, "1");
		formatter.setWidth(0, previousMonthColumn, "1");
		formatter.setWidth(0, monthColumn, "100%");
		formatter.setWidth(0, nextMonthColumn, "1");
		formatter.setWidth(0, nextYearColumn, "1");
		grid.setStyleName(BASE_NAME + "MonthSelector");*/
		initWidget(grid);
	}

	public void addMonths(int numMonths) {
		model.shiftCurrentMonth(numMonths);
		picker.refreshComponents();
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		if(event.getEventType() == FieldEvent.SPN_SPINUP) {
			if(event.getSource().equals(monthSpinner)) {
				addMonths(1);
			} else if(event.getSource().equals(yearSpinner)) {
				addMonths(12);
			}
		} else if(event.getEventType() == FieldEvent.SPN_SPINDOWN) {
			if(event.getSource().equals(monthSpinner)) {
				addMonths(-1);
			} else if(event.getSource().equals(yearSpinner)) {
				addMonths(-12);
			}
		}
	}
}