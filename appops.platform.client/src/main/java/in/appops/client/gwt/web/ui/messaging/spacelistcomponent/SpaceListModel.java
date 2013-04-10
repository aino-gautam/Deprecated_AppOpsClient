/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging.spacelistcomponent;

import in.appops.client.common.core.EntityListModel;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;

/**
 * @author mahesh@ensarm.com
 * This model will create the space listing query and fetch the space list for the space listing widget
 */
public class SpaceListModel extends EntityListModel{

	public SpaceListModel(Entity contactEntity){
		createQuery(contactEntity);
	}
	
	/**
	 * Will create a space listing query along along with the operation name and bind them
	 * to parent {@link EntityListModel}
	 * @param contactEntity 
	 */
	private void createQuery(Entity contactEntity){
		try {
			
			//TODO the contact entity should be used to fetch all the specific space
			// related to it.
			
			Query query = new Query();
			query.setQueryName("getAllSpaces");
			query.setListSize(5);
			
			setQueryToBind(query);
			
			String operationName = "spacemanagement.SpaceManagementService.getEntityList";
			setOperationNameToBind(operationName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
