/**
 * 
 */
package in.appops.showcase.web.gwt.dragonwheel.client;

import in.appops.client.gwt.web.ui.Cylinder;
import in.appops.client.gwt.web.ui.DragonWheelNew;
import in.appops.client.gwt.web.ui.Row;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author Debasish Padhy Created it on 06-Mar-2013
 *
 */
public class DragonWheelShowCase implements EntryPoint,ClickHandler{

	final Cylinder cylinder = new Cylinder();
	Button btn =new Button("spin");
		
	@Override
	public void onModuleLoad() {
		
		HorizontalPanel hpanel =new HorizontalPanel();
		btn.addClickHandler(this);
		
		DragonWheelNew wheel = new DragonWheelNew() ;
		cylinder.setName("cyl1");
		
		Row row =new Row("2012");
		row.setWidgetSpacing(150);
		row.setxLeft(200);
		row.setyTop(30);
		row.setIndependent(true);
		row.setRowPosition(0);
		row.initializeRow();
		cylinder.addRow(row);
		
		Row row1 =new Row("2013");
		row1.setWidgetSpacing(150);
		row1.setxLeft(200);
		row1.setyTop(150);
		row1.setIndependent(false);
		row1.setRowPosition(1);
		row1.initializeRow();
		
		cylinder.addRow(row1);
		cylinder.setOrder(0);
		wheel.addCylinder(cylinder);
		
		
		btn.setVisible(true);
		hpanel.add(btn);
		
		wheel.initWidgetPositions();
		hpanel.add(wheel);
		
		RootPanel.get().add(hpanel);
		//wheel.setWidth("100%") ;
		//wheel.setHeight("100%") ;
		
	}

	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource() instanceof Button){
			cylinder.spinRow();
		}
		
	}
	
}
