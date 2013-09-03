package in.appops.showcase.web.gwt.componentconfiguration.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class ComponentConfigurationManger implements EntryPoint {

	@Override
	public void onModuleLoad() {
		try{
			ConfigurationManagerHome confManagerHome = new ConfigurationManagerHome();
			confManagerHome.createUi();
			RootPanel.get().add(confManagerHome);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}