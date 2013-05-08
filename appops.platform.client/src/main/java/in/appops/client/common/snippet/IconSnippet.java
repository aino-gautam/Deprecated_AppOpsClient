package in.appops.client.common.snippet;

import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.media.constant.MediaConstant;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class IconSnippet extends HorizontalPanel implements Snippet {

	private Image iconImage = null;
	private Entity entity;
	private String type;
	
	public IconSnippet() {
		
	}
	
	@Override
	public void initialize() {
		String bloId = getEntity().getProperty(MediaConstant.BLOBID).getValue().toString();
		BlobDownloader blobDownloader = new BlobDownloader();
		iconImage = new Image(blobDownloader.getImageDownloadURL(bloId));
		iconImage.addStyleName("iconSnippetImage");
		setStylePrimaryName("iconSnippetPanel");
		add(iconImage);
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
	
	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
		
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
		
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionContext getActionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActionContext(ActionContext actionContext) {
		// TODO Auto-generated method stub
		
	}

}
