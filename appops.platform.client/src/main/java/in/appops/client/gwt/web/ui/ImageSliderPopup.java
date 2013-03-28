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
	private VerticalPanel nextImagePanel  = new VerticalPanel();
	private VerticalPanel prevImagePanel  = new VerticalPanel();
	private ArrayList<ImageWidget> widgetList = new ArrayList<ImageWidget>();
	private int index = 0;
	
	public ImageSliderPopup() {
		
	}
	
	public void initialize(ArrayList<ImageWidget> widgetSetForRow){
		
		imageWithSliderPanel = new HorizontalPanel();
		actualImagePanel = new HorizontalPanel();
		slider = new VerticalPanel();
		widgetList = widgetSetForRow;
			
		crossImg.addClickHandler(this);
		prevImg.addClickHandler(this);
		nextImg.addClickHandler(this);
		nextImagePanel.add(nextImg);
		prevImagePanel.add(prevImg);
		
		imageWithSliderPanel.add(prevImagePanel);
		imageWithSliderPanel.add(actualImagePanel);
		imageWithSliderPanel.add(nextImagePanel);
		
		nextImagePanel.setStylePrimaryName("nextPrevImagePanel");
		prevImagePanel.setStylePrimaryName("nextPrevImagePanel");
		//imageWithSliderPanel.add(slider);
		
		imageWithSliderPanel.setCellVerticalAlignment(prevImagePanel, HasVerticalAlignment.ALIGN_MIDDLE);
		prevImagePanel.setCellHorizontalAlignment(prevImg, HasHorizontalAlignment.ALIGN_CENTER);
		
		actualImagePanel.setStylePrimaryName("actualImagePanel");
		
		imageWithSliderPanel.setCellVerticalAlignment(nextImagePanel, HasVerticalAlignment.ALIGN_MIDDLE);
		nextImagePanel.setCellHorizontalAlignment(nextImg, HasHorizontalAlignment.ALIGN_CENTER);
		
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
		setWidget(basePanel);
		
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
		Image image = new Image(blobDownloader.getImageDownloadURL(bloId));
		image.setSize("800px", "550px");
		actualImagePanel.add(image);
		
		actualImagePanel.setCellHorizontalAlignment(image, HasHorizontalAlignment.ALIGN_CENTER);
	}
	
	@Override
	public void onClick(ClickEvent event) {
		
		Widget widget = (Widget) event.getSource();
		if(widget.equals(crossImg)){
			hide();
			return;
		}else if(widget.equals(prevImg)){
			if(index!=0){
				index--;
				actualImagePanel.clear();
				String bloId = widgetList.get(index).getEntity().getProperty(MediaConstant.BLOBID).getValue().toString();
				Image image = new Image(blobDownloader.getImageDownloadURL(bloId));
				image.setSize("800px", "550px");
				actualImagePanel.add(image);
				
			}else{
				index=widgetList.size()-1;
				actualImagePanel.clear();
				String bloId = widgetList.get(index).getEntity().getProperty(MediaConstant.BLOBID).getValue().toString();
				Image image = new Image(blobDownloader.getImageDownloadURL(bloId));
				image.setSize("800px", "550px");
				actualImagePanel.add(image);
				
			}
		}else if(widget.equals(nextImg)){
			if(index<widgetList.size()-1){
				index++;
				actualImagePanel.clear();
				String bloId = widgetList.get(index).getEntity().getProperty(MediaConstant.BLOBID).getValue().toString();
				Image image = new Image(blobDownloader.getImageDownloadURL(bloId));
				image.setSize("800px", "550px");
				actualImagePanel.add(image);
				
			}else{
				index=0;
			}
				
		}else{
			/*ImageWidget imageWidget = (ImageWidget) widget;
			
			showImage(imageWidget);*/
		}
	}

}