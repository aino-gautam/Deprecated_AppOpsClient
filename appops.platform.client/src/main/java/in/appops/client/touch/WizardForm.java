package in.appops.client.touch;

import java.util.HashMap;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

import in.appops.client.common.core.EntityModel;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

public class WizardForm extends Composite implements Form{
	
	private EntityModel entityModel;
	private HashMap<Integer, Screen> screensMap;
	private Screen currentScreen;
	private SimplePanel panel;
	private Configuration configuration;
	
	public WizardForm(){
		panel = new SimplePanel();
		initWidget(panel);
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
	 * displays a particular screen based on the order provided
	 * @param order Integer order number of the screen which needs to be displayed
	 */
	public void displayScreen(int order){
		if(screensMap != null){
			Screen screen = screensMap.get(order);
			screen.createScreen();
			setCurrentScreen(screen);
			panel.clear();
			panel.setWidget(screen);
		}
	}
	
	public void goToNextScreen(){
		
	}
	
	public void goToPreviousScreen(){
		
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
}