package in.appops.client.common.core;

public interface EntityReceiver {

	public void noMoreData();
	
	public void onEntityReceived();
	
	public void onEntityUpdated();
}
