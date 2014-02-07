package in.appops.client.common.components;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.textfield.TextField;
import in.appops.client.common.config.field.textfield.TextField.TextFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.ComboBoxField;
import in.appops.client.common.fields.DateOnlyPicker;
import in.appops.client.common.fields.DateTimeField;
import in.appops.client.common.fields.DateTimePicker;
import in.appops.client.common.fields.LinkField;
import in.appops.client.common.fields.TimePicker;
import in.appops.client.common.util.AppEnviornment;
import in.appops.client.touch.Screen;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.constants.propertyconstants.UserConstants;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.GeoLocation;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.InitiateActionContext;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.calendar.constant.EventConstant;
import in.appops.platform.server.core.services.calendar.constant.ReminderConstant;
import in.appops.platform.server.core.services.calendar.constant.ReminderTypeConstant;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CreateCalendarEntryScreen extends Composite implements Screen,ClickHandler{
	
	private VerticalPanel mainPanel ;
	//private Button createEventButton = new Button("Create event");
	private InitiateActionContext actionContext;
	private Configuration configuration;
	public static String CREATE_EVENT = "createEvent";
	public static String REMINDER = "reminder";
	public static String SCREEN_TYPE = "screenType";
	public static String REMINDER_EDIT = "reminderEdit";
	public static String REMINDER_NEW = "reminderNew";
	public static String REMINDER_MODE = "reminderMode";
	private Entity reminderEntity;
	private Entity entity;
	private TextField textFieldTB;
	private TextField descriptionTextField;
	private DateTimeField dateTimeOnlyField ;
	private ComboBoxField reminderTypeComboBoxField;
	private ComboBoxField reminderUnitComboBoxField;
	private LinkField backToEventsLinkField;
	private HorizontalPanel errorHorizontalPanel ;
	private HorizontalPanel reminderFieldPanel;
	private String createEntityType;
	private boolean isAddMoreDetails = false;
	private TextField remindarDurationTextFieldTB;
	private static final long MILLISECONDS_IN_SECOND = 1000l;
	private static final long SECONDS_IN_MINUTE = 60l;
	private static final long MINUTES_IN_HOUR = 60l;
	private static final long HOURS_IN_DAY = 24l;
	private static final long MILLISECONDS_IN_DAY = MILLISECONDS_IN_SECOND *
	        SECONDS_IN_MINUTE *
	        MINUTES_IN_HOUR *
	        HOURS_IN_DAY;
	
	private static final long MILLISECONDS_IN_Hours = MILLISECONDS_IN_SECOND *
	        SECONDS_IN_MINUTE *
	        MINUTES_IN_HOUR ;
	private static final long MILLISECONDS_IN_Minutes = MILLISECONDS_IN_SECOND *
	        SECONDS_IN_MINUTE  ;
	
	public CreateCalendarEntryScreen() {
		mainPanel = new VerticalPanel();
		reminderFieldPanel = new HorizontalPanel();
		errorHorizontalPanel= new HorizontalPanel();
		initWidget(mainPanel);
		reminderFieldPanel.setWidth("100%");
	    fetchReminderTypes();
		
	}
	
	

	@SuppressWarnings("unchecked")
	private void fetchReminderTypes() {
		try{
			LabelField labelField = new LabelField();
			Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			labelField.setFieldValue("Loading fields");
			labelField.setConfiguration(labelConfig);
			labelField.create();
		    reminderFieldPanel.add(labelField);
		
			reminderTypeComboBoxField = new ComboBoxField();
			DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
			DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);
	
			Query query = new Query();
			query.setQueryName("getReminderTypes");
			
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("query", query);
			
			
			
			StandardAction action = new StandardAction(EntityList.class, "calendar.CalendarService.getEntityList", paramMap);
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
				
				public void onFailure(Throwable caught) {
					Window.alert("operation failed ");
					caught.printStackTrace();
				}
				
				public void onSuccess(Result<EntityList> result) {
					if(result!=null){
						try{
							EntityList reminderTypeEntityList=result.getOperationResult();
							reminderTypeComboBoxField.setEntityList(reminderTypeEntityList);
							//reminderTypeComboBoxField.setFieldValue(ReminderTypeConstant.TYPE);
							reminderTypeComboBoxField.setConfiguration(createComboboxConfig(null,ReminderTypeConstant.TYPE,"appops-comboBoxField",null,reminderTypeEntityList));
							reminderTypeComboBoxField.create();
							fetchReminderUnits();
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
	
				
			});
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private Configuration createComboboxConfig(String entityType,
			String entityPropertyName, String primaryCss, String secondaryCss, EntityList entityList) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(ComboBoxField.ComboBoxField_EntityType, entityType);
		configuration.setPropertyByName(ComboBoxField.ComboBoxField_EntityPropertyName, entityPropertyName);
		configuration.setPropertyByName(ComboBoxField.STATEFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(ComboBoxField.STATEFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(ComboBoxField.ComboBoxField_EntityList, entityList);
		return configuration;
	}

	@SuppressWarnings("unchecked")
	private void fetchReminderUnits() {
		reminderUnitComboBoxField = new ComboBoxField();
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		Query query = new Query();
		query.setQueryName("getReminderUnits");
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("query", query);
		
		
		
		StandardAction action = new StandardAction(EntityList.class, "calendar.CalendarService.getEntityList", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
			
			public void onFailure(Throwable caught) {
				Window.alert("operation failed ");
				caught.printStackTrace();
			}
			
			public void onSuccess(Result<EntityList> result) {
				if(result!=null){
					try{
						reminderFieldPanel.clear();
						EntityList reminderUnitEntityList=result.getOperationResult();
						reminderUnitComboBoxField.setEntityList(reminderUnitEntityList);
						//reminderUnitComboBoxField.setFieldValue("unit");
						reminderUnitComboBoxField.setConfiguration(createComboboxConfig(null,"unit","appops-comboBoxField",null,reminderUnitEntityList));
						reminderUnitComboBoxField.create();
						reminderFieldPanel.add(createRemiderField());
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		});
		
	}



	@Override
	public void createScreen() {
		try{
		if(getConfiguration()!=null){	
		 if(getConfiguration().getPropertyByName(SCREEN_TYPE).equals(CREATE_EVENT)){	
			 
			 createEventDataForm();
			
		 }else if(getConfiguration().getPropertyByName(SCREEN_TYPE).equals(REMINDER)){
			 if(getConfiguration().getPropertyByName(REMINDER_MODE).equals(REMINDER_EDIT)){
				// fetchReminderEntity();
				 createReminderForm();
				 
			 }else if(getConfiguration().getPropertyByName(REMINDER_MODE).equals(REMINDER_NEW)){
				 createReminderForm();
			 }
			  
		 }
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}

	private void fetchReminderEntity() {
		try{
			
			createReminderForm();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}



	public void createReminderForm() {
        FlexTable flexTable = new FlexTable();
		try{
			LabelField labelField = new LabelField();
			Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			labelField.setFieldValue("What :");
			labelField.setConfiguration(labelConfig);
			labelField.create();
			
			textFieldTB = new TextField();
			textFieldTB.setFieldValue("");
			textFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, "appops-TextField", null, null));
			textFieldTB.create();
			
			DOM.setStyleAttribute(textFieldTB.getElement(), "width", "215px");
			
			LabelField labelWhenField = new LabelField();
			Configuration labelWhenConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			labelWhenField.setFieldValue("When :");
			labelWhenField.setConfiguration(labelWhenConfig);
			labelWhenField.create();
			
			dateTimeOnlyField = new DateTimeField();
			dateTimeOnlyField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_SELECTION,DateTimeField.DATETIMEFIELD_DATETIMEONLY,null));
			dateTimeOnlyField.create();
			
			LabelField reminderLabelField = new LabelField();
			Configuration reminderLabelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			reminderLabelField.setFieldValue("Reminder :");
			reminderLabelField.setConfiguration(reminderLabelConfig);
			reminderLabelField.create();
			
			
			if(reminderEntity!=null){
				
				String reminderTitle = reminderEntity.getPropertyByName(ReminderConstant.TITLE);
				textFieldTB.setFieldValue(reminderTitle);
				textFieldTB.reset();
				Date reminderTime=reminderEntity.getPropertyByName(ReminderConstant.REMINDERTIME);
				DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy HH:mm:ss");
				String date = fmt.format(reminderTime);
				DateTimePicker dateTimePicker = (DateTimePicker) dateTimeOnlyField.getCurrentField();
				dateTimePicker.getTextbox().setText(date);
				
				/*Entity reminderType = (Entity) reminderEntity.getProperty(ReminderConstant.REMINDERTYPE);
				String typeName=reminderType.getProperty(ReminderTypeConstant.TYPE).getValue().toString();
				reminderTypeComboBoxField.setFieldValue(typeName);
				reminderTypeComboBoxField.resetField();*/
				//reminderUnitComboBoxField.setFieldValue(fieldValue);
			}
			
			
			flexTable.setWidget(1, 0, labelField);
			flexTable.setWidget(1, 2, textFieldTB);
			
			flexTable.setWidget(3, 0, labelWhenField);
			flexTable.setWidget(3, 2, dateTimeOnlyField);
			
			flexTable.setWidget(5, 0, reminderLabelField);
			flexTable.setWidget(5, 2, reminderFieldPanel);
			//flexTable.setWidget(7, 0, createEventButton);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		mainPanel.add(flexTable);
		mainPanel.setCellHorizontalAlignment(flexTable, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellVerticalAlignment(flexTable, HasVerticalAlignment.ALIGN_MIDDLE);
	}

	private void createEventDataForm() {
		final FlexTable flexTable = new FlexTable();
		try{
			LabelField labelField = new LabelField();
			Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			labelField.setFieldValue("What :");
			labelField.setConfiguration(labelConfig);
			labelField.create();
			
			textFieldTB = new TextField();
			textFieldTB.setFieldValue("");
			textFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, "appops-TextField", null, null));
			textFieldTB.create();
			
			DOM.setStyleAttribute(textFieldTB.getElement(), "width", "215px");
			
			LabelField labelWhenField = new LabelField();
			Configuration labelWhenConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			labelWhenField.setFieldValue("When :");
			labelWhenField.setConfiguration(labelWhenConfig);
			labelWhenField.create();
			
			dateTimeOnlyField = new DateTimeField();
			dateTimeOnlyField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_SELECTION,DateTimeField.DATETIMEFIELD_DATETIMEONLY,null));
			dateTimeOnlyField.create();
			
			LabelField labelDescriptionField = new LabelField();
			Configuration labelDescriptionConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			labelDescriptionField.setFieldValue("Description :");
			labelDescriptionField.setConfiguration(labelDescriptionConfig);
			labelDescriptionField.create();
			
			descriptionTextField = new TextField();
			descriptionTextField.setFieldValue("");
			descriptionTextField.setConfiguration(getTextFieldConfiguration(10, false, TextFieldConstant.TFTTYPE_TXTAREA, "appops-TextField", null, null));
			descriptionTextField.create();
			DOM.setStyleAttribute(descriptionTextField.getElement(), "width", "215px");
			DOM.setStyleAttribute(descriptionTextField.getElement(), "height", "65px");
			
			final Anchor addMoreDetailsAnchor = new Anchor("Add more details");
			addMoreDetailsAnchor.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					try{
						isAddMoreDetails = true;
						LabelField attachmentLabelField = new LabelField();
						Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
						attachmentLabelField.setFieldValue("Attachment :");
						attachmentLabelField.setConfiguration(labelConfig);
						attachmentLabelField.create();
						
						
						//Anchor attachementAnchor = new Anchor("Add attachment");
						WebMediaAttachWidget mediaWidget = createMediaField(); 
						
						LabelField reminderLabelField = new LabelField();
						Configuration reminderLabelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
						reminderLabelField.setFieldValue("Reminder :");
						reminderLabelField.setConfiguration(reminderLabelConfig);
						reminderLabelField.create();
						
						//HorizontalPanel reminderFieldPanel=createRemiderField();
						
						flexTable.remove(addMoreDetailsAnchor);
						
						flexTable.setWidget(7, 0, attachmentLabelField);
						//flexTable.getCellFormatter().setHeight(5, 0, "100px");
						flexTable.setWidget(7, 2, mediaWidget);
						//flexTable.getCellFormatter().setHeight(5, 2, "100px");
						flexTable.setWidget(10, 0, reminderLabelField);
						flexTable.setWidget(10, 2, reminderFieldPanel);
							
					}catch(Exception e){
						e.printStackTrace();
					}
					FieldEvent fieldEvent = new FieldEvent();
					fieldEvent.setEventType(FieldEvent.EDITINPROGRESS);
					fieldEvent.setEventData("Create a event");	
					AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, CreateCalendarEntryScreen.this);
				}

				
			});
			
			
			backToEventsLinkField = new LinkField();
			backToEventsLinkField.setFieldValue("Back");
			backToEventsLinkField.setConfiguration(getLinkFieldConfiguration(LinkField.LINKFIELDTYPE_ANCHOR, "crossImageCss", null, null));
			backToEventsLinkField.create();
			
			((Anchor) backToEventsLinkField.getWidget()).addClickHandler(this);
			
			flexTable.setWidget(1, 0, labelField);
			flexTable.setWidget(1, 2, textFieldTB);
			
			flexTable.setWidget(3, 0, labelWhenField);
			flexTable.setWidget(3, 2, dateTimeOnlyField);
			
			flexTable.setWidget(5, 0, labelDescriptionField);
			flexTable.setWidget(5, 2, descriptionTextField);
			flexTable.getCellFormatter().setVerticalAlignment(5, 0, HasVerticalAlignment.ALIGN_TOP);
			
			flexTable.setWidget(7, 2, addMoreDetailsAnchor);
			flexTable.setWidget(12, 2, backToEventsLinkField);
			
			mainPanel.add(flexTable);
			mainPanel.setCellHorizontalAlignment(flexTable, HasHorizontalAlignment.ALIGN_CENTER);
			mainPanel.setCellVerticalAlignment(flexTable, HasVerticalAlignment.ALIGN_MIDDLE);
			
		}catch(Exception e){
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
	
	private HorizontalPanel createRemiderField() {
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		try{
			horizontalPanel.setWidth("100%");
			horizontalPanel.add(reminderTypeComboBoxField);
			horizontalPanel.setCellWidth(reminderTypeComboBoxField, "10%");
			
			if(getConfiguration().getPropertyByName(SCREEN_TYPE).equals(CREATE_EVENT)){
				    remindarDurationTextFieldTB = new TextField();
					remindarDurationTextFieldTB.setFieldValue("");
					//textFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextField.TEXTFIELDTYPE_TEXTBOX, "appops-TextField", null, null));
					remindarDurationTextFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, "reminderDurationBox", null, null));
					remindarDurationTextFieldTB.create();
					
					horizontalPanel.add(remindarDurationTextFieldTB);
					horizontalPanel.setCellWidth(remindarDurationTextFieldTB, "10%");
			 }else if(getConfiguration().getPropertyByName(SCREEN_TYPE).equals(REMINDER)){
				 if(getConfiguration().getPropertyByName(REMINDER_MODE).equals(REMINDER_NEW)){
					   remindarDurationTextFieldTB = new TextField();
						remindarDurationTextFieldTB.setFieldValue("");
						//textFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextField.TEXTFIELDTYPE_TEXTBOX, "appops-TextField", null, null));
						remindarDurationTextFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, "reminderDurationBox", null, null));
						remindarDurationTextFieldTB.create();
						
						
						horizontalPanel.add(remindarDurationTextFieldTB);
						horizontalPanel.setCellWidth(remindarDurationTextFieldTB, "10%");
				 }
			 }
			
			horizontalPanel.add(reminderUnitComboBoxField);
			horizontalPanel.setCellWidth(reminderUnitComboBoxField, "40%");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return horizontalPanel;
	}
	
	private WebMediaAttachWidget createMediaField() {
		final WebMediaAttachWidget mediaWidget = new WebMediaAttachWidget();
		mediaWidget.isFadeUpEffect(true);
		mediaWidget.createUi();
		mediaWidget.setVisible(true);
		mediaWidget.setWidth("100%");
		mediaWidget.createAttachmentUi();
		if(actionContext != null && actionContext.getUploadedMedia() != null && !((InitiateActionContext)actionContext).getUploadedMedia().isEmpty()){
			mediaWidget.setMediaAttachments(actionContext.getUploadedMedia());
		} else{
			mediaWidget.collapse();
		}
		mediaWidget.getMedia().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(mediaWidget.isExpand()){
					mediaWidget.collapse();
				} else if(mediaWidget.isCollapse()){
					mediaWidget.expand();
				}
			}
		});
		return mediaWidget;
	}
	
	
	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
		
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
		
	}

	@Override
	public boolean validate() {
		errorHorizontalPanel.clear();
		
		try{
			if(createEntityType.equals("Create reminder")){
				
				if(textFieldTB.getFieldText().equals("")){
					
					throw new NullPointerException(); 
				}
				DateTimePicker dateTimePicker = (DateTimePicker) dateTimeOnlyField.getCurrentField();
				if(dateTimePicker.getTextbox().getText().equals("")){
								
					throw new NullPointerException(); 
				}
				
			}else if(createEntityType.equals("Create event")){
				
				if(textFieldTB.getFieldText().equals("")){
									
					throw new NullPointerException(); 
				}
				DateTimePicker dateTimePicker = (DateTimePicker) dateTimeOnlyField.getCurrentField();
				if(dateTimePicker.getTextbox().getText().equals("")){
								
					throw new NullPointerException(); 
				}
			  if(isAddMoreDetails){
				  if(remindarDurationTextFieldTB.getFieldText().equals("")){
				      throw new NullPointerException(); 
				  }
				if(reminderTypeComboBoxField.getFieldValue().equals("--type--")){
					throw new NullPointerException(); 
				}
				
				if(reminderUnitComboBoxField.getFieldValue().equals("--unit--")){
					throw new NullPointerException(); 
				}
			  }
				
			}
		}catch(Exception e){
			try{
				
				LabelField errorLabelField = new LabelField();
				Configuration labelConfig = getLabelFieldConfiguration(true, "appops-errorLabel", null, null);
				errorLabelField.setFieldValue("*Please enter all data appropriately.");
				errorLabelField.setConfiguration(labelConfig);
				errorLabelField.create();
				errorHorizontalPanel.add(errorLabelField);
				mainPanel.add(errorHorizontalPanel);
			}catch(Exception e1){
				e1.printStackTrace();
			}
			
			return false;
		}
		return true;
	}

	@Override
	public Entity populateEntity() {
		Entity entity = null;
		try{
			if(createEntityType.equals("Create reminder")){
				
				 entity = new  Entity();
				 entity.setType(new MetaType(TypeConstants.REMINDER));
				 
				    String eventTitle = textFieldTB.getFieldText();
				    Property<String> descProp = new Property<String>();
					descProp.setName(ReminderConstant.TITLE);
					descProp.setValue(eventTitle);
					entity.setProperty(descProp);
					
					 DateTimePicker dateOnlyPicker = (DateTimePicker) dateTimeOnlyField.getCurrentField();
					 String eventDateTime = dateOnlyPicker.getTextbox().getText();
									
					 DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy hh:mm:ss");
					 Date date = fmt.parse(eventDateTime);
					
					 Property<Date> reminderTimeProp = new Property<Date>(date);
					 entity.setProperty(ReminderConstant.REMINDERTIME, reminderTimeProp);
					 
					 String reminderType=reminderTypeComboBoxField.getFieldValue();
					 Entity reminderTypeEntity=(Entity) reminderTypeComboBoxField.getNameVsEntity().get(reminderType);
					
					 entity.setProperty(ReminderConstant.REMINDERTYPE, reminderTypeEntity);
					 
					 if(getConfiguration().getPropertyByName(REMINDER_MODE).equals(REMINDER_NEW)){
						 Property<Date> createdOnDateProp = new Property<Date>(new Date());
						 entity.setProperty(ReminderConstant.CREATEDON, createdOnDateProp);
							
							Property<Date> modifiedOnDateProp = new Property<Date>(new Date());
							entity.setProperty(ReminderConstant.MODIFIEDON, modifiedOnDateProp);
					 }else if(getConfiguration().getPropertyByName(REMINDER_MODE).equals(REMINDER_EDIT)){
						 Property<Date> modifiedOnDateProp = new Property<Date>(new Date());
						 entity.setProperty(ReminderConstant.MODIFIEDON, modifiedOnDateProp);
					 }
					 
					
					 
					/* String reminderUnit=reminderUnitComboBoxField.getFieldValue();
					 Entity reminderUnitEntity=(Entity) reminderTypeComboBoxField.getNameVsEntity().get(reminderUnit);
					
					 entity.setProperty("unit", reminderUnitEntity);*/
					 
					
				
			}else if(createEntityType.equals("Create event")){
			  if(isAddMoreDetails){	
				  /*entity = new Entity(); 
					String eventTitle = textFieldTB.getText();
					String eventDescription = descriptionTextField.getText();
					DateOnlyPicker dateOnlyPicker = (DateOnlyPicker) dateTimeOnlyField.getCurrentField();
					String eventDateTime = dateOnlyPicker.getTextbox().getText();*/
					
					entity =  createEventEntity();
					reminderEntity=createReminderEntity(entity);
										
			  }else{
				   
				   entity =  createEventEntity();
					
					 
					/* String[] zoneIds = TimeZone.getAvailableIDs();
					 for (int i = 0; i < zoneIds.length; i++) {
					 TimeZone tz = TimeZone.getTimeZone(zoneIds[i]);
					 System.out.println("Country Id :"+tz.getID() + "   timeZone :" + tz.getDisplayName());
					 }*/
			  }
			}
			
			return entity;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	private Entity createReminderEntity(Entity eventEntity) {
		 Entity reminderEnt = new  Entity();
		 reminderEnt.setType(new MetaType(TypeConstants.REMINDER));
		 
		    String eventTitle = eventEntity.getPropertyByName(EventConstant.NAME);
		    Property<String> descProp = new Property<String>();
			descProp.setName(ReminderConstant.TITLE);
			descProp.setValue(eventTitle);
			reminderEnt.setProperty(descProp);
			
			Entity userEntity=AppEnviornment.getCurrentUser();
			  Key<Serializable> userKey=(Key<Serializable>) userEntity.getProperty(UserConstants.ID).getValue();
			  Long userId = (Long) userKey.getKeyValue();
			
			reminderEnt.setPropertyByName(ReminderConstant.USERID,userId);
			
			
			
			reminderEnt.setPropertyByName(ReminderConstant.TYPEID,Long.valueOf(52));
			
			/*Property<String> instanceIdProp = new Property<String>();
			instanceIdProp.setName(ReminderConstant.INSTANCEID);
			instanceIdProp.setValue(String.valueOf("2"));
			reminderEnt.setProperty(instanceIdProp);*/
			Long serviceId=Long.valueOf("12");
						
			reminderEnt.setPropertyByName(ReminderConstant.SERVICEID, serviceId);
			
			Property<Date> createdOnDateProp = new Property<Date>(new Date());
			reminderEnt.setProperty(ReminderConstant.CREATEDON, createdOnDateProp);
			
			Property<Date> modifiedOnDateProp = new Property<Date>(new Date());
			reminderEnt.setProperty(ReminderConstant.MODIFIEDON, modifiedOnDateProp);
			
			Byte b = 0;
			Property<Byte> isDeletedProp = new Property<Byte>();
			isDeletedProp.setValue(b);
			isDeletedProp.setName(ReminderConstant.ISDELETED);
			reminderEnt.setProperty(ReminderConstant.ISDELETED, isDeletedProp);
			
			/*Property<Key<Long>> keyProp = new Property<Key<Long>>();
			Key<Long> key = new Key<Long>(2L);
			keyProp.setValue(key);*/

			
			String reminderType=reminderTypeComboBoxField.getFieldValue();
			Entity reminderTypeEntity=(Entity) reminderTypeComboBoxField.getNameVsEntity().get(reminderType);
			
			
			/*Entity reminderTypeEnt = new Entity();
			reminderTypeEnt.setType(new MetaType(TypeConstants.REMINDERTYPE));
			reminderTypeEnt.setProperty("id", keyProp);*/
			
			reminderEnt.setProperty(ReminderConstant.REMINDERTYPE, reminderTypeEntity);
			
			DateTimePicker dateOnlyPicker = (DateTimePicker) dateTimeOnlyField.getCurrentField();
			String eventDateTime = dateOnlyPicker.getTextbox().getText();
			
			int indexOfSpace=eventDateTime.indexOf(" ");
			
			String dateStr= eventDateTime.substring(0,indexOfSpace);
			String timeStr = eventDateTime.substring(indexOfSpace+1);
			
			DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy HH:mm:ss");
			Date date = fmt.parse(eventDateTime);
			
			String reminderUnitStr= reminderUnitComboBoxField.getFieldValue();
			Entity reminderUnitEntity=(Entity) reminderTypeComboBoxField.getNameVsEntity().get(reminderUnitStr);
			Date newDate = null ;
			
			
			if(reminderUnitStr.equals("weeks")){
				
				//Long.valueOf(eventDateTime);
				int remindarDuration = Integer.parseInt(remindarDurationTextFieldTB.getFieldText());
				  int days = remindarDuration * 7;
				   newDate = new Date(date.getTime()-days * MILLISECONDS_IN_DAY);
				
			}else if(reminderUnitStr.equals("minutes")){
				int remindarDuration = Integer.parseInt(remindarDurationTextFieldTB.getFieldText());
				 newDate = new Date(date.getTime()- remindarDuration * MILLISECONDS_IN_Minutes);
				
			}else if(reminderUnitStr.equals("days")){
				int remindarDuration = Integer.parseInt(remindarDurationTextFieldTB.getFieldText());
				// CalendarUtil.addDaysToDate(date, 21);
				 newDate = new Date(date.getTime() - remindarDuration * MILLISECONDS_IN_DAY);
				
			}else if(reminderUnitStr.equals("hours")){
				int remindarDuration = Integer.parseInt(remindarDurationTextFieldTB.getFieldText());
				newDate = new Date(date.getTime() - remindarDuration * MILLISECONDS_IN_Hours);
			}
				
			
			Property<Date> reminderTimeProp = new Property<Date>(newDate);
			reminderEnt.setProperty(ReminderConstant.REMINDERTIME, reminderTimeProp);
			
			
		 
		 
		return reminderEnt;
	}



	private Entity createEventEntity() {
		
		    Entity entity = new  Entity();
		    entity.setType(new MetaType(TypeConstants.EVENT));
		    
		    String eventName = textFieldTB.getFieldText();
		    entity.setPropertyByName(EventConstant.NAME, eventName);
		    
		    String eventDescription = descriptionTextField.getFieldText();
		    entity.setPropertyByName(EventConstant.DESCRIPTION, eventDescription);
		    
		    DateTimePicker dateOnlyPicker = (DateTimePicker) dateTimeOnlyField.getCurrentField();
			String eventDateTime = dateOnlyPicker.getTextbox().getText();
			int indexOfSpace=eventDateTime.indexOf(" ");
			
			String dateStr= eventDateTime.substring(0,indexOfSpace);
			String timeStr = eventDateTime.substring(indexOfSpace+1);
			
			DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy hh:mm:ss");
			Date date = fmt.parse(eventDateTime);
			
			/*DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"); 
			Date date = df.parse(eventDateTime);*/
			
			Property<Date> fromdateProp = new Property<Date>(date);
			entity.setProperty(EventConstant.FROMDATE, fromdateProp);
			
			Property<Date> toDateProp = new Property<Date>(date);
			entity.setProperty(EventConstant.TODATE, toDateProp);
			
			Property<String> fromTimeProp = new Property<String>();
			fromTimeProp.setName(EventConstant.FROMTIME);
			fromTimeProp.setValue(timeStr);
			entity.setProperty(fromTimeProp);
			
			Property<String> toTimeProp = new Property<String>();
			toTimeProp.setName(EventConstant.TOTIME);
			toTimeProp.setValue(timeStr);
			entity.setProperty(toTimeProp);
			
			
			
			 Entity userEntity=AppEnviornment.getCurrentUser();
			  Key<Serializable> key=(Key<Serializable>) userEntity.getProperty(UserConstants.ID).getValue();
			  Long userId = (Long) key.getKeyValue();
			 entity.setPropertyByName(EventConstant.OWNER, userId);
			 entity.setPropertyByName(EventConstant.CREATEDBY, userId);
			 entity.setPropertyByName(EventConstant.MODIFIEDBY, userId);
			 
			 /*entity.setPropertyByName(EventConstant.OWNER, Long.valueOf(5));
			 entity.setPropertyByName(EventConstant.CREATEDBY, Long.valueOf(5));
			 entity.setPropertyByName(EventConstant.MODIFIEDBY, Long.valueOf(5));*/
			 
			 Property<Long> serviceProp = new Property<Long>();
			 serviceProp.setName(EventConstant.SERVICEID);
			 serviceProp.setValue(12L);
			 entity.setProperty(serviceProp);
			  
			 //latLng.getLatitude();
			 //latLng.getLongitude();
			 //TODO:latitude and longitude are hard coded
			 GeoLocation geoLocation = new GeoLocation();
			 geoLocation.setLatitude(45.375334);
			 geoLocation.setLongitude(21.5633);
			 Property<GeoLocation> geoLocationProp = new Property<GeoLocation>(geoLocation);
			 entity.setProperty("geolocation", geoLocationProp);
		 
		
		
		return entity;
	}



	public Configuration getConfiguration() {
		return configuration;
	}
	public Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId) {
		Configuration conf = new Configuration();
		conf.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, allowWordWrap);
		conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		conf.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		return conf;
	}
	
	private Configuration getTextFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, visibleLines);
		configuration.setPropertyByName(TextFieldConstant.BF_READONLY, readOnly);
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(TextFieldConstant.BF_DCLS, secondaryCss);
		return configuration;
	}
	private Configuration getDateTimeFieldConfiguration(String modeSelection,String datetimefieldTimeonly, String modeTimeValue) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(DateTimeField.DATETIMEFIELD_MODE, modeSelection);
		configuration.setPropertyByName(DateTimeField.DATETIMEFIELD_TYPE, datetimefieldTimeonly);
		if(modeTimeValue!=null)
		  configuration.setPropertyByName(modeTimeValue, modeTimeValue);
		
		return configuration;
	}

	public InitiateActionContext getActionContext() {
		return actionContext;
	}

	public void setActionContext(InitiateActionContext actionContext) {
		this.actionContext = actionContext;
	}



	public Entity getReminderEntity() {
		return reminderEntity;
	}



	public void setReminderEntity(Entity reminderEntity) {
		this.reminderEntity = reminderEntity;
	}



	public void addHandle(FieldEventHandler handler) {
		AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, this, handler);
		
	}



	public String getCreateEntityType() {
		return createEntityType;
	}



	public void setCreateEntityType(String createEntityType) {
		this.createEntityType = createEntityType;
	}



	public boolean isAddMoreDetails() {
		return isAddMoreDetails;
	}



	public void setAddMoreDetails(boolean isAddMoreDetails) {
		this.isAddMoreDetails = isAddMoreDetails;
	}



	public void clearAllFields() {
		remindarDurationTextFieldTB.clear();
		descriptionTextField.clear();
		textFieldTB.clear();
		Widget widget=dateTimeOnlyField.getCurrentField();
		if(widget instanceof DateTimePicker){
			
			DateTimePicker dateTimePicker = (DateTimePicker) widget;
			dateTimePicker.getTextbox().setText("");
			
		}else if(widget instanceof DateOnlyPicker){
			
			DateOnlyPicker dateOnlyPicker = (DateOnlyPicker) widget;
			dateOnlyPicker.getTextbox().setText("");
		}else if(widget instanceof TimePicker){
			TimePicker timePicker = (TimePicker) widget;
			timePicker.getTextBox().setText("");
		}
		
	}



	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender instanceof Anchor){
			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent.setEventType(FieldEvent.EDITINPROGRESS);
			fieldEvent.setEventData("Back");	
			AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, CreateCalendarEntryScreen.this);
		}
	}



	/*public LatLng getLatLng() {
		return latLng;
	}



	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}*/
}
