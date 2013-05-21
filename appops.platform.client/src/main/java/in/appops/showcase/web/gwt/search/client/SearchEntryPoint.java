package in.appops.showcase.web.gwt.search.client;

import in.appops.client.gwt.web.ui.search.SearchListModel;
import in.appops.client.gwt.web.ui.search.SearchListSnippet;
import in.appops.client.gwt.web.ui.search.SearchWidget;
import in.appops.platform.core.entity.query.Query;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author mahesh@ensarm.com	
 *
 */
public class SearchEntryPoint implements EntryPoint{

	@Override
	public void onModuleLoad() {
		try{
			SearchListModel searchListModel = new SearchListModel();
			searchListModel.setOperationNameToBind("social.SocialService.getSearchList");
			Query appopsQuery = new Query();
			appopsQuery.setQueryName("getEntitiesForSearchWidget");
			appopsQuery.setListSize(10);
			searchListModel.setQueryToBind(appopsQuery);
			
			SearchWidget searchWidget = new SearchWidget(searchListModel);
		
			VerticalPanel basePanel = new VerticalPanel();
			basePanel.setStylePrimaryName("fullWidth");
			
			SearchListSnippet searchListSnippet = new SearchListSnippet();
			searchListModel.setEntityListReceiver(searchListSnippet);
			basePanel.add(searchWidget);
			basePanel.add(searchListSnippet);
			
			basePanel.setCellHorizontalAlignment(searchWidget, HorizontalPanel.ALIGN_CENTER);
			basePanel.setCellHorizontalAlignment(searchListSnippet, HorizontalPanel.ALIGN_CENTER);
			
			RootPanel.get().add(basePanel);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
