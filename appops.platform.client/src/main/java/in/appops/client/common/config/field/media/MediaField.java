package in.appops.client.common.config.field.media;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
</ul>
<p>
<h3>Configuration</h3>
<a href="MediaField.MediaFieldConstant.html">Available configurations</a>
</p>

<p>
<h3>Example</h3>

MediaField mediaField = new MediaField();<br>
Configuration configuration = new Configuration();<br>
configuration.setPropertyByName(MediaFieldConstant.MF_MEDIAIMG_BLOB, "images/Media.png");<br>
configuration.setPropertyByName(MediaFieldConstant.MF_MEDIAIMG_PCLS, "mediaImage");<br>
configuration.setPropertyByName(MediaFieldConstant.MF_MEDIAIMG_DCLS, "fadeInUp");<br>
configuration.setPropertyByName(MediaFieldConstant.MF_ISPROFILE_IMG, true);<br>
configuration.setPropertyByName(MediaFieldConstant.MF_FILEUPLOADER_CLS, "appops-webMediaAttachment");<br>
<br>
ArrayList<String> extensions = new ArrayList<String>();<br>
extensions.add("jpg");<br>
extensions.add("jpeg");<br>
extensions.add("gif");<br>
extensions.add("png");<br>
configuration.setPropertyByName(MediaFieldConstant.MF_VALIDEXTEXNSION_LIST, extensions);<br>
mediaField.setConfiguration(configuration);<br>
mediaField.configure();<br>
mediaField.create();<br>

</p>*/
public class MediaField extends BaseField implements FieldEventHandler{
	
	private VerticalPanel basePanel;
	private MediaAttachWidget mediaAttachWidget;
	private Logger logger = Logger.getLogger(getClass().getName());
	private static String MEDIA_IMAGEID = "mediaUpload";

	public MediaField(){
		basePanel = new VerticalPanel();
	}
	
