/**
 * 
 */

package in.appops.client.common.snippet.activity;
import in.appops.client.common.core.EntityListModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author mahesh@ensarm.com
 *
 */
public class ActivityModel extends EntityListModel{

	private Query query;
	private String operationName;

	private final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public EntityList getEntityList(int noOfEntities,final EntityListReceiver listReceiver) {
		Map parameterMap = new HashMap();
		getQuery().setListSize(noOfEntities);
		parameterMap.put("query", getQuery());

		StandardAction action = new StandardAction(EntityList.class, getOperationName(), parameterMap);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EntityList getEntityList(int noOfEntities) {
		Map parameterMap = new HashMap();

		if(noOfEntities != 0)
			getQuery().setListSize(noOfEntities);

		parameterMap.put("query", getQuery());

		StandardAction action = new StandardAction(EntityList.class, getOperationName(), parameterMap);
		dispatch.execute(action, new AsyncCallback<Result>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result result) {
				EntityList entityList = (EntityList) result.getOperationResult();
				setCurrentEntityList(entityList);
				getEntityListReceiver().onEntityListReceived(entityList);
			}
		});
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getNextEntityList(int startIndex) {
		try{
			Map parameterMap = new HashMap();

			if(startIndex != 0)
				getQuery().setListSize(startIndex);

			parameterMap.put("query", getQuery());

			StandardAction action = new StandardAction(EntityList.class, getOperationName(), parameterMap);
			dispatch.execute(action, new AsyncCallback<Result>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result result) {
					EntityList entityList = (EntityList) result.getOperationResult();
					setCurrentEntityList(entityList);
					((ActivityComponent) getEntityListReceiver()).onUpdatedEntity(entityList);
				}
			});

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void setBroadcastEntity(Entity entity) {
		try{
			if(entity != null)
				if(getEntityListReceiver() != null)
					getEntityListReceiver().updateCurrentView(entity);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the query
	 */
	public Query getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(Query query) {
		this.query = query;
	}

	/**
	 * @return the operationName
	 */
	public String getOperationName() {
		return operationName;
	}

	/**
	 * @param operationName the operationName to set
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

}
