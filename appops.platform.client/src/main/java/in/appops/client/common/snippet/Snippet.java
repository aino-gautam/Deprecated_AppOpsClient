package in.appops.client.common.snippet;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;

import com.google.gwt.user.client.ui.Composite;

public class Snippet extends Composite{
	
	public Entity entity;
	public String type;
	
	/**	Any Data to be passed to a Snippet will could be taken from ActionContext **/
	private ActionContext actionContext;
	
	public Entity getEntity() {
		return entity;
	}
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public ActionContext getActionContext() {
		return actionContext;
	}
	
	public void setActionContext(ActionContext actionContext) {
		this.actionContext = actionContext;
	}
	
	public void initialize(){
		
	}
	
	 
}
