package in.appops.client.common.config.dsnip;

import java.io.Serializable;

import in.appops.platform.core.entity.Entity;

@SuppressWarnings("serial")
public class ActionEvent extends Entity {

	private final String EVENTNAME = "eventName";
	private final String EVENTDATA = "eventData";
	
	public String getEventName() {
		return getPropertyByName(EVENTNAME);
	}
	
	public void setEventName(String eventName) {
		setPropertyByName(EVENTNAME, eventName);
	}
	
	public Object getEventData() {
		return getPropertyByName(EVENTDATA);
	}
	
	public void setEventData(Object eventData) {
		setPropertyByName(EVENTDATA, (Serializable)eventData);
	}
}
