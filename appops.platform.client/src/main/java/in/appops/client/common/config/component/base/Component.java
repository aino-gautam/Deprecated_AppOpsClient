package in.appops.client.common.config.component.base;

import in.appops.platform.core.shared.Configurable;

public interface Component extends Configurable {
	
	void configure();
	
	void create();
	

}
