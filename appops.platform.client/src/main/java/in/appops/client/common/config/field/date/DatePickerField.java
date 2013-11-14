package in.appops.client.common.config.field.date;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.fields.DateTimeField;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ShowRangeEvent;
import com.google.gwt.event.logical.shared.ShowRangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.datepicker.client.CalendarUtil;

/**
 * Old Raw Code for test. Code to be committed.
 * @author nitish@ensatm.com 
 *
 */
public class DatePickerField  extends BaseField implements ClickHandler {
	
	public interface DatePickerConstant extends BaseFieldConstant {
		
		/** Style class primary for spinner text box  **/ 
		public static final String DP_BXPCLS = "dateBoxPrimaryCss";
		
		/** Style class dependent for spinner text box. **/
		public static final String DP_BXDCLS = "dateBoxDependentCss";
		
		public static final String DP_MAXDATE = "maxDate";
		
		/** The minimum allowed value. Will be used by the field's validation logic **/
		public static final String DP_MINDATE = "minDate";
		
		public static final String DP_FORMAT = "format";
		
		public static final String DP_ALTFORMAT= "altformat";
		
		public static final String DP_ALLOWBLNK = "allowBlank";

		public static final String DP_ERRMSGBLNK = "blnktxt";
		
		public static final String DP_ERRMSGMIN = "minText";
		
		public static final String DP_ERRMSGMAX = "maxText";

	}
	
