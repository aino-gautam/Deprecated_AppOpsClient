package in.appops.client.common.config.model;

import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.util.EntityList;

public class ConfigurationListModel extends EntityListModel{

	@Override
	public void fetchEntityList() {
		String queryName = getQueryName();
	
		Query query = new Query();
		query.setQueryName(queryName);
		
		EntityList entityList = globalEntityCache.getEntityList(query);
		if(entityList != null && !entityList.isEmpty()) {
			listReceiver.onEntityListReceived(entityList);
		}
		
		String querypPointer = globalEntityCache.getQueryIdentifier(query);
		
		if(!interestedQueryList.contains(querypPointer)) {
			interestedQueryList.add(querypPointer);
		}
	}
}
