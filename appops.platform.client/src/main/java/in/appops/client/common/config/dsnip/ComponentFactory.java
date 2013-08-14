package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.field.BaseField;

public interface ComponentFactory {
	BaseField getField(String string);
	BaseComponentPresenter getComponent(String type);
}
