/**
 * 
 */
package in.appops.showcase.web.gwt.dragonwheel.client;

import in.appops.client.gwt.web.ui.DragonWheel;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Debasish Padhy Created it on 06-Mar-2013
 *
 */
public class DragonWheelShowCase implements EntryPoint {

	@Override
	public void onModuleLoad() {
		DragonWheel wheel = new DragonWheel() ;
		wheel.setWidgetList(getDummyWidgetList(8));
		wheel.layOutDragonWheel();
		
		RootPanel.get("nameFieldContainer").add(wheel);
		
		wheel.setWidth("100%") ;
		wheel.setHeight("100%") ;
	}
	
	static ArrayList<Widget> getDummyWidgetList(int num){
		
		ArrayList<Widget> dummyList = new ArrayList<Widget>();
		for (int indx = 0 ; indx < num ; indx++){
			HorizontalPanel hPanel = new HorizontalPanel();
			Label lbl = new Label("test " + indx) ; 
			lbl.setStylePrimaryName("label");
			hPanel.add(lbl);
			hPanel.setStylePrimaryName("wheelWidget");
			dummyList.add(hPanel) ;
		}
		return dummyList ;
	}

}
