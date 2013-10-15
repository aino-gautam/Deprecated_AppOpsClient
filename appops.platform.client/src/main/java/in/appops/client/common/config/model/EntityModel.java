package in.appops.client.common.config.model;

import in.appops.client.common.core.EntityReceiver;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class EntityModel extends AppopsBaseModel {
	
	private EntityReceiver receiver;
	private Entity currentEntity;
	
	public void updateProperty(Property prop){
		currentEntity.setProperty(prop);
	}
	
	public void fetchEntity() {
		if(queryName != null) {
			
			Configuration queryParam = queryParameters;
			if(queryParam != null) {
				HashMap<String, Object> queryParamMap = new HashMap<String, Object>();
		
				Set<Entry<String, Property<? extends Serializable>>> confSet = queryParam.getValue().entrySet();
		
				for(Entry<String, Property<? extends Serializable>> entry : confSet) {
					String paramName = entry.getKey();
					Serializable value = entry.getValue().getValue();
					if(value != null) {
						queryParamMap.put(paramName, value);
					} else return;
				}
		
				if(!queryParamMap.isEmpty()) {
					Query query = new Query();
					query.setQueryName(queryName);
					query.setQueryParameterMap(queryParamMap);
					
					String querypPointer = globalEntityCache.getQueryIdentifier(query);
					if(!interestedQueryList.contains(querypPointer)) {
						interestedQueryList.add(querypPointer);
					}
						
					currentEntity = globalEntityCache.getEntity(query);
					if(currentEntity != null) {
						receiver.onEntityReceived(currentEntity);
					}
					executeQuery(query);
				}
			}
		}
	}

	public void setReceiver(EntityReceiver receiver) {
		this.receiver = receiver;
	}
	
	@Override
	public void onQueryUpdated(String query, Serializable data) {
		if(isInterestingQuery(query)) {
			currentEntity = (Entity)data;
			receiver.onEntityReceived(currentEntity);
		}
	}
	
	public EntityReceiver getReceiver() {
		return receiver;
	}

	public Entity getCurrentEntity() {
		return currentEntity;
	}

	public void setCurrentEntity(Entity entity) {
		this.currentEntity = entity;
	}
}
