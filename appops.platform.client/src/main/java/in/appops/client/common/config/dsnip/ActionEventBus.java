package in.appops.client.common.config.dsnip;

import in.appops.client.common.util.JsonToEntityConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

public class ActionEventBus implements ValueChangeHandler<String> {
	private Map<String, List<ActionEventHandler>> eventHandlers = new HashMap<String, List<ActionEventHandler>>();
	private static  ActionEventBus actionEventBus;
	
	private ActionEventBus() {
		History.addValueChangeHandler(this);
	}
	
	public static ActionEventBus getInstance(){
		if(actionEventBus == null) {
			actionEventBus = new ActionEventBus();
		}
		return actionEventBus;
	}
	
	public void registerHandler(String event, ActionEventHandler handler) {
		List<ActionEventHandler> handlers = getRegisteredList(event);
		handlers.add(handler);
	}
	
	private List<ActionEventHandler> getRegisteredList(String event) {
		List<ActionEventHandler> handlers = eventHandlers.get(event);
		
		if(handlers == null) {
			handlers = new ArrayList<ActionEventHandler>();
			eventHandlers.put(event, handlers);
		}
		return handlers;
	}

	private List<ActionEventHandler> getDispatchList(String event) {
		List<ActionEventHandler> handlers = getHandlerList(event);
		return handlers;
	}
	
	private List<ActionEventHandler> getHandlerList(String event) {
		List<ActionEventHandler> handlers = eventHandlers.get(event);
		
		if(handlers == null) {
			return Collections.emptyList();
		}
		return handlers;
	}

	public void removeHandler(String event, ActionEventHandler handler) {
		List<ActionEventHandler> handlers = getHandlerList(event);
		boolean removed = handlers.remove(handler);
		assert removed : "redundant remove call";
		
		if(removed && handlers.isEmpty()) {
			List<ActionEventHandler> list = eventHandlers.remove(event);
			assert list != null : "Can't remove event that wasn't there";
		}
	}
	
	private void fireActionEvent(ActionEvent event) {
		if(!eventHandlers.isEmpty()) {
			String eventName = event.getEventName();
			
			List<ActionEventHandler> dispatchHandlers = getDispatchList(eventName);
			
			for(ActionEventHandler handler : dispatchHandlers) {
				handler.onActionEvent(event);
			}
		}
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String actionEventJson = event.getValue();

		ActionEvent actionEvent = (ActionEvent) new JsonToEntityConverter().convertjsonStringToEntity(actionEventJson);
		fireActionEvent(actionEvent);
	}
}
