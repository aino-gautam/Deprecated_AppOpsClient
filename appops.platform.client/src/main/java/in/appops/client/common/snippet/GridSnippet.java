package in.appops.client.common.snippet;

import in.appops.client.common.core.EntityListModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.core.EntitySelectionModel;
import in.appops.client.common.event.SelectionEvent;
import in.appops.client.common.event.handlers.SelectionEventHandler;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class GridSnippet extends Composite implements Snippet, EntityListReceiver,ScrollHandler,SelectionEventHandler {

	private HorizontalPanel basePanel = new HorizontalPanel();
	private FlexTable gridPanel;
	private ScrollPanel scrollPanel ;
	private EntityList entityList;
	private final AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
	private int noOfRows = 0;
	private int noOfCols = 3;
	private EntityListModel entityListModel;
	private int currentScrollPosition=0;
	private int lastScrollPosition;
	private int currentRow = 0;
	private int currentStartIndex = 0;
	private Entity entity;
	private String type;
	private Configuration configuration;
	private static final String  NOOFCOLUMNS = "noOfColumns";
	
	
	public GridSnippet() {
		initWidget(basePanel);
	}

	public GridSnippet(EntityListModel entityListModel) {
		this();
		this.entityListModel = entityListModel;
		
	}

	@Override
	public void initialize(){
		int height = Window.getClientHeight() - 120;
		int width = Window.getClientWidth() - 100;
		
		if(getConfiguration()!=null){
			if(getConfiguration().getPropertyByName(NOOFCOLUMNS)!=null){
				noOfCols = (Integer)getConfiguration().getPropertyByName(NOOFCOLUMNS);
			}
		}
		
		gridPanel = new FlexTable();
		
		gridPanel.setSize("100%", "100%");

		gridPanel.setCellSpacing(10);
		gridPanel.setCellPadding(2);

		scrollPanel = new ScrollPanel(gridPanel);
		
		basePanel.add(scrollPanel);
		
		scrollPanel.addScrollHandler(this);
		
		basePanel.setStylePrimaryName("serviceListPanel");
		
		getEntityListModel().getEntityList(entityListModel.getNoOfEntities(), this);
				
	}
	
	@SuppressWarnings("unused")
	private void initializeGridPanel(EntityList entityList){
		
		SnippetFactory snippetFactory = injector.getSnippetFactory();

		noOfRows = (entityList.size() / noOfCols) + 1;
		
		int index = 0;
		for (int row = 0; row < noOfRows; row++) {
			for (int col = 0; col < noOfCols; col++) {
				if (index < entityList.size()) {
					Entity entity = entityList.get(index);
					Snippet snippet = snippetFactory.getSnippetByEntityType(entity.getType(), null);
					snippet.setEntity(entity);
					snippet.setConfiguration(getConfiguration());
					snippet.initialize();
					gridPanel.setWidget(row, col, snippet);
					index++;
				} else {
					break;
				}
			}
			currentRow++;
		}
	}

	public int getNoOfRows() {
		return noOfRows;
	}

	public void setNoOfRows(int noOfRows) {
		this.noOfRows = noOfRows;
	}
	

	public EntityList getEntityList() {
		return entityList;
	}

	public void setEntityList(EntityList entityList) {
		this.entityList = entityList;
	}

	@Override
	public void onScroll(ScrollEvent event) {
		ScrollPanel scroll = (ScrollPanel) event.getSource();
		currentScrollPosition=scrollPanel.getVerticalScrollPosition();
		lastScrollPosition = scrollPanel.getMaximumVerticalScrollPosition();
		
		currentStartIndex = currentStartIndex + entityListModel.getListSize();
		
		if(currentScrollPosition == lastScrollPosition){
			fetchNextEntityList(currentStartIndex);
		}
		
	}

	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityListReceived(EntityList entityList) {
		initializeGridPanel(entityList);
		
	}

	@Override
	public void onEntityListUpdated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCurrentView(Entity entity) {
		// TODO Auto-generated method stub
		
	}
	
	public void fetchNextEntityList(int startIndex){
		getEntityListModel().setStartIndex(startIndex);
		getEntityListModel().getEntityList(getEntityListModel().getNoOfEntities(), this);
		
		calculateAndUpdateScrollPosition();
	}
	
	public void calculateAndUpdateScrollPosition(){
		
		int twentyPercentScrollPosition=(lastScrollPosition*5)/100;
		int scrollPosition = lastScrollPosition-twentyPercentScrollPosition;
		scrollPanel.setVerticalScrollPosition(scrollPosition);
	}

	public EntityListModel getEntityListModel() {
		return entityListModel;
	}

	public void setEntityListModel(EntityListModel entityListModel) {
		this.entityListModel = entityListModel;
	}
	
	@Override
	public void onSelection(SelectionEvent event) {
		Entity entity = (Entity) event.getEventData();
		int eventType = event.getEventType();
		
		if (getConfiguration() != null) {

			if ((Boolean) getConfiguration().getPropertyByName(SnippetConstant.SELECTIONMODE)) {

				EntitySelectionModel entitySelectionModel = (EntitySelectionModel) entityListModel;
				/*
				 * if(event.getEventType() == SelectionEvent.SELECTED){
				 * entitySelectionModel.addSelectedEntity(entity); }else
				 * if(event.getEventType() == SelectionEvent.DESELECTED){
				 * entitySelectionModel.removeSelection(entity); }
				 */

				switch (eventType) {
				case SelectionEvent.SELECTED: {
					entitySelectionModel.addSelectedEntity(entity);
					break;
				}
				case SelectionEvent.DESELECTED: {
					entitySelectionModel.removeSelection(entity);
					break;
				}

				default:
					break;
				}

			}
		}
		
	}
	
	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
		
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
		
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
		
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
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

}
