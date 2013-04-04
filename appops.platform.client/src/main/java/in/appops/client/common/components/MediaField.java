package in.appops.client.common.components;

import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MediaField extends Composite implements  Configurable{
	private Image media;
	private VerticalPanel basePanel;
	private boolean isFadeUpEffect;
	private MediaAttachWidget mediaAttachWidget;
	
	public MediaField(){
		basePanel = new VerticalPanel();
		//basePanel.setBorderWidth(3);
		initWidget(basePanel);
	}

	public void showMediaField() {
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

	public void isFadeUpEffect(boolean isFadeUpEffect){
		this.isFadeUpEffect = isFadeUpEffect;
	}

	
	public void showMediaOption() {
		mediaAttachWidget = new WebMediaAttachWidget();
		basePanel.add(mediaAttachWidget);
	}
	
	public Image getMedia() {
		return media;
	}

	public void setMedia(Image media) {
		this.media = media;
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
