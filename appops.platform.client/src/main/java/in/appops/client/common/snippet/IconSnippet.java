package in.appops.client.common.snippet;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class IconSnippet extends Snippet {

	HorizontalPanel basePanel = new HorizontalPanel();
	Image iconImage = new Image();

	public IconSnippet() {

		initWidget(basePanel);
		createIconSnippet();
	}

	public void createIconSnippet() {

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
	}

}
