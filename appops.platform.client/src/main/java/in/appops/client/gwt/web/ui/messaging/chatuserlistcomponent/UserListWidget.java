/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging.chatuserlistcomponent;

import in.appops.client.gwt.web.ui.messaging.ChatMessagingComponent;
import in.appops.platform.core.entity.Entity;

import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

/**
 * @author mahesh@ensarm.com
 * The main widget that will hold the actual user listing for chat
 */
public class UserListWidget extends Composite{
	
	/**
	 * Base panel for holding the next, prev image and 3 user widget.
	 */
	private HorizontalPanel baseHorizontalPanel;
	
	/**
	 * used to show next user from the list
	 */
	private Image nextImage;
	
	/**
	 * used to show previous user from the list
	 */
	private Image prevImage;
	
	/**
	 * The user widget will be contained here. 
	 * In normal scenario this will hold at a time 3 user widget.
	 */
	private HorizontalPanel userDisplayerPanel;
	
	/**
	 * Map to maintain the position vs the user entity.
	 */
	private HashMap<Integer, Entity> chatUserWidgetMap;
	
	/**
	 * counter to increase when any new entity is been added in to the map.
	 */
	private int mapCount=0;
	
	/**
	 * counter for the first user widget position.
	 */
	private int firstIndex=0;
	
	/**
	 * counter for the secondIndex user widget position.
	 */
	private int secondIndex=1;
	
	/**
	 * counter for the thridIndex user widget position.
	 */
	private int thridIndex=2;
	
	/**
	 * Parent reference for getting the grand parent messaging component for firing change window chat event.
	 */
	private MainUserListingComponent chatUserListWidget;
	
	/**
	 * Constructor in which the global variable will be initialising and
	 * the base ui is created. 
	 */
	public UserListWidget(MainUserListingComponent chatUserListWidget){
		this.chatUserListWidget = chatUserListWidget;
		initialize();
		createBaseUi();
		initWidget(baseHorizontalPanel);
	}

	
	/**
	 * The actual base ui is been create the panel are added in 
	 * specific structure.
	 * Along with handling of click of next prev image link.
	 */
	private void createBaseUi() {
		baseHorizontalPanel.add(prevImage);
		baseHorizontalPanel.add(userDisplayerPanel);
		baseHorizontalPanel.add(nextImage);
		
		nextImage.setStylePrimaryName("chatUserListNxtPrevImg");
		prevImage.setStylePrimaryName("chatUserListNxtPrevImg");
		
		nextImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				if((mapCount-1)>thridIndex){
					firstIndex++;
					secondIndex++;
					thridIndex++;

					reCreateUi();
				}
			}
		});
		
		prevImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				if(firstIndex>0){
					firstIndex--;
					secondIndex--;
					thridIndex--;
					reCreateUi();
				}
			}
		});
	}

	/**
	 * Any global variable must be initialised here.
	 */
	private void initialize() {
		baseHorizontalPanel = new HorizontalPanel();
		nextImage = new Image("images/nextImg.png");
		prevImage = new Image("images/prevImg.png");
		userDisplayerPanel = new HorizontalPanel();
		chatUserWidgetMap = new HashMap<Integer, Entity>();
	}
	
	/**
	 * This will add the user to the user display panel and update the three counter appropriately.
	 * @param userEntity
	 */
	public void addUserToList(Entity userEntity){
		try {
			
			ChatUserWidget widget = new ChatUserWidget(this);
			widget.createUserUi(userEntity);
			
			chatUserWidgetMap.put(mapCount,userEntity);
			mapCount++;
						
			if(userDisplayerPanel.getWidgetCount()<3){
				userDisplayerPanel.add(widget);
			}
			else{
				firstIndex++;
				secondIndex++;
				thridIndex++;
				
				reCreateUi();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * On click of next / prev image this method will be called for recreate the three user widget
	 * using the three counter.
	 */
	private void reCreateUi() {
		try{
			userDisplayerPanel.clear();
			
			Entity firstEnt = chatUserWidgetMap.get(firstIndex);
			Entity secondEnt = chatUserWidgetMap.get(secondIndex);
			Entity thirdEnt = chatUserWidgetMap.get(thridIndex);
			
			ChatUserWidget firstWidget = new ChatUserWidget(this);
			firstWidget.createUserUi(firstEnt);

			ChatUserWidget secondWidget = new ChatUserWidget(this);
			secondWidget.createUserUi(secondEnt);
			
			ChatUserWidget thirdWidget = new ChatUserWidget(this);
			thirdWidget.createUserUi(thirdEnt);
			
			userDisplayerPanel.add(firstWidget);
			userDisplayerPanel.add(secondWidget);
			userDisplayerPanel.add(thirdWidget);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the nextImage
	 */
	public Image getNextImage() {
		return nextImage;
	}

	/**
	 * @param nextImage the nextImage to set
	 */
	public void setNextImage(Image nextImage) {
		this.nextImage = nextImage;
	}

	/**
	 * @return the prevImage
	 */
	public Image getPrevImage() {
		return prevImage;
	}

	/**
	 * @param prevImage the prevImage to set
	 */
	public void setPrevImage(Image prevImage) {
		this.prevImage = prevImage;
	}

	/**
	 * @return the userDisplayerPanel
	 */
	public HorizontalPanel getUserDisplayerPanel() {
		return userDisplayerPanel;
	}

	/**
	 * @param userDisplayerPanel the userDisplayerPanel to set
	 */
	public void setUserDisplayerPanel(HorizontalPanel userDisplayerPanel) {
		this.userDisplayerPanel = userDisplayerPanel;
	}

	/**
	 * @return the chatUserWidgetMap
	 */
	public HashMap<Integer, Entity> getChatUserWidgetMap() {
		return chatUserWidgetMap;
	}

	/**
	 * @param chatUserWidgetMap the chatUserWidgetMap to set
	 */
	public void setChatUserWidgetMap(HashMap<Integer, Entity> chatUserWidgetMap) {
		this.chatUserWidgetMap = chatUserWidgetMap;
	}

	public ChatMessagingComponent getParentMessagingComponent() {
		return chatUserListWidget.getParentMessagingComponent();
	}

}
