package in.appops.client.common.config.dsnip.event.form;

import in.appops.platform.core.shared.Configuration;

import java.util.Set;

public class FormEventRuleMap extends Configuration{
	
	private static final long serialVersionUID = 1L;

	public FormEventRulesList getFormEventRules(String eventName){
		return this.getPropertyByName(eventName);
	}
	
	public void addFormEventRules(String eventName, FormEventRulesList ruleSet){
		this.setPropertyByName(eventName, ruleSet);
	}
	
	public Set<String> getFormEventNames(){
		return this.getValue().keySet();
	}

}
