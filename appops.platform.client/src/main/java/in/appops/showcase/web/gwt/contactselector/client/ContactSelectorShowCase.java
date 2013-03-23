package in.appops.showcase.web.gwt.contactselector.client;

import in.appops.client.common.contactmodel.ContactSelector;
import in.appops.client.common.contactmodel.ContactSelectorModel;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class ContactSelectorShowCase implements EntryPoint {

	@Override
	public void onModuleLoad() {
		Query query = new Query();
		query.setQueryName("getContactList");
		ContactSelectorModel contactSelectorModel = new ContactSelectorModel(query);
		contactSelectorModel.setEntityList(getEntityList());
		contactSelectorModel.setNearByContact(true);
		contactSelectorModel.setYourContact(true);
		contactSelectorModel.setContactKnown(true);
		ContactSelector contactSelector = new ContactSelector(contactSelectorModel);
		contactSelector.setSelectionAllowed(true);
		contactSelector.initialize();
		RootPanel.get().add(contactSelector);
	}
	
	public EntityList getEntityList() {
		
		EntityList entityList = new EntityList();
	
		Entity contact1 = getEntity("Debasish Padhy");
		entityList.add(contact1);
		
		Entity contact2 = getEntity("Nitish Borade");
		entityList.add(contact2);
		
		Entity contact3 = getEntity("Kiran Bhalerao");
		entityList.add(contact3);
		
		Entity contact4 = getEntity("Mahesh More");
		entityList.add(contact4);
		
		Entity contact5 = getEntity("Milind Bharambe");
		entityList.add(contact5);
		
		Entity contact6 = getEntity("Chetan Solanki");
		entityList.add(contact6);
		
		Entity contact7 = getEntity("Vikram Patekar");
		entityList.add(contact7);
		
		Entity contact8 = getEntity("Dhananjay Patil");
		entityList.add(contact8);
		
		return entityList;
	}
	
	public Entity getEntity(String name) {
		Entity entity = new Entity();
		entity.setType(new MetaType(TypeConstants.CONTACT));
		entity.setPropertyByName(ContactConstant.NAME, name);
		return entity;
	}

}
