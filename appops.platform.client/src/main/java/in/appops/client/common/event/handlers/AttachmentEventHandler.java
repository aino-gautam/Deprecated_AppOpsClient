package in.appops.client.common.event.handlers;

import com.google.gwt.event.shared.EventHandler;
import in.appops.client.common.event.AttachmentEvent;

public interface AttachmentEventHandler extends EventHandler {
	
	public void onAttachmentEvent(AttachmentEvent event);

}
