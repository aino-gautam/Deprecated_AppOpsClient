package com.appops.gwtgenerator.client;

import com.appops.gwtgenerator.client.config.util.HtmlPageProcessor;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTGENERATOR implements EntryPoint {

	public void onModuleLoad() {
		Window.alert("In on module load");
		HtmlPageProcessor processor = new HtmlPageProcessor();
		try {
			processor.processPageDescription();

			Window.alert("html proccessor completed");
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		/*HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("emailId", "pallavi@ensarm.com");
		parameters.put("password", "pallavi123");

		EntityToJsonClientConvertor clientConvertor = new EntityToJsonClientConvertor();
		JSONObject obj = clientConvertor.encodeMap(parameters);
		System.out.println(obj.toString());*/

		/*
		 TempConfiguration temp = new TempConfiguration();
		 temp.getConfiguration2();*/

		// PlaySnippet playSnippet = GWT.create(PlaySnippet.class);
		/*
		 * LibraryTagProvider LIBRARY_TAG_PROVIDER =
		 * GWT.create(LibraryTagProvider.class);
		 * LIBRARY_TAG_PROVIDER.toString();
		 */// Dynamic textBox = GWT.create(TextBox.class);

		/*
		 * Label label = GWT.create(Label.class);
		 * 
		 * CheckBox checkBox = GWT.create(CheckBox.class);
		 */

		/*
		 * RadioButton radioButton = new RadioButton("name");
		 * radioButton.setText("radioButton");
		 * 
		 * PopupPanel panel = new PopupPanel();
		 */
		// RootPanel.get().add((TextBox)textBox);
		/*
		 * RootPanel.get().add(label); RootPanel.get().add(checkBox);
		 */
		/* RootPanel.get().add(radioButton); */
	}
}
