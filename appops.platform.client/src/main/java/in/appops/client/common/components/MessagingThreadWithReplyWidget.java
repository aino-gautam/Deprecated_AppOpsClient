package in.appops.client.common.components;

import in.appops.client.common.config.field.textfield.TextField;
import in.appops.client.common.config.field.textfield.TextField.TextFieldConstant;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.client.common.snippet.SnippetFactory;
import in.appops.client.common.util.AppEnviornment;
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
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;
import in.appops.platform.server.core.services.usermessage.constant.MessageConstant;
import in.appops.platform.server.core.services.usermessage.constant.MessageParticipantsConstant;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MessagingThreadWithReplyWidget extends Composite implements ClickHandler,FocusHandler{

	private VerticalPanel mainPanel;
	private VerticalPanel subVerticalPanel;
	private ScrollPanel scrollPanel;
	private VerticalPanel replyBoxPanel;
	private Button replyButton;
	private TextField textFieldTA ;
	private Entity parentEntity;
	private Entity contactEntity;
	private Entity clickSnippetEntity;
	private final AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
	private SnippetFactory snippetFactory ;
	public MessagingThreadWithReplyWidget() {
		initialize();
		initWidget(mainPanel);
		mainPanel.setHeight("100%");
		mainPanel.setWidth("100%");
	}
	
	private void initialize() {
		mainPanel = new VerticalPanel();
		subVerticalPanel = new VerticalPanel();
		
		
		
	}
	
	public void createComponent(EntityList list){
		mainPanel.clear();
		snippetFactory = injector.getSnippetFactory();
		parentEntity=list.get(0);
		for(Entity entity:list){
			entity.setType(new MetaType(TypeConstants.MESSAGE));
			MessageWithUserSnippet messageWithUserSnippet = (MessageWithUserSnippet) snippetFactory.getSnippetByEntityType(entity.getType(), null);
			messageWithUserSnippet.setEntity(entity);
			messageWithUserSnippet.createUi();
			messageWithUserSnippet.setStylePrimaryName("flowPanelContent");
			subVerticalPanel.add(messageWithUserSnippet);
		}
		scrollPanel = new ScrollPanel(subVerticalPanel);
		scrollPanel.setStylePrimaryName("messageWithUserSnippetPanel");
		mainPanel.add(scrollPanel);
		mainPanel.setCellHeight(scrollPanel, "75%");
		
		replyBoxPanel = new VerticalPanel();
		replyBoxPanel = createMessageReplyBox();
		mainPanel.add(replyBoxPanel);
		mainPanel.setCellVerticalAlignment(replyBoxPanel, HasVerticalAlignment.ALIGN_BOTTOM);
		mainPanel.setCellHeight(replyBoxPanel, "25%");
		replyBoxPanel.setStylePrimaryName("replyBoxWidget");
	}
	
	
	private VerticalPanel createMessageReplyBox() {
		VerticalPanel vpPanel = new VerticalPanel();
		
		textFieldTA = new TextField();
		textFieldTA.setFieldValue("Write a reply..");
		textFieldTA.setConfiguration(getTextFieldConfiguration(10, false, TextFieldConstant.TFTTYPE_TXTAREA, "appops-TextField", "replyBoxField", null));
		textFieldTA.create();
		vpPanel.add(textFieldTA);
		
		((TextArea) textFieldTA.getWidget()).addFocusHandler(this);
				
		
		
		replyButton = new Button("Reply");
		replyButton.setStylePrimaryName("appops-Button");
		vpPanel.add(replyButton);
		vpPanel.setCellHorizontalAlignment(replyButton, HasHorizontalAlignment.ALIGN_RIGHT);
		replyButton.addClickHandler(this);
		
		return vpPanel;
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

	
	private EntityList createMessageParticipantsEntity(Entity messageEntity) {
		try{
			EntityList list = new EntityList();
			
			Entity messageParticipantsEntity =null;
			
			
			if(clickSnippetEntity.getPropertyByName("groupParticipants")!=null){
				
				HashMap<String, Object> hashMap = clickSnippetEntity.getPropertyByName("groupParticipants");
				String conString = contactEntity.getPropertyByName(ContactConstant.NAME);
				 hashMap.remove(conString);
				Iterator iter = hashMap.entrySet().iterator();
				
				
				while (iter.hasNext()) {
					messageParticipantsEntity = new Entity();
					messageParticipantsEntity.setType(new MetaType(TypeConstants.MESSAGEPARTICIPANTS));
					Map.Entry mEntry = (Map.Entry) iter.next();
					Entity contactEntity=(Entity) mEntry.getValue();
					Property< Serializable> idProperty = (Property<Serializable>) contactEntity.getProperty("id");
					Property< Serializable> idPrope =(Property< Serializable>) idProperty.getValue();
					Key<Serializable>key = (Key<Serializable>) idPrope.getValue();
					Long idValue=(Long) key.getKeyValue();
					messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.CONTACTID, idValue);
				
								
					//messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.MESSAGE, messageEntity);
					if(contactEntity.getPropertyByName("userId")!=null){
					  Long userId=contactEntity.getPropertyByName("userId");
					  messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.USERID, userId);
					}
					String name=contactEntity.getPropertyByName("name");
					name = name.trim();
					String imgBlobId=contactEntity.getPropertyByName("imgBlobId");
					messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.USERDISPLAYNAME, name);
					messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.BLOBID, imgBlobId);
					list.add(messageParticipantsEntity);
				}
				
			}else{
				messageParticipantsEntity = new Entity();
				messageParticipantsEntity.setType(new MetaType(TypeConstants.MESSAGEPARTICIPANTS));
				Property< Serializable> idProperty = (Property<Serializable>) clickSnippetEntity.getProperty("id");
				//Property< Serializable> idPrope =(Property< Serializable>) idProperty.getValue();
				Key<Serializable>key = (Key<Serializable>) idProperty.getValue();
				Long idValue=(Long) key.getKeyValue();
				messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.CONTACTID, idValue);
			
							
				//messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.MESSAGE, messageEntity);
				
				Long userId=clickSnippetEntity.getPropertyByName("userId");
				messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.USERID, userId);
				
				String name=clickSnippetEntity.getPropertyByName("name");
				name = name.trim();
				String imgBlobId=clickSnippetEntity.getPropertyByName("imgBlobId");
				messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.USERDISPLAYNAME, name);
				messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.BLOBID, imgBlobId);
				list.add(messageParticipantsEntity);
			}
		    
		    
		    return list;
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	private Entity createMessageParentEntity() {
		try{
			Entity messageEntity = new Entity();
			
			
			messageEntity.setType(new MetaType(TypeConstants.MESSAGE));
		    
			parentEntity.getProperty(MessageConstant.ID);
			
			//Key<Long> key = new Key<Long>(48L);
			Property< Serializable> idProperty = (Property<Serializable>) parentEntity.getProperty(MessageConstant.ID);
			
			Long leval=parentEntity.getPropertyByName(MessageConstant.LEVEL);
			Long familyId=parentEntity.getPropertyByName(MessageConstant.FAMILYID);
			Long spaceId=parentEntity.getPropertyByName(MessageConstant.SPACEID);
			String description =parentEntity.getPropertyByName(MessageConstant.DESCRIPTION);
			Key<Serializable> key1=(Key<Serializable>) contactEntity.getProperty(ContactConstant.ID).getValue();
			Long senderd = (Long) key1.getKeyValue();
			messageEntity.setPropertyByName(MessageConstant.ID, idProperty);
			messageEntity.setPropertyByName(MessageConstant.DESCRIPTION, description);
			messageEntity.setPropertyByName(MessageConstant.LEVEL, leval);
			messageEntity.setPropertyByName(MessageConstant.FAMILYID,familyId);
			messageEntity.setPropertyByName(MessageConstant.SPACEID, spaceId);
			messageEntity.setPropertyByName(MessageConstant.SENDERID, senderd);
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
	
	
	
	private Entity createMessageEntity() {
		try{
			Entity messageEntity = new Entity();
			messageEntity.setType(new MetaType(TypeConstants.MESSAGE));
		    
			Key<Serializable> key1=(Key<Serializable>) contactEntity.getProperty(ContactConstant.ID).getValue();
			Key<Serializable> key = (Key<Serializable>) parentEntity.getProperty(MessageConstant.ID).getValue();
			Long parentId=(Long) key.getKeyValue();
			 String description = null;
			
			description = textFieldTA.getFieldText();
			
			Long senderd = (Long) key1.getKeyValue();
			
			Entity spaceEntity=AppEnviornment.getCurrentSpace();
		      Key<Serializable> spaceKey=(Key<Serializable>) spaceEntity.getProperty(SpaceConstants.ID).getValue();
		      
		      Long spaceId=(Long) spaceKey.getKeyValue();
			
			messageEntity.setPropertyByName(MessageConstant.DESCRIPTION, description);
			messageEntity.setPropertyByName(MessageConstant.LEVEL, Long.valueOf(0));
			messageEntity.setPropertyByName(MessageConstant.SPACEID, spaceId);
			messageEntity.setPropertyByName(MessageConstant.SENDERID, senderd);
			messageEntity.setPropertyByName(MessageConstant.PARENTID, parentId);
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
	
	public Entity getContactEntity() {
		return contactEntity;
	}

	public void setContactEntity(Entity contactEntity) {
		this.contactEntity = contactEntity;
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if(sender instanceof Button){
			if(sender.equals(replyButton)){
				//textFieldTA.getText();
				if((!textFieldTA.getFieldText().equals("Write a reply..") || textFieldTA.getFieldText().equals("")) && (textFieldTA.getFieldText().equals("Write a reply..") || !textFieldTA.getFieldText().equals(""))){	
					Entity messageParentEntity=createMessageParentEntity();
					Entity messageEntity = createMessageEntity();
					EntityList messageParticipantsList=createMessageParticipantsEntity(messageEntity);
					
					replyToMessage(messageEntity,messageParentEntity,messageParticipantsList);
				}else{
					textFieldTA.setFieldValue("Write a reply..");
					textFieldTA.reset();
					
				}
			}
		}
		
	}

		
	@SuppressWarnings("unchecked")
	private void replyToMessage(final Entity messageEntity, Entity messageParentEntity, EntityList messageParticipantsList) {
		
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("messageEntity",messageEntity);
		paramMap.put("messageParticipantsEntityList", messageParticipantsList);
		paramMap.put("parentMessageEntity", messageParentEntity);
		
		StandardAction action = new StandardAction(EntityList.class, "usermessage.UserMessageService.sendMessage", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<Entity>>() {
			
			public void onFailure(Throwable caught) {
				Window.alert("operation failed ");
				caught.printStackTrace();
			}
			
			public void onSuccess(Result<Entity> result) {
				if(result!=null){
				 Entity messageParticipantEntity=result.getOperationResult();
				 messageEntity.getPropertyByName(MessageConstant.DESCRIPTION);
				  Entity embeddedEntity=createEmbeddedMessageContactEntity(messageEntity, messageParticipantEntity);
				    embeddedEntity.setType(new MetaType(TypeConstants.MESSAGE));
					MessageWithUserSnippet messageWithUserSnippet = (MessageWithUserSnippet) snippetFactory.getSnippetByEntityType(embeddedEntity.getType(), null);
					messageWithUserSnippet.setEntity(embeddedEntity);
					messageWithUserSnippet.createUi();
					messageWithUserSnippet.setStylePrimaryName("flowPanelContent");
					subVerticalPanel.add(messageWithUserSnippet);
					textFieldTA.setFieldValue("");
					textFieldTA.reset();
				}
			}
		});
		
	}
	
	private Entity createEmbeddedMessageContactEntity(Entity entity,
			Entity messageParticipantEntity) {
		Entity contactMessageEmbeddedEntity = new Entity();
		
		String description = entity.getPropertyByName(MessageConstant.DESCRIPTION);
		
		
		String name = contactEntity.getPropertyByName(ContactConstant.NAME);
		
		contactMessageEmbeddedEntity.setPropertyByName(MessageConstant.DESCRIPTION, description);
		contactMessageEmbeddedEntity.setPropertyByName(ContactConstant.NAME, name);
		if(contactEntity.getPropertyByName(ContactConstant.IMGBLOBID)!=null){
			String blobId = contactEntity.getPropertyByName(ContactConstant.IMGBLOBID);
		    contactMessageEmbeddedEntity.setPropertyByName(ContactConstant.IMGBLOBID, blobId);
		}
		
		return contactMessageEmbeddedEntity;
		
		
	}
	
	
	public Entity getClickSnippetEntity() {
		return clickSnippetEntity;
	}

	public void setClickSnippetEntity(Entity clickSnippetEntity) {
		this.clickSnippetEntity = clickSnippetEntity;
	}

	@Override
	public void onFocus(FocusEvent event) {
		if(event.getSource().equals(textFieldTA.getWidget())) {
			if(((TextArea) textFieldTA.getWidget()).getText().equals("Write a reply..")) {
				textFieldTA.clear();
			} 
		}
	}
}
