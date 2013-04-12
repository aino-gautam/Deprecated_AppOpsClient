/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging.spacelistcomponent;

import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.event.AppUtils;
import in.appops.client.gwt.web.ui.messaging.MessagingComponent;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEvent;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEventHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.broadcast.ChatEntity;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com
 * The Space listing will be done in this widget in the one below the other manner.
 */
public class SpaceListWidget extends Composite implements EntityListReceiver,MessengerEventHandler {
	
	/**
	 * This is the main panel that will contain the near by link and also the the space list along with 
	 * the all space link
	 */
	private VerticalPanel baseVp;
	
	/**
	 * The space listing from database will be set on this panel.
	 */
	private VerticalPanel centerBaseConatiner;
	
	/**
	 * Model reference to fetch the space list from database as and when needed.
	 */
	private SpaceListModel spaceListModel ;
	
	/**
	 * The reference to the parent to fire chat window chat event on click of nearby space.
	 */
	private MessagingComponent parentMessagingComponent;
	
	
	/**
	 * Constructor in which the global variable will be initialising and
	 * the base ui is created. 
	 * @param spaceListModel
	 */
	public SpaceListWidget(SpaceListModel spaceListModel){
		this.spaceListModel = spaceListModel;
		initialize();
		createBasicUi();
		initWidget(baseVp);
	}

	/**
	 * This method will create the basic ui for space listing.
	 * I.e. the near by link and then added the centre base panel for other psace listing and also the 
	 * all spaces link. 
	 */
	private void createBasicUi() {
		try{
			createNearByContainer();
			baseVp.add(centerBaseConatiner);
			createAllPanelContainer();
			spaceListModel.getEntityList(5, this);
			
			baseVp.setStylePrimaryName("baseChatListContainer");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * This will create the near by anchor and also code on click of near by will initiate a chat 
	 * with the near by people.
	 */
	private void createNearByContainer() {
		try {
			HorizontalPanel nearbyWidgetContainer = new HorizontalPanel();
			nearbyWidgetContainer.setStylePrimaryName("nearbyWidgetContainer");
			final Anchor spaceNameAnchor = new Anchor("Near by");
			spaceNameAnchor.setStylePrimaryName("chatSpaceNameAnchor");

			spaceNameAnchor.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					//TODO : this should be passed nearby contact entity list
					//and added them as participants
					
					ChatEntity entity = new ChatEntity();
					String groupName = spaceNameAnchor.getHTML();
					if(getParentMessagingComponent().getGrpMapEntityMap().get(groupName)==null){
						EntityList participantList = new EntityList();
						participantList.add(getParentMessagingComponent().getContactEntity());
						
						entity.setParticipantEntity(participantList);
						entity.setHeaderTitle(groupName);
						
						entity.setSpaceEntity(getParentMessagingComponent().getContactEntity());
						entity.setIsGroupChat(true);
						
						getParentMessagingComponent().startNewChat(entity);
					}
					else{
						ChatEntity chatEnt = getParentMessagingComponent().getGrpMapEntityMap().get(groupName);
						getParentMessagingComponent().startNewChat(chatEnt);
					}
				}
			});
			
			Image bearByImage = new Image("images/nearby.png");
			bearByImage.setStylePrimaryName("chatSpaceIcon");
			nearbyWidgetContainer.add(bearByImage);
			nearbyWidgetContainer.add(spaceNameAnchor);
			
			baseVp.add(nearbyWidgetContainer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This will create the three dotted panel along with the all space list.
	 * On click of all space will show all the space for user. 
	 */
	private void createAllPanelContainer() {
		try{
			VerticalPanel allLinkConatiner = new VerticalPanel();
			allLinkConatiner.setStylePrimaryName("fullWidth");
			allLinkConatiner.addStyleName("allSpacePanel");
			Label dotLbl1 = new Label(".");
			Label dotLbl2 = new Label(".");
			Label dotLbl3 = new Label(".");
			
			allLinkConatiner.add(dotLbl1);
			allLinkConatiner.add(dotLbl2);
			allLinkConatiner.add(dotLbl3);
			
			Anchor allLink = new Anchor("All spaces");
			allLink.setStylePrimaryName("chatSpaceNameAnchor");
			allLinkConatiner.add(allLink);
			
			allLinkConatiner.setCellHorizontalAlignment(dotLbl1, VerticalPanel.ALIGN_CENTER);
			allLinkConatiner.setCellHorizontalAlignment(dotLbl2, VerticalPanel.ALIGN_CENTER);
			allLinkConatiner.setCellHorizontalAlignment(dotLbl3, VerticalPanel.ALIGN_CENTER);
			allLinkConatiner.setCellHorizontalAlignment(allLink, VerticalPanel.ALIGN_CENTER);
			
			allLink.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					// TODO fire event to display all the space in popup
					
				}
			});
			
			baseVp.add(allLinkConatiner);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Any global variable must be initialised here.
	 */
	private void initialize() {
		baseVp = new VerticalPanel();
		centerBaseConatiner = new VerticalPanel();
		AppUtils.EVENT_BUS.addHandler(MessengerEvent.TYPE, this);
	}

	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub
		
	}

	/**
	 *  The space entity list from model will be passed here for space widget creation 
	 *  and setting on listing panel
	 */
	@Override
	public void onEntityListReceived(EntityList entityList) {
		try{
			for(Entity entity : entityList){
				SpaceWidgetIcon spWidgetIcon = new SpaceWidgetIcon(entity);
				spWidgetIcon.setParentSpaceListWidget(this);
				centerBaseConatiner.add(spWidgetIcon);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onEntityListUpdated() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Set the parent messaging component reference.
	 * @param messagingComponent
	 */
	public void setParentMessagingWidget(MessagingComponent messagingComponent) {
		setParentMessagingComponent(messagingComponent);
	}

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

	@Override
	public void onMessengerEvent(MessengerEvent event) {
		try{
			if(event.getEventType() == MessengerEvent.ONSPACEMSGRECIEVED){
				//Window.alert("Space Message Received");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCurrentView(Entity entity) {
		// TODO Auto-generated method stub
		
	}

}
