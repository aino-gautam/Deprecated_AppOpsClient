/**
 * 
 */

package in.appops.client.common.snippet.activity;

import in.appops.client.common.config.component.Notifier;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.core.EntityReceiver;
import in.appops.client.common.snippet.SnippetConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.GeoLocation;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.search.constant.SearchConstant;

import java.util.HashMap;

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com
 * @modifiedBy pallavi@ensarm.com
 * 
 */
public class ActivityComponent extends Composite implements EntityListReceiver,EntityReceiver{

	private VerticalPanel baseVp;
	private ActivityModel activityModel;
	private FlexTable headerContainerFlex;
	private VerticalPanel resultContainer;
	private ActivityListSnippet activityListSnippet;
	private Notifier notifier;
	private Long maxResult = 0L;
	private int startIndex =0;
	private int pageSize = 10;
	private Configuration configuration = null;
	
	public static String DEFAULT_NOTIFICATION_IMG = "images/defaultLoader.gif";
	public static String DEFAULT_NOTIFICATION_IMGCSS = "defaultNotificationIcon";


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
	
	private Configuration getNotifierConfig(String imageUrl,String imageCss,String basePanelcss) {
		Configuration configuration = null;
		try {
			configuration = new Configuration();
			configuration.setPropertyByName(ActivityComponentConstant.NOTIFICATION_IMAGE_URL, imageUrl);
			configuration.setPropertyByName(ActivityComponentConstant.NOTIFICATION_IMG_CSS, imageCss);
			configuration.setPropertyByName(ActivityComponentConstant.NOTIFIER_BASEPANEL_CSS, basePanelcss);
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}
	

	public void createUi(){
		try {
			baseVp.clear();
			resultContainer.clear();
			baseVp.add(headerContainerFlex);

			notifier = new Notifier(getNotifierConfig(getNotificationImageUrl(),getNotificationImageCss(),getNotifierBasePanelCss()));
			notifier.createDefaultNotifier();
			notifier.setVisible(false);
			baseVp.add(notifier);
			baseVp.setCellHorizontalAlignment(notifier,HorizontalPanel.ALIGN_CENTER);
			notifier.setVisible(true);
			
			/*HashMap<String, Object> parMap = new HashMap<String, Object>();
			parMap.put(OperationParameterConstant.STARTINDEX, startIndex);
			parMap.put(OperationParameterConstant.LISTSIZE, pageSize);
			in.appops.platform.core.entity.query.Query appopsQuery = new in.appops.platform.core.entity.query.Query();
			appopsQuery.setQueryName("getAllActivity");
			appopsQuery.setQueryParameterMap(parMap);
			activityModel.setQuery(appopsQuery);
			String operationName = "search.SearchService.getSearchList";
			activityModel.setOperationName(operationName);*/
			
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
				notifier.setVisible(false);
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
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public String getNotificationImageUrl() {
		String imageUrl = DEFAULT_NOTIFICATION_IMG;
		try {
			if(hasConfiguration(ActivityComponentConstant.NOTIFICATION_IMAGE_URL)) {
				imageUrl =  configuration.getPropertyByName(ActivityComponentConstant.NOTIFICATION_IMAGE_URL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageUrl;
	}
	
	public String getNotificationImageCss() {
		String imageCss = DEFAULT_NOTIFICATION_IMGCSS;
		try {
			if(hasConfiguration(ActivityComponentConstant.NOTIFICATION_IMG_CSS)) {
				imageCss =  configuration.getPropertyByName(ActivityComponentConstant.NOTIFICATION_IMG_CSS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageCss;
	}
	
	public String getNotifierBasePanelCss() {
		String css = null;
		try {
			if(hasConfiguration(ActivityComponentConstant.NOTIFIER_BASEPANEL_CSS)) {
				css =  configuration.getPropertyByName(ActivityComponentConstant.NOTIFIER_BASEPANEL_CSS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return css;
	}
	
	/**
	 * Returns true if the configuration is provided.
	 * @param configKey - The configuration to check
	 * @return
	 */
	protected boolean hasConfiguration(String configKey) {
		try {
			if(configuration != null && configuration.getPropertyByName(configKey) != null) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;
	}
	
	public interface ActivityComponentConstant {
		
		public static final String NOTIFICATION_IMAGE_URL = "imageUrl";
		
		public static final String NOTIFICATION_IMG_CSS = "imageCss";
		
		public static final String NOTIFIER_BASEPANEL_CSS = "notifierBasepanelCss";
		
	}
	
}
