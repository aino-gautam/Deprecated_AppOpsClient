package com.appops.gwtgenerator.client.generator;

import in.appops.platform.core.shared.Configuration;

import com.appops.gwtgenerator.client.component.presenter.Presenter;

public class DynamicInstantiator {
	
	public Dynamic getMVP(String correspondingClass, Configuration configuration) throws Exception {
		Dynamic dynamicWidget = (Dynamic) this.getInstance(correspondingClass);
		dynamicWidget.setPresenter(new Presenter(configuration));
		return dynamicWidget;
	}
	
	public Dynamic getInstance(String correspondingClass) throws Exception {
		Dynamic dynamicWidget = (Dynamic) this.getInstance(correspondingClass);
		return dynamicWidget;
	}
}
