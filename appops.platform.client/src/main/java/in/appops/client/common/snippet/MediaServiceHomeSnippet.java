package in.appops.client.common.snippet;

import in.appops.client.gwt.web.ui.MediaViewer;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.platform.coreplatformservice.constant.ServiceConstant;

import com.google.gwt.user.client.ui.VerticalPanel;

public class MediaServiceHomeSnippet extends Snippet implements Configurable{
	
	private VerticalPanel basePanel = new VerticalPanel();
	
	public MediaServiceHomeSnippet() {
		initialize();
		initWidget(basePanel);
	}
	
	@Override
	public void initialize(){
		//Entity serviceEnt = getEntity();
		//String servicename = serviceEnt.getProperty(ServiceConstant.NAME).getValue().toString();
		MediaViewer mediaViewer = new MediaViewer();
		basePanel.add(mediaViewer);
	}

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		// TODO Auto-generated method stub
		
	}

}
