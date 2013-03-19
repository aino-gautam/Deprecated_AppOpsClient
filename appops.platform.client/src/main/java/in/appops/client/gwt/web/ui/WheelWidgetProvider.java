/**
 * 
 */
package in.appops.client.gwt.web.ui;


import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Debasish Padhy Created it on 14-Mar-2013
 *
 */
public interface WheelWidgetProvider extends Provider<Widget>{

	/**
	 * Method used to get the next widget set for the row.
	 * @param row
	 * @return widget set for the row.
	 */
	public LinkedHashSet<Widget> getNextWidgetSet(Row row) ;
	
	/**
	 * Method used to get next widget set for the cylinder.
	 * @param cyl
	 * @return Hashmap of row vs it's widget set.
	 */
	public Map<Row , Set<Widget>> getNextWidgetSet(Cylinder cyl) ;
	

}
