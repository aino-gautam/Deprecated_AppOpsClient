package com.appops.gwtgenerator.client.config.cache;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

import java.util.HashMap;
/**
 * Will cache the configuration entities that are loaded with the App
 * @author Administrator
 *
 */
public class ConfigCache {
	private HashMap<String, Entity>						id_config_map		= new HashMap<String, Entity>();
	private HashMap<String, HashMap<String, Entity>>	type_id_config_map	= new HashMap<String, HashMap<String, Entity>>();

	
	
	public ConfigCache() {
	}



	public Configuration getConfig(String configID) {
		return null;
		
	}
	
}
