package in.appops.client.common.snippet;

import in.appops.client.common.components.CalendarEvents;
import in.appops.client.common.components.CalendarReminders;
import in.appops.client.common.components.CreateCalendarEntryScreen;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.CalendarAndReminderEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.fields.Field;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.constants.propertyconstants.SpaceConstants;
import in.appops.platform.core.constants.propertyconstants.UserConstants;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.calendar.constant.CalendarConstant;
import in.appops.platform.server.core.services.calendar.constant.ReminderConstant;
import in.appops.platform.server.core.services.calendar.constant.UserCalendarEventConstant;

import java.io.Serializable;
import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CalendarServiceHomeSnippet extends Composite implements Snippet ,Field,EventListener,ClickHandler{
	
	private ListSnippet listSnippet ;
	private Entity entity;
	private String type;
	private Configuration configuration;
	private VerticalPanel mainPanel ;
	private VerticalPanel childPanel ;
	private TabPanel tabPanel;	
	private LabelField quickEventLabelField;
	private LabelField reminderLabelField;
	private CreateCalendarEntryScreen calendarEntryScreen ;
	private ReminderListSnippet reminderListSnippet;
	private CalendarReminders calendarReminders;
	private Button createEntityButton ;
	private Entity userEntity;
	private CalendarEvents calendarEvents ;
	
	public CalendarServiceHomeSnippet() {
		//clear this snippet and move the code to reminder list snippet 
		//create ui as discussed for this snippet
		//set configuration with reminder/event creation
		mainPanel = new VerticalPanel();
		initWidget(mainPanel);
		mainPanel.setStylePrimaryName("calendarServiceHome");
	}
	
	@Override
	public void initialize(){
		
		tabPanel = new TabPanel();
		childPanel = new VerticalPanel();
		createEntityButton = new Button();
		
		createEntityButton.setStylePrimaryName("appops-Button");
		DOM.setStyleAttribute(childPanel.getElement(), "padding", "5px");
		DOM.setStyleAttribute(createEntityButton.getElement(), "padding", "5px");
		userEntity=AppEnviornment.getCurrentUser();
		childPanel.setHeight("100%");
		createEntityButton.addClickHandler(this);
		createUi();
		
	}
	
	
	
	public void createUi(){
		try{
			
			calendarEvents = new CalendarEvents();
			calendarEvents.addHandle(this);
			calendarEvents.createUi();
			
			//reminderListSnippet = new ReminderListSnippet();
			calendarReminders = new CalendarReminders();
			preSelectedTab();			
			mainPanel.add(createHeaderPanel());
			
			mainPanel.add(childPanel);
			mainPanel.setCellVerticalAlignment(childPanel, HasVerticalAlignment.ALIGN_TOP);
			mainPanel.setCellHorizontalAlignment(childPanel, HasHorizontalAlignment.ALIGN_CENTER);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	private Configuration getConfigurationForCreateEvent() {
		
			Configuration configuration = new Configuration();
			//configuration.setPropertyByName(CreateCalendarEntryScreen.REMINDER_MODE, CreateCalendarEntryScreen.REMINDER_NEW);
			configuration.setPropertyByName(CreateCalendarEntryScreen.SCREEN_TYPE, CreateCalendarEntryScreen.CREATE_EVENT);
			return configuration;
		
	}

	private void preSelectedTab() {
		childPanel.clear();
		childPanel.add(calendarEvents);
		
		if(getConfigurationForCreateEvent().getPropertyByName(CreateCalendarEntryScreen.SCREEN_TYPE).equals(CreateCalendarEntryScreen.CREATE_EVENT)){
			createEntityButton.setText("Create event");
		}else{
			createEntityButton.setText("Create reminder");
		}
		
		
		
	}

	private HorizontalPanel createHeaderPanel() {
		HorizontalPanel headerPanel = new HorizontalPanel();
		
		try{
			quickEventLabelField = new LabelField();
			Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", "calendarHomeTab", null);
			if(getConfigurationForCreateEvent().getPropertyByName(CreateCalendarEntryScreen.SCREEN_TYPE).equals(CreateCalendarEntryScreen.CREATE_EVENT)){
				quickEventLabelField.setFieldValue("Events");
			}else{
				quickEventLabelField.setFieldValue("Create reminder");
			}
			quickEventLabelField.setConfiguration(labelConfig);
			quickEventLabelField.createField();
			quickEventLabelField.addClickHandler(this);
			
			quickEventLabelField.addStyleName("calendarHomeTabSelected");
			
			reminderLabelField = new LabelField();
			Configuration reminderLabelConfig = getLabelFieldConfiguration(true, "flowPanelContent", "calendarHomeTab", null);
			reminderLabelField.setFieldValue("Reminders");
			reminderLabelField.setConfiguration(reminderLabelConfig);
			reminderLabelField.createField();
			reminderLabelField.addClickHandler(this);
			
			LabelField seperatorLabelField = new LabelField();
			Configuration seperatorLabelFieldConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			seperatorLabelField.setFieldValue("|");
			seperatorLabelField.setConfiguration(seperatorLabelFieldConfig);
			seperatorLabelField.createField();
					
			HorizontalPanel sepratorPanel = new HorizontalPanel();
			
			sepratorPanel.add(seperatorLabelField);
			
			
			headerPanel.add(quickEventLabelField);
			headerPanel.add(sepratorPanel);
			headerPanel.add(reminderLabelField);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return headerPanel;
			
	}

	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
		
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
		
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
		
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public ActionContext getActionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActionContext(ActionContext actionContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		String eventData ;
		Entity snippetEntity;
		if(event.getEventData() instanceof String){
			eventData = (String) event.getEventData();
			
			if(eventData.equals("Create a event")){
				quickEventLabelField.clearField();
				quickEventLabelField.setFieldValue(eventData);
				quickEventLabelField.resetField();
			}else if(eventData.equals("Back")){
				
				quickEventLabelField.clearField();
				quickEventLabelField.setFieldValue("Events");
				quickEventLabelField.resetField();
				
				calendarEvents = new CalendarEvents();
				calendarEvents.addHandle(this);
				calendarEvents.createUi();
				childPanel.clear();
				childPanel.add(calendarEvents);
			}else{
				quickEventLabelField.clearField();
				quickEventLabelField.setFieldValue(eventData);
				quickEventLabelField.resetField();
				
				calendarEntryScreen = new CreateCalendarEntryScreen();
				calendarEntryScreen.setConfiguration(getConfigurationForCreateEvent());
				calendarEntryScreen.createScreen();
				calendarEntryScreen.addHandle(this);
				
				childPanel.clear();
				childPanel.add(calendarEntryScreen);
				childPanel.add(createEntityButton);
			}
		}
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

	public Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId) {
		Configuration config = new Configuration();
		config.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		config.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return config;
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if(sender instanceof LabelField){
			if(sender.equals(quickEventLabelField)){
				calendarEvents.createUi();
				//calendarEntryScreen.clearAllFields();
				//DOM.removeElementAttribute(reminderLabelField.getElement(), "textDecoration");
				
				//DOM.setStyleAttribute(quickEventLabelField.getElement(), "textDecoration","underline");
				reminderLabelField.removeStyleName("calendarHomeTabSelected");
				quickEventLabelField.setStylePrimaryName("flowPanelContent");
				quickEventLabelField.addStyleName("calendarHomeTabSelected");
				preSelectedTab();
			}else if(sender.equals(reminderLabelField)){
				childPanel.clear();
				//DOM.removeElementAttribute(quickEventLabelField.getElement(), "textDecoration");
				//DOM.setStyleAttribute(reminderLabelField.getElement(), "textDecoration","underline");
				quickEventLabelField.removeStyleName("calendarHomeTabSelected");
				reminderLabelField.setStylePrimaryName("flowPanelContent");
				reminderLabelField.addStyleName("calendarHomeTabSelected");
				calendarReminders.createUi();
				
				//reminderListSnippet.initialize();
				//childPanel.add(reminderListSnippet);
				childPanel.add(calendarReminders);
			}
		}else if(sender instanceof Button){
			
			if(createEntityButton.getText().equals("Create reminder")){
				calendarEntryScreen.setCreateEntityType(createEntityButton.getText());
				if(calendarEntryScreen.validate()){
					//populate entity for saving
					Entity entity=calendarEntryScreen.populateEntity();
				}
			}else if(createEntityButton.getText().equals("Create event")){
				
				checkCalenderForLoggedInUSer();
				
				
			}else if(createEntityButton.getText().equals("Quick event")){
				
			}
			
		}
	}

	@SuppressWarnings("unchecked")
	private void checkCalenderForLoggedInUSer() {
		
		Key<Serializable> key=(Key<Serializable>) userEntity.getProperty(UserConstants.ID).getValue();
		Long userId = (Long) key.getKeyValue();
		
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		Query query = new Query();
		query.setQueryName("getUserCalendar");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("owner", userId);
		query.setQueryParameterMap(map);
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("query", query);
		
		
		
		StandardAction action = new StandardAction(EntityList.class, "calendar.CalendarService.getEntityList", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
			
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
			
			public void onSuccess(Result<EntityList> result) {
				if(result!=null){
					EntityList calenderEntityList=result.getOperationResult();
					if(calenderEntityList.size()>0){
					for(Entity calenderEntity:calenderEntityList){
					   if(calenderEntity!=null)	{
							calendarEntryScreen.setCreateEntityType(createEntityButton.getText());
							if(calendarEntryScreen.validate()){
								//populate entity for saving
								Entity eventEntity=calendarEntryScreen.populateEntity();
								createEventSavingIntoDb(eventEntity,calenderEntity);
							}
					   }else{
						   calendarEntryScreen.setCreateEntityType(createEntityButton.getText());
							if(calendarEntryScreen.validate()){
								//populate entity for saving
								Entity eventEntity=calendarEntryScreen.populateEntity();
								Entity calendarEntity=createCalendarEntity();
								createEventSavingIntoDb(eventEntity,calendarEntity);
							}
					   }
					}
					}else{
						calendarEntryScreen.setCreateEntityType(createEntityButton.getText());
						if(calendarEntryScreen.validate()){
							//populate entity for saving
							Entity eventEntity=calendarEntryScreen.populateEntity();
							Entity calendarEntity=createCalendarEntity();
							createEventSavingIntoDb(eventEntity,calendarEntity);
						}
					}
				}else{
					
				}
			 }

			});
		
	}
	
	private Entity createCalendarEntity() {
		Entity calendarEnt = new  Entity();
		calendarEnt.setType(new MetaType(TypeConstants.CALENDAR));
		
		//TODO:currently time zone and country fields are hard code  
		
		Key<Serializable> key=(Key<Serializable>) userEntity.getProperty(UserConstants.ID).getValue();
		Long userId = (Long) key.getKeyValue();
		
		
		/*TimeZone timeZone=TimeZone.getDefault();*/
		 
		String userFirstName = userEntity.getPropertyByName("firstname");
		String userLastName = userEntity.getPropertyByName("lastname");
		
		/*String userFirstName ="Kiran";
		String userLastName ="Bhalerao";*/
		
		/*TimeZone tz = TimeZone.getTimeZone(timeZone.getID());
		System.out.println("Country Id :"+tz.getID() + "   timeZone :" + tz.getDisplayName());*/
		
		Property<String> nameProp = new Property<String>();
		nameProp.setName(CalendarConstant.NAME);
		nameProp.setValue(userFirstName+" "+userLastName);
		calendarEnt.setProperty(nameProp);
				
		
		Property<String> countryProp = new Property<String>();
		countryProp.setName(CalendarConstant.COUNTRY);
		//countryProp.setValue(Locale.getDefault().getDisplayCountry());
		countryProp.setValue("India");
		calendarEnt.setProperty(countryProp);
		
		//Date date = new Date();
		//String timeZone=DateTimeFormat.getFormat("ZZZZ").format(date);
		
		Property<String> timezoneProp = new Property<String>();
		timezoneProp.setName(CalendarConstant.TIMEZONE);
		//timezoneProp.setValue(tz.getDisplayName());
		timezoneProp.setValue("India Standard Time");
		calendarEnt.setProperty(timezoneProp);
		
		Property<String> ownerProp = new Property<String>();
		ownerProp.setName(CalendarConstant.OWNER);
		ownerProp.setValue(String.valueOf(userId));
		calendarEnt.setProperty(ownerProp);
		
		Entity spaceEntity=AppEnviornment.getCurrentSpace();
		
		Key<Serializable> key1 = (Key<Serializable>) spaceEntity.getProperty(SpaceConstants.ID).getValue();
		Long spaceID= (Long) key1.getKeyValue();
		
		Property<String> spaceIdProp = new Property<String>();
		spaceIdProp.setName(CalendarConstant.SPACEID);
		spaceIdProp.setValue(String.valueOf(spaceID));
		calendarEnt.setProperty(spaceIdProp);
		
		
		
		return calendarEnt;
	}
	@SuppressWarnings("unchecked")
	private void createEventSavingIntoDb(Entity eventEntity, Entity calenderEntity) {
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("event", eventEntity);
		paramMap.put("calendar", calenderEntity);
		
		
		
		StandardAction action = new StandardAction(EntityList.class, "calendar.CalendarService.addEventToCalendar", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<Entity>>() {
			
			public void onFailure(Throwable caught) {
				Window.alert("operation failed ");
				caught.printStackTrace();
			}
			
			public void onSuccess(Result<Entity> result) {
				if(result!=null){
					Entity userCalendarEventEntity=result.getOperationResult();
				   if(userCalendarEventEntity!=null)	{
					 Entity calendarEntity=  userCalendarEventEntity.getPropertyByName(UserCalendarEventConstant.EVENT);
					    
					   if(!calendarEntryScreen.isAddMoreDetails()){
					     PopupPanel popupPanel = new  PopupPanel();
						 HorizontalPanel horizontalPanel = new HorizontalPanel();
						 Label label = new Label("Event created successfully.");
						 horizontalPanel.add(label);
						 horizontalPanel.setCellHorizontalAlignment(label, HasHorizontalAlignment.ALIGN_CENTER);
						 popupPanel.add(horizontalPanel);
						 popupPanel.show();
						 popupPanel.center();
						 popupPanel.setAutoHideEnabled(true);
						 calendarEntryScreen.clearAllFields();
						 CalendarAndReminderEvent calendarEvent = new CalendarAndReminderEvent();
						 calendarEvent.setEventData("");
						 calendarEvent.setEventType(CalendarAndReminderEvent.COMPLETION);
						 popupPanel.hide();
					     AppUtils.EVENT_BUS.fireEvent(calendarEvent);
					   }else{
						   addReminderForEvent(calendarEntity);
					   }
				   }else{
					   
				   }
				}else{
					
				}
			 }

			

			});
		
	}
	
	@SuppressWarnings("unchecked")
	private void addReminderForEvent(Entity calendarEntity) {
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		Entity reminderEntity=calendarEntryScreen.getReminderEntity();
		Key<Serializable> key =(Key<Serializable>) calendarEntity.getProperty(CalendarConstant.ID).getValue();
		Long calendarId = (Long) key.getKeyValue();
		reminderEntity.setPropertyByName(ReminderConstant.INSTANCEID, calendarId);
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("reminder", reminderEntity);
				
		
		StandardAction action = new StandardAction(EntityList.class, "calendar.CalendarService.addReminderToEvent", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				
			}

			@Override
			public void onSuccess(Result<Entity> result) {
			  if(result!=null){	
				Entity entity = result.getOperationResult();
				if(entity!=null){
				     PopupPanel popupPanel = new  PopupPanel();
					 HorizontalPanel horizontalPanel = new HorizontalPanel();
					 Label label = new Label("Event created with remindar successfully.");
					 horizontalPanel.add(label);
					 horizontalPanel.setCellHorizontalAlignment(label, HasHorizontalAlignment.ALIGN_CENTER);
					 popupPanel.add(horizontalPanel);
					 popupPanel.show();
					 popupPanel.center();
					 popupPanel.setAutoHideEnabled(true);
					 calendarEntryScreen.clearAllFields();
					 CalendarAndReminderEvent calendarEvent = new CalendarAndReminderEvent();
					 calendarEvent.setEventData("");
					 calendarEvent.setEventType(CalendarAndReminderEvent.COMPLETION);
					 popupPanel.hide();
				     AppUtils.EVENT_BUS.fireEvent(calendarEvent);
				}
				
			  }
			}
		
	  });
	}
}
