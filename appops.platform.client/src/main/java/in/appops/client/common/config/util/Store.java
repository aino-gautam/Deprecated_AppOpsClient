package in.appops.client.common.config.util;

import in.appops.client.common.util.JsonToEntityConverter;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.i18n.client.Dictionary;

public class Store {
	
    private static Dictionary configStore;
    private static Dictionary cacheStore; 
    
    public static void loadStore() {
    	configStore = Dictionary.getDictionary("pageconfig");
    	cacheStore = Dictionary.getDictionary("pagecache");
    }
 	
	public static Configuration getConfiguration(String configId) {
		try {
			String jsonConfig = configStore.get(configId);
			JsonToEntityConverter convertor = new JsonToEntityConverter();
			Configuration config = (Configuration) convertor.convertjsonStringToEntity(jsonConfig);

			return config;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Configuration getChildConfiguration(String instance, String dataConfig) {
		Configuration parentConfig = getConfiguration(instance);
		return (Configuration) parentConfig.getProperty(dataConfig);
	}
	
	public static Configuration getChildConfiguration(Configuration conf, String dataConfig) {
		return (Configuration) conf.getProperty(dataConfig);
	}
	
	public static Configuration getFromCache(String infoKey) {
		String jsonConfig = cacheStore.get(infoKey);
		JsonToEntityConverter convertor = new JsonToEntityConverter();
		Configuration config = (Configuration) convertor.convertjsonStringToEntity(jsonConfig);

		return config;
	}
	
	
}
