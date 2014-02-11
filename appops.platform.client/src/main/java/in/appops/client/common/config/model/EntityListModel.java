package in.appops.client.common.config.model;

import in.appops.client.common.core.EntityListReceiver;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class EntityListModel extends AppopsBaseModel {

	protected EntityListReceiver listReceiver;
	private EntityList entityList;
	private int START_INDEX = 0;
	
	@Override
	public void configure() {
		super.configure();
	}
	
	public void fetchEntityList() {
				
		String queryName = getQueryName();
		HashMap<String, Object> queryParamMap = null;

		//if(hasQueryParam()) {
			Configuration queryParam = getQueryParameters();
			
			
			Set<Entry<String, Property<? extends Serializable>>> confSet = queryParam.getValue().entrySet();
			
			if(confSet != null && !confSet.isEmpty()) {
				queryParamMap = new HashMap<String, Object>();
				for(Entry<String, Property<? extends Serializable>> entry : confSet) {
					String paramName = entry.getKey();
					Serializable value = entry.getValue().getValue();
					queryParamMap.put(paramName, value);
				}
			}
		//}
		if(queryParamMap == null || (queryParamMap != null && !queryParamMap.isEmpty())) {
			Query query = new Query();
			query.setQueryName(queryName);
			query.setQueryParameterMap(queryParamMap);
			
			if(!isFetchAll()) {
				query.setListSize(getFetchSize());
				query.setStartIndex(START_INDEX);
			}
			
/*			EntityList entityList = globalEntityCache.getEntityList(query);
			if(entityList != null && !entityList.isEmpty()) {
				listReceiver.onEntityListReceived(entityList);
			}
			
			collect(entityList);
			
			String querypPointer = GlobalEntityCache.getQueryIdentifier(query);
			
			if(!interestedQueryList.contains(querypPointer)) {
				interestedQueryList.add(querypPointer);
			}*/
			executeQuery(query);
		}
	}
	
	private void collect(EntityList entityList) {
		try {
			if(this.entityList == null) {
				this.entityList = entityList;
				return;
			}
			this.entityList.addAll(entityList); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isEmpty() {
		return (entityList != null && !entityList.isEmpty()) ? false : true;
	}
	
	@Override
	public void onQueryUpdated(String query, Serializable data) {
		if(isInterestingQuery(query)) {
			EntityList entityList = (EntityList)data;
			listReceiver.onEntityListReceived(entityList);
			collect(entityList);
		}
	}

	public void setReceiver(EntityListReceiver listReceiver) {
		this.listReceiver = listReceiver;
	}

	public EntityList getEntityList() {
		return entityList;
	}
	
	private Boolean isFetchAll() {
		Boolean param = true;
		if(getConfigurationValue(EntityListModelConstant.FETCH_ALL) != null) {
			param = (Boolean) getConfigurationValue(EntityListModelConstant.FETCH_ALL);
		}
		return param;
	}
	
	private int getFetchSize() {
		int size = 5;
		if(getConfigurationValue(EntityListModelConstant.FETCH_SIZE) != null) {
			size = (Integer) getConfigurationValue(EntityListModelConstant.FETCH_SIZE);
		}
		return size;
	}
	
	public interface EntityListModelConstant extends AppopsModelConstant {
		String FETCH_ALL = "fetchAll";
		String FETCH_SIZE = "fetchSize";
	}

	@Override
	public void onDataReceived(Serializable data) {
		EntityList receivedEntityList = null;  
		if(data != null) {
			receivedEntityList = (EntityList)data;
			collect(receivedEntityList);
		
			if(!receivedEntityList.isEmpty() && !isFetchAll()) {
				START_INDEX = receivedEntityList.size() == getFetchSize() ? 
						START_INDEX + getFetchSize() : START_INDEX + receivedEntityList.size();
			}
		}
		listReceiver.onEntityListReceived(receivedEntityList);
	}

}
