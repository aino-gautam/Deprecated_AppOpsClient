package in.appops.showcase.web.gwt.componentconfiguration.client.developer;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EntityDiagramHome extends Composite implements FieldEventHandler{

	TabBar tabBar;
	VerticalPanel vPan;
	VerticalPanel contenPanel;

	public EntityDiagramHome(){
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}

	public void initialize(){
		tabBar = new TabBar();
		vPan = new VerticalPanel();
		contenPanel = new VerticalPanel();
	}

	public void createUI(){

		tabBar.addTab(new Label("Diagrams"));
		tabBar.addTab("Entities");
		tabBar.addTab("New Entity");
		tabBar.addTab("Test Data");
		tabBar.addTab("Live Data");
		//tabBar.selectTab(0);
		vPan.add(tabBar);
		//vPan.setStylePrimaryName("entityTabBarPanel");
		initWidget(vPan);

		tabBar.addSelectionHandler(new SelectionHandler<Integer>() {

			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				int selectedTab=event.getSelectedItem();
				FieldEvent fieldEvent = new FieldEvent(FieldEvent.SELECTION_EVENT, "You Selected "+selectedTab);
				fieldEvent.setEventSource(this);
				AppUtils.EVENT_BUS.fireEvent(fieldEvent);
			}
		});
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			int eventType = event.getEventType();
			switch (eventType) {
			case FieldEvent.SELECTION_EVENT: {
				if(vPan.getWidgetCount()>1){
					vPan.remove(1);
					vPan.add(new Label(event.getEventData().toString()));
				}
				else{
					vPan.add(new Label(event.getEventData().toString()));
				}

				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
