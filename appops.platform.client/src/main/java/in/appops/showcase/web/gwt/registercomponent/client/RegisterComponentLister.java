/**
 * 
 */
package in.appops.showcase.web.gwt.registercomponent.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author root
 *
 */
public class RegisterComponentLister extends Composite{

	private VerticalPanel basePanel;
	
	public RegisterComponentLister(){
		initialize();
		createUi();
	}

	private void createUi() {
		
	}

	private void initialize() {
		basePanel = new VerticalPanel();
		initWidget(basePanel);
	}
}
