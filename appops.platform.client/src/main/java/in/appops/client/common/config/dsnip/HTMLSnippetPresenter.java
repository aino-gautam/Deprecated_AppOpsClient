package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponent.BaseComponentConstant;
import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.dsnip.event.SnippetControllerRule;

public class HTMLSnippetPresenter extends BaseComponentPresenter {

	public interface HTMLSnippetConstant extends BaseComponentConstant {
		String HS_PCLS = "basePrimaryCss";
		String HS_DCLS = "baseDependentCss";
	}

	public HTMLSnippetPresenter(String snippetType, String snippetInstance) {
		super(snippetType, snippetInstance);
	}

	@Override
	protected void initialize() {
		model = (HTMLSnippetModel) dynamicFactory.requestModel(DynamicMVPFactory.HTMLSNIPPET);
		view = dynamicFactory.requestView(DynamicMVPFactory.HTMLSNIPPET);
		view.setModel(model);
		((HTMLSnippetView) view).setSnippetType(type);
		view.initialize();
	}

	@Override
	public void create() {
		super.create();
		
		if(model.getModelConfiguration() != null) {
			String operationName = ((HTMLSnippetModel) model).getOperationName();
			if(operationName != null && !operationName.equals("")) {
				((HTMLSnippetModel) model).fetchEntity();
			}
		}
	}
	@Override
	public void processSnippetControllerRule(SnippetControllerRule snippetControllerRule) {
		// TODO Auto-generated method stub
		
	}
}