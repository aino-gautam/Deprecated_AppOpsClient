package com.appops.gwtgenerator.client.component.factory;

import com.appops.gwtgenerator.client.config.ContextConfigurationManager;
import com.appops.gwtgenerator.client.generator.Dynamic;
import com.appops.gwtgenerator.client.generator.LibraryTagProvider;

public class CoreComponentFactory {
	public static Dynamic getTagInstance(String tag) throws Exception {
		LibraryTagProvider libraryTagProvider = ContextConfigurationManager.getLIBRARY_TAG_PROVIDER();
		//split tag name
		String[] splitarray = tag.split(":");
		Dynamic widget = libraryTagProvider.getInstance(splitarray[0].toLowerCase(), splitarray[1].toLowerCase());
		return widget;
	}
}
