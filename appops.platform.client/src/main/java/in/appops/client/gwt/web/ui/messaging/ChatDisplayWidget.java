/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging;

import java.util.HashMap;

import in.appops.client.gwt.web.ui.messaging.datastructure.ChatEntity;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
public class ChatDisplayWidget extends ScrollPanel{

	/**
	 * The header and the chat displayer will be present here.
	 */
	private VerticalPanel basePanel;
	
	/**
	 * Cchat will be displayed her on this panel.
	 */
	private VerticalPanel actualDisplayPanel;
	
	/**
	 * The header panel which will contain the message/chat toggle button ,
	 * chat title and cross image to close the chat.
	 * 
	 */
	private HorizontalPanel headerConatiner;
	
	/**
	 * At a particular time will have specific chat entity to maintain chat record
	 */
	private ChatEntity chatEntity;
	
	/**
	 * This is maintain to have an unique id for chat record map, 
	 * which is present in the chat entity.
	 * 
	 */
	private Long counter = 0L;
	
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
		headerConatiner.clear();
		actualDisplayPanel.clear();
		
		headerConatiner.setStylePrimaryName("fullWidth");
		try {
			this.chatEntity = entity;
			String headerText = entity.getHeaderTitle();
			//	Image upImage = new Image("Message");
			//	Image downImage = new Image("Chat");
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

			headerConatiner.add(toggleBtn);
			Label chatHeaderLabel = new Label();
			chatHeaderLabel.setText(headerText);
			chatHeaderLabel.setTitle(headerText);
			chatHeaderLabel.setStylePrimaryName("chatHeaderLabel");
			headerConatiner.add(chatHeaderLabel);

			Image crossImage = new Image("images/closeIconBlackNormal.png");
			crossImage.setTitle("Close chat");
			crossImage.setStylePrimaryName("closeChat");
			crossImage.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					// TODO fire event to close chat

				}
			});

			headerConatiner.add(crossImage);

			headerConatiner.setCellHorizontalAlignment(toggleBtn, HorizontalPanel.ALIGN_LEFT);
			headerConatiner.setCellHorizontalAlignment(chatHeaderLabel, HorizontalPanel.ALIGN_LEFT);
			headerConatiner.setCellHorizontalAlignment(crossImage, HorizontalPanel.ALIGN_RIGHT);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will accept the text that has been typed and along with whom have typed the statement.
	 * The chat is wrapped in the entity and this entity is then set into a map which is then set into the 
	 * global map contained in the chat entity.
	 * @param text
	 * @param chatInitialEnt
	 */
	public void addChatMessage(String text, Entity chatInitialEnt ){
		try{
			Entity chatTextEntity = new Entity();
			Property<String> chatTextProp = new Property<String>(text);
			chatTextEntity.setProperty("chatText", chatTextProp);


			HashMap<Long, HashMap<Entity, Entity>> chatMap = getChatEntity().getChatRecordMap();

			if(chatMap == null){
				chatMap = new HashMap<Long, HashMap<Entity, Entity>>();
			}

			HashMap<Entity, Entity> tempMap =  new HashMap<Entity, Entity>();
			tempMap.put(chatInitialEnt, chatTextEntity);
			chatMap.put(counter,tempMap);
			counter++;
			getChatEntity().setChatRecordMap(chatMap);

			HorizontalPanel horizontalPanel = new HorizontalPanel();

			String name = chatInitialEnt.getPropertyByName(ContactConstant.NAME).toString();
			Label userNameLbl = new  Label(name);
			userNameLbl.setStylePrimaryName("userChattingLbl");
			horizontalPanel.add(userNameLbl);

			Label chatTextLbl = new  Label(text);
			chatTextLbl.setStylePrimaryName("chatTextLbl");
			horizontalPanel.add(chatTextLbl);
			actualDisplayPanel.add(horizontalPanel);
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
	private void initialize() {
		basePanel = new  VerticalPanel();
		basePanel.setStylePrimaryName("fullWidth");

		actualDisplayPanel = new VerticalPanel();
		headerConatiner = new HorizontalPanel();
		setStylePrimaryName("fullWidth");
		actualDisplayPanel.setStylePrimaryName("actualChatContainer");
		actualDisplayPanel.addStyleName("fullWidth");
		headerConatiner.setStylePrimaryName("fullWidth");
		basePanel.add(headerConatiner);

		basePanel.add(actualDisplayPanel);
		add(basePanel);
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
	 */
	public void refreshChatUi(Entity userEnt, Entity chatTextEntity) {
		try{
			HorizontalPanel horizontalPanel = new HorizontalPanel();
			
			String name = userEnt.getPropertyByName(ContactConstant.NAME).toString();
			Label userNameLbl = new  Label(name);
			userNameLbl.setStylePrimaryName("userChattingLbl");
			horizontalPanel.add(userNameLbl);

			String text = chatTextEntity.getPropertyByName("chatText");
			Label chatTextLbl = new  Label(text);
			chatTextLbl.setStylePrimaryName("chatTextLbl");
			horizontalPanel.add(chatTextLbl);
			actualDisplayPanel.add(horizontalPanel);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
