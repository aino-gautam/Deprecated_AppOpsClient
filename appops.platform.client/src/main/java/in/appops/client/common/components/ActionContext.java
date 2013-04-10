package in.appops.client.common.components;

import java.util.List;

/**
 * @author nitish@ensarm.com
 */
public class ActionContext implements IActionContext{
	private List<String> uploadedMedia;
	private String spaceId;
	private IActionLabel action;
	private IIntelliThought intelliThought;
	
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
	public IActionLabel getAction() {
		return action;
	}
	
	@Override
	public void setAction(IActionLabel action) {
		this.action = action;
	}

	@Override
	public IIntelliThought getIntelliThought() {
		return intelliThought;
	}

	@Override
	public void setIntelliThought(IIntelliThought intelliThought) {
		this.intelliThought = intelliThought;
	}
}
