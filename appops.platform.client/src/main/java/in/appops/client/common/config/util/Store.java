package in.appops.client.common.config.util;

import in.appops.client.common.util.JsonToEntityConverter;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.i18n.client.Dictionary;

/**
 * This class stores all the configuration for a page, description of snippets used in a page() and
 * data that is pre-populated for a page.
 * @author nitish@ensarm.com
 */
public class Store {
	
	/** To store configuration	**/
    private static Dictionary configStore;
    
    /** To store pre-populated data	**/
    private static Dictionary cacheStore; 
    
    /** To store snippets used in a page **/
    private static Dictionary snippetStore;
    
    /** Constants **/
    private final static String PAGECONFIG = "pageconfig";
    private final static String PAGECACHE = "pagecache";
    private final static String PAGESNIPPET = "snippetStore";

    /**
     * This methods populates the respective store
     */
    public static void loadStore() {
    	configStore = Dictionary.getDictionary(PAGECONFIG);
    	cacheStore = Dictionary.getDictionary(PAGECACHE);
    	snippetStore = Dictionary.getDictionary(PAGESNIPPET);
    }
 	
	public static Configuration getFromConfigurationStore(String configId) {
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
	
	/**
	 * Returns the configuration based on the context path.
	 * E.g. - We want to get the configuration for S5, for path S1.S2.S3.S4.S5
	 * We first fetch the configuration for S1 (i.e. the parent configuration)
	 * From the parent configuration we fetch S5 by getGraphPropertyValue("S2.S3.S4.S5").
	 * @param contextPath
	 * @return
	 */
	public static Configuration getContextConfiguration(String contextPath) {
		try {
			// Context Path eg S1.S2.S3.S4.S5
			
			String parentConfigStr = contextPath.substring(0, contextPath.indexOf(".")); // Returns S1
			String childConfigStr = contextPath.substring(contextPath.indexOf(".") + 1); // Returns S2.S3.S4.S5
			
			String jsonConfig = configStore.get(parentConfigStr); // Returns configuration for S1
			JsonToEntityConverter convertor = new JsonToEntityConverter();
			Configuration parentConfig = (Configuration) convertor.convertjsonStringToEntity(jsonConfig);
			
			Configuration contextConfig = parentConfig.getGraphPropertyValue(childConfigStr, null); // Returns configuration for S5 from parent configuration S1 using S2.S3.S4.S5 
			return contextConfig;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public static Configuration getChildConfiguration(String instance, String dataConfig) {
		Configuration parentConfig = getFromConfigurationStore(instance);
		return (Configuration) parentConfig.getProperty(dataConfig);
	}
	
	public static Configuration getChildConfiguration(Configuration conf, String dataConfig) {
		return (Configuration) conf.getProperty(dataConfig);
	}
	
	public static Configuration getFromCacheStore(String key) {
		try {
			String jsonConfig = cacheStore.get(key);
			JsonToEntityConverter convertor = new JsonToEntityConverter();
			Configuration config = (Configuration) convertor.convertjsonStringToEntity(jsonConfig);

			return config;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getFromSnippetStore(String snippetType) {
		try {
			String snippetDesc = snippetStore.get(snippetType);
			return snippetDesc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
