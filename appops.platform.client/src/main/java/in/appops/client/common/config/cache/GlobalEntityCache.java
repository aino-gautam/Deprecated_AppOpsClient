package in.appops.client.common.config.cache;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.util.EntityList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class GlobalEntityCache implements EntityCache {

	/**
	 * Storage for entity identifier vs. entity
	 * Entity Identifier = service id + entity type + entity id
	 */
	private Map<String, Entity> entityCache;

	/** Keeps track of query identifier vs. list of entity identifier for the resultant entity list
	 *	Query Identifier = query name + [paramName = paramValue...] 
	 */
	private Map<String, ArrayList<String>> queryCache;
	
	/** List of all the models registered for data from cache */
	private ArrayList<EntityCacheListener> registeredListeners;
	
	private static final GlobalEntityCache globalEntityCache = new GlobalEntityCache();
	
	private static final String IS_ENTITYLIST_QUERY = "isQueryEntityList";

	private GlobalEntityCache() {
		entityCache = new HashMap<String, Entity>();
		queryCache = new HashMap<String, ArrayList<String>>();
	}

	public static GlobalEntityCache getInstance() {
		return globalEntityCache;
	}

	@Override
	public void register(EntityCacheListener listener) {
		if(registeredListeners == null || registeredListeners.isEmpty()) {
			registeredListeners = new ArrayList<EntityCacheListener>();
		}
		registeredListeners.add(listener);
	}
	
	@Override
	public EntityList getEntityList(Query query) {
		String queryIdentifier = getQueryIdentifier(query);
		
		if(queryCache.containsKey(queryIdentifier)) {
			EntityList entityList = null;
			
			ArrayList<String> entityPointerList = queryCache.get(queryIdentifier);
			
			if(entityPointerList != null && !entityPointerList.isEmpty()) {
				entityList = new EntityList();
				for(String entityPointer : entityPointerList) {
					Entity entity = entityCache.get(entityPointer);
					if(entity != null) {
						entityList.add(entity);
					}
				}
				if(!entityList.isEmpty()) {
					return entityList;
				}
			}
		}
		return null;
	}

	@Override
	public Entity getEntity(Query query) {
		String queryIdentifier = getQueryIdentifier(query);
		
		if(queryCache.containsKey(queryIdentifier)) {
			ArrayList<String> entityPointerList = queryCache.get(queryIdentifier);
			
			if(entityPointerList != null && !entityPointerList.isEmpty()) {
				String entityPointer = entityPointerList.get(0);
				Entity entity = entityCache.get(entityPointer);
				if(entity != null) {
					return entity;
				}
			}
		}
		return null;
	}

	@Override
	public void updateCache(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCache(Query query, Entity entity) {
		if(entity != null) {
			EntityList entityList = new EntityList();
			entityList.add(entity);
			
			query.setPropertyByName(IS_ENTITYLIST_QUERY, false);
			
			updateCache(query, entityList);
		}
	}

	@Override
	public void updateCache(Query query, EntityList entityList) {
		String queryIdentifier = getQueryIdentifier(query);
		ArrayList<String> entityPointerList = null;
		
		if(entityList != null && !entityList.isEmpty()) {
			entityPointerList = new ArrayList<String>();
			for(Entity entity : entityList) {
				String entityIdentifier = getEntityIdentifer(entity);
				
	/*			 TODO Check for partial entity.
				
				if(!entityCache.containsKey(entityIdentifier) || (entityCache.containsKey(entityIdentifier) && !entity.isPartial())) {
					entityCache.put(entityIdentifier, entity);
				}
	*/		
				entityCache.put(entityIdentifier, entity);
				entityPointerList.add(entityIdentifier);
			}
			queryCache.put(queryIdentifier, entityPointerList);
			
			if(query.getPropertyByName(IS_ENTITYLIST_QUERY) == null) {
				query.setPropertyByName(IS_ENTITYLIST_QUERY, true);
			}
			fireQueryUpdated(query);
		}
	}
	
	public static String getEntityIdentifer(Entity entity) {
		final String splitter = "##";
		final String dotSeperator = ".";
		String entityType = entity.getType().getTypeName();
		
		if(entityType.contains(dotSeperator)) {
			entityType = entityType.substring(entityType.lastIndexOf(dotSeperator) + 1);
		}
		
		Long serviceId = entity.getType().getServiceId();
		
		Key<Long> key = entity.getPropertyByName("id");
		Long entityId = key.getKeyValue();
		
		String identifier = (serviceId != null ? serviceId.toString() + splitter : "") + entityType + splitter + entityId.toString();

		return identifier;
	}
	
	public static String getQueryIdentifier(Query query) {
		final String querySplitter = "?";
		final String paramSplitter = "&&";
		final String valueAssign = "=";
		String queryIdentifier = null;
		
		String queryName = query.getQueryName();
		Map<String, Object> paramMap = query.getQueryParameters();
		
		queryIdentifier = queryName;
		
		if(paramMap != null && !paramMap.isEmpty()) {
			queryIdentifier += querySplitter;

			Iterator<Entry<String, Object>> mapItr = paramMap.entrySet().iterator();
			while (mapItr.hasNext()) {
			
				Entry<String, Object> paramEntry = mapItr.next();
				String paramName = paramEntry.getKey();
				Object paramValue = paramEntry.getValue();
				
				queryIdentifier = queryIdentifier + paramName + valueAssign + paramValue.toString();
				
				if(mapItr.hasNext()) {
					queryIdentifier += paramSplitter;
				}
			}
		}
		
		return queryIdentifier;
	}
	
	private synchronized void fireQueryUpdated(Query updatedQuery) {
		
		String updatedQueryPointer = getQueryIdentifier(updatedQuery);
		boolean isEntityListQuery = (Boolean)updatedQuery.getPropertyByName(IS_ENTITYLIST_QUERY);
		
		for (EntityCacheListener registeredListner : registeredListeners) {

			ArrayList<String> entityPointerList = queryCache.get(updatedQueryPointer);
			
			if(entityPointerList != null && !entityPointerList.isEmpty()) {
				if(isEntityListQuery) {
					EntityList entityList = getEntityListFromPointer(entityPointerList);
					registeredListner.onQueryUpdated(updatedQueryPointer, entityList);
				} else {
					String entityPointer = entityPointerList.get(0);
					Entity entity = getEntityFromPointer(entityPointer);
					registeredListner.onQueryUpdated(updatedQueryPointer, entity);
				}
			}
		}
	}

	private EntityList getEntityListFromPointer(ArrayList<String> entityPointerList) {
		EntityList entityList = null;
		
		for(String entityPointer : entityPointerList) {
			Entity entity = getEntityFromPointer(entityPointer);
			if(entity != null) {
				if(entityList == null) {
					entityList = new EntityList();
				}
				entityList.add(entity);
			}
		}
		return entityList;
	}

	private Entity getEntityFromPointer(String entityPointer) {
		Entity entity = entityCache.get(entityPointer);
		return entity;
	}

	public void setQueryCache(Map<String, ArrayList<String>> queryCache) {
		this.queryCache = queryCache;
	}

	public void setEntityCache(Map<String, Entity> entityCache) {
		this.entityCache = entityCache;
	}
	
	@Override
	public EntityList getConfigurationList(Query query) {
		EntityList configurationList = getEntityList(query);
		return configurationList;
	}
}