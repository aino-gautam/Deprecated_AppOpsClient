package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponent.BaseComponentConstant;

/**
 * 
 * @author nairutee
 *
 */
public class FormSnippetPresenter extends HTMLSnippetPresenter {

	public interface FormSnippetConstant extends BaseComponentConstant {
		String HAS_INLINE_EDITING = "hasInlineEditing";
		String FS_PCLS = "formPrimaryCss";
		String FS_DCLS = "formDependentCss";
	}
	
	public FormSnippetPresenter(String snippetType, String snippetInstance) {
		super(snippetType, snippetInstance);
	}

	@Override
	protected void initialize() {
		localEventBus = injector.getLocalEventBus();
		model = dynamicFactory.requestModel(DynamicMvpFactory.FORMSNIPPET);
		view = dynamicFactory.requestView(DynamicMvpFactory.FORMSNIPPET);
		view.setLocalEventBus(localEventBus);
		view.setModel(model);
		((FormSnippetView) view).setSnippetType(type);
		view.initialize();
	}
	
	/**
	 * Process the action to be executed by the form.
	 */
	public void processFormAction(){
		
	}
	
	/**
	 * Iterates the element map and clears all the fields
	 */
	public void clear(){
		
	}
	
	/**
	 * Resets the form to it's initial state
	 */
	public void reset(){
		
	}

}
