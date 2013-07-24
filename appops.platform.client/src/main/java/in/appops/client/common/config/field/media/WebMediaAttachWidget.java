package in.appops.client.common.config.field.media;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.AttachmentEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.client.common.snippet.Snippet;
import in.appops.client.common.snippet.SnippetFactory;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.media.constant.MediaConstant;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WebMediaAttachWidget extends MediaAttachWidget implements FieldEventHandler{


	private HashMap<String, HorizontalPanel> uploadedBlobIdVsSnippetMap = null;
	private List<String> uploadedMediaId = null;
	private MultiUploader multiUploader;
	private boolean isProfileImage;
	private String fileUploadPanelCss;
	private Logger logger = Logger.getLogger(getClass().getName());

	public WebMediaAttachWidget(){
		initializeComponent();
	}

	public void initializeComponent(){
		try {
			logger.log(Level.INFO, "[WebMediaAttachWidget] ::In initializeComponent method ");
			fileUploadPanel = new VerticalPanel();
			attachmentPanel = new HorizontalPanel();
			uploadedBlobIdVsSnippetMap = new HashMap<String, HorizontalPanel>();
			uploadedMediaId = new LinkedList<String>();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[WebMediaAttachWidget] ::Exception in initializeComponent method :"+e);
		}
	}

	public MultiUploader getMultiUploader(){
		MultiUploader fileUploader = new MultiUploader(FileInputType.BROWSER_INPUT);
		try {
			logger.log(Level.INFO, "[WebMediaAttachWidget] ::In getMultiUploader method ");
			fileUploader.setServletPath("servlet.gupld?isProfileImage=" + isProfileImage);
			fileUploader.setAutoSubmit(true);
			fileUploader.setEnabled(true);
			fileUploader.avoidRepeatFiles(true);
			fileUploader.setVisible(true);
					
			String[] validExtensions = getExtensionList().toArray(new String[getExtensionList().size()]);
			fileUploader.setValidExtensions(validExtensions);

			fileUploader.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
				@Override
				public void onFinish(IUploader uploader) {
					if (uploader.getStatus() == Status.SUCCESS) {

						uploader.getStatusWidget().setVisible(false);
						
						/** The server sends useful information to the client by default */
						UploadedInfo info = uploader.getServerInfo();

						/** This is the saved blob Id */
						String savedBlobId = info.message;
						createSnippet(savedBlobId);
						
						if(isSingleUpload) {
							multiUploader.setVisible(false);
						}
						
						AppUtils.EVENT_BUS.fireEvent(new AttachmentEvent(2, savedBlobId));
					}
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[WebMediaAttachWidget] ::Exception in getMultiUploader method :"+e);

		}
		return fileUploader;
	}

	private void createSnippet(String blobId){
		try {
			logger.log(Level.INFO, "[WebMediaAttachWidget] ::In createSnippet method ");
			HorizontalPanel iconPanel = new HorizontalPanel();
			
			Entity entity = new Entity();
			entity.setPropertyByName(MediaConstant.BLOBID, blobId);
			
			AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
			SnippetFactory snippetFactory = injector.getSnippetFactory();
			
			Snippet snippet = snippetFactory.getSnippetByEntityType(null, "ImageUpload");
			snippet.setEntity(entity);
			snippet.initialize();
			iconPanel.add(snippet);
			
			IconWithCrossImageField crossImageField = new IconWithCrossImageField();
			crossImageField.setConfiguration(getUplodedImageConfiguration(getCrossImageBlobId()));
			crossImageField.setBlobId(blobId);
			crossImageField.configure();
			crossImageField.create();
			
			iconPanel.add(crossImageField);
			
			attachmentPanel.add(iconPanel);
			uploadedBlobIdVsSnippetMap.put(blobId, iconPanel);
			if(!uploadedMediaId.contains(blobId)){
				uploadedMediaId.add(blobId);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[WebMediaAttachWidget] ::Exception in createSnippet method :"+e);

		}
		
	}
	
	IUploader.OnCancelUploaderHandler onCancelUploaderHandler = new IUploader.OnCancelUploaderHandler() {
		@Override
		public void onCancel(IUploader uploader) {
			logger.log(Level.INFO, "[WebMediaAttachWidget] ::In onCancel method ");
			uploader.getStatusWidget().setVisible(false);
			UploadedInfo info = uploader.getServerInfo();
			String blobId = info.message;
			if(uploadedBlobIdVsSnippetMap.containsKey(blobId)){
				HorizontalPanel snippet = uploadedBlobIdVsSnippetMap.get(blobId);
				uploadedMediaId.remove(blobId);
				uploadedBlobIdVsSnippetMap.remove(blobId);
				attachmentPanel.remove(snippet);
				AppUtils.EVENT_BUS.fireEvent(new AttachmentEvent(3, blobId));
			}
		}
	};

	public HashMap<String, HorizontalPanel> getUploadedBlobIdVsSnippetMap() {
		return uploadedBlobIdVsSnippetMap;
	}

	public void setUploadedBlobIdVsSnippetMap(HashMap<String, HorizontalPanel> uploadedBlobIdVsSnippetMap) {
		this.uploadedBlobIdVsSnippetMap = uploadedBlobIdVsSnippetMap;
	}

	public List<String> getUploadedMediaId() {
		return uploadedMediaId;
	}

	public void setUploadedMediaId(List<String> uploadedMediaId) {
		this.uploadedMediaId = uploadedMediaId;
	}

	@Override
	public	void createAttachmentUi() {
		try {
			logger.log(Level.INFO, "[WebMediaAttachWidget] ::In createAttachmentUi method ");
			basePanel.add(fileUploadPanel);
			fileUploadPanel.setStylePrimaryName(getFileUploadPanelCss());
			fileUploadPanel.setSpacing(10);
			
			multiUploader = getMultiUploader();
			fileUploadPanel.add(multiUploader);
			
			attachmentPanel.setSpacing(5);
			fileUploadPanel.add(attachmentPanel);
			fileUploadPanel.setVisible(false);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[WebMediaAttachWidget] ::Exception in createAttachmentUi method :"+e);

		}
	}

	private void addToAttachments() {
		try {
			logger.log(Level.INFO, "[WebMediaAttachWidget] ::In addToAttachments method ");
			for(String blobId : uploadedMediaId ){
				createSnippet(blobId);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[WebMediaAttachWidget] ::Exception in addToAttachments method :"+e);

		}
	}

	@Override
	public	void setMediaAttachments(List<String> media) {
		try {
			logger.log(Level.INFO, "[WebMediaAttachWidget] ::In setMediaAttachments method ");
			this.uploadedMediaId = media;
			if(uploadedMediaId != null && !uploadedMediaId.isEmpty()){
				addToAttachments();
			}
			expand();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[WebMediaAttachWidget] ::Exception in setMediaAttachments method :"+e);

		}
	}
	
	public void isProfileImage(boolean isProfileImage) {
		this.isProfileImage = isProfileImage;
	}

	public String getFileUploadPanelCss() {
		return fileUploadPanelCss;
	}

	public void setFileUploadPanelCss(String fileUploadPanelCss) {
		this.fileUploadPanelCss = fileUploadPanelCss;
	}
	
	private Configuration getUplodedImageConfiguration(String blobId){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO, "[WebMediaAttachWidget] ::In getUplodedImageConfiguration method ");
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, blobId);
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS, getCrossImagePrimaryCss());
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_CLICK_EVENT,FieldEvent.UPLOADED_IMGCLICKED);
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "UploadImage");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[WebMediaAttachWidget] ::Exception in getUplodedImageConfiguration method :"+e);

		}
		return configuration;
		
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			logger.log(Level.INFO, "[WebMediaAttachWidget] ::In onFieldEvent method ");
			int eventType = event.getEventType();
			switch (eventType) {
			case FieldEvent.UPLOADED_IMGCLICKED: {
				if(isSingleUpload) {
					multiUploader.setVisible(true);
				}
				
				IconWithCrossImageField crossImage = (IconWithCrossImageField) event.getSource();
				String blbId = crossImage.getBlobId();
				if(uploadedBlobIdVsSnippetMap.containsKey(blbId)){
					HorizontalPanel snippetPanel = uploadedBlobIdVsSnippetMap.get(blbId);
					uploadedBlobIdVsSnippetMap.remove(blbId);
					uploadedMediaId.remove(blbId);
					attachmentPanel.remove(snippetPanel);
					AppUtils.EVENT_BUS.fireEvent(new AttachmentEvent(3, blbId));
				}
			}
			default:
				break;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[WebMediaAttachWidget] ::Exception in onFieldEvent method :"+e);

		}
		
	}
}
