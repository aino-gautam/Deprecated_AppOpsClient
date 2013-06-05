package in.appops.client.common.fields;

import java.util.Date;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DateTimeField extends Composite implements Field{

	private Configuration configuration;
	private String fieldValue;
	private DatePicker datePicker;
	private DateBox dateBox;
	private String fieldType;
	private Date value;
	private FieldEventHandler handler;
	
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
	public static final String EVENTTYPE = "eventType";

	private Widget currentField = null;
	private String timeOnlyValue;
	private String dateTimeOnlyValue;
	private Date dateOnlyValue;
	
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
				if(getConfiguration().getPropertyByName(EVENTTYPE)!=null){
					String eventType = getConfiguration().getPropertyByName(EVENTTYPE);
					DateOnlyPicker dateOnlyPicker = new  DateOnlyPicker();
					dateOnlyPicker.setEntityType(eventType);
					dateOnlyPicker.addHandle(handler);
					initWidget(dateOnlyPicker);
					setCurrentField(dateOnlyPicker);
				}else{
					DateOnlyPicker dateOnlyPicker = new  DateOnlyPicker();
					dateOnlyPicker.addHandle(this);
					dateOnlyPicker.showPicker();
					initWidget(dateOnlyPicker);
					setCurrentField(dateOnlyPicker);
				}
			}else if(fieldType.equalsIgnoreCase(DATETIMEFIELD_TIMEONLY)){
				TimePicker timePicker = null ;
				
				if(getConfiguration().getPropertyByName(SHORT_HOURS)!=null){
					timePicker = new TimePicker(SHORT_HOURS);
					timePicker.addHandle(this);
				}else if(getConfiguration().getPropertyByName(SHORT_MINUTE)!=null){
					timePicker = new TimePicker(SHORT_MINUTE);
					timePicker.addHandle(this);
				}else if(getConfiguration().getPropertyByName(SHORT_SECONDS)!=null){
					timePicker = new TimePicker(SHORT_SECONDS);
					timePicker.addHandle(this);
				}else if(getConfiguration().getPropertyByName(Full_Time)!=null){
					timePicker = new TimePicker(Full_Time);
					timePicker.addHandle(this);
				}
				initWidget(timePicker);
				setCurrentField(timePicker);
			}else if(fieldType.equalsIgnoreCase(DATETIMEFIELD_DATETIMEONLY)){
				if(getConfiguration().getPropertyByName(EVENTTYPE)!=null){
					String entityType = getConfiguration().getPropertyByName(EVENTTYPE);
					DateTimePicker dateTimePicker = new DateTimePicker();
					dateTimePicker.setEntityType(entityType);
					dateTimePicker.addHandle(handler);
					initWidget(dateTimePicker);
					setCurrentField(dateTimePicker);
				}else{
					DateTimePicker dateTimePicker = new DateTimePicker();
					dateTimePicker.addHandle(this);
					initWidget(dateTimePicker);
					setCurrentField(dateTimePicker);
					
				}
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
		int type = event.getEventType();
		if(type == FieldEvent.TIMEONLY){
			timeOnlyValue=(String) event.getEventData();
		}else if(type == FieldEvent.DATETIMEONLY){
			dateTimeOnlyValue = (String) event.getEventData();
			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent.setEventType(FieldEvent.DATETIMEONLY);
			fieldEvent.setEventData(dateTimeOnlyValue);	
			AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, DateTimeField.this);
		}else if(type == FieldEvent.DATEONLY){
			dateOnlyValue = (Date) event.getEventData();
		}
	}

	public Widget getCurrentField() {
		return currentField;
	}

	public void setCurrentField(Widget currentField) {
		this.currentField = currentField;
	}

	public FieldEventHandler getHandler() {
		return handler;
	}

	public void setHandler(FieldEventHandler handler) {
		this.handler = handler;
	}

	public String getTimeOnlyValue() {
		return timeOnlyValue;
	}

	public void setTimeOnlyValue(String timeOnlyValue) {
		this.timeOnlyValue = timeOnlyValue;
	}

	public String getDateTimeOnlyValue() {
		return dateTimeOnlyValue;
	}

	public void setDateTimeOnlyValue(String dateTimeOnlyValue) {
		this.dateTimeOnlyValue = dateTimeOnlyValue;
	}

	public Date getDateOnlyValue() {
		return dateOnlyValue;
	}

	public void setDateOnlyValue(Date dateOnlyValue) {
		this.dateOnlyValue = dateOnlyValue;
	}
}