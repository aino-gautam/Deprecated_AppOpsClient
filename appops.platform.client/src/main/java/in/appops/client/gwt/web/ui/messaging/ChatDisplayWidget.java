/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging;

import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.broadcast.ChatEntity;
import in.appops.platform.core.operation.Result;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com			
 * Actual chat display will be done here though this component.
 */
public class ChatDisplayWidget extends VerticalPanel /*implements AtmosphereListener*/{

	//private AtmosphereClient client;
	private final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	
	private String CHATSTRING = "chatText";

	/**
	 * Chat will be displayed her on this panel.
	 */
	private VerticalPanel actualDisplayPanel;
	private ScrollPanel actualDisplayScrollPanel;
	/**
	 * The header panel which will contain the message/chat toggle button ,
	 * chat title and cross image to close the chat.
	 * 
	 */
	private HorizontalPanel headerContainer;
	
	/**
	 * At a particular time will have specific chat entity to maintain chat record
	 */
	private ChatEntity chatEntity;
	
	private Entity contactEntity;
	
	private MessagingComponent parentMessagingComponent;
	
	
	public ChatDisplayWidget(){
		initialize();
	}

	/**
	 * This will create a chat display ui for the chat entity passed to it.
	 * This will also handle the toggling between chat and message and will also 
	 * handle the closing of the chat display.
	 * @param entity
	 */
	public void createUi(ChatEntity entity) {
		headerContainer.clear();
		actualDisplayPanel.clear();
		
		try {
			this.setChatEntity(entity);
			String headerText = entity.getHeaderTitle();
			String chatHeaderText = getChatHeaderText(headerText);
			final ToggleButton toggleBtn = new ToggleButton("Message", "Chat");
			toggleBtn.setStylePrimaryName("messageChatToogle");
			toggleBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if (toggleBtn.isDown()) {
						displayChatUi();
					}
					else{
						disPlayMsgUi();
					}
				}

			});

			headerContainer.add(toggleBtn);
			Label chatHeaderLabel = new Label();
			chatHeaderLabel.setText(chatHeaderText);
			chatHeaderLabel.setTitle(chatHeaderText);
			chatHeaderLabel.setStylePrimaryName("chatHeaderLabel");
			headerContainer.add(chatHeaderLabel);

			Image crossImage = new Image("images/closeIconBlackNormal.png");
			crossImage.setTitle("Close chat");
			crossImage.setStylePrimaryName("closeChat");
			crossImage.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					String header = chatEntity.getHeaderTitle();
					parentMessagingComponent.removeFromDisplayList(header);
				}
			});

			headerContainer.add(crossImage);

			headerContainer.setCellHorizontalAlignment(toggleBtn, HorizontalPanel.ALIGN_LEFT);
			headerContainer.setCellHorizontalAlignment(chatHeaderLabel, HorizontalPanel.ALIGN_LEFT);
			headerContainer.setCellHorizontalAlignment(crossImage, HorizontalPanel.ALIGN_RIGHT);
			
			headerContainer.setCellVerticalAlignment(chatHeaderLabel, HasVerticalAlignment.ALIGN_MIDDLE);
			headerContainer.setCellVerticalAlignment(crossImage, HasVerticalAlignment.ALIGN_MIDDLE);
			
			headerContainer.addStyleName("chatHeaderPanel");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getChatHeaderText(String headerText) {
		if(headerText.contains("##")) {
			String[] headerTextArr = headerText.split("##");
			String contactName = contactEntity.getPropertyByName(ContactConstant.NAME).toString();
			for(int i = 0;i < headerTextArr.length;i++) {
				if(!contactName.equals(headerTextArr[i])) {
					return headerTextArr[i];
				}
			}
		} else {
			return headerText;
		}
		return null;
	}

	/**
	 * This method will accept the text that has been typed and along with whom have typed the statement.
	 * The chat is wrapped in the entity and this entity is then set into a map which is then set into the 
	 * global map contained in the chat entity.
	 * @param text
	 * @param chatInitialEnt
	 */
	@SuppressWarnings("unchecked")
	public void addChatMessage(String text, Entity chatInitialEnt ){
		try{
			Entity chatTextEntity = new Entity();
			Property<String> chatTextProp = new Property<String>(text);
			chatTextEntity.setProperty(CHATSTRING, chatTextProp);


			HashMap<Long, HashMap<Entity, Entity>> chatMap = getChatEntity().getChatRecordMap();

			if(chatMap == null){
				chatMap = new HashMap<Long, HashMap<Entity, Entity>>();
			}

			HashMap<Entity, Entity> tempMap =  new HashMap<Entity, Entity>();
			Integer count =  chatMap.size();
			Long counter = Long.parseLong(count.toString());
			tempMap.put(chatInitialEnt, chatTextEntity);
			chatMap.put(counter,tempMap);
			getChatEntity().setChatRecordMap(chatMap);

			Entity broadcastEntity = new Entity();
			broadcastEntity.setProperty("chat_initiated_user", chatInitialEnt);
			broadcastEntity.setProperty("chat_text" , chatTextEntity);
			
			//client.broadcast(new RealTimeSyncEvent(broadcastEntity));
			
			HorizontalPanel horizontalPanel = new HorizontalPanel();

			String name = chatInitialEnt.getPropertyByName(ContactConstant.NAME).toString();
			Label userNameLbl = new  Label(name);
			userNameLbl.setStylePrimaryName("userChattingLbl");
			horizontalPanel.add(userNameLbl);

			Label chatTextLbl = new  Label(text);
			chatTextLbl.setStylePrimaryName("chatTextLbl");
			horizontalPanel.add(chatTextLbl);
			actualDisplayPanel.add(horizontalPanel);
			
			actualDisplayScrollPanel.setVerticalScrollPosition(actualDisplayScrollPanel.getMaximumVerticalScrollPosition());

			
			Map parameters = new HashMap();
			parameters.put("chatEntity", getChatEntity());
			
			StandardAction action = new StandardAction(Entity.class, "messenger.MessengerService.saveChatEntity", parameters);
			dispatch.execute(action, new AsyncCallback<Result>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result result) {
					//Entity savedEntity = (Entity) result.getOperationResult();
					//TODO : need to update further					
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display all the msgs in the form of msgs on the display panel
	 */
	private void disPlayMsgUi() {
		try{

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display all the msgs in the form of chat on the display panel
	 */
	private void displayChatUi() {
		try{

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Any global variable must be initialised here.
	 */
	public void initialize() {
		clear();
		actualDisplayScrollPanel = new ScrollPanel();
		actualDisplayPanel = new VerticalPanel();
		headerContainer = new HorizontalPanel();
		actualDisplayScrollPanel.setStylePrimaryName("fullWidth");
		actualDisplayScrollPanel.addStyleName("scrollHeightForChat");
		
		actualDisplayPanel.setStylePrimaryName("actualChatContainer");
		actualDisplayPanel.addStyleName("fullWidth");
		
		//headerContainer.setStylePrimaryName("fullWidth");
		add(headerContainer);

		actualDisplayScrollPanel.add(actualDisplayPanel);
		add(actualDisplayScrollPanel);
	
		setStylePrimaryName("chatDisplayerWidget");
	}

	/**
	 * @return the chatEntity
	 */
	public ChatEntity getChatEntity() {
		return chatEntity;
	}

	/**
	 * Through this if the chat history is been previously present for the user or group then that ill be added
	 * on the actual display panel.
	 * @param userEnt
	 * @param chatTextEntity
	 * @param isChatRecieved 
	 */
	public void refreshChatUi(Entity userEnt, Entity chatTextEntity, boolean isChatRecieved) {
		try{
			HorizontalPanel horizontalPanel = new HorizontalPanel();
			
			String name = userEnt.getPropertyByName(ContactConstant.NAME).toString();
			Label userNameLbl = new  Label(name);
			userNameLbl.setStylePrimaryName("userChattingLbl");
			horizontalPanel.add(userNameLbl);

			String text = chatTextEntity.getPropertyByName(CHATSTRING);
			Label chatTextLbl = new  Label(text);
			chatTextLbl.setStylePrimaryName("chatTextLbl");
			
			if(isChatRecieved)
				chatTextLbl.addStyleName("chatRecievedLbl");
			
			horizontalPanel.add(chatTextLbl);
			actualDisplayPanel.add(horizontalPanel);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Override
/*	public void onMessage(List<?> messages) {
		System.out.println("Message....");

			for(Object obj : messages) {
				if(obj instanceof Serializable){
					//RealTimeSyncEvent event = (RealTimeSyncEvent)obj;
					
					HashMap<Entity, Entity> recordMap = new HashMap<Entity, Entity>();
					
					Entity broadcastEntity = (Entity) obj;
					
					Entity userEnt = (Entity) broadcastEntity.getProperty(BroadcastConstant.CHATINITIATEDUSER);
					Entity chatTextEntity = (Entity) broadcastEntity.getProperty(BroadcastConstant.CHATTEXTENTITY);
					
					recordMap.put(userEnt, chatTextEntity);
					
					Entity currentEntity = chatEntity.getUserEntity();
					String curUserEmail = currentEntity.getPropertyByName(ContactConstant.EMAILID).toString().trim();
					
					String chtInitEmail = userEnt.getPropertyByName(ContactConstant.EMAILID).toString().trim();
					
					HashMap<Long, HashMap<Entity, Entity>> chatMap = getChatEntity().getChatRecordMap();
					
					if(chatMap == null){
						chatMap = new HashMap<Long, HashMap<Entity, Entity>>();
					}
					
					Integer count =  chatMap.size();
					Long counter = Long.parseLong(count.toString());
					
					chatMap.put(counter,recordMap);
					getChatEntity().setChatRecordMap(chatMap);
					
					if(!curUserEmail.equals(chtInitEmail)){
						refreshChatUi(userEnt, chatTextEntity, true);
					}
				}
			}
	}*/

	/**
	 * @return the parentMessagingComponent
	 */
	public MessagingComponent getParentMessagingComponent() {
		return parentMessagingComponent;
	}

	/**
	 * @param parentMessagingComponent the parentMessagingComponent to set
	 */
	public void setParentMessagingComponent(MessagingComponent parentMessagingComponent) {
		this.parentMessagingComponent = parentMessagingComponent;
	}

	public void setChatEntity(ChatEntity chatEntity) {
		this.chatEntity = chatEntity;
	}

	public void setContactEntity(Entity contactEntity) {
		this.contactEntity = contactEntity;
	}

/*	*//**
	 * @return the client
	 *//*
	public AtmosphereClient getClient() {
		return client;
	}

	*//**
	 * @param client the client to set
	 *//*
	public void setClient(AtmosphereClient client) {
		this.client = client;
	}*/
}
