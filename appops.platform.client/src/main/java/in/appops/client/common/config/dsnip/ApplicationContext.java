package in.appops.client.common.config.dsnip;

import in.appops.platform.core.entity.Entity;

@SuppressWarnings("serial")
public class ApplicationContext extends Entity {
	public static ApplicationContext applicationContext;
	
	public static ApplicationContext getInstance(){
		if(applicationContext == null)
			applicationContext = new ApplicationContext();
			
		return applicationContext;
	}
}
