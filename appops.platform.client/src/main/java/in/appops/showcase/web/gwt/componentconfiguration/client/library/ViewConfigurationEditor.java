/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com
 *
 */
public class ViewConfigurationEditor extends Composite{

	private VerticalPanel basePanel;
	
	public ViewConfigurationEditor(){
		initialize();
	}

	private void initialize() {
		try{
			basePanel = new VerticalPanel();
			initWidget(basePanel);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
