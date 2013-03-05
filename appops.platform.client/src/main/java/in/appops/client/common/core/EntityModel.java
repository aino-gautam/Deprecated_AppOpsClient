package in.appops.client.common.core;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;

public class EntityModel implements AppOpsModel{
	
	private Entity entity;
	
	public EntityModel(){
		
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
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
	public Query getQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQuery() {
		// TODO Auto-generated method stub
		
	}

}
