package in.appops.client.common.components;

import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.Composite;


public abstract class MediaAttachWidget extends Composite implements Configurable{
	
	public MediaAttachWidget(){
		createUi();
	}
	
	abstract void createUi();
	
	@Override
	public Configuration getConfiguration() {
		return null;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		
	}

}
