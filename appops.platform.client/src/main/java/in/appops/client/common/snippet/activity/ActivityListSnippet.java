/**
 * 
 */

package in.appops.client.common.snippet.activity;

import in.appops.client.common.snippet.Snippet;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com
 *
 */
public class ActivityListSnippet extends Composite implements Snippet{


	private EntityList entityList;
	private int row = 0;
	private FlexTable listFlexTable;
	private VerticalPanel basePanel;
	private ScrollPanel	scrollPanel;

	public ActivityListSnippet(){
		initailize();
		initWidget(basePanel);
	}

	private void initailize() {
		basePanel = new VerticalPanel();
		basePanel.setStylePrimaryName("fullWidth");

		setListFlexTable(new FlexTable());
		basePanel.addStyleName("activityListSnippet");
		getListFlexTable().setStylePrimaryName("fullWidth");

		setListFlexTable(new FlexTable());
		setScrollPanel(new ScrollPanel(getListFlexTable()));

		int height = Window.getClientHeight()- 200;
		int width = Window.getClientWidth() - 600;
		getScrollPanel().setHeight(height + "px");
		getScrollPanel().setWidth(width + "px");

		/*Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				getScrollPanel().setHeight((event.getHeight()-186) + "px");
				getScrollPanel().setWidth((event.getWidth()-100) + "px");
			}
		});*/

		basePanel.add(getScrollPanel());
		basePanel.setCellHorizontalAlignment(getScrollPanel(), HorizontalPanel.ALIGN_CENTER);
	}

	@Override
	public Entity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEntity(Entity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize() {
		try{
			getListFlexTable().clear();
			row = 0 ;
			//		SnippetFactory snippetFactory = injector.getSnippetFactory();

			if(!getEntityList().isEmpty()){
				for(Entity entity:getEntityList()){
					//	Snippet snippet = snippetFactory.getSnippetByEntityType(entity.getType(), null);
					ActivitySnippet snippet = new ActivitySnippet();
					snippet.setEntity(entity);
					snippet.setConfiguration(getConfiguration());
					snippet.initialize();
					getListFlexTable().setWidget(row ,0 ,snippet);
					row++;
				}
			}
			else{
				Label noResultLbl = new Label("No result(s)");
				noResultLbl.setStylePrimaryName("bolderLbl");
				getListFlexTable().setWidget(row ,0 ,noResultLbl);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionContext getActionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActionContext(ActionContext actionContext) {
		// TODO Auto-generated method stub

	}

	public EntityList getEntityList() {
		return entityList;
	}

	public void setEntityList(EntityList entityList) {
		this.entityList = entityList;
	}

	public FlexTable getListFlexTable() {
		return listFlexTable;
	}

	public void setListFlexTable(FlexTable listFlexTable) {
		this.listFlexTable = listFlexTable;
	}

	public ScrollPanel getScrollPanel() {
		return scrollPanel;
	}

	public void setScrollPanel(ScrollPanel scrollPanel) {
		this.scrollPanel = scrollPanel;
	}

	public void onUpdateEntityReceived(Entity entity) {
		try{
			if(row == 0){
				getListFlexTable().clear();
				ActivitySnippet snippet = new ActivitySnippet();
				snippet.setEntity(entity);
				snippet.setConfiguration(getConfiguration());
				snippet.initialize();
				getListFlexTable().setWidget(row ,0 ,snippet);
				row++;
			}
			else{
				ActivitySnippet snippet = new ActivitySnippet();
				snippet.setEntity(entity);
				snippet.setConfiguration(getConfiguration());
				snippet.initialize();
				getListFlexTable().setWidget(0 ,0 ,snippet);
				row++;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onNextEntityListRecevied(EntityList entityList2) {
		try{
			if(!getEntityList().isEmpty()){
				for(Entity entity:getEntityList()){
					ActivitySnippet snippet = new ActivitySnippet();
					snippet.setEntity(entity);
					snippet.setConfiguration(getConfiguration());
					snippet.initialize();
					getListFlexTable().setWidget(row ,0 ,snippet);
					row++;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
