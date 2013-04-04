package in.appops.client.common.components;

import java.util.ArrayList;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;
import gwtupload.client.PreloadedImage;
import gwtupload.client.PreloadedImage.OnLoadPreloadedImageHandler;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WebMediaAttachWidget extends MediaAttachWidget{

	private VerticalPanel mainPanel = null;
	private FlowPanel uploadedImgPanel = null;
	private ArrayList<String> uploadedBlobIdList = null;

	public WebMediaAttachWidget(){
		super();
	}

	@Override
	void createUi() {
		initializeComponent();
		MultiUploader multiUploader = getMultiUploader();
		mainPanel.add(multiUploader);
		mainPanel.add(uploadedImgPanel);
		initWidget(mainPanel);
	}

	public void initializeComponent(){
		mainPanel = new VerticalPanel();
		mainPanel.setStylePrimaryName("appops-webMediaAttachment");
		mainPanel.setSpacing(10);

		uploadedImgPanel = new FlowPanel();
		uploadedImgPanel.setWidth("100%");

		setUploadedBlobIdList(new ArrayList<String>());
	}

	public MultiUploader getMultiUploader(){
		MultiUploader fileUploader = new MultiUploader(FileInputType.BROWSER_INPUT);
		fileUploader.setAutoSubmit(true);
		fileUploader.setEnabled(true);
		fileUploader.avoidRepeatFiles(true);
		fileUploader.setVisible(true);
		String[] validExtensions = {"jpg","jpeg","gif","png"};
		fileUploader.setValidExtensions(validExtensions);

		/**
		 * To get thumbnail of uploaded image
		 */
		final OnLoadPreloadedImageHandler preloadedImgHandler = new OnLoadPreloadedImageHandler() {
			public void onLoad(PreloadedImage image) {
				image.setWidth("45px");
				image.setHeight("45px");
				uploadedImgPanel.add(image);
			}
		};

		fileUploader.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
			@Override
			public void onFinish(IUploader uploader) {
				if (uploader.getStatus() == Status.SUCCESS) {
					
					/** To get thumbnail of uploaded image uncomment this */
					//new PreloadedImage(uploader.fileUrl(), preloadedImgHandler);

					/** The server sends useful information to the client by default */
					UploadedInfo info = uploader.getServerInfo();

					/** This is the saved blob Id */
					String savedBlobId = info.message;
					uploadedBlobIdList.add(savedBlobId);
				}
			}
		});
		return fileUploader;
	}

	IUploader.OnCancelUploaderHandler onCancelUploaderHandler = new IUploader.OnCancelUploaderHandler() {
		@Override
		public void onCancel(IUploader uploader) {
		}
	};

	public ArrayList<String> getUploadedBlobIdList() {
		return uploadedBlobIdList;
	}

	public void setUploadedBlobIdList(ArrayList<String> uploadedBlobIdList) {
		this.uploadedBlobIdList = uploadedBlobIdList;
	}

}
