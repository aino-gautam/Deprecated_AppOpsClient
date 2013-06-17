package in.appops.client.common.components;

import java.util.Date;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.DateTimeField;
import in.appops.client.common.fields.Field;
import in.appops.client.common.snippet.EventListSnippet;
import in.appops.client.common.snippet.ListSnippet;
import in.appops.client.common.snippet.SnippetConstant;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CalendarEvents extends VerticalPanel implements ClickHandler,Field{

	private HorizontalPanel dateTimePickerPlus;
	private DateTimeField dateTimeOnlyField ;
	private Button createEventPlusButton;
	private EventListSnippet eventListSnippet;
	private HorizontalPanel eventListPanel;
	
	public CalendarEvents() {
		// TODO Auto-generated constructor stub
	}
	
	public void createUi(){
		clear();
		try{
			dateTimePickerPlus = new HorizontalPanel();
			
			dateTimeOnlyField = new DateTimeField();
			dateTimeOnlyField.setHandler(this);
			dateTimeOnlyField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_SELECTION,DateTimeField.DATETIMEFIELD_DATEONLY,null));
			dateTimeOnlyField.createField();
			
			createEventPlusButton = new Button("+");
			createEventPlusButton.setTitle("Create a event");
			
			eventListSnippet = new EventListSnippet();
			eventListSnippet.setConfiguration(createConfigurationForDefaultList());
			eventListSnippet.initialize();
			dateTimePickerPlus.add(dateTimeOnlyField);
			dateTimePickerPlus.setCellWidth(dateTimeOnlyField, "45%");
			dateTimePickerPlus.add(createEventPlusButton);
			dateTimePickerPlus.setCellWidth(createEventPlusButton, "5%");
			
			dateTimePickerPlus.setCellHorizontalAlignment(dateTimeOnlyField, HasHorizontalAlignment.ALIGN_CENTER);
			dateTimePickerPlus.setCellHorizontalAlignment(createEventPlusButton, HasHorizontalAlignment.ALIGN_CENTER);
			
			createEventPlusButton.addClickHandler(this);
			
			add(dateTimePickerPlus);
			setCellHorizontalAlignment(dateTimePickerPlus, HasHorizontalAlignment.ALIGN_CENTER);
			eventListPanel = new HorizontalPanel();
			eventListPanel.add(eventListSnippet);
			add(eventListPanel);
			dateTimePickerPlus.setWidth("50%");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private Configuration createConfigurationForDefaultList() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(SnippetConstant.SELECTIONMODE, false);
		configuration.setPropertyByName(ListSnippet.SCROLLPANELWIDTH, 650);
		configuration.setPropertyByName(ListSnippet.SCROLLPANELHEIGHT, 500);
		configuration.setPropertyByName(EventListSnippet.DEFAULT_LIST, "defaultList");
		setConfiguration(configuration);
		return configuration;
		
	}

	private Configuration getDateTimeFieldConfiguration(String modeSelection,String datetimefieldTimeonly, String modeTimeValue) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(DateTimeField.DATETIMEFIELD_MODE, modeSelection);
		configuration.setPropertyByName(DateTimeField.DATETIMEFIELD_TYPE, datetimefieldTimeonly);
		configuration.setPropertyByName(DateTimeField.EVENTTYPE, "event");
		if(modeTimeValue!=null)
		  configuration.setPropertyByName(modeTimeValue, modeTimeValue);
		
		return configuration;
	}

	public void addHandle(FieldEventHandler handler) {
		AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, this, handler);
		
	}
	
	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender instanceof Button){
			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent.setEventType(FieldEvent.EDITINPROGRESS);
			fieldEvent.setEventData("Quick event");	
			AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, CalendarEvents.this);
		}
		
	}

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		eventListPanel.clear();
		int eventType=event.getEventType();
		Date eventData = (Date) event.getEventData();
		eventListSnippet = new EventListSnippet();
		eventListSnippet.setConfiguration(createConfigurationForDateList(eventData));
		eventListSnippet.initialize();
		eventListPanel.add(eventListSnippet);
	}

	private Configuration createConfigurationForDateList(Date eventData) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(SnippetConstant.SELECTIONMODE, false);
		configuration.setPropertyByName(ListSnippet.SCROLLPANELWIDTH, 650);
		configuration.setPropertyByName(ListSnippet.SCROLLPANELHEIGHT, 500);
		configuration.setPropertyByName(EventListSnippet.DATEBASED_LIST, "dateBasedList");
		configuration.setPropertyByName(EventListSnippet.DATEBASED_LIST_VALUE, eventData);
		
		
		return configuration;
	}

	@Override
	public void createField() throws AppOpsException {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		// TODO Auto-generated method stub
		
	}
}
