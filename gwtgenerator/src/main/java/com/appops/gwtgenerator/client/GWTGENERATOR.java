package com.appops.gwtgenerator.client;

import com.appops.gwtgenerator.client.generator.Dynamic;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTGENERATOR implements EntryPoint {
	public void onModuleLoad() {
		Dynamic textBox = GWT.create(TextBox.class);
		
		/*Label label = GWT.create(Label.class);
		
		CheckBox checkBox = GWT.create(CheckBox.class);*/
		
		/*RadioButton radioButton = new RadioButton("name");
		radioButton.setText("radioButton");
		
		PopupPanel panel = new PopupPanel();
		*/
		RootPanel.get().add((TextBox)textBox);
		/*RootPanel.get().add(label);
		RootPanel.get().add(checkBox);*/
		/*RootPanel.get().add(radioButton);*/
		
	}
	
}
