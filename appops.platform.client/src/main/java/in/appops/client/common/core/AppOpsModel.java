package in.appops.client.common.core;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;

public interface AppOpsModel {
	
	public Entity saveEntity(Entity entity);
	
	public Entity deleteEntity(Entity entity);
	
	public Query getQueryToBind();
	
	public void setQueryToBind(Query query);

}
