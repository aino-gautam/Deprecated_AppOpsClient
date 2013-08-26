package in.appops.client.common.config.model;

import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;

public class AppopsBaseModel implements AppopsModel {
	
	public interface AppopsModelConstant {
		String ABM_QRY_NAME = "queryname";
		String ABM_OPR_NM = "operationname";
		String ABM_QRY_PARAM = "queryParam";
		String ABM_OPR_PARAM = "operationParam";
	}
	
	protected final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	protected final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	
	protected Configuration configuration;
	protected String operationName;
	protected String queryName;
//	protected ArrayList<Configuration> queryParamList;
	
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

//	@Override
//	public void setQueryParamList(ArrayList<Configuration> queryParamList) {
//		this.queryParamList = queryParamList;
//	}
	
	@Override
	public void configure() {
		setOperationName(getOperationName());
		setQueryName(getQueryName());
//		setQueryParamList(getQueryParamList());
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
		if(getConfigurationValue(AppopsModelConstant.ABM_OPR_NM) != null) {
			operation = getConfigurationValue(AppopsModelConstant.ABM_OPR_NM).toString();
		}
		return operation;
	}

	public String getQueryName() {
		String queryName = null;
		if(getConfigurationValue(AppopsModelConstant.ABM_QRY_NAME) != null) {
			queryName = getConfigurationValue(AppopsModelConstant.ABM_QRY_NAME).toString();
		}
		return queryName;
	}
	
	public Configuration getQueryParam() {
		Configuration param = null;
		if(getConfigurationValue(AppopsModelConstant.ABM_QRY_PARAM) != null) {
			param = (Configuration) getConfigurationValue(AppopsModelConstant.ABM_QRY_PARAM);
		}
		return param;
	}

	public Configuration getOperationParam() {
		Configuration param = null;
		if(getConfigurationValue(AppopsModelConstant.ABM_OPR_PARAM) != null) {
			param = (Configuration) getConfigurationValue(AppopsModelConstant.ABM_OPR_PARAM);
		}
		return param;
	}

}
