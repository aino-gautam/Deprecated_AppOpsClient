package com.appops.gwtgenerator.client.config.cache;

import in.appops.platform.core.entity.Entity;

import java.util.HashMap;

public class ConfigCache {
	private HashMap<String, Entity>						id_config_map		= new HashMap<String, Entity>();
	private HashMap<String, HashMap<String, Entity>>	type_id_config_map	= new HashMap<String, HashMap<String, Entity>>();

	
	
	public ConfigCache() {
	}
	
}
