package in.appops.client.gwt.web.ui.search;

import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.client.common.snippet.Snippet;
import in.appops.client.common.snippet.SnippetFactory;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author milind@ensarm.com
 * @modifiedBy mahesh@ensarm.com
 */
public class SearchListSnippet extends Composite implements  Snippet, Configurable, EntityListReceiver{

	private VerticalPanel mainPanel = new VerticalPanel();
	private ScrollPanel scrollPanel ;
	private VerticalPanel listPanel;
	private Configuration configuration;
	private EntityList entityList;
	private final AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
	private Image loaderImage;
	private VerticalPanel resultDisplayer = new VerticalPanel();

	public SearchListSnippet() {
		initWidget(mainPanel);
		setLoaderImage(new Image("images/opptinLoader.gif"));
		getLoaderImage().setStylePrimaryName("appops-intelliThoughtActionImage");
		getLoaderImage().setVisible(false);
		
		mainPanel.add(getLoaderImage());
		mainPanel.add(getResultDisplayer());
		mainPanel.setCellHorizontalAlignment(getLoaderImage(),HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellVerticalAlignment(getLoaderImage(),HasVerticalAlignment.ALIGN_MIDDLE);
		
		mainPanel.setCellHorizontalAlignment(getResultDisplayer(),HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellVerticalAlignment(getResultDisplayer() ,HasVerticalAlignment.ALIGN_MIDDLE);
	}
	
	public EntityList getEntityList() {
		return entityList;
	}

	public void setEntityList(EntityList entityList) {
		this.entityList = entityList;
	}

	@Override
	public void initialize(){
		getResultDisplayer().clear();
		try {
			if(entityList!=null && !entityList.isEmpty()){
				VerticalPanel headerPanel = new VerticalPanel();
				headerPanel.setSpacing(10);
				Label searchHeaderLbl = new Label("Search results:");
				searchHeaderLbl.setStylePrimaryName("searchResultLbl");
				headerPanel.add(searchHeaderLbl);
				headerPanel.setCellHorizontalAlignment(searchHeaderLbl, HasHorizontalAlignment.ALIGN_RIGHT);

				getResultDisplayer().add(headerPanel);
				getResultDisplayer().setCellHorizontalAlignment(headerPanel, HasHorizontalAlignment.ALIGN_LEFT);
				
				SnippetFactory snippetFactory = injector.getSnippetFactory();

				listPanel = new VerticalPanel();
				scrollPanel = new ScrollPanel(listPanel);

				int height = Window.getClientHeight() - 120;
				int width = Window.getClientWidth() - 100;
				scrollPanel.setHeight(height + "px");
				scrollPanel.setWidth(width + "px");

				listPanel.setSpacing(5);
				listPanel.setStylePrimaryName("listComponentPanel");
				for(Entity entity :entityList){
					Snippet snippet = snippetFactory.getSnippetByEntityType(entity.getType(), null);
					snippet.setEntity(entity);
					snippet.initialize();
					listPanel.add(snippet);
				}
				getResultDisplayer().add(scrollPanel);
				getResultDisplayer().setCellHorizontalAlignment(scrollPanel, HasAlignment.ALIGN_CENTER);
				getResultDisplayer().setStylePrimaryName("listComponentPanel");
			}else{
				Label noResultLbl = new Label("No Search results....!!!");
				noResultLbl.setStylePrimaryName("searchResultLbl");
				getResultDisplayer().add(noResultLbl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	@Override
	public void noMoreData() {

	}

	@Override
	public void onEntityListReceived(EntityList entityList) {
		loaderImage.setVisible(false);
		getResultDisplayer().clear();
		this.entityList = entityList;
		initialize();
	}

	@Override
	public void onEntityListUpdated() {

	}

	@Override
	public void updateCurrentView(Entity entity) {

	}

	@Override
	public Widget asWidget() {
		return null;
	}

	@Override
	public Entity getEntity() {
		return null;
	}

	@Override
	public void setEntity(Entity entity) {
		
	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public void setType(String type) {
		
	}

	@Override
	public ActionContext getActionContext() {
		return null;
	}

	@Override
	public void setActionContext(ActionContext actionContext) {
		
	}

	public Image getLoaderImage() {
		return loaderImage;
	}

	public void setLoaderImage(Image loaderImage) {
		this.loaderImage = loaderImage;
	}

	public VerticalPanel getResultDisplayer() {
		return resultDisplayer;
	}

	public void setResultDisplayer(VerticalPanel resultDisplayer) {
		this.resultDisplayer = resultDisplayer;
	}
}