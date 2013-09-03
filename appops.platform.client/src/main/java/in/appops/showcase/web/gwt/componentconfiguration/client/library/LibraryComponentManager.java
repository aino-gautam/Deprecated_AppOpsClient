package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LibraryComponentManager extends Composite {
	
	private VerticalPanel basePanel;
	
	/** CSS styles used ****/
	private final String LIBPANEL_CSS = "libraryPanel";
	private final String HEADERLBL_CSS = "componentSectionHeaderLbl";
	
	
	public LibraryComponentManager() {
		initialize();
	}

	private void initialize() {
		basePanel = new VerticalPanel();
		
		HorizontalPanel libraryPanel = new HorizontalPanel();
		ListBoxField libraryBox = new ListBoxField();
		libraryBox.setConfiguration(getLibraryListBoxConfiguration());
		libraryBox.configure();
		libraryBox.create();
		

		LabelField libHeaderLbl = new LabelField();
		Configuration headerLblConfig = getHeaderLblConfig();
	
		libHeaderLbl.setConfiguration(headerLblConfig);
		libHeaderLbl.configure();
		libHeaderLbl.create();
		
		libraryPanel.add(libHeaderLbl);
		libraryPanel.add(libraryBox);
		libraryPanel.setStylePrimaryName(LIBPANEL_CSS);
		
		ComponentManager componentManager = new ComponentManager();
		componentManager.createUi();
		
		ConfigurationEditor configurationEditor = new ConfigurationEditor();
		configurationEditor.createUi();
		
		basePanel.add(libraryPanel);
		basePanel.add(componentManager);
		basePanel.add(configurationEditor);
		
		initWidget(basePanel);
	}
	
	private Configuration getHeaderLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Select Library: ");
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}
	
	private Configuration getLibraryListBoxConfiguration() {
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,LIBRARYBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"appdefinition.AppDefinitionService.getLibraries");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getAllLibraries");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
			//configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"Select library");
		} catch (Exception e) {
			
		}
		return configuration;
	}
}
