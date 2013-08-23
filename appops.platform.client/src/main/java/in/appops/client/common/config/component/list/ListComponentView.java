package in.appops.client.common.config.component.list;

import in.appops.client.common.config.component.base.BaseComponentView;
import in.appops.client.common.config.component.list.ListComponentPresenter.ListComponentConstant;
import in.appops.client.common.config.dsnip.HTMLSnippetPresenter;
import in.appops.client.common.config.dsnip.SnippetGenerator;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;

public class ListComponentView extends BaseComponentView  {
	private ScrollPanel scrollPanel;
	protected FlexTable listPanel;
	private int row = 0;
	private String instanceType;
	private String snippetType;
	private EntityList entityList;


	public ListComponentView() {
		super();
	}


	@Override
	public void initialize() {
		super.initialize();
		listPanel = new FlexTable();
		scrollPanel = new ScrollPanel(listPanel);
	}
	
	@Override
	public void configure() {
		super.configure();
		
		listPanel.setStylePrimaryName(getListPanelPrimCss());
		
		setInstanceType(getInstanceType());
		setSnippetType(getSnippetType());
	}
	
	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}


	public void setSnippetType(String snippetType) {
		this.snippetType = snippetType;
	}


	private String getListPanelPrimCss() {
		String primaryCss = "appops-ListScrollCss";
		if(getConfigurationValue(ListComponentConstant.LC_LISTCLS) != null) {
			primaryCss = getConfigurationValue(ListComponentConstant.LC_LISTCLS).toString();
		}
		return primaryCss;
	}


	@Override
	public void create() {
		super.create();
		basePanel.add(scrollPanel, DockPanel.CENTER);
		basePanel.setCellHorizontalAlignment(scrollPanel, HasAlignment.ALIGN_CENTER);
		scrollPanel.setAlwaysShowScrollBars(false);
	}

	
	
	protected void populate() {
		listPanel.clear();
		row = 0;
		for(Entity entity : entityList){
			HTMLSnippetPresenter snippetPres  = getChildSnippet();
			listPanel.setWidget(row ,0 ,snippetPres.getHTMLSnippet());
			snippetPres.setEntity(entity);
			snippetPres.load();
			row++;
		}
	}

	private HTMLSnippetPresenter getChildSnippet() {
		AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
		SnippetGenerator snippetGenerator = (SnippetGenerator)injector.getSnippetGenerator();
		HTMLSnippetPresenter snippetPres = snippetGenerator.generateSnippet(snippetType, instanceType);
		return snippetPres;
	}


	private String getInstanceType() {
		String instanceType = null;
		if(getConfigurationValue(ListComponentConstant.LC_INSTANCETYPE) != null) {
			instanceType = getConfigurationValue(ListComponentConstant.LC_INSTANCETYPE).toString();
		}
		return instanceType;
	}


	private String getSnippetType() {
		String snippetType = "appops-ListScrollCss";
		if(getConfigurationValue(ListComponentConstant.LC_SNIPPETTYPE) != null) {
			snippetType = getConfigurationValue(ListComponentConstant.LC_SNIPPETTYPE).toString();
		}
		return snippetType;
	}


	public EntityList getEntityList() {
		return entityList;
	}


	public void setEntityList(EntityList entityList) {
		this.entityList = entityList;
	}
}
