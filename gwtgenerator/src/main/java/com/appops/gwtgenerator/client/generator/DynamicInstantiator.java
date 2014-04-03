package com.appops.gwtgenerator.client.generator;

import in.appops.platform.core.shared.Configuration;

public class DynamicInstantiator {
	
	public Dynamic getDriverInstance(String correspondingClass, Configuration configuration) throws Exception {
		Dynamic dynamicWidget = (Dynamic) this.getInstance(correspondingClass);
		//dynamicWidget.setPresenter(new Presenter(configuration));
		return dynamicWidget;
	}
	
	public Dynamic getInstance(String correspondingClass) throws Exception {
		Dynamic dynamicWidget = (Dynamic) this.getInstance(correspondingClass);
		return dynamicWidget;
	}
	
	public Driver getDriverInstance(String correspondingClass) throws Exception {
		Driver dynamicWidget = (Driver) this.getDriverInstance(correspondingClass);
		return dynamicWidget;
	}
}
