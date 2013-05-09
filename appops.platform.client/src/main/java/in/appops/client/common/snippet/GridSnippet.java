package in.appops.client.common.snippet;

import in.appops.client.common.core.EntityListModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.core.EntitySelectionModel;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.SelectionEvent;
import in.appops.client.common.event.handlers.SelectionEventHandler;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
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
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class GridSnippet extends Composite implements Snippet, EntityListReceiver,ScrollHandler,SelectionEventHandler,ClickHandler {

	private HorizontalPanel basePanel = new HorizontalPanel();
	private FlexTable gridPanel;
	private ScrollPanel scrollPanel ;
	private EntityList entityList;
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
	private CheckBox selectAllCheckboxField ;
	private Loader loader = null;
	private LabelField noMoreResultLabel;
	private Long maxResult = 0L;
	
	public GridSnippet() {
		initWidget(basePanel);
	}

	public GridSnippet(EntityListModel entityListModel) {
		this();
		this.entityListModel = entityListModel;
		
	}

	@Override
	public void initialize(){
		loader = new Loader();
		loader.createLoader();
		loader.setVisible(true);
		basePanel.add(loader);
		basePanel.setCellHorizontalAlignment(loader, HasAlignment.ALIGN_LEFT);
		basePanel.setCellVerticalAlignment(loader, HasVerticalAlignment.ALIGN_TOP);
		
		noMoreResultLabel = new LabelField();
		Configuration labelConfig = getLabelFieldConfiguration(true, "noMoreResultlabel", null, null);
		noMoreResultLabel.setConfiguration(labelConfig);
		try {
			noMoreResultLabel.createField();
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
		
		basePanel.add(noMoreResultLabel);
		
		basePanel.setCellHorizontalAlignment(noMoreResultLabel, HasAlignment.ALIGN_CENTER);
		basePanel.setCellVerticalAlignment(noMoreResultLabel, HasVerticalAlignment.ALIGN_TOP);
		
		int height = Window.getClientHeight() - 120;
		int width = Window.getClientWidth() - 100;
		
		if(getConfiguration()!=null){
			if(getConfiguration().getPropertyByName(NOOFCOLUMNS)!=null){
				noOfCols = (Integer)getConfiguration().getPropertyByName(NOOFCOLUMNS);
			}
		}
		
		gridPanel = new FlexTable();
				
		gridPanel.setCellSpacing(10);
		gridPanel.setCellPadding(2);

		scrollPanel = new ScrollPanel(gridPanel);
		
		if (getConfiguration() != null && entityListModel instanceof EntitySelectionModel) {
			if (getConfiguration().getPropertyByName(SnippetConstant.SELECTIONMODE) != null) {
				if ((Boolean) getConfiguration().getPropertyByName(SnippetConstant.SELECTIONMODE)) {
					selectAllCheckboxField = new CheckBox("Select All");
					selectAllCheckboxField.setChecked(false);
					selectAllCheckboxField.addClickHandler(this);
					basePanel.add(selectAllCheckboxField);
					basePanel.setCellHorizontalAlignment(selectAllCheckboxField, HasAlignment.ALIGN_RIGHT);
				}
			}
		}
		
		basePanel.add(scrollPanel);
		
		scrollPanel.addScrollHandler(this);
		
		basePanel.setStylePrimaryName("gridListPanel");
		
		AppUtils.EVENT_BUS.addHandler(SelectionEvent.TYPE, this);
		
		getEntityListModel().getEntityList(entityListModel.getNoOfEntities(), this);
				
	}
	
	public Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId) {
		Configuration config = new Configuration();
		config.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		config.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return config;
	}
	
	
	@SuppressWarnings("unused")
	private void initializeGridPanel(EntityList entityList){
		
		SnippetFactory snippetFactory = getSnippetFactory();

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
	
	public SnippetFactory getSnippetFactory(){
		AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
		SnippetFactory snippetFactory = injector.getSnippetFactory();
		return snippetFactory;
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
		
		if(currentStartIndex <= maxResult){
			if(currentScrollPosition == lastScrollPosition){
			
				currentStartIndex = currentStartIndex + entityListModel.getQueryToBind().getListSize();
			
				fetchNextEntityList(currentStartIndex);
			}
		}
		
	}

	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityListReceived(EntityList entityList) {
		loader.setVisible(false);
		if (entityList != null) {
			if(entityList.isEmpty()){
				noMoreResultLabel.setText("No result(s)");
			}else{
				maxResult = entityList.getMaxResult();
				initializeGridPanel(entityList);
			}
		}
		
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
		getEntityListModel().getQueryToBind().setStartIndex(startIndex);
		getEntityListModel().getQueryToBind().setListSize(10);
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
			if(getConfiguration().getPropertyByName(SnippetConstant.SELECTIONMODE)!=null){
				if((Boolean)getConfiguration().getPropertyByName(SnippetConstant.SELECTIONMODE)){

				EntitySelectionModel entitySelectionModel = (EntitySelectionModel) entityListModel;
				
				switch (eventType) {
				case SelectionEvent.SELECTED: {
					entitySelectionModel.addSelectedEntity(entity);
					if (entitySelectionModel.getSelectedList() == entitySelectionModel.getCurrentEntityList()) {
						selectAllCheckboxField.setChecked(true);
					}
					break;
				}
				case SelectionEvent.DESELECTED: {
					entitySelectionModel.removeSelection(entity);
					boolean checked = selectAllCheckboxField.isChecked();
					if (checked)
						selectAllCheckboxField.setChecked(false);

					break;
				}

				default:
					break;
				}

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
	
	private void selectAllSnippets(boolean checked) {
		EntitySelectionModel entitySelectionModel = (EntitySelectionModel) entityListModel;
		
		if(checked){
			entitySelectionModel.selectCurrentEntityList();
			
			for(int i=0;i<currentRow ;i++){
				CardSnippet snippet = (CardSnippet) gridPanel.getWidget(i, 0);
				snippet.selectSnippet();
			}
		}else{
			entitySelectionModel.clearSelection();
			
			for(int i=0;i<currentRow ;i++){
				CardSnippet snippet = (CardSnippet) gridPanel.getWidget(i, 0);
				snippet.deSelectSnippet();
			}
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget widget = (Widget) event.getSource();
		if(widget instanceof CheckBox){
			if(widget.equals(selectAllCheckboxField)){
				CheckBox selectAllChkBox = (CheckBox) widget;
				boolean checked = selectAllChkBox.isChecked();
				selectAllSnippets(checked);
			}
		}
		
	}
	
	public void isSelectAllCheckboxVisible(boolean isVisible) {
		selectAllCheckboxField.setVisible(isVisible);
	}

}
