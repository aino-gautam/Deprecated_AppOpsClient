package in.appops.client.common.config.dsnip;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;


public class PageSnippetView extends HTMLSnippetView {

	@Override
	public void create() {
		try {
			NodeList<Element> spanList = getAllSpanNodes();

			if(spanList != null) {
				int totalSpans = spanList.getLength();

				for (int i = totalSpans - 1; i > -1; i--) {
					Node node = spanList.getItem(i);
					Element pageSpan = Element.as(node);
					pageSpan.setId(pageSpan.getId());
					if (pageSpan != null) {
						if (pageSpan.getAttribute(COMPONENT_TYPE).equalsIgnoreCase(HTMLSNIPPET)) {
							String type = pageSpan.getAttribute(TYPE);
							String instance = pageSpan.getAttribute(DATA_CONFIG);
							HTMLSnippetPresenter snippetPres = mvpFactory.requestHTMLSnippet(type, instance);

							Context snippetContext = new Context();
							snippetPres.getModel().setContext(snippetContext);

							snippetPres.configure();
							snippetPres.create();
							elementMap.put(instance, snippetPres);
							snippetPanel.addAndReplaceElement(snippetPres.getView(), pageSpan);
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
