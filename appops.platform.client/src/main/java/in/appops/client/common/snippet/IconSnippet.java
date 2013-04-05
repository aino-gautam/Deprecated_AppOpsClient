package in.appops.client.common.snippet;

import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.server.core.services.media.constant.MediaConstant;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class IconSnippet extends Snippet {

	private HorizontalPanel basePanel = null;
	private Image iconImage = null;
	
	public IconSnippet() {
		basePanel = new HorizontalPanel();
		initWidget(basePanel);
		//createIconSnippet();
	}
	
	@Override
	public void initialize() {
		String bloId = getEntity().getProperty(MediaConstant.BLOBID).getValue().toString();
		BlobDownloader blobDownloader = new BlobDownloader();
		iconImage = new Image(blobDownloader.getImageDownloadURL(bloId));
		iconImage.addStyleName("iconSnippetImage");
		basePanel.setStylePrimaryName("iconSnippetPanel");
		basePanel.add(iconImage);
	}
	
	/*public void createIconSnippet() {
		iconImage.setUrl("images/userIcon.jpg");
		iconImage.addStyleName("iconSnippetImage");
		basePanel.setStylePrimaryName("iconSnippetPanel");
		basePanel.add(iconImage);
	}

	public HorizontalPanel getBasePanel() {
		return basePanel;
	}

	public void setBasePanel(HorizontalPanel basePanel) {
		this.basePanel = basePanel;
	}

	public Image getIconLogo() {
		return iconImage;
	}

	public void setIconLogo(Image iconLogo) {
		this.iconImage = iconLogo;
	}*/

}
