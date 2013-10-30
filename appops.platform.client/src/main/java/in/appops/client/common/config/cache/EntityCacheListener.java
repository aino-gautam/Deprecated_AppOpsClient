package in.appops.client.common.config.cache;

import java.io.Serializable;

public interface EntityCacheListener {
	
	void onQueryUpdated(String query, Serializable data);
}
