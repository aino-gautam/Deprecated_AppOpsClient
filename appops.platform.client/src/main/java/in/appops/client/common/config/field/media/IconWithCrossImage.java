package in.appops.client.common.config.field.media;

import com.google.gwt.user.client.ui.Image;

/**
 * @author pallavi@ensarm.com
 */

public class IconWithCrossImage extends Image{

	private String blobId = null;
	
	public IconWithCrossImage(String blobId, String url) {
		this.blobId = blobId;
		setUrl(url);
		setStylePrimaryName("crossIconSmall");
	}

	public String getBlobId() {
		return blobId;
	}

	public void setBlobId(String blobId) {
		this.blobId = blobId;
	}
	
}
