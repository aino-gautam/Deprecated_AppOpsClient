package in.appops.client.common.core;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.util.EntityList;

public class EntitySelectionModel extends EntityListModel{
	
	private EntityList selectedEntityList = new EntityList();
	
	public EntitySelectionModel() {
		// TODO Auto-generated constructor stub
	}

	public void addSelectedEntity(Entity selectedEntity){
		selectedEntityList.add(selectedEntity);
	}
	
	public Entity getSelectedEntity(){
		return null;
	}
	
	public void selectCurrentEntityList(){
		selectedEntityList.clear();
		selectedEntityList.addAll(getCurrentEntityList());
		
	}
	
	public void clearSelection(){
		selectedEntityList.clear();
	}
	
	public EntityList getSelectedList(){
		return selectedEntityList;
		
	}
	
	public void removeSelection(Entity entity){
		selectedEntityList.remove(entity);
	}

}
