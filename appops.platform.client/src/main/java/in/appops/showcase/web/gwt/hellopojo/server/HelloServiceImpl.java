/**
 * 
 */
package in.appops.showcase.web.gwt.hellopojo.server;

import in.appops.platform.core.service.BaseAppOpsService;
import in.appops.platform.server.core.IsServiceOperation;
import in.appops.platform.server.core.Parameters;
import in.appops.showcase.web.gwt.hellopojo.shared.HelloPojo;

/**
 * @author Debasish Padhy Created it on 28-Aug-2012
 */

public class HelloServiceImpl extends BaseAppOpsService implements HelloService {
	
	
	public HelloPojo sayHello(String name, String msg) {
		HelloPojo pojo = new HelloPojo(name, msg);
		return pojo;
	}

	
	public HelloPojo sayHello(String name) {
		HelloPojo pojo = new HelloPojo(name);
		return pojo;
	}


	@Override
	@IsServiceOperation(async = true, connected = true)
	@Parameters(names = { "name", "msg" })
	public HelloPojo sayBigHello(String name, String msg) {
		HelloPojo pojo = new HelloPojo("BIG one "+name, msg);
		return pojo;
	}
	
}
