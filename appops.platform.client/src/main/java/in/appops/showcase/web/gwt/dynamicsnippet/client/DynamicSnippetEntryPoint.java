package in.appops.showcase.web.gwt.dynamicsnippet.client;

import in.appops.client.common.config.dsnip.ApplicationContext;
import in.appops.client.common.config.dsnip.PageProcessor;
import in.appops.client.common.config.util.Configurator;
import in.appops.client.common.config.util.ReusableSnippetStore;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

public class DynamicSnippetEntryPoint implements EntryPoint{
	
	@Override
	public void onModuleLoad() {
		Configurator.loadConfiguration();
		ReusableSnippetStore.loadSnippetDesc();
		
		Configuration appContextConfig = Configurator.getConfiguration("applicationContext");
		ArrayList<String> contextParamList = appContextConfig.getPropertyByName("contextparam");
		
		
		for(String param : contextParamList) {
			ApplicationContext.getInstance().setPropertyByName(param, null);
		}
		
		Element rootEle = RootPanel.get("art-main").getElement();
		String htmlDesc = rootEle.getInnerHTML();
		PageProcessor processor = new PageProcessor(htmlDesc);
		rootEle.setInnerHTML("");
		RootPanel.get("art-main").add(processor);

		processor.processPageDescription();
	}

}
