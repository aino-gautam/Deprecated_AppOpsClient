package in.appops.showcase.web.gwt.addressbook.server;

import in.appops.platform.core.service.AppOpsService;
import in.appops.platform.server.core.Parameters;
import in.appops.showcase.web.gwt.addressbook.shared.Address;

public interface AddressBookService extends AppOpsService {
	
	@Parameters(names = { "contactName", "address" })
	public Address addAddress(String contactName, String address);
}
