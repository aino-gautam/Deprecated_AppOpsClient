package in.appops.client.common.config.dsnip.event;

/**
 * Interface to be implemented by those classes that support processing of events rules
 * @author nairutee
 *
 */
public interface SupportsEventActionRules {
	
	public void processEventActionRuleMap(String eventName, Object eventData);
	
	public void processSnippetControllerRule(SnippetControllerRule snippetControllerRule);
	
	public void processUpdateConfigurationRule(UpdateConfigurationRule updateConfigurationRule, Object eventData);
	
	public void processSubEventRule(SubEventRule subEventRule);

}
