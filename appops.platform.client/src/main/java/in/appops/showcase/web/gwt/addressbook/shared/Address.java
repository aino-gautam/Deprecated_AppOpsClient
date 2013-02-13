package in.appops.showcase.web.gwt.addressbook.shared;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;

public class Address extends Entity {
	
	public Address() {
		
	}
	
	public Address(String contact, String address) {
		Property<String> addressProp = new Property<String>();
		addressProp.setValue(address);
		addressProp.setName("address");
		
		Property<String> contactProp = new Property<String>();
		contactProp.setValue(contact);
		contactProp.setName("contact");
	}
	
}
