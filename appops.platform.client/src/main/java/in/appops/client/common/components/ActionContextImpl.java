package in.appops.client.common.components;

import java.util.List;

/**
 * @author nitish@ensarm.com
 */
public class ActionContextImpl implements ActionContext{
	private List<String> uploadedMedia;
	private String spaceId;
	private ActionLabel action;
	
	@Override
	public List<String> getUploadedMedia() {
		return uploadedMedia;
	}
	
	@Override
	public void setUploadedMedia(List<String> uploadedMedia) {
		this.uploadedMedia = uploadedMedia;
	}
	
	@Override
	public String getSpaceId() {
		return spaceId;
	}
	
	@Override
	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}
	
	@Override
	public ActionLabel getAction() {
		return action;
	}
	
	@Override
	public void setAction(ActionLabel action) {
		this.action = action;
	}
}
