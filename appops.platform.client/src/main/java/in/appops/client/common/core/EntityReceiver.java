package in.appops.client.common.core;

import in.appops.platform.core.entity.Entity;

public interface EntityReceiver {

	public void noMoreData();
	
	public void onEntityReceived(Entity entity);
	
	public void onEntityUpdated(Entity entity);
}
