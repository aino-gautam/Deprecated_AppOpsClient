package in.appops.client.common.components;

import in.appops.client.common.event.ActionEvent;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.AttachmentEvent;
import in.appops.client.common.event.handlers.AttachmentEventHandler;
import in.appops.client.common.fields.ContactBoxField;
import in.appops.client.common.fields.IntelliThoughtField;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.constants.propertyconstants.UserConstants;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.InitiateActionContext;
import in.appops.platform.core.operation.IntelliThought;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;
import in.appops.platform.server.core.services.usermessage.constant.MessageConstant;
import in.appops.platform.server.core.services.usermessage.constant.MessageParticipantsConstant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class SendMessageWidget extends Composite implements Configurable, ClickHandler, AttachmentEventHandler{

	private FlexTable baseFlexTable;
	private ContactBoxField contactBoxField;
	private IntelliThoughtField intelliThoughtField;
	private MediaAttachWidget attachMediaField;
	private boolean isAttachedMediaField;
	private HorizontalPanel mediaServicePanel;
	private Button sendMessageButton;
	public static final String IS_INTELLISHAREFIELD = "isIntelliShareField";
	public static String IS_ATTACHMEDIAFIELD = "isAttachMediaField";
	private ArrayList<String> uploadedMediaId = null;
	private SuggestionAction suggestionAction;
	private InitiateActionContext actionContext;
	private Entity userEntity;
	private Entity contactEntity;
	
	public SendMessageWidget() {
		initialize();
		initWidget(baseFlexTable);
		userEntity=AppEnviornment.getCurrentUser();
		fetchContactOfLoggedUser(userEntity);
	}
	
	public SendMessageWidget(InitiateActionContext actionContext) {
		this.actionContext = actionContext;
		initialize();
		initWidget(baseFlexTable);
		userEntity=AppEnviornment.getCurrentUser();
		fetchContactOfLoggedUser(userEntity);
	}
	
	private void initialize() {
		baseFlexTable = new FlexTable();
		contactBoxField = new ContactBoxField();
		mediaServicePanel = new HorizontalPanel();
		sendMessageButton = new Button("Send Message");
		uploadedMediaId = new ArrayList<String>();
		suggestionAction = new SuggestionAction();
	}
	
	
	public void createContactBoxField(){
		
		try {
			contactBoxField.createField();
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
	}
	
	private void createAttachMediaField() {
		attachMediaField = new WebMediaAttachWidget();
		attachMediaField.isFadeUpEffect(true);
		attachMediaField.createUi();
		
		mediaServicePanel.add(attachMediaField);
		attachMediaField.setVisible(false);
		mediaServicePanel.setWidth("100%");
		mediaServicePanel.setCellHorizontalAlignment(attachMediaField, HasHorizontalAlignment.ALIGN_RIGHT);
		
		attachMediaField.getMedia().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				attachMediaField.createAttachmentUi();
			}
		});
	}
	
	public void createComponent(Configuration configuration) throws AppOpsException{
		contactBoxField.setConfiguration(configuration);
		
		baseFlexTable.setWidget(0, 0, contactBoxField);
		baseFlexTable.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		baseFlexTable.getFlexCellFormatter().getElement(0, 0).setClassName("intelliThoughtFieldCol");
			
		baseFlexTable.setWidget(3, 0, mediaServicePanel);
		mediaServicePanel.setStylePrimaryName("intelliShareField");
		
		Boolean isIntelliShareField = 	configuration.getPropertyByName(IS_INTELLISHAREFIELD);
		createContactBoxField();
		createAttachMediaField();
		if(isIntelliShareField){
			createIntelliShareField();
		}
		
		
		//intelliShareField.addFieldEventHandler(this);
		AppUtils.EVENT_BUS.addHandler(AttachmentEvent.TYPE, this);

		
		baseFlexTable.setWidget(10, 5, sendMessageButton); 
		baseFlexTable.getCellFormatter().setHeight(8, 0, "20px");
		sendMessageButton.addClickHandler(this);
   }
	
	
	private void createIntelliShareField() throws AppOpsException {
		Configuration intelliFieldConf = getIntelliFieldConfiguration("intelliShareField", null);
		intelliThoughtField = new IntelliThoughtField();

		try {
			intelliThoughtField.setConfiguration(intelliFieldConf);
			intelliThoughtField.createField();
			
			if(actionContext != null && actionContext.getIntelliThought() != null){
				IntelliThought intelliThought = actionContext.getIntelliThought();
				String intelliHtml = intelliThought.getIntelliHtml(); 
				if( intelliHtml != null && !intelliHtml.equals("")){
					intelliThoughtField.setHTML(intelliHtml);
				}
			}
		} catch (AppOpsException e) {

		}
		baseFlexTable.getFlexCellFormatter().setRowSpan(5, 0, 4);
		VerticalPanel vp = new VerticalPanel();
		vp.add(intelliThoughtField);
		baseFlexTable.setWidget(5, 0, vp);
		baseFlexTable.getFlexCellFormatter().setVerticalAlignment(5, 0, HasVerticalAlignment.ALIGN_TOP);
		//baseFlexTable.getFlexCellFormatter().getElement(5, 0).setClassName("intelliThoughtFieldCol");
	}
	
	@SuppressWarnings("unchecked")
	private void fetchContactOfLoggedUser(final Entity userEntity) {
 		Key<Serializable> key = (Key<Serializable>) userEntity.getProperty(UserConstants.ID).getValue();
	    Long userId = (Long) key.getKeyValue();
	    Query query = new Query();
		query.setQueryName("getContactFromUser");
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("userId", userId);
		
		query.setQueryParameterMap(hashMap);
		
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("query",query);
				
		StandardAction action = new StandardAction(EntityList.class, "contact.ContactService.getEntityList", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
			
			public void onFailure(Throwable caught) {
				Window.alert("operation failed ");
				caught.printStackTrace();
			}
			
			public void onSuccess(Result<EntityList> result) {
				if(result!=null){
				  EntityList  list=result.getOperationResult();
				  try {
					   for(Entity conEntity : list){
						   contactEntity = conEntity;
						  
					   }
					   createComponent(getConfiguration(null, null));
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				    
				}
			}
		});
		
		
		
		
		
		
	    
	    
		
	}
	private Configuration getConfiguration(String primaryCss, String secondaryCss){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		
		configuration.setPropertyByName(IntelliThoughtWidget.IS_INTELLISHAREFIELD, true);
		configuration.setPropertyByName(IntelliThoughtWidget.IS_ATTACHMEDIAFIELD, true);
		return configuration;
	}
	
	private Configuration getIntelliFieldConfiguration(String primaryCss, String secondaryCss){
		// Some configurations provided as of now. To be changed as required.

		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(IntelliThoughtField.FIRE_EDITINITIATED_EVENT, "false");
		configuration.setPropertyByName(IntelliThoughtField.FIRE_THREECHARENTERED_EVENT, "true");
		configuration.setPropertyByName(IntelliThoughtField.FIRE_WORDENTERED_EVENT, "false");
		return configuration;
	}
	
	
	@Override
	public void onAttachmentEvent(AttachmentEvent event) {
		String blobId = (String) event.getEventData();
		if(event.getEventType()==AttachmentEvent.ATTACHMENTINITIATED){

		}else if(event.getEventType()==AttachmentEvent.ATTACHMENTCOMPLETED){
			if(blobId!=null){
				if(!uploadedMediaId.contains(blobId))
					uploadedMediaId.add(blobId);
			}
		}else if(event.getEventType()==AttachmentEvent.ATTACHMENTCANCELLED){
			if(blobId!=null){
				if(uploadedMediaId.contains(blobId))
					uploadedMediaId.remove(blobId);
			}
		}
		
	}

