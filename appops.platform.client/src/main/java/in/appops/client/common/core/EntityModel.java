package in.appops.client.common.core;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.entity.type.Type;
import in.appops.platform.core.util.EntityList;

public class EntityModel implements AppOpsModel{
	
	private Entity entity;
	private Query query;
	
	public EntityModel(){
		
	}

	
	public Entity getEntity() {
		// do the entity fetch here using the query
		return null;
	}

	/**
	 * sets the fetched Entity on the model.
	 * @param entity Entity
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * Saves the entity to the database.
	 */
	@Override
	public Entity saveEntity(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Deletes the passed entity from the database.
	 */
	@Override
	public Entity deleteEntity(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the {@link Query} to which the entity model binds to
	 */
	@Override
	public Query getQueryToBind() {
		return this.query;
	}

	/**
	 * Set the {@link Query} object to bind to in order to carry out database operations.
	 */
	@Override
	public void setQueryToBind(Query query) {
		this.query = query;
	}

	/**
	 * Makes a server call to fetch the requested entitylist using the specified query to bind to
	 * @return EntityList
	 */
	@Override
	public EntityList getEntityList(int noOfEntities,
			EntityListReceiver listReceiver) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Makes a server call to fetch the requested entity using the specified query to bind to for the type and 
	 * entity id provided
	 * @return Entity
	 */
	@Override
	public Entity getEntity(Type type, int entityId,
			EntityReceiver entityReceiver) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getOperationNameToBind() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setOperationNameToBind(String name) {
		// TODO Auto-generated method stub
		
	}

}
