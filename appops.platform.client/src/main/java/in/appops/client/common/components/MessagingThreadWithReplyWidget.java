package in.appops.client.common.components;

import in.appops.client.common.fields.TextField;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.client.common.snippet.SnippetFactory;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;
import in.appops.platform.server.core.services.entitystore.typemapper.AppopsTypeMapperCollector;
import in.appops.platform.server.core.services.entitystore.typemapper.ServiceTypeMap;
import in.appops.platform.server.core.services.usermessage.constant.MessageConstant;
import in.appops.platform.server.core.services.usermessage.constant.MessageParticipantsConstant;
import in.appops.platform.server.core.services.usermessage.domain.MessageParticipants;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MessagingThreadWithReplyWidget extends Composite implements ClickHandler{

	private VerticalPanel mainPanel;
	private VerticalPanel subVerticalPanel;
	private ScrollPanel scrollPanel;
	private VerticalPanel replyBoxPanel;
	private Button replyButton;
	private TextField textFieldTA ;
	private Entity parentEntity;
	private Entity contactEntity;
	private final AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
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
		SnippetFactory snippetFactory = injector.getSnippetFactory();
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
		scrollPanel.setHeight("150px;");
		mainPanel.add(scrollPanel);
		mainPanel.setCellHeight(scrollPanel, "75%");
		replyBoxPanel = new VerticalPanel();
		replyBoxPanel = createMessageReplyBox();
		mainPanel.add(replyBoxPanel);
		mainPanel.setCellVerticalAlignment(replyBoxPanel, HasVerticalAlignment.ALIGN_BOTTOM);
		mainPanel.setCellHeight(replyBoxPanel, "25%");
	}

	private VerticalPanel createMessageReplyBox() {
		VerticalPanel vpPanel = new VerticalPanel();
		
		textFieldTA = new TextField();
		textFieldTA.setFieldValue("");
		textFieldTA.setConfiguration(getTextFieldConfiguration(10, false, TextField.TEXTFIELDTYPE_TEXTAREA, "appops-TextField", null, null));
		try {
			textFieldTA.createField();
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
		vpPanel.add(textFieldTA);
		
		replyButton = new Button("Reply");
		vpPanel.add(replyButton);
		vpPanel.setCellHorizontalAlignment(replyButton, HasHorizontalAlignment.ALIGN_RIGHT);
		
		return vpPanel;
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

	private EntityList createMessageParticipantsEntity(Entity messageEntity) {
		try{
			EntityList list = new EntityList();
			
			Entity messageParticipantsEntity = new Entity();
			messageParticipantsEntity.setType(new MetaType(TypeConstants.MESSAGEPARTICIPANTS));
			
			messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.CONTACTID, Long.valueOf(62));
			messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.USERID, Long.valueOf(9));
						
			//messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.MESSAGE, messageEntity);
			messageParticipantsEntity.setPropertyByName(MessageParticipantsConstant.USERDISPLAYNAME, "Kiran Bhalerao");
		    list.add(messageParticipantsEntity);
		    
		    
		    
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
			Long parentId=parentEntity.getPropertyByName(MessageConstant.PARENTID);
			String description = textFieldTA.getText();
			Long senderd = (Long) key1.getKeyValue();
			
			messageEntity.setPropertyByName(MessageConstant.DESCRIPTION, description);
			messageEntity.setPropertyByName(MessageConstant.LEVEL, Long.valueOf(0));
			messageEntity.setPropertyByName(MessageConstant.FAMILYID, Long.valueOf(1));
			messageEntity.setPropertyByName(MessageConstant.SPACEID, Long.valueOf(1));
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
				textFieldTA.getText();
				Entity messageEntity = createMessageEntity();
				createMessageParentEntity();
				createMessageParticipantsEntity(messageEntity);
			}
		}
		
	}
}
