package in.appops.client.common.config.dsnip;


public interface SnippetGenerator {
	PageSnippetPresenter requestPageSnippet();

	HTMLSnippetPresenter requestHTMLSnippet(String type);
	
	HTMLSnippetPresenter requestHTMLSnippet(String type, String instance);
}
