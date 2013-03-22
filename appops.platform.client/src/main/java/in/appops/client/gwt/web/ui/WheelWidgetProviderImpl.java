package in.appops.client.gwt.web.ui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class WheelWidgetProviderImpl implements WheelWidgetProvider {
   
	
	@Override
	public LinkedHashSet<Widget> getNextWidgetSet(Row row) {
		
		LinkedHashSet<Widget> widgetSet =new LinkedHashSet<Widget>();
		
		for (int indx = 0 ; indx < 10 ; indx++){
			HorizontalPanel hPanel = new HorizontalPanel();
			Label lbl = new Label("test " + indx) ; 
			hPanel.add(lbl);
			hPanel.setStylePrimaryName("wheelWidget");
			widgetSet.add(hPanel) ;
		}
		
		return widgetSet;
	}

	@Override
	public Map<Row, Set<Widget>> getNextWidgetSet(Cylinder cyl) {
		Set<Widget> set =new HashSet<Widget>();
		Map widgetSet =new HashMap<Row, Set<Widget>>();
		
		
		return widgetSet;
	}

}
