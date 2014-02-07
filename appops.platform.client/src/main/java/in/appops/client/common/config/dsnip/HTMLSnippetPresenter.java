package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponent.BaseComponentConstant;
import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.dsnip.event.SnippetControllerRule;
import in.appops.client.common.config.model.ConfigurationModel;
import in.appops.client.common.config.model.IsConfigurationModel;
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
	
	@Override
	public void processSnippetControllerRule(SnippetControllerRule snippetControllerRule) {
		boolean transformation = snippetControllerRule.hasTransformation();

		if(transformation) {
			String transformToSnippet = snippetControllerRule.getTransformToSnippet();
			String transformFromSnippet = snippetControllerRule.getTransformFromSnippet();
			String snippetInstance = snippetControllerRule.getSnippetInstance();

			createAddSnippet(transformToSnippet, transformFromSnippet, snippetInstance);
		}
	}
	
	private void createAddSnippet(String transformToSnippet, String transformFromSnippet, String snippetInstance) {
		try {
			HTMLSnippetPresenter snippetFrom = null;
			if(!((HTMLSnippetView) view).getElementMap().isEmpty()) {
				if(transformFromSnippet == null) {
					transformFromSnippet =  ((HTMLSnippetView) view).getElementMap().keySet().toArray()[0].toString();
				}
				snippetFrom = (HTMLSnippetPresenter) ((HTMLSnippetView) view).getElementMap().get(transformFromSnippet);
			}
			
			if(snippetFrom != null) {
				HTMLSnippetPresenter snippetTo = dynamicFactory.requestHTMLSnippet(transformToSnippet, snippetInstance);
				if(snippetTo != null) {
					Context componentContext = new Context();
					componentContext.setParentEntity(((ConfigurationModel)model).getEntity());
					String componentContextPath = !model.getContext().getContextPath().equals("") ?
							model.getContext().getContextPath() + IsConfigurationModel.SEPARATOR + model.getInstance() : model.getInstance();
					componentContext.setContextPath(componentContextPath);
					snippetTo.getModel().setContext(componentContext);
					
					snippetTo.configure();
					snippetTo.create();
					((HTMLSnippetView) view).addAndReplaceElement(snippetTo.getView(), snippetFrom.getView().getElement());

					snippetFrom.removeHandlers();
					((HTMLSnippetView) view).getElementMap().remove(transformFromSnippet);
					((HTMLSnippetView) view).getElementMap().put(transformToSnippet, snippetTo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}