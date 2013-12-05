package in.appops.client.common.config.dsnip.event.form;

import in.appops.client.common.config.dsnip.event.Rule;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;

public class FormEventRule extends Configuration implements Rule {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void addFormEventRule(String ruleName, Object ruleValue){
		this.setPropertyByName(ruleName, (Serializable)ruleValue);
	}

}
