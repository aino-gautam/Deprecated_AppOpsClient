package in.appops.client.common.config.dsnip.event.form;

import java.util.ArrayList;

/**
 * A set consisting of {@link FormEventRule} objects
 * @author nairutee
 *
 */
public class FormEventRulesList extends ArrayList<FormEventRule>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * adds a rule to the rule list
	 * @param rule
	 */
	public void addFormEventRule(FormEventRule rule){
		this.add(rule);
	}

}
