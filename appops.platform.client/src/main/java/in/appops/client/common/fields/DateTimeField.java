package in.appops.client.common.fields;

import java.util.Date;

import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DateTimeField extends Composite implements Field{

	private Configuration configuration;
	private String fieldValue;
	private DatePicker datePicker;
	private DateBox dateBox;
	private String fieldType;
	private Date value;
	public static final String DATETIMEFIELD_DATEONLY = "dateTimeFieldDateOnly";
	public static final String DATETIMEFIELD_TIMEONLY = "dateTimeFieldTimeOnly";
	public static final String DATETIMEFIELD_DATETIMEONLY = "dateTimeFieldDateTimeOnly";
	public static final String DATETIMEFIELD_DATEFORMAT = "dateTimeFieldDateFormat";

	public static final String DATETIMEFIELD_MODE = "dateTimeFieldMode";
	public static final String DATETIMEFIELD_TYPE = "dateTimeFieldType";
	public static final String MODE_VIEW = "view";
	public static final String MODE_SELECTION = "selection";
	
	public static final String SHORT_HOURS = "short_Hours";
	public static final String SHORT_MINUTE = "short_Minute";
	public static final String SHORT_SECONDS = "short_Seconds";
	public static final String Full_Time = "full_Time";
			
	public DateTimeField(){
		
	}
	
	@Override
	public Configuration getConfiguration() {
		return this.configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	@Override
	public void createField() throws AppOpsException {
		if(getConfiguration() == null)
			throw new AppOpsException("DateTimeField configuration unavailable");
		String fieldMode = getConfiguration().getPropertyByName(DATETIMEFIELD_MODE).toString();
		fieldType = getConfiguration().getPropertyByName(DATETIMEFIELD_TYPE).toString();
		
		if(fieldMode.equalsIgnoreCase(MODE_VIEW)){
			
			getConfiguration().getPropertyByName(DATETIMEFIELD_DATEFORMAT).toString();
			
		}else if(fieldMode.equalsIgnoreCase(MODE_SELECTION)){
			if(fieldType.equalsIgnoreCase(DATETIMEFIELD_DATEONLY)){
				DateOnlyPicker dateOnlyPicker = new  DateOnlyPicker();
				//datePicker.setValue(value);
				initWidget(dateOnlyPicker);
			}else if(fieldType.equalsIgnoreCase(DATETIMEFIELD_TIMEONLY)){
				/* datePicker = new DatePicker();
				 long time=value.getTime();
				 value.setTime(time);
				 datePicker.setValue(value);*/
				TimePicker timePicker = null ;
				
				if(getConfiguration().getPropertyByName(SHORT_HOURS)!=null){
					timePicker = new TimePicker(SHORT_HOURS);
					
				}else if(getConfiguration().getPropertyByName(SHORT_MINUTE)!=null){
					timePicker = new TimePicker(SHORT_MINUTE);
					
				}else if(getConfiguration().getPropertyByName(SHORT_SECONDS)!=null){
					timePicker = new TimePicker(SHORT_SECONDS);
					
				}else if(getConfiguration().getPropertyByName(Full_Time)!=null){
					timePicker = new TimePicker(Full_Time);
					
				}
				
				initWidget(timePicker);
			}else if(fieldType.equalsIgnoreCase(DATETIMEFIELD_DATETIMEONLY)){
				DateTimePicker dateTimePicker = new DateTimePicker();
				initWidget(dateTimePicker);
			}
		}
		
		
		
	}

	@Override
	public void clearField() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetField() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFieldValue() {
		return this.fieldValue;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}

	
}