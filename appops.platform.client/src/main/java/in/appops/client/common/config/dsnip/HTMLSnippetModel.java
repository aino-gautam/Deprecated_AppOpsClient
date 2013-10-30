package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.model.ConfigurationModel;
import in.appops.client.common.config.util.Store;

/**
 * 
 * @author nitish / nairutee
 * 
 */
public class HTMLSnippetModel extends ConfigurationModel {

	/**
	 * Fetches the snippet description from the Store
	 * @param snippetType
	 * @return String - html description of the snippet
	 */
	public String getDescription(String snippetType) {
		String description = Store.getFromSnippetStore(snippetType);
		return description;
	}
	
}
