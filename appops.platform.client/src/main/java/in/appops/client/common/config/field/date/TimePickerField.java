package in.appops.client.common.config.field.date;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.spinner.SpinnerField;
import in.appops.client.common.config.field.spinner.SpinnerField.SpinnerFieldConstant;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class TimePickerField extends BaseField{
	
	private HorizontalPanel timePanel;
	private SpinnerField hourSpinner;
	private SpinnerField minuteSpinner;
	private SpinnerField secondSpinner;
	private SpinnerField formatSpinner;
	private Logger logger = Logger.getLogger(getClass().getName());
	public TimePickerField() {
	}
	
	@Override
	public void create() {
		
		try {
			logger.log(Level.INFO,"[TimePickerField]:: In create  method ");
			getBasePanel().add(timePanel,DockPanel.CENTER);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[TimePickerField]::Exception In create  method :"+e);
		}
	}
	
	@Override
	public void configure() {
				
		try {
			logger.log(Level.INFO,"[TimePickerField]:: In configure  method ");
			timePanel =new HorizontalPanel();
			Date date = new Date();
			
			String timeFormat = getTimeFormat();
			int hour = date.getHours();
			
			if(timeFormat.equalsIgnoreCase(TimePickerFieldConstant.FORMAT24HOUR_WITH_SECONDS) ||timeFormat.equalsIgnoreCase(TimePickerFieldConstant.FORMAT24HOUR_WITHOUT_SECONDS)){
				hourSpinner = getSpinnerField(getSpinnerConfiguration(0F, 23F,hour));
			}else if(timeFormat.equalsIgnoreCase(TimePickerFieldConstant.AMPMFORMAT_WITHOUT_SECONDS) ||timeFormat.equalsIgnoreCase(TimePickerFieldConstant.AMPMFORMAT_WITH_SECONDS)){
				hourSpinner = getSpinnerField(getSpinnerConfiguration(1F, 12F,hour-12));
			}
			
			minuteSpinner = getSpinnerField(getSpinnerConfiguration(0F, 59F,date.getMinutes()));
			
			timePanel.add(hourSpinner);
			timePanel.add(getSeperator());
			timePanel.add(minuteSpinner);
			
			if(timeFormat.equalsIgnoreCase(TimePickerFieldConstant.FORMAT24HOUR_WITH_SECONDS) ||timeFormat.equalsIgnoreCase(TimePickerFieldConstant.AMPMFORMAT_WITH_SECONDS)){
				secondSpinner = getSpinnerField(getSpinnerConfiguration(0F, 59F,date.getSeconds()));
				timePanel.add(getSeperator());
				timePanel.add(secondSpinner);
			}
			
			if(timeFormat.equalsIgnoreCase(TimePickerFieldConstant.AMPMFORMAT_WITHOUT_SECONDS) ||timeFormat.equalsIgnoreCase(TimePickerFieldConstant.AMPMFORMAT_WITH_SECONDS)){
				
				formatSpinner = getSpinnerField(getAMPMSpinnerConfiguration(hour));
				timePanel.add(formatSpinner);
			}
			
			if(getBaseFieldPrimCss()!=null)
				getBasePanel().setStylePrimaryName(getBaseFieldPrimCss());
			
			if(getBaseFieldDependentCss()!=null)
				getBasePanel().setStyleName(getBaseFieldDependentCss());
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[TimePickerField]::Exception In configure  method :"+e);
		}
		
	}
	
	private LabelField getSeperator(){
		LabelField seperator =new LabelField();
		try {
			logger.log(Level.INFO,"[TimePickerField]:: In getSeperator  method ");
			seperator.setConfiguration(getLabelConfiguration());
			seperator.configure();
			seperator.create();
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[TimePickerField]::Exception In getSeperator  method :"+e);
		}
		return seperator;
	}
	
	private SpinnerField getSpinnerField(Configuration configuration){
		SpinnerField spinner = new SpinnerField();
		try {
			logger.log(Level.INFO,"[TimePickerField]:: In getSpinnerField  method ");
			spinner.setConfiguration(configuration);
			spinner.configure();
			spinner.create();
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[TimePickerField]::Exception In getSpinnerField  method :"+e);
		}
		return spinner;
	}
	
	@Override
	public Object getValue(){
		Date date = null;
		try {
			logger.log(Level.INFO,"[TimePickerField]:: In getValue  method ");
			String time = hourSpinner.getValue().toString()+":"+minuteSpinner.getValue().toString() + ":" +secondSpinner.getValue().toString();
			
			String timeFormat = getTimeFormat();
			
			if(timeFormat.equalsIgnoreCase(TimePickerFieldConstant.AMPMFORMAT_WITHOUT_SECONDS) ||timeFormat.equalsIgnoreCase(TimePickerFieldConstant.AMPMFORMAT_WITH_SECONDS)){
				date = DateTimeFormat.getFormat(getTimeFormat()).parse(time+" "+ formatSpinner.getValue().toString());
			}else{
				date = DateTimeFormat.getFormat(getTimeFormat()).parse(time);
			}
		} catch (IllegalArgumentException e) {
			logger.log(Level.SEVERE,"[TimePickerField]::Exception In getValue  method :"+e);
		}
		return date;
	}
	
	
	@Override
	public String getFieldValue() {
		logger.log(Level.INFO,"[TimePickerField]:: In getFieldValue  method ");
		String time = hourSpinner.getValue().toString()+":"+minuteSpinner.getValue().toString() + ":" +secondSpinner.getValue().toString();
		
		String timeFormat =  getTimeFormat();
		
		if(timeFormat.equalsIgnoreCase(TimePickerFieldConstant.AMPMFORMAT_WITHOUT_SECONDS) ||timeFormat.equalsIgnoreCase(TimePickerFieldConstant.AMPMFORMAT_WITH_SECONDS)){
			return time+" "+ formatSpinner.getValue().toString();
		}else{
			return time;
		}
	}
	
	/****************************************************************/
	
	public String getTimeFormat() {
		String format = TimePickerFieldConstant.AMPMFORMAT_WITH_SECONDS;
		try {
			logger.log(Level.INFO,"[TimePickerField]:: In getTimeFormat  method ");
			if(viewConfiguration.getConfigurationValue(TimePickerFieldConstant.TIME_FORMAT) != null) {
				format = viewConfiguration.getConfigurationValue(TimePickerFieldConstant.TIME_FORMAT).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[TimePickerField]::Exception In getTimeFormat  method :"+e);
		}
		return format;
	}
	
	public String getSpinnerPrimaryCss(){
		String primCss = "timeSpinner";
		try {
			logger.log(Level.INFO,"[TimePickerField]:: In getSpinnerPrimaryCss  method ");
			if(viewConfiguration.getConfigurationValue(TimePickerFieldConstant.TIMESPINNER_PCLS) != null) {
				
				primCss = viewConfiguration.getConfigurationValue(TimePickerFieldConstant.TIMESPINNER_PCLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[TimePickerField]::Exception In getSpinnerPrimaryCss  method :"+e);
		}
		return primCss;
		
	}
	
	public String getSpinnerDependentCss(){
		String dependentCss = null;
		try {
			logger.log(Level.INFO,"[TimePickerField]:: In getSpinnerDependentCss  method ");
			if(viewConfiguration.getConfigurationValue(TimePickerFieldConstant.TIMESPINNER_DCLS) != null) {
				
				dependentCss = viewConfiguration.getConfigurationValue(TimePickerFieldConstant.TIMESPINNER_DCLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[TimePickerField]::Exception In getSpinnerDependentCss  method :"+e);
		}
		return dependentCss;
		
	}
	
	private Configuration getSpinnerConfiguration(Float minValue , Float maxvalue ,int defaultVal){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[TimePickerField]:: In getSpinnerConfiguration  method ");
			//configuration.setPropertyByName(SpinnerFieldConstant.SP_MAXVAL, maxvalue);
			//configuration.setPropertyByName(SpinnerFieldConstant.SP_MINVAL, minValue);
			configuration.setPropertyByName(SpinnerFieldConstant.SP_CIRCULAR, true);
			configuration.setPropertyByName(SpinnerFieldConstant.BF_PCLS, getSpinnerPrimaryCss());
			configuration.setPropertyByName(SpinnerFieldConstant.BF_DCLS, getSpinnerDependentCss());
			configuration.setPropertyByName(SpinnerFieldConstant.BF_DEFVAL, Float.parseFloat(String.valueOf(defaultVal)));
			configuration.setPropertyByName(SpinnerFieldConstant.SP_TYPE, SpinnerFieldConstant.SP_TYPENUMERIC);
			configuration.setPropertyByName(SpinnerFieldConstant.BF_ERRPOS, SpinnerFieldConstant.BF_BOTTOM);
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE,"[TimePickerField]::Exception In getSpinnerConfiguration  method :"+e);
		}
		
		return configuration;
	}
	
	private Configuration getLabelConfiguration(){
		
		Configuration conf = new Configuration();
		try {
			logger.log(Level.INFO,"[TimePickerField]:: In getLabelConfiguration  method ");
			conf.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, ":");
			conf.setPropertyByName(LabelFieldConstant.BF_PCLS, "timeSeperator");
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[TimePickerField]::Exception In getLabelConfiguration  method :"+e);
		}
		return conf;
	}
	
	private Configuration getAMPMSpinnerConfiguration( int hours){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[TimePickerField]:: In getAMPMSpinnerConfiguration  method ");
			ArrayList<String> format = new ArrayList<String>();
			format.add("AM");
			format.add("PM");
			configuration.setPropertyByName(SpinnerFieldConstant.SP_TYPE, SpinnerFieldConstant.SP_TYPELIST);
			configuration.setPropertyByName(SpinnerFieldConstant.BF_PCLS, getSpinnerPrimaryCss());
			configuration.setPropertyByName(SpinnerFieldConstant.BF_DCLS, getSpinnerDependentCss());
			if(hours >= 12){
				configuration.setPropertyByName(SpinnerFieldConstant.BF_DEFVAL,"PM");
			}else{
				configuration.setPropertyByName(SpinnerFieldConstant.BF_DEFVAL,"AM");
			}
			
			configuration.setPropertyByName(SpinnerFieldConstant.SP_VALUELIST, format);
			configuration.setPropertyByName(SpinnerFieldConstant.SP_VALUEIDX, 0);
			configuration.setPropertyByName(SpinnerFieldConstant.SP_CIRCULAR, true);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[TimePickerField]::Exception In getAMPMSpinnerConfiguration  method :"+e);
		}
		return configuration;
	}
	
	/******************************************************************************/
	
	public interface TimePickerFieldConstant extends BaseFieldConstant{
				
		public static final String TIMESPINNER_PCLS = "spinnerPrimeCss";
		
		public static final String TIMESPINNER_DCLS = "spinnerDependentCss";
		
		public static final String TIME_FORMAT = "timeFormat";
		
		public static final String FORMAT24HOUR_WITH_SECONDS = "HH:mm:ss";
		
		public static final String FORMAT24HOUR_WITHOUT_SECONDS = "HH:mm";
		
		public static final String AMPMFORMAT_WITH_SECONDS = "hh:mm:ss a";
		
		public static final String AMPMFORMAT_WITHOUT_SECONDS = "hh:mm a";
		
	}

}
