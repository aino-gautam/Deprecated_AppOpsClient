package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ConfigurationListDisplayer extends Composite{

	private VerticalPanel basePanel;
	
	public ConfigurationListDisplayer(){
		initialize();
	}

	private void initialize() {
		initWidget(basePanel);
	}
}
