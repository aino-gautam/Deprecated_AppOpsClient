/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging.chatuserlistcomponent;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.fields.suggestion.AppopsSuggestion;
import in.appops.client.common.fields.suggestion.AppopsSuggestionBox;
import in.appops.client.gwt.web.ui.messaging.MessagingComponent;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEvent;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEventHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.broadcast.ChatEntity;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

/**
 * @author mahesh@ensarm.com	
 * The actual bottom user listing widget that holds the user listing widget along with the 
 * user search widget and  toggle button to show specific and all users.
 */
public class MainUserListingComponent extends Composite implements MessengerEventHandler{

	/**
	 * main container to hold the user listing widget and the user suggestion widget and toggle button
	 */
	private HorizontalPanel baseHp;
	
	/**
	 * User suggestion widget for suggesting the typed user.
	 */
	private AppopsSuggestionBox userSuggestionField;
	
	/**
	 * user listing panel for listing the selected user for chat.
	 */
	private UserListWidget userListWidget;
	
	/**
	 * Button to toggle between the showing the all users or spcific users.
	 */
	private ToggleButton allSpecificBtn;
	
	/**
	 * The reference to the parent to fire chat window chat event on click of nearby space.
	 */
	private MessagingComponent parentMessagingComponent;
	

	/**
	 * Constructor in which the global variable will be initialising and
	 * the base ui is created. 
	 */
	public MainUserListingComponent(){
		initialize();
		createUi();
		initWidget(baseHp);
	}

	/**
	 * The actual base ui is been create the panel are added in 
	 * specific structure.
	 * And handling of user selection from suggestion box and also for the toggle button.
	 */
	private void createUi() {
		try{
			baseHp.setStylePrimaryName("fullWidth");
			baseHp.addStyleName("chatUserListWidget");
			
			createUserSelectionField();
			
			createUserSuggestionWidget();
			
			userSuggestionField.setStylePrimaryName("chatUserSuggestionField");
			userSuggestionField.getSuggestBox().setStylePrimaryName("chatUserSuggestionBox");
			allSpecificBtn.setStylePrimaryName("allSpecificBtn");
			
			allSpecificBtn.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					//TODO: on click of all all the user show in botom tray else specific ie 3 users shown
				}
			});
			
			baseHp.add(userSuggestionField);
			baseHp.add(allSpecificBtn);
			
			baseHp.setCellHorizontalAlignment(userSuggestionField, HorizontalPanel.ALIGN_RIGHT);
			baseHp.setCellHorizontalAlignment(allSpecificBtn, HorizontalPanel.ALIGN_RIGHT);
			
			userSuggestionField.getSuggestBox().addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
				
				@Override
				public void onSelection(SelectionEvent<Suggestion> event) {
			
					AppopsSuggestion selectedSuggestion = userSuggestionField.getSelectedSuggestion();
					Entity accountent = selectedSuggestion.getEntity();
					userListWidget.addUserToList(accountent);
					userSuggestionField.getSuggestBox().setText("");
					
					
					EntityList participantList = new EntityList();
					participantList.add(accountent);
					
					participantList.add(getParentMessagingComponent().getContactEntity());
					participantList.add(accountent);
					
					ChatEntity entity = new ChatEntity();
					String currenUserName = parentMessagingComponent.getContactEntity().getPropertyByName(ContactConstant.NAME).toString();
					String aliasName = accountent.getPropertyByName(ContactConstant.NAME).toString();
					
					String headerTitle = currenUserName +"##"+ aliasName;
					if(getParentMessagingComponent().getGrpMapEntityMap().get(headerTitle)==null){
						entity.setParticipantEntity(participantList);
						entity.setHeaderTitle(headerTitle);
						entity.setIsGroupChat(false);

						getParentMessagingComponent().startNewChat(entity);
					}
					else{
						ChatEntity chatEnt = getParentMessagingComponent().getGrpMapEntityMap().get(headerTitle);
						getParentMessagingComponent().startNewChat(chatEnt);
					}
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The user listing widget initialising done here
	 */
	private void createUserSelectionField() {
		try{
			userListWidget = new UserListWidget(this);
			baseHp.add(userListWidget);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * binding the query name and operation name for fetching the typed user in the suggestion box.
	 */
	private void createUserSuggestionWidget() {
		userSuggestionField.setQueryName("getContactListSuggestion");
		userSuggestionField.setOperationName("contact.ContactService.getEntityList");
	}

	/**
	 * Any global variable must be initialised here.
	 */
	private void initialize() {
		baseHp = new HorizontalPanel();
		userSuggestionField = new AppopsSuggestionBox();
		userSuggestionField.setPropertyToDisplay(ContactConstant.NAME);
		allSpecificBtn = new ToggleButton("All", "Specific");
		AppUtils.EVENT_BUS.addHandler(MessengerEvent.TYPE, this);
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
			if(event.getEventType() == MessengerEvent.ONUSERMSGRECEIVED){
				//Window.alert("User Message Received");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
