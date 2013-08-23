package in.appops.client.common.config.dsnip;


public interface SnippetGenerator {
	/**
	 * Takes the html description for the snippet, creates and returns the dynamic snippet.
	 * @param htmlDesc
	 * @return
	 */
//	DynamicSnippet generateSnippet(String htmlDesc);
//	DynamicSnippet generateSnippet(String type, String instance);

	HTMLSnippetPresenter generateSnippet(String type, String instance);
}
