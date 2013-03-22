package in.appops.client.common.components;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.MultiUploader;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AttachMediaField extends Composite implements ClickHandler, OnFinishUploaderHandler, Configurable{
	private FlexTable basePanel;
	private Image media;
	private Button liveFeed;
	private Label takePhoto;
	private Label captureVideo;
	private Label startfeed;
	

	
	public AttachMediaField(){
		initialize();
		initWidget(basePanel);
	}

	public void createMediaField() {
		//basePanel.getFlexCellFormatter().setColSpan(0, 0, 2);
		basePanel.setWidget(0, 0, media);
		basePanel.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
	}

	private void initialize() {
		basePanel = new FlexTable();
		media = new Image("images/Media.png");
		media.setStylePrimaryName("mediaImage");
		media.addStyleName("fadeInUp");

		liveFeed = new Button("Live Feed");
	}

	public void addHandler(ClickHandler handler) {
		media.addClickHandler(handler);
		liveFeed.addClickHandler(handler);
	}
	
	public void showMediaOption() {
	//	basePanel.clear();
		
		
		HorizontalPanel mediaBasePanel = new HorizontalPanel();
		VerticalPanel mediaPanel = new VerticalPanel();
		takePhoto = new Label ("Take Photo");
		captureVideo = new Label("Capture Video");
		startfeed = new Label("Start Feed");
		
		takePhoto.setStylePrimaryName("appops-intelliThought-Label");
		captureVideo.setStylePrimaryName("appops-intelliThought-Label");
		startfeed.setStylePrimaryName("appops-intelliThought-Label");
		
		mediaPanel.add(takePhoto);
		mediaPanel.add(captureVideo);
		mediaPanel.add(startfeed);

		VerticalPanel dragDropPanel = new VerticalPanel();
		
		final String dragDropMessage = "Drag Drop Or";
		Label dragDropLabel = new Label(dragDropMessage);
		dragDropLabel.setStylePrimaryName("appops-intelliThought-Label");

		
		MultiUploader fileUploader = new MultiUploader(FileInputType.BROWSER_INPUT);
		fileUploader.avoidRepeatFiles(true);
		fileUploader.addOnFinishUploadHandler(this);
		
		dragDropPanel.add(dragDropLabel);
		dragDropPanel.add(fileUploader);
		dragDropPanel.setCellHorizontalAlignment(dragDropLabel, HasHorizontalAlignment.ALIGN_CENTER);
		dragDropPanel.setCellHorizontalAlignment(fileUploader, HasHorizontalAlignment.ALIGN_CENTER);

		
		startfeed.addClickHandler(this);
		
//		basePanel.setWidget(1, 0, mediaPanel);
//		basePanel.setWidget(1, 1, dragDropPanel);
		mediaBasePanel.add(mediaPanel);		
		mediaBasePanel.add(dragDropPanel);
		mediaBasePanel.setBorderWidth(2);
		mediaBasePanel.setCellWidth(mediaPanel, "50%");
		mediaBasePanel.setCellWidth(dragDropPanel, "50%");
		mediaBasePanel.setStylePrimaryName("fadeInDown");
		
		basePanel.setWidget(1, 0, mediaBasePanel);
		basePanel.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		
		
	}
	
	public Image getMedia() {
		return media;
	}

	public void setMedia(Image media) {
		this.media = media;
	}

	public Button getLiveFeed() {
		return liveFeed;
	}

	public void setLiveFeed(Button liveFeed) {
		this.liveFeed = liveFeed;
	}

	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(startfeed)){
			uploadMedia();
		}
	}

	private void uploadMedia() {
		basePanel.clear();
		
		HorizontalPanel attachmentPanel = new HorizontalPanel();
		
		final String attachMessage = "Attach images by dragging & dropping them or";
		Label label = new Label(attachMessage);
		
		MultiUploader fileUploader = new MultiUploader(FileInputType.BROWSER_INPUT);
		fileUploader.avoidRepeatFiles(true);
		fileUploader.addOnFinishUploadHandler(this);

		attachmentPanel.add(label);
		attachmentPanel.add(fileUploader);

		basePanel.add(attachmentPanel);
	}

	@Override
	public void onFinish(IUploader uploader) {
		if (uploader.getStatus() == Status.SUCCESS) {			
			System.out.println("Done upload");
		}
	}

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		// TODO Auto-generated method stub
		
	}
}
