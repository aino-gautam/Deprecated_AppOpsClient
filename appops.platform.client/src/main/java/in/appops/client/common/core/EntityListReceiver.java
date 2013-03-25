package in.appops.client.common.core;

public interface EntityListReceiver {
	
	public void noMoreData();
	
	public void onEntityListReceived();
	
	public void onEntityListUpdated();
	
}
