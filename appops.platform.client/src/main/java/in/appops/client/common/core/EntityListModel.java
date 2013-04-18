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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class EntityListModel implements AppOpsModel {
	
	private EntityList currentEntityList;
	private Query query;
	private String operationName;
	private final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	private int noOfEntities;
	private ArrayList<Type> interestingTypesList;
	private EntityListReceiver entityListReceiver;
	private int startIndex = 0;
	private int listSize = 10 ;
	
	public EntityListModel(){
		
	}

	public void setCurrentEntityList(EntityList currentEntityList) {
		this.currentEntityList = currentEntityList;
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
		if(query!=null){
			query.setStartIndex(getStartIndex());
			query.setListSize(getListSize());
		}
		
		StandardAction action = new StandardAction(EntityList.class, operationName, parameterMap);
		dispatch.execute(action, new AsyncCallback<Result>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result result) {
				EntityList entityList = (EntityList) result.getOperationResult();
				setCurrentEntityList(entityList);
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

	@Override
	public void addInterestingType(Type type) {
		if(interestingTypesList == null)
			interestingTypesList = new ArrayList<Type>();
		
		interestingTypesList.add(type);
		
	}

	@Override
	public boolean isInterestingType(Type type) {
		for(Type t : interestingTypesList){
			if(t.getTypeId() == type.getTypeId())
				return true;
		}
		return false;
	}

	@Override
	public void setBroadcastEntity(Entity entity) {
		if (currentEntityList != null) {
			for (Entity ent : currentEntityList) {
				if (ent.getType().getTypeName().equalsIgnoreCase(entity.getType().getTypeName())) {
					long entId = (Long) ent.getPropertyByName("id");
					long entityId = (Long) entity.getPropertyByName("id");

					if (entId == entityId) {
						getEntityListReceiver().updateCurrentView(entity);
					}
				}
			}
		}
	}

	public EntityListReceiver getEntityListReceiver() {
		return entityListReceiver;
	}

	public void setEntityListReceiver(EntityListReceiver entityListReceiver) {
		this.entityListReceiver = entityListReceiver;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public EntityList getCurrentEntityList() {
		return currentEntityList;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
}
