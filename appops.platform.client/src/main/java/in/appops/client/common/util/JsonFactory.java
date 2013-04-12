package in.appops.client.common.util;

import in.appops.client.common.components.IActionContext;
import in.appops.client.common.components.IActionLabel;
import in.appops.client.common.components.IIntelliThought;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface JsonFactory extends AutoBeanFactory {
	public AutoBean<IActionContext> actionContext(IActionContext context);
	public AutoBean<IActionLabel> action(IActionLabel action);
	public AutoBean<IIntelliThought> intelliThought(IIntelliThought intelliThought);
}
