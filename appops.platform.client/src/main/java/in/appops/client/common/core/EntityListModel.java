package in.appops.client.common.core;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.util.EntityList;

public class EntityListModel implements AppOpsModel {
	
	private EntityList entityList;
	private Query query;
	
	public EntityListModel(){
		
	}

	public EntityList getEntityList() {
		// do the entity list fetching here using the query.
		
		return null;
	}

	public void setEntityList(EntityList entityList) {
		this.entityList = entityList;
	}

	@Override
	public Entity saveEntity(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity deleteEntity(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query getQueryToBind() {
		return this.query;
	}

	@Override
	public void setQueryToBind(Query query) {
		this.query = query;
	}
	

}
