package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.dsnip.event.SnippetControllerRule;

public class HTMLSnippetPresenter extends BaseComponentPresenter {

	public interface HTMLSnippetConstant {
		String HS_FIELDEVENTS = "interestedFieldEvents";
		String HS_PCLS = "basePrimaryCss";
		String HS_DCLS = "baseDependentCss";
		String CONTAINERSNIPPET = "containerSnippet";
		String HTMLSNIPPET = "htmlSnippet";
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
	public void processSnippetControllerRule(SnippetControllerRule snippetControllerRule) {
		// TODO Auto-generated method stub
		
	}
}