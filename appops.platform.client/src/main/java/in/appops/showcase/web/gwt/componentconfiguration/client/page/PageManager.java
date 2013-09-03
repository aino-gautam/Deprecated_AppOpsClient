package in.appops.showcase.web.gwt.componentconfiguration.client.page;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PageManager extends Composite {
	
	private VerticalPanel basePanel;

	public PageManager() {
		
	}

	public void initialize() {
		basePanel = new VerticalPanel();
		initWidget(basePanel);
	}
}
