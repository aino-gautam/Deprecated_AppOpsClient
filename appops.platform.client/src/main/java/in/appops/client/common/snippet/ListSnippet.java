package in.appops.client.common.snippet;

import in.appops.client.common.core.EntityListModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.core.EntitySelectionModel;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.SelectionEvent;
import in.appops.client.common.event.handlers.SelectionEventHandler;
import in.appops.client.common.fields.CheckboxField;
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
	private CheckBox selectAllCheckboxField ;
	public static final String SNIPPETTYPE = "snippetType";
	public static final String LISTPANELCSS = "listPanelCss";
	public static final String SCROLLPANELWIDTH = "scrollPanelWidth";
	public static final String SCROLLPANELHEIGHT = "scrollPanelHeight";
	public static final String SCROLLPANELCSS = "scrollPanelCss";
	private Loader loader = null;
	private LabelField noMoreResultLabel;
	private Long maxResult = 0L;
	
	public ListSnippet() {
		initWidget(basepanel);
	}

	public ListSnippet(EntityListModel entityListModel) {
		this();
		this.entityListModel = entityListModel;
		
	}

	@Override
	public void initialize(){
		basepanel.clear();
		loader = new Loader();
		loader.createLoader();
		loader.setVisible(true);
		basepanel.add(loader);
		basepanel.setCellHorizontalAlignment(loader, HasAlignment.ALIGN_CENTER);
		basepanel.setCellVerticalAlignment(loader, HasVerticalAlignment.ALIGN_TOP);
		
		listPanel = new FlexTable();
		scrollPanel = new ScrollPanel(listPanel);
		
		if (getConfiguration() != null && entityListModel instanceof EntitySelectionModel) {
			if (getConfiguration().getPropertyByName(SnippetConstant.SELECTIONMODE) != null) {
				if ((Boolean) getConfiguration().getPropertyByName(SnippetConstant.SELECTIONMODE)) {
					selectAllCheckboxField = new CheckBox("Select All");
					selectAllCheckboxField.setChecked(false);
					selectAllCheckboxField.addClickHandler(this);
					basepanel.add(selectAllCheckboxField);
					basepanel.setCellHorizontalAlignment(selectAllCheckboxField, HasAlignment.ALIGN_RIGHT);
				}
			}
		}
		
		if(getConfiguration() != null){
			String listPanelCss = getConfiguration().getPropertyByName(LISTPANELCSS);
			String scrollPanelCss = getConfiguration().getPropertyByName(SCROLLPANELCSS);
			
			if(listPanelCss != null) {
				listPanel.setStylePrimaryName(listPanelCss);
				setStylePrimaryName(listPanelCss);
			}

			if(scrollPanelCss != null) {
				scrollPanel.setStylePrimaryName(scrollPanelCss);
			}
			
			if(getConfiguration().getPropertyByName(SCROLLPANELWIDTH) != null)
				scrollPanel.setWidth(getConfiguration().getPropertyByName(SCROLLPANELWIDTH) + "px");
			else{
				/*int width = Window.getClientWidth() - 100;
				scrollPanel.setWidth(width + "px");*/
			}
			
			if(getConfiguration().getPropertyByName(SCROLLPANELHEIGHT) != null)
				scrollPanel.setHeight(getConfiguration().getPropertyByName(SCROLLPANELHEIGHT) + "px");
			else{
				/*int height = Window.getClientHeight() - 120;
				scrollPanel.setHeight(height + "px");*/
			}
			
		}else{
			listPanel.setStylePrimaryName("listComponentPanel");
			setStylePrimaryName("listComponentPanel");
			int height = Window.getClientHeight() - 120;
			int width = Window.getClientWidth() - 100;
			scrollPanel.setHeight(height + "px");
			scrollPanel.setWidth(width + "px");
		}
		
		basepanel.add(scrollPanel);
		basepanel.setCellHorizontalAlignment(scrollPanel, HasAlignment.ALIGN_CENTER);
		scrollPanel.addScrollHandler(this);
		
		noMoreResultLabel = new LabelField();
		Configuration labelConfig = getLabelFieldConfiguration(true, "noMoreResultlabel", null, null);
		noMoreResultLabel.setConfiguration(labelConfig);
		try {
			noMoreResultLabel.createField();
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
		
		basepanel.add(noMoreResultLabel);
		
		basepanel.setCellHorizontalAlignment(noMoreResultLabel, HasAlignment.ALIGN_CENTER);
		basepanel.setCellVerticalAlignment(noMoreResultLabel, HasVerticalAlignment.ALIGN_BOTTOM);
		
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
		
		getEntityListModel().getQueryToBind().setStartIndex(startIndex);
		getEntityListModel().getQueryToBind().setListSize(10);
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
		loader.setVisible(false);
		if (entityList != null) {
			if (entityList.isEmpty()){
				noMoreResultLabel.setText("No result(s)");
			}else{
				if(maxResult==0) {
					if(entityList.getMaxResult() != null) {
						maxResult = entityList.getMaxResult();
					}
				}
				initializeListPanel(entityList);
			}
		}
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
		snippet.setConfiguration(getConfiguration());
		snippet.initialize();
		listPanel.setWidget(0, 0,snippet);
		if(selectAllCheckboxField.isChecked()){
			selectAllCheckboxField.setChecked(false);
		}
	}
	
	public void addToIndex(int row, int col, Entity entity){
		SnippetFactory snippetFactory = injector.getSnippetFactory();
		Snippet snippet = snippetFactory.getSnippetByEntityType(entity.getType(), null);
		snippet.setEntity(entity);
		snippet.setConfiguration(getConfiguration());
		snippet.initialize();
		listPanel.setWidget(row, col,snippet);
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
		int eventType = event.getEventType();
		
		if(event.getEventData() instanceof Entity) {
			Entity entity = (Entity) event.getEventData();
			if (getConfiguration() != null) {
				if (getConfiguration().getPropertyByName(SnippetConstant.SELECTIONMODE) != null) {
					if ((Boolean) getConfiguration().getPropertyByName(SnippetConstant.SELECTIONMODE)) {

						EntitySelectionModel entitySelectionModel = (EntitySelectionModel) entityListModel;

						switch (eventType) {
						case SelectionEvent.SELECTED: {
							entitySelectionModel.addSelectedEntity(entity);
							if (entitySelectionModel.getSelectedList().size() == entitySelectionModel.getCurrentEntityList().size()) {
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
				selectAllSnippets(checked);
			}
		}
		
		
	}

	private void selectAllSnippets(boolean checked) {
		if(checked){
			
			EntitySelectionModel entitySelectionModel = (EntitySelectionModel) entityListModel;
			entitySelectionModel.selectCurrentEntityList();
			
			for(int i=0;i<row ;i++){
				RowSnippet snippet = (RowSnippet) listPanel.getWidget(i, 0);
				snippet.selectSnippet();
			}
		}else{
			EntitySelectionModel entitySelectionModel = (EntitySelectionModel) entityListModel;
			entitySelectionModel.clearSelection();
			
			for(int i=0;i<row ;i++){
				RowSnippet snippet = (RowSnippet) listPanel.getWidget(i, 0);
				snippet.deSelectSnippet();
			}
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
