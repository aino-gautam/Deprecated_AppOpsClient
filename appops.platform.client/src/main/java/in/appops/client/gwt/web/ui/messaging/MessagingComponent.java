/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging;

import java.util.HashMap;
import java.util.List;

import org.atmosphere.gwt.client.AtmosphereListener;

import in.appops.client.gwt.web.ui.messaging.chatuserlistcomponent.MainUserListingComponent;
import in.appops.client.gwt.web.ui.messaging.datastructure.ChatEntity;
import in.appops.client.gwt.web.ui.messaging.spacelistcomponent.SpaceListModel;
import in.appops.client.gwt.web.ui.messaging.spacelistcomponent.SpaceListWidget;
import in.appops.platform.core.entity.Entity;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
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
public class MessagingComponent extends Composite{
	
	/**
	 * Actual base container.
	 */
	private VerticalPanel baseVp;
	
	/**
	 * Panel to hold different chat display widget.
	 */
	private DeckPanel chatDisplayPanel;
	
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
	private Entity userEntity;
	
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
		createUi();
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
			
			chatWindowTextAreaConatiner.add(chatDisplayPanel);
			chatWindowTextAreaConatiner.add(chatTextArea);
			
			middleContainerPanel.add(chatWindowTextAreaConatiner);

			SpaceListWidget rightNavigatorPanel = createRightNavigatorPanel();
			middleContainerPanel.add(rightNavigatorPanel);
			
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
			
			chatDisplayPanel.setAnimationEnabled(true);
			chatDisplayPanel.add(chatDisplayWidget);
			chatDisplayPanel.showWidget(0);
			
			chatDisplayPanel.setStylePrimaryName("chatDisplayerDeck");
			chatDisplayPanel.addStyleName("fullWidth");
			
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
			ChatDisplayWidget chatDisplayWidget = (ChatDisplayWidget) chatDisplayPanel.getWidget(0);
			chatDisplayWidget.addChatMessage(typedText, getUserEntity());
			chatDisplayPanel.showWidget(0);
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
		SpaceListModel spaceListModel  = new SpaceListModel();
		SpaceListWidget spaceListWidget = new SpaceListWidget(spaceListModel);
		spaceListWidget.setParentMessagingWidget(this);
		return spaceListWidget;
	}

	/**
	 * Any global variable must be initialised here.
	 */
	private void initialize() {
		baseVp = new VerticalPanel();
		chatDisplayPanel = new DeckPanel();
		chatTextArea = new TextArea();
		setGrpMapEntityMap(new HashMap<String, ChatEntity>());
		chatDisplayWidget = new ChatDisplayWidget();
	}

	/**
	 * @return the userEntity
	 */
	public Entity getUserEntity() {
		return userEntity;
	}

	/**
	 * @param userEntity the userEntity to set
	 */
	public void setUserEntity(Entity userEntity) {
		this.userEntity = userEntity;
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
					chatDisplayWidget.refreshChatUi(userEnt,chatTextEntity);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
