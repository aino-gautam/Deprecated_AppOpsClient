package in.appops.client.gwt.web.ui;

import java.util.ArrayList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author dhananjay@ensarm.com
 *
 */

public class ImagePopup extends PopupPanel implements ClickHandler,MouseOverHandler,MouseOutHandler{
	
	
	private ArrayList<String> listofImages;
	private Image nextImg;
	private Image prevImg;
	private Image displayImage;
	private Image crossImg;
	private VerticalPanel mainMainPanel;
	private HorizontalPanel mainPanel;
	private HorizontalPanel imagePanel;
	private int index = 0;
	
	public ArrayList<String> getListofImages() {
		return listofImages;
	}

	public void setListofImages(ArrayList<String> list) {
		
		listofImages = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
		    String str= list.get(i).replace("ThumbnailImages/", "");
			listofImages.add(str);
			
		}
	}

	public ImagePopup(String imgPath) {
		initialize();
		imgPath = imgPath.replace("http://127.0.0.1:8888/", "");
		imgPath = imgPath.replace("ThumbnailImages/", "");
		displayImage.setUrl(imgPath);
		imagePanel.add(displayImage);
		createUi();
		add(mainMainPanel);
	}
	
	private void initialize() {
		mainMainPanel = new VerticalPanel();
		mainPanel = new HorizontalPanel();
		imagePanel = new HorizontalPanel();
		imagePanel.setSize("210px", "180px");
		nextImg = new Image("images/next.png");
		prevImg = new Image("images/previous.png");
		displayImage = new Image();
		displayImage.setStylePrimaryName("popupImageFullheighWidth");
	
		crossImg = new Image("images/cross.png");
		setStylePrimaryName("imagePopup");
		setSize("330px", "230px");
		setAutoHideEnabled(true);
		setAnimationEnabled(true);
		setGlassEnabled(true);
		setGlassStyleName("imageGlassPanel");
		prevImg.setStylePrimaryName("popupNextPrevImage");
		nextImg.setStylePrimaryName("popupNextPrevImage");
		displayImage.addMouseOutHandler(this);
		displayImage.addMouseOverHandler(this);
		displayImage.addClickHandler(this);
		nextImg.addMouseOutHandler(this);
		prevImg.addMouseOutHandler(this);
		prevImg.addMouseOverHandler(this);
		nextImg.addMouseOverHandler(this);
		nextImg.addClickHandler(this);
		prevImg.addClickHandler(this);
		crossImg.addClickHandler(this);
	}

	private void createUi() {
		mainMainPanel.add(crossImg);
		mainPanel.add(prevImg);
		mainPanel.add(imagePanel);
		mainPanel.add(nextImg);
		mainMainPanel.add(mainPanel);
		mainMainPanel.setCellHorizontalAlignment(crossImg, HasHorizontalAlignment.ALIGN_RIGHT);
		imagePanel.setCellVerticalAlignment(displayImage, HasVerticalAlignment.ALIGN_MIDDLE);
		imagePanel.setCellHorizontalAlignment(displayImage, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellVerticalAlignment(imagePanel, HasVerticalAlignment.ALIGN_MIDDLE);
		mainPanel.setCellHorizontalAlignment(imagePanel, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellVerticalAlignment(prevImg, HasVerticalAlignment.ALIGN_MIDDLE);
		mainPanel.setCellVerticalAlignment(nextImg, HasVerticalAlignment.ALIGN_MIDDLE);
		mainPanel.setCellHorizontalAlignment(nextImg, HasHorizontalAlignment.ALIGN_RIGHT);
	}



	@Override
	public void onClick(ClickEvent event) {
		Image img = (Image) event.getSource();
		if(img.equals(nextImg)||img.equals(displayImage)){
			if(index<listofImages.size()-1){
				index++;
				mainPanel.clear();
				displayImage.setUrl(listofImages.get(index));
				imagePanel.add(displayImage);
				createUi();
			}else{
				index=0;
				mainPanel.clear();
				displayImage.setUrl(listofImages.get(index));
				imagePanel.add(displayImage);
				createUi();
			}
		}else if(img.equals(prevImg)){
			if(index!=0){
				index--;
				mainPanel.clear();
				displayImage.setUrl(listofImages.get(index));
				imagePanel.add(displayImage);
				createUi();
			}else{
				index=listofImages.size()-1;
				mainPanel.clear();
				displayImage.setUrl(listofImages.get(index));
				imagePanel.add(displayImage);
				createUi();
			}
		}
		else if(img.equals(crossImg))
			hide();
		
	}
	@Override
	public void onMouseOver(MouseOverEvent event) {
		if(event.getSource() instanceof Image){
			nextImg.removeStyleName("popupNextPrevImage");
			prevImg.removeStyleName("popupNextPrevImage");
		}
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		
		if(event.getSource() instanceof Image){
			nextImg.setStylePrimaryName("popupNextPrevImage");
			prevImg.setStylePrimaryName("popupNextPrevImage");
		}
	}
	
}