/**
 *
 */
package in.appops.client.common.config.model;

import in.appops.client.common.config.dsnip.Context;
import in.appops.client.common.config.dsnip.event.EventActionRuleMap;
import in.appops.client.common.config.util.Store;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityGraphException;

import java.io.Serializable;

/**
 * @author mahesh@ensarm.com / nairutee@ensarm.com
 *
 *
 */
public class PropertyModel extends AppopsBaseModel implements IsConfigurationModel {

	private Configuration configuration;
	private ConfigurationModel parentEntityModel;
	private Property<Serializable> property;
	private String instance;
	private Context context;

	/**
	 * constructor
	 */
	protected PropertyModel(ConfigurationModel parentEntityModel){
		this.parentEntityModel = parentEntityModel;
	}

	public ConfigurationModel getParentEntityModel() {
		return parentEntityModel;
	}

	public void setParentEntityModel(ConfigurationModel parentEntityModel) {
		this.parentEntityModel = parentEntityModel;
	}

	public Property<Serializable> getProperty() {
		return property;
	}

	public void setProperty(Property<Serializable> property) {
		this.property = property;
	}

	@Override
	public void configure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadInstanceConfiguration() {
		//String contextPath = parentEntityModel.getInstance() + seperator + getInstance();
		if(configuration == null) {
			configuration = Store.getContextConfiguration(context.getContextPath() + SEPARATOR + instance);
		}
		//configuration = (Configuration) parentEntityModel.getViewConfiguration().getConfigurationValue(instance);
	}

	@Override
	public Configuration getModelConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Configuration getViewConfiguration() {
		if(configuration.getConfigurationValue(CONFIG_VIEW) != null) {
			return (Configuration)configuration.getConfigurationValue(CONFIG_VIEW);
		}
		return null;
	}

	@Override
	public EventActionRuleMap getEventActionRuleMap() {
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
	public void setInstance(String instance) {
		this.instance = instance;
	}

	@Override
	public String getInstance() {
		return instance;
	}

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		// TODO Auto-generated method stub

	}

	public Object getPropertyValue(String property) {
		Entity entity = parentEntityModel.getEntity();
		Serializable value = entity.getPropertyByName(property);
		return value;
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
