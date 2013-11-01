package in.appops.client.common.config.component.list;

import in.appops.client.common.config.component.base.BaseComponent.BaseComponentConstant;
import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.model.ConfigurationListModel;
import in.appops.client.common.event.FieldEvent;

public class ListComponentPresenter extends BaseComponentPresenter {

	public ListComponentPresenter(String type, String instance) {
		super(type, instance);
	}

	@Override
	public void initialize() {
		model = dynamicFactory.requestModel(type);
		view = dynamicFactory.requestView(type);
		view.setModel(model);
		view.initialize();
	}

	@Override
	public void configure() {
		super.configure();

		if(model.getModelConfiguration() != null) {
			String operationName = ((ConfigurationListModel) model).getOperationName();
			if(operationName != null && !operationName.equals("")) {
				((ConfigurationListModel) model).fetchEntityList();
			}
		}
	}

	@Override
	public void create() {
		super.create();
	}

	@Override
	protected void registerHandlers() {
		super.registerHandlers();
		if(view.getLocalEventBus() != null) {
			handlerRegistrationList.add(view.getLocalEventBus().addHandler(FieldEvent.TYPE, this));
		}
	}

	public interface ListComponentConstant extends BaseComponentConstant {
		String LC_LISTCLS = "listCss";
		String LC_LISTSCROLLCLS = "listScrollCss";
		String LC_SNIPPETTYPE = "snippetType";
		String LC_INSTANCETYPE = "snippetInstance";
	}
}
