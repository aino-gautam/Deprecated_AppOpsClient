package in.appops.client.common.config.model;

import in.appops.client.common.config.component.base.BaseComponentPresenter.BaseComponentConstant;
import in.appops.client.common.config.dsnip.Container.ContainerConstant;
import in.appops.client.common.config.dsnip.event.EventActionRuleMap;
import in.appops.client.common.config.util.Store;
import in.appops.platform.core.shared.Configuration;

/**
 * 
 * @author nairutee
 *
 */
public class ConfigurationModel extends EntityModel implements IsConfigurationModel {
	
	Configuration configuration;
	Configuration modelConfiguration;
	
	/**
	 * loads a configuration object based on the instance id 
	 * @param instance - String instanceId 
	 */
	@Override
	public void loadInstanceConfiguration(String instance){
		configuration = Store.getFromConfigurationStore(instance);
	}
	
	/**
	 * Fetches the model configurations from the configuration object
	 * @return {@link Configuration}
	 */
	@Override
	public Configuration getModelConfiguration(){
		if(configuration.getConfigurationValue(BaseComponentConstant.BC_CONFIGMODEL) != null) {
			return (Configuration)configuration.getConfigurationValue(BaseComponentConstant.BC_CONFIGMODEL);
		}
		return null;
	}
	
	/**
	 * Fetches the view configurations from the configuration object
	 * @return {@link Configuration}
	 */
	@Override
	public Configuration getViewConfiguration(){
		if(configuration.getConfigurationValue(BaseComponentConstant.BC_CONFIGMODEL) != null) {
			return (Configuration)configuration.getConfigurationValue(BaseComponentConstant.BC_CONFIGMODEL);
		}
		return null;
	}
	
	/**
	 * Fetches the {@klink EventActionRuleMap} from the configuration object
	 * @return {@klink EventActionRuleMap} 
	 */
	@Override
	public EventActionRuleMap getEventActionRuleMap(){
		if(configuration.getConfigurationValue(ContainerConstant.CT_INTRSDEVNTS) != null) {
			return (EventActionRuleMap) configuration.getConfigurationValue(ContainerConstant.CT_INTRSDEVNTS); 
		}
		
		return null;
	}

	@Override
	public void updateConfiguration(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * configures the model configurations such as query name, query params, op name and op params
	 */
	@Override
	public void configure() {
		modelConfiguration = getModelConfiguration();
		if(modelConfiguration != null){
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
	}
	
	public String getOperationName() {
		String operation = null;
		if(modelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_OPR_NM) != null) {
			operation = modelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_OPR_NM).toString();
		}
		return operation;
	}

	public String getQueryName() {
		String queryName = null;
		if(modelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_QRY_NAME) != null) {
			queryName = modelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_QRY_NAME).toString();
		}
		return queryName;
	}
	
	public Configuration getQueryParameters() {
		Configuration param = null;
		if(modelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_QRY_PARAM) != null) {
			param = (Configuration) modelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_QRY_PARAM);
		}
		return param;
	}
	

	private Boolean isCacheable() {
		Boolean param = true;
		if(modelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_CHCBLE) != null) {
			param = (Boolean) modelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_CHCBLE);
		}
		return param;
	}
	
	public Configuration getOperationParameters() {
		Configuration param = null;
		if(modelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_OPR_PARAM) != null) {
			param = (Configuration) modelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_OPR_PARAM);
		}
		return param;
	}
	
	public Boolean hasQueryParam() {
		Boolean param = null;
		if(modelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_HAS_QRYPARAM) != null) {
			param = (Boolean) modelConfiguration.getConfigurationValue(AppopsModelConstant.ABM_HAS_QRYPARAM);
		}
		return param;
	}


	
	
}
