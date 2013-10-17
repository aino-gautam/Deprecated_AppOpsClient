/**
 * 
 */
package in.appops.client.common.config.model;

import in.appops.client.common.config.dsnip.event.EventActionRuleMap;
import in.appops.client.common.config.util.Store;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;

/**
 * @author mahesh@ensarm.com / nairutee@ensarm.com
 * 
 *
 */
public class PropertyModel extends AppopsBaseModel implements IsConfigurationModel {
	
	private ConfigurationModel parentEntityModel;
	private Property<Serializable> property;
	private final String seperator = ".";
	private String instance;

	private Configuration configuration;
	/**
	 * constructor
	 */
	protected PropertyModel(ConfigurationModel parentEntityModel){
		this.parentEntityModel = parentEntityModel;
	}

	/**
	 * gets the configurations for the property.
	 * Why would I get the field configuration from the Store, when I have the parentConfiguration available through the parentModel?
	 * @param contextPath - the context path of the property. 
	 * @return
	 */
	@Deprecated
	private Configuration getPropertyConfiguration(String contextPath) {
		Configuration propertyConfig = Store.getContextConfiguration(contextPath);
		return propertyConfig;
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
		/*String contextPath = parentEntityModel.getInstance() + seperator + getInstance();
		configuration = getPropertyConfiguration(contextPath);*/
		configuration = (Configuration) parentEntityModel.getViewConfiguration().getConfigurationValue(instance);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateConfiguration(String key, Object value) {
		// TODO Auto-generated method stub
		
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
	
}
