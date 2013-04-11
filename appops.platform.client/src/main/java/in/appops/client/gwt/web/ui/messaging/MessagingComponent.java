/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.atmosphere.gwt.client.AtmosphereClient;
import org.atmosphere.gwt.client.AtmosphereGWTSerializer;
import org.atmosphere.gwt.client.AtmosphereListener;

import in.appops.client.common.event.AppUtils;
import in.appops.client.gwt.web.ui.messaging.atomosphereutil.RealTimeSyncEventSerializer;
import in.appops.client.gwt.web.ui.messaging.chatuserlistcomponent.MainUserListingComponent;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEvent;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEventHandler;
import in.appops.client.gwt.web.ui.messaging.spacelistcomponent.SpaceListModel;
import in.appops.client.gwt.web.ui.messaging.spacelistcomponent.SpaceListWidget;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.broadcast.ChatEntity;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;

import com.google.gwt.user.client.ui.Composite;
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
public class MessagingComponent extends Composite implements MessengerEventHandler , AtmosphereListener{
	
	/**
	 * Actual base container.
	 */
	private VerticalPanel baseVp;
	
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
	
	/**
	 * Constructor in which the global variable will be initialising and
	 * the base ui is created. 
	 */
	public MessagingComponent(){
		initialize();
		initWidget(baseVp);
	}

	/**
	 * The actual base ui is been create the panel are added in 
	 * specific structure.
	 */
	private void createUi() {
		try{
			HorizontalPanel middleContainerPanel = new HorizontalPanel();
			middleContainerPanel.setStylePrimaryName("fullWidth");
		
			initailizeChatField();
			
			VerticalPanel chatWindowTextAreaConatiner = new VerticalPanel();
			chatWindowTextAreaConatiner.setStylePrimaryName("fullWidth");
			
		//	chatWindowTextAreaConatiner.add(chatDisplayPanel);
			chatWindowTextAreaConatiner.add(chatDisplayWidget);
			chatWindowTextAreaConatiner.add(chatTextArea);
			
			middleContainerPanel.add(chatWindowTextAreaConatiner);

			chatDisplayWidget.setStylePrimaryName("chatDisplayerWidget");

			SpaceListWidget rightNavigatorPanel = createRightNavigatorPanel();
			middleContainerPanel.add(rightNavigatorPanel);
			
			middleContainerPanel.setCellHorizontalAlignment(chatWindowTextAreaConatiner, HorizontalPanel.ALIGN_LEFT);
			middleContainerPanel.setCellHorizontalAlignment(rightNavigatorPanel, HorizontalPanel.ALIGN_RIGHT);
			
			middleContainerPanel.setCellWidth(chatWindowTextAreaConatiner, "83%");
			middleContainerPanel.setCellWidth(rightNavigatorPanel, "25%");
			
			baseVp.add(middleContainerPanel);
			
			MainUserListingComponent bottomUserListPanel = createBottomUserListPanel();
			baseVp.add(bottomUserListPanel);
			
			baseVp.setStylePrimaryName("fullWidth");
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
		SpaceListWidget spaceListWidget = new SpaceListWidget(spaceListModel);
		spaceListWidget.setParentMessagingWidget(this);
		return spaceListWidget;
	}

	/**
	 * Any global variable must be initialised here.
	 */
	private void initialize() {
		baseVp = new VerticalPanel();
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
					Entity chatTextEntity = tempMap.get(userEnt);
					chatDisplayWidget.refreshChatUi(userEnt,chatTextEntity,true);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessengerEvent(MessengerEvent event) {
		
		if(event.getEventType() == MessengerEvent.CONTACTUSERFOUND){
			Entity contactEntity = (Entity) event.getEventData();
			setContactEntity(contactEntity);

			AtmosphereGWTSerializer serializer = (AtmosphereGWTSerializer) GWT.create(RealTimeSyncEventSerializer.class);
			Key<Long> value = contactEntity.getPropertyByName(ContactConstant.ID);
			String val =  value.getKeyValue().toString();
			
			String url = GWT.getHostPageBaseURL() + "gwtComet?entity_id="+val;
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
					ChatEntity chatEntity = (ChatEntity) obj;
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
						}
					}
					getGrpMapEntityMap().put(title, chatEntity);
					
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
}
