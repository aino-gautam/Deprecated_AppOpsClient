package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.util.Configurator;
import in.appops.client.common.config.util.ReusableSnippetStore;
import in.appops.platform.core.shared.Configuration;


public class SnippetGeneratorImpl implements SnippetGenerator {

/*	@Override
	public DynamicSnippet generateSnippet(String htmlDesc) {
		DynamicSnippet snippet = new DynamicSnippet(htmlDesc);
		snippet.processSnippetDescription();
		return snippet;
	}

	@Override
	public DynamicSnippet generateSnippet(String type, String instance) {

		String snippetDesc = ReusableSnippetStore.getSnippetDesc(type);
		snippetDesc = processPlaceHolders(snippetDesc);
		
		
		DynamicSnippet snippet = new DynamicSnippet(snippetDesc);
		snippet.setInstance(instance);
		snippet.processSnippetDescription();		
		return snippet;
	}

	private String processPlaceHolders(String desc) {
		HTMLPanel panel = new HTMLPanel(desc);
		NodeList<Element> nodeList = panel.getElement().getElementsByTagName("span");
		int lengthOfNodes = nodeList.getLength();
		for (int i = lengthOfNodes - 1; i > -1; i--) { // Iterating through the <span> elements
			Node node = nodeList.getItem(i);
			Element rootFormElement = Element.as(node);
			if (rootFormElement != null) {
				if (rootFormElement.hasAttribute("widgetContainerType") && rootFormElement.getAttribute("widgetContainerType").equals("form")) {
					rootFormElement.setId(rootFormElement.getId() + UUIDGenerator.uuid());
				}
			}
		}
		
		String processedDesc = panel.getElement().getInnerHTML();
		return processedDesc;
	} */

	@Override
	public HTMLSnippetPresenter generateSnippet(String type, String instance) {
		HTMLSnippetPresenter snippetPres = new HTMLSnippetPresenter();
		snippetPres.setSnippetType(type);
		snippetPres.setSnippetInstance(instance);
		snippetPres.init();
		snippetPres.create();
		return snippetPres;
	}
	

}
