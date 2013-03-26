package in.appops.client.common.core;

import in.appops.platform.core.util.EntityList;

public interface EntityListReceiver {
	
	public void noMoreData();
	
	public void onEntityListReceived(EntityList entityList);
	
	public void onEntityListUpdated();
	
}
