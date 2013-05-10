package in.appops.client.common.components;

import in.appops.client.common.components.WebMediaAttachWidget;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.ComboBoxField;
import in.appops.client.common.fields.DateOnlyPicker;
import in.appops.client.common.fields.DateTimeField;
import in.appops.client.common.fields.DateTimePicker;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.TextField;
import in.appops.client.touch.Screen;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.GeoLocation;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.InitiateActionContext;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.calendar.constant.EventConstant;
import in.appops.platform.server.core.services.calendar.constant.ReminderTypeConstant;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
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

public class CreateCalendarEntryScreen extends Composite implements Screen{
	
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
	private HashMap<String, Object> remindarTypeVsEntity ;
	private HashMap<String, Object> remindarUnitVsEntity;
	private HorizontalPanel reminderFieldPanel;
	private String createEntityType;
	private boolean isAddMoreDetails = false;
	//private LatLng latLng ;
	
	public CreateCalendarEntryScreen() {
		mainPanel = new VerticalPanel();
		reminderFieldPanel = new HorizontalPanel();
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
			labelField.createField();
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
							reminderTypeComboBoxField.setFieldValue(ReminderTypeConstant.TYPE);
							reminderTypeComboBoxField.setConfiguration(createComboboxConfig(null,ReminderTypeConstant.TYPE,"appops-comboBoxField",null,reminderTypeEntityList));
							reminderTypeComboBoxField.createField();
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
						reminderUnitComboBoxField.setFieldValue("unit");
						reminderUnitComboBoxField.setConfiguration(createComboboxConfig(null,"unit","appops-comboBoxField",null,reminderUnitEntityList));
						reminderUnitComboBoxField.createField();
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
				 fetchReminderEntity();
				 
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
			labelField.createField();
			
			textFieldTB = new TextField();
			textFieldTB.setFieldValue("");
			textFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextField.TEXTFIELDTYPE_TEXTBOX, "appops-TextField", null, null));
			textFieldTB.createField();
			
			LabelField labelWhenField = new LabelField();
			Configuration labelWhenConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			labelWhenField.setFieldValue("When :");
			labelWhenField.setConfiguration(labelWhenConfig);
			labelWhenField.createField();
			
			dateTimeOnlyField = new DateTimeField();
			dateTimeOnlyField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_SELECTION,DateTimeField.DATETIMEFIELD_DATETIMEONLY,null));
			dateTimeOnlyField.createField();
			
			LabelField reminderLabelField = new LabelField();
			Configuration reminderLabelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			reminderLabelField.setFieldValue("Reminder :");
			reminderLabelField.setConfiguration(reminderLabelConfig);
			reminderLabelField.createField();
			
			
			if(reminderEntity!=null){
				//reminder entity data will set to field
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
			labelField.createField();
			
			textFieldTB = new TextField();
			textFieldTB.setFieldValue("");
			textFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextField.TEXTFIELDTYPE_TEXTBOX, "appops-TextField", null, null));
			textFieldTB.createField();
			
			LabelField labelWhenField = new LabelField();
			Configuration labelWhenConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			labelWhenField.setFieldValue("When :");
			labelWhenField.setConfiguration(labelWhenConfig);
			labelWhenField.createField();
			
			dateTimeOnlyField = new DateTimeField();
			dateTimeOnlyField.setConfiguration(getDateTimeFieldConfiguration(DateTimeField.MODE_SELECTION,DateTimeField.DATETIMEFIELD_DATETIMEONLY,null));
			dateTimeOnlyField.createField();
			
			LabelField labelDescriptionField = new LabelField();
			Configuration labelDescriptionConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			labelDescriptionField.setFieldValue("Description :");
			labelDescriptionField.setConfiguration(labelDescriptionConfig);
			labelDescriptionField.createField();
			
			descriptionTextField = new TextField();
			descriptionTextField.setFieldValue("");
			descriptionTextField.setConfiguration(getTextFieldConfiguration(10, false, TextField.TEXTFIELDTYPE_TEXTAREA, "appops-TextField", null, null));
			descriptionTextField.createField();
			
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
						attachmentLabelField.createField();
						
						
						//Anchor attachementAnchor = new Anchor("Add attachment");
						WebMediaAttachWidget mediaWidget = createMediaField(); 
						
						LabelField reminderLabelField = new LabelField();
						Configuration reminderLabelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
						reminderLabelField.setFieldValue("Reminder :");
						reminderLabelField.setConfiguration(reminderLabelConfig);
						reminderLabelField.createField();
						
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
			
			
			flexTable.setWidget(1, 0, labelField);
			flexTable.setWidget(1, 2, textFieldTB);
			
			flexTable.setWidget(3, 0, labelWhenField);
			flexTable.setWidget(3, 2, dateTimeOnlyField);
			
			flexTable.setWidget(5, 0, labelDescriptionField);
			flexTable.setWidget(5, 2, descriptionTextField);
			flexTable.getCellFormatter().setVerticalAlignment(5, 0, HasVerticalAlignment.ALIGN_TOP);
			
			flexTable.setWidget(7, 2, addMoreDetailsAnchor);
			//flexTable.setWidget(10, 0, createEventButton);
			
			mainPanel.add(flexTable);
			mainPanel.setCellHorizontalAlignment(flexTable, HasHorizontalAlignment.ALIGN_CENTER);
			mainPanel.setCellVerticalAlignment(flexTable, HasVerticalAlignment.ALIGN_MIDDLE);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	private HorizontalPanel createRemiderField() {
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		try{
			horizontalPanel.setWidth("100%");
			horizontalPanel.add(reminderTypeComboBoxField);
			horizontalPanel.setCellWidth(reminderTypeComboBoxField, "10%");
			
			TextField textFieldTB = new TextField();
			textFieldTB.setFieldValue("");
			//textFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextField.TEXTFIELDTYPE_TEXTBOX, "appops-TextField", null, null));
			textFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextField.TEXTFIELDTYPE_TEXTBOX, "reminderDurationBox", null, null));
			textFieldTB.createField();
			
			//TODO:ComboBox field will add
			
			horizontalPanel.add(textFieldTB);
			horizontalPanel.setCellWidth(textFieldTB, "10%");
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
		try{
			if(createEntityType.equals("Create reminder")){
				
			}else if(createEntityType.equals("Create event")){
				
				if(textFieldTB.getText().equals("")){
									
					throw new NullPointerException(); 
				}
				DateTimePicker dateOnlyPicker = (DateTimePicker) dateTimeOnlyField.getCurrentField();
				if(dateOnlyPicker.getTextbox().getText().equals("")){
								
					throw new NullPointerException(); 
				}
			  if(isAddMoreDetails){	
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
			Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			errorLabelField.setFieldValue("Please enter all data appropriately.");
			errorLabelField.setConfiguration(labelConfig);
			errorLabelField.createField();
			mainPanel.add(errorLabelField);
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
				
			}else if(createEntityType.equals("Create event")){
			  if(isAddMoreDetails){	
				  entity = new Entity(); 
					String eventTitle = textFieldTB.getText();
					String eventDescription = descriptionTextField.getText();
					DateOnlyPicker dateOnlyPicker = (DateOnlyPicker) dateTimeOnlyField.getCurrentField();
					String eventDateTime = dateOnlyPicker.getTextbox().getText();
										
			  }else{
				    entity = new  Entity();
				    entity.setType(new MetaType(TypeConstants.EVENT));
				    
				    String eventName = textFieldTB.getText();
				    entity.setPropertyByName(EventConstant.NAME, eventName);
				    
				    String eventDescription = descriptionTextField.getText();
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
					
					
					
					 /*Entity userEntity=AppEnviornment.getCurrentUser();
					  Key<Serializable> key=(Key<Serializable>) userEntity.getProperty(UserConstants.ID).getValue();
					  Long userId = (Long) key.getKeyValue();
					 entity.setPropertyByName(EventConstant.OWNER, userId);
					 entity.setPropertyByName(EventConstant.CREATEDBY, userId);
					 entity.setPropertyByName(EventConstant.MODIFIEDBY, userId);*/
					 
					 entity.setPropertyByName(EventConstant.OWNER, Long.valueOf(1));
					 entity.setPropertyByName(EventConstant.CREATEDBY, Long.valueOf(1));
					 entity.setPropertyByName(EventConstant.MODIFIEDBY, Long.valueOf(1));
					 
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

	public Configuration getConfiguration() {
		return configuration;
	}
	public Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId) {
		Configuration config = new Configuration();
		config.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		config.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return config;
	}
	
	private Configuration getTextFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextField.TEXTFIELD_VISIBLELINES, visibleLines);
		configuration.setPropertyByName(TextField.TEXTFIELD_READONLY, readOnly);
		configuration.setPropertyByName(TextField.TEXTFIELD_TYPE, textFieldType);
		configuration.setPropertyByName(TextField.TEXTFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(TextField.TEXTFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(TextField.TEXTFIELD_DEBUGID, debugId);
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



	/*public LatLng getLatLng() {
		return latLng;
	}



	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}*/
}
