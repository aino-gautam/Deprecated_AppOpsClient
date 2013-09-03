package in.appops.showcase.web.gwt.componentconfiguration.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ConfigurationManagerHome extends Composite{
	
	private VerticalPanel basePanel;
	
	public ConfigurationManagerHome() {
		initialize();
	}

	private void initialize() {
		basePanel = new VerticalPanel();
		initWidget(basePanel);
	}
	
	public void createUi(){
		
	}

}
