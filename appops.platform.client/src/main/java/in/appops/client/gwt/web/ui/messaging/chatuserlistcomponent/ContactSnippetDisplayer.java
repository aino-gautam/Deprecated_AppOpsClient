package in.appops.client.gwt.web.ui.messaging.chatuserlistcomponent;

import in.appops.client.common.contactmodel.ContactSnippet;
import in.appops.client.common.event.AppUtils;
import in.appops.client.gwt.web.ui.messaging.event.MessengerEvent;

import com.google.gwt.event.dom.client.ClickEvent;

/**
 * 
 * @author vikram@ensarm.com
 * @modified mahesh@ensarm.com
 *
 */
public class ContactSnippetDisplayer extends ContactSnippet{

	public ContactSnippetDisplayer() {
		super();
		getBasePanel().setStylePrimaryName("contactDisplayerBasePanel");
	}

	@Override
	public void onClick(ClickEvent event) {
		getBasePanel().removeStyleName("hightlightSnippet");
		getBasePanel().addStyleName("widgetSeparator");
		MessengerEvent msgEvent = new MessengerEvent(MessengerEvent.RESTARTPREVIOUSCHAT,getEntity());
		AppUtils.EVENT_BUS.fireEvent(msgEvent);
	}

}
