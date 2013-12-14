package com.appops.gwtgenerator.client.config.core;

import java.io.Serializable;

/**
 * The class which contains all configuration details for a widget
 * @author nairutee
 *
 */
@SuppressWarnings("serial")
public class Configuration extends Entity {
	
	public Configuration() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Checks whether the configuration has the requested property
	 * @param property
	 * @return
	 */
	public boolean hasConfiguration(String property) {
		if(getPropertyByName(property) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the value for the requested property
	 * @param property
	 * @return
	 */
	public Serializable getConfigurationValue(String property) {
		if(hasConfiguration(property)) {
			return getPropertyByName(property);
		}
		return null;
	}
	

}
