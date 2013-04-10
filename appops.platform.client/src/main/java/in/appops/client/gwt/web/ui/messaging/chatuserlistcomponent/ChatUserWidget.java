/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging.chatuserlistcomponent;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.broadcast.ChatEntity;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * @author mahesh@ensarm.com
 * The specific user widget that will show the user name and hold the specific user entity.
 */
public class ChatUserWidget extends HorizontalPanel{

	/**
	 * Entity reference to which this widget belong to.
	 */
	private Entity entity;
	
	/**
	 * The reference of parent so as to go the messaging component and fire appropriate event.
	 */
	private UserListWidget parentUserListWidget;
	
	public ChatUserWidget(UserListWidget userListWidget){
		this.parentUserListWidget = userListWidget;
	}
	
	/**
	 * The ui will be create using the user name and on click of user name his chat window will be displayed. 
	 * @param contactEnt
	 */
	public void createUserUi(Entity contactEnt){
		try{
			this.entity = contactEnt;
			String aliasName = contactEnt.getPropertyByName("alias").toString();
			Label userNameLbl = new Label(aliasName);
			userNameLbl.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					EntityList participantList = new EntityList();
					participantList.add(entity);
					
					ChatEntity chatEntity = new ChatEntity();
					
					participantList.add(parentUserListWidget.getParentMessagingComponent().getContactEntity());
				
					String currenUserName = parentUserListWidget.getParentMessagingComponent().getContactEntity().getPropertyByName(ContactConstant.NAME).toString();
					String aliasName = entity.getPropertyByName(ContactConstant.NAME).toString();
					
					String headerTitle = currenUserName +"##"+ aliasName;
					if(parentUserListWidget.getParentMessagingComponent().getGrpMapEntityMap().get(headerTitle)==null){
						
						chatEntity.setParticipantEntity(participantList);
						chatEntity.setHeaderTitle(headerTitle);
						chatEntity.setIsGroupChat(false);

						parentUserListWidget.getParentMessagingComponent().startNewChat(chatEntity);
					}
					else{
						ChatEntity chatEnt = parentUserListWidget.getParentMessagingComponent().getGrpMapEntityMap().get(headerTitle);
						parentUserListWidget.getParentMessagingComponent().startNewChat(chatEnt);
					}
				}
			});
			
			add(userNameLbl);
			setStylePrimaryName("chatUserWidget");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
