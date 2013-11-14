package in.appops.client.common.config.model;

import in.appops.platform.core.entity.Property;

/**
 * @author mahesh@ensarm.com
 *
 */
public interface PropertyReceiver {
	
	public void onPropertyReceived(Property property);

	public void onPropertyUpdated(Property property);
	
}
