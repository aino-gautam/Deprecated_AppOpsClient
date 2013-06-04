package in.appops.showcase.web.gwt.snippets.client;

import in.appops.client.common.core.EntityListModel;
import in.appops.client.common.core.EntitySelectionModel;
import in.appops.client.common.snippet.GridSnippet;
import in.appops.client.common.snippet.ListSnippet;
import in.appops.client.common.snippet.SnippetConstant;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
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
	public static final String ACTIONBOXSNIPPET = "ActionBox Snippet";
	
	@Override
	public void onModuleLoad() {
		
		HorizontalPanel listBoxPanel = new HorizontalPanel();
		Label titleLabel = new Label("Select a snippet");
		
		listBox = new ListBox();
		listBox.addItem("--Select--");
		listBox.addItem(LISTSNIPPET);
		listBox.addItem(GRIDSNIPPET);
		listBox.addItem(ICONSNIPPET);
		listBox.addItem(ACTIONBOXSNIPPET);
		
		listBox.addChangeHandler(this);
		
		listBoxPanel.add(titleLabel);
		listBoxPanel.add(listBox);
		
		listBoxPanel.setSpacing(5);
		
		innerPanel = new VerticalPanel();
		innerPanel.setWidth("100%");
		
		basePanel = new VerticalPanel();
		basePanel.add(listBoxPanel);
		basePanel.add(innerPanel);
		
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
		}

	}
	
	private void showGridSnippet() {
		
		CheckBox selectionCheckBox = new CheckBox("Allow Selection ");

		Query query = new Query();
		query.setQueryName("getAllServices");
		query.setListSize(8);
		
		final EntityListModel listModel = new EntityListModel();
		listModel.setQueryToBind(query);
		listModel.setNoOfEntities(10);
		listModel.setOperationNameToBind("spacemanagement.SpaceManagementService.getServicesForSpace");
		
		EntitySelectionModel entitySelectionModel = new EntitySelectionModel();
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
		
		
		selectionCheckBox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				CheckBox widget = (CheckBox) event.getSource();
				boolean isChecked = widget.isChecked();
				Configuration configuration = new Configuration();
				configuration.setPropertyByName(SnippetConstant.SELECTIONMODE, isChecked);
				
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
		query.setListSize(8);
		
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
				listSnippet.setConfiguration(configuration);
				listSnippet.initialize();
				listSnippetPanel.clear();
				listSnippetPanel.add(listSnippet);
			}
		});
		
	}
	
	

	
}
