package com.appops.gwtgenerator.client.components;

import java.util.ArrayList;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.TextBox;
//sample used for reference.. in the TextBoxRebinder.
@Export
public class AppOpsTextBox extends TextBox implements Exportable {
	public AppOpsTextBox() {
		try {
			ArrayList<String> parameters = new ArrayList<String>();
			parameters.add("I was invoked by calling the obfuscated version of the method setText");
			Object myValue = invokeMethod("setText", parameters);
			GWT.log("Got value " + myValue, null);
		}
		catch (Exception e) {
			GWT.log("JSNI method invokeMethod() threw an exception:", e);
		}
	}
	
	
	@Override
	public void setText(String text) {
		super.setText(text);
	}
	
	public static native Object invokeMethod(String obfuscatedMethodName, ArrayList parameters) /*-{
																								$wnd.alert(obfuscatedMethodName);
																								var theInstance = this;
																								theInstance.obfuscatedMethodName("this is set by me by performing magic!!");
																								return null;
																								}-*/;
}

/*
@Attributes({

@Attribute(name = "text", type = String.class, restricted = true),
@Attribute(name = "visible", type = Boolean.class, restricted = true), 
@Attribute(name = "size", type = Integer.class, restricted = true)

})

@EventSet(applicable = true, dynamic = true)
@FiresEvent(eventName = "click", param = { "param1", "param2" })
public class AppOpsTextBox extends TextBox implements Field {
	
	@Attribute(name = "visibleLines", type = Integer.class)
	private int	visibleLines	= 2;
	
}*/