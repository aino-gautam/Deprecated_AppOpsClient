package com.appops.gwtgenerator.client.generator;

public class DynamicInstantiator {
	
	public Dynamic getInstance(String correspondingClass) throws Exception {
		Dynamic dynamicWidget = (Dynamic) this.getInstance(correspondingClass);
		return dynamicWidget;
	}
}
