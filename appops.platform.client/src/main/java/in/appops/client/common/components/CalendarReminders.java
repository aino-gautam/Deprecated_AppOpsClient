package in.appops.client.common.components;

import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.fields.DateTimeField;
import in.appops.client.common.fields.Field;
import in.appops.client.common.snippet.ReminderListSnippet;
import in.appops.client.common.snippet.SnippetConstant;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CalendarReminders extends VerticalPanel implements Field{

	private HorizontalPanel dateTimePickerPlus;
	private DateTimeField dateTimeOnlyField ;
	private HorizontalPanel reminderListPanel;
	private ReminderListSnippet reminderListSnippet;
	
	
	public CalendarReminders() {
		// TODO Auto-generated constructor stub
	}
	
	public void createUi(){
		clear();
		try{
			dateTimePickerPlus = new HorizontalPanel();
			reminderListPanel = new HorizontalPanel();
			reminderListSnippet = new ReminderListSnippet();
			dateTimeOnlyField = new DateTimeField();
			dateTimeOnlyField.setHandler(this);
			dateTimeOnlyField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_SELECTION,DateTimeField.DATETIMEFIELD_DATETIMEONLY,null));
			dateTimeOnlyField.createField();
			
			dateTimePickerPlus.add(dateTimeOnlyField);
			
			add(dateTimePickerPlus);
			setCellVerticalAlignment(dateTimePickerPlus, HasVerticalAlignment.ALIGN_MIDDLE);
			setCellHorizontalAlignment(dateTimePickerPlus, HasHorizontalAlignment.ALIGN_CENTER);
			reminderListSnippet.setConfiguration(createConfigurationForDefaultList());
			reminderListSnippet.initialize();
			
			reminderListPanel.add(reminderListSnippet);
			
			add(reminderListPanel);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private Configuration getDateTimeFieldConfiguration(String modeSelection,String datetimefieldTimeonly, String modeTimeValue) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(DateTimeField.DATETIMEFIELD_MODE, modeSelection);
		configuration.setPropertyByName(DateTimeField.DATETIMEFIELD_TYPE, datetimefieldTimeonly);
		configuration.setPropertyByName(DateTimeField.EVENTTYPE, "reminder");
		if(modeTimeValue!=null)
		  configuration.setPropertyByName(modeTimeValue, modeTimeValue);
		
		return configuration;
	}
	
	private Configuration createConfigurationForDefaultList() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(SnippetConstant.SELECTIONMODE, false);
		configuration.setPropertyByName(ReminderListSnippet.DEFAULT_LIST, "defaultList");
		setConfiguration(configuration);
		return configuration;
		
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
		reminderListPanel.clear();
		int eventType=event.getEventType();
		String eventData = (String) event.getEventData();
		 DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy hh:mm:ss");
		 Date date = fmt.parse(eventData);
		
		reminderListSnippet = new ReminderListSnippet();
		reminderListSnippet.setConfiguration(createConfigurationForDateList(date));
		reminderListSnippet.initialize();
		reminderListPanel.add(reminderListSnippet);
		
	}

	private Configuration createConfigurationForDateList(Date eventData) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(SnippetConstant.SELECTIONMODE, false);
		configuration.setPropertyByName(ReminderListSnippet.DATEBASED_LIST, "dateBasedList");
		configuration.setPropertyByName(ReminderListSnippet.DATEBASED_LIST_VALUE, eventData);
		
		
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
