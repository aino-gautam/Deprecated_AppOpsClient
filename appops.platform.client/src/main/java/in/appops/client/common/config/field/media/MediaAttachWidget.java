package in.appops.client.common.config.field.media;

import in.appops.client.common.config.field.ImageField;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.List;

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
	private String primaryCss;
	private String dependentcss;
	private String fileUploadPanelCss;
	private Configuration mediaImageConfiguration;
	private ArrayList<String> extensionList ;
	
	public MediaAttachWidget(){
		initialize();
		initWidget(basePanel);
	}
	
	private void initialize() {
		basePanel = new VerticalPanel();
	}

	public void createUi(){
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
		fileUploadPanel.setVisible(true);
		this.isExpand = true;
		this.isCollapse = false;
	}

	public boolean isCollapse() {
		return isCollapse;
	}

	public void collapse() {
		fileUploadPanel.setVisible(false);
		this.isCollapse = true;
		this.isExpand = false;
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
}
