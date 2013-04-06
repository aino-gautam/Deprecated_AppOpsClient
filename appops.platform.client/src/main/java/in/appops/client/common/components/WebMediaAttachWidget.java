package in.appops.client.common.components;

import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.client.common.snippet.Snippet;
import in.appops.client.common.snippet.SnippetFactory;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.server.core.services.media.constant.MediaConstant;

import java.util.HashMap;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WebMediaAttachWidget extends MediaAttachWidget{

	private VerticalPanel mainPanel = null;
	private HorizontalPanel subPanel = null;
	private HashMap<String, HorizontalPanel> uploadedBlobIdVsSnippetMap = null;
	
	public WebMediaAttachWidget(){
	}

	@Override
	void createUi() {
		initializeComponent();
		initWidget(mainPanel);
		
		mainPanel.setStylePrimaryName("appops-webMediaAttachment");
		mainPanel.setSpacing(10);
		
		MultiUploader multiUploader = getMultiUploader();
		mainPanel.add(multiUploader);
		
		subPanel.setSpacing(5);
		mainPanel.add(subPanel);
	}

	public void initializeComponent(){
		mainPanel = new VerticalPanel();
		subPanel = new HorizontalPanel();
		uploadedBlobIdVsSnippetMap = new HashMap<String, HorizontalPanel>();
	}

	public MultiUploader getMultiUploader(){
		MultiUploader fileUploader = new MultiUploader(FileInputType.BROWSER_INPUT);
		fileUploader.setAutoSubmit(true);
		fileUploader.setEnabled(true);
		fileUploader.avoidRepeatFiles(true);
		fileUploader.setVisible(true);
		String[] validExtensions = {"jpg","jpeg","gif","png"};
		fileUploader.setValidExtensions(validExtensions);

		fileUploader.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
			@Override
			public void onFinish(IUploader uploader) {
				if (uploader.getStatus() == Status.SUCCESS) {
					/** To get thumbnail of uploaded image uncomment this */
					/*new PreloadedImage(uploader.fileUrl(), new OnLoadPreloadedImageHandler() {
						@Override
						public void onLoad(PreloadedImage image) {
							image.setWidth("45px");
							image.setHeight("45px");
							uploadedImgPanel.add(image);
						}
					});*/

					uploader.getStatusWidget().setVisible(false);
					
					/** The server sends useful information to the client by default */
					UploadedInfo info = uploader.getServerInfo();

					/** This is the saved blob Id */
					String savedBlobId = info.message;
					createSnippet(savedBlobId);
				}
			}
		});
		return fileUploader;
	}

	private void createSnippet(String blobId){
		HorizontalPanel iconPanel = new HorizontalPanel();
		
		Entity entity = new Entity();
		entity.setPropertyByName(MediaConstant.BLOBID, blobId);
		
		AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
		SnippetFactory snippetFactory = injector.getSnippetFactory();
		
		Snippet snippet = snippetFactory.getSnippetByEntityType(null, "ImageUpload");
		snippet.setEntity(entity);
		snippet.initialize();
		iconPanel.add(snippet);
		
		IconWithCrossImage crossImage = new IconWithCrossImage(blobId, "images/crossIconSmall.png");
		iconPanel.add(crossImage);
		
		subPanel.add(iconPanel);
		uploadedBlobIdVsSnippetMap.put(blobId, iconPanel);
		
		crossImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				IconWithCrossImage crossImage = (IconWithCrossImage) event.getSource();
				String blbId = crossImage.getBlobId();
				if(uploadedBlobIdVsSnippetMap.containsKey(blbId)){
					HorizontalPanel snippetPanel = uploadedBlobIdVsSnippetMap.get(blbId);
					subPanel.remove(snippetPanel);
				}
			}
		});
	}
	
	IUploader.OnCancelUploaderHandler onCancelUploaderHandler = new IUploader.OnCancelUploaderHandler() {
		@Override
		public void onCancel(IUploader uploader) {
			uploader.getStatusWidget().setVisible(false);
			UploadedInfo info = uploader.getServerInfo();
			String blobId = info.message;
			if(uploadedBlobIdVsSnippetMap.containsKey(blobId)){
				HorizontalPanel snippet = uploadedBlobIdVsSnippetMap.get(blobId);
				subPanel.remove(snippet);
			}
		}
	};

	public HashMap<String, HorizontalPanel> getUploadedBlobIdVsSnippetMap() {
		return uploadedBlobIdVsSnippetMap;
	}

	public void setUploadedBlobIdVsSnippetMap(HashMap<String, HorizontalPanel> uploadedBlobIdVsSnippetMap) {
		this.uploadedBlobIdVsSnippetMap = uploadedBlobIdVsSnippetMap;
	}

}
