package in.appops.client.common.config.field.date;

import in.appops.client.common.config.field.spinner.ListModel;
import in.appops.client.common.config.field.spinner.NumericModel;
import in.appops.client.common.config.field.spinner.SpinnerField;
import in.appops.client.common.config.field.spinner.SpinnerField.SpinnerFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.datepicker.client.CalendarModel;
import com.google.gwt.user.datepicker.client.MonthSelector;

/**
 * Old Raw Code for test. Code to be committed.
 * @author nitish@ensatm.com 
 *
 */
public class AppopsMonthSelector extends MonthSelector implements FieldEventHandler{


	private Grid grid;

	private CalendarModel model;
	private AppopsDatePicker picker;

	private SpinnerField monthSpinner;
	private ArrayList<String> monthAbbrList;
	
	private SpinnerField yearSpinner;
	private Date maxDate;
	private Date minDate;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public void setModel(CalendarModel model) {
		this.model = model;
	}

	public void setPicker(AppopsDatePicker picker) {
		this.picker = picker;
	}

	@Override
	protected void refresh() {
		try {
			logger.log(Level.INFO,"[AppopsMonthSelector]:: In refresh  method ");
			String formattedMonth =  DateTimeFormat.getFormat(PredefinedFormat.MONTH_ABBR).format(getModel().getCurrentMonth());
			ListModel listModel = (ListModel)monthSpinner.getModel();
			listModel.setCurrentIndex(listModel.getValueList().indexOf(formattedMonth));
			monthSpinner.setSpinnerValue();
			
			String formattedYear =  DateTimeFormat.getFormat(PredefinedFormat.YEAR).format(getModel().getCurrentMonth());
			NumericModel numericModel = (NumericModel)yearSpinner.getModel();
			numericModel.setValue(numericModel.parseValue(formattedYear));
			yearSpinner.setSpinnerValue();
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[AppopsMonthSelector]::Exception In refresh  method :"+e);
		}
	}

	@Override
	protected void setup() {

		try {
			logger.log(Level.INFO,"[AppopsMonthSelector]:: In setup  method ");
			monthSpinner = new SpinnerField();
			monthAbbrList = new ArrayList<String>();

			DateTimeFormat dtf = DateTimeFormat.getFormat(PredefinedFormat.MONTH_ABBR);
			for (int i = 1; i <= 12; i++) {
				String mon = i + "";
				if (i <= 9) {
					mon = "0" + i;
				}
				Date startDt = DateTimeFormat.getFormat("dd/M/yyyy").parse("01/" + mon + "/2000");
				String monthName = dtf.format(startDt);
				monthAbbrList.add(monthName);
			}

			Configuration configuration = new Configuration();
			configuration.setPropertyByName(SpinnerFieldConstant.SP_VALUELIST, monthAbbrList);
			configuration.setPropertyByName(SpinnerFieldConstant.SP_VALUEIDX, 0);
			configuration.setPropertyByName(SpinnerFieldConstant.SP_CIRCULAR, true);
			configuration.setPropertyByName(SpinnerFieldConstant.SP_TYPE, SpinnerFieldConstant.SP_TYPELIST);
			configuration.setPropertyByName(SpinnerFieldConstant.BF_PCLS, "appops-DpMonYrSpin");
			configuration.setPropertyByName(SpinnerFieldConstant.BF_ERRPOS, SpinnerFieldConstant.BF_ERRINLINE);

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
			confNs.setPropertyByName(SpinnerFieldConstant.SP_TYPE, SpinnerFieldConstant.SP_TYPENUMERIC);
			confNs.setPropertyByName(SpinnerFieldConstant.BF_ERRPOS, SpinnerFieldConstant.BF_ERRINLINE);
			confNs.setPropertyByName(SpinnerFieldConstant.BF_ERRPOS, SpinnerFieldConstant.BF_ERRINLINE);
			
			/*DateTimeFormat yrFrmt = DateTimeFormat.getFormat(PredefinedFormat.YEAR);
			String maxYearStr = yrFrmt.format(maxDate);
			String minYearStr = yrFrmt.format(minDate);

			Float maxYear = Float.parseFloat(maxYearStr);
			Float minYear = Float.parseFloat(minYearStr);
			
			confNs.setPropertyByName(SpinnerFieldConstant.SP_MAXVAL, maxYear);
			confNs.setPropertyByName(SpinnerFieldConstant.SP_MINVAL, minYear);*/
			
			yearSpinner.setConfiguration(confNs);
			yearSpinner.configure();
			yearSpinner.create();

			AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, monthSpinner, this);
			AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, yearSpinner, this);

			// Set up grid.
			grid = new Grid(1, 2);
			grid.setWidget(0, 0, monthSpinner);
			grid.setWidget(0, 1, yearSpinner);

			initWidget(grid);
		} catch (IllegalArgumentException e) {
			logger.log(Level.SEVERE,"[AppopsMonthSelector]::Exception In setup  method :"+e);
		}
	}

	public void addMonths(int numMonths) {
		try {
			logger.log(Level.INFO,"[AppopsMonthSelector]:: In addMonths  method ");
			model.shiftCurrentMonth(numMonths);
			picker.refreshComponents();
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[AppopsMonthSelector]::Exception In addMonths  method :"+e);
		}
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			logger.log(Level.INFO,"[AppopsMonthSelector]:: In onFieldEvent  method ");
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
			} else if(event.getEventType() == FieldEvent.VALUECHANGED) {
				if(event.getSource().equals(yearSpinner)) {
					BigDecimal bd = new BigDecimal((Float)event.getEventData());
				    bd.stripTrailingZeros();
				    BigDecimal rounded = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
				    Integer year = Integer.parseInt(rounded.toString());
					
					Date current = getModel().getCurrentMonth();
					current.setYear(year - 1900);
					getModel().setCurrentMonth(current);
					picker.refreshComponents();
				}
			}
		} catch (NumberFormatException e) {
			logger.log(Level.INFO,"[AppopsMonthSelector]::Exception In onFieldEvent  method :"+e);
		}
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;		
	}
}