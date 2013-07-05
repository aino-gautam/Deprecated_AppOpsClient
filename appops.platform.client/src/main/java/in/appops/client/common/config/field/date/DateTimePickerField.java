package in.appops.client.common.config.field.date;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.date.DatePickerField.DatePickerConstant;
import in.appops.client.common.config.field.date.TimePickerField.TimePickerFieldConstant;
import in.appops.platform.core.shared.Configuration;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class DateTimePickerField extends BaseField{
	
	private HorizontalPanel dateTimePanel ;
	private DatePickerField dtPickerField;
	private TimePickerField timeField;
	
	public DateTimePickerField() {
		
	}
	
	/**
	 * Method creates the DateTimepicker field.
	 */
	
	@Override
	public void create() {
		getBasePanel().add(dateTimePanel,DockPanel.CENTER);
	}
	
	/**
	 * Method configure the field.
	 */
	@Override
	public void configure() {
		
		dateTimePanel = new HorizontalPanel();
		
		timeField = new TimePickerField();
		timeField.setConfiguration(getTimePickerConfiguration());
		timeField.configure();
		timeField.create();
		
		dtPickerField = new DatePickerField();
		dtPickerField.setConfiguration(getDatePickerConfiguration());
		dtPickerField.configure();
		dtPickerField.create();
		
		dateTimePanel.add(dtPickerField);
		dateTimePanel.add(timeField);
				
		if(getBaseFieldPrimCss()!=null)
			getBasePanel().setStylePrimaryName(getBaseFieldPrimCss());
		
		if(getBaseFieldCss()!=null)
			getBasePanel().setStyleName(getBaseFieldCss());
	}
	
	@Override
	public Object getValue(){
		String dateTime = dtPickerField.getFieldValue() + " "+timeField.getFieldValue();
		Date result = DateTimeFormat.getFormat(dtPickerField.getFormat() +" "+ timeField.getTimeFormat()).parse(dateTime);
						
		return result;
	}
	
	/**
	 * Method gets the property value from configuration and return.
	 * @param propertyName
	 * @return
	 */
	private String getValueFromConf(String propertyName){
					
		if(getConfigurationValue(propertyName)!=null)
			return getConfigurationValue(propertyName).toString();
		
		return null;
	}
	/**
	 * Method get the time configuration values from DateTimePickerField configuration and creates the configuration object for TimePickerField.
	 * @return
	 */
	private Configuration getTimePickerConfiguration(){
		
		Configuration configuration = new Configuration();
		
		configuration.setPropertyByName(TimePickerFieldConstant.BF_PCLS, getValueFromConf(DateTimePickerFieldConstant.TIMEPICKER_PCLS));
		configuration.setPropertyByName(TimePickerFieldConstant.BF_DCLS, getValueFromConf(DateTimePickerFieldConstant.TIMEPICKER_DCLS));
		configuration.setPropertyByName(TimePickerFieldConstant.TIME_FORMAT, getValueFromConf(TimePickerFieldConstant.TIME_FORMAT));
		configuration.setPropertyByName(TimePickerFieldConstant.TIMESPINNER_PCLS, getValueFromConf(DateTimePickerFieldConstant.TIMESPINNER_PCLS));
		configuration.setPropertyByName(TimePickerFieldConstant.TIMESPINNER_DCLS, getValueFromConf(DateTimePickerFieldConstant.TIMESPINNER_DCLS));
		return configuration;
	}
	
	/**
	 * Method get the time configuration values from DateTimePickerField configuration and creates the configuration object for DateTimePickerField.
	 * @return
	 */
	private Configuration getDatePickerConfiguration(){
		
		Configuration configuration = new Configuration();
		
		configuration.setPropertyByName(DatePickerConstant.BF_DEFVAL, getValueFromConf(DateTimePickerFieldConstant.DATE_DEFVAL));
		configuration.setPropertyByName(DatePickerConstant.DP_MAXDATE, getValueFromConf(DateTimePickerFieldConstant.MAXDATE));
		configuration.setPropertyByName(DatePickerConstant.DP_MINDATE, getValueFromConf(DateTimePickerFieldConstant.MINDATE));
		configuration.setPropertyByName(DatePickerConstant.DP_FORMAT, getValueFromConf(DateTimePickerFieldConstant.DATE_FORMAT));
		
		boolean allowBlank = Boolean.valueOf(getValueFromConf(DateTimePickerFieldConstant.DATE_ALLOWBLNK));
		configuration.setPropertyByName(DatePickerConstant.DP_ALLOWBLNK,allowBlank);
		configuration.setPropertyByName(DatePickerConstant.BF_PCLS, getValueFromConf(DateTimePickerFieldConstant.DATE_PCLS));
		configuration.setPropertyByName(DatePickerConstant.BF_DCLS, getValueFromConf(DateTimePickerFieldConstant.DATE_DCLS));
		configuration.setPropertyByName(DatePickerConstant.BF_ERRPOS, getValueFromConf(DateTimePickerFieldConstant.BF_ERRPOS));
		configuration.setPropertyByName(DatePickerConstant.DP_ERRMSGBLNK, getValueFromConf(DateTimePickerFieldConstant.DATE_ERRMSGBLNK));
		configuration.setPropertyByName(DatePickerConstant.DP_ERRMSGMAX, getValueFromConf(DateTimePickerFieldConstant.DATE_ERRMSGMAX));
		configuration.setPropertyByName(DatePickerConstant.DP_ERRMSGMIN, getValueFromConf(DateTimePickerFieldConstant.DATE_ERRMSGMIN));

		return configuration;
		
	}

	public interface DateTimePickerFieldConstant extends BaseFieldConstant {
		
		public static final String DATE_PCLS = "dateBoxPrimaryCss";
		
		/** Style class dependent for spinner text box. **/
		public static final String DATE_DCLS = "dateBoxDependentCss";
		
		public static final String MAXDATE = "maxDate";
		
		public static final String MINDATE = "minDate";
		
		public static final String DATE_DEFVAL = "defaultValueForDate";
		
		public static final String DATE_FORMAT = "format";
		
		public static final String ALTFORMAT= "altformat";
		
		public static final String DATE_ALLOWBLNK = "allowBlank";

		public static final String DATE_ERRMSGBLNK = "blnktxt";
		
		public static final String DATE_ERRMSGMIN = "minText";
		
		public static final String DATE_ERRMSGMAX = "maxText";
		
		public static final String TIMEPICKER_PCLS = "timePickerPrimeCss";
		
		public static final String TIMEPICKER_DCLS = "timePickerDependent";
		
		public static final String TIMESPINNER_PCLS = "timeSpinnerPrimeCss";
		
		public static final String TIMESPINNER_DCLS = "timeSpinnerDependentCss";
		
		public static final String TIME_FORMAT = "timeFormat";
		
		public static final String FORMAT24HOUR_WITH_SECONDS = "HH:mm:ss";
		
		public static final String FORMAT24HOUR_WITHOUT_SECONDS = "HH:mm";
		
		public static final String AMPMFORMAT_WITH_SECONDS = "hh:mm:ss a";
		
		public static final String AMPMFORMAT_WITHOUT_SECONDS = "hh:mm a";

		
	}
	

}
