package com.appops.gwtgenerator.client.generator;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;

public class TagInstanceProvider {
	
	private HashMap<String, String>	tag_Class_map	= new HashMap<String, String>();
	// GWT.create needs a class literal but class.forname is not supported at run time in GWT
		public Object getInstance(String tagName) throws Exception {
			try {
				String correspondingClassName = tag_Class_map.get(tagName);
				// may not work as some classes may not have default / default public constructors
				// should be using the instance factory to get the class instance created with the help of configurations
				//return GWT.create(correspondingClassName);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return null;
			
		}
	
	public void add(String tagName, String className) {
		this.tag_Class_map.put(tagName, className);
	}
	
	/*public void add(String tagName, Class className) {
		this.tag_Class_map.put(tagName, className.getCanonicalName());
	}*/
}
