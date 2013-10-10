package in.appops.client.common.config.model;

import in.appops.client.common.config.cache.EntityCacheListener;
import in.appops.client.common.config.cache.GlobalEntityCache;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AppopsBaseModel implements EntityCacheListener, Configurable {
	
	public interface AppopsModelConstant {
		String ABM_QRY_NAME = "queryname";
		String ABM_OPR_NM = "operationname";
		String ABM_QRY_PARAM = "queryParam";
		String ABM_CHCBLE = "cacheable";
		String ABM_OPR_PARAM = "operationParam";
		String ABM_HAS_QRYPARAM = "hasQueryParam";
	}
	
	protected final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	protected final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	
	protected Configuration configuration;
	protected String operationName;
	protected String queryName;
	protected Configuration queryParameters;
	protected Configuration operationParameters;
	protected Boolean cacheable;
	
	protected ArrayList<String> interestedQueryList = new ArrayList<String>();;
	protected GlobalEntityCache globalEntityCache = GlobalEntityCache.getInstance();

	
	public abstract void configure();
	
	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	
	public void setCacheable(Boolean cacheable) {
		this.cacheable = cacheable;
	}

	public void setOperationParameters(Configuration operationParameters) {
		this.operationParameters = operationParameters;
	}
	
	public void setQueryParameters(Configuration queryParameters) {
		this.queryParameters = queryParameters;
	}
	
	

	protected boolean isInterestingQuery(String query) {
		if(!interestedQueryList.isEmpty() && interestedQueryList.contains(query)) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void executeQuery(final Query query) {
		Map<String, Serializable> queryParam = new HashMap<String, Serializable>();
		queryParam.put("query", query);
		
		StandardAction action = new StandardAction(EntityList.class, operationName, queryParam);
		dispatch.execute(action, new AsyncCallback<Result>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result result) {
				Serializable opResult =  result.getOperationResult();
				//TODO
				
				if(opResult != null) {
					if(opResult instanceof EntityList) {
						EntityList entList = (EntityList)opResult;
						if(entList != null) {
							globalEntityCache.updateCache(query, entList);
						} 
					} else if(opResult instanceof Entity) {
						Entity ent = (Entity)opResult;
						if(ent != null) {
							globalEntityCache.updateCache(query, ent);
						} 
					}
				}
			
			}
		});
	}
	
	@Override
	public void onQueryUpdated(String query, Serializable data) {
		// TODO Auto-generated method stub
		
	}
}
