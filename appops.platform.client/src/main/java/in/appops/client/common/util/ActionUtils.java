package in.appops.client.common.util;

import in.appops.client.common.components.IActionContext;
import in.appops.client.common.components.IActionLabel;
import in.appops.client.common.components.IIntelliThought;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

public class ActionUtils {

	public static JsonFactory factory = GWT.create(JsonFactory.class);

	public static IActionContext makeActionContext(IActionContext context) {
		AutoBean<IActionContext> actionContext = factory.actionContext(context);
		return actionContext.as();
	}
	
	public static IActionLabel makeAction(IActionLabel action) {
		AutoBean<IActionLabel> actionLabel = factory.action(action);
		return actionLabel.as();
	}

	public static IIntelliThought makeIntelliThought(IIntelliThought intelliThought) {
		AutoBean<IIntelliThought> intelliTht = factory.intelliThought(intelliThought);
		return intelliTht.as();
	}

	public static String serializeToJson(IActionContext context) {
		AutoBean<IActionContext> bean = AutoBeanUtils.getAutoBean(context);
		return AutoBeanCodex.encode(bean).getPayload();
	}

	public static IActionContext deserializeFromJson(String json) {
		 return AutoBeanCodex.decode(factory, IActionContext.class, json).as();
	}
}
