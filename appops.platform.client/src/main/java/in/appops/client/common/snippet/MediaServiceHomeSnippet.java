package in.appops.client.common.snippet;

import in.appops.client.gwt.web.ui.MediaViewer;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.VerticalPanel;

public class MediaServiceHomeSnippet extends VerticalPanel implements Configurable,Snippet{
	
	private Entity entity;
	private String type;
	
	public MediaServiceHomeSnippet() {
		
	}
	
	@Override
	public void initialize(){
		MediaViewer mediaViewer = new MediaViewer();
		add(mediaViewer);
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
	public ActionContext getActionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActionContext(ActionContext actionContext) {
		// TODO Auto-generated method stub
		
	}

}
