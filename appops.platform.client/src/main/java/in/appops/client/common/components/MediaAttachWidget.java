package in.appops.client.common.components;

import java.util.List;

import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;


public abstract class MediaAttachWidget extends Composite implements Configurable{
	private Image media;
	protected VerticalPanel basePanel;
	private boolean isFadeUpEffect;
	private boolean isExpand;
	private boolean isCollapse;
	protected VerticalPanel fileUploadPanel = null;
	protected HorizontalPanel attachmentPanel = null;
	
	public MediaAttachWidget(){
		initialize();
		initWidget(basePanel);
	}
	
	private void initialize() {
		basePanel = new VerticalPanel();
	}

	public void isFadeUpEffect(boolean isFadeUpEffect){
		this.isFadeUpEffect = isFadeUpEffect;
	}
	
	public Image getMedia() {
		return media;
	}

	public void setMedia(Image media) {
		this.media = media;
	}
	
	public void createUi(){
		HorizontalPanel mediaPanel = new HorizontalPanel();
		mediaPanel.setStylePrimaryName("appops-webMediaAttachment");
		basePanel.add(mediaPanel);
		
		media = new Image("images/Media.png");
		media.setStylePrimaryName("mediaImage");
		
		if(isFadeUpEffect){
			media.addStyleName("fadeInUp");
		}

		mediaPanel.add(media);
		mediaPanel.setCellHorizontalAlignment(media, HasHorizontalAlignment.ALIGN_RIGHT);
		mediaPanel.setCellVerticalAlignment(media, HasVerticalAlignment.ALIGN_MIDDLE);
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
		media.setVisible(visible);
	}
	
}
