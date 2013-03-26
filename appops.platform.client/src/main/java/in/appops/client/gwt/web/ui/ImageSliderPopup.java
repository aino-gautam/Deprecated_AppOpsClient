package in.appops.client.gwt.web.ui;

import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.server.core.services.media.constant.MediaConstant;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ImageSliderPopup extends PopupPanel implements ClickHandler {

	private Image crossImg = new Image("images/cross.png");
	private Image nextImg = new Image("images/next.png");
	private Image prevImg = new Image("images/prev.png");
	private VerticalPanel basePanel  = new VerticalPanel();
	private HorizontalPanel imageWithSliderPanel;
	private HorizontalPanel actualImagePanel;
	private BlobDownloader blobDownloader;
	private VerticalPanel slider ;
	private ArrayList<ImageWidget> widgetList = new ArrayList<ImageWidget>();
	private int index = 0;
	
	public ImageSliderPopup() {

	}
	
	public void initialize(ArrayList<ImageWidget> widgetSetForRow){
		
		imageWithSliderPanel = new HorizontalPanel();
		actualImagePanel = new HorizontalPanel();
		slider = new VerticalPanel();
		widgetList = widgetSetForRow;
				
		for (Widget widget:widgetSetForRow) {
			ImageWidget imageWidget = (ImageWidget) widget;
			imageWidget.addDomHandler(this,ClickEvent.getType());
			slider.add(imageWidget);
		}
		
		crossImg.addClickHandler(this);
		prevImg.addClickHandler(this);
		nextImg.addClickHandler(this);
		
		imageWithSliderPanel.add(prevImg);
		imageWithSliderPanel.add(actualImagePanel);
		imageWithSliderPanel.add(nextImg);
		imageWithSliderPanel.add(slider);
		
		imageWithSliderPanel.setCellVerticalAlignment(prevImg, HasVerticalAlignment.ALIGN_MIDDLE);
		actualImagePanel.setStylePrimaryName("actualImagePanel");
		imageWithSliderPanel.setCellVerticalAlignment(nextImg, HasVerticalAlignment.ALIGN_MIDDLE);
		slider.setStylePrimaryName("sliderPanel");
		imageWithSliderPanel.setCellHorizontalAlignment(slider, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		basePanel.add(crossImg);
		basePanel.setCellHorizontalAlignment(crossImg, HasHorizontalAlignment.ALIGN_RIGHT);
		basePanel.add(imageWithSliderPanel);
		
		setStylePrimaryName("imagePopup");
		
		setAutoHideEnabled(true);
		setAnimationEnabled(true);
		setGlassEnabled(true);
		setGlassStyleName("imageGlassPanel");
		
		basePanel.setStylePrimaryName("basePanelInPopup");
		add(basePanel);
		
	}
	
	public void showImage(Widget widget){
		for(int i=0; i<widgetList.size(); i++){
			if(widgetList.get(i).equals(widget)){
				index = i;
			}
		}
		ImageWidget imageWidget = (ImageWidget) widget;
		actualImagePanel.clear();
		if(blobDownloader==null)
			blobDownloader = new BlobDownloader();
		String bloId = imageWidget.getEntity().getProperty(MediaConstant.BLOBID).getValue().toString();
		ImageWidget image = new ImageWidget(blobDownloader.getImageDownloadURL(bloId));
		actualImagePanel.add(image);
	}
	
	@Override
	public void onClick(ClickEvent event) {
		
		Widget widget = (Widget) event.getSource();
		if(widget.equals(crossImg)){
			hide();
		}else if(widget.equals(prevImg)){
			if(index!=0){
				index--;
				actualImagePanel.clear();
				actualImagePanel.add(widgetList.get(index));
				
			}else{
				index=widgetList.size()-1;
				actualImagePanel.clear();
				actualImagePanel.add(widgetList.get(index));
				
			}
		}else if(widget.equals(nextImg)){
			if(index<widgetList.size()-1){
				index++;
				actualImagePanel.clear();
				actualImagePanel.add(widgetList.get(index));
				
			}else{
				index=0;
			}
				
		}else{
			/*ImageWidget imageWidget = (ImageWidget) widget;
			
			showImage(imageWidget);*/
		}
	}

}
