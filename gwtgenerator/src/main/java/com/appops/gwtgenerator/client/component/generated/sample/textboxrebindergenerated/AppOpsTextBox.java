package com.appops.gwtgenerator.client.component.generated.sample.textboxrebindergenerated;

import com.appops.gwtgenerator.client.config.annotation.Tag;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.TextBox;
//sample used for reference.. in the TextBoxRebinder.
public class AppOpsTextBox extends TextBox {
	public AppOpsTextBox() {
		try {
			String[] parameters = {"I was invoked by calling the obfuscated version of the method setText"};
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
	
	public static native Object invokeMethod(String obfuscatedMethodName, String[] parameters) /*-{
																								$wnd.alert(obfuscatedMethodName);
																								var theInstance = this;
																								var fn = theInstance[obfuscatedMethodName];
																								
																								fn.apply(theInstance, parameters);
																								theInstance[obfuscatedMethodName]("this is set by me by performing magic!!");
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