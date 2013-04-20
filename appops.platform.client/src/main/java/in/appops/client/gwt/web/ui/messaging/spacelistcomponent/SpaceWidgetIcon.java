/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging.spacelistcomponent;

import in.appops.client.common.event.AppUtils;
import in.appops.client.gwt.web.ui.messaging.atomosphereutil.RealTimeSyncEventSerializer;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEvent;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEventHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.broadcast.ChatEntity;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.spacemanagement.constants.SpaceConstants;

import org.atmosphere.gwt.client.AtmosphereClient;
import org.atmosphere.gwt.client.AtmosphereGWTSerializer;

import com.google.gwt.core.client.GWT;
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
public class SpaceWidgetIcon extends VerticalPanel implements MessengerEventHandler{
	
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
					
					removeStyleName("hightlightSnippet");
					EntityList participantList = new EntityList();
					participantList.add(parentSpaceListWidget.getParentMessagingComponent().getContactEntity());
					
					ChatEntity entity = new ChatEntity();
					String groupName = spaceNameAnchor.getHTML();
					if(getParentSpaceListWidget().getParentMessagingComponent().getGrpMapEntityMap().get(groupName)==null){
						entity.setParticipantEntity(participantList);
						entity.setHeaderTitle(groupName);
						entity.setSpaceEntity(spaceEntity);
						entity.setIsGroupChat(true);

						getParentSpaceListWidget().getParentMessagingComponent().startNewChat(entity);
						
						AtmosphereGWTSerializer serializer = (AtmosphereGWTSerializer) GWT.create(RealTimeSyncEventSerializer.class);
						Key<Long> value = spaceEntity.getPropertyByName(SpaceConstants.ID);
						String val =  value.getKeyValue().toString();
						
						String url = GWT.getHostPageBaseURL() + "gwtComet?entity_id="+val+"&is_space_type=true";
						AtmosphereClient client = new AtmosphereClient(url,serializer, getParentSpaceListWidget().getParentMessagingComponent());
						client.start();
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
			
			AppUtils.EVENT_BUS.addHandler(MessengerEvent.TYPE, this);
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

	@Override
	public void onMessengerEvent(MessengerEvent event) {
		try{
			if(event.getEventType() == MessengerEvent.ONSPACEMSGRECIEVED){
				//Window.alert("Space chat received");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Entity getSpaceEntity() {
		return spaceEntity;
	}

	public void highlightWidget() {
		addStyleName("hightlightSnippet");
	}
}
