package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.platform.core.shared.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LibraryComponentManager extends Composite{
	
	private VerticalPanel basePanel;
	private Logger logger = Logger.getLogger("LibraryComponentManager");
	
	/** Field id**/
	public static final String LIBRARYLISTBOX_ID = "libraryListBoxId";
	
	public LibraryComponentManager() {
		
	}

	public void initialize() {
		try {
			basePanel = new VerticalPanel();
			
			ListBoxField libraryBox = new ListBoxField();
			libraryBox.setConfiguration(getLibraryListBoxConfiguration());
			libraryBox.configure();
			libraryBox.create();
									
			ComponentManager componentManager = new ComponentManager();
			componentManager.createUi();
			
			ConfigurationEditor configurationEditor = new ConfigurationEditor();
			configurationEditor.createUi();
			
			basePanel.add(libraryBox);
			basePanel.add(componentManager);
			basePanel.add(configurationEditor);
			
			initWidget(basePanel);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "LibraryComponentManager :: initialize :: Exception", e);
		}
	}
	
	private Configuration getLibraryListBoxConfiguration() {
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,LIBRARYLISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"appdefinition.AppDefinitionService.getLibraries");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getAllLibraries");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--- Please select a library---");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "LibraryComponentManager :: getLibraryListBoxConfiguration :: Exception", e);
		}
		return configuration;
	}

}
