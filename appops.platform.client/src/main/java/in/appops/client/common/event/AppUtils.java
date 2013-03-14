package in.appops.client.common.event;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

public class AppUtils {

	public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);
}
