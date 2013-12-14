package com.appops.gwtgenerator.client.config.cache;

import java.util.HashMap;

import com.appops.gwtgenerator.client.config.core.Entity;
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
	
}
