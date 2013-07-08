package in.appops.client.common.config.field.media;

import in.appops.client.common.config.field.ImageField;

/**
 * @author pallavi@ensarm.com
 */

public class IconWithCrossImageField extends ImageField{

	private String blobId = null;
	
	public IconWithCrossImageField() {
		super();
	}
	public String getBlobId() {
		return blobId;
	}

	public void setBlobId(String blobId) {
		this.blobId = blobId;
	}
	
}
