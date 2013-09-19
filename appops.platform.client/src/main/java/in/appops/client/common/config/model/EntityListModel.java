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

	private EntityList entityList;
	protected EntityListReceiver listReceiver;
	
	@Override
	public void configure() {
		super.configure();
	}
	
	@SuppressWarnings("unchecked")
	public void fetchEntityList() {
				
		String queryName = getQueryName();
		HashMap<String, Object> queryParamMap = null;

		if(hasQueryParam()) {
			Configuration queryParam = getQueryParam();
			
			queryParamMap = new HashMap<String, Object>();
			
			Set<Entry<String, Property<? extends Serializable>>> confSet = queryParam.getValue().entrySet();
			
			for(Entry<String, Property<? extends Serializable>> entry : confSet) {
				String paramName = entry.getKey();
				Serializable value = entry.getValue().getValue();
					queryParamMap.put(paramName, value);
			}
		}
		if(queryParamMap == null || (queryParamMap != null && !queryParamMap.isEmpty())) {
			Query query = new Query();
			query.setQueryName(queryName);
			query.setQueryParameterMap(queryParamMap);
			
			EntityList entityList = globalEntityCache.getEntityList(query);
			if(entityList != null && !entityList.isEmpty()) {
				listReceiver.onEntityListReceived(entityList);
			}
			
			String querypPointer = globalEntityCache.getQueryIdentifier(query);
			
			if(!interestedQueryList.contains(querypPointer)) {
				interestedQueryList.add(querypPointer);
			}
			executeQuery(query);
		}
	}
	
	/*@SuppressWarnings("unchecked")
	public void executeQuery(Query query) {
		Map<String, Serializable> queryParam = new HashMap<String, Serializable>();
		queryParam.put("query", query);
		
		StandardAction action = new StandardAction(EntityList.class, getOperationName(), queryParam);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result<EntityList> result) {
				EntityList entList = (EntityList) result.getOperationResult();
				//TODO
				if(entList != null) {
					listReceiver.onEntityListReceived(entList);
				}
			
			}
		});
	}*/

	public void setReceiver(EntityListReceiver listReceiver) {
		this.listReceiver = listReceiver;
	}
	
	
	public EntityListReceiver getReceiver() {
		return listReceiver;
	}
	
	@Override
	public void onQueryUpdated(String query, Serializable data) {
		if(isInterestingQuery(query)) {
			EntityList entityList = (EntityList)data;
			listReceiver.onEntityListReceived(entityList);
		}
	}
}
