package in.appops.client.common.config.dsnip;

import in.appops.platform.core.entity.Property;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gwt.user.client.ui.RootPanel;

public class PageSnippetPresenter extends HTMLSnippetPresenter {

	public PageSnippetPresenter(String type, String instance) {
		super(type, instance);
	}

	@Override
	protected void initialize() {
		model = dynamicFactory.requestModel(DynamicMvpFactory.PAGE);
		view = dynamicFactory.requestView(DynamicMvpFactory.PAGE);
		view.setModel(model);
		((PageSnippetView) view).setSnippetType(type);
		view.initialize();
	}

	@Override
	public void create() {
		super.create();
		RootPanel.get(type).getElement().setInnerHTML("");
		RootPanel.get(type).add(view);
	}

	/*@Override
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
			if(!((PageSnippetView) view).getElementMap().isEmpty()) {
				snippetFrom = (HTMLSnippetPresenter) ((PageSnippetView) view).getElementMap().get(transformFromSnippet);
				if(snippetFrom != null) {
					snippetFrom.removeHandlers();
				}
			}

			HTMLSnippetPresenter snippetTo = dynamicFactory.requestHTMLSnippet(transformToSnippet, snippetInstance);
			snippetTo.configure();
			snippetTo.create();
			((PageSnippetView) view).addAndReplaceElement(snippetTo.getView(), snippetFrom.getView().getElement());
			((PageSnippetView) view).getElementMap().put(transformToSnippet, snippetTo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	@Override
	protected	void applyConfiguration(HashMap<String, Property<? extends Serializable>>  preparedConfigMap) {
		try {
			/* Eg  - Preparedconfigmap = [si1.model.query.param1 = <value>
										  si2.model.query.param1 = <value>
										  si1.model.query.param2 = <value>
			*/
			HTMLSnippetPresenter snippetToUpdate = null;
			while(!preparedConfigMap.isEmpty()) {
				for(Entry<String, Property<? extends Serializable>> configToUpdateEntry : preparedConfigMap.entrySet()) {
					String qualifiedProperty = configToUpdateEntry.getKey(); // si1.model.query.param1
					Serializable propertyValue = configToUpdateEntry.getValue().getValue(); // <value>

					String snippetInstance = qualifiedProperty.substring(0, qualifiedProperty.indexOf(".")); // si1
					String propertyToUpdate = qualifiedProperty.substring(qualifiedProperty.indexOf(".") + 1); // model.query.param1

					/*
					 * Iterating all the configurations for the current snippet instance i.e. si1 in this case.
					 * Once si1 is completed it will do the same for si2 i.e. the process is repeated for all the snippet instance contained in the map.
					 */
					if((snippetToUpdate == null && (snippetToUpdate = (HTMLSnippetPresenter) ((PageSnippetView) view).getElementMap().get(snippetInstance)) != null ) ||
							(snippetToUpdate != null && snippetInstance.equals(snippetToUpdate.getModel().getInstance()))) {
						snippetToUpdate.getModel().updateConfiguration(propertyToUpdate, propertyValue);
						preparedConfigMap.remove(propertyToUpdate);
					}
				}
				snippetToUpdate = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
