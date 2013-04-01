package in.appops.client.touch;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.NavigationEvent;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Navigator extends Composite implements ClickHandler {

	private HorizontalPanel basePanel;
	private Configuration configuration;
	private int totalScreens = 0;
	private int currentScreenNo = 1;
	private Widget nextWidget, prevWidget;
	
	public static final String NAVIGATOR_ALIGNMENT = "navigatorHorizontalAlignment";
	
	public static final String ALIGNMENT_HORIZONTAL = "horizontalAlignment";
	public static final String ALIGNMENT_VERTICAL = "verticalAlignment";
	
	public Navigator(){
		basePanel = new HorizontalPanel();
		initWidget(basePanel);
	}
	
	public void createNavigatorUI() throws AppOpsException {
		basePanel.clear();
		if(getConfiguration() == null)
			throw new AppOpsException("Navigator configuration unavailable");
		
		nextWidget.addDomHandler(this, ClickEvent.getType());
		prevWidget.addDomHandler(this, ClickEvent.getType());
		
		
		if(getConfiguration().getPropertyByName(NAVIGATOR_ALIGNMENT).equals(ALIGNMENT_HORIZONTAL)){
			HorizontalPanel hpanel = new HorizontalPanel();
			hpanel.add(prevWidget);
			hpanel.add(nextWidget);
			
		
			if(currentScreenNo == 1 && currentScreenNo < totalScreens){
				prevWidget.setVisible(false);
				nextWidget.setVisible(true);
			} else if(currentScreenNo > 1 && currentScreenNo < totalScreens){
				prevWidget.setVisible(true);
				nextWidget.setVisible(true);
			} else if(currentScreenNo > 1 && currentScreenNo == totalScreens){
				prevWidget.setVisible(true);
				nextWidget.setVisible(false);
			} else if(currentScreenNo == 1 && currentScreenNo == totalScreens){
				prevWidget.setVisible(false);
				nextWidget.setVisible(false);
			}
				
			basePanel.add(hpanel);

		} else if(getConfiguration().getPropertyByName(NAVIGATOR_ALIGNMENT).equals(ALIGNMENT_VERTICAL)){
			VerticalPanel vpanel = new VerticalPanel();
			vpanel.add(nextWidget);
			vpanel.add(prevWidget);
			
			basePanel.add(vpanel);
		}
			
	}

	public void setNextWidget(Widget w){
		nextWidget = w;
	}
	
	public void setPrevWidget(Widget w){
		prevWidget = w;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public int getTotalScreens() {
		return totalScreens;
	}

	public void setTotalScreens(int totalScreens) {
		this.totalScreens = totalScreens;
	}

	public int getCurrentScreenNo() {
		return currentScreenNo;
	}

	public void setCurrentScreenNo(int currentScreenNo) {
		this.currentScreenNo = currentScreenNo;
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget widget =  (Widget)event.getSource();
		NavigationEvent navEvent = new NavigationEvent();
		if(widget == nextWidget){
			navEvent.setEventType(NavigationEvent.GONEXT);
			int screenNo = currentScreenNo+1;
			navEvent.setEventData(screenNo);
			setCurrentScreenNo(screenNo);
			setVisibilityOfNextPrevElement();
			
		} else if(widget == prevWidget){
			navEvent.setEventType(NavigationEvent.GOPREVIOUS);
			int screenNo = currentScreenNo-1;
			navEvent.setEventData(screenNo);
			setCurrentScreenNo(screenNo);
			setVisibilityOfNextPrevElement();
		}
		AppUtils.EVENT_BUS.fireEvent(navEvent);
	}

	public void setVisibilityOfNextPrevElement() {
		if(currentScreenNo == 1 && currentScreenNo < totalScreens){
			prevWidget.setVisible(false);
			nextWidget.setVisible(true);
		} else if(currentScreenNo > 1 && currentScreenNo < totalScreens){
			prevWidget.setVisible(true);
			nextWidget.setVisible(true);
		} else if(currentScreenNo > 1 && currentScreenNo == totalScreens){
			prevWidget.setVisible(true);
			nextWidget.setVisible(false);
		} else if(currentScreenNo == 1 && currentScreenNo == totalScreens){
			prevWidget.setVisible(false);
			nextWidget.setVisible(false);
		}
		
	}
}
