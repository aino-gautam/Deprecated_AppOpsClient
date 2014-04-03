package in.appops.client.common.config.component.editor;

import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.component.base.BaseComponent.BaseComponentConstant;
import in.appops.client.common.config.model.ConfigurationModel;

/**
 * Class to represent configuration editing for a field / component / html snippet
 * @author nairutee
 *
 */
public class ConfigEditorComponentPresenter extends BaseComponentPresenter {

	public ConfigEditorComponentPresenter(String type, String instance){
		super(type, instance);
	}
	
	@Override
	protected void initialize() {
		model = (ConfigurationModel) dynamicFactory.requestModel(type);
		view = dynamicFactory.requestView(type);
		view.setModel(model);
		view.initialize();
	}
	
	@Override
	public void create() {
		super.create();
		
		if(model.getModelConfiguration() != null) {		
			String operationName = ((ConfigurationModel) model).getOperationName();
			if(operationName != null && !operationName.equals("")) {
				((ConfigurationModel) model).fetchEntity();
			}
		}
	}
	
	public interface ConfigEditorComponentConstant extends BaseComponentConstant {
		String CE_EDITORCSS = "editorCss";
		String CE_ERRORMSG = "errorMsg";
	}

}
