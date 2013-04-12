package in.appops.client.common.components;

import java.util.List;

/**
 * @author nitish@ensarm.com
 */
public interface IActionContext {
	List<String> getUploadedMedia();
	void setUploadedMedia(List<String> uploadedMedia);
	
	String getSpaceId();
	void setSpaceId(String spaceId);
	
	IIntelliThought getIntelliThought();
	void setIntelliThought(IIntelliThought intelliThought);
	
	IActionLabel getAction();
	void setAction(IActionLabel action);
}
