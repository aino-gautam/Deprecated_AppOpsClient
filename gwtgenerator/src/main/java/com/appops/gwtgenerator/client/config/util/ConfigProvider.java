package com.appops.gwtgenerator.client.config.util;

import in.appops.client.common.util.JsonToEntityConverter;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.i18n.client.Dictionary;

public class ConfigProvider {
	
    private static Dictionary dictionary;
    
    public static void loadConfiguration() {
    	dictionary = Dictionary.getDictionary("pageconfig");
    }
 	
	public static Configuration getConfiguration(String configId) {
		String jsonConfig = dictionary.get(configId);
		JsonToEntityConverter convertor = new JsonToEntityConverter();
		Configuration config = (Configuration) convertor.convertjsonStringToEntity(jsonConfig);

		return config;
	}

	public static Configuration getChildConfiguration(String instance, String dataConfig) {
		Configuration parentConfig = getConfiguration(instance);
		return (Configuration) parentConfig.getProperty(dataConfig);
	}
	
	public static Configuration getChildConfiguration(Configuration conf, String dataConfig) {
		return (Configuration) conf.getProperty(dataConfig);
	}
}
