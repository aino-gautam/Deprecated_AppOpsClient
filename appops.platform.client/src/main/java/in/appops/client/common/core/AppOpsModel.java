package in.appops.client.common.core;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.entity.type.Type;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.util.EntityList;

public interface AppOpsModel extends Configurable{
	
	public Entity saveEntity(Entity entity);
	
	public Entity deleteEntity(Entity entity);
	
	public Query getQueryToBind();
	
	public void setQueryToBind(Query query);
	
	public EntityList getEntityList(int noOfEntities, EntityListReceiver listReceiver);
	
	public Entity getEntity(String op, Long entityId, EntityReceiver entityReceiver);
	
	public String getOperationNameToBind();
	
	public void setOperationNameToBind(String name);
	
	public void addInterestingType(Type type);
	
	public boolean isInterestingType(Type type);
	
	public void setBroadcastEntity(Entity entity);
	
	public void configure();

}
