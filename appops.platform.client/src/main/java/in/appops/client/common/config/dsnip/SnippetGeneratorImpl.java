package in.appops.client.common.config.dsnip;


/**
 * @author nitish@ensarm.com	
 * Class that will be responsible for return proper instance {@link HTMLSnippetPresenter}
 * Depending on the type of the html.
 */
public class SnippetGeneratorImpl implements SnippetGenerator {

	private final String PAGESNIPPET = "pageSnippet";

	public HTMLSnippetPresenter requestHTMLSnippet(String type, String instance) {
		try{
			HTMLSnippetPresenter snippetPresenter = null;
			if(type.equalsIgnoreCase(PAGESNIPPET)) {
				snippetPresenter = new PageSnippetPresenter();
			}  else {
				snippetPresenter = new HTMLSnippetPresenter(type, instance);
			}
			return snippetPresenter;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public HTMLSnippetPresenter requestHTMLSnippet(String type) {
		return requestHTMLSnippet(type, null);
	}

	@Override
	public PageSnippetPresenter requestPageSnippet() {
		PageSnippetPresenter pageSnippetPresenter = (PageSnippetPresenter)requestHTMLSnippet(PAGESNIPPET);
		return pageSnippetPresenter;
	}
}
