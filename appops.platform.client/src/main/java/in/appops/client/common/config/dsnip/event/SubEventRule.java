package in.appops.client.common.config.dsnip.event;

import java.util.HashMap;

/**
 * 
 * @author nairutee
 * Rule that provides configurations for an event that might need to be fired as a part of some
 * other event
 */
public class SubEventRule extends EventActionRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String IS_APP_EVENT = "isappevt";
	public static final String EVENT_TO_BE_FIRED = "etbf";
	public static final String EVENT_DATA_POPULATION_CONFIGURATION= "edpopconf";
	
	/**
	 * Whether the event is an appevent. If false then it is a local event
	 * Needed to know which event bus to use to fire the event
	 * @return boolean true / false
	 */
	public Boolean isAppEvent(){
		return this.getPropertyByName(IS_APP_EVENT);
	}
	
	/**
	 * The name of the event which has to be fired
	 * @return String event name
	 */
	public String getEventNameToBeFired(){
		return this.getPropertyByName(EVENT_TO_BE_FIRED);
	}
	
	/**
	 * Get the configurations to be used for populating the event data object
	 * @return
	 */
	public HashMap<String, Object> getEventDataPopulationConfig() {
		return this.getPropertyByName(EVENT_DATA_POPULATION_CONFIGURATION);
	}
	
	

}
