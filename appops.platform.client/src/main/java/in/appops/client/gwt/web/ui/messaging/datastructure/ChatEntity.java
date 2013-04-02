/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging.datastructure;

import java.util.HashMap;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.util.EntityList;

/**
 * @author mahesh@ensarm.com
 * Domain entity which which contain the reference of :
 * 1. is the entity a group chat
 * 2. who is the user
 * 3. what is header title
 * 4. chat record map for this specific entity. 
 */
public class ChatEntity extends Entity{
	
	private String isGroupChat = "isGroupChat";
	private String userChatEnt = "userEntity" ;
	private String participantEntity = "participantEntity";
	private String headerTitle = "headerTitle";
	private String chatRecordMap = "chatRecordMap";
	
	public void setIsGroupChat(Boolean isGroupChat){
		setPropertyByName(this.isGroupChat, isGroupChat);
	}
	
	public Boolean getIsGroupChat(){
		return getPropertyByName(this.isGroupChat);
	}
	
	public void setUserEntity(Entity userEntity){
		setPropertyByName(this.userChatEnt, userEntity);
	}
	
	public Entity getUserEntity(){
		return getPropertyByName(this.userChatEnt);
	}
	
	public void setParticipantEntity(EntityList participantEntity){
		setPropertyByName(this.participantEntity, participantEntity);
	}
	
	public EntityList getParticipantEntity(){
		return getPropertyByName(this.participantEntity);
	}
	
	public void setHeaderTitle(String headerTitle){
		setPropertyByName(this.headerTitle, headerTitle);
	}
	
	public String getHeaderTitle(){
		return getPropertyByName(this.headerTitle);
	}
	
	public void setChatRecordMap(HashMap<Long,HashMap<Entity, Entity>>  chatRecordMap){
		setPropertyByName(this.chatRecordMap, chatRecordMap);
	}
	
	public HashMap<Long,HashMap<Entity, Entity>> getChatRecordMap(){
		return getPropertyByName(this.chatRecordMap);
	}
}
