package in.appops.showcase.web.gwt.contactselector.client;

import in.appops.client.common.contactmodel.ContactSelector;
import in.appops.client.common.contactmodel.ContactSelectorModel;
import in.appops.platform.core.entity.query.Query;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class ContactSelectorShowCase implements EntryPoint {

	@Override
	public void onModuleLoad() {
		Query query = new Query();
		query.setQueryName("getContactList");
		query.setListSize(8);
		ContactSelectorModel contactSelectorModel = new ContactSelectorModel(query,"contact.ContactService.getEntityList",0);
		contactSelectorModel.setNearByContact(true);
		contactSelectorModel.setYourContact(true);
		contactSelectorModel.setContactKnown(true);
		ContactSelector contactSelector = new ContactSelector(contactSelectorModel);
		contactSelector.setSelectionAllowed(true);
		RootPanel.get().add(contactSelector);
	}
}
