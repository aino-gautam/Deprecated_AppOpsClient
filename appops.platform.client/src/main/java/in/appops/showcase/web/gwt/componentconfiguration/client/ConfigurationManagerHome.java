package in.appops.showcase.web.gwt.componentconfiguration.client;

import in.appops.client.common.config.breadcrumb.BreadcrumbView;
import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.entitydesigner.client.ui.SchemaCreatorSelector;
import in.appops.entitydesigner.client.ui.SchemaManageEditor;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;
import in.appops.showcase.web.gwt.componentconfiguration.client.app.CreateAppWidget;
import in.appops.showcase.web.gwt.componentconfiguration.client.developer.DeveloperHome;
import in.appops.showcase.web.gwt.componentconfiguration.client.library.LibraryComponentManager;
import in.appops.showcase.web.gwt.componentconfiguration.client.page.PageCreation;
import in.appops.showcase.web.gwt.componentconfiguration.client.page.SnippetManager;
import in.appops.showcase.web.gwt.componentconfiguration.client.service.CreateServicePageWidget;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ConfigurationManagerHome extends Composite implements FieldEventHandler,ConfigEventHandler{
	
	private VerticalPanel mainBasePanel;
	private HorizontalPanel basePanel;
	private VerticalPanel contentPanel;
	private HorizontalPanel breadcrumbHolder;
	
	
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
	private static String ENTITYDESIGNER_BTN_ID = "entityDesignerBtnId";
	
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
		breadcrumbHolder = new HorizontalPanel();
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
			
/*			ButtonField entityDesignerBtn = new ButtonField();
			entityDesignerBtn.setConfiguration(getEntityDesignerBtnConfig());
			entityDesignerBtn.configure();
			entityDesignerBtn.create();*/
			
			VerticalPanel toolbar = new VerticalPanel();
			VerticalPanel btnPanel = new VerticalPanel();
			btnPanel.add(createAppBtn);
			btnPanel.add(manageLibBtn);
			btnPanel.add(createSnippetBtn);
			btnPanel.add(createPageBtn);
		//	btnPanel.add(entityDesignerBtn);
			
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
			
			breadcrumbHolder.setStylePrimaryName("breadcrumbCompHolder");

			createStaticBreadcrumb();
			
			mainBasePanel.add(augsHeader);
			mainBasePanel.add(breadcrumbHolder);
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
	
	private void createStaticBreadcrumb() {
		try{
			breadcrumbHolder.clear();
			Entity subOp1 = new Entity();
			subOp1.setPropertyByName("name", "diagrams");
			
			Entity subOp2 = new Entity();
			subOp2.setPropertyByName("name", "entities");
			
			Entity subOp3 = new Entity();
			subOp3.setPropertyByName("name", "new entity");

			Entity subOp4 = new Entity();
			subOp4.setPropertyByName("name", "test data");
			
			Entity subOp5 = new Entity();
			subOp5.setPropertyByName("name", "live data");
			
			Entity subOp6 = new Entity();
			subOp6.setPropertyByName("name", "diagrams");
			
			EntityList subOplist1 = new EntityList();
			subOplist1.add(subOp1);
			subOplist1.add(subOp2);
			subOplist1.add(subOp3);
			subOplist1.add(subOp4);
			subOplist1.add(subOp5);
			subOplist1.add(subOp6);

			Entity opEnt1 = new Entity();
			opEnt1.setPropertyByName("name", "entities");
			opEnt1.setPropertyByName("child", subOplist1);

			
			Entity subOp11 = new Entity();
			subOp11.setPropertyByName("name", "All queries");
			
			Entity subOp12 = new Entity();
			subOp12.setPropertyByName("name", "New query");
			
			EntityList subOpList2 = new EntityList();
			subOpList2.add(subOp11);
			subOpList2.add(subOp12);
			
			Entity opEnt2 = new Entity();
			opEnt2.setPropertyByName("name", "queries");
			opEnt2.setPropertyByName("child", subOpList2);

			
			Entity subOp21 = new Entity();
			subOp21.setPropertyByName("name", "New operation");
			
			Entity subOp22 = new Entity();
			subOp22.setPropertyByName("name", "Entity derived");
			
			Entity subOp23 = new Entity();
			subOp23.setPropertyByName("name", "All operations");
			
			EntityList subOpList3 = new EntityList();
			subOpList3.add(subOp21);
			subOpList3.add(subOp22);
			subOpList3.add(subOp23);
			
			Entity opEntOp = new Entity();
			opEntOp.setPropertyByName("name", "operation");
			opEntOp.setPropertyByName("child", subOpList3);

			
			Entity subOp31 = new Entity();
			subOp31.setPropertyByName("name", "All snippets");
			
			Entity subOp32 = new Entity();
			subOp32.setPropertyByName("name", "New snippet");
			
			Entity subOp33 = new Entity();
			subOp33.setPropertyByName("name", "Snippet instance");
			
			EntityList subOpList4 = new EntityList();
			subOpList4.add(subOp31);
			subOpList4.add(subOp32);
			subOpList4.add(subOp33);
			
			Entity opEnt3 = new Entity();
			opEnt3.setPropertyByName("name", "snippets");
			opEnt3.setPropertyByName("child", subOpList4);

			
			Entity subOp41 = new Entity();
			subOp41.setPropertyByName("name", "New action");
			
			Entity subOp42 = new Entity();
			subOp42.setPropertyByName("name", "Entity derived");
			
			Entity subOp43 = new Entity();
			subOp43.setPropertyByName("name", "All action");
			
			EntityList subOpList5 = new EntityList();
			subOpList5.add(subOp41);
			subOpList5.add(subOp42);
			subOpList5.add(subOp43);
			
			Entity opEnt4 = new Entity();
			opEnt4.setPropertyByName("name", "actions");
			opEnt4.setPropertyByName("child", subOpList5);

			Entity subOp51 = new Entity();
			subOp41.setPropertyByName("name", "New app");
			
			Entity subOp52 = new Entity();
			subOp42.setPropertyByName("name", "All apps");

			
			EntityList subOpList6 = new EntityList();
			subOpList6.add(subOp51);
			subOpList6.add(subOp52);
			
			Entity opEnt5 = new Entity();
			opEnt5.setPropertyByName("name", "apps");
			opEnt5.setPropertyByName("child", subOpList6);

			Entity opEnt6 = new Entity();
			opEnt6.setPropertyByName("name", "spaces");
			
			Entity opEnt7 = new Entity();
			opEnt7.setPropertyByName("name", "admin");
			
			Entity opEnt8 = new Entity();
			opEnt8.setPropertyByName("name", "roles");
			
			Entity opEnt9 = new Entity();
			opEnt9.setPropertyByName("name", "dependencies");
			
			Entity opEnt10 = new Entity();
			opEnt10.setPropertyByName("name", "features and bundles");
			
			EntityList operationList = new EntityList();
			operationList.add(opEnt1);
			operationList.add(opEnt2);
			operationList.add(opEntOp);
			operationList.add(opEnt3);
			operationList.add(opEnt4);
			operationList.add(opEnt5);
			operationList.add(opEnt6);
			operationList.add(opEnt7);
			operationList.add(opEnt8);
			operationList.add(opEnt9);
			operationList.add(opEnt10);
			
			Entity versionEntity = new Entity();
			versionEntity.setPropertyByName("name", "Version 0.1");
			versionEntity.setPropertyByName("child", operationList);
			
			EntityList versionList = new EntityList();
			versionList.add(versionEntity);
			
			Entity serviceEntity = new Entity();
			serviceEntity.setPropertyByName("name", "Blogger");
			serviceEntity.setPropertyByName("child", versionList);

			
			BreadcrumbView serviceBreadCrumb = new BreadcrumbView();
			serviceBreadCrumb.setActionEnt(serviceEntity);
			serviceBreadCrumb.create();
			
			breadcrumbHolder.add(serviceBreadCrumb);
		}
		catch (Exception e) {
			e.printStackTrace();
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

	private Configuration getEntityDesignerBtnConfig() {
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Entity Designer");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,HOME_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, ENTITYDESIGNER_BTN_ID);
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
					else if(btnField.getBaseFieldId().equals(ENTITYDESIGNER_BTN_ID)){
						contentPanel.clear();
						deregisterWidgetHandlers();
						SchemaCreatorSelector schemaSelector = new SchemaCreatorSelector();
						widgetList.add(schemaSelector);

						SchemaManageEditor scmEditor = new SchemaManageEditor(Window.getClientWidth()-40, Window.getClientHeight()-60);
						widgetList.add(scmEditor);
						
						contentPanel.add(schemaSelector);
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
				contentPanel.clear();
				deregisterWidgetHandlers();
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
