/**
 * 
 */
package in.appops.client.gwt.web.ui;


import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Debasish Padhy Created it on 14-Mar-2013
 *
 */
public interface WheelWidgetProvider extends Provider<Widget>{

	public Set<Widget> getNextWidgetSet(Row row) ;
	
	public Map<Row , Set<Widget>> getNextWidgetSet(Cylinder cyl) ;
	

}
