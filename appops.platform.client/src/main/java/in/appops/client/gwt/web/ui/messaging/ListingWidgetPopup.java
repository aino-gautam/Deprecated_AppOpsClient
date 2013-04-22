package in.appops.client.gwt.web.ui.messaging;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ListingWidgetPopup extends PopupPanel{

	public ListingWidgetPopup() {
		super(true);
		//this.setAnimationEnabled(false);
	}
	
	public void setBaseWidget(Widget widget) {
		this.setWidget(widget);
	}
}
