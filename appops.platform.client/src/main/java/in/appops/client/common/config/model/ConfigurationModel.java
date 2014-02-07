package in.appops.client.common.config.model;

import in.appops.client.common.config.dsnip.Context;
import in.appops.client.common.config.dsnip.event.EventActionRuleMap;
import in.appops.client.common.config.util.Store;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityGraphException;

import java.io.Serializable;

/**
 *
 * @author nairutee
 *
 */
public class ConfigurationModel extends EntityModel implements IsConfigurationModel {
	protected Configuration configuration;
	protected Configuration modelConfiguration;
	private String instance;
	private Context context;

	/**
	 * loads a configuration object based on the instance id
	 * @param instance - String instanceId
	 */
	@Override
	public void loadInstanceConfiguration() {
		if(configuration == null) {

			if(context.getContextPath().equals("")) {
				configuration = Store.getFromConfigurationStore(instance);
			} else {
				configuration = Store.getContextConfiguration(context.getContextPath() + SEPARATOR + instance);
			}
		}
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

	@Override
	public void setInstance(String instance) {
		this.instance = instance;
	}

	@Override
	public String getInstance() {
		return instance;
	}

	@Override
	public Configuration getConfiguration() {
		return null;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		// TODO Auto-generated method stub

	}

	/**
	 * instantiate a property model with a reference to the entity model and the property it binds to.
	 * @param prop
	 * @return
	 */
	public PropertyModel getPropertyModel() {
		PropertyModel propertyModel = new PropertyModel(this);
		return propertyModel;
	}

	@Override
	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public Context getContext() {
		return context;
	}
}
