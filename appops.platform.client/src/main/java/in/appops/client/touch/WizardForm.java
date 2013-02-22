package in.appops.client.touch;

import java.util.HashMap;

import in.appops.platform.core.entity.Entity;

public class WizardForm {
	
	private Entity entity;
	private HashMap<Integer, Screen> screensMap;
	
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
	 * removes the screen of the particular order
	 * @param order Integer order no of the screen
	 */
	public void removeScreen(int order){
		if(screensMap != null)
			screensMap.remove(order);
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
}
