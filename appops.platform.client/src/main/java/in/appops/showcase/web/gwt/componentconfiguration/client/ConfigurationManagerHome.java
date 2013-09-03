package in.appops.showcase.web.gwt.componentconfiguration.client;

import in.appops.showcase.web.gwt.componentconfiguration.client.library.LibraryComponentManager;
import in.appops.showcase.web.gwt.componentconfiguration.client.page.PageManager;

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
		//library and page two tabs on left side.
		
		LibraryComponentManager libraryComponentManager = new LibraryComponentManager();
		libraryComponentManager.initialize();
		
		PageManager pageManager = new PageManager();
		pageManager.initialize();
		
		basePanel.add(libraryComponentManager);
		basePanel.add(pageManager);
		
	}

}
