package in.appops.showcase.web.gwt.dynamicsnippet.client;

import in.appops.client.common.config.cache.GlobalEntityCache;
import in.appops.client.common.config.dsnip.ApplicationContext;
import in.appops.client.common.config.dsnip.PageProcessor;
import in.appops.client.common.config.util.ReusableSnippetStore;
import in.appops.client.common.config.util.Store;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

public class DynamicSnippetEntryPoint implements EntryPoint{

	@Override
	public void onModuleLoad() {
		/**
		 * Load Configuration in a dictionary.
		 */
		Store.loadStore();

		/**
		 * Load all the reusable snippet descriptions to a dictionary.
		 */
		ReusableSnippetStore.loadSnippetDesc();
		
		
		/**
		 * Load Cache
		 */
		Configuration queryCacheConfiguration = Store.getFromCache("querycache");
		Map<String, ArrayList<String>> queryCache = (Map<String, ArrayList<String>>) queryCacheConfiguration.getPropertyByName("cache");

		Configuration entityCacheConfiguration = Store.getFromCache("entitycache");
		Map<String, Entity> entityCache = (Map<String, Entity>) entityCacheConfiguration.getPropertyByName("cache");

		GlobalEntityCache globalEntityCache = GlobalEntityCache.getInstance();
		globalEntityCache.setQueryCache(queryCache);
		globalEntityCache.setEntityCache(entityCache);

		/**
		 * Add application properties to ApplicationContext
		 */
		Configuration appContextConfig = Store.getConfiguration("applicationContext");
		if(appContextConfig != null) {
			ArrayList<String> contextParamList = appContextConfig.getPropertyByName("contextparam");
	
			for (String param : contextParamList) {
				ApplicationContext.getInstance().setPropertyByName(param, null);
			}
		}

		/**
		 * From the html page get the content. All the content intended for
		 * processing should be enclosed in an element with an id. With
		 * reference to this id the content would be extracted and give to the
		 * page processor.
		 */
		Element rootEle = RootPanel.get("art-main").getElement();
		String htmlDesc = rootEle.getInnerHTML();

		PageProcessor processor = new PageProcessor(htmlDesc);
		rootEle.setInnerHTML("");

		RootPanel.get("art-main").add(processor);

		processor.processPageDescription();
	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	public void onModuleLoad() {
		DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
		DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
		
		String queryName = "getAllBlogs";
		
		Query query = new Query();
		query.setQueryName(queryName);
		
		
		Map<String, Serializable> queryParam = new HashMap<String, Serializable>();
		queryParam.put("query", query);
		
		StandardAction action = new StandardAction(EntityList.class, "blogging.BloggingService.getArticles", queryParam);
		
		final HashMap<String, ArrayList<String>> queryCacheMap = new HashMap<String, ArrayList<String>>();
		final Configuration entityCacheConfig = new Configuration();
		entityCacheConfig.setType(new MetaType("config"));

		final ArrayList<String> entityPointerList = new ArrayList<String>();
		
		final GlobalEntityCache globalEntityCache = GlobalEntityCache.getInstance();
		
		final String queryPointer = globalEntityCache.getQueryIdentifier(query);

		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result<EntityList> result) {
				EntityList entList = (EntityList) result.getOperationResult();
				//TODO
				if(entList != null) {
					for(Entity entity : entList) {
						String entityPointer = globalEntityCache.getEntityIdentifer(entity);
						entityCacheConfig.setProperty(entityPointer, entity);
					}
					JSONObject entityCacheJson = EntityToJsonClientConvertor.createJsonFromEntity(entityCacheConfig);
					System.out.println(entityCacheJson.toString());
				}
			
			}
		});
	}
*/
}
