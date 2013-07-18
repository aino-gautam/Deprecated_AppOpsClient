package in.appops.showcase.web.gwt.dynamicsnippet.client;

import in.appops.client.common.config.dsnip.PageProcessor;
import in.appops.client.common.config.util.Configurator;
import in.appops.client.common.config.util.ReusableSnippetStore;
import in.appops.client.common.util.EntityToJsonClientConvertor;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DynamicSnippetEntryPoint implements EntryPoint{
	
	@Override
	public void onModuleLoad() {
		Configurator.loadConfiguration();
		ReusableSnippetStore.loadSnippetDesc();
		Element rootEle = RootPanel.get("art-main").getElement();
		String htmlDesc = rootEle.getInnerHTML();
		PageProcessor processor = new PageProcessor(htmlDesc);
		rootEle.setInnerHTML("");
		RootPanel.get("art-main").add(processor);

		processor.processPageDescription();
		//processor.loadPage();
		//processor.attach();
		//RootPanel.getBodyElement().replaceChild(rootEle, processor.getElement());
		//processor.attach();
	}

	/*@Override
	public void onModuleLoad() {
		Configuration configuration = new Configuration();
		configuration.setType(new MetaType("config"));
		
		
		Configuration areditField = new Configuration();
		areditField.setType(new MetaType("config"));
		areditField.setPropertyByName(ActionFieldConstant.AF_PAGE, "authorhome.html");
		areditField.setPropertyByName(ActionFieldConstant.ACSS, "recentPost-articleLinkField");
		areditField.setPropertyByName(ActionFieldConstant.BF_DEFVAL, "Author Home");
		areditField.setPropertyByName(ActionFieldConstant.BF_VISIBLE, true);
		areditField.setPropertyByName(ActionFieldConstant.MODE, 2);

		Configuration writeConf = new Configuration();
		writeConf.setType(new MetaType("config"));
		writeConf.setPropertyByName(ActionFieldConstant.AF_PAGE, "writehome.html");
		writeConf.setPropertyByName(ActionFieldConstant.ACSS, "recentPost-articleLinkField");
		writeConf.setPropertyByName(ActionFieldConstant.BF_DEFVAL, "Write Article");
		writeConf.setPropertyByName(ActionFieldConstant.MODE, 2);
		writeConf.setPropertyByName(ActionFieldConstant.BF_VISIBLE, false);
		
		Configuration blogHomeConf = new Configuration();
		blogHomeConf.setType(new MetaType("config"));
		blogHomeConf.setPropertyByName(ActionFieldConstant.AF_PAGE, "dynamicsnippet.html");
		blogHomeConf.setPropertyByName(ActionFieldConstant.ACSS, "recentPost-articleLinkField");
		blogHomeConf.setPropertyByName(ActionFieldConstant.BF_DEFVAL, "Author Home");
		blogHomeConf.setPropertyByName(ActionFieldConstant.BF_VISIBLE, true);
		blogHomeConf.setPropertyByName(ActionFieldConstant.MODE, 2);
		 
		
//		Configuration labelField = new Configuration();
//		labelField.setType(new MetaType("config"));
//		labelField.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Posted On");
//		labelField.setPropertyByName(LabelFieldConstant.BF_PCLS, "postenOnLabelFieldBase");
//		labelField.setPropertyByName(LabelFieldConstant.LBLFD_FCSS, "postenOnLabelField");
//
//		Configuration label1Field = new Configuration();
//		label1Field.setType(new MetaType("config"));
//		label1Field.setPropertyByName(LabelFieldConstant.BF_BINDPROP, "createdOn");
//		label1Field.setPropertyByName(LabelFieldConstant.BF_PCLS, "postenOnLabelFieldBase");
//		label1Field.setPropertyByName(LabelFieldConstant.LBLFD_FCSS, "postenDateLabelField");
//
//		Configuration htmlField = new Configuration();
//		htmlField.setType(new MetaType("config"));
//		htmlField.setPropertyByName(HTMLFieldConstant.BF_BINDPROP, "contentBlobId");
//		htmlField.setPropertyByName(HTMLFieldConstant.BF_PCLS, "article-SummaryHTMLFieldBase");
//		htmlField.setPropertyByName(HTMLFieldConstant.LBLFD_FCSS, "article-summaryHTMLField");
		
		 		
//		configuration.setProperty("entityLink", arlinkField);
//		configuration.setProperty("entityEditLink", areditField);
//		configuration.setProperty("postedDate", label1Field);
//		configuration.setProperty("content", htmlField);
		
		
//				Configuration configModel = new Configuration();
//		configModel.setType(new MetaType("config"));
//		configModel.setPropertyByName(ListComponentConstant.BC_MODELOP, "getRecentPost");
//
		Configuration configView = new Configuration();
		configView.setType(new MetaType("config"));
//		configView.setPropertyByName(ListComponentConstant.BC_HEADER, true);
//		configView.setPropertyByName(ListComponentConstant.BC_HEADERVAL, "Recent Posts");
//		ArrayList<String> interestedEvents = new ArrayList<String>();
//		interestedEvents.add("viewArticle");
//		interestedEvents.add("editArticle");
//		
		configView.setProperty("authorHomeLinkConfig", areditField);
		configView.setProperty("writeArticleLinkConfig", writeConf);
		configView.setProperty("blogHomeLinkConfig", blogHomeConf);
//		
//		configuration.setProperty(ListComponentConstant.BC_CONFIGMODEL, configModel);
		configuration.setProperty(ListComponentConstant.BC_CONFIGVIEW, configView); 	
		
//		Configuration onLoadEvent = new Configuration();
//		onLoadEvent.setType(new MetaType("config"));
//		onLoadEvent.setPropertyByName(EventConstant.EVNT_TYPE, EventConstant.EVNT_TRANSWGT); 	
//		onLoadEvent.setPropertyByName(EventConstant.EVNT_TRANSTYPE, EventConstant.EVNT_COMPONENT); 	
//		onLoadEvent.setPropertyByName(EventConstant.EVNT_TRANSTO, "listComponent"); 	
//		onLoadEvent.setPropertyByName(EventConstant.EVNT_TRANSINS, "recentArticleListConfig"); 	
//		
//		Configuration onViewArticleEvent = new Configuration();
//		onViewArticleEvent.setType(new MetaType("config"));
//		onViewArticleEvent.setPropertyByName(EventConstant.EVNT_TYPE, EventConstant.EVNT_TRANSWGT); 	
//		onViewArticleEvent.setPropertyByName(EventConstant.EVNT_TRANSTYPE, EventConstant.EVNT_SNIPPET); 	
//		onViewArticleEvent.setPropertyByName(EventConstant.EVNT_TRANSTO, "entityContentSnippet"); 	
//		onViewArticleEvent.setPropertyByName(EventConstant.EVNT_TRANSINS, "onLoadArticleContentSnippet");
//		
//		Configuration onEditArticleEvent = new Configuration();
//		onEditArticleEvent.setType(new MetaType("config"));
//		onEditArticleEvent.setPropertyByName(EventConstant.EVNT_TYPE, EventConstant.EVNT_TRANSWGT); 	
//		onEditArticleEvent.setPropertyByName(EventConstant.EVNT_TRANSTYPE, EventConstant.EVNT_COMPONENT); 	
//		onEditArticleEvent.setPropertyByName(EventConstant.EVNT_TRANSTO, "entityEditCreateSnippet"); 	
//		onEditArticleEvent.setPropertyByName(EventConstant.EVNT_TRANSINS, "articleEditCreateSnippet"); 	
//
//		HashMap<String, Configuration> eventMap = new HashMap<String, Configuration>();
//		eventMap.put("onLoad", onLoadEvent);
//		eventMap.put("onViewArticle", onViewArticleEvent);
//		eventMap.put("onEditArticle", onEditArticleEvent);
//		
//		configuration.setPropertyByName(ContainerConstant.CT_INTRSDEVNTS, eventMap); 	

		JSONObject configJson = EntityToJsonClientConvertor.createJsonFromEntity(configuration);
		System.out.println(configJson);
	} */	

}
