package in.appops.client.common.config.component.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import in.appops.client.common.config.component.base.BaseComponentView.BaseComponentConstant;
import in.appops.client.common.core.AppOpsModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.core.EntityReceiver;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.entity.type.Type;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

public class BaseComponentModel implements AppOpsModel{
	
	private Configuration configuration;
	private String operationName;
	private final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	
	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}


	@Override
	public void configure() {
		setOperationNameToBind(getOperation());
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQueryToBind(Query query) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityList getEntityList(int noOfEntities,	final EntityListReceiver listReceiver) {
				
		StandardAction action = new StandardAction(EntityList.class, operationName, null);
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
	public Entity getEntity(String op, Long entityId,
			final EntityReceiver entityReceiver) {
		Map parameterMap = new HashMap();
		parameterMap.put("articleId", entityId);		
		StandardAction action = new StandardAction(EntityList.class, op, parameterMap);
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
		});
		
		return null;
	}

	@Override
	public String getOperationNameToBind() {
		return operationName;
	}

	@Override
	public void setOperationNameToBind(String name) {
		this.operationName = name;
	}

	@Override
	public void addInterestingType(Type type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInterestingType(Type type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBroadcastEntity(Entity entity) {
		// TODO Auto-generated method stub
		
	}
	
	protected boolean hasConfiguration(String configKey) {
		if(configuration != null && configuration.getPropertyByName(configKey) != null) {
			return true;
		}
		return false;
	}
	
	protected Serializable getConfigurationValue(String configKey) {
		if(hasConfiguration(configKey)) {
			return configuration.getPropertyByName(configKey);
		}
		return null;
	}
	
	private String getOperation() {
		String operation = null;
		if(getConfigurationValue(BaseComponentConstant.BC_MODELOP) != null) {
			operation = getConfigurationValue(BaseComponentConstant.BC_MODELOP).toString();
		}
		return operation;
	}

	

}
