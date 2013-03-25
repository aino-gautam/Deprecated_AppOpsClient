package in.appops.client.gwt.web.ui;


import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class ImageWidget extends Composite{
	
	private int xLeft = 0 ;
	private int yTop = 0 ;
	private int zOrder = 1 ;
	private int width = 100 ;
	private int height = 100 ;
	
	private HorizontalPanel imagePanel =new HorizontalPanel();
	private Configuration configuration = new Configuration();
	private Image image = null;
	public static String IMAGEWIDGET_PRIMARY_CSS = "imageWidget";
	
	public ImageWidget() {
		
	}
	
	public ImageWidget(String imageUrl){
		initialize(imageUrl);
		initWidget(imagePanel);
		
	}
	
	private void initialize(String imageUrl) {
		image  = new Image(imageUrl);
		image.setStylePrimaryName("imageWidget");
		imagePanel.add(image);
		imagePanel.setStylePrimaryName("imageWidget");
	}

	ImageWidget(int x , int y , int z , int w , int h){
		xLeft = x ;
		yTop = y ;
		zOrder = z;
		width = w ;
		height = h ;
	}
	
	public int getLeftOffset() {
		return xLeft;
	}


	public void setLeftOffset(int xLeft) {
		this.xLeft = xLeft;
	}


	public int getTopOffset() {
		return yTop;
	}


	public void setTopOffset(int yTop) {
		this.yTop = yTop;
	}


	public int getOrder() {
		return zOrder;
	}


	public void setOrder(int zOrder) {
		this.zOrder = zOrder;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}

	public HorizontalPanel getImagePanel() {
		return imagePanel;
	}

	public void setImagePanel(HorizontalPanel imagePanel) {
		this.imagePanel = imagePanel;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
}
