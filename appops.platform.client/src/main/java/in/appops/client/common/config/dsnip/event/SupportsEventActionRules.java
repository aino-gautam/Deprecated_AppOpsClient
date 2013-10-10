package in.appops.client.common.config.dsnip.event;

/**
 * Interface to be implemented by those classes that support processing of events rules
 * @author nairutee
 *
 */
public interface SupportsEventActionRules {
	
	public void processEventRules(String eventName);
	

}
