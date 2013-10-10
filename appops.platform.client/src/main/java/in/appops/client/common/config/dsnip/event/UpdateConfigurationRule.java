package in.appops.client.common.config.dsnip.event;

import java.util.HashMap;

/**
 * Represents rules used for updating configurations
 * @author nairutee
 *
 */
public class UpdateConfigurationRule extends EventActionRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String CONFIGURATION_TO_UPDATE = "ConfigurationToUpdate";
	public static final String HAS_UPDATE_CONFIGURATION	 = "HasUpdateConfiguration";

	public boolean hasConfigurationUpdation() {
		return this.getPropertyByName(HAS_UPDATE_CONFIGURATION);
	}

	public HashMap<String, Object> getConfigurationToUpdateMap() {
		return this.getPropertyByName(CONFIGURATION_TO_UPDATE);
	}
}
