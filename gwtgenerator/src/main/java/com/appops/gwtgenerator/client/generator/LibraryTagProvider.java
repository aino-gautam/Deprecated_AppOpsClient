package com.appops.gwtgenerator.client.generator;

import java.util.HashMap;


public class LibraryTagProvider {
	
	private HashMap<String, TagInstanceProvider>	lib_tagProvider_map	= new HashMap<String, TagInstanceProvider>();
	
	public LibraryTagProvider() {
		// TODO Auto-generated constructor stub
	}
	
	public Object getInstance(String library, String tagName) throws Exception {
		try {
			TagInstanceProvider tagProvider = lib_tagProvider_map.get(tagName);
			return tagProvider.getInstance(tagName);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void add(String library, String tagName, String className) {
		TagInstanceProvider provider = lib_tagProvider_map.get(library);
		if (provider == null) {
			provider = new TagInstanceProvider();
			lib_tagProvider_map.put(tagName, provider);
		}
		provider.add(tagName, className);
	}
	
	/*public void add(String library, String tagName, Class className) {
		TagInstanceProvider provider = lib_tagProvider_map.get(library);
		if (provider == null) {
			provider = new TagInstanceProvider();
			lib_tagProvider_map.put(tagName, provider);
		}
		provider.add(tagName, className.getCanonicalName());
	}*/
}
