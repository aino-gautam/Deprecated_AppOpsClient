/**
 * 
 */
package in.appops.showcase.web.gwt.facebook.client;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.fields.suggestion.AppopsSuggestion;
import in.appops.client.common.fields.suggestion.SuggestionField;
import in.appops.client.gwt.web.ui.messaging.MessagingComponent;
import in.appops.client.gwt.web.ui.messaging.chatuserlistcomponent.MainUserListingComponent;
import in.appops.client.gwt.web.ui.messaging.chatuserlistcomponent.UserListWidget;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEvent;
import in.appops.client.gwt.web.ui.messaging.spacelistcomponent.SpaceListModel;
import in.appops.client.gwt.web.ui.messaging.spacelistcomponent.SpaceListWidget;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

/**
 * @author mahesh@ensarm.com
 *
 */
public class FacebookTest implements EntryPoint {
	

	@Override
	public void onModuleLoad() {
		try{
			
		/*	String clientId = "478704968863629";
			String serviceId = "11";
			AppopsFacebookWidget widget = new AppopsFacebookWidget(clientId,serviceId);
			RootPanel.get().add(widget);*/
			
	/*		SearchWidgetTest searchWidget = new SearchWidgetTest();
			RootPanel.get().add(searchWidget);*/
			
			

		/*	SpaceListModel spaceListModel  = new SpaceListModel();
			SpaceListWidget spaceListWidget = new SpaceListWidget(spaceListModel);
			*/
			
		//	Entity userEnt = createDummyEnt();
			
		//	MessagingComponent messaginComponent = new MessagingComponent();
//			messaginComponent.setUserEntity(userEnt);
			
		//	ChatUserListWidget widget = new ChatUserListWidget();
		//	RootPanel.get().add(messaginComponent);
			
			createAndAddCgatEntryPoint();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createAndAddCgatEntryPoint() {
		try{
			HorizontalPanel mainHp = new HorizontalPanel();
		
			Label enterTxtLbl = new Label("Enter name to chat:");
			enterTxtLbl.setStylePrimaryName("enterNameLbl");
		
			final SuggestionField userSuggestionField = new SuggestionField();
			userSuggestionField.setQueryName("getContactListSuggestion");
			userSuggestionField.setOperationName("contact.ContactService.getEntityList");
		
			mainHp.add(enterTxtLbl);
			mainHp.add(userSuggestionField);
			
			mainHp.setStylePrimaryName("fullWidth");
			
			mainHp.setCellHorizontalAlignment(enterTxtLbl, HorizontalPanel.ALIGN_RIGHT);
			mainHp.setCellHorizontalAlignment(userSuggestionField, HorizontalPanel.ALIGN_LEFT);

			RootPanel.get().add(mainHp);

			userSuggestionField.getSuggestBox().addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

				@Override
				public void onSelection(SelectionEvent<Suggestion> event) {

					AppopsSuggestion selectedSuggestion = userSuggestionField.getSelectedSuggestion();
					Entity accountent = selectedSuggestion.getEntity();
					userSuggestionField.getSuggestBox().setText("");
					
					MessagingComponent messaginComponent = new MessagingComponent();
					
					MessengerEvent msgEvent = new MessengerEvent(MessengerEvent.CONTACTUSERFOUND, accountent);
					AppUtils.EVENT_BUS.fireEvent(msgEvent);
				
					RootPanel.get().clear();
					RootPanel.get().add(messaginComponent);
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*private Entity createDummyEnt() {
		Entity entity = new Entity();
		entity.setType(new MetaType(TypeConstants.CONTACT));
		
		Property<Key<Long>> keyPropbase = new Property<Key<Long>>();
		Key<Long> key1 = new Key<Long>(60L);
		keyPropbase.setValue(key1);
		entity.setProperty(ContactConstant.ID, keyPropbase);
		
		Property<String> nameProp = new Property<String>("Mahesh More");
		entity.setProperty(ContactConstant.NAME, nameProp);
		
		Property<String> emailProp = new Property<String>("mahesh@ensarm.com");
		entity.setProperty(ContactConstant.EMAILID, emailProp);
		
		Property<String> aliasProp = new Property<String>("mahesh");
		entity.setProperty(ContactConstant.ALIAS, aliasProp);
		
		return entity;
	}*/

}
