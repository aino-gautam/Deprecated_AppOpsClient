package in.appops.client.common.config.model;

import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;

public class AppopsBaseModel implements AppopsModel {
	
	public interface AppopsModelConstant {
		String QUERYNM = "queryname";
		String OPRNM = "operationname";
		String LISTSIZE = "listSize";
	}
	
	protected final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	protected final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	
	protected Configuration configuration;
	protected String operationName;
	protected String queryName;
	
	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	@Override
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	@Override
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	@Override
	public void configure() {
		setOperationName(getOperationName());
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
	
	public String getOperationName() {
		String operation = null;
		if(getConfigurationValue(AppopsModelConstant.OPRNM) != null) {
			operation = getConfigurationValue(AppopsModelConstant.OPRNM).toString();
		}
		return operation;
	}

}
