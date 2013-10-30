package in.appops.client.common.config.dsnip.event;

import java.io.Serializable;

import in.appops.platform.core.shared.Configuration;

/**
 * Base class for an event rule. 
 * @author nairutee
 *
 */
public class EventActionRule extends Configuration implements Rule {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void addEventActionRule(String ruleName, Object ruleValue){
		this.setPropertyByName(ruleName, (Serializable)ruleValue);
	}

}
