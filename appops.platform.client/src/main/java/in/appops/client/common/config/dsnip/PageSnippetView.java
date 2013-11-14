package in.appops.client.common.config.dsnip;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;


public class PageSnippetView extends HTMLSnippetView {
	private final String HTMLSNIPPET = "htmlsnippet";
	private final String WIDGET_TYPE = "widgetType";
	private final String DATA_CONFIG = "data-config";
	
	@Override
	protected void processSnippetDescription() {
		try {
			NodeList<Element> spanList = getAllSpanNodes();
			
			if(spanList != null) {
				int totalSpans = spanList.getLength();
				
				for (int i = totalSpans - 1; i > -1; i--) {
					Node node = spanList.getItem(i);
					Element pageSpan = Element.as(node);
					pageSpan.setId(pageSpan.getId());
					if (pageSpan != null) {
						if (pageSpan.hasAttribute(WIDGET_TYPE) && pageSpan.getAttribute(WIDGET_TYPE).equals(HTMLSNIPPET)) {
							String type = pageSpan.getAttribute(WIDGET_TYPE);
							String instance = pageSpan.getAttribute(DATA_CONFIG);
							HTMLSnippetPresenter snippetPres = snippetGenerator.requestHTMLSnippet(type, instance);
							snippetPres.create();
							
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
