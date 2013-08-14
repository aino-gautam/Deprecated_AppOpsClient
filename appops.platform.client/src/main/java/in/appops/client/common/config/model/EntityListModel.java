package in.appops.client.common.config.model;

import in.appops.client.common.core.EntityListReceiver;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class EntityListModel extends AppopsBaseModel {

	private EntityList entityList;
	private EntityListReceiver listReceiver;
	
	@Override
	public void configure() {
		super.configure();
	}
	
	@SuppressWarnings("unchecked")
	public void fetchEntityList() {
				
		String queryName = getQueryName();
		Configuration queryParam = getQueryParam();
		
		HashMap<String, Object> queryParamMap = new HashMap<String, Object>();
		
		Set<Entry<String, Property<? extends Serializable>>> confSet = queryParam.getValue().entrySet();
		
		for(Entry<String, Property<? extends Serializable>> entry : confSet) {
			String paramName = entry.getKey();
			Serializable value = entry.getValue().getValue();
				queryParamMap.put(paramName, value);
		}
		
		if(!queryParamMap.isEmpty()) {
			Query query = new Query();
			query.setQueryName(queryName);
			query.setQueryParameterMap(queryParamMap);
			
			executeQuery(query);
		}
	}
	
	@SuppressWarnings("unchecked")
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
	}

	public void setReceiver(EntityListReceiver listReceiver) {
		this.listReceiver = listReceiver;
	}
}
