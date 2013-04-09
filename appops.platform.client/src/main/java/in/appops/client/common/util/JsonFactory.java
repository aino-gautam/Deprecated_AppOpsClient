package in.appops.client.common.util;

import in.appops.client.common.components.ActionContext;
import in.appops.client.common.components.ActionLabel;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface JsonFactory extends AutoBeanFactory {
	public AutoBean<ActionContext> actionContext(ActionContext context);
	public AutoBean<ActionLabel> action(ActionLabel action);
}
