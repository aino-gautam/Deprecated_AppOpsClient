package com.appops.gwtgenerator.client.config.cache;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

import java.util.HashMap;

import com.appops.gwtgenerator.client.config.util.JsonToEntityConverter;
import com.google.gwt.i18n.client.Dictionary;

/**
 * Will cache the configuration entities that are loaded with the App
 * @author Administrator
 *
 */
public class ConfigCache {
	private HashMap<String, Entity>						id_config_map		= new HashMap<String, Entity>();
	private HashMap<String, HashMap<String, Entity>>	type_id_config_map	= new HashMap<String, HashMap<String, Entity>>();
	
	private static Dictionary							configDictonary;
	
	public static void loadSnippetDesc() {
		configDictonary = Dictionary.getDictionary("config");
	}
	
	public ConfigCache() {
	}
	
	public static Configuration getConfig(String configID) {
		loadSnippetDesc();
		String snippetDesc = configDictonary.get(configID);
		Configuration config = null;
		try {
			config = convertToConfig(snippetDesc);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}
	
	private static Configuration convertToConfig(String config) {
		JsonToEntityConverter converter = new JsonToEntityConverter();
		Configuration configuration = (Configuration) converter.convertjsonStringToEntity(config);
		return configuration;
	}
	
}
