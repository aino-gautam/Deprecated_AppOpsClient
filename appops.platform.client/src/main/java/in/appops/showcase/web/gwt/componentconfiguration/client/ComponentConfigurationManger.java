package in.appops.showcase.web.gwt.componentconfiguration.client;

import in.appops.showcase.web.gwt.componentconfiguration.client.developer.DeveloperHome;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class ComponentConfigurationManger implements EntryPoint {

	@Override
	public void onModuleLoad() {
		try{
			ConfigurationManagerHome confManagerHome = new ConfigurationManagerHome();
			confManagerHome.createUi();
			RootPanel.get().add(confManagerHome);
			/*DeveloperHome devHome= new DeveloperHome();
			devHome.initialize();
			devHome.createUI();
			RootPanel.get().add(devHome);
			devHome.getServiceRecords();*/
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}