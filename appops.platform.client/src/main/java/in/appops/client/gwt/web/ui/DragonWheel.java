/**
 * 
 */
package in.appops.client.gwt.web.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Debasish Padhy Created it on 06-Mar-2013
 *
 */
public class DragonWheel extends Composite {
	
	
	private ArrayList<Widget> widgetList;

	@Override
	protected void initWidget(Widget widget) {
		
		
		
		super.initWidget(widget);
	}
	
	public void setWidgetList(ArrayList<Widget> widgets){
		widgetList = widgets ;
	}
	
	public ArrayList<Widget> getWidgetList(){
		return widgetList ;
	}
	
	public boolean removeWidget(Widget w){
		return widgetList.remove(w);
	}
	
	public void addWidget(Widget w){
		widgetList.add(w);
	}
	
	
	

}
