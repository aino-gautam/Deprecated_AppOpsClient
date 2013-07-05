package in.appops.client.common.config.field.media;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MediaField extends BaseField implements  Configurable{
	
	private ImageField mediaImageField;
	private VerticalPanel basePanel;
	private MediaAttachWidget mediaAttachWidget;
	
	public MediaField(){
		basePanel = new VerticalPanel();
		initWidget(basePanel);
	}
	
	@Override
	public void create() {
		
		mediaAttachWidget.createUi();
		mediaImageField.setVisible(isMediaImageVisible());
		mediaAttachWidget.createAttachmentUi();
		basePanel.add(mediaAttachWidget);
		getBasePanel().add(basePanel,DockPanel.CENTER);
	}

	@Override
	public void configure() {
		
		mediaAttachWidget = new WebMediaAttachWidget();
		if(getBaseFieldPrimCss()!=null)
			mediaAttachWidget.setPrimaryCss(getBaseFieldPrimCss());
		if(getBaseFieldCss()!=null)
		mediaAttachWidget.setDependentcss(getBaseFieldCss());
		
		mediaAttachWidget.setExtensionList(getExtensionList());
		mediaAttachWidget.setMediaImageConfiguration(getMediaImageConfiguration());
		mediaAttachWidget.setFileUploadPanelCss(getFileUploadPanelcss());
	}
	
	/**
	 * Method returns media image blobId.
	 * @return
	 */
	private String getMediaImageBlobId() {
		
		String blobId = "mediaImage";
		
		if(getConfigurationValue(MediaFieldConsatnt.MF_MEDIAIMG_BLOB) != null) {
			
			blobId = getConfigurationValue(MediaFieldConsatnt.MF_MEDIAIMG_BLOB).toString();
		}
		return blobId;
	}
	
	/**
	 * Method returns media image primary css.
	 * @return
	 */
	private String getMediaImagePrimaryCss() {
		
		String primCss = null;
		
		if(getConfigurationValue(MediaFieldConsatnt.MF_MEDIAIMG_PCLS) != null) {
			
			primCss = getConfigurationValue(MediaFieldConsatnt.MF_MEDIAIMG_PCLS).toString();
		}
		return primCss;
	}
	
	/**
	 * Method returns media image dependent css.
	 * @return
	 */
	private String getMediaImageDependentCss() {
		
		String primCss = null;
		
		if(getConfigurationValue(MediaFieldConsatnt.MF_MEDIAIMG_DCLS) != null) {
			
			primCss = getConfigurationValue(MediaFieldConsatnt.MF_MEDIAIMG_DCLS).toString();
		}
		return primCss;
	}
	
	
	/**
	 * Method returns cross image blobId .
	 * @return
	 */
	private String getCrossImageBlobId() {
		
		String primCss = null;
		
		if(getConfigurationValue(MediaFieldConsatnt.MF_CROSSIMG_BLOBID) != null) {
			
			primCss = getConfigurationValue(MediaFieldConsatnt.MF_CROSSIMG_BLOBID).toString();
		}
		return primCss;
	}
	
	/**
	 * Method returns file upload panel css.
	 * @return
	 */
	private String getFileUploadPanelcss() {
		
		String primCss = null;
		
		if(getConfigurationValue(MediaFieldConsatnt.MF_FILEUPLOADER_CLS) != null) {
			
			primCss = getConfigurationValue(MediaFieldConsatnt.MF_FILEUPLOADER_CLS).toString();
		}
		return primCss;
	}
	/**
	 * Returns if profile image.
	 * @return
	 */
	private Boolean isProfileImage() {
		
		Boolean isProfileImage = null;
		
		if(getConfigurationValue(MediaFieldConsatnt.MF_ISPROFILE_IMG) != null) {
			
			isProfileImage = (Boolean)getConfigurationValue(MediaFieldConsatnt.MF_ISPROFILE_IMG);
		}
		return isProfileImage;
	}
	
	
	
	/**
	 * Returns if media image shouldbe visible. Defaults to true.
	 * @return
	 */
	private Boolean isMediaImageVisible() {
		
		Boolean isVisible = true;
		
		if(getConfigurationValue(MediaFieldConsatnt.MF_ISMEDIAIMG_VISIBLE) != null) {
			
			isVisible = (Boolean)getConfigurationValue(MediaFieldConsatnt.MF_ISMEDIAIMG_VISIBLE);
		}
		return isVisible;
	}

	
	/**
	 * Method returns the extension list.
	 * @return
	 */
	private ArrayList<String> getExtensionList() {
		
		ArrayList<String> extensionList = null;
		
		if(getConfigurationValue(MediaFieldConsatnt.MF_VALIDEXTEXNSION_LIST) != null) {
			
			extensionList = (ArrayList<String>) getConfigurationValue(MediaFieldConsatnt.MF_VALIDEXTEXNSION_LIST);
		}
		return extensionList;
	}
	/**
	 * Method creates the configuration object for media image and return.
	 * @return
	 */
	private Configuration getMediaImageConfiguration(){
		
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, "images/test2.jpg");
		configuration.setPropertyByName(ImageFieldConstant.BF_PCLS,getMediaImagePrimaryCss());
		configuration.setPropertyByName(ImageFieldConstant.BF_DCLS,getMediaImageDependentCss());
		configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "Upload");
		return configuration;
		
	}

	public interface MediaFieldConsatnt extends BaseFieldConstant{
		
		/** Specifies the blobId for media image to show **/
		public static final String MF_MEDIAIMG_BLOB = "blobId";
		
		/** Specifies the blobId for media image to show **/
		public static final String MF_MEDIAIMG_PCLS = "mediaImagePrimaryCss";
		
		/** Specifies the blobId for media image to show **/
		public static final String MF_MEDIAIMG_DCLS = "mediaImageDependentCss";
		
		/** Specifies if its profile image **/
		public static final String MF_ISPROFILE_IMG = "isProfileImg";
		
		/** Specifies the valid extensions supported**/
		public static final String MF_VALIDEXTEXNSION_LIST = "extensionList";
		
		/** Specifies the valid extensions supported**/
		public static final String MF_FILEUPLOADER_CLS = "fileUploadPanelCss";
		
		/** Specifies the cross image css**/
		public static final String MF_CROSSIMG_BLOBID = "crossImageBlobId";
		
		/** Specifies id media image should be visible or not**/
		public static final String MF_ISMEDIAIMG_VISIBLE = "isMediaImageVisible";
		
	}
	
}
