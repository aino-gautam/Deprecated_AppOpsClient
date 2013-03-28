/**
 * 
 */
package in.appops.showcase.web.gwt.facebook.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author mahesh@ensarm.com
 *
 */
public class FacebookTest implements EntryPoint {

	@Override
	public void onModuleLoad() {
		try{
			
			String clientId = "478704968863629";
			String serviceId = "11";
			AppopsFacebookWidget widget = new AppopsFacebookWidget(clientId,serviceId);
			RootPanel.get().add(widget);
			
	/*		SearchWidgetTest searchWidget = new SearchWidgetTest();
			RootPanel.get().add(searchWidget);*/
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
