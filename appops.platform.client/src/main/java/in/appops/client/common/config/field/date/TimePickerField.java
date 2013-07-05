package in.appops.client.common.config.field.date;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.spinner.SpinnerField;
import in.appops.client.common.config.field.spinner.SpinnerField.SpinnerFieldConstant;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class TimePickerField extends BaseField{
	
	private HorizontalPanel timePanel;
	private SpinnerField hourSpinner;
	private SpinnerField minuteSpinner;
	private SpinnerField secondSpinner;
	private SpinnerField formatSpinner;
	
	public TimePickerField() {
	}
	
	@Override
	public void create() {
		getBasePanel().add(timePanel,DockPanel.CENTER);
	}
	
	@Override
	public void configure() {
				
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
		
		if(getBaseFieldCss()!=null)
			getBasePanel().setStyleName(getBaseFieldCss());
		
	}
	
	private LabelField getSeperator(){
		LabelField seperator =new LabelField();
		seperator.setConfiguration(getLabelConfiguration());
		seperator.configure();
		seperator.create();
		return seperator;
	}
	
	private SpinnerField getSpinnerField(Configuration configuration){
		SpinnerField spinner = new SpinnerField();
		spinner.setConfiguration(configuration);
		spinner.configure();
		spinner.create();
		return spinner;
	}
	
	@Override
	public Object getValue(){
		Date date = null;
		String time = hourSpinner.getValue().toString()+":"+minuteSpinner.getValue().toString() + ":" +secondSpinner.getValue().toString();
		
		String timeFormat = getTimeFormat();
		
		if(timeFormat.equalsIgnoreCase(TimePickerFieldConstant.AMPMFORMAT_WITHOUT_SECONDS) ||timeFormat.equalsIgnoreCase(TimePickerFieldConstant.AMPMFORMAT_WITH_SECONDS)){
			date = DateTimeFormat.getFormat(getTimeFormat()).parse(time+" "+ formatSpinner.getValue().toString());
		}else{
			date = DateTimeFormat.getFormat(getTimeFormat()).parse(time);
		}
		return date;
	}
	
	
	@Override
	public String getFieldValue() {
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
		if(getConfigurationValue(TimePickerFieldConstant.TIME_FORMAT) != null) {
			format = getConfigurationValue(TimePickerFieldConstant.TIME_FORMAT).toString();
		}
		return format;
	}
	
	public String getSpinnerPrimaryCss(){
		String primCss = "timeSpinner";
		if(getConfigurationValue(TimePickerFieldConstant.TIMESPINNER_PCLS) != null) {
			
			primCss = getConfigurationValue(TimePickerFieldConstant.TIMESPINNER_PCLS).toString();
		}
		return primCss;
		
	}
	
	public String getSpinnerDependentCss(){
		String dependentCss = null;
		if(getConfigurationValue(TimePickerFieldConstant.TIMESPINNER_DCLS) != null) {
			
			dependentCss = getConfigurationValue(TimePickerFieldConstant.TIMESPINNER_DCLS).toString();
		}
		return dependentCss;
		
	}
	
	private Configuration getSpinnerConfiguration(Float minValue , Float maxvalue ,int defaultVal){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(SpinnerFieldConstant.SP_MAXVAL, maxvalue);
		configuration.setPropertyByName(SpinnerFieldConstant.SP_MINVAL, minValue);
		configuration.setPropertyByName(SpinnerFieldConstant.SP_CIRCULAR, true);
		configuration.setPropertyByName(SpinnerFieldConstant.BF_PCLS, getSpinnerPrimaryCss());
		configuration.setPropertyByName(SpinnerFieldConstant.BF_DCLS, getSpinnerDependentCss());
		configuration.setPropertyByName(SpinnerFieldConstant.BF_DEFVAL, Float.parseFloat(String.valueOf(defaultVal)));
		configuration.setPropertyByName(SpinnerFieldConstant.SP_TYPE, SpinnerFieldConstant.SP_TYPENUMERIC);
		configuration.setPropertyByName(SpinnerFieldConstant.BF_ERRPOS, SpinnerFieldConstant.BF_BOTTOM);
		
		return configuration;
	}
	
	private Configuration getLabelConfiguration(){
		
		Configuration conf = new Configuration();
		conf.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, ":");
		conf.setPropertyByName(LabelFieldConstant.BF_PCLS, "timeSeperator");
		return conf;
	}
	
	private Configuration getAMPMSpinnerConfiguration( int hours){
		ArrayList<String> format = new ArrayList<String>();
		format.add("AM");
		format.add("PM");
		Configuration configuration = new Configuration();
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
