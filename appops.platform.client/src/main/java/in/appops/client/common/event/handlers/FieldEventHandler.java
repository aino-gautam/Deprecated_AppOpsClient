package in.appops.client.common.event.handlers;

import in.appops.client.common.event.FieldEvent;

public interface FieldEventHandler extends AppopsBaseEventHandler {
	
	public void onFieldEvent(FieldEvent event);

}
