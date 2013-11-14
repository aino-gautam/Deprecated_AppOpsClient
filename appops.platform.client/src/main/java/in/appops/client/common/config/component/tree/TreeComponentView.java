package in.appops.client.common.config.component.tree;

import in.appops.client.common.config.component.base.BaseComponentView;
import in.appops.client.common.config.component.tree.TreeComponentPresenter.TreeComponentConstant;
import in.appops.client.common.config.dsnip.HTMLSnippetPresenter;
import in.appops.client.common.config.dsnip.SnippetGenerator;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class TreeComponentView extends BaseComponentView {
	
	private ScrollPanel scrollPanel;
	private String instanceType;
	private String snippetType;
	private EntityList entityList;
	
	Tree root = new Tree();

	public TreeComponentView() {
		super();
	}


	@Override
	public void initialize() {
		super.initialize();
		scrollPanel = new ScrollPanel();
	}
	
	@Override
	public void configure() {
		super.configure();
		
		setInstanceType(getInstanceType());
		setSnippetType(getSnippetType());
		root.setWidth("190px");
	}
	
	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}


	public void setSnippetType(String snippetType) {
		this.snippetType = snippetType;
	}

	@Override
	public void create() {
		super.create();
		basePanel.add(scrollPanel, DockPanel.CENTER);
		basePanel.setCellHorizontalAlignment(scrollPanel, HasAlignment.ALIGN_CENTER);
		scrollPanel.setAlwaysShowScrollBars(false);
	}

	
	
	protected void populate() {
		TreeItem selectedTreeItem = root.getSelectedItem();
		if(selectedTreeItem == null) {
			root.clear();
			for(Entity entity : entityList){
				HTMLSnippetPresenter snippetPres  = getChildSnippet();
				TreeItem item = root.addItem(snippetPres.getView());
				item.setTitle("0");
				item.setStylePrimaryName("treeItem");
				item.addItem("");
				snippetPres.setEntity(entity);
				snippetPres.load();
			}
		} else {
			selectedTreeItem.removeItems();
			for(Entity entity : entityList){
				HTMLSnippetPresenter snippetPres  = getChildSnippet();
				TreeItem item = selectedTreeItem.addItem(snippetPres.getView());
				int depth = (Integer.parseInt(selectedTreeItem.getTitle())) + 1;
				item.setTitle(Integer.toString(depth));

				item.setStylePrimaryName("treeItem");
				item.addItem("");
				snippetPres.setEntity(entity);
				snippetPres.load();
			}
		}
		scrollPanel.setWidget(root);
	}

	private HTMLSnippetPresenter getChildSnippet() {
		AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
		SnippetGenerator snippetGenerator = (SnippetGenerator)injector.getSnippetGenerator();
		HTMLSnippetPresenter snippetPres = snippetGenerator.requestHTMLSnippet(snippetType, instanceType);
		return snippetPres;
	}


	private String getInstanceType() {
		String instanceType = null;
		if(getConfigurationValue(TreeComponentConstant.TR_INSTANCETYPE) != null) {
			instanceType = getConfigurationValue(TreeComponentConstant.TR_INSTANCETYPE).toString();
		}
		return instanceType;
	}


	private String getSnippetType() {
		String snippetType = null;
		if(getConfigurationValue(TreeComponentConstant.TR_SNIPPETTYPE) != null) {
			snippetType = getConfigurationValue(TreeComponentConstant.TR_SNIPPETTYPE).toString();
		}
		return snippetType;
	}

	public EntityList getEntityList() {
		return entityList;
	}


	public void setEntityList(EntityList entityList) {
		this.entityList = entityList;
	}

	public Tree getRoot() {
		return this.root;
	}
}
