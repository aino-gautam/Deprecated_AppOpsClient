package in.appops.showcase.web.gwt.dynamicsnippet.client;

import in.appops.client.common.config.cache.GlobalEntityCache;
import in.appops.client.common.config.dsnip.ApplicationContext;
import in.appops.client.common.config.dsnip.Context;
import in.appops.client.common.config.dsnip.DynamicMVPFactory;
import in.appops.client.common.config.dsnip.PageSnippetPresenter;
import in.appops.client.common.config.dsnip.event.EventActionRuleMap;
import in.appops.client.common.config.dsnip.event.EventActionRulesList;
import in.appops.client.common.config.dsnip.event.SnippetControllerRule;
import in.appops.client.common.config.util.Store;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.client.common.util.EntityToJsonClientConvertor;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;

public class DynamicSnippetEntryPoint implements EntryPoint{

	@Override
	public void onModuleLoad() {
		//createJsonConfiguration();
		init();
		start();
	}

	private void createJsonConfiguration() {
		Configuration pageConfiguration = new Configuration();
		pageConfiguration.setType(new MetaType("config"));

		EventActionRuleMap eventActionRuleMap = new EventActionRuleMap();
		eventActionRuleMap.setType(new MetaType("eventActionRuleMap"));

		EventActionRulesList eventActionRulesList = new EventActionRulesList();

		SnippetControllerRule snippetControllerRule = new SnippetControllerRule();
		snippetControllerRule.setType(new MetaType("snippetControllerRule"));
		snippetControllerRule.addEventActionRule(SnippetControllerRule.HAS_TRANSFORMATION, true);
		snippetControllerRule.addEventActionRule(SnippetControllerRule.TRANSFORM_FROM_SNIPPET, "toggleHeaderFooterSnippet");
		snippetControllerRule.addEventActionRule(SnippetControllerRule.TRANSFORM_TO_SNIPPET, "newHeaderFooterSnippet");
		snippetControllerRule.addEventActionRule(SnippetControllerRule.TRANSFORM_TO_SNIPPET_INSTANCE, "newHeaderFooterSnippetConfig");
		eventActionRulesList.addEventActionRule(snippetControllerRule);

		eventActionRuleMap.addEventActionRules("onToggleHeader", eventActionRulesList);

		pageConfiguration.setProperty("eventActionRuleMap", eventActionRuleMap);

		JSONObject pageJsonObject = EntityToJsonClientConvertor.createJsonFromEntity(pageConfiguration);
		System.out.println(pageJsonObject.toString());
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
		DynamicMVPFactory snippetGenerator = injector.getMVPFactory();

		PageSnippetPresenter snippetPres = snippetGenerator.requestPageSnippet();
		Context pageContext = new Context();
		snippetPres.getModel().setContext(pageContext);
		snippetPres.configure();
		snippetPres.create();
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
