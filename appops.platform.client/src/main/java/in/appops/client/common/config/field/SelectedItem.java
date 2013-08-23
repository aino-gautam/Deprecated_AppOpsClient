package in.appops.client.common.config.field;

import in.appops.platform.core.entity.Entity;

public class SelectedItem{
	
	private String itemString;
	private Entity associatedEntity;
	
	public String getItemString() {
		return itemString;
	}
	public void setItemString(String itemString) {
		this.itemString = itemString;
	}
	public Entity getAssociatedEntity() {
		return associatedEntity;
	}
	public void setAssociatedEntity(Entity associatedEntity) {
		this.associatedEntity = associatedEntity;
	}
	
	
	
	
}
