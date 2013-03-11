package in.appops.client.touch;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

/**
 * Represents a screen (view) to be displayed
 * @author nairutee
 *
 */
public interface Screen {
	
	public void createScreen();
	
	public void setConfiguration(Configuration configuration);
	
	public void setEntity(Entity entity);
	
	
}
