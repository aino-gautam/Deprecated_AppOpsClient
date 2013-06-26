package in.appops.client.common.fields;

import java.util.Date;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.LabelField.LabelFieldConstant;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DateTimeField extends Composite implements Field{

	private Configuration configuration;
	private String fieldValue;
	private DatePicker datePicker;
	private DateBox dateBox;
	private String fieldType;
	private String fieldMode;
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
	private VerticalPanel panel ;
	private HorizontalPanel selectedFieldValuePanel = new HorizontalPanel();
	
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
	public void create() throws AppOpsException {
		if(getConfiguration() == null)
			throw new AppOpsException("DateTimeField configuration unavailable");
		fieldMode = getConfiguration().getPropertyByName(DATETIMEFIELD_MODE).toString();
		fieldType = getConfiguration().getPropertyByName(DATETIMEFIELD_TYPE).toString();
		
		if(fieldMode.equalsIgnoreCase(MODE_VIEW)){
			panel = new VerticalPanel();
			//getConfiguration().getPropertyByName(DATETIMEFIELD_DATEFORMAT).toString();
			
			if(fieldType.equalsIgnoreCase(DATETIMEFIELD_DATEONLY)){
				if(getConfiguration().getPropertyByName(EVENTTYPE)!=null){
					String eventType = getConfiguration().getPropertyByName(EVENTTYPE);
					DateOnlyPicker dateOnlyPicker = new  DateOnlyPicker();
					dateOnlyPicker.setEntityType(eventType);
					dateOnlyPicker.addHandle(handler);
					panel.add(dateOnlyPicker);
					initWidget(panel);
					setCurrentField(dateOnlyPicker);
				}else{
					DateOnlyPicker dateOnlyPicker = new  DateOnlyPicker();
					dateOnlyPicker.addHandle(this);
					dateOnlyPicker.showPicker();
					panel.add(dateOnlyPicker);
					initWidget(panel);
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
				panel.add(timePicker);
				initWidget(panel);
				setCurrentField(timePicker);
			}else if(fieldType.equalsIgnoreCase(DATETIMEFIELD_DATETIMEONLY)){
				if(getConfiguration().getPropertyByName(EVENTTYPE)!=null){
					String entityType = getConfiguration().getPropertyByName(EVENTTYPE);
					DateTimePicker dateTimePicker = new DateTimePicker();
					dateTimePicker.setEntityType(entityType);
					dateTimePicker.addHandle(handler);
					panel.add(dateTimePicker);
					initWidget(panel);
					setCurrentField(dateTimePicker);
				}else{
					DateTimePicker dateTimePicker = new DateTimePicker();
					dateTimePicker.addHandle(this);
					panel.add(dateTimePicker);
					initWidget(panel);
					setCurrentField(dateTimePicker);
					
				}
			}
			
			
			
			
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
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
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
		try{
		if(fieldMode.equalsIgnoreCase(MODE_VIEW)){
			
			selectedFieldValuePanel.clear();
			int type = event.getEventType();
			if(type == FieldEvent.TIMEONLY){
				timeOnlyValue=(String) event.getEventData();
				LabelField label = new LabelField();
				label.setFieldValue("Time: ");
				label.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
				label.create();
				
				LabelField valueLabel = new LabelField();
				
				if(getConfiguration().getPropertyByName(SHORT_HOURS)!=null){
					
					valueLabel.setFieldValue(" "+timeOnlyValue+" hours");
					valueLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
					valueLabel.create();
				}else if(getConfiguration().getPropertyByName(SHORT_MINUTE)!=null){
					
					valueLabel.setFieldValue(" "+timeOnlyValue+" minutes");
					valueLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
					valueLabel.create();
				}else if(getConfiguration().getPropertyByName(SHORT_SECONDS)!=null){
					
					valueLabel.setFieldValue(" "+timeOnlyValue+" secs.");
					valueLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
					valueLabel.create();
				}else if(getConfiguration().getPropertyByName(Full_Time)!=null){
					
					valueLabel.setFieldValue(" "+timeOnlyValue+" hh:mm");
					valueLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
					valueLabel.create();
				}
				
				
				selectedFieldValuePanel.add(label);
				selectedFieldValuePanel.add(valueLabel);
				DOM.setStyleAttribute(selectedFieldValuePanel.getElement(), "padding", "10px");
				panel.add(selectedFieldValuePanel);
				DOM.setStyleAttribute(panel.getElement(), "marginLeft", "20px");
			}else if(type == FieldEvent.DATETIMEONLY){
				dateTimeOnlyValue = (String) event.getEventData();
				LabelField label = new LabelField();
				label.setFieldValue("Date Time: ");
				label.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
				label.create();
				
				LabelField valueLabel = new LabelField();
				valueLabel.setFieldValue(" "+dateTimeOnlyValue);
				valueLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
				valueLabel.create();
				
				selectedFieldValuePanel.add(label);
				selectedFieldValuePanel.add(valueLabel);
				DOM.setStyleAttribute(selectedFieldValuePanel.getElement(), "padding", "10px");
				panel.add(selectedFieldValuePanel);
				DOM.setStyleAttribute(panel.getElement(), "marginLeft", "20px");
			}else if(type == FieldEvent.DATEONLY){
				String dateOnlyValue = (String) event.getEventData();
				LabelField label = new LabelField();
				label.setFieldValue("Date: ");
				label.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
				label.create();
				
				LabelField valueLabel = new LabelField();
				valueLabel.setFieldValue(" "+dateOnlyValue);
				valueLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
				valueLabel.create();
				selectedFieldValuePanel.add(label);
				selectedFieldValuePanel.add(valueLabel);
				DOM.setStyleAttribute(selectedFieldValuePanel.getElement(), "padding", "10px");
				panel.add(selectedFieldValuePanel);
				DOM.setStyleAttribute(panel.getElement(), "marginLeft", "20px");
			}
		}else if(fieldMode.equalsIgnoreCase(MODE_SELECTION)){
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
		}catch(Exception e){
			e.printStackTrace();
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
	
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId){
		Configuration conf = new Configuration();
		conf.setPropertyByName(LabelFieldConstant.LBLFIELD_WORDWRAP, allowWordWrap);
		conf.setPropertyByName(LabelFieldConstant.LBLFIELD_DISPLAYTXT, "Config label");
		conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		conf.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		return conf;
	}

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		
	}
}