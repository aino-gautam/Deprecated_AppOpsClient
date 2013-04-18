package in.appops.client.common.snippet;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.IsWidget;

public interface Snippet extends IsWidget {
		
	public Entity getEntity(); 
	
	public void setEntity(Entity entity); 
	
	public String getType(); 
	
	public void setType(String type);
	
	public void initialize();
	
	public void setConfiguration(Configuration configuration);
	
	public Configuration getConfiguration();

	/**	Any Data to be passed to a Snippet will/could be taken from ActionContext **/
	public ActionContext getActionContext() ;
	
	public void setActionContext(ActionContext actionContext);
	
	 
}
