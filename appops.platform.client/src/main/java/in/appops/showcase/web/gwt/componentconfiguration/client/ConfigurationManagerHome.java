package in.appops.showcase.web.gwt.componentconfiguration.client;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;
import in.appops.showcase.web.gwt.componentconfiguration.client.library.LibraryComponentManager;
import in.appops.showcase.web.gwt.componentconfiguration.client.page.PageManager;
import in.appops.showcase.web.gwt.componentconfiguration.client.page.SnippetManager;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ConfigurationManagerHome extends Composite implements FieldEventHandler{
	
	private HorizontalPanel basePanel;
	private VerticalPanel contentPanel;
	private Logger logger = Logger.getLogger("ConfigurationManagerHome");
	
	/** CSS styles used **/
	private static String HOME_BTN_PCLS = "managerHomeButton";
	private static String HOME_BTN_DCLS = "managerHomeSelectedButton";
	private static String TOOLBAR_PCLS = "buttonToolbar";
	private static String CONTENTPANEL_PCLS = "contentPanel";
	private static String BASEPANEL_PCLS = "managerHomePanel";
	
	/** Field id **/
	private static String MANAGELIB_BTN_ID = "manageLibBtnId";
	private static String CREATEPAGE_BTN_ID = "createPageBtnId";
	private static String CREATESNIPPET_BTN_ID = "createSnippetBtnId";
	
	public ConfigurationManagerHome() {
		initialize();
	}

	private void initialize() {
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		basePanel = new HorizontalPanel();
		initWidget(basePanel);
	}
	
	public void createUi(){
				
		try {
			ButtonField manageLibBtn = new ButtonField();
			manageLibBtn.setConfiguration(getLibraryBtnConf());
			manageLibBtn.configure();
			manageLibBtn.create();
			
			ButtonField createPageBtn = new ButtonField();
			createPageBtn.setConfiguration(getCreatePageBtnConf());
			createPageBtn.configure();
			createPageBtn.create();
			
			ButtonField createSnippetBtn = new ButtonField();
			createSnippetBtn.setConfiguration(getCreateSnippetBtnConf());
			createSnippetBtn.configure();
			createSnippetBtn.create();
			
			VerticalPanel toolbar = new VerticalPanel();
			VerticalPanel btnPanel = new VerticalPanel();
			btnPanel.add(manageLibBtn);
			btnPanel.add(createPageBtn);
			btnPanel.add(createSnippetBtn);
			toolbar.add(btnPanel);
			
			LibraryComponentManager libraryComponentManager = new LibraryComponentManager();
			libraryComponentManager.initialize();
								
			contentPanel = new VerticalPanel();
			contentPanel.add(libraryComponentManager);
			basePanel.add(toolbar);
			basePanel.add(contentPanel);
			
			toolbar.setStylePrimaryName(TOOLBAR_PCLS);
			contentPanel.setStylePrimaryName(CONTENTPANEL_PCLS);
			basePanel.setStylePrimaryName(BASEPANEL_PCLS);
			
			int width = Window.getClientWidth() - 100;
			int height = Window.getClientHeight() - 120;
			
			int toolBarWidth = (width/7);
			toolbar.setSize(toolBarWidth+"px", height+"px");
			basePanel.setSize((width-toolBarWidth)+"px", height+"px");
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ConfigurationManagerHome :: createUi :: Exception", e);
		}
		
	}
	
	/**
	 * Creates the library button configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getLibraryBtnConf(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Manage library");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,HOME_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, MANAGELIB_BTN_ID);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ConfigurationManagerHome :: getLibraryBtnConf :: Exception", e);
		}
		return configuration;
	}
	
	/**
	 * Creates the create page button configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getCreatePageBtnConf(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Create a page");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,HOME_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, CREATEPAGE_BTN_ID);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ConfigurationManagerHome :: getCreatePageBtnConf :: Exception", e);
		}
		return configuration;
	}
	
	/**
	 * Creates the create snippet button configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getCreateSnippetBtnConf(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Create a snippet");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,HOME_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, CREATESNIPPET_BTN_ID);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ConfigurationManagerHome :: getCreateSnippetBtnConf :: Exception", e);
		}
		return configuration;
	}
	
	/**
	 * Method add dependent css property to the existing configuration and return.
	 * @param configuration
	 * @return modified configuration instance.
	 *//*
	private Configuration getSelectedBtnConf(Configuration configuration){
		
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BF_DCLS,HOME_BTN_DCLS);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ConfigurationManagerHome :: getLibraryBtnConf :: Exception", e);
		}
		return configuration;
	}

	*//**
	 * Method removes dependent css property from the existing configuration and return.
	 * @param configuration
	 * @return modified configuration instance.
	 *//*
	private Configuration getDeselectedBtnConf(Configuration configuration){
		
		try {
			configuration.getValue().remove(ButtonFieldConstant.BF_DCLS);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ConfigurationManagerHome :: getLibraryBtnConf :: Exception", e);
		}
		return configuration;
	}*/
	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case FieldEvent.CLICKED: {
				if (eventSource instanceof ButtonField) {
					ButtonField btnField = (ButtonField) eventSource;
					if (btnField.getBaseFieldId().equals(MANAGELIB_BTN_ID)) {
						contentPanel.clear();
						LibraryComponentManager libraryComponentManager = new LibraryComponentManager();
						libraryComponentManager.initialize();
						contentPanel.add(libraryComponentManager);
					}else if (btnField.getBaseFieldId().equals(CREATEPAGE_BTN_ID)) {
						contentPanel.clear();
						PageManager pageManager = new PageManager();
						pageManager.initialize();
						contentPanel.add(pageManager);
					}else if (btnField.getBaseFieldId().equals(CREATESNIPPET_BTN_ID)) {
						contentPanel.clear();
						SnippetManager snippetManager = new SnippetManager();
						snippetManager.initialize();
						contentPanel.add(snippetManager);
					}
				}
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ConfigurationManagerHome :: onFieldEvent :: Exception", e);
		}
	}

}
