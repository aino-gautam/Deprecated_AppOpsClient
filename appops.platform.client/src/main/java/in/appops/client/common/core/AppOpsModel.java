package in.appops.client.common.core;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.entity.type.Type;
import in.appops.platform.core.util.EntityList;

public interface AppOpsModel {
	
	public Entity saveEntity(Entity entity);
	
	public Entity deleteEntity(Entity entity);
	
	public Query getQueryToBind();
	
	public void setQueryToBind(Query query);
	
	public EntityList getEntityList(int noOfEntities, EntityListReceiver listReceiver);
	
	public Entity getEntity(Type type, int entityId, EntityReceiver entityReceiver);

}
