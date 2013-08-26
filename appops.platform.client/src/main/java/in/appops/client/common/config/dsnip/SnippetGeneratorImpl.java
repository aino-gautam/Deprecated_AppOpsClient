package in.appops.client.common.config.dsnip;

import com.google.gwt.dom.client.Element;

import in.appops.client.common.config.form.FormSnippetPresenter;
import in.appops.client.common.config.util.Configurator;
import in.appops.client.common.config.util.ReusableSnippetStore;
import in.appops.platform.core.shared.Configuration;

/**
 * @author nitish@ensarm.com	
 * Class that will be responsible for return proper instance {@link HTMLSnippetPresenter}
 * Depending on the type of the html.
 */
public class SnippetGeneratorImpl implements SnippetGenerator {

	private final String FORMSNIPPET = "formSnippet";

	/**
	 * The method which will return the instance of type {@link HTMLSnippetPresenter}
	 * depending on the type string passed to it.
	 * @param type
	 * @return
	 */
	@Override
	public HTMLSnippetPresenter generateSnippet(String type, String instance) {
		try{
			String snippetDesc = ReusableSnippetStore.getSnippetDesc(type);
			Configuration configuration = Configurator.getConfiguration(instance);

			HTMLSnippetPresenter snippetPres;
			HTMLSnippet snippet = new HTMLSnippet(snippetDesc);
			Element node = snippet.getElement().getFirstChildElement();

			String nodeName = node.getNodeName();

			if(nodeName.equalsIgnoreCase(FORMSNIPPET)){
				snippet = new HTMLSnippet(node.getInnerHTML());
				snippetPres = new FormSnippetPresenter();
			}
			else {
				snippetPres = new HTMLSnippetPresenter();
			}
			snippetPres.setConfiguration(configuration);
			snippetPres.setHtmlSnippet(snippet);
			snippetPres.init();
			snippetPres.create();
			return snippetPres;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
