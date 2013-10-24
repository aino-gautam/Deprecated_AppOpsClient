package in.appops.client.common.config.dsnip.event;

import in.appops.platform.core.entity.Property;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents rules used for updating configurations
 * @author nairutee
 *
 */
public class UpdateConfigurationRule extends EventActionRule {

	public interface UpdateConfigurationRuleConstant {
		String EVENTDATA = "@eventdata";
		String SEPERATOR = ".";
		String APPCONTEXT = "@applicationcontext";
		String CURRENTENTITY = "@currententity";
		String PARENTENTITY = "@parententity";
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final String CONFIGURATION_TO_UPDATE = "ConfigurationToUpdate";
	public static final String HAS_UPDATE_CONFIGURATION	 = "HasUpdateConfiguration";

	public Boolean hasConfigurationUpdation() {
		return this.getPropertyByName(HAS_UPDATE_CONFIGURATION);
	}

	public HashMap<String, Property<? extends Serializable>> getConfigurationToUpdateMap() {
		Configuration configurationToUpdate = (Configuration) this.getProperty(CONFIGURATION_TO_UPDATE);

		return configurationToUpdate.getValue();
	}
}
