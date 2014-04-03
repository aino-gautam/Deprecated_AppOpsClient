package com.appops.gwtgenerator.client.config;

import com.appops.gwtgenerator.client.config.cache.ConfigCache;
import com.appops.gwtgenerator.client.generator.LibraryTagProvider;
import com.google.gwt.core.client.GWT;

/**
 * Holds the populated library tag provider
 *
 */
public class ContextConfigurationManager {
	public static LibraryTagProvider	LIBRARY_TAG_PROVIDER	= GWT.create(LibraryTagProvider.class);
	public static ConfigCache			CONFIG_CACHE			= GWT.create(ConfigCache.class);
	
	public static LibraryTagProvider getLIBRARY_TAG_PROVIDER() {
		return LIBRARY_TAG_PROVIDER;
	}
	
	public static void setLIBRARY_TAG_PROVIDER(LibraryTagProvider lIBRARY_TAG_PROVIDER) {
		LIBRARY_TAG_PROVIDER = lIBRARY_TAG_PROVIDER;
	}
	
	public static ConfigCache getConfigCache() {
		return new ConfigCache();
	}
	
}
