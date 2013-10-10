package in.appops.client.common.config.model;

import in.appops.client.common.config.dsnip.event.EventActionRuleMap;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

public interface IsConfigurationModel extends Configurable {
	
	public void loadInstanceConfiguration(String instance);
	
	public Configuration getModelConfiguration();
	
	public Configuration getViewConfiguration();
	
	public EventActionRuleMap getEventActionRuleMap();
	
	public void updateConfiguration(String key, Object value);

}
