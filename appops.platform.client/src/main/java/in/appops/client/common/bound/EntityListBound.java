package in.appops.client.common.bound;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.util.EntityList;

public interface EntityListBound extends Bound{
	
	public EntityList getEntityList();
	
	public void setEntityList(EntityList entityList);
	
	public void addToList(Entity entity);
	
	public void removeFromList(Entity entity);

}
