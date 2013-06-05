package in.appops.showcase.web.gwt.snippets.client;

import in.appops.client.common.core.EntitySelectionModel;
import in.appops.client.common.snippet.BoxSnippet;
import in.appops.client.common.snippet.GridSnippet;
import in.appops.client.common.snippet.IconSnippet;
import in.appops.client.common.snippet.ListSnippet;
import in.appops.client.common.snippet.SnippetConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.media.constant.MediaConstant;
import in.appops.platform.server.core.services.spacemanagement.constants.SpaceConstants;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SnippetShowCase implements EntryPoint,ChangeHandler {

	private ListBox listBox;
	private VerticalPanel basePanel;
	private VerticalPanel innerPanel;
	public static final String GRIDSNIPPET = "Grid Snippet";
	public static final String LISTSNIPPET = "List Snippet";
	public static final String ICONSNIPPET = "Icon Snippet";
	public static final String BOXSNIPPET = "Box Snippet";
	public static final String ACTIONBOXSNIPPET = "ActionBox Snippet";
	
	@Override
	public void onModuleLoad() {
		
		HorizontalPanel listBoxPanel = new HorizontalPanel();
		Label titleLabel = new Label("Select a snippet");
		
		listBox = new ListBox();
		listBox.addItem("--Select--");
		listBox.addItem(GRIDSNIPPET);
		listBox.addItem(LISTSNIPPET);
		listBox.addItem(ICONSNIPPET);
		listBox.addItem(BOXSNIPPET);
		//listBox.addItem(ACTIONBOXSNIPPET);
		
		listBox.addChangeHandler(this);
		
		listBoxPanel.add(titleLabel);
		listBoxPanel.add(listBox);
		
		listBoxPanel.setSpacing(7);
		
		innerPanel = new VerticalPanel();
		innerPanel.setWidth("100%");
		
		basePanel = new VerticalPanel();
		basePanel.add(listBoxPanel);
		basePanel.add(innerPanel);
		basePanel.setCellHorizontalAlignment(innerPanel, HasAlignment.ALIGN_CENTER);
		
		basePanel.setSpacing(20);
		
		RootPanel.get().add(basePanel);
	}

	@Override
	public void onChange(ChangeEvent event) {
		int index = listBox.getSelectedIndex();
		String snippetName = listBox.getValue(index);
		initializeSnippet(snippetName);
		
	}

	private void initializeSnippet(String snippetName) {
		innerPanel.clear();
		
		if (snippetName.equals(LISTSNIPPET)) {
			showListSnippet();
			
		}else if(snippetName.equals(GRIDSNIPPET)){
			showGridSnippet();
		}else if(snippetName.equals(ICONSNIPPET)){
			IconSnippet iconSnippet = new IconSnippet();
			Entity entity = new Entity();
			entity.setPropertyByName(MediaConstant.BLOBID, "irqSN52SzHwHksn9NQFKxA6zR2xKgDVgQLkP5WGJXH4%3D");
			iconSnippet.setEntity(entity);
			iconSnippet.initialize();
			innerPanel.add(iconSnippet);
		}else if(snippetName.equals(BOXSNIPPET)){
			BoxSnippet boxSnippet = new BoxSnippet();
			Entity entity = new Entity();
			entity.setPropertyByName(SpaceConstants.BANNERBLOBID, "irqSN52SzHwHksn9NQFKxMREz%2BgpaiNmSv6XfvrUtgWvxK4Kvah8xgcdwUpjkpQ9");
			entity.setPropertyByName(SpaceConstants.NAME, "Ensarm");
			boxSnippet.setEntity(entity);
			boxSnippet.initialize();
			innerPanel.add(boxSnippet);
		}

	}
	
	private void showGridSnippet() {
		
		CheckBox selectionCheckBox = new CheckBox("Allow Selection ");

		Query query = new Query();
		query.setQueryName("getAllServices");
		query.setListSize(9);
					
		final EntitySelectionModel entitySelectionModel = new EntitySelectionModel();
		entitySelectionModel.setQueryToBind(query);
		entitySelectionModel.setNoOfEntities(10);
		entitySelectionModel.setOperationNameToBind("spacemanagement.SpaceManagementService.getServicesForSpace");
		
		final GridSnippet gridSnippet = new GridSnippet();
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(SnippetConstant.SELECTIONMODE, false);
		
		gridSnippet.setEntityListModel(entitySelectionModel);
		gridSnippet.setConfiguration(configuration);
		gridSnippet.initialize();
		
		final VerticalPanel gridSnippetPanel = new VerticalPanel();
		gridSnippetPanel.add(gridSnippet);
		
		innerPanel.add(selectionCheckBox);
		innerPanel.add(gridSnippetPanel);
		
		innerPanel.setSpacing(5);
		
		
		selectionCheckBox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				CheckBox widget = (CheckBox) event.getSource();
				boolean isChecked = widget.isChecked();
				Configuration configuration = new Configuration();
				configuration.setPropertyByName(SnippetConstant.SELECTIONMODE, isChecked);
				GridSnippet gridSnippet = new GridSnippet(entitySelectionModel);
				entitySelectionModel.getQueryToBind().setStartIndex(0);
				gridSnippet.setConfiguration(configuration);
				gridSnippet.initialize();
				gridSnippetPanel.clear();
				gridSnippetPanel.add(gridSnippet);
				gridSnippetPanel.setStylePrimaryName("serviceListPanel");
			}
		});
		
	}

	private void showListSnippet(){
		CheckBox selectionCheckBox = new CheckBox("Allow Selection ");

		Query query = new Query();
		query.setQueryName("getContactList");
		query.setListSize(10);
		
		final EntitySelectionModel entitySelectionModel = new EntitySelectionModel();
		entitySelectionModel.setQueryToBind(query);
		entitySelectionModel.setNoOfEntities(10);
		entitySelectionModel.setOperationNameToBind("contact.ContactService.getEntityList");
		
		final ListSnippet listSnippet = new ListSnippet();
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(SnippetConstant.SELECTIONMODE, false);
		
		listSnippet.setEntityListModel(entitySelectionModel);
		listSnippet.setConfiguration(configuration);
		listSnippet.initialize();
		
		final VerticalPanel listSnippetPanel = new VerticalPanel();
		listSnippetPanel.add(listSnippet);
		
		innerPanel.add(selectionCheckBox);
		innerPanel.add(listSnippetPanel);
		
		
		selectionCheckBox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				CheckBox widget = (CheckBox) event.getSource();
				boolean isChecked = widget.isChecked();
				Configuration configuration = new Configuration();
				configuration.setPropertyByName(SnippetConstant.SELECTIONMODE, isChecked);
				ListSnippet listSnippet = new ListSnippet(entitySelectionModel);
				entitySelectionModel.getQueryToBind().setStartIndex(0);
				listSnippet.setConfiguration(configuration);
				listSnippet.initialize();
				listSnippetPanel.clear();
				listSnippetPanel.add(listSnippet);
			}
		});
		
	}
}
