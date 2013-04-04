/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging.spacelistcomponent;

import in.appops.client.gwt.web.ui.messaging.datastructure.ChatEntity;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.spacemanagement.constants.SpaceConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com
 * The space widget is created using a default icon and the space name that is fetched 
 * from the space entity sent to it.
 */
public class SpaceWidgetIcon extends VerticalPanel{
	
	private Entity spaceEntity;
	
	/**
	 * parent reference to get to the messaging component and fire specific event.
	 */
	private SpaceListWidget parentSpaceListWidget;
	
	/**
	 * The space entity is sent to it to create the space name and icon ui for 
	 * space that will be listed ion the chat widget.
	 * On click of the space name anchor an event will be fired that will display the current space chat window.
	 * @param spaceEnt
	 */
	public SpaceWidgetIcon(Entity spaceEnt){
		try {
			this.spaceEntity = spaceEnt;
			HorizontalPanel widgetIconHolder = new HorizontalPanel();
			String spaceName = spaceEnt.getPropertyByName(SpaceConstants.NAME);
			final Anchor spaceNameAnchor = new Anchor(spaceName);
			spaceNameAnchor.setStylePrimaryName("chatSpaceNameAnchor");
			spaceNameAnchor.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					//TODO :	EntityList participantList =null;
					
					EntityList participantList = null;
					ChatEntity entity = new ChatEntity();
					String groupName = spaceNameAnchor.getHTML();
					if(getParentSpaceListWidget().getParentMessagingComponent().getGrpMapEntityMap().get(groupName)==null){
						entity.setParticipantEntity(participantList);
						entity.setHeaderTitle(groupName);
						entity.setUserEntity(getParentSpaceListWidget().getParentMessagingComponent().getUserEntity());
						entity.setIsGroupChat(true);

						getParentSpaceListWidget().getParentMessagingComponent().startNewChat(entity);
					}
					else{
						ChatEntity chatEnt = getParentSpaceListWidget().getParentMessagingComponent().getGrpMapEntityMap().get(groupName);
						getParentSpaceListWidget().getParentMessagingComponent().startNewChat(chatEnt);
					}
				}
			});
			
			Image spaceIcon = new Image("images/spaceIcon.png");	
			spaceIcon.setStylePrimaryName("chatSpaceIcon");
			widgetIconHolder.add(spaceIcon);
			widgetIconHolder.add(spaceNameAnchor);
			
			add(widgetIconHolder);
			
			setStylePrimaryName("spaceWidgetIcon");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the parentSpaceListWidget
	 */
	public SpaceListWidget getParentSpaceListWidget() {
		return parentSpaceListWidget;
	}

	/**
	 * @param parentSpaceListWidget the parentSpaceListWidget to set
	 */
	public void setParentSpaceListWidget(SpaceListWidget parentSpaceListWidget) {
		this.parentSpaceListWidget = parentSpaceListWidget;
	}
	
}
