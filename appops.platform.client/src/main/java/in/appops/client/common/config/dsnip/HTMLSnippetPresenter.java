package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponent.BaseComponentConstant;
import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.event.FieldEvent;

import com.google.gwt.event.shared.SimpleEventBus;

public class HTMLSnippetPresenter extends BaseComponentPresenter {
	private SimpleEventBus localEventBus;

	public interface HTMLSnippetConstant extends BaseComponentConstant {
		String HS_PCLS = "basePrimaryCss";
		String HS_DCLS = "baseDependentCss";
	}

	public HTMLSnippetPresenter(String snippetType, String snippetInstance) {
		super(snippetType, snippetInstance);
	}

	@Override
	protected void initialize() {
		localEventBus = injector.getLocalEventBus();
		model = dynamicFactory.requestModel(DynamicMVPFactory.HTMLSNIPPET);
		view = dynamicFactory.requestView(DynamicMVPFactory.HTMLSNIPPET);
		view.setLocalEventBus(localEventBus);
		view.setModel(model);
		((HTMLSnippetView) view).setSnippetType(type);
		view.initialize();
	}

	@Override
	public void configure() {
		super.configure();

		if(model.getModelConfiguration() != null) {
			String operationName = ((HTMLSnippetModel) model).getOperationName();
			if(operationName != null && !operationName.equals("")) {
				((HTMLSnippetModel) model).fetchEntity();
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
		if(localEventBus != null) {
			handlerRegistrationList.add(localEventBus.addHandler(FieldEvent.TYPE, this));
		}
	}


}