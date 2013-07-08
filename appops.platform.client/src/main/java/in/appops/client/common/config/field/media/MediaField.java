package in.appops.client.common.config.field.media;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;
import java.util.ArrayList;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
Field class to represent a {@link Media upload field}
@author pallavi@ensarm.com


<h3>CSS Style Rules</h3>
<ul class='css'>
<li>.mediaImage { primary style for media upload image  }</li>
<li>.crossIconSmall { primary style for cross on uploaded image}</li>
<li>.appops-webMediaAttachment { class with width and height }</li>
<li>.appops-validFieldTopBottomCls { style to show valid messages outside the field }</li>
<li>.appops-validFieldIconCls { style class with valid field icon set in the background }</li>
<li>.appops-validFieldInline { style class to show valid field  }</li>
<li>.appops-suggestionText { style to show suggestion text }</li>
</ul>
<p>
<h3>Configuration</h3>
<a href="MediaField.MediaFieldConstant.html">Available configurations</a>
</p>

<p>
<h3>Example</h3>

MediaField mediaField = new MediaField();
Configuration configuration = new Configuration();
configuration.setPropertyByName(MediaFieldConstant.MF_MEDIAIMG_BLOB, "images/Media.png");
configuration.setPropertyByName(MediaFieldConstant.MF_MEDIAIMG_PCLS, "mediaImage");
configuration.setPropertyByName(MediaFieldConstant.MF_MEDIAIMG_DCLS, "fadeInUp");
configuration.setPropertyByName(MediaFieldConstant.MF_ISPROFILE_IMG, true);
configuration.setPropertyByName(MediaFieldConstant.MF_FILEUPLOADER_CLS, "appops-webMediaAttachment");
configuration.setPropertyByName(MediaFieldConstant.MF_MEDIAIMG_CLICKEVENT, FieldEvent.MEDIA_UPLOAD);

ArrayList<String> extensions = new ArrayList<String>();
extensions.add("jpg");
extensions.add("jpeg");
extensions.add("gif");
extensions.add("png");
configuration.setPropertyByName(MediaFieldConstant.MF_VALIDEXTEXNSION_LIST, extensions);
mediaField.setConfiguration(configuration);
mediaField.configure();<br>
mediaField.create();<br>

</p>*/
public class MediaField extends BaseField implements FieldEventHandler{
	
	private VerticalPanel basePanel;
	private MediaAttachWidget mediaAttachWidget;
	
	public MediaField(){
		basePanel = new VerticalPanel();
	}
	
	@Override
	public void create() {
		
		mediaAttachWidget.createUi();
		mediaAttachWidget.createAttachmentUi();
		mediaAttachWidget.collapse();
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
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
		
		mediaAttachWidget.setProfileImage(isProfileImage());
		mediaAttachWidget.setExtensionList(getExtensionList());
		mediaAttachWidget.setMediaImageConfiguration(getMediaImageConfiguration());
		mediaAttachWidget.setFileUploadPanelCss(getFileUploadPanelcss());
		mediaAttachWidget.setCrossImageBlobId(getCrossImageBlobId());
		mediaAttachWidget.setCrossImagePrimaryCss(getCrossImagePrimaryCss());
	}
	
	/**
	 * Method returns media image blobId. Defaults to "images/Media.png".
	 * @return
	 */
	private String getMediaImageBlobId() {
		
		String blobId = "images/Media.png";
		
		if(getConfigurationValue(MediaFieldConstant.MF_MEDIAIMG_BLOB) != null) {
			
			blobId = getConfigurationValue(MediaFieldConstant.MF_MEDIAIMG_BLOB).toString();
		}
		return blobId;
	}
	
	/**
	 * Method returns media image primary css. 
	 * @return
	 */
	private String getMediaImagePrimaryCss() {
		
		String primCss = "mediaImage";
		
		if(getConfigurationValue(MediaFieldConstant.MF_MEDIAIMG_PCLS) != null) {
			
			primCss = getConfigurationValue(MediaFieldConstant.MF_MEDIAIMG_PCLS).toString();
		}
		return primCss;
	}
	
	/**
	 * Method returns media image primary css.
	 * @return
	 */
	private String getCrossImagePrimaryCss() {
		
		String primCss = "crossIconSmall";
		
		if(getConfigurationValue(MediaFieldConstant.MF_CROSSIMG_PCLS) != null) {
			
			primCss = getConfigurationValue(MediaFieldConstant.MF_CROSSIMG_PCLS).toString();
		}
		return primCss;
	}
	
