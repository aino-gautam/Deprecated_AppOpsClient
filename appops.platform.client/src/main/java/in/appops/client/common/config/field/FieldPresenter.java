package in.appops.client.common.config.field;

import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.model.PropertyModel;
import in.appops.client.common.event.FieldEvent;

public class FieldPresenter extends BaseComponentPresenter {

	public FieldPresenter(String fieldType, String instance, PropertyModel model) {
		super(fieldType, instance, model);
	}

	@Override
	protected void initialize() {
		view = dynamicFactory.requestView(type);
		view.setModel(model);
		view.initialize();
	}

	@Override
	protected void registerHandlers() {
		super.registerHandlers();
		if(view.getLocalEventBus() != null) {
			handlerRegistrationList.add(view.getLocalEventBus().addHandler(FieldEvent.TYPE, this));
		}
	}

}
