package in.appops.client.common.config.model;

import in.appops.client.common.config.component.list.ListComponentPresenter.ListComponentConstant;
import in.appops.client.common.config.dsnip.event.EventActionRuleMap;
import in.appops.client.common.config.util.Store;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityGraphException;

import java.io.Serializable;

public class ConfigurationListModel extends EntityListModel implements IsConfigurationModel{

	private Configuration configuration;
	private Configuration listModelConfiguration;
	private String instance;

	public ConfigurationListModel(){
		
	}
	
	/**
	 * loads a configuration object based on the instance id 
	 * @param instance - String instanceId 
	 */
	@Override
	public void loadInstanceConfiguration(){
		configuration = Store.getFromConfigurationStore(instance);
	}
	
	/**
	 * Fetches the model configurations from the configuration object
	 * @return {@link Configuration}
	 */
	@Override
	public Configuration getModelConfiguration(){
		if(configuration.getConfigurationValue(CONFIG_MODEL) != null) {
			return (Configuration)configuration.getConfigurationValue(CONFIG_MODEL);
		}
		return null;
	}
	
	/**
	 * Fetches the view configurations from the configuration object
	 * @return {@link Configuration}
	 */
	@Override
	public Configuration getViewConfiguration(){
		if(configuration.getConfigurationValue(CONFIG_VIEW) != null) {
			return (Configuration)configuration.getConfigurationValue(CONFIG_VIEW);
		}
		return null;
	}
	
	/**
	 * Fetches the {@klink EventActionRuleMap} from the configuration object
	 * @return {@klink EventActionRuleMap} 
	 */
	@Override
	public EventActionRuleMap getEventActionRuleMap(){
		if(configuration.getConfigurationValue(CONFIG_EVENTACTIONRULEMAP) != null) {
			return (EventActionRuleMap) configuration.getConfigurationValue(CONFIG_EVENTACTIONRULEMAP); 
		}
		
		return null;
	}
	
	@Override
	public void updateConfiguration(String key, Object value) {
		try {
			configuration.setGraphPropertyValue(key, (Serializable)value, null);
		} catch (EntityGraphException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void configure() {
		listModelConfiguration = getModelConfiguration();
		if(getOperationName() != null) {
			setOperationName(getOperationName());
		}
		if(getQueryName() != null) {
			setQueryName(getQueryName());
		}
		if(getOperationParameters() != null) {
			setOperationParameters(getOperationParameters());
		}
		if(getQueryParameters() != null) {
			setQueryParameters(getQueryParameters());
		}
		setCacheable(isCacheable());
		
		if(isCacheable()) {
			globalEntityCache.register(this);
		}
	}
	
	public String getOperationName() {
		String operation = null;
		if(listModelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_OPR_NM) != null) {
			operation = listModelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_OPR_NM).toString();
		}
		return operation;
	}

	public String getQueryName() {
		String queryName = null;
		if(listModelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_QRY_NAME) != null) {
			queryName = listModelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_QRY_NAME).toString();
		}
		return queryName;
	}
	
	public Configuration getQueryParameters() {
		Configuration param = null;
		if(listModelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_QRY_PARAM) != null) {
			param = (Configuration) listModelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_QRY_PARAM);
		}
		return param;
	}
	

	private Boolean isCacheable() {
		Boolean param = true;
		if(listModelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_CHCBLE) != null) {
			param = (Boolean) listModelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_CHCBLE);
		}
		return param;
	}
	
	public Configuration getOperationParameters() {
		Configuration param = null;
		if(listModelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_OPR_PARAM) != null) {
			param = (Configuration) listModelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_OPR_PARAM);
		}
		return param;
	}
	
	public Boolean hasQueryParam() {
		Boolean param = null;
		if(listModelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_HAS_QRYPARAM) != null) {
			param = (Boolean) listModelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_HAS_QRYPARAM);
		}
		return param;
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	@Override
	public void setInstance(String instance) {
		this.instance = instance;
	}

	@Override
	public String getInstance() {
		return instance;
	}

	public String getSnippetInstance() {
		String snippetInstance = null;
		if(configuration.getConfigurationValue(ListComponentConstant.LC_INSTANCETYPE) != null) {
			snippetInstance = configuration.getConfigurationValue(ListComponentConstant.LC_INSTANCETYPE).toString();
		}
		return snippetInstance;
	}


	public String getSnippetType() {
		String snippetType = null;
		if(configuration.getConfigurationValue(ListComponentConstant.LC_SNIPPETTYPE) != null) {
			snippetType = configuration.getConfigurationValue(ListComponentConstant.LC_SNIPPETTYPE).toString();
		}
		return snippetType;
	}
}
