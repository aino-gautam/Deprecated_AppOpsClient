/**
 * 
 */
package in.appops.showcase.web.gwt.validateServiceOperation.client;

import in.appops.showcase.web.gwt.validateServiceOperation.client.ui.TestServiceComponent;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author mahesh@ensarm.com
 *
 */
public class ValidateServiceOperationEntryPoint implements EntryPoint{

	@Override
	public void onModuleLoad() {
		/**
		 * For ServiceList
		 */
		RootPanel.get().clear();
		TestServiceComponent component = new TestServiceComponent();
		RootPanel.get().add(component);
	}

}
