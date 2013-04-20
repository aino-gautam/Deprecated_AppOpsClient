/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging;

import in.appops.client.common.event.AppUtils;
import in.appops.client.gwt.web.ui.messaging.atomosphereutil.RealTimeSyncEventSerializer;
import in.appops.client.gwt.web.ui.messaging.chatuserlistcomponent.MainUserListingComponent;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEvent;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEventHandler;
import in.appops.client.gwt.web.ui.messaging.spacelistcomponent.SpaceListModel;
import in.appops.client.gwt.web.ui.messaging.spacelistcomponent.SpaceListWidget;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.broadcast.BroadcastEntity;
import in.appops.platform.core.entity.broadcast.ChatEntity;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.atmosphere.gwt.client.AtmosphereClient;
import org.atmosphere.gwt.client.AtmosphereGWTSerializer;
import org.atmosphere.gwt.client.AtmosphereListener;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author mahesh@ensarm.com
 * This will be the main holder component for all the components invloved in support
 * of appops chat widget.
 * 
 */
public class MessagingComponent extends Composite implements MessengerEventHandler , AtmosphereListener, ClickHandler{
	
	/**
	 * Actual base container.
	 */
	private HorizontalPanel baseHp;
	
	/**
	 * Medium through which we can send input to our chat.
	 */
	private TextArea chatTextArea;
	
	/**
	 * A map that with hold the group name (user name) vs the {@link ChatEntity}
	 * Which is used to changed the contents of display panel.
	 */
	private HashMap<String, ChatEntity> grpMapEntityMap;
	
	/**
	 * The current user in the session who is initiating the chat.
	 */
	private Entity contactEntity;
	
	/**
	 * A single chat display widget depending on the group user selected, the
	 * specific chat entity is taken from the map and sent to this widget.
	 */
	private ChatDisplayWidget chatDisplayWidget;
	
	private MainUserListingComponent mainUserListPanel;
	private FocusPanel userMsgAlertPanel;
	private FocusPanel spaceMsgAlertPanel;
	private ListingWidgetPopup userListPopup;
	private ListingWidgetPopup spaceListPopup;
	private HorizontalPanel middleContainerPanel;
	private SpaceListWidget spaceListWidget;
	
	/**
	 * Constructor in which the global variable will be initialising and
	 * the base ui is created. 
	 */
	public MessagingComponent(){
		initialize();
		initWidget(baseHp);
	}

