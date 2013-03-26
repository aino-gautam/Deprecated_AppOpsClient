package in.appops.client.gwt.web.ui;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.server.core.services.media.constant.MediaConstant;
import java.util.LinkedHashSet;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class ImageSliderPopup extends PopupPanel implements ClickHandler{
	
	private VerticalPanel slider =new VerticalPanel();
	private HorizontalPanel actualImagePanel = new HorizontalPanel();
	private HorizontalPanel basePanel = new HorizontalPanel();
	private LinkedHashSet<Widget> widgetList;
	
	public ImageSliderPopup() {
		
	}
	
	public ImageSliderPopup(LinkedHashSet<Widget> list){
		widgetList =list;
		
		for (Widget widget : widgetList) {
			ImageWidget imageWidget = (ImageWidget) widget;
			imageWidget.addDomHandler(this, ClickEvent.getType());
			slider.add(imageWidget);
		}
		
		basePanel.add(actualImagePanel);
		basePanel.add(slider);
	}
	
	private void initializeSlider(){
		basePanel.add(actualImagePanel);
		basePanel.add(slider);
		//initWidget(basePanel);
	}
	
	private void showImage(Widget widget){
		actualImagePanel.clear();
		actualImagePanel.add(widget);
	}

	@Override
	public void onClick(ClickEvent event) {
		ImageWidget imageWidget = (ImageWidget) event.getSource();
		Entity entity = imageWidget.getEntity();
		
		//download the image and show it in the actualImagePanel
		Image image = new Image(entity.getProperty(MediaConstant.BLOBID).getValue().toString());
		
		showImage(image);
		
	}

}
