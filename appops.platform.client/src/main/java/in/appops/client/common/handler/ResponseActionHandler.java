package in.appops.client.common.handler;

import in.appops.client.common.core.EntityReceiver;
import in.appops.platform.core.entity.Entity;

public interface ResponseActionHandler {
	Entity getPostEntity();
	void setPostEntity(Entity postEntity);
	
	Entity getEmbeddedEntity();
	void setEmbeddedEntity(Entity embeddedEntity);
	
	Entity getResponseEntity();
	void setResponseEntity(Entity responseEntity);
	
	void executeResponse(EntityReceiver entityReceiver);
}
