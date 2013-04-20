package in.appops.client.common.components;

import in.appops.client.common.event.ActionEvent;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.AttachmentEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.AttachmentEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.ContactBoxField;
import in.appops.client.common.fields.IntelliThoughtField;
import in.appops.client.common.util.AppEnviornment;
import in.appops.client.common.util.EntityToJsonClientConvertor;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.constants.propertyconstants.SpaceConstants;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.InitiateActionContext;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;
import in.appops.platform.server.core.services.usermessage.constant.MessageConstant;
import in.appops.platform.server.core.services.usermessage.constant.MessageParticipantsConstant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;


public class SendMessageWidget extends Composite implements Configurable, ClickHandler, FieldEventHandler, AttachmentEventHandler{

	private FlexTable baseFlexTable;
	private ContactBoxField contactBoxField;
	private IntelliThoughtField intelliShareField;
	private MediaAttachWidget attachMediaField;
	private boolean isAttachedMediaField;
	private HorizontalPanel mediaServicePanel;
	private Button sendMessageButton;
	public static final String IS_INTELLISHAREFIELD = "isIntelliShareField";
	public static String IS_ATTACHMEDIAFIELD = "isAttachMediaField";
	private ArrayList<String> uploadedMediaId = null;
	private SuggestionAction suggestionAction;
	public SendMessageWidget() {
		initialize();
		initWidget(baseFlexTable);
	}
	
	private void initialize() {
		baseFlexTable = new FlexTable();
		contactBoxField = new ContactBoxField();
		intelliShareField = new IntelliThoughtField();
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
		
		
		intelliShareField.addFieldEventHandler(this);
		AppUtils.EVENT_BUS.addHandler(AttachmentEvent.TYPE, this);
	  
		baseFlexTable.setWidget(10, 5, sendMessageButton); 
		baseFlexTable.getCellFormatter().setHeight(8, 0, "20px");
		sendMessageButton.addClickHandler(this);
   }
	
	
	private void createIntelliShareField() throws AppOpsException {
		intelliShareField.createField();
		baseFlexTable.getFlexCellFormatter().setRowSpan(5, 0, 4);

		baseFlexTable.setWidget(5, 0, intelliShareField);
		baseFlexTable.getFlexCellFormatter().setVerticalAlignment(5, 0, HasVerticalAlignment.ALIGN_TOP);
		baseFlexTable.getFlexCellFormatter().getElement(5, 0).setClassName("intelliThoughtFieldCol");
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

	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		String eventData = (String) event.getEventData();
		
		if(eventType == FieldEvent.EDITINITIATED) {
			if (!isAttachedMediaField) {
				attachMediaField.setVisible(true);
				setAttachedMediaField(true);
			} 
		} else if(eventType == FieldEvent.WORDENTERED) {
			handleWordEnteredEvent(eventData);
		}
		
	}

	private void handleWordEnteredEvent(String string) {
		String intelliText  = intelliShareField.getText();
		String[] words = intelliText.split("\\s+");
		
		
		showActionSuggestion(string);
	}
	
	@SuppressWarnings("unchecked")
	public void showActionSuggestion(String word){
	
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("word", "%"+ word +"%");
		
		StandardAction action = new StandardAction(EntityList.class, "spacemanagement.SpaceManagementService.getSuggestionAction", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
			
			public void onFailure(Throwable caught) {
				Window.alert("operation failed ");
				caught.printStackTrace();
			}
			
			public void onSuccess(Result<EntityList> result) {
				EntityList  entityList =  result.getOperationResult();
				for(Entity entity : entityList ){
					String widgetName = entity.getPropertyByName("widgetname");
					final ActionLabel actionLabel = new ActionLabel(IActionLabel.WIDGET, entity);
					actionLabel.setText(widgetName);
					//suggestionAction.addSuggestionAction(actionLabel);
					
					actionLabel.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							handleActionClick(actionLabel);
						}
					});
				}
			}
		});
	}
	
  private void handleActionClick(ActionLabel actionLabel) {
		
		InitiateActionContext context = new InitiateActionContext();
		context.setType(new MetaType("ActionContext"));
		
		Entity spaceEntity = new Entity();
		spaceEntity.setType(new MetaType(TypeConstants.SPACE));
		spaceEntity.setPropertyByName(SpaceConstants.ID, 3);
		spaceEntity.setPropertyByName(SpaceConstants.NAME, "Pune");
		
		context.setSpace(AppEnviornment.getCurrentSpace());
		context.setUploadedMedia(uploadedMediaId);
		context.setIntelliThought(intelliShareField.getIntelliThought());
		context.setAction(actionLabel.getText());
		
		JSONObject token = EntityToJsonClientConvertor.createJsonFromEntity(context);
//		Entity ent = new JsonToEntityConverter().getConvertedEntity(token);
//		InitiateActionContext cont = (InitiateActionContext)ent;
//		String action = cont.getAction();
//		ArrayList<String> media = cont.getUploadedMedia();
//		Entity space = cont.getSpace();
		ActionEvent actionEvent = getActionEvent(ActionEvent.TRANSFORMWIDGET, token.toString()); 
		fireActionEvent(actionEvent);
	}
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
				  if(!intelliShareField.getText().equals("")){
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
		
		StandardAction action = new StandardAction(EntityList.class, "usermessage.UserMessageService.sendMessage", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<Entity>>() {
			
			public void onFailure(Throwable caught) {
				Window.alert("operation failed ");
				caught.printStackTrace();
			}
			
			public void onSuccess(Result<Entity> result) {
				if(result!=null){
				 Entity entity=result.getOperationResult();
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
		      Entity messageEntity = new Entity();
		      messageEntity.setType(new MetaType(TypeConstants.MESSAGE));
		       //TODO currently spaceId,FamilyId is 1
		      messageEntity.setPropertyByName(MessageConstant.DESCRIPTION, intelliShareField.getText());
		      messageEntity.setPropertyByName(MessageConstant.LEVEL, Long.valueOf(0));
		      messageEntity.setPropertyByName(MessageConstant.FAMILYID, Long.valueOf(1));
		      messageEntity.setPropertyByName(MessageConstant.SPACEID, Long.valueOf(1));
		      messageEntity.setPropertyByName(MessageConstant.SENDERID, Long.valueOf(62));
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
		intelliShareField.setConfiguration(conf);
	}
}
