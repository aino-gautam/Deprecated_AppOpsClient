package com.appops.gwtgenerator.client.generator;

import java.util.HashMap;

import com.appops.gwtgenerator.client.config.core.Configuration;
import com.google.gwt.core.client.GWT;

public class TagInstanceProvider {
	
	private HashMap<String, String>	tag_Class_map	= new HashMap<String, String>();
	private DynamicInstantiator		instantiator	= GWT.create(DynamicInstantiator.class);
	
	public Dynamic getInstance(String tagName) throws Exception {
		try {
			String correspondingClass = tag_Class_map.get(tagName);
			return instantiator.getMVP(correspondingClass,new Configuration());
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public void add(String tagName, String className) {
		this.tag_Class_map.put(tagName, className);
	}
}
