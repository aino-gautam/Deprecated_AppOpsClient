/**
 * 
 */
package in.appops.client.gwt.web.ui.messaging.spacelistcomponent;

import in.appops.client.common.core.EntityListModel;
import in.appops.platform.core.entity.query.Query;

/**
 * @author mahesh@ensarm.com
 * This model will create the space listing query and fetch the space list for the space listing widget
 */
public class SpaceListModel extends EntityListModel {

	public SpaceListModel(){
		createQuery();
	}
	
	/**
	 * Will create a space listing query along along with the operation name and bind them
	 * to parent {@link EntityListModel}
	 */
	private void createQuery(){
		try {
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
