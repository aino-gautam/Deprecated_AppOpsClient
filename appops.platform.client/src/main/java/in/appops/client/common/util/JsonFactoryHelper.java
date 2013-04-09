package in.appops.client.common.util;

import in.appops.client.common.components.ActionContext;
import in.appops.client.common.components.ActionLabel;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

public class JsonFactoryHelper {

	JsonFactory factory = GWT.create(JsonFactory.class);

	public ActionContext makeActionContext(ActionContext context) {
		AutoBean<ActionContext> actionContext = factory.actionContext(context);
		return actionContext.as();
	}
	
	public ActionLabel makeAction(ActionLabel action) {
		AutoBean<ActionLabel> actionLabel = factory.action(action);
		return actionLabel.as();
	}

	public String serializeToJson(ActionContext context) {
		AutoBean<ActionContext> bean = AutoBeanUtils.getAutoBean(context);
		return AutoBeanCodex.encode(bean).getPayload();
	}

	public ActionContext deserializeFromJson(String json) {
		 return AutoBeanCodex.decode(factory, ActionContext.class, 	json).as();
	}

}