//	@Override
//	public void onFieldEvent(FieldEvent event) {
//		int eventType = event.getEventType();
//		String eventData = (String) event.getEventData();
//		
//		if(eventType == FieldEvent.EDITINITIATED) {
//			if (!isAttachedMediaField) {
//				attachMediaField.setVisible(true);
//				setAttachedMediaField(true);
//			} 
//		} else if(eventType == FieldEvent.WORDENTERED) {
//			handleWordEnteredEvent(eventData);
//		}
//		
//	}

//	private void handleWordEnteredEvent(String string) {
//		String intelliText  = intelliThoughtField.getText();
//		String[] words = intelliText.split("\\s+");
//		
//		
//		showActionSuggestion(string);
//	}
	
	
	

     private ActionEvent getActionEvent(int type, String data){
		ActionEvent actionEvent = new ActionEvent();
		actionEvent.setEventType(type);			
		actionEvent.setEventData(data);
		return actionEvent;
	}
     
     private void fireActionEvent(ActionEvent actionEvent) {
 		AppUtils.EVENT_BUS.fireEvent(actionEvent);
 	}
	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if(sender instanceof Button){
			if(sender.equals(sendMessageButton)){
				EntityList messageParticipantsEntityList = new EntityList();
				 Entity messageEntity = null;
				 Entity messageParticipantsEntity ;
				if(!contactBoxField.getText().equals("")){
				  if(!intelliThoughtField.getText().equals("")){
					   messageEntity=createMessageEntity();
				  }
					
				String [] userData=contactBoxField.getText().split(",");
				for(int i=0;i<userData.length;i++){
				if(contactBoxField.getThoseHaveContactIdMap().size()!=0 || contactBoxField.getThoseHaveContactIdUSerIdMap().size()!=0 ){
					
					if(!userData[i].contains("@")){
					  if(contactBoxField.getThoseHaveContactIdMap().containsKey(userData[i])){	 
					     Entity conatctEntity=contactBoxField.getThoseHaveContactIdMap().get(userData[i]);
					     
					     messageParticipantsEntity = createMessageParticipantsEntity(messageEntity,conatctEntity,false,"");
					     if(messageParticipantsEntity!=null)
					     messageParticipantsEntityList.add(messageParticipantsEntity);
					  }else{
						  Entity conatctEntity=contactBoxField.getThoseHaveContactIdUSerIdMap().get(userData[i]);
						  
						  messageParticipantsEntity = createMessageParticipantsEntity(messageEntity,conatctEntity,false,"");
						  if(messageParticipantsEntity!=null)
						  messageParticipantsEntityList.add(messageParticipantsEntity);
					  }
					}else{
						String plainEmail = userData[i]; 
						messageParticipantsEntity = createMessageParticipantsEntity(messageEntity,null,true,plainEmail);
						 if(messageParticipantsEntity!=null)
						 messageParticipantsEntityList.add(messageParticipantsEntity);
					}
					
					
				 }else{
					 String plainEmail = userData[i]; 
						messageParticipantsEntity = createMessageParticipantsEntity(messageEntity,null,true,plainEmail);
						 if(messageParticipantsEntity!=null)
						 messageParticipantsEntityList.add(messageParticipantsEntity);
				 }
			  }
				semdMessageToPrticipants(messageEntity,messageParticipantsEntityList);
			}
				
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void semdMessageToPrticipants(Entity messageEntity,
			EntityList messageParticipantsEntityList) {
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("messageEntity",messageEntity);
		paramMap.put("messageParticipantsEntityList", messageParticipantsEntityList);
		paramMap.put("parentMessageEntity", null);
		
		
		StandardAction action = new StandardAction(EntityList.class, "usermessage.UserMessageService.sendMessage", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<Entity>>() {
			
			public void onFailure(Throwable caught) {
				Window.alert("operation failed ");
				caught.printStackTrace();
			}
			
			public void onSuccess(Result<Entity> result) {
				if(result!=null){
				 Entity entity=result.getOperationResult();
				 intelliThoughtField.clearField();
				 contactBoxField.clearField();
				 PopupPanel popupPanel = new  PopupPanel();
				 HorizontalPanel horizontalPanel = new HorizontalPanel();
				 Label label = new Label("Your message send successfully..");
				 horizontalPanel.add(label);
				 horizontalPanel.setCellHorizontalAlignment(label, HasHorizontalAlignment.ALIGN_CENTER);
				 popupPanel.add(horizontalPanel);
				 popupPanel.show();
				 popupPanel.center();
				 popupPanel.setAutoHideEnabled(true);
				}
			}
		});
		
	}

	private Entity createMessageParticipantsEntity(Entity messageEntity, Entity conatctEntity,boolean isPlainEmail,String plainEmail) {
		    try{
		     
		      Entity messageParticipantsEntity = null ;
		     
		      
		     if(!isPlainEmail){
		      if(conatctEntity!=null){
		    		 messageParticipantsEntity = new Entity();
		    		 messageParticipantsEntity.setType(new MetaType(TypeConstants.MESSAGEPARTICIPANTS));
		        Key key = (Key) conatctEntity.getProperty(ContactConstant.ID).getValue();
		       long id =  (Long) key.getKeyValue();
		      messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.CONTACTID , Long.valueOf((id)));
		      if(conatctEntity.getPropertyByName(ContactConstant.USERID)!=null){
		           messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.USERID, Long.valueOf(9));
		      }      
		      //messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.MESSAGE, messageEntity);
		      if(conatctEntity.getPropertyByName(ContactConstant.NAME)!=null){
		        messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.USERDISPLAYNAME, conatctEntity.getPropertyByName(ContactConstant.NAME).toString());
		      }
		      if(conatctEntity.getPropertyByName(ContactConstant.IMGBLOBID)!=null){
		    	  messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.BLOBID, conatctEntity.getPropertyByName(ContactConstant.IMGBLOBID).toString()); 
		      }
		     }
		    	 return messageParticipantsEntity;
		     }else{
		    	 messageParticipantsEntity = new Entity();
		    	 messageParticipantsEntity.setType(new MetaType(TypeConstants.MESSAGEPARTICIPANTS));
		    	 messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.PLAINEMAIL, plainEmail);
		    	 return messageParticipantsEntity;
		     }
		    
		    }catch(Exception e){
		      e.printStackTrace();
		    }
		    return null;
		  }
	
	
	private Entity createMessageEntity() {
		    try{
		    	Property<Serializable>idProperty=(Property<Serializable>) contactEntity.getProperty(ContactConstant.ID);
		    	Key<Serializable>key=(Key<Serializable>) idProperty.getValue();
		    	Long id = (Long) key.getKeyValue();
		      Entity messageEntity = new Entity();
		      messageEntity.setType(new MetaType(TypeConstants.MESSAGE));
		       //TODO currently spaceId,FamilyId is 1
		      messageEntity.setPropertyByName(MessageConstant.DESCRIPTION, intelliThoughtField.getText());
		      messageEntity.setPropertyByName(MessageConstant.LEVEL, Long.valueOf(0));
		      messageEntity.setPropertyByName(MessageConstant.FAMILYID, Long.valueOf(1));
		      messageEntity.setPropertyByName(MessageConstant.SPACEID, Long.valueOf(1));
		      messageEntity.setPropertyByName(MessageConstant.SENDERID, id);
		      Property<Date> createdOnProp = new Property<Date>(new Date());
		      messageEntity.setProperty(MessageConstant.CREATEDON, createdOnProp);
		      Property<Date> modifiedOnProp = new Property<Date>(new Date());
		      messageEntity.setProperty(MessageConstant.MODIFIEDON, modifiedOnProp);
		      
		      byte value = 0;
		      Property isDeletedProp = new Property();
		      isDeletedProp.setValue(Byte.valueOf(value));
		      isDeletedProp.setName(MessageConstant.ISDELETED);
		      messageEntity.setProperty(MessageConstant.ISDELETED, isDeletedProp);
		      return messageEntity;
		      
		    }catch(Exception e){
		      e.printStackTrace();
		    }
		    return null;
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
	public boolean isAttachedMediaField() {
		return isAttachedMediaField;
	}

	public void setAttachedMediaField(boolean isAttachedMediaField) {
		this.isAttachedMediaField = isAttachedMediaField;
	}
	public void setIntelliShareFieldConfiguration(Configuration conf){
		//intelliThoughtField.setConfiguration(conf);
	}

	public InitiateActionContext getActionContext() {
		return actionContext;
	}

	public void setActionContext(InitiateActionContext actionContext) {
		this.actionContext = actionContext;
	}

	public Entity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(Entity userEntity) {
		this.userEntity = userEntity;
	}

	public Entity getContactEntity() {
		return contactEntity;
	}

	public void setContactEntity(Entity contactEntity) {
		this.contactEntity = contactEntity;
	}
}
