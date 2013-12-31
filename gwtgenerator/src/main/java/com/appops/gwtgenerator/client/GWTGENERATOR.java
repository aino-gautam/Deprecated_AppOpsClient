package com.appops.gwtgenerator.client;

import com.appops.gwtgenerator.client.component.generated.sample.textboxrebindergenerated.CoreTextBox;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTGENERATOR implements EntryPoint {
	public void onModuleLoad() {
		/*HtmlPageProcessor processor = new HtmlPageProcessor();
		try {
			processor.processPageDescription();
		}
		catch (Exception e) {
			e.printStackTrace();
		}*/
		
		GWT.create(CoreTextBox.class);
		/*	LibraryTagProvider LIBRARY_TAG_PROVIDER = GWT.create(LibraryTagProvider.class);
			LIBRARY_TAG_PROVIDER.toString();
		*///Dynamic textBox = GWT.create(TextBox.class);
		
		/*Label label = GWT.create(Label.class);
		
		CheckBox checkBox = GWT.create(CheckBox.class);*/
		
		/*RadioButton radioButton = new RadioButton("name");
		radioButton.setText("radioButton");
		
		PopupPanel panel = new PopupPanel();
		*/
		//RootPanel.get().add((TextBox)textBox);
		/*RootPanel.get().add(label);
		RootPanel.get().add(checkBox);*/
		/*RootPanel.get().add(radioButton);*/
	}
	
}
