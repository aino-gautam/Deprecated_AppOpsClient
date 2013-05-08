package in.appops.showcase.web.gwt.messagehome.client;

import in.appops.client.common.components.MessagesHomeWidget;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class MessageHomeEntry implements EntryPoint{

	@Override
	public void onModuleLoad() {
		MessagesHomeWidget homeWidget = new MessagesHomeWidget();
		
		/*try {
			homeWidget.createComponent();
		} catch (AppOpsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		RootPanel.get().add(homeWidget); 
		RootPanel.get().setSize("100%","100%");
		RootPanel.get().setWidgetPosition(homeWidget, 400, 50);
		
		
	}

}
