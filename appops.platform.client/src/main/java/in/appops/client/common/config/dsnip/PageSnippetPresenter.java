package in.appops.client.common.config.dsnip;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

public class PageSnippetPresenter extends HTMLSnippetPresenter {
	
	private final static String PAGE = "page";
	
	private final static Element pageSpan = RootPanel.get(PAGE).getElement();
	
	public PageSnippetPresenter() {
		super(PAGE, pageSpan.getAttribute("data-config"));
	}
	
	@Override
	protected void init() {
		model = new PageSnippetModel();
		String pageDescription = model.getDescription(getSnippetType());

		if(pageDescription != null && !pageDescription.equals("")) {
			view = new PageSnippetView();
		}
	}
	
	@Override
	protected void configure() {
		// TODO
	}
	
	public void createPage() {
		RootPanel.get(PAGE).getElement().setInnerHTML("");
		RootPanel.get(PAGE).add(view);
		create();
	}
	
}
