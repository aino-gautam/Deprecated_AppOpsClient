package in.appops.client.common.config.model;

import java.io.Serializable;
import java.util.Map;

import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.core.EntityReceiver;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class EntityListModel extends AppopsBaseModel {

	private EntityList entityList;
	
	@Override
	public void configure() {
		super.configure();
		
		setListSize(getListSize());
	}
	
	private void setListSize(Object listSize) {
		// TODO Auto-generated method stub
		
	}

	private int getListSize() {
		int listSize = 5;
		if(getConfigurationValue(AppopsModelConstant.LISTSIZE) != null) {
			listSize = (Integer)getConfigurationValue(AppopsModelConstant.LISTSIZE);
		}
		return listSize;
	}

/*	@SuppressWarnings("unchecked")
	public void fetchEntityList(final EntityListReceiver listReceiver) {
				
		StandardAction action = new StandardAction(EntityList.class, operationName, null);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result<EntityList> result) {
				EntityList entList = (EntityList) result.getOperationResult();
			
				if(entityList == null) {
					entityList = new EntityList();
				}
				
				if(entList != null) {
					entityList.addAll(entList);
	
					listReceiver.onEntityListReceived(entList);
				}
			}
		});
		
	}*/
	
	@SuppressWarnings("unchecked")
	public void executeOperation(String operation, Map<String, Serializable> param,  final EntityListReceiver listReceiver) {
		StandardAction action = new StandardAction(EntityList.class, operation, param);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result<EntityList> result) {
				EntityList entList = (EntityList) result.getOperationResult();
				
				if(entityList == null) {
					entityList = new EntityList();
				}
				
				if(entList != null) {
					entityList.addAll(entList);
					listReceiver.onEntityListReceived(entList);
				}
			}
		});
	}
	
}
