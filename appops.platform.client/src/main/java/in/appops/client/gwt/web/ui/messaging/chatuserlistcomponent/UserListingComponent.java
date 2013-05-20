package in.appops.client.gwt.web.ui.messaging.chatuserlistcomponent;

import in.appops.client.gwt.web.ui.messaging.ChatMessagingComponent;
import in.appops.platform.core.entity.Key;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserListingComponent extends Composite{

	private ScrollPanel basePanel;
	private VerticalPanel userSnippetContainerPanel;
	private HashMap<Integer, ContactSnippetDisplayer> contactSnippetMap = new HashMap<Integer, ContactSnippetDisplayer>();
	private MainUserListingComponent chatUserListWidget;
	private ArrayList<ContactSnippetDisplayer> widgetList = new ArrayList<ContactSnippetDisplayer>();
	
	public UserListingComponent(MainUserListingComponent chatUserListWidget){
		this.chatUserListWidget = chatUserListWidget;
		initialize();
		createUI();
		initWidget(basePanel);
	}
	
	private void initialize() {
		userSnippetContainerPanel = new VerticalPanel();
		basePanel = new ScrollPanel(userSnippetContainerPanel);
	}
	
	private void createUI() {
		int height = Window.getClientHeight() - 180;
		basePanel.setHeight(height + "px");
		addScrollHandler();
	}
	
	private void addScrollHandler() {
		basePanel.addScrollHandler(new ScrollHandler() {
			
			@Override
			public void onScroll(ScrollEvent event) {
				ScrollPanel scrollPanel = (ScrollPanel) event.getSource();
				int scrollPosition = scrollPanel.getVerticalScrollPosition();
				int lastScrollPosition = scrollPanel.getMaximumVerticalScrollPosition();
				if(scrollPosition == lastScrollPosition){
					Set<Integer> keySet = contactSnippetMap.keySet();
					Iterator<Integer> iterator = keySet.iterator();
					while(iterator.hasNext()) {
						int index = 0;
						Integer key = iterator.next();
						ContactSnippetDisplayer contactSnippet = contactSnippetMap.get(key);
						index = userSnippetContainerPanel.getWidgetIndex(contactSnippet);
						if(index == 0) {
							userSnippetContainerPanel.add(contactSnippet);
						}
					}
				}
			}
		});
	}

	public void addToDisplayContact(Widget widget) {
		Iterator<ContactSnippetDisplayer> iterator = widgetList.iterator();
		while(iterator.hasNext()) {
			ContactSnippetDisplayer contactWidget = iterator.next();
			String contactId = ((Key<Long>)contactWidget.getEntity().getPropertyByName(ContactConstant.ID)).getKeyValue().toString();
			String currentContactId = ((Key<Long>)((ContactSnippetDisplayer)widget).getEntity().getPropertyByName(ContactConstant.ID)).getKeyValue().toString();
			if(contactId.equals(currentContactId)) {
				return;
			}
		}
		
		userSnippetContainerPanel.add(widget);
		widgetList.add((ContactSnippetDisplayer) widget);
	}
	
	public ChatMessagingComponent getParentMessagingComponent() {
		return chatUserListWidget.getParentMessagingComponent();
	}

	public void highlightSnippet(String contactIdToHighlight) {
		Iterator<ContactSnippetDisplayer> iterator = widgetList.iterator();
		while(iterator.hasNext()) {
			ContactSnippetDisplayer contactWidget = iterator.next();
			String contactId = ((Key<Long>)contactWidget.getEntity().getPropertyByName(ContactConstant.ID)).getKeyValue().toString();
			if(contactId.equals(contactIdToHighlight)) {
				contactWidget.addStyleName("hightlightSnippet");
			}
		}
	}

	public void removeContactSnippet(String contactIdToRemove) {
		
		for(int i = 0; i < widgetList.size(); i++) {
			ContactSnippetDisplayer contactWidget = widgetList.get(i);
			String contactId = ((Key<Long>)contactWidget.getEntity().getPropertyByName(ContactConstant.ID)).getKeyValue().toString();
			if(contactId.equals(contactIdToRemove)) {
				userSnippetContainerPanel.remove(contactWidget);
				widgetList.remove(contactWidget);
			}
		}
	}
}
