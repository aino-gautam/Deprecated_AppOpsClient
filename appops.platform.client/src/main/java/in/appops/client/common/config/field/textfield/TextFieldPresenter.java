/**
 * 
 */
package in.appops.client.common.config.field.textfield;

import in.appops.client.common.config.model.PropertyReceiver;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

/**
 * @author mahesh@ensarm.com
 *
 */
public class TextFieldPresenter implements Configurable, PropertyReceiver{

	private Configuration configuration;
	
	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		configuration = conf;
	}

	@Override
	public void onPropertyReceived(Property property) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPropertyUpdated(Property property) {
		// TODO Auto-generated method stub
	}

}
