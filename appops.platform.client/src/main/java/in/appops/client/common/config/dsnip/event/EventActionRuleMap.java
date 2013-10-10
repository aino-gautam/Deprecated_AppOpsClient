package in.appops.client.common.config.dsnip.event;

import java.util.ArrayList;
import java.util.Set;

import in.appops.platform.core.shared.Configuration;

/**
 * A HashMap of all the {@link EventActionRulesList} vs their event name
 * 
 * @author nairutee
 *
 */
public class EventActionRuleMap extends Configuration{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EventActionRulesList getEventActionRules(String eventName){
		return this.getPropertyByName(eventName);
	}
	
	public void addEventActionRules(String eventName, EventActionRulesList ruleSet){
		this.setPropertyByName(eventName, ruleSet);
	}
	
	public Set<String> getEventActionNames(){
		return this.getValue().keySet();
	}
}
