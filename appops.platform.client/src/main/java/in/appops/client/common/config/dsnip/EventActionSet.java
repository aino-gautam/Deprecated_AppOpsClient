package in.appops.client.common.config.dsnip;

import in.appops.platform.core.entity.Property;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class EventActionSet extends Configuration {
	
	public void addEvent(EventAction eventAction) {
		try {
			String eventName = eventAction.getEventName();
			this.setProperty(eventName, eventAction);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public EventAction getEventAction(String event) {
		EventAction eventAction = null;
		try {
			HashMap<String, Property<? extends Serializable>> eventActionMap = getValue(); 
			eventAction = (EventAction) eventActionMap.get(event);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return eventAction;
	}
	
	public List<EventAction> getAllEventActions() {
		List<EventAction> eventActionsList = new ArrayList<EventAction>();
		try {
			for(Map.Entry<String, Property<? extends Serializable>> eventActionEntry : getValue().entrySet()) {
				EventAction eventAction  = (EventAction) eventActionEntry.getValue();
				eventActionsList.add(eventAction);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return eventActionsList;
	}
}
