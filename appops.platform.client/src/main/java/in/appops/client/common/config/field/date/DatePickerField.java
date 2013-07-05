package in.appops.client.common.config.field.date;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.fields.DateTimeField;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.Date;

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
		super.configure();
		dtPickBox.setStylePrimaryName(getDatePickBoxPrimCss());

		dtPickBox.getElement().setAttribute("placeholder", getFormat());
		
		if(getDefaultValue() == null || parseDate(getDefaultValue().toString()) == null) {
			setFieldValue("");
		} else {
			Date defaultValue = parseDate(getDefaultValue().toString());
			setFieldValue(format(defaultValue, getFormat()));
			setValue(defaultValue);
		}
		
	}
	
	@Override
	public Object getValue(){
		Date date = DateTimeFormat.getFormat(getFormat()).parse(dtPickBox.getText());
		return date;
	}
	
/*	@Override
	public String getFieldValue(){
		return dtPickBox.getText();
	}*/
	
	
	public String getFormat() {
		String format = "dd/MM/yyyy";
		if(getConfigurationValue(DatePickerConstant.DP_FORMAT) != null) {
			format = getConfigurationValue(DatePickerConstant.DP_FORMAT).toString();
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
		
		if(primaryCss == null) {
			return "appops-SpinnerFieldPrimary";
		}
		return primaryCss;
	}

	protected String getBaseFieldCss() {
		String depCss = super.getBaseFieldCss();
		if(depCss == null) {
			return "appops-SpinnerFieldDependent";
		}	
		return depCss;
	}
	
	@Override
	public void create(){
		super.create();
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
	}
	
	private Configuration getDateTimeFieldConfiguration(String modeSelection,String datetimefieldTimeonly, String modeTimeValue) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(DateTimeField.DATETIMEFIELD_MODE, modeSelection);
		configuration.setPropertyByName(DateTimeField.DATETIMEFIELD_TYPE, datetimefieldTimeonly);
		if(modeTimeValue!=null)
		  configuration.setPropertyByName(modeTimeValue, modeTimeValue);
		
		return configuration;
	}
	
	private void hidePicker() {
		dtPickPopup.hide();
	}
	
	private void setValidDates(ShowRangeEvent<Date> dateShowRangeEvent) {
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
    }
	
    private void disableDate(Date date) {
        dtPicker.getCalendarView().setEnabledOnDate(false, date);
    }


	/**
	 * Returns the primary style to be applied to the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getDatePickFieldPrimCss() {
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
		String dependentCss = "appops-dtPickBoxDep";
//		if(getConfigurationValue(SpinnerConfigurationConstant.BOX_DEPENDENTCSS) != null) {
//			dependentCss = getConfigurationValue(SpinnerConfigurationConstant.BOX_DEPENDENTCSS).toString();
//		}
		return dependentCss;
	}
	
	@Override
	public boolean isAllowBlank() {
		boolean allowblank = false; 
		if(getConfigurationValue(DatePickerConstant.DP_ALLOWBLNK) != null) {
			allowblank = (Boolean)getConfigurationValue(DatePickerConstant.DP_ALLOWBLNK);
		}
		return allowblank;
	}

	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(dtPickTrigger)) {
			if(dtPickTrigger.isDown()) {
				createPicker();
				displayPicker();
				} else if(!dtPickTrigger.isDown()) {

			}
		}
		
	}
	
	private void createPicker() {
		String fieldValue = dtPickBox.getText();
		Date dateVal = new Date();
		if(getErrors(fieldValue).isEmpty()) {
			dateVal = parseDate(fieldValue); 
		}
		dtPicker.setCurrentMonth(dateVal);
		dtPicker.setValue(dateVal);
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
		String fieldValue = dtPickBox.getText().trim();
		return fieldValue;
	}
	
	public Date getMinDate() {
		Date min = new Date(0);
		Date minDate = null;
		if(getConfigurationValue(DatePickerConstant.DP_MINDATE) != null) {
			String minDateStr =  getConfigurationValue(DatePickerConstant.DP_MINDATE).toString();
			minDate = parseDate(minDateStr);
			if(minDate != null) {
				min = minDate;
			}
		}
		return min;
	}

	public Date getMaxDate() {
		Date max = new Date(Long.MAX_VALUE);
		Date maxDate = null;
		if(getConfigurationValue(DatePickerConstant.DP_MAXDATE) != null) {
			String maxDateStr =  getConfigurationValue(DatePickerConstant.DP_MAXDATE).toString();
			maxDate = parseDate(maxDateStr);
			if(maxDate !=  null) {
				max = maxDate;
			}
		}
		return max;
	}

	private String getMinErrMsg() {
		String minMsg = "The date in the field should be equal or after " + format(getMinDate(), getFormat());
		if(getConfigurationValue(DatePickerConstant.DP_ERRMSGMIN) != null) {
			minMsg = getConfigurationValue(DatePickerConstant.DP_ERRMSGMIN).toString();
		}
		return minMsg;
	}
	
	private String getMaxErrMsg() {
		String maxMsg = "The date in the field should be equal or before " + format(getMaxDate(), getFormat());
		if(getConfigurationValue(DatePickerConstant.DP_ERRMSGMAX) != null) {
			maxMsg = getConfigurationValue(DatePickerConstant.DP_ERRMSGMAX).toString();
		}
		return maxMsg;
	}
	
	private String getBlankErrMsg() {
		String blnkMsg = "This field is required";
		if(getConfigurationValue(DatePickerConstant.DP_ERRMSGBLNK) != null) {
			blnkMsg = getConfigurationValue(DatePickerConstant.DP_ERRMSGBLNK).toString();
		}
		return blnkMsg;
	}
	
	private Date parseDate(String valueStr) {
		try {
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
	protected String getInvalidMsg() {
		String invalidMsg = getFieldValue() + " is not a valid date. It must be in the format " + getFormat();
		if(getConfigurationValue(BaseFieldConstant.BF_INVLDMSG) != null) {
			invalidMsg = getConfigurationValue(BaseFieldConstant.BF_INVLDMSG).toString();
		}
		return invalidMsg;
	}
	
	@Override
	public ArrayList<String> getErrors(String fieldValue) {
		ArrayList<String> errors = new ArrayList<String>();
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
		return errors;
	}
}