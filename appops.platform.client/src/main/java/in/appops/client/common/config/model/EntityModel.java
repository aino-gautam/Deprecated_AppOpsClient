package in.appops.client.common.config.model;

import in.appops.client.common.core.EntityReceiver;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class EntityModel extends AppopsBaseModel {
	private Entity entity;
	private EntityReceiver receiver;

	
	public void saveEntity(String operation) {
		/*StandardAction action = new StandardAction(EntityList.class, operation, parameterMap);
		dispatch.execute(action, new AsyncCallback<Result>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result result) {
				Entity entity = (Entity) result.getOperationResult();
			
				entityReceiver.onEntityReceived(entity);
			}
		});*/

	}
	
	public void draftEntity(Property<Serializable> prop) {
		
		if(entity == null) {
			entity = new Entity();
		}
		entity.setProperty(prop);
	}
	
	public void deleteEntity() {
		
	}
	
	@SuppressWarnings("unchecked")
	public void fetchEntity() {
		String queryName = getQueryName();
		
		if(queryName != null) {
			
			Configuration queryParam = getQueryParam();
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
					
					executeQuery(query);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void executeQuery(Query query) {
		Map<String, Serializable> queryParam = new HashMap<String, Serializable>();
		queryParam.put("query", query);
		
		StandardAction action = new StandardAction(EntityList.class, getOperationName(), queryParam);
		dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result<Entity> result) {
				Entity ent = result.getOperationResult();
				//TODO
				if(ent != null) {
					receiver.onEntityReceived(ent);
				}
			
			}
		});
	}

	public void setReceiver(EntityReceiver receiver) {
		this.receiver = receiver;
	}
	
	
}
