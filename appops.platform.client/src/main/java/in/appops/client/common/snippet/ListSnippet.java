package in.appops.client.common.snippet;

import in.appops.client.common.core.EntityListModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.core.EntitySelectionModel;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.SelectionEvent;
import in.appops.client.common.event.handlers.SelectionEventHandler;
import in.appops.client.common.fields.CheckboxField;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author pallavi@ensarm.com
 */

public class ListSnippet extends Composite implements Snippet, EntityListReceiver,ScrollHandler,SelectionEventHandler,ClickHandler{

	private VerticalPanel basepanel = new VerticalPanel();
	private ScrollPanel scrollPanel ;
	private FlexTable listPanel;
	private Configuration configuration;
	private EntityList entityList;
	private final AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
	private EntityListModel entityListModel;
	private int currentScrollPosition=0;
	private int lastScrollPosition;
	private int currentStartIndex = 0;
	private int row = 0;
	private Entity entity;
	private String type;
	
	//private CheckboxField selectAllCheckboxField ;
	
	private CheckBox selectAllCheckboxField ;
	
	public static final String SNIPPETTYPE = "snippetType";
	//public static final String SELECTIONMODE = "selectionMode";

	public ListSnippet() {
		initWidget(basepanel);
	}

	public ListSnippet(EntityListModel entityListModel) {
		this();
		this.entityListModel = entityListModel;
		
	}

	@Override
	public void initialize(){
		
		selectAllCheckboxField = new CheckBox("Select All");
		selectAllCheckboxField.setChecked(true);
		selectAllCheckboxField.addClickHandler(this);
		/*Configuration config = getCheckboxFieldConfiguration("Select All");
		selectAllCheckboxField.setFieldValue("false");
		selectAllCheckboxField.setConfiguration(config);*/
		
		listPanel = new FlexTable();
		scrollPanel = new ScrollPanel(listPanel);
		
		int height = Window.getClientHeight() - 120;
		int width = Window.getClientWidth() - 100;
		scrollPanel.setHeight(height + "px");
		scrollPanel.setWidth(width + "px");
		
		basepanel.add(scrollPanel);
		
		listPanel.setStylePrimaryName("listComponentPanel");
		
		basepanel.setCellHorizontalAlignment(scrollPanel, HasAlignment.ALIGN_CENTER);
		
		setStylePrimaryName("listComponentPanel");
		
		scrollPanel.addScrollHandler(this);
		
		AppUtils.EVENT_BUS.addHandler(SelectionEvent.TYPE, this);
		
		getEntityListModel().getEntityList(entityListModel.getNoOfEntities(), this);
		
	}
	
	@SuppressWarnings("unused")
	private void initializeListPanel(EntityList entityList){
		
		SnippetFactory snippetFactory = injector.getSnippetFactory();
		
		for(Entity entity:entityList){
			Snippet snippet = snippetFactory.getSnippetByEntityType(entity.getType(), null);
			snippet.setEntity(entity);
			snippet.setConfiguration(getConfiguration());
			snippet.initialize();
			listPanel.setWidget(row ,0 ,snippet);
			row++;
		}
	}
	
	public void fetchNextEntityList(int startIndex){
		
		getEntityListModel().setStartIndex(startIndex);
		
		getEntityListModel().getEntityList(getEntityListModel().getNoOfEntities(), this);
		
		
		
		calculateAndUpdateScrollPosition();
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
	public void noMoreData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityListReceived(EntityList entityList) {
		initializeListPanel(entityList);
	}


	@Override
	public void onEntityListUpdated() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateCurrentView(Entity entity) {
		addToTop(entity);
		
	}
	
	public EntityList getEntityList() {
		return entityList;
	}


	public void setEntityList(EntityList entityList) {
		this.entityList = entityList;
	}

	
	public void addToTop(Entity entity){
		listPanel.insertRow(0);
		
		SnippetFactory snippetFactory = injector.getSnippetFactory();
		Snippet snippet = snippetFactory.getSnippetByEntityType(entity.getType(), null);
		snippet.setEntity(entity);
		snippet.initialize();
		listPanel.setWidget(0, 0,snippet);
	}
	
	public void addToIndex(int row, int col, Entity entity){
		SnippetFactory snippetFactory = injector.getSnippetFactory();
		Snippet snippet = snippetFactory.getSnippetByEntityType(entity.getType(), null);
		snippet.setEntity(entity);
		snippet.initialize();
		listPanel.setWidget(row, col,snippet);
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
	
	public void calculateAndUpdateScrollPosition(){
		
		int twentyPercentScrollPosition=(lastScrollPosition*5)/100;
		int scrollPosition = lastScrollPosition-twentyPercentScrollPosition;
		scrollPanel.setVerticalScrollPosition(scrollPosition);
		System.out.println(scrollPanel.getVerticalScrollPosition());
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
		
		if((Boolean)getConfiguration().getPropertyByName(SnippetConstant.SELECTIONMODE)){
			
			EntitySelectionModel entitySelectionModel = (EntitySelectionModel) entityListModel;
			/*if(event.getEventType() == SelectionEvent.SELECTED){
				entitySelectionModel.addSelectedEntity(entity);
			}else if(event.getEventType() == SelectionEvent.DESELECTED){
				entitySelectionModel.removeSelection(entity);
			}*/
			
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
	
	public Configuration getCheckboxFieldConfiguration(String text) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(CheckboxField.CHECKBOXFIELD_DISPLAYTEXT, text);
		return configuration;
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget widget = (Widget) event.getSource();
		if(widget instanceof CheckBox){
			if(widget.equals(selectAllCheckboxField)){
				CheckBox selectAllChkBox = (CheckBox) widget;
				boolean checked = selectAllChkBox.isChecked();
				selectList(checked);
			}
		}
		
		
	}

	private void selectList(boolean checked) {
		if(checked){
			
			EntitySelectionModel entitySelectionModel = (EntitySelectionModel) entityListModel;
			entitySelectionModel.selectCurrentEntityList();
			
			for(int i=0;i<row ;i++){
				Snippet snippet = (Snippet) listPanel.getWidget(i, 0);
			}
		}else{
			
		}
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
