/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging.chatuserlistcomponent;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.fields.ImageField;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.suggestion.AppopsSuggestion;
import in.appops.client.common.fields.suggestion.AppopsSuggestionBox;
import in.appops.client.common.util.AppEnviornment;
import in.appops.client.common.util.BlobDownloader;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEvent;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEventHandler;
import in.appops.platform.core.constants.propertyconstants.SpaceConstants;
import in.appops.platform.core.constants.propertyconstants.UserConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import java.util.HashMap;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com	
 * The actual bottom user listing widget that holds the user listing widget along with the 
 * user search widget and  toggle button to show specific and all users.
 */
public class MainUserListingComponent extends Composite implements MessengerEventHandler{

	/**
	 * main container to hold the user listing widget and the user suggestion widget and toggle button
	 */
	private VerticalPanel baseVp;
	
	/**
	 * User suggestion widget for suggesting the typed user.
	 */
	private AppopsSuggestionBox userSuggestionField;
	
	/**
	 * user listing panel for listing the selected user for chat.
	 */
	private UserListingComponent userListing;
	/*
	*//**
	 * Button to toggle between the showing the all users or spcific users.
	 *//*
	private ToggleButton allSpecificBtn;*/
	private Entity userEntity;
	/**
	 * Constructor in which the global variable will be initialising and
	 * the base ui is created. 
	 */
	public MainUserListingComponent(Entity userEntity){
		this.userEntity = userEntity;
		initialize();
		createUi();
		initWidget(baseVp);
	}

	/**
	 * The actual base ui is been create the panel are added in 
	 * specific structure.
	 * And handling of user selection from suggestion box and also for the toggle button.
	 */
	private void createUi() {
		try{
			baseVp.setStylePrimaryName("fullWidth");
			baseVp.addStyleName("chatUserListWidget");
			
			baseVp.add(userSuggestionField);
			baseVp.setCellHorizontalAlignment(userSuggestionField, HorizontalPanel.ALIGN_RIGHT);
			
			createUserSelectionField();
			
			createUserSuggestionWidget();
			
			userSuggestionField.setStylePrimaryName("chatUserSuggestionField");
			userSuggestionField.getSuggestBox().setStylePrimaryName("chatUserSuggestionBox");
		/*	allSpecificBtn.setStylePrimaryName("allSpecificBtn");
			
			allSpecificBtn.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					//TODO: on click of all all the user show in botom tray else specific ie 3 users shown
				}
			});
			
			
			baseVp.add(allSpecificBtn);
			baseVp.setCellHorizontalAlignment(allSpecificBtn, HorizontalPanel.ALIGN_RIGHT);*/
			
			userSuggestionField.getSuggestBox().addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
				
				@Override
				public void onSelection(SelectionEvent<Suggestion> event) {
			
					AppopsSuggestion selectedSuggestion = userSuggestionField.getSelectedSuggestion();
					Entity contactEnt = selectedSuggestion.getEntity();
					userSuggestionField.getSuggestBox().setText("");
					
					MessengerEvent msgEvent = new MessengerEvent(MessengerEvent.STARTUSERSELECTEDCHAT, contactEnt);
					AppUtils.EVENT_BUS.fireEvent(msgEvent);
					
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createContactSnippet(Entity contactEnt) {
		try{
			ContactSnippetDisplayer contactDisplayer = new ContactSnippetDisplayer();
			String url;
			if(contactEnt.getPropertyByName(ContactConstant.IMGBLOBID) != null) {
				String blobId = contactEnt.getPropertyByName(ContactConstant.IMGBLOBID).toString();
				BlobDownloader downloader = new BlobDownloader();
				url = downloader.getIconDownloadURL(blobId);
			} else {
				url = "images/default_userIcon.png";
			}
			Configuration imageConfig = getImageFieldConfiguration(url, "contactIcon");
			Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			contactDisplayer.setConfigurationForFields(labelConfig, imageConfig);
			contactDisplayer.initialize(contactEnt);
			userListing.addToDisplayContact(contactDisplayer);
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
			userListing = new UserListingComponent();
			baseVp.add(userListing);
			baseVp.setCellHorizontalAlignment(userListing, HasHorizontalAlignment.ALIGN_CENTER);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * binding the query name and operation name for fetching the typed user in the suggestion box.
	 */
	private void createUserSuggestionWidget() {
		//TODO: changes made for spaceId not present i.e for AppopsShowcase
		//userSuggestionField.setQueryName("getMessageContact");
		
		userSuggestionField.setQueryName("getContactListSuggestion");
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		Long spaceId = null;
		if(AppEnviornment.getCurrentSpace()!=null){
			Entity spaceEnt = AppEnviornment.getCurrentSpace();
			spaceId = ((Key<Long>)spaceEnt.getPropertyByName(SpaceConstants.ID)).getKeyValue();
			paramMap.put("spaceId", spaceId);
		}
		
		Long userId = null;
		Entity spaceEnt = AppEnviornment.getCurrentSpace();
		userId = ((Key<Long>)userEntity.getPropertyByName(UserConstants.ID)).getKeyValue();
		paramMap.put("userId", userId);
		userSuggestionField.setQueryRestrictions(paramMap);
		userSuggestionField.setOperationName("contact.ContactService.getEntityList");
	}

	/**
	 * Any global variable must be initialised here.
	 */
	private void initialize() {
		baseVp = new VerticalPanel();
		userSuggestionField = new AppopsSuggestionBox();
		userSuggestionField.setPropertyToDisplay(ContactConstant.NAME);
	/*	allSpecificBtn = new ToggleButton("All", "Specific");*/
		AppUtils.EVENT_BUS.addHandler(MessengerEvent.TYPE, this);
	}
	
	@Override
	public void onMessengerEvent(MessengerEvent event) {
		try{
			if(event.getEventType() == MessengerEvent.ONUSERMSGRECEIVED){
				//Window.alert("User Message Received");
			}
			else if(event.getEventType() == MessengerEvent.ONCHATRECEIVED){
				String contactId = (String) event.getEventData();
				userListing.highlightSnippet(contactId);
			}
			else if(event.getEventType() == MessengerEvent.ONCHATENTITYREMOVED){
				String contactId = (String) event.getEventData();
				userListing.removeContactSnippet(contactId);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Configuration getImageFieldConfiguration(String url, String primaryCSS) {
		Configuration config = new Configuration();
		config.setPropertyByName(ImageField.IMAGEFIELD_BLOBID, url);
		config.setPropertyByName(ImageField.IMAGEFIELD_PRIMARYCSS, primaryCSS);
		return config;
	}
	
	public Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId) {
		Configuration config = new Configuration();
		config.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		config.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return config;
	}
	
	public Entity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(Entity userEntity) {
		this.userEntity = userEntity;
	}
}