	/**
	 * The actual base ui is been create the panel are added in 
	 * specific structure.
	 */
	private void createUi() {
		try{
			
			userMsgAlertPanel = new FocusPanel();
			mainUserListPanel = createBottomUserListPanel();
			userListPopup = new ListingWidgetPopup();
			userListPopup.setStylePrimaryName("mainUserListPanelExpand");
			userListPopup.setBaseWidget(mainUserListPanel);
			
			baseHp.add(userMsgAlertPanel);
			userMsgAlertPanel.setTitle("View User List");
			baseHp.setCellWidth(userMsgAlertPanel, "1%");
			baseHp.setCellVerticalAlignment(userMsgAlertPanel, HasVerticalAlignment.ALIGN_MIDDLE);
			userMsgAlertPanel.setStylePrimaryName("chatAlertPanel");
			userMsgAlertPanel.addClickHandler(this);
			
			middleContainerPanel = new HorizontalPanel();
			middleContainerPanel.setWidth("100%");
			
			initailizeChatField();
			
			VerticalPanel chatWindowTextAreaConatiner = new VerticalPanel();
			chatWindowTextAreaConatiner.setWidth("100%");
			
			chatWindowTextAreaConatiner.add(chatDisplayWidget);
			chatWindowTextAreaConatiner.add(chatTextArea);
			chatWindowTextAreaConatiner.setCellHorizontalAlignment(chatDisplayWidget, HasHorizontalAlignment.ALIGN_CENTER);
			chatWindowTextAreaConatiner.setCellHorizontalAlignment(chatTextArea, HasHorizontalAlignment.ALIGN_CENTER);
			
			middleContainerPanel.add(chatWindowTextAreaConatiner);
			middleContainerPanel.setCellHorizontalAlignment(chatWindowTextAreaConatiner, HasHorizontalAlignment.ALIGN_CENTER);
			
			chatDisplayWidget.setStylePrimaryName("chatDisplayerWidget");
			
			baseHp.add(middleContainerPanel);
			baseHp.setCellWidth(middleContainerPanel, "98%");
			
			SpaceListWidget rightNavigatorPanel = createRightNavigatorPanel();
			spaceMsgAlertPanel = new FocusPanel();
			spaceListPopup = new ListingWidgetPopup();
			spaceListPopup.setStylePrimaryName("spaceListPanelExpand");
			spaceListPopup.setBaseWidget(rightNavigatorPanel);
			
			baseHp.add(spaceMsgAlertPanel);
			spaceMsgAlertPanel.setTitle("View Space List");
			baseHp.setCellWidth(spaceMsgAlertPanel, "1%");
			baseHp.setCellVerticalAlignment(spaceMsgAlertPanel, HasVerticalAlignment.ALIGN_MIDDLE);
			spaceMsgAlertPanel.setStylePrimaryName("chatAlertPanel");
			spaceMsgAlertPanel.addClickHandler(this);
			
			baseHp.setWidth("100%");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This will create the ui through which user will enter
	 *  msgs and the msgs will be displayed in the chat window.
	 * 
	 */
	private void initailizeChatField() {
		try{
			chatDisplayWidget.setStylePrimaryName("chatDisplayerDeck");
			chatTextArea.setStylePrimaryName("chatTextArea");
			chatTextArea.addKeyDownHandler(new KeyDownHandler() {
				
				@Override
				public void onKeyDown(KeyDownEvent event) {
					if(event.getNativeKeyCode() == 13){
						chatTextArea.cancelKey();
						String typedText = chatTextArea.getText();
						if(!typedText.trim().equals("")){
							chatTextArea.setText("");
							addToDisplayPanel(typedText);
						}
					}
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * This will place the text on chat/message display panel 
	 * using the current user entity.
	 * @param typedText
	 */
	private void addToDisplayPanel(String typedText) {
		try{
			chatDisplayWidget.addChatMessage(typedText, getContactEntity());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the bottom user list widget and also will 
	 * add the search widget for searching user and adding
	 * them in the list.
	 * @return
	 */
	private MainUserListingComponent createBottomUserListPanel() {
		MainUserListingComponent chatUserListWidget = new MainUserListingComponent();
		chatUserListWidget.setParentMessagingWidget(this);
		return chatUserListWidget;
	}

	/**
	 * RHS Space list widget creation and initialisation
	 * @return
	 */
	private SpaceListWidget createRightNavigatorPanel() {
		SpaceListModel spaceListModel  = new SpaceListModel(contactEntity);
		spaceListWidget = new SpaceListWidget(spaceListModel);
		spaceListWidget.setParentMessagingWidget(this);
		return spaceListWidget;
	}

	/**
	 * Any global variable must be initialised here.
	 */
	private void initialize() {
		baseHp = new HorizontalPanel();
		chatTextArea = new TextArea();
		setGrpMapEntityMap(new HashMap<String, ChatEntity>());
		chatDisplayWidget = new ChatDisplayWidget();
		chatDisplayWidget.setParentMessagingComponent(this);
		AppUtils.EVENT_BUS.addHandler(MessengerEvent.TYPE, this);
	}

	/**
	 * @return the userEntity
	 */
	public Entity getContactEntity() {
		return contactEntity;
	}

	/**
	 * @param userEntity the userEntity to set
	 */
	public void setContactEntity(Entity userEntity) {
		this.contactEntity = userEntity;
	}

	/**
	 * @return the grpMapEntityMap
	 */
	public HashMap<String, ChatEntity> getGrpMapEntityMap() {
		return grpMapEntityMap;
	}

	/**
	 * @param grpMapEntityMap the grpMapEntityMap to set
	 */
	public void setGrpMapEntityMap(HashMap<String, ChatEntity> grpMapEntityMap) {
		this.grpMapEntityMap = grpMapEntityMap;
	}

	/**
	 * Chat window display handling done here. If the entity is in the map then previous
	 * displayed chat entity is been removed and added on the map and then new using the new entity
	 * the chat display is created.
	 * Else new chat display is created for new chat entity. 
	 * @param entity
	 */
	public void startNewChat(ChatEntity entity) {
		try {
			
			ChatEntity prevChatEntity = chatDisplayWidget.getChatEntity();
			if(prevChatEntity != null ){
				String title  = prevChatEntity.getHeaderTitle();
				getGrpMapEntityMap().put(title, prevChatEntity);
			}
			else{
				String title  = entity.getHeaderTitle();
				getGrpMapEntityMap().put(title, entity);
			}
			
			chatDisplayWidget.createUi(entity);
			
			HashMap<Long, HashMap<Entity, Entity>> recordMap = entity.getChatRecordMap();
			if(recordMap!=null){
				for(Long counter : recordMap.keySet()){
					HashMap<Entity, Entity> tempMap = recordMap.get(counter);
					Entity userEnt = tempMap.keySet().iterator().next();
					String curUserEmail = contactEntity.getPropertyByName(ContactConstant.EMAILID).toString().trim();
					String chtInitEmail = userEnt.getPropertyByName(ContactConstant.EMAILID).toString().trim();
					Entity chatTextEntity = tempMap.get(userEnt);
					if(!curUserEmail.equals(chtInitEmail)) {
						chatDisplayWidget.refreshChatUi(userEnt,chatTextEntity,true);
					} else {
						chatDisplayWidget.refreshChatUi(userEnt,chatTextEntity,false);
					}
				}
			}
			
			initializeContactSnippet(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessengerEvent(MessengerEvent event) {
		
		if(event.getEventType() == MessengerEvent.CONTACTUSERFOUND){
			Entity contactEntity = (Entity) event.getEventData();
			setContactEntity(contactEntity);
			chatDisplayWidget.setContactEntity(contactEntity);
			
			AtmosphereGWTSerializer serializer = (AtmosphereGWTSerializer) GWT.create(RealTimeSyncEventSerializer.class);
			Key<Long> value = contactEntity.getPropertyByName(ContactConstant.ID);
			String val =  value.getKeyValue().toString();
			
			String typeName = contactEntity.getType().getTypeName();
			typeName = typeName.replace(".", "#");
			String[] splittedArray = typeName.split("#");
			String type = splittedArray[splittedArray.length-1];
			
			String url = GWT.getHostPageBaseURL() + "gwtComet?entity_id="+val+"&entity_type="+type;
			AtmosphereClient client = new AtmosphereClient(url,serializer, this);
			client.start();
			
			createUi();
		}
		
	}

	@Override
	public void onConnected(int heartbeat, int connectionID) {
		System.out.println("Connected....");
	}

	@Override
	public void onBeforeDisconnected() {
		System.out.println("Before disconnected....");
	}


	@Override
	public void onDisconnected() {
		System.out.println("DisConnected....");

	}


	@Override
	public void onError(Throwable exception, boolean connected) {
		System.out.println("Error...."+exception.getMessage());

	}

	@Override
	public void onHeartbeat() {
		System.out.println("heartbeat....");
		
	}

	@Override
	public void onRefresh() {
		System.out.println("Refresh....");
	}

	@Override
	public void onAfterRefresh() {
		System.out.println("after Refresh....");
	}

	@Override
	public void onMessage(List<?> messages) {
		System.out.println("Message....");
		try{
			for(Object obj : messages) {
				if(obj instanceof Serializable){
					BroadcastEntity broadcastEntity = (BroadcastEntity) obj;
					ChatEntity chatEntity = (ChatEntity) broadcastEntity.getBroadcastEntity();
					String title  = chatEntity.getHeaderTitle();

					if(getGrpMapEntityMap().isEmpty()){
						startNewChat(chatEntity);
					}
					else{
						if(chatDisplayWidget.getChatEntity().getHeaderTitle().equals(title)){
							HashMap<Long, HashMap<Entity, Entity>> recordMap = chatEntity.getChatRecordMap();
							if(recordMap!=null){
								Integer val = recordMap.size()-1;
								Long counter = Long.parseLong(val.toString());
								HashMap<Entity, Entity> tempMap = recordMap.get(counter);
								
								
								String curUserEmail = contactEntity.getPropertyByName(ContactConstant.EMAILID).toString().trim();
								
								
								
								Entity userEnt = tempMap.keySet().iterator().next();
								
								String chtInitEmail = userEnt.getPropertyByName(ContactConstant.EMAILID).toString().trim();
								
								Entity chatTextEntity = tempMap.get(userEnt);
								
								if(!curUserEmail.equals(chtInitEmail))
									chatDisplayWidget.refreshChatUi(userEnt,chatTextEntity,true);
								/*else
									chatDisplayWidget.refreshChatUi(userEnt,chatTextEntity,true);*/
							}
						} else {
							ChatEntity chatEnt = getGrpMapEntityMap().get(title);
							if(chatEnt != null) {
								if(chatEnt.getIsGroupChat()) {
									spaceListWidget.receivedChat(chatEnt);
									spaceMsgAlertPanel.addStyleName("chatReceivedAlert");
								} else {
									mainUserListPanel.receivedChat(chatEnt);
									userMsgAlertPanel.addStyleName("chatReceivedAlert");
								}
							} else {
								if(chatEntity.getIsGroupChat()) {
									spaceListWidget.receivedChat(chatEntity);
									spaceMsgAlertPanel.addStyleName("chatReceivedAlert");
								} else {
									initializeContactSnippet(chatEntity);
									mainUserListPanel.receivedChat(chatEntity);
									userMsgAlertPanel.addStyleName("chatReceivedAlert");
								}
							}
						}
					}
					getGrpMapEntityMap().put(title, chatEntity);
					chatDisplayWidget.setChatEntity(chatEntity);
					
					MessengerEvent msgEvent;
					if(chatEntity.getIsGroupChat()){
						msgEvent = new MessengerEvent(MessengerEvent.ONSPACEMSGRECIEVED, chatEntity);
					}
					else{
						msgEvent = new MessengerEvent(MessengerEvent.ONUSERMSGRECEIVED, chatEntity);
					}
					AppUtils.EVENT_BUS.fireEvent(msgEvent);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(userMsgAlertPanel)) {
			userMsgAlertPanel.removeStyleName("chatReceivedAlert");
			userListPopup.show();
			userListPopup.setPopupPosition(0, 45);
		} else if(event.getSource().equals(spaceMsgAlertPanel)) {
			spaceMsgAlertPanel.removeStyleName("chatReceivedAlert");
			spaceListPopup.show();
			int left = Window.getClientWidth() - 200;
			spaceListPopup.setPopupPosition(left, 45);
		}
	}

	public void removeFromDisplayList(String header) {
		ChatEntity chatEnt = getGrpMapEntityMap().remove(header);
		mainUserListPanel.removeFromList(chatEnt);
		chatDisplayWidget.setChatEntity(null);
		Set<String> ketSet = getGrpMapEntityMap().keySet();
		Iterator<String> iterator = ketSet.iterator();
		if(iterator.hasNext()) {
			String key = iterator.next();
			ChatEntity chatEntity = getGrpMapEntityMap().get(key);
			startNewChat(chatEntity);
		} else {
			chatDisplayWidget.initialize();
		}
	}
	
	private void initializeContactSnippet(ChatEntity entity) {
		if(!entity.getIsGroupChat()) {
			EntityList participantList = entity.getParticipantEntity();
			Iterator<Entity> iterator = participantList.iterator();
			while(iterator.hasNext()) {
				Entity contactEnt = iterator.next();
				String contactId = ((Key<Long>)contactEnt.getPropertyByName(ContactConstant.ID)).getKeyValue().toString();
				String currentContactId = ((Key<Long>)getContactEntity().getPropertyByName(ContactConstant.ID)).getKeyValue().toString();
				if(!contactId.equals(currentContactId)) {
					mainUserListPanel.createContactSnippet(contactEnt);
				}
			}
		}
	}
}
