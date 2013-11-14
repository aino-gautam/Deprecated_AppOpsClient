package in.appops.client.common.config.dsnip;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

public class PageSnippetModel extends HTMLSnippetModel {

	@Override
	public String getDescription(String snippetType) {
		Element pageElement = RootPanel.get(snippetType).getElement();
		String pageDescription = pageElement.getInnerHTML();
		return pageDescription;
	}
}
