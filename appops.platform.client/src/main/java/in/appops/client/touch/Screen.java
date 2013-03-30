package in.appops.client.touch;

import com.google.gwt.user.client.ui.IsWidget;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

/**
 * Represents a screen (view) to be displayed
 * @author nairutee
 *
 */
public interface Screen extends IsWidget{
	
	public void createScreen();
	
	public void setConfiguration(Configuration configuration);
	
	public void setEntity(Entity entity);
	
	public boolean validate();
	
	public Entity populateEntity();
	
	
}