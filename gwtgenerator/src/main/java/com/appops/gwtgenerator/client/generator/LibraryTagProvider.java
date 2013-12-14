package com.appops.gwtgenerator.client.generator;

import java.util.HashMap;

public class LibraryTagProvider {
	
	private HashMap<String, TagInstanceProvider>	lib_tagProvider_map	= new HashMap<String, TagInstanceProvider>();
	
	public Dynamic getInstance(String library, String tagName) throws Exception {
		try {
			TagInstanceProvider tagProvider = lib_tagProvider_map.get(library);
			return tagProvider.getInstance(tagName);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void add(String library, String tagName, String clazz) {
		library = library.toLowerCase();
		tagName = tagName.toLowerCase();
		TagInstanceProvider provider = lib_tagProvider_map.get(library);
		if (provider == null) {
			provider = new TagInstanceProvider();
			lib_tagProvider_map.put(library, provider);
		}
		provider.add(tagName, clazz);
	}
}
