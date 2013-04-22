package in.appops.client.gwt.web.ui.messaging.chatuserlistcomponent;

import in.appops.client.common.contactmodel.ContactSnippet;
import in.appops.platform.core.entity.broadcast.ChatEntity;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import com.google.gwt.event.dom.client.ClickEvent;

public class ContactSnippetDisplayer extends ContactSnippet{

	private UserListingComponent parentUserListWidget;
	
	public ContactSnippetDisplayer() {
		super();
		getBasePanel().setStylePrimaryName("contactDisplayerBasePanel");
	}
	
	public ContactSnippetDisplayer(UserListingComponent userListing) {
		super();
		this.parentUserListWidget = userListing;
		getBasePanel().setStylePrimaryName("contactDisplayerBasePanel");
	}

	@Override
	public void onClick(ClickEvent event) {
		getBasePanel().removeStyleName("hightlightSnippet");
	
		String currenUserName = parentUserListWidget.getParentMessagingComponent().getContactEntity().getPropertyByName(ContactConstant.NAME).toString();
		String aliasName = getEntity().getPropertyByName(ContactConstant.NAME).toString();
		
		String headerTitle = currenUserName +"##"+ aliasName;
		String reverseHeaderTitle = aliasName + "##" + currenUserName;
		ChatEntity chatEnt = getChatEntity(headerTitle, reverseHeaderTitle);
		parentUserListWidget.getParentMessagingComponent().startNewChat(chatEnt);
	}

	private ChatEntity getChatEntity(String headerTitle, String reverseHeaderTitle) {
		
		if(parentUserListWidget.getParentMessagingComponent().getGrpMapEntityMap().get(headerTitle) != null){
			ChatEntity chatEnt = parentUserListWidget.getParentMessagingComponent().getGrpMapEntityMap().get(headerTitle);
			return chatEnt;
		} else if(parentUserListWidget.getParentMessagingComponent().getGrpMapEntityMap().get(reverseHeaderTitle) != null){
			ChatEntity chatEnt = parentUserListWidget.getParentMessagingComponent().getGrpMapEntityMap().get(reverseHeaderTitle);
			return chatEnt;
		} else {
			ChatEntity chatEntity = new ChatEntity();
			EntityList participantList = new EntityList();
			participantList.add(getEntity());
			
			participantList.add(parentUserListWidget.getParentMessagingComponent().getContactEntity());
			chatEntity.setParticipantEntity(participantList);
			chatEntity.setHeaderTitle(headerTitle);
			chatEntity.setIsGroupChat(false);
			return chatEntity;
		}
	}
}
