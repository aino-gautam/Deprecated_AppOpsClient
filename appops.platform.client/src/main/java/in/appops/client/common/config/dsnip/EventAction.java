package in.appops.client.common.config.dsnip;

import in.appops.platform.core.shared.Configuration;

import java.util.HashMap;

@SuppressWarnings("serial")
public class EventAction extends Configuration {
	
	public String getEventName() {
		return null;
	}
	
	public String getTransformSnippet() {
		return null;
	}
	
	public String getSnippetInstance() {
		return null;
	}

	public HashMap<String, Object> getConfigurationToUpdateMap() {
		return null;
	}
	
	public boolean hasTransformation() {
		return false;
	}

	public boolean hasConfigurationUpdation() {
		return false;
	}
	
}
