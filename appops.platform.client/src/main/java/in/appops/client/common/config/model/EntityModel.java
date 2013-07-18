package in.appops.client.common.config.model;

import in.appops.client.common.core.EntityReceiver;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class EntityModel extends AppopsBaseModel {
	private Entity entity;

	
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
	public void fetchEntity(Long entityId, final EntityReceiver entityReceiver) {
		HashMap<String, Serializable> parameterMap = new HashMap<String, Serializable>();
		parameterMap.put("articleId", entityId);
		//executeOperation(parameterMap, entityReceiver);
	}
	
	@SuppressWarnings("unchecked")
	public void executeOperation(String operation, HashMap<String, Serializable> param,  final EntityReceiver entityReceiver) {
		StandardAction action = new StandardAction(EntityList.class, operation, param);
		dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught.getLocalizedMessage());
			}

			@Override
			public void onSuccess(Result<Entity> result) {
				entity = (Entity) result.getOperationResult();
				
				if(entity != null) {
					entityReceiver.onEntityReceived(entity);
				}
			}
		});
	}
}
