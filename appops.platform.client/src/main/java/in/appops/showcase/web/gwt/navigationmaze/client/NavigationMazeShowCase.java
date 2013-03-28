package in.appops.showcase.web.gwt.navigationmaze.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author milind@ensarm.com
 */
public class NavigationMazeShowCase implements EntryPoint{

	private Button button = new Button("run");
	
	@Override
	public void onModuleLoad() {
		

		VerticalPanel vertcalPanel = new VerticalPanel();
		vertcalPanel.setWidth("100%");
		
		final MazeWidget mazeWidget = new MazeWidget();
		
		vertcalPanel.add(mazeWidget);
		
		vertcalPanel.add(button);
		
		vertcalPanel.setCellHorizontalAlignment(mazeWidget, HasHorizontalAlignment.ALIGN_CENTER);
		vertcalPanel.setCellHorizontalAlignment(button, HasHorizontalAlignment.ALIGN_CENTER);
		
		RootPanel.get().add(vertcalPanel);
		
		button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				mazeWidget.refreshMaze();
			}
		});
	}
}
