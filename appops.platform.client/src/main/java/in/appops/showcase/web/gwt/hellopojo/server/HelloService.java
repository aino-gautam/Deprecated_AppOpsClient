/**
 * 
 */
package in.appops.showcase.web.gwt.hellopojo.server;

import in.appops.platform.core.service.AppOpsService;
import in.appops.platform.server.core.IsService;
import in.appops.platform.server.core.IsServiceOperation;
import in.appops.platform.server.core.Parameters;
import in.appops.showcase.web.gwt.hellopojo.shared.HelloPojo;

/**
 * @author Debasish Padhy Created it on 28-Aug-2012
 */
@IsService
public interface HelloService extends AppOpsService {
	
	@IsServiceOperation(async = true, connected = true)
	@Parameters(names = { "name", "msg" })
	public HelloPojo sayHello(String name, String msg);
	
	@IsServiceOperation(async = true, connected = true)
	@Parameters(names = { "name", "msg" })
	public HelloPojo sayBigHello(String name, String msg);
	
	@Parameters(names = { "name" })
	public HelloPojo sayHello(String name);
}
