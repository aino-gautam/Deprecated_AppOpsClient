/**
 * 
 */

package in.appops.client.common.snippet.activity;

import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.core.EntityReceiver;
import in.appops.client.common.snippet.SnippetConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.GeoLocation;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.search.constant.OperationParameterConstant;
import in.appops.platform.server.core.services.search.constant.SearchConstant;

import java.util.HashMap;

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com
 * 
 */
public class ActivityComponent extends Composite implements EntityListReceiver,EntityReceiver{

	private VerticalPanel baseVp;
	private ActivityModel activityModel;
	private FlexTable headerContainerFlex;
	private VerticalPanel resultContainer;
	private ActivityListSnippet activityListSnippet;
	private Image loaderImage;
	private Long maxResult = 0L;
	private int startIndex =0;
	private int pageSize = 10;

	public ActivityComponent(ActivityModel model){
		activityModel = model;
		initialize();
		initWidget(baseVp);
	}

	private void initialize() {
		try {
			baseVp = new VerticalPanel();
			baseVp.setStylePrimaryName("fullWidth");

			headerContainerFlex = new FlexTable();
			headerContainerFlex.setStylePrimaryName("fullWidth");

			resultContainer = new VerticalPanel();
			resultContainer.setStylePrimaryName("fullWidth");
			resultContainer.addStyleName("activityResultDisplayer");

			loaderImage = new Image("images/opptinLoader.gif");
			loaderImage.setVisible(false);

			activityModel.setEntityListReceiver(this);

			//headerContainerFlex.setWidth("350px");

	/*		Window.addResizeHandler(new ResizeHandler() {

				@Override
				public void onResize(ResizeEvent event) {
					//	baseVp.setHeight(event.getHeight() + "px");
					baseVp.setWidth((event.getWidth()-50) + "px");
					headerContainerFlex.setWidth((Window.getClientWidth()-100) + "px");
				}
			});*/

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createUi(){
		try {
			baseVp.clear();
			resultContainer.clear();
			baseVp.add(headerContainerFlex);

			baseVp.add(loaderImage);
			baseVp.setCellHorizontalAlignment(loaderImage,HorizontalPanel.ALIGN_CENTER);
			loaderImage.setVisible(true);

			startIndex = 0;
			HashMap<String, Object> parMap = new HashMap<String, Object>();
			parMap.put(OperationParameterConstant.STARTINDEX, startIndex);
			parMap.put(OperationParameterConstant.LISTSIZE, pageSize);
			in.appops.platform.core.entity.query.Query appopsQuery = new in.appops.platform.core.entity.query.Query();
			appopsQuery.setQueryName("getAllActivity");
			appopsQuery.setQueryParameterMap(parMap);
			activityModel.setQuery(appopsQuery);
			String operationName = "search.SearchService.getSearchList";
			activityModel.setOperationName(operationName);
			activityListSnippet = new ActivityListSnippet();
			activityListSnippet.getScrollPanel().addScrollHandler(new ScrollHandler() {

				@Override
				public void onScroll(ScrollEvent event) {
					ScrollPanel scrollPanel = (ScrollPanel) event.getSource();
					int scrollPosition = scrollPanel.getVerticalScrollPosition();
					int lastScrollPosition = scrollPanel.getMaximumVerticalScrollPosition();

					if(startIndex <= maxResult){
						if(scrollPosition == lastScrollPosition){
							startIndex = startIndex + pageSize;
							activityModel.getNextEntityList(startIndex);
						}
					}
				}
			});

			Configuration configuration = new Configuration();
			configuration.setPropertyByName(SnippetConstant.SELECTIONMODE, true);
			activityListSnippet.setConfiguration(configuration);
			activityModel.getEntityList(pageSize, this);

			resultContainer.add(activityListSnippet);
			baseVp.add(resultContainer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEntityListReceived(EntityList entityList) {
		try{
			if(entityList !=null){
				maxResult = entityList.getMaxResult();
				activityListSnippet.setEntityList(entityList);
				activityListSnippet.initialize();
				loaderImage.setVisible(false);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onEntityListUpdated() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCurrentView(Entity entity) {
		try{
			activityListSnippet.onUpdateEntityReceived(entity);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void getNearbyActivity(GeoLocation geolocation) {
		try{
			if(geolocation != null){
				HashMap<String, Object> parMap = new HashMap<String, Object>();

				parMap.put(SearchConstant.LATITUDE, geolocation.getLatitude());
				parMap.put(SearchConstant.LONGITUDE, geolocation.getLongitude());
				parMap.put(SearchConstant.RANGE, 100.0);

				activityModel.setOperationNameToBind("search.SearchService.getSearchList");

				Query appopsQuery = new Query();
				appopsQuery.setQueryName("getAllNearByActivity");

				appopsQuery.setQueryParameterMap(parMap);

				activityModel.setQuery(appopsQuery);
				activityModel.getEntityList(pageSize);

			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onEntityReceived(Entity entity) {
	}

	@Override
	public void onEntityUpdated(Entity entity) {
		// TODO Auto-generated method stub

	}

	public void onUpdatedEntity(EntityList entityList) {
		try{
			if(entityList != null && !entityList.isEmpty()){
				maxResult = entityList.getMaxResult();
				activityListSnippet.setEntityList(entityList);
				activityListSnippet.initialize();
				//activityListSnippet.onNextEntityListRecevied(entityList);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
