package in.appops.client.common.event.handlers;

import in.appops.client.common.event.CheckBoxSelectEvent;

import com.google.gwt.event.shared.EventHandler;

public interface CheckBoxSelectEventHandler extends EventHandler{

	public void onSelect(CheckBoxSelectEvent event);
}
