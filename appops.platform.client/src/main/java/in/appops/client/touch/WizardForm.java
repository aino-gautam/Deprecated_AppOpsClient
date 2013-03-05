package in.appops.client.touch;

import java.util.HashMap;

import in.appops.platform.core.entity.Entity;

public class WizardForm {
	
	private Entity entity;
	private HashMap<Integer, Screen> screensMap;
	private Screen currentScreen;
	
	public WizardForm(){
		
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
		}
	}
	
	/**
	 * returns an Entity
	 * @return Entity
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * sets the Entity on the WizardForm
	 * @param entity Entity to be set
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Screen getCurrentScreen() {
		return currentScreen;
	}

	public void setCurrentScreen(Screen currentScreen) {
		this.currentScreen = currentScreen;
	}
}
