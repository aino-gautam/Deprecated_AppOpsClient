/**
 * 
 */
package in.appops.showcase.web.gwt.addressbook.client;

import java.util.HashMap;
import java.util.Map;

import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.operation.Result;
import in.appops.showcase.web.gwt.addressbook.shared.Address;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Debasish Padhy Created it on 27-Aug-2012
 */
public class AddressBook implements EntryPoint {
	
	private final DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
	private final DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);
	
	@SuppressWarnings("unchecked")
	
	public void onModuleLoad() {
		
		Map map = new HashMap();
		map.put("serviceName", "AddressBook");
		map.put("contactName", "Debasish");
		map.put("address", " Liberty Society..");
		
		StandardAction action = new StandardAction(Address.class, "addressbook.AddressBookService.addAddress", map);
		
		dispatch.execute(action, new AsyncCallback<Result>() {
			
			
			public void onFailure(Throwable caught) {
				Window.alert("operation failed ");
				caught.printStackTrace();
			}
			
			
			public void onSuccess(Result result) {
				Window.alert("operation executed");
			}
		});
		
	}
}
