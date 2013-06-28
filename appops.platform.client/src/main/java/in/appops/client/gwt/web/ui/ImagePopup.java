package in.appops.client.gwt.web.ui;

import in.appops.client.common.fields.ImageField;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
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

public class ImagePopup extends PopupPanel implements ClickHandler {

	private ImageField crossImageField ;
	private ImageField nextImageField ;
	private ImageField prevImageField ;
	
	private VerticalPanel basePanel  = new VerticalPanel();
	private HorizontalPanel imageWithSliderPanel;
	private HorizontalPanel actualImagePanel;
	private BlobDownloader blobDownloader;
	private VerticalPanel slider ;
	private VerticalPanel nextImagePanel  = new VerticalPanel();
	private VerticalPanel prevImagePanel  = new VerticalPanel();
	private ArrayList<ImageWidget> widgetList = new ArrayList<ImageWidget>();
	private int index = 0;
	
	public ImagePopup() {
		
	}
	
	public void initialize(ArrayList<ImageWidget> widgetSetForRow){
		
		imageWithSliderPanel = new HorizontalPanel();
		actualImagePanel = new HorizontalPanel();
		slider = new VerticalPanel();
		widgetList = widgetSetForRow;
		
		/********************* cross image***********/
		
		crossImageField = new ImageField();
		
		crossImageField.setConfiguration(getImageFieldConfiguration("images/crossIconSmall.png", "crossImageInPhotoviewer"));
		try {
			crossImageField.createField();
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
		
		/**************************** next image********/
		
		nextImageField = new ImageField();
		
		nextImageField.setConfiguration(getImageFieldConfiguration("images/next.png", null));
		try {
			nextImageField.createField();
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
		
		/****************** prev image *********************/
		
		prevImageField = new ImageField();
		
		prevImageField.setConfiguration(getImageFieldConfiguration("images/prev.png", null));
		try {
			prevImageField.createField();
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
				
		crossImageField.addClickHandler(this);
		prevImageField.addClickHandler(this);
		nextImageField.addClickHandler(this);
		
		
		nextImagePanel.add(nextImageField);
		prevImagePanel.add(prevImageField);
		
		imageWithSliderPanel.add(prevImagePanel);
		imageWithSliderPanel.add(actualImagePanel);
		imageWithSliderPanel.add(nextImagePanel);
		
		nextImagePanel.setStylePrimaryName("nextPrevImagePanel");
		prevImagePanel.setStylePrimaryName("nextPrevImagePanel");
		//imageWithSliderPanel.add(slider);
		
		imageWithSliderPanel.setCellVerticalAlignment(prevImagePanel, HasVerticalAlignment.ALIGN_MIDDLE);
		prevImagePanel.setCellHorizontalAlignment(prevImageField, HasHorizontalAlignment.ALIGN_CENTER);
		
		actualImagePanel.setStylePrimaryName("actualImagePanel");
		
		imageWithSliderPanel.setCellVerticalAlignment(nextImagePanel, HasVerticalAlignment.ALIGN_MIDDLE);
		nextImagePanel.setCellHorizontalAlignment(nextImageField, HasHorizontalAlignment.ALIGN_CENTER);
		
		slider.setStylePrimaryName("sliderPanel");
		imageWithSliderPanel.setCellHorizontalAlignment(slider, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		basePanel.add(crossImageField);
		basePanel.setCellHorizontalAlignment(crossImageField, HasHorizontalAlignment.ALIGN_RIGHT);
		basePanel.add(imageWithSliderPanel);
		
		setStylePrimaryName("imagePopup");
		
		setAutoHideEnabled(true);
		setAnimationEnabled(true);
		setGlassEnabled(true);
		setGlassStyleName("imageGlassPanel");
		
		basePanel.setStylePrimaryName("basePanelInPopup");
		setWidget(basePanel);
		
	}
	
	public Configuration getImageFieldConfiguration(String url, String primaryCSS) {
		Configuration config = new Configuration();
		config.setPropertyByName(ImageField.IMAGEFIELD_BLOBID, url);
		config.setPropertyByName(ImageField.IMAGEFIELD_PRIMARYCSS, primaryCSS);
		return config;
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
		image.setStylePrimaryName("imageInImageViewer");
		actualImagePanel.add(image);
		
		actualImagePanel.setCellHorizontalAlignment(image, HasHorizontalAlignment.ALIGN_CENTER);
	}
	
	@Override
	public void onClick(ClickEvent event) {
		
		Widget widget = (Widget) event.getSource();
		if(widget.equals(crossImageField)){
			hide();
			return;
		}else if(widget.equals(prevImageField)){
			if(index!=0){
				index--;
				actualImagePanel.clear();
				String bloId = widgetList.get(index).getEntity().getProperty(MediaConstant.BLOBID).getValue().toString();
				Image image = new Image(blobDownloader.getImageDownloadURL(bloId));
				image.setStylePrimaryName("imageInImageViewer");
				actualImagePanel.add(image);
				
			}else{
				index=widgetList.size()-1;
				actualImagePanel.clear();
				String bloId = widgetList.get(index).getEntity().getProperty(MediaConstant.BLOBID).getValue().toString();
				Image image = new Image(blobDownloader.getImageDownloadURL(bloId));
				image.setStylePrimaryName("imageInImageViewer");
				actualImagePanel.add(image);
				
			}
		}else if(widget.equals(nextImageField)){
			if(index<widgetList.size()-1){
				index++;
				actualImagePanel.clear();
				String bloId = widgetList.get(index).getEntity().getProperty(MediaConstant.BLOBID).getValue().toString();
				Image image = new Image(blobDownloader.getImageDownloadURL(bloId));
				image.setStylePrimaryName("imageInImageViewer");
				actualImagePanel.add(image);
				
			}else{
				index=0;
			}
				
		}
	}

}