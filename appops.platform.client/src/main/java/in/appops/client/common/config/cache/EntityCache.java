package in.appops.client.common.config.cache;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.util.EntityList;

public interface EntityCache {
	
	void register(EntityCacheListener listener);
	
	EntityList getEntityList(Query query);
	
	Entity getEntity(Query query);
	
	void updateCache(Entity entity);

	void updateCache(Query query, Entity entity);
	
	void updateCache(Query query, EntityList entityList);
	
	EntityList getConfigurationList(Query query);
	
}
