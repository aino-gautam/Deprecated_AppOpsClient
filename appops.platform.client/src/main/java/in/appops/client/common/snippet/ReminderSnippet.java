package in.appops.client.common.snippet;

import in.appops.client.common.components.CreateCalendarEntryScreen;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.fields.LinkField;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.calendar.constant.ReminderConstant;
import in.appops.platform.server.core.services.calendar.constant.ReminderTypeConstant;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ReminderSnippet extends VerticalPanel implements Snippet , ClickHandler{
	
	private Image crossImg = new Image("images/crossImage.png");
	private final DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
	private final DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);
	private Entity embededEntity;
	private Entity entity;
	private String type;
	private Button createReminderButton;	
	private LinkField editLinkField;
	private CreateCalendarEntryScreen calendarEntryScreen;
	private PopupPanel createReminderPopupPanel;
	private LinkField reminderTitleLink;
	private LabelField dateTime;
	private LabelField reminderTypeLabel;
	public ReminderSnippet() {
	}
	
	
	public ReminderSnippet(Entity reminder) {
		this.entity = reminder;
	}
	
	@Override
	public void initialize(){
		
		Entity entity = getEntity();
		
		try {
			String title = entity.getProperty(ReminderConstant.TITLE).getValue().toString();
			
			reminderTitleLink = new LinkField();
			reminderTitleLink.setFieldValue(title);
			reminderTitleLink.setConfiguration(getLinkFieldConfiguration(LinkField.LINKFIELDTYPE_ANCHOR, "reminderTitleLabel", "crossImageCss", null));
			reminderTitleLink.create();
						
			//reminderTitleLink.getWidget().addDomHandler(this, ClickEvent.getType());
			
			
			
			/*reminderTitleLink = new Anchor(title);
			reminderTitleLink.addClickHandler(this);
			reminderTitleLink.setStylePrimaryName("appops-LinkField");
			reminderTitleLink.addStyleName("reminderTitleLabel");*/
			
								
			HorizontalPanel reminderTitlePanel = new HorizontalPanel();
			reminderTitlePanel.add(reminderTitleLink);
			reminderTitlePanel.add(crossImg);
			crossImg.setHeight("14px");
			reminderTitlePanel.setCellHorizontalAlignment(crossImg, HasHorizontalAlignment.ALIGN_RIGHT);
			reminderTitlePanel.setStylePrimaryName("reminderTitlePanel");
			crossImg.setStylePrimaryName("crossImageCss");
						
			add(reminderTitlePanel);
			
			LabelField dateTimeLbl = getLabelField("Remind me on:","postLabel");
			
			Date date = (Date) entity.getProperty(ReminderConstant.REMINDERTIME).getValue();
			
			String dateTimeValue = DateTimeFormat.getLongDateFormat().format(date) +" at "+ DateTimeFormat.getShortTimeFormat().format(date);
					
			dateTime = getLabelField(dateTimeValue,"postLabel");
			
			LabelField typeLabel = getLabelField("Remind me via:","postLabel");
					
			Entity reminderType = (Entity) entity.getProperty(ReminderConstant.REMINDERTYPE);
					
			reminderTypeLabel = getLabelField(reminderType.getProperty(ReminderTypeConstant.TYPE).getValue().toString(),"postLabel");
					
			add(typeLabel);
			
			FlexTable flex = new FlexTable();
			flex.setWidget(0, 0, dateTimeLbl);
			flex.setWidget(0, 1, dateTime);
			flex.setWidget(1, 0, typeLabel);
			flex.setWidget(1, 1, reminderTypeLabel);
			add(flex);
			
			editLinkField = new LinkField();
			
			editLinkField.setFieldValue("Edit");
			editLinkField.setConfiguration(getLinkFieldConfiguration(LinkField.LINKFIELDTYPE_ANCHOR, "postLink", "crossImageCss", null));
			editLinkField.create();
			
			HorizontalPanel linkPanel = new HorizontalPanel();
			//linkPanel.add(editLinkField);
			//snippetPanel.add(editLinkField);
			add(linkPanel);
			//addDomHandler(this,ClickEvent.getType());
			((Anchor) reminderTitleLink.getWidget()).addClickHandler(this);
			crossImg.addClickHandler(this);		
			setStylePrimaryName("snippetPanel");
		} catch (AppOpsException e) {
			 e.printStackTrace();
		}
	}
	
	private Configuration getLinkFieldConfiguration(String linkFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LinkField.LINKFIELD_TYPE, linkFieldType);
		configuration.setPropertyByName(LinkField.LINKFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LinkField.LINKFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LinkField.LINKFIELD_DEBUGID, debugId);
		return configuration;
	}
	
	private LabelField getLabelField(String lblText,String primaryCss) throws AppOpsException{
		LabelField label = new LabelField();
		label.setFieldValue(lblText);
		Configuration config = getLabelFieldConfiguration(true,primaryCss,null,null);
		label.setConfiguration(config);
		label.configure();
		label.create();
		
		return label;
		
	}
	

	public Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		return configuration;
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget widget = (Widget) event.getSource();
		
		if(widget instanceof Anchor){
			createReminderPopupPanel = new PopupPanel();
			VerticalPanel childVerticalPanel = new VerticalPanel();
			Entity entity=getEntity();
			calendarEntryScreen  = new CreateCalendarEntryScreen();
			calendarEntryScreen.setReminderEntity(entity);
			calendarEntryScreen.setConfiguration(getConfigurationForCreateReminder());
			calendarEntryScreen.setCreateEntityType("Create reminder");
			calendarEntryScreen.createScreen();
			createReminderButton = new Button("Create Reminder");
			createReminderButton.addClickHandler(this);
			childVerticalPanel.add(calendarEntryScreen);
			childVerticalPanel.add(createReminderButton);
			createReminderPopupPanel.add(childVerticalPanel);
			createReminderPopupPanel.showRelativeTo(reminderTitleLink);
			createReminderPopupPanel.setAutoHideEnabled(true);
		}else if(widget instanceof Button){
			
		  if(calendarEntryScreen.validate()){	
			Entity reminderEntity=calendarEntryScreen.populateEntity();
			Entity entity=getEntity();
			Property<Serializable>reminderIdProperty=(Property<Serializable>) entity.getProperty(ReminderConstant.ID);
			reminderEntity.setProperty(ReminderConstant.ID, reminderIdProperty);
			Date createdOn = entity.getPropertyByName(ReminderConstant.CREATEDON);
			 Property<Date> modifiedOnDateProp = new Property<Date>(createdOn);
			 reminderEntity.setProperty(ReminderConstant.CREATEDON, modifiedOnDateProp);
			 Long instanceId=entity.getPropertyByName(ReminderConstant.INSTANCEID);
			 Long typeId = entity.getPropertyByName(ReminderConstant.TYPEID);
			 Long serviceId =entity.getPropertyByName(ReminderConstant.SERVICEID);
			 Long userId =entity.getPropertyByName(ReminderConstant.USERID);
			 
			 reminderEntity.setPropertyByName(ReminderConstant.TYPEID,typeId);
			 reminderEntity.setPropertyByName(ReminderConstant.INSTANCEID,instanceId);
			 reminderEntity.setPropertyByName(ReminderConstant.USERID, userId);
			 
			 reminderEntity.setPropertyByName(ReminderConstant.SERVICEID, serviceId);
			saveEditedReminderEntity(reminderEntity);
		  }
		}else if(widget instanceof Image){
			 
			Entity entity=getEntity();
					
			deleteReminderEntity(entity);
			
		}
	}
	
	@SuppressWarnings("unchecked")
	private void deleteReminderEntity(Entity reminderEntity) {
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);
				
		Byte b = 1;
		Property<Byte> isDeletedProp = new Property<Byte>();
		isDeletedProp.setValue(b);
		isDeletedProp.setName(ReminderConstant.ISDELETED);
		reminderEntity.setProperty(ReminderConstant.ISDELETED, isDeletedProp);
		
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
									
					removeReminderSnippet();
					 
				}
				
			  }
			}

		
	  });
		
	}

	private void removeReminderSnippet() {
	     this.removeFromParent();
		
	}
	@SuppressWarnings("unchecked")
	private void saveEditedReminderEntity(Entity reminderEntity) {
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);
				
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
					 createReminderPopupPanel.hide();
					 updateSnippetWithNewValue(entity);
				     PopupPanel popupPanel = new  PopupPanel();
					 HorizontalPanel horizontalPanel = new HorizontalPanel();
					 Label label = new Label("Remindar edit successfully.");
					 horizontalPanel.add(label);
					 horizontalPanel.setCellHorizontalAlignment(label, HasHorizontalAlignment.ALIGN_CENTER);
					 popupPanel.add(horizontalPanel);
					 popupPanel.show();
					 popupPanel.center();
					 popupPanel.setAutoHideEnabled(true);
					 calendarEntryScreen.clearAllFields();
				}
				
			  }
			}

			
		
	  });
	}

	private void updateSnippetWithNewValue(Entity editedReminderEntity) {
		try {
			String title = editedReminderEntity.getProperty(ReminderConstant.TITLE).getValue().toString();
			reminderTitleLink.setFieldValue(title);
			reminderTitleLink.reset();
			
			Date date = (Date) editedReminderEntity.getProperty(ReminderConstant.REMINDERTIME).getValue();
			
			String dateTimeValue = DateTimeFormat.getLongDateFormat().format(date) +" at "+ DateTimeFormat.getShortTimeFormat().format(date);
			dateTime.setFieldValue(dateTimeValue);
			dateTime.reset();
			
			Entity reminderType = (Entity) entity.getProperty(ReminderConstant.REMINDERTYPE);
			
			
			reminderTypeLabel.setFieldValue(reminderType.getProperty(ReminderTypeConstant.TYPE).getValue().toString());
			reminderTypeLabel.reset();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private Configuration getConfigurationForCreateReminder() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(CreateCalendarEntryScreen.REMINDER_MODE, CreateCalendarEntryScreen.REMINDER_EDIT);
		configuration.setPropertyByName(CreateCalendarEntryScreen.SCREEN_TYPE, CreateCalendarEntryScreen.REMINDER);
		return configuration;
	}


	@SuppressWarnings("unchecked")
	private Entity getEmbededEntity(Entity reminderEntity){
			
		Map parameters = new HashMap();
		parameters.put("reminderEntity", reminderEntity);
		
		StandardAction action = new StandardAction(Entity.class, "calendar.CalendarService.getEmbededEntityForReminder", parameters);
		dispatch.execute(action, new AsyncCallback<Result>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Operation failed");
			}

			@Override
			public void onSuccess(Result result) {
				embededEntity = (Entity) result.getOperationResult();
				
			}
		});
		return embededEntity;
	}
	
	private String constructLink(Entity entity){
		String serviceId  = entity.getProperty(ReminderConstant.SERVICEID).getValue().toString();
		String typeId  = entity.getProperty(ReminderConstant.TYPEID).getValue().toString();
		String instanceId  = entity.getProperty(ReminderConstant.INSTANCEID).getValue().toString();
		String WidgetName  = entity.getProperty("widgetName").getValue().toString();
		
		String link = "serviceId="+serviceId+"typeId="+typeId+"instanceId="+instanceId+"WidgetName="+WidgetName;
		
		return link;
		
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
		// TODO Auto-generated method stub
		
	}


	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
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
	

}
