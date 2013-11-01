package in.appops.showcase.web.gwt.dynamicsnippet.client;

import in.appops.client.common.config.cache.GlobalEntityCache;
import in.appops.client.common.config.dsnip.ApplicationContext;
import in.appops.client.common.config.dsnip.Context;
import in.appops.client.common.config.dsnip.DynamicMvpFactory;
import in.appops.client.common.config.dsnip.PageSnippetPresenter;
import in.appops.client.common.config.dsnip.event.EventActionRuleMap;
import in.appops.client.common.config.dsnip.event.EventActionRulesList;
import in.appops.client.common.config.dsnip.event.SnippetControllerRule;
import in.appops.client.common.config.field.textfield.TextField.TextFieldConstant;
import in.appops.client.common.config.util.Store;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.client.common.util.EntityToJsonClientConvertor;
import in.appops.client.common.util.JsonToEntityConverter;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;

public class DynamicSnippetEntryPoint implements EntryPoint{

	@Override
	public void onModuleLoad() {
		//createSnippetJsonConfiguration();
		//createEntityQueryCache();
		//createSnippetConfiguration();
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

	private void createSnippetJsonConfiguration() {
		String snippetJson = "{\"config\":{\"view\":{\"config\":{}}, \"eventActionRuleMap\":{\"eventActionRuleMap\":{\"labelField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"labelFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"labelFieldPreviewSnippetConfig\"}}}]}, \"textField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"textFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"textFieldPreviewSnippetConfig\"}}}]}, \"basicImageField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"basicImageFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"basicImageFieldPreviewSnippetConfig\"}}}]}, \"toggleImageField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"toggleImageFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"toggleImageFieldPreviewSnippetConfig\"}}}]}, \"numericTextField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"numericTextFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"numericTextFieldPreviewSnippetConfig\"}}}]}, \"numericSpinnerField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"numericSpinnerFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"numericSpinnerFieldPreviewSnippetConfig\"}}}]}}}, \"defaultPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"defaultLabelPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"disclosureLabel\"}, \"defaultValue\":{\"String\":\"To preview a component first expand a category and then choose a component\"},\"visible\":{\"Boolean\":\"true\"}}}}}}}, \"labelFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"labelFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"labelFieldPreview\"}, \"defaultValue\":{\"String\":\"I am a Label Field\"},\"visible\":{\"Boolean\":\"true\"}}}}}}}, \"textFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"textFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"textFieldPreview\"},\"visible\":{\"Boolean\":\"true\"}, \"suggestionText\":{\"String\":\"Enter text here \"}, \"suggestionPosition\":{\"String\":\"suggestionInline\"}, \"readOnly\":{\"Boolean\":\"false\"}, \"visibleLines\":{\"Integer\":\"1\"},\"fieldType\":{\"String\":\"txtbox\"}, \"valueType\":{\"StringValueType\":{\"maxlength\":{\"Integer\":\"50\"}, \"defaultValue\":{\"String\":\"This is a text box\"}}}}}}}}}, \"basicImageFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"basicImageFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"basicImageFieldPreview\"}, \"blobId\":{\"String\":\"images/userIcon.jpg\"},\"visible\":{\"Boolean\":\"true\"}}}}}}}, \"toggleImageFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"toggleImageFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"stateImgPrimaryCss\":{\"String\":\"toggleImageFieldPreview\"}, \"upStateUrl\":{\"String\":\"images/disclosureRightArrow.png\"}, \"downStateUrl\":{\"String\":\"images/disclosureDownArrow.png\"}, \"visible\":{\"Boolean\":\"true\"}}}}}}}, \"numericTextFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"numericTextFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"textFieldPreview\"},\"visible\":{\"Boolean\":\"true\"}, \"suggestionText\":{\"String\":\"Enter Number \"}, \"suggestionPosition\":{\"String\":\"suggestionInline\"}, \"readOnly\":{\"Boolean\":\"false\"}, \"visibleLines\":{\"Integer\":\"1\"},\"fieldType\":{\"String\":\"numeric\"}, \"errorPosition\":{\"String\":\"top\"}, \"validateField\":{\"Boolean\":\"true\"}, \"valueType\":{\"IntegerValueType\":{\"maxValue\":{\"Integer\":\"50\"}, \"minValue\":{\"Integer\":\"15\"}, \"defaultValue\":{\"String\":\"25\"}}}}}}}}}, \"numericSpinnerFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"numericSpinnerFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"visible\":{\"Boolean\":\"true\"}, \"step\":{\"Integer\":\"3\"}, \"unit\":{\"String\":\"km\"}, \"circular\":{\"Boolean\":\"true\"}, \"spinnerType\":{\"Integer\":\"1\"}, \"errorPosition\":{\"String\":\"bottom\"}, \"validateOnChange\":{\"Boolean\":\"true\"}, \"valueType\":{\"DecimalValueType\":{\"maxValue\":{\"Float\":\"50\"}, \"minValue\":{\"Float\":\"15\"}, \"defaultValue\":{\"String\":\"25\"}}}}}}}}}, \"listSpinnerFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"listSpinnerFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"visible\":{\"Boolean\":\"true\"}, \"valueListIndex\":{\"Integer\":\"0\"}, \"circular\":{\"Boolean\":\"true\"}, \"spinnerType\":{\"Integer\":\"2\"}, \"valueList\":{\"arrayList\": [\"Sunday\", \"Monday\", \"Tuesday\", \"Wednesday\", \"Thursday\", \"Friday\", \"Saturday\"]}}}}}}}}}";
		
		Entity snippetConfig = new JsonToEntityConverter().convertjsonStringToEntity(snippetJson);
		EventActionRuleMap eventActionRuleMap = snippetConfig.getPropertyByName("eventActionRuleMap");
		
		EventActionRulesList eventActionRulesList = new EventActionRulesList();
		
		SnippetControllerRule snippetControllerRule = new SnippetControllerRule();
		snippetControllerRule.setType(new MetaType("snippetControllerRule"));
		snippetControllerRule.addEventActionRule(SnippetControllerRule.HAS_TRANSFORMATION, true);
		snippetControllerRule.addEventActionRule(SnippetControllerRule.TRANSFORM_FROM_SNIPPET, "listSpinnerFieldPreviewSnippet");
		snippetControllerRule.addEventActionRule(SnippetControllerRule.TRANSFORM_TO_SNIPPET_INSTANCE, "listSpinnerFieldPreviewSnippetConfig");
		eventActionRulesList.addEventActionRule(snippetControllerRule);
		
		eventActionRuleMap.addEventActionRules("listSpinnerField", eventActionRulesList);
		
		
		JSONObject pageJsonObject = EntityToJsonClientConvertor.createJsonFromEntity(snippetConfig);
		System.out.println(pageJsonObject.toString());
	}
	
	private void createEntityQueryCache() {
		/*String entityCachejson = "{\"config\":{\"cache\":{\"map\":{\"3##Category##1\":{\"in.appops.platform.server.core.service.blogging.domain.Category\":{\"id\":{\"Key\":{\"Long\":\"1\"}}, \"title\":{\"String\":\"Fields\"}}}, \"3##Category##2\":{\"in.appops.platform.server.core.service.blogging.domain.Category\":{\"id\":{\"Key\":{\"Long\":\"2\"}}, \"title\":{\"String\":\"Lists\"}}}, \"3##Category##3\":{\"in.appops.platform.server.core.service.blogging.domain.Category\":{\"id\":{\"Key\":{\"Long\":\"3\"}}, \"title\":{\"String\":\"Menus\"}}}, \"3##Component##1\":{\"in.appops.platform.server.core.service.blogging.domain.Component\":{\"id\":{\"Key\":{\"Long\":\"1\"}}, \"name\":{\"String\":\"labelField\"}, \"title\":{\"String\":\"Label\"}}}, \"3##Component##2\":{\"in.appops.platform.server.core.service.blogging.domain.Component\":{\"id\":{\"Key\":{\"Long\":\"2\"}}, \"name\":{\"String\":\"textField\"}, \"title\":{\"String\":\"Text\"}}}, \"3##Component##3\":{\"in.appops.platform.server.core.service.blogging.domain.Component\":{\"id\":{\"Key\":{\"Long\":\"3\"}}, \"name\":{\"String\":\"basicImageField\"}, \"title\":{\"String\":\"Basic Image\"}}}, \"3##Component##4\":{\"in.appops.platform.server.core.service.blogging.domain.Component\":{\"id\":{\"Key\":{\"Long\":\"4\"}}, \"name\":{\"String\":\"toggleImageField\"}, \"title\":{\"String\":\"Toggle Image\"}}}, \"3##Component##5\":{\"in.appops.platform.server.core.service.blogging.domain.Component\":{\"id\":{\"Key\":{\"Long\":\"5\"}}, \"name\":{\"String\":\"numericTextField\"}, \"title\":{\"String\":\"Numeric Text Field\"}}}, \"3##Component##6\":{\"in.appops.platform.server.core.service.blogging.domain.Component\":{\"id\":{\"Key\":{\"Long\":\"6\"}}, \"name\":{\"String\":\"numericSpinnerField\"}, \"title\":{\"String\":\"Numeric Spinner Field\"}}}, \"3##Component##7\":{\"in.appops.platform.server.core.service.blogging.domain.Component\":{\"id\":{\"Key\":{\"Long\":\"7\"}}, \"name\":{\"String\":\"listSpinnerField\"}, \"title\":{\"String\":\"List Spinner Field\"}}} }}}}";
		
		Entity cacheEntity = new JsonToEntityConverter().convertjsonStringToEntity(entityCachejson);
		HashMap<String, Entity> cacheMap = cacheEntity.getPropertyByName("cache");
		
		Entity entity = new Entity();
		Type entityType = new MetaType("in.appops.platform.server.core.service.blogging.domain.Component");
		entityType.setServiceId(3L);
		
		entity.setType(entityType);
		
		Key<Long> idKey = new Key<Long>(8L);
		entity.setPropertyByName("id", idKey);
		entity.setPropertyByName("name", "buttonField");
		entity.setPropertyByName("title", "Button Field");
		
		String entityIdentifier = GlobalEntityCache.getEntityIdentifer(entity);
		cacheMap.put(entityIdentifier, entity);
		
		JSONObject entityJsonObject = EntityToJsonClientConvertor.createJsonFromEntity(cacheEntity);
		System.out.println(entityJsonObject.toString());
		
		String querycache = "{\"config\":{\"cache\":{\"map\":{\"getAllCategories\":[\"3##Category##1\",\"3##Category##2\", \"3##Category##3\"], \"getAllComponents?category=1\":[\"3##Component##1\",\"3##Component##2\", \"3##Component##3\", \"3##Component##4\", \"3##Component##5\", \"3##Component##6\", \"3##Component##7\"]}}}}";
		
		Entity queryCacheEntity = new JsonToEntityConverter().convertjsonStringToEntity(querycache);
		HashMap<String, ArrayList<String>> queryCacheMap = queryCacheEntity.getPropertyByName("cache");
		ArrayList<String> entityPointerList = queryCacheMap.get("getAllComponents?category=1");
		entityPointerList.add(entityIdentifier);
		
		JSONObject queryCacheJsonObject = EntityToJsonClientConvertor.createJsonFromEntity(queryCacheEntity);
		System.out.println(queryCacheJsonObject.toString());*/
		
		Entity epic = new Entity();
		epic.setType(new MetaType("Epic"));
		Key<Long> epicKey = new Key<Long>(2L);
		epic.setPropertyByName("id", epicKey);
		epic.setPropertyByName("title", "Now & next - A detailed task management system");

		Entity createdBy = new Entity();
		createdBy.setType(new MetaType("User"));
		Key<Long> createdByKey = new Key<Long>(3L);
		createdBy.setPropertyByName("id", createdByKey);
		createdBy.setPropertyByName("name", "Mahesh");

		Entity modifiedBy = new Entity();
		modifiedBy.setType(new MetaType("User"));
		Key<Long> modifiedByKey = new Key<Long>(4L);
		modifiedBy.setPropertyByName("id", modifiedByKey);
		modifiedBy.setPropertyByName("name", "Nitish");

		Entity status = new Entity();
		status.setType(new MetaType("Epic"));
		Key<Long> statusKey = new Key<Long>(5L);
		status.setPropertyByName("id", statusKey);
		status.setPropertyByName("name", "Identified");
		
		Entity story = new Entity();
		story.setType(new MetaType("Story"));
		
		Key<Long> storyKey = new Key<Long>(6L);
		story.setPropertyByName("id", storyKey);
		story.setPropertyByName("title", "User can change task status effortlessly and in one click");
		
		String str_date="11-June-13";
		Date modifiedOn = DateTimeFormat.getFormat("dd-MMM-yy").parse(str_date);
		story.setPropertyByName("modifiedOn", modifiedOn);

		story.setProperty("epic", epic);
		story.setProperty("status", status);
		story.setProperty("createdBy", createdBy);
		story.setProperty("modifiedBy", modifiedBy);
	
		JSONObject storyJsonObject = EntityToJsonClientConvertor.createJsonFromEntity(story);
		System.out.println(storyJsonObject.toString());
		
	}
	
	private void createSnippetConfiguration() {
		Configuration snippetConfiguration = new Configuration();
		snippetConfiguration.setType(new MetaType("config"));

		Configuration viewConfiguration = new Configuration();
		viewConfiguration.setType(new MetaType("config"));
		snippetConfiguration.setProperty("view", viewConfiguration);
		
		Configuration fieldConfiguration = new Configuration();
		fieldConfiguration.setType(new MetaType("config"));

		snippetConfiguration.setProperty("fieldConfigurationInstance", fieldConfiguration); // fieldConfigurationInstance is the 
																							// Data-config value in field span
		
		Configuration fieldViewConfiguration = new Configuration();
		fieldViewConfiguration.setType(new MetaType("config"));
		fieldViewConfiguration.setPropertyByName(TextFieldConstant.TF_VISLINES, 3);
		fieldViewConfiguration.setPropertyByName(TextFieldConstant.BF_READONLY, true);
		fieldViewConfiguration.setPropertyByName(TextFieldConstant.BF_PCLS, "primaryCss");
		fieldViewConfiguration.setPropertyByName(TextFieldConstant.BF_DCLS, "dependentCss");
		fieldViewConfiguration.setPropertyByName(TextFieldConstant.BF_ALLOWBLNK, false);
		fieldViewConfiguration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
		fieldViewConfiguration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter field value");
		fieldViewConfiguration.setPropertyByName(TextFieldConstant.BF_VALIDATEONCHANGE, true);
		fieldViewConfiguration.setPropertyByName(TextFieldConstant.BF_ERRPOS, TextFieldConstant.BF_SIDE);
		fieldViewConfiguration.setPropertyByName(TextFieldConstant.VALIDATEFIELD, true);
		
		fieldConfiguration.setProperty("view", fieldViewConfiguration);
		

		
		JSONObject pageJsonObject = EntityToJsonClientConvertor.createJsonFromEntity(snippetConfiguration);
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
		DynamicMvpFactory snippetGenerator = injector.getMVPFactory();

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
