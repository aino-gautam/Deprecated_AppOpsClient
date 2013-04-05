package in.appops.client.common.snippet;

import in.appops.platform.core.entity.Entity;

import com.google.gwt.user.client.ui.Composite;

public class Snippet extends Composite{
	
	public Entity entity;
	public String type;
	
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
	
	public void initialize(){
		
	}
	
	 
}