	private HorizontalPanel dtPickFieldBase;
	private PopupPanel dtPickPopup;
	private TextBox dtPickBox;
	private ToggleButton dtPickTrigger;
	private AppopsDatePicker dtPicker;
	private Logger logger = Logger.getLogger(getClass().getName());
	public DatePickerField() {
		super();
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		dtPickFieldBase = new HorizontalPanel();
		dtPickBox=new TextBox();
		dtPickTrigger = new ToggleButton();
		dtPickPopup = new PopupPanel(true);
		dtPickPopup.setWidth("142px");
		dtPicker = new AppopsDatePicker();

	}
	
	
	@Override
	public void configure() {
		
		try {
			super.configure();
			logger.log(Level.INFO,"[DatePickerField]:: In configure  method ");
			dtPickBox.setStylePrimaryName(getDatePickBoxPrimCss());

			dtPickBox.getElement().setAttribute("placeholder", getFormat());
			
			if(getDefaultValue() == null || parseDate(getDefaultValue().toString()) == null) {
				setFieldValue("");
			} else {
				Date defaultValue = parseDate(getDefaultValue().toString());
				setFieldValue(format(defaultValue, getFormat()));
				setValue(defaultValue);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In configure  method :"+e);
		}
		
	}
	
	@Override
	public Object getValue(){
		logger.log(Level.INFO,"[DatePickerField]:: In getValue  method ");
		Date date = DateTimeFormat.getFormat(getFormat()).parse(dtPickBox.getText());
		return date;
	}
	
/*	@Override
	public String getFieldValue(){
		return dtPickBox.getText();
	}*/
	
	
	public String getFormat() {
		String format = "dd/MM/yyyy";
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In getFormat  method ");
			if(getConfigurationValue(DatePickerConstant.DP_FORMAT) != null) {
				format = getConfigurationValue(DatePickerConstant.DP_FORMAT).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In getFormat  method :"+e);
		}
		return format;
	}
	
	@Override
	public void setFieldValue(String fieldValue) {
		dtPickBox.setText(fieldValue);
	}
	
	@Override
	protected String getBaseFieldPrimCss() {
		String primaryCss = super.getBaseFieldPrimCss();
		
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In getBaseFieldPrimCss  method ");
			if(primaryCss == null) {
				return "appops-SpinnerFieldPrimary";
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In getBaseFieldPrimCss  method :"+e);
		}
		return primaryCss;
	}

	protected String getBaseFieldDependentCss() {
		String depCss = super.getBaseFieldDependentCss();
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In getBaseFieldCss  method ");
			if(depCss == null) {
				return "appops-SpinnerFieldDependent";
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In getBaseFieldCss  method :"+e);
		}	
		return depCss;
	}
	
	@Override
	public void create(){
		super.create();
        try {
        	logger.log(Level.INFO,"[DatePickerField]:: In create  method ");
			basePanel.add(dtPickFieldBase, DockPanel.CENTER);
			dtPickFieldBase.add(dtPickBox);
			
			dtPickTrigger.setStylePrimaryName("appops-dtPickTrigger");
			dtPickFieldBase.add(dtPickTrigger);
			dtPickTrigger.setDown(false);

			NodeList<com.google.gwt.dom.client.Element> nodeList = dtPickFieldBase.getElement().getElementsByTagName("td");
			Node td1Node = nodeList.getItem(0);
			Element td1Element = (Element) Element.as(td1Node);
			td1Element.setClassName("appops-dtPicker-border-box");

			Node td2Node = nodeList.getItem(1);
			Element td2Element = (Element) Element.as(td2Node);
			td2Element.setClassName("appops-dtPicker-border-box");
			
			dtPicker.setWidth("100%");
			
			dtPickPopup.setAnimationEnabled(true);
			dtPickPopup.setWidget(dtPicker);
			
			dtPickTrigger.addClickHandler(this);
			
			dtPicker.addValueChangeHandler(new ValueChangeHandler<Date>(){
				@Override
				public void onValueChange(ValueChangeEvent<Date> event) {
					Date date = (Date) event.getValue();
			        String dateString = format(date, getFormat());
			        dtPickBox.setText(dateString);
			        hidePicker();
			     }
			});
			
			dtPickPopup.addCloseHandler(new CloseHandler<PopupPanel>() {
				@Override
				public void onClose(CloseEvent<PopupPanel> event) {
					dtPickTrigger.setDown(false);
				}
			});
			
			dtPicker.addShowRangeHandler(new ShowRangeHandler<Date>() {
			     public void onShowRange(ShowRangeEvent<Date> dateShowRangeEvent) {
			         setValidDates(dateShowRangeEvent);
			     }
			});
			
			dtPickBox.addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					validate();
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In create  method :"+e);
		}
	}
	
	private Configuration getDateTimeFieldConfiguration(String modeSelection,String datetimefieldTimeonly, String modeTimeValue) {
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In getDateTimeFieldConfiguration  method ");
			configuration.setPropertyByName(DateTimeField.DATETIMEFIELD_MODE, modeSelection);
			configuration.setPropertyByName(DateTimeField.DATETIMEFIELD_TYPE, datetimefieldTimeonly);
			if(modeTimeValue!=null)
			  configuration.setPropertyByName(modeTimeValue, modeTimeValue);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In getDateTimeFieldConfiguration  method :"+e);
		}
		
		return configuration;
	}
	
	private void hidePicker() {
		dtPickPopup.hide();
	}
	
