package in.appops.client.common.core;

import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.entity.type.Type;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class EntityListModel implements AppOpsModel {
	
	private EntityList entityList;
	private Query query;
	private String operationName;
	private final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	private int noOfEntities;
	
	public EntityListModel(){
		
	}

	public void setEntityList(EntityList entityList) {
		this.entityList = entityList;
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
	public Query getQueryToBind() {
		return this.query;
	}

	@Override
	public void setQueryToBind(Query query) {
		this.query = query;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EntityList getEntityList(int noOfEntities, final EntityListReceiver listReceiver) {
		
		Map parameterMap = new HashMap();
		parameterMap.put("query", query);
		
		StandardAction action = new StandardAction(EntityList.class, operationName, parameterMap);
		dispatch.execute(action, new AsyncCallback<Result>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result result) {
				EntityList entityList = (EntityList) result.getOperationResult();
				listReceiver.onEntityListReceived(entityList);
			}
		});
		return null;
	}

	@Override
	public Entity getEntity(Type type, int entityId, EntityReceiver entityReceiver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOperationNameToBind() {
		return this.operationName;
	}

	@Override
	public void setOperationNameToBind(String name) {
		this.operationName = name;
	}

	public int getNoOfEntities() {
		return noOfEntities;
	}

	public void setNoOfEntities(int noOfEntities) {
		this.noOfEntities = noOfEntities;
	}
}
