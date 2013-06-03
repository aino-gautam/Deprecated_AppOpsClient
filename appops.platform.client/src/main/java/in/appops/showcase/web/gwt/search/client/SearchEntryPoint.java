package in.appops.showcase.web.gwt.search.client;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.SearchEvent;
import in.appops.client.common.event.handlers.SearchEventHandler;
import in.appops.client.gwt.web.ui.search.SearchListModel;
import in.appops.client.gwt.web.ui.search.SearchListSnippet;
import in.appops.client.gwt.web.ui.search.SearchWidget;
import in.appops.platform.core.entity.query.Query;

import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author mahesh@ensarm.com	
 *
 */
public class SearchEntryPoint implements EntryPoint,SearchEventHandler{

	private SearchListSnippet searchListSnippet;
	
	public SearchEntryPoint(){
		initialize();
	}
	
	private void initialize() {
		try{
			searchListSnippet = new SearchListSnippet();
			AppUtils.EVENT_BUS.addHandler(SearchEvent.TYPE, this);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

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
			
			
			searchListModel.setEntityListReceiver(searchListSnippet);
			basePanel.add(searchWidget);
			basePanel.add(searchListSnippet);
			
			basePanel.setCellHorizontalAlignment(searchWidget, HorizontalPanel.ALIGN_CENTER);
			basePanel.setCellHorizontalAlignment(searchListSnippet, HorizontalPanel.ALIGN_CENTER);
			searchWidget.addStyleName("fadeInLeft");
			
			/**** For Appops Showcase *****/

			String queryString = Window.Location.getQueryString();
			
			if(queryString != null  && queryString.contains("text=")) {
				searchListSnippet.getResultDisplayer().clear();
				searchListSnippet.getLoaderImage().setVisible(true);
				String searchText = queryString.substring(queryString.indexOf("text=") + "text=".length() );
				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("searchChar", searchText);
				
				searchListModel.getQueryToBind().setQueryParameterMap(paramMap);
				searchListModel.getEntityList(0);
			}
			/********************88*****/

			
			RootPanel.get().add(basePanel);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSearchEvent(SearchEvent event) {
		try{
			if(event.getEventType() == SearchEvent.SEARCHFIRED){
				searchListSnippet.getResultDisplayer().clear();
				searchListSnippet.getLoaderImage().setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
