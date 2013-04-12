package in.appops.client.common.core;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.util.EntityList;

public interface EntityListReceiver {
	
	public void noMoreData();
	
	public void onEntityListReceived(EntityList entityList);
	
	public void onEntityListUpdated();
	
	public void updateCurrentView(Entity entity);
	
}
