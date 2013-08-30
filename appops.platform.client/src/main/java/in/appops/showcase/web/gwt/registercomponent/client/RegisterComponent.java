package in.appops.showcase.web.gwt.registercomponent.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class RegisterComponent implements EntryPoint {

	@Override
	public void onModuleLoad() {
		try{
			RegisterComponentManager compManager = new RegisterComponentManager();
			compManager.createUi();
			RootPanel.get().add(compManager);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}