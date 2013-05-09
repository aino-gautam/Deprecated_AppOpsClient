package in.appops.client.common.snippet;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class Loader extends Composite {
	
	private HorizontalPanel hpLoader;
	private Image loaderImg;
	private Label loadingMessageLbl;
	
	private final static String LOADINGMESSAGE = "Loading...";
	
	
	public Loader(){
		hpLoader = new HorizontalPanel();
		loaderImg = new Image("images/opptinLoader.gif");
		loadingMessageLbl = new Label(LOADINGMESSAGE);
		initWidget(hpLoader);
	}
	
	public Loader(String imageUrl, String loadingMsg){
		hpLoader = new HorizontalPanel();
		loaderImg = new Image(imageUrl);
		loadingMessageLbl = new Label(loadingMsg);
		initWidget(hpLoader);
	}
	
	public void createLoader(){
		loaderImg.setStylePrimaryName("loader");
		hpLoader.add(loaderImg);
		hpLoader.add(loadingMessageLbl);
	}
	
	public void setLoadingImage(String url){
		loaderImg.setUrl(url);
	}
	
	public void setLoadingMessage(String message){
		loadingMessageLbl.setText(message);
	}
}
