package in.appops.showcase.web.gwt.dynamicsnippet.client;

import in.appops.client.common.config.cache.GlobalEntityCache;
import in.appops.client.common.config.dsnip.ApplicationContext;
import in.appops.client.common.config.dsnip.HTMLSnippetPresenter;
import in.appops.client.common.config.dsnip.PageSnippetPresenter;
import in.appops.client.common.config.dsnip.SnippetGenerator;
import in.appops.client.common.config.util.Store;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class DynamicSnippetEntryPoint implements EntryPoint{
	
	private final String PAGESNIPPET = "pageSnippet";
	
	@Override
	public void onModuleLoad() {
		
		init();
		start();
	}

	private void start() {
		/**
		 * From the html page get the content. All the content intended for
		 * processing should be enclosed in an element with an id. With
		 * reference to this id the content would be extracted and give to the
		 * page processor.
		 */
/*		Element pageElement = RootPanel.get("page").getElement();
		String pageDescription = pageElement.getInnerHTML();

		PageProcessor processor = new PageProcessor(pageDescription);
		pageElement.setInnerHTML("");

		RootPanel.get("art-main").add(processor);
		processor.processPageDescription();		*/

		AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
		SnippetGenerator snippetGenerator = (SnippetGenerator)injector.getSnippetGenerator();

		PageSnippetPresenter snippetPres = snippetGenerator.requestPageSnippet();
		snippetPres.createPage();
	}

	private void init() {
		Store.loadStore();
		Configuration queryCacheConfiguration = Store.getFromCacheStore("querycache");
		Map<String, ArrayList<String>> queryCache = (Map<String, ArrayList<String>>) queryCacheConfiguration.getPropertyByName("cache");

		Configuration entityCacheConfiguration = Store.getFromCacheStore("entitycache");
		Map<String, Entity> entityCache = (Map<String, Entity>) entityCacheConfiguration.getPropertyByName("cache");

		GlobalEntityCache globalEntityCache = GlobalEntityCache.getInstance();
		globalEntityCache.setQueryCache(queryCache);
		globalEntityCache.setEntityCache(entityCache);

		/**
		 * Add application properties to ApplicationContext
		 */
		Configuration appContextConfig = Store.getFromConfigurationStore("applicationContext");
		if(appContextConfig != null) {
			ArrayList<String> contextParamList = appContextConfig.getPropertyByName("contextparam");
	
			for (String param : contextParamList) {
				ApplicationContext.getInstance().setPropertyByName(param, null);
			}
		}
	}
	
}
