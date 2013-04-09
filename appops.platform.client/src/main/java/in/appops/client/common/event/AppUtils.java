package in.appops.client.common.event;

import in.appops.client.common.components.ActionContext;
import in.appops.client.common.components.ActionLabel;
import in.appops.client.common.util.JsonFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

public class AppUtils {

	public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);
	public static JsonFactory factory = GWT.create(JsonFactory.class);

	public static ActionContext makeActionContext(ActionContext context) {
		AutoBean<ActionContext> actionContext = factory.actionContext(context);
		return actionContext.as();
	}
	
	public static ActionLabel makeAction(ActionLabel action) {
		AutoBean<ActionLabel> actionLabel = factory.action(action);
		return actionLabel.as();
	}

	public static String serializeToJson(ActionContext context) {
		AutoBean<ActionContext> bean = AutoBeanUtils.getAutoBean(context);
		return AutoBeanCodex.encode(bean).getPayload();
	}

	public static ActionContext deserializeFromJson(String json) {
		 return AutoBeanCodex.decode(factory, ActionContext.class, json).as();
	}
}
