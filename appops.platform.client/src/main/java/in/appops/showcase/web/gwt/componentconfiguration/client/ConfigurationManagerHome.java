package in.appops.showcase.web.gwt.componentconfiguration.client;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;
import in.appops.showcase.web.gwt.componentconfiguration.client.app.CreateAppWidget;
import in.appops.showcase.web.gwt.componentconfiguration.client.developer.DeveloperHome;
import in.appops.showcase.web.gwt.componentconfiguration.client.library.LibraryComponentManager;
import in.appops.showcase.web.gwt.componentconfiguration.client.page.PageCreation;
import in.appops.showcase.web.gwt.componentconfiguration.client.page.SnippetManager;
import in.appops.showcase.web.gwt.componentconfiguration.client.service.CreateServicePageWidget;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ConfigurationManagerHome extends Composite implements FieldEventHandler,ConfigEventHandler{
	
	private VerticalPanel mainBasePanel;
	private HorizontalPanel basePanel;
	private VerticalPanel contentPanel;
	private Logger logger = Logger.getLogger("ConfigurationManagerHome");
	private ArrayList<Widget> widgetList  = null;
	private AugsHeader augsHeader ;
	
	/** CSS styles used **/
	private static String HOME_BTN_PCLS = "managerHomeButton";
	private static String TOOLBAR_PCLS = "buttonToolbar";
	private static String CONTENTPANEL_PCLS = "contentPanel";
	private static String BASEPANEL_PCLS = "managerHomePanel";
	
	/** Field id **/
	private static String MANAGELIB_BTN_ID = "manageLibBtnId";
	private static String CREATEPAGE_BTN_ID = "createPageBtnId";
	private static String CREATESNIPPET_BTN_ID = "createSnippetBtnId";
	private static String CREATEAPP_BTN_ID = "createAppBtnId";
	
	public ConfigurationManagerHome() {
		initialize();
	}

	private void initialize() {
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);

		basePanel = new HorizontalPanel();
		mainBasePanel = new VerticalPanel();
		initWidget(mainBasePanel);
		augsHeader = new AugsHeader();
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
			
			ButtonField createAppBtn = new ButtonField();
			createAppBtn.setConfiguration(getCreateAppBtnConf());
			createAppBtn.configure();
			createAppBtn.create();
			
			VerticalPanel toolbar = new VerticalPanel();
			VerticalPanel btnPanel = new VerticalPanel();
			btnPanel.add(createAppBtn);
			btnPanel.add(manageLibBtn);
			btnPanel.add(createSnippetBtn);
			btnPanel.add(createPageBtn);
		
			toolbar.add(btnPanel);
			
			if(widgetList == null)
				widgetList = new ArrayList<Widget>();
			
			contentPanel = new VerticalPanel();

			augsHeader.createUi();
			
			basePanel.add(toolbar);
			basePanel.add(contentPanel);
			
			toolbar.setStylePrimaryName(TOOLBAR_PCLS);
			contentPanel.setStylePrimaryName(CONTENTPANEL_PCLS);
			basePanel.setStylePrimaryName(BASEPANEL_PCLS);
			
			mainBasePanel.add(augsHeader);
			mainBasePanel.add(basePanel);
			mainBasePanel.setWidth("100%");
			
			DeveloperHome devHome= new DeveloperHome();
			devHome.initialize();
			devHome.createUI();
			devHome.getServiceRecords();
			contentPanel.add(devHome);
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ConfigurationManagerHome :: createUi :: Exception", e);
		}
		
	}
	
	/**
	 * This method for create configuration for the create app button field
	 * @return
	 */
	private Configuration getCreateAppBtnConf() {
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Create app");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,HOME_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, CREATEAPP_BTN_ID);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ConfigurationManagerHome :: getCreateAppBtnConf :: Exception", e);
		}
		return configuration;
	}

	/**
	 * Creates the library button configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getLibraryBtnConf(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Manage Components");
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
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Create page");
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
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Create snippet");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,HOME_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, CREATESNIPPET_BTN_ID);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ConfigurationManagerHome :: getCreateSnippetBtnConf :: Exception", e);
		}
		return configuration;
	}
	
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
						deregisterWidgetHandlers();
						LibraryComponentManager libraryComponentManager = new LibraryComponentManager();
						libraryComponentManager.initialize();
						widgetList.add(libraryComponentManager);
						contentPanel.add(libraryComponentManager);
					}else if (btnField.getBaseFieldId().equals(CREATEPAGE_BTN_ID)) {
						contentPanel.clear();
						deregisterWidgetHandlers();
						PageCreation pageCreation = new PageCreation();
						widgetList.add(pageCreation);
						contentPanel.add(pageCreation);
					}else if (btnField.getBaseFieldId().equals(CREATESNIPPET_BTN_ID)) {
						contentPanel.clear();
						deregisterWidgetHandlers();
						SnippetManager snippetManager = new SnippetManager();
						snippetManager.initialize();
						widgetList.add(snippetManager);
						contentPanel.add(snippetManager);
					}else if(btnField.getBaseFieldId().equals(CREATEAPP_BTN_ID)){
						contentPanel.clear();
						deregisterWidgetHandlers();
						CreateAppWidget createAppWidget = new CreateAppWidget();
						createAppWidget.createUi();
						widgetList.add(createAppWidget);
						contentPanel.add(createAppWidget);
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
	
	
	private void deregisterWidgetHandlers(){
		for(Widget w : widgetList){
			if(w instanceof LibraryComponentManager){
				((LibraryComponentManager)w).deregisterHandler();
			}else if(w instanceof PageCreation){
				((PageCreation)w).deregisterHandler();
			}else if(w instanceof SnippetManager){
				((SnippetManager)w).deregisterHandler();
			}else if(w instanceof CreateAppWidget){
				((CreateAppWidget)w).deregisterHandler();
			}else if(w instanceof CreateServicePageWidget){
				((CreateServicePageWidget)w).deregisterHandler();
			}
		}
		
		widgetList.clear();
	}

	@Override
	public void onConfigEvent(ConfigEvent event) {
		try{
			if(event.getEventType() == ConfigEvent.CREATENEWSERVICE){
				contentPanel.clear();
				deregisterWidgetHandlers();
				CreateServicePageWidget createServicePageWidget = new CreateServicePageWidget();
				createServicePageWidget.createUi();
				widgetList.add(createServicePageWidget);
				contentPanel.add(createServicePageWidget);
			}
			else if(event.getEventType() == ConfigEvent.AUGSHOME){
				DeveloperHome devHome= new DeveloperHome();
				devHome.initialize();
				devHome.createUI();
				devHome.getServiceRecords();
				contentPanel.add(devHome);
			}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "ConfigurationManagerHome :: onConfigEvent :: Exception", e);
		}
	}

}
