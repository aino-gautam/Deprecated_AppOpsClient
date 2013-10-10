/**
 * 
 */
package in.appops.client.common.config.model;

import in.appops.client.common.config.util.Store;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.shared.Configuration;

/**
 * @author mahesh@ensarm.com / nairutee@ensarm.com
 * 
 *
 */
public class PropertyModel extends AppopsBaseModel{
	
	private EntityModel parentEntityModel;
	private Property property;

	/**
	 * constructor
	 */
	protected PropertyModel(EntityModel parentEntityModel, Property property){
		this.parentEntityModel = parentEntityModel;
		this.property = property;
	}

	/**
	 * gets the configurations for the property
	 * @param contextPath - the context path of the property. 
	 * @return
	 */
	protected Configuration getPropertyConfiguration(String contextPath){
		Configuration propertyConfig = Store.getContextConfiguration(contextPath);
		return propertyConfig;
	}
		
	public EntityModel getParentEntityModel() {
		return parentEntityModel;
	}

	public void setParentEntityModel(EntityModel parentEntityModel) {
		this.parentEntityModel = parentEntityModel;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		
	}
	
}
