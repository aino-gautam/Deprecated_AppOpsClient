package com.appops.gwtgenerator.client;

import org.timepedia.exporter.client.ExporterUtil;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTGENERATOR implements EntryPoint {
	public void onModuleLoad() {
		TextBox textBox = GWT.create(TextBox.class);
		ExporterUtil.exportAll();

		RootPanel.get().add(textBox);
	}
}