	private void setValidDates(ShowRangeEvent<Date> dateShowRangeEvent) {
        try {
        	logger.log(Level.INFO,"[DatePickerField]:: In setValidDates  method ");
			Date start = dateShowRangeEvent.getStart();
			Date end = dateShowRangeEvent.getEnd();

			Integer daysBetween = CalendarUtil.getDaysBetween(start, end);

			for (int i = 0; i < daysBetween; i++) {
			    Date date = new Date(start.getTime());
			    CalendarUtil.addDaysToDate(date, i);
			    if(date.before(getMinDate()) || date.after(getMaxDate())) {
			    	disableDate(date);
			    }
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In setValidDates  method :"+e);
		}
    }
	
    private void disableDate(Date date) {
    	logger.log(Level.INFO,"[DatePickerField]:: In disableDate  method ");
        dtPicker.getCalendarView().setEnabledOnDate(false, date);
    }


	/**
	 * Returns the primary style to be applied to the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getDatePickFieldPrimCss() {
		logger.log(Level.INFO,"[DatePickerField]:: In getDatePickFieldPrimCss  method ");
		String primaryCss = "appops-dtPickFieldPrim";
//		if(getConfigurationValue(SpinnerConfigurationConstant.SPINNER_PRIMARYCSS) != null) {
//			primaryCss = getConfigurationValue(SpinnerConfigurationConstant.SPINNER_PRIMARYCSS).toString();
//		}
		return primaryCss;
	}

	/**
	 * Returns the dependent style to be applied to the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getDatePickFieldDepCss() {
		logger.log(Level.INFO,"[DatePickerField]:: In getDatePickFieldDepCss  method ");
		String depCss = "appops-dtPickFieldDep";
//		if(getConfigurationValue(SpinnerConfigurationConstant.SPINNER_DEPENDENTCSS) != null) {
//			depCss = getConfigurationValue(SpinnerConfigurationConstant.SPINNER_DEPENDENTCSS).toString();
//		}
		return depCss;
	}

	/**
	 * Returns the primary style to be applied to the textbox of the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getDatePickBoxPrimCss() {
		logger.log(Level.INFO,"[DatePickerField]:: In getDatePickBoxPrimCss  method ");
		String primaryCss = "appops-SpinnerBoxPrimary";
//		if(getConfigurationValue(SpinnerConfigurationConstant.BOX_PRIMARYCSS) != null) {
//			primaryCss = getConfigurationValue(SpinnerConfigurationConstant.BOX_PRIMARYCSS).toString();
//		}
		return primaryCss;
	}
	
	/**
	 * Returns the dependent style to be applied to the textbox of the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getDatePickBoxDepCss() {
		logger.log(Level.INFO,"[DatePickerField]:: In getDatePickBoxDepCss  method ");
		String dependentCss = "appops-dtPickBoxDep";
//		if(getConfigurationValue(SpinnerConfigurationConstant.BOX_DEPENDENTCSS) != null) {
//			dependentCss = getConfigurationValue(SpinnerConfigurationConstant.BOX_DEPENDENTCSS).toString();
//		}
		return dependentCss;
	}
	
	@Override
	public boolean isAllowBlank() {
		boolean allowblank = false; 
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In isAllowBlank  method ");
			if(getConfigurationValue(DatePickerConstant.DP_ALLOWBLNK) != null) {
				allowblank = (Boolean)getConfigurationValue(DatePickerConstant.DP_ALLOWBLNK);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In isAllowBlank  method :"+e);
		}
		return allowblank;
	}

	@Override
	public void onClick(ClickEvent event) {
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In onClick  method ");
			if(event.getSource().equals(dtPickTrigger)) {
				if(dtPickTrigger.isDown()) {
					createPicker();
					displayPicker();
					} else if(!dtPickTrigger.isDown()) {

				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In onClick  method :"+e);
		}
		
	}
	
	private void createPicker() {
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In createPicker  method ");
			String fieldValue = dtPickBox.getText();
			Date dateVal = new Date();
			if(getErrors(fieldValue).isEmpty()) {
				dateVal = parseDate(fieldValue); 
			}
			dtPicker.setCurrentMonth(dateVal);
			dtPicker.setValue(dateVal);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In createPicker  method :"+e);
		}
	}
	
	private void displayPicker() {
		dtPickPopup.showRelativeTo(dtPickBox);
	}

	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFieldValue() {
		logger.log(Level.INFO,"[DatePickerField]:: In getFieldValue  method ");
		String fieldValue = dtPickBox.getText().trim();
		return fieldValue;
	}
	
	public Date getMinDate() {
		Date min = new Date(0);
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In getMinDate  method ");
			Date minDate = null;
			if(getConfigurationValue(DatePickerConstant.DP_MINDATE) != null) {
				String minDateStr =  getConfigurationValue(DatePickerConstant.DP_MINDATE).toString();
				minDate = parseDate(minDateStr);
				if(minDate != null) {
					min = minDate;
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In getMinDate  method :"+e);
		}
		return min;
	}

	public Date getMaxDate() {
		Date max = new Date(Long.MAX_VALUE);
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In getMaxDate  method ");
			Date maxDate = null;
			if(getConfigurationValue(DatePickerConstant.DP_MAXDATE) != null) {
				String maxDateStr =  getConfigurationValue(DatePickerConstant.DP_MAXDATE).toString();
				maxDate = parseDate(maxDateStr);
				if(maxDate !=  null) {
					max = maxDate;
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In getMaxDate  method :"+e);
		}
		return max;
	}

	private String getMinErrMsg() {
		String minMsg = "The date in the field should be equal or after " + format(getMinDate(), getFormat());
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In getMinErrMsg  method ");
			if(getConfigurationValue(DatePickerConstant.DP_ERRMSGMIN) != null) {
				minMsg = getConfigurationValue(DatePickerConstant.DP_ERRMSGMIN).toString();
			}
		} catch (Exception e) {
			
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In getMinErrMsg  method :"+e);
		}
		return minMsg;
	}
	
	private String getMaxErrMsg() {
		String maxMsg = "The date in the field should be equal or before " + format(getMaxDate(), getFormat());
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In getMaxErrMsg  method ");
			if(getConfigurationValue(DatePickerConstant.DP_ERRMSGMAX) != null) {
				maxMsg = getConfigurationValue(DatePickerConstant.DP_ERRMSGMAX).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In getMaxErrMsg  method :"+e);
		}
		return maxMsg;
	}
	
	private String getBlankErrMsg() {
		String blnkMsg = "This field is required";
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In getBlankErrMsg  method ");
			if(getConfigurationValue(DatePickerConstant.DP_ERRMSGBLNK) != null) {
				blnkMsg = getConfigurationValue(DatePickerConstant.DP_ERRMSGBLNK).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In getBlankErrMsg  method :"+e);
		}
		return blnkMsg;
	}
	
	private Date parseDate(String valueStr) {
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In parseDate  method ");
			if(valueStr == null || valueStr.trim().equals("")){
				return null;
			}
			Date date = DateTimeFormat.getFormat(getFormat()).parse(valueStr.trim()); 
			if(date != null) {
				return date;
			}
			return null;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	private String format(Date date, String format) {
		return DateTimeFormat.getFormat(format).format(date);
	}
	
	@Override
	public String getInvalidMsg() {
		String invalidMsg = getFieldValue() + " is not a valid date. It must be in the format " + getFormat();
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In getInvalidMsg  method ");
			if(getConfigurationValue(BaseFieldConstant.BF_INVLDMSG) != null) {
				invalidMsg = getConfigurationValue(BaseFieldConstant.BF_INVLDMSG).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In getInvalidMsg  method :"+e);
		}
		return invalidMsg;
	}
	
	@Override
	public ArrayList<String> getErrors(String fieldValue) {
		ArrayList<String> errors = new ArrayList<String>();
		try {
			logger.log(Level.INFO,"[DatePickerField]:: In getErrors  method ");
			if(fieldValue != null) {
				if(!isAllowBlank() && fieldValue.toString().trim().equals("")) {
					errors.add(getBlankErrMsg());
					return errors;
				}
//			if(!fieldValue.toString().matches("-?\\d+(\\.\\d+)?")) {
//				errors.add(getInvalidErrMsg());
//				return errors;
//			}
				
				Date date = parseDate(fieldValue.toString());
				if(date == null) {
					errors.add(getInvalidMsg());
					return errors;
				}

				if(date.before(getMinDate())) {
					errors.add(getMinErrMsg());
				}
				if(date.after(getMaxDate())) {
					errors.add(getMaxErrMsg());
				} 
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DatePickerField]::Exception In getErrors  method :"+e);
		}
		return errors;
	}
}