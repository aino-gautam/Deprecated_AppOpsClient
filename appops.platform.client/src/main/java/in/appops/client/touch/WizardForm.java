package in.appops.client.touch;

import in.appops.client.common.core.EntityModel;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.NavigationEvent;
import in.appops.client.common.event.handlers.NavigationEventHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import java.util.HashMap;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class WizardForm extends Composite implements Form, NavigationEventHandler{
	
	private EntityModel entityModel;
	private HashMap<Integer, Screen> screensMap;
	private Screen currentScreen;
	private SimplePanel panel;
	private Configuration configuration;
	private Navigator navigator;
	private DockPanel dockPanel;
	private Entity entity;
	
	public final static String NAVIGATOR_ALIGNMENT = "navigatorAlignment";
	public final static String ALIGNMENT_TOP ="alignmentTop";
	public final static String ALIGNMENT_BOTTOM = "alignmentBottom";
	public final static String ALIGNMENT_LEFT = "alignmentLeft";
	public final static String ALIGNMENT_RIGHT = "alignementRight";
	public final static String NAVIGATOR_CONFIG = "navigatorConfig";
	
	public WizardForm(){
		dockPanel = new DockPanel();
		panel = new SimplePanel();
		navigator = new Navigator();
		initWidget(dockPanel);
		AppUtils.EVENT_BUS.addHandler(NavigationEvent.TYPE, this);
	}

	/**
	 * adds a screen to the form wizard
	 * @param screen Screen object
	 * @param order Integer order in which the screen should appear
	 */
	public void addScreen(Screen screen, int order){
		if(screensMap == null)
			screensMap = new HashMap<Integer, Screen>();
		
		screensMap.put(order, screen);
	}
	
	/**
	 * initializes form elements and their positions
	 * @throws AppOpsException
	 */
	public void initializeForm() throws AppOpsException  {
		if(getConfiguration() == null)
			throw new AppOpsException("WizardForm configuration unavailable");
		
		
		navigator.setNextWidget(new Label("NEXT"));
		navigator.setPrevWidget(new Label("PREVIOUS"));
		navigator.setTotalScreens(screensMap.size());
		
		Configuration navConfig = getConfiguration().getPropertyByName(NAVIGATOR_CONFIG);
		navigator.setConfiguration(navConfig);
		
		navigator.createNavigatorUI();
		
		if(getConfiguration().getPropertyByName(NAVIGATOR_ALIGNMENT).equals(ALIGNMENT_BOTTOM)){
			dockPanel.add(panel, DockPanel.NORTH);
			dockPanel.add(navigator, DockPanel.SOUTH);
		} else if(getConfiguration().getPropertyByName(NAVIGATOR_ALIGNMENT).equals(ALIGNMENT_TOP)){
			dockPanel.add(navigator, DockPanel.NORTH);
			dockPanel.add(panel, DockPanel.SOUTH);
		}if(getConfiguration().getPropertyByName(NAVIGATOR_ALIGNMENT).equals(ALIGNMENT_RIGHT)){
			dockPanel.add(panel, DockPanel.EAST);
			dockPanel.add(navigator, DockPanel.CENTER);
		}if(getConfiguration().getPropertyByName(NAVIGATOR_ALIGNMENT).equals(ALIGNMENT_LEFT)){
			dockPanel.add(panel, DockPanel.WEST);
			dockPanel.add(navigator, DockPanel.CENTER);
		}
	}
	
	/**
	 * displays a particular screen based on the order provided
	 * @param order Integer order number of the screen which needs to be displayed
	 */
	public void displayScreen(int order){
		if(screensMap != null){
			Screen screen1 = screensMap.get(order-1);
			if(screen1!=null)
			 entity=screen1.populateEntity();
			
			Screen screen = screensMap.get(order);
			screen.setEntity(entity);
			screen.createScreen();
			setCurrentScreen(screen);
			panel.clear();
			Widget widget = (Widget) screen;
			panel.add(widget);
		}
	}
	
	/**
	 * returns an Entity
	 * @return Entity
	 */
	public Entity getEntity() {
		return entityModel.getEntity();
	}

	/**
	 * sets the Entity on the WizardForm
	 * @param entity Entity to be set
	 */
	public void setEntity(Entity entity) {
		this.getEntityModel().setEntity(entity);
	}

	public Screen getCurrentScreen() {
		return currentScreen;
	}

	public void setCurrentScreen(Screen currentScreen) {
		this.currentScreen = currentScreen;
	}

	public EntityModel getEntityModel() {
		return entityModel;
	}

	public void setEntityModel(EntityModel entityModel) {
		this.entityModel = entityModel;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public Form getFormInstance() {
		return this;
	}

	@Override
	public void onNavigation(NavigationEvent event) {
		int eventType =  event.getEventType();
		switch (eventType) {
			case NavigationEvent.GONEXT:{
				displayScreen((Integer)event.getEventData());
				
				break;
			}
			case NavigationEvent.GOPREVIOUS:{
				displayScreen((Integer)event.getEventData());
				
				break;
			}	
		}
		
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

	public DockPanel getDockPanel() {
		return dockPanel;
	}

	public void setDockPanel(DockPanel dockPanel) {
		this.dockPanel = dockPanel;
	}

	public HashMap<Integer, Screen> getScreensMap() {
		return screensMap;
	}

	public void setScreensMap(HashMap<Integer, Screen> screensMap) {
		this.screensMap = screensMap;
	}

	public SimplePanel getPanel() {
		return panel;
	}

	public void setPanel(SimplePanel panel) {
		this.panel = panel;
	}
}