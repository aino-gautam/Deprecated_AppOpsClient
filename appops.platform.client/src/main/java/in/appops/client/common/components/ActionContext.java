package in.appops.client.common.components;

import java.util.List;

/**
 * @author nitish@ensarm.com
 */
public interface ActionContext {
	public List<String> getUploadedMedia();
	public void setUploadedMedia(List<String> uploadedMedia);
	
	public String getSpaceId();
	public void setSpaceId(String spaceId);
	
	public ActionLabel getAction();
	public void setAction(ActionLabel action);
}
