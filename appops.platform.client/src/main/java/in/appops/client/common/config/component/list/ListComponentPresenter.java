package in.appops.client.common.config.component.list;

import in.appops.client.common.config.component.base.BaseComponent.BaseComponentConstant;
import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.model.ConfigurationListModel;

public class ListComponentPresenter extends BaseComponentPresenter {
	
	public ListComponentPresenter(String type, String instance) {
		super(type, instance);
	}
	
	@Override
	public void initialize() {
		model = (ConfigurationListModel) dynamicFactory.requestModel(type);
		view = dynamicFactory.requestView(type);
		view.setModel(model);
		view.initialize();
	}

	@Override
	public void create() {
		super.create();
		
		if(model.getModelConfiguration() != null) {		
			String operationName = ((ConfigurationListModel) model).getOperationName();
			if(operationName != null && !operationName.equals("")) {
				((ConfigurationListModel) model).fetchEntityList();
			}
		}
	}
	
	public interface ListComponentConstant extends BaseComponentConstant {
		String LC_LISTCLS = "listCss";
		String LC_SNIPPETTYPE = "snippetType";
		String LC_INSTANCETYPE = "snippetInstance";
	}
}
