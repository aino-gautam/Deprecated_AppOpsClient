package in.appops.client.common.config.field.media;

import in.appops.client.common.config.field.ImageField;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public abstract class MediaAttachWidget extends Composite implements Configurable{
	private ImageField mediaImageField;
	protected VerticalPanel basePanel;
	private boolean isExpand;
	private boolean isCollapse;
	protected VerticalPanel fileUploadPanel = null;
	protected HorizontalPanel attachmentPanel = null;
	protected boolean isSingleUpload;
	
	protected boolean isProfileImage;
	private String primaryCss;
	private String dependentcss;
	private String fileUploadPanelCss;
	private Configuration mediaImageConfiguration;
	private ArrayList<String> extensionList ;
	private String crossImageBlobId;
	private String crossImagePrimaryCss;
	private Logger logger = Logger.getLogger(getClass().getName());
	public MediaAttachWidget(){
		initialize();
		initWidget(basePanel);
	}
	
	private void initialize() {
		basePanel = new VerticalPanel();
	}

	public void createUi(){
		try {
			logger.log(Level.INFO, "[MediaAttachWidget] ::In createUi method ");
			HorizontalPanel mediaPanel = new HorizontalPanel();
			if(getPrimaryCss()!=null)
				mediaPanel.setStylePrimaryName(getPrimaryCss());
			
			if(getDependentcss()!=null)
				mediaPanel.setStylePrimaryName(getDependentcss());
			
			basePanel.add(mediaPanel);
			
			mediaImageField = new ImageField();
			mediaImageField.setConfiguration(getMediaImageConfiguration());
			mediaImageField.configure();
			mediaImageField.create();
			
			mediaPanel.add(mediaImageField);
			mediaPanel.setCellHorizontalAlignment(mediaImageField, HasHorizontalAlignment.ALIGN_RIGHT);
			mediaPanel.setCellVerticalAlignment(mediaImageField, HasVerticalAlignment.ALIGN_MIDDLE);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaAttachWidget] ::Exception In createUi method :"+e);
		}
	}
	
	@Override
	public Configuration getConfiguration() {
		return null;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		
	}
	
	public abstract void createAttachmentUi();
	public abstract void setMediaAttachments(List<String> media);

	public boolean isExpand() {
		return isExpand;
	}

	public void expand() {
		try {
			logger.log(Level.INFO, "[MediaAttachWidget] ::In expand method ");
			fileUploadPanel.setVisible(true);
			this.isExpand = true;
			this.isCollapse = false;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaAttachWidget] ::Exception In expand method :"+e);
		}
	}

	public boolean isCollapse() {
		return isCollapse;
	}

	public void collapse() {
		try {
			logger.log(Level.INFO, "[MediaAttachWidget] ::In collapse method ");
			fileUploadPanel.setVisible(false);
			this.isCollapse = true;
			this.isExpand = false;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[MediaAttachWidget] ::Exception In collapse method :"+e);
		}
	}
	
	public void isMediaImageVisible(boolean visible) {
		mediaImageField.setVisible(visible);
	}
	
	public void isSingleUpload(boolean isSingleUpload) {
		this.isSingleUpload = isSingleUpload;
	}

	public String getPrimaryCss() {
		return primaryCss;
	}

	public void setPrimaryCss(String primaryCss) {
		this.primaryCss = primaryCss;
	}

	public String getDependentcss() {
		return dependentcss;
	}

	public void setDependentcss(String dependentcss) {
		this.dependentcss = dependentcss;
	}

	public Configuration getMediaImageConfiguration() {
		return mediaImageConfiguration;
	}

	public void setMediaImageConfiguration(Configuration mediaImageConfiguration) {
		this.mediaImageConfiguration = mediaImageConfiguration;
	}

	public ArrayList<String> getExtensionList() {
		return extensionList;
	}

	public void setExtensionList(ArrayList<String> extensionList) {
		this.extensionList = extensionList;
	}

	public String getFileUploadPanelCss() {
		return fileUploadPanelCss;
	}

	public void setFileUploadPanelCss(String fileUploadPanelCss) {
		this.fileUploadPanelCss = fileUploadPanelCss;
	}

	public boolean isProfileImage() {
		return isProfileImage;
	}

	public void setProfileImage(boolean isProfileImage) {
		this.isProfileImage = isProfileImage;
	}

	public ImageField getMediaImageField() {
		return mediaImageField;
	}

	public void setMediaImageField(ImageField mediaImageField) {
		this.mediaImageField = mediaImageField;
	}

	public String getCrossImageBlobId() {
		return crossImageBlobId;
	}

	public void setCrossImageBlobId(String crossImageBlobId) {
		this.crossImageBlobId = crossImageBlobId;
	}

	public String getCrossImagePrimaryCss() {
		return crossImagePrimaryCss;
	}

	public void setCrossImagePrimaryCss(String crossImagePrimaryCss) {
		this.crossImagePrimaryCss = crossImagePrimaryCss;
	}
}
