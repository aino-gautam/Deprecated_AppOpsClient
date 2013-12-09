package com.appops.gwtgenerator.client.components.generated.sample.stubgenerated;

import com.google.gwt.user.client.ui.TextBox;

//sample used for reference.. in the TextBoxRebinder.
public class AppOpsTextBox extends TextBox {
	Object[]	parameters;
	
	public AppOpsTextBox() {
		Integer.parseInt(parameters[1].toString());
	}
	
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