	@Override
	public void create() {
		
		try {
			logger.log(Level.INFO, "[MediaField] ::In create method ");
			mediaAttachWidget.createUi();
			mediaAttachWidget.createAttachmentUi();
			if(!isMediaImageVisible()){
				mediaAttachWidget.isMediaImageVisible(false);
				mediaAttachWidget.expand();
			}else{
			   mediaAttachWidget.collapse();
			}
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
			basePanel.add(mediaAttachWidget);
			getBasePanel().add(basePanel,DockPanel.CENTER);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaField] ::Exception In create method :"+e);
		}
	}

	@Override
	public void configure() {
		
		try {
			logger.log(Level.INFO, "[MediaField] ::In configure method ");
			mediaAttachWidget = new WebMediaAttachWidget();
			if(getBaseFieldPrimCss()!=null)
				mediaAttachWidget.setPrimaryCss(getBaseFieldPrimCss());
			if(getBaseFieldDependentCss()!=null)
			mediaAttachWidget.setDependentcss(getBaseFieldDependentCss());
			
			if (getBasePanelPrimCss() != null)
				getBasePanel().setStylePrimaryName(getBasePanelPrimCss());
			if (getBasePanelDependentCss() != null)
				getBasePanel().addStyleName(getBasePanelDependentCss());
			
			
			mediaAttachWidget.setProfileImage(isProfileImage());
			mediaAttachWidget.setExtensionList(getExtensionList());
			mediaAttachWidget.setMediaImageConfiguration(getMediaImageConfiguration());
			mediaAttachWidget.setFileUploadPanelCss(getFileUploadPanelcss());
			mediaAttachWidget.setCrossImageBlobId(getCrossImageBlobId());
			mediaAttachWidget.setCrossImagePrimaryCss(getCrossImagePrimaryCss());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaField] ::Exception In configure method :"+e);

		}
	}
	
	/**
	 * Method returns media image blobId. Defaults to "images/Media.png".
	 * @return
	 */
	private String getMediaImageBlobId() {
		
		String blobId = "images/Media.png";
		
		try {
			logger.log(Level.INFO, "[MediaField] ::In getMediaImageBlobId method ");
			if(getConfigurationValue(MediaFieldConstant.MF_MEDIAIMG_BLOB) != null) {
				
				blobId = getConfigurationValue(MediaFieldConstant.MF_MEDIAIMG_BLOB).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaField] ::Exception In getMediaImageBlobId method :"+e);

		}
		return blobId;
	}
	
	/**
	 * Method returns media image primary css. 
	 * @return
	 */
	private String getMediaImagePrimaryCss() {
		
		String primCss = "mediaImage";
		
		try {
			logger.log(Level.INFO, "[MediaField] ::In getMediaImagePrimaryCss method ");
			if(getConfigurationValue(MediaFieldConstant.MF_MEDIAIMG_PCLS) != null) {
				
				primCss = getConfigurationValue(MediaFieldConstant.MF_MEDIAIMG_PCLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaField] ::Exception In getMediaImagePrimaryCss method :"+e);

		}
		return primCss;
	}
	
	/**
	 * Method returns media image primary css.
	 * @return
	 */
	private String getCrossImagePrimaryCss() {
		
		String primCss = "crossIconSmall";
		
		try {
			logger.log(Level.INFO, "[MediaField] ::In getCrossImagePrimaryCss method ");
			if(getConfigurationValue(MediaFieldConstant.MF_CROSSIMG_PCLS) != null) {
				
				primCss = getConfigurationValue(MediaFieldConstant.MF_CROSSIMG_PCLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaField] ::Exception In getCrossImagePrimaryCss method :"+e);

		}
		return primCss;
	}
	
	/**
	 * Method returns media image dependent css.
	 * @return
	 */
	private String getMediaImageDependentCss() {
		
		String primCss = null;
		
		try {
			logger.log(Level.INFO, "[MediaField] ::In getMediaImageDependentCss method ");
			if(getConfigurationValue(MediaFieldConstant.MF_MEDIAIMG_DCLS) != null) {
				
				primCss = getConfigurationValue(MediaFieldConstant.MF_MEDIAIMG_DCLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaField] ::Exception In getMediaImageDependentCss method :"+e);

		}
		return primCss;
	}
	
	
	/**
	 * Method returns cross image blobId .
	 * @return
	 */
	private String getCrossImageBlobId() {
		
		String crossImageBlobId = "images/crossIconSmall.png";
		
		try {
			logger.log(Level.INFO, "[MediaField] ::In getCrossImageBlobId method ");
			if(getConfigurationValue(MediaFieldConstant.MF_CROSSIMG_BLOBID) != null) {
				
				crossImageBlobId = getConfigurationValue(MediaFieldConstant.MF_CROSSIMG_BLOBID).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaField] ::Exception In getCrossImageBlobId method :"+e);

		}
		return crossImageBlobId;
	}
	
	/**
	 * Method returns file upload panel css.
	 * @return
	 */
	private String getFileUploadPanelcss() {
		
		String primCss = "appops-webMediaAttachment";
		
		try {
			logger.log(Level.INFO, "[MediaField] ::In getFileUploadPanelcss method ");
			if(getConfigurationValue(MediaFieldConstant.MF_FILEUPLOADER_CLS) != null) {
				
				primCss = getConfigurationValue(MediaFieldConstant.MF_FILEUPLOADER_CLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaField] ::Exception In getFileUploadPanelcss method :"+e);

		}
		return primCss;
	}
	
	/**
	 * Returns if profile image. Defaults to false.
	 * @return
	 */
	private Boolean isProfileImage() {
		
		Boolean isProfileImage = false;
		
		try {
			logger.log(Level.INFO, "[MediaField] ::In isProfileImage method ");
			if(getConfigurationValue(MediaFieldConstant.MF_ISPROFILE_IMG) != null) {
				
				isProfileImage = (Boolean)getConfigurationValue(MediaFieldConstant.MF_ISPROFILE_IMG);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaField] ::Exception In isProfileImage method :"+e);

		}
		return isProfileImage;
	}
	
	
	
	/**
	 * Returns if media image should be visible. Defaults to true.
	 * @return
	 */
	private Boolean isMediaImageVisible() {
		
		Boolean isVisible = true;
		
		try {
			logger.log(Level.INFO, "[MediaField] ::In isMediaImageVisible method ");
			if(getConfigurationValue(MediaFieldConstant.MF_ISMEDIAIMG_VISIBLE) != null) {
				
				isVisible = (Boolean)getConfigurationValue(MediaFieldConstant.MF_ISMEDIAIMG_VISIBLE);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaField] ::Exception In isMediaImageVisible method :"+e);

		}
		return isVisible;
	}

	
	/**
	 * Method returns the extension list.
	 * @return
	 */
	private ArrayList<String> getExtensionList() {
		
		ArrayList<String> extensionList = null;
		
		try {
			logger.log(Level.INFO, "[MediaField] ::In getExtensionList method ");
			if(getConfigurationValue(MediaFieldConstant.MF_VALIDEXTEXNSION_LIST) != null) {
				
				extensionList = (ArrayList<String>) getConfigurationValue(MediaFieldConstant.MF_VALIDEXTEXNSION_LIST);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaField] ::Exception In getExtensionList method :"+e);

		}
		return extensionList;
	}
	/**
	 * Method creates the configuration object for media image and return.
	 * @return
	 */
	private Configuration getMediaImageConfiguration(){
		
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO, "[MediaField] ::In getMediaImageConfiguration method ");
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, getMediaImageBlobId());
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS,getMediaImagePrimaryCss());
			configuration.setPropertyByName(ImageFieldConstant.BF_DCLS,getMediaImageDependentCss());
			configuration.setPropertyByName(ImageFieldConstant.BF_ID,MEDIA_IMAGEID);
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "Upload");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaField] ::Exception In getMediaImageConfiguration method :"+e);

		}
		return configuration;
		
	}
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			logger.log(Level.INFO, "[MediaField] ::In onFieldEvent method ");
			int eventType = event.getEventType();
			switch (eventType) {
			case FieldEvent.CLICKED: {
				if(event.getEventSource() instanceof ImageField){
					ImageField mediaImg = (ImageField)event.getEventSource();
					if(mediaImg.getBaseFieldId().equalsIgnoreCase(MEDIA_IMAGEID)){
						if(mediaAttachWidget.isExpand()){
							mediaAttachWidget.collapse();
						} else if(mediaAttachWidget.isCollapse()){
							mediaAttachWidget.expand();
						}
					}
					
				}
				
			}
			default:
				break;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaField] ::Exception In onFieldEvent method :"+e);

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
		
	}
	
}