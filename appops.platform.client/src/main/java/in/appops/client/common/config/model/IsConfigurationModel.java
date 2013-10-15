package in.appops.client.common.config.model;

import in.appops.client.common.config.dsnip.event.EventActionRuleMap;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

public interface IsConfigurationModel extends Configurable {
	
	String CONFIG_MODEL = "model";
	String CONFIG_VIEW = "view";
	String CONFIG_EVENTACTIONRULEMAP = "eventActionRuleMap";
	
	public void configure();
	
	public void loadInstanceConfiguration();
	
	public Configuration getModelConfiguration();
	
	public Configuration getViewConfiguration();
	
	public EventActionRuleMap getEventActionRuleMap();
	
	public void updateConfiguration(String key, Object value);
	
	public void setInstance(String instance);

	public String getInstance();

}