	/**
	 * Method returns media image dependent css.
	 * @return
	 */
	private String getMediaImageDependentCss() {
		
		String primCss = null;
		
		if(getConfigurationValue(MediaFieldConstant.MF_MEDIAIMG_DCLS) != null) {
			
			primCss = getConfigurationValue(MediaFieldConstant.MF_MEDIAIMG_DCLS).toString();
		}
		return primCss;
	}
	
	
	/**
	 * Method returns cross image blobId .
	 * @return
	 */
	private String getCrossImageBlobId() {
		
		String crossImageBlobId = "images/crossIconSmall.png";
		
		if(getConfigurationValue(MediaFieldConstant.MF_CROSSIMG_BLOBID) != null) {
			
			crossImageBlobId = getConfigurationValue(MediaFieldConstant.MF_CROSSIMG_BLOBID).toString();
		}
		return crossImageBlobId;
	}
	
	/**
	 * Method returns file upload panel css.
	 * @return
	 */
	private String getFileUploadPanelcss() {
		
		String primCss = "appops-webMediaAttachment";
		
		if(getConfigurationValue(MediaFieldConstant.MF_FILEUPLOADER_CLS) != null) {
			
			primCss = getConfigurationValue(MediaFieldConstant.MF_FILEUPLOADER_CLS).toString();
		}
		return primCss;
	}
	
	/**
	 * Method returns event that will be fired when user clicks on media upload image.
	 * @return
	 */
	private Integer getMediaImageClickEvent() {
		
		Integer clickEvent = null;
		
		if(getConfigurationValue(MediaFieldConstant.MF_MEDIAIMG_CLICKEVENT) != null) {
			
			clickEvent = (Integer) getConfigurationValue(MediaFieldConstant.MF_MEDIAIMG_CLICKEVENT);
		}
		return clickEvent;
	}
	/**
	 * Returns if profile image. Defaults to false.
	 * @return
	 */
	private Boolean isProfileImage() {
		
		Boolean isProfileImage = false;
		
		if(getConfigurationValue(MediaFieldConstant.MF_ISPROFILE_IMG) != null) {
			
			isProfileImage = (Boolean)getConfigurationValue(MediaFieldConstant.MF_ISPROFILE_IMG);
		}
		return isProfileImage;
	}
	
	
	
	/**
	 * Returns if media image should be visible. Defaults to true.
	 * @return
	 */
	private Boolean isMediaImageVisible() {
		
		Boolean isVisible = true;
		
		if(getConfigurationValue(MediaFieldConstant.MF_ISMEDIAIMG_VISIBLE) != null) {
			
			isVisible = (Boolean)getConfigurationValue(MediaFieldConstant.MF_ISMEDIAIMG_VISIBLE);
		}
		return isVisible;
	}

	
	/**
	 * Method returns the extension list.
	 * @return
	 */
	private ArrayList<String> getExtensionList() {
		
		ArrayList<String> extensionList = null;
		
		if(getConfigurationValue(MediaFieldConstant.MF_VALIDEXTEXNSION_LIST) != null) {
			
			extensionList = (ArrayList<String>) getConfigurationValue(MediaFieldConstant.MF_VALIDEXTEXNSION_LIST);
		}
		return extensionList;
	}
	/**
	 * Method creates the configuration object for media image and return.
	 * @return
	 */
	private Configuration getMediaImageConfiguration(){
		
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, getMediaImageBlobId());
		configuration.setPropertyByName(ImageFieldConstant.BF_PCLS,getMediaImagePrimaryCss());
		configuration.setPropertyByName(ImageFieldConstant.BF_DCLS,getMediaImageDependentCss());
		configuration.setPropertyByName(ImageFieldConstant.IMGFD_CLICK_EVENT,getMediaImageClickEvent());
		configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "Upload");
		return configuration;
		
	}
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		switch (eventType) {
		case FieldEvent.MEDIA_UPLOAD: {
			if(mediaAttachWidget.isExpand()){
				mediaAttachWidget.collapse();
			} else if(mediaAttachWidget.isCollapse()){
				mediaAttachWidget.expand();
			}
		}
		default:
			break;
		}
		
	}

	public interface MediaFieldConstant extends BaseFieldConstant{
		
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
		
		/** Specifies the cross image blobId**/
		public static final String MF_CROSSIMG_BLOBID = "crossImageBlobId";
		
		/** Specifies the cross image css**/
		public static final String MF_CROSSIMG_PCLS = "crossImageCss";
		
		/** Specifies id media image should be visible or not**/
		public static final String MF_ISMEDIAIMG_VISIBLE = "isMediaImageVisible";
		
		/** Specifies the event that will be fired when user clicks on media upload image**/
		public static final String MF_MEDIAIMG_CLICKEVENT = "mediaClickEvent";
		
	}
	
}
