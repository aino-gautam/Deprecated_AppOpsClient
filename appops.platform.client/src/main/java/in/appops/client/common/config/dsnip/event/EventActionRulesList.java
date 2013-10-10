package in.appops.client.common.config.dsnip.event;

import java.util.ArrayList;

/**
 * A set consisting of {@link EventActionRule} objects
 * @author nairutee
 *
 */
public class EventActionRulesList extends ArrayList<EventActionRule>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * adds a rule to the rule list
	 * @param rule
	 */
	public void addEventActionRule(EventActionRule rule){
		this.add(rule);
	}

}
