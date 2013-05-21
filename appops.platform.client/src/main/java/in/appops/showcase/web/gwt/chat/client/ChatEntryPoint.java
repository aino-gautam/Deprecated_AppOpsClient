package in.appops.showcase.web.gwt.chat.client;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.fields.suggestion.AppopsSuggestion;
import in.appops.client.common.fields.suggestion.AppopsSuggestionBox;
import in.appops.client.gwt.web.ui.messaging.ChatMessagingComponent;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEvent;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

/**
 * 
 * @author mahesh@ensarm.com
 *
 */
public class ChatEntryPoint implements EntryPoint{

	@Override
	public void onModuleLoad() {
		try{
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
		
			final AppopsSuggestionBox userSuggestionField = new AppopsSuggestionBox();
			//userSuggestionField.setQueryName("getContactListSuggestion");
			userSuggestionField.setQueryName("getContactForChat");
			userSuggestionField.setOperationName("contact.ContactService.getEntityList");
			userSuggestionField.setPropertyToDisplay(ContactConstant.NAME);
			
			HorizontalPanel widgetContainerHp = new HorizontalPanel();
			widgetContainerHp.setStylePrimaryName("mainChatLogin");
			widgetContainerHp.addStyleName("fadeInLeft");
			
			widgetContainerHp.add(enterTxtLbl);
			widgetContainerHp.add(userSuggestionField);

			widgetContainerHp.setCellHorizontalAlignment(enterTxtLbl, HorizontalPanel.ALIGN_RIGHT);
			widgetContainerHp.setCellHorizontalAlignment(userSuggestionField, HorizontalPanel.ALIGN_LEFT);
		
			mainHp.setStylePrimaryName("fullWidth");
			RootPanel.get().add(mainHp);

			mainHp.add(widgetContainerHp);
			mainHp.setCellHorizontalAlignment(widgetContainerHp, HorizontalPanel.ALIGN_CENTER);
			
			userSuggestionField.getSuggestBox().addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

				@Override
				public void onSelection(SelectionEvent<Suggestion> event) {

					AppopsSuggestion selectedSuggestion = userSuggestionField.getSelectedSuggestion();
					Entity accountent = selectedSuggestion.getEntity();
					userSuggestionField.getSuggestBox().setText("");
					
					ChatMessagingComponent messaginComponent = new ChatMessagingComponent();
					
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
}