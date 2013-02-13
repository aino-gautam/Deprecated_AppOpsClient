package in.appops.showcase.web.gwt.addressbook.server;

import in.appops.platform.core.service.BaseAppOpsService;
import in.appops.platform.server.core.Parameters;
import in.appops.showcase.web.gwt.addressbook.shared.Address;

public class AddressBookServiceImpl extends BaseAppOpsService implements AddressBookService {
	
	public AddressBookServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Parameters(names = { "contactName", "address" })
	public Address addAddress(String contactName, String address) {
		Address addressEntity = new Address(contactName, address);
		return addressEntity;
	}
	
}
