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
	
	public void fetchEntityList() {
				
		HashMap<String, Object> queryParamMap = null;

		//if(hasQueryParam()) {
			Configuration queryParam = queryParameters;
			
			
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
