package in.appops.client.common.config.component.list;

import in.appops.client.common.config.component.base.BaseComponent;
import in.appops.client.common.config.component.list.ListComponentPresenter.ListComponentConstant;
import in.appops.client.common.config.dsnip.Context;
import in.appops.client.common.config.dsnip.HTMLSnippetPresenter;
import in.appops.client.common.config.model.ConfigurationListModel;
import in.appops.client.common.config.model.ConfigurationModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;

public class ListComponentView extends BaseComponent implements EntityListReceiver {
	private ScrollPanel scrollPanel;
	protected FlexTable listPanel;
	private int row = 0;

	@Override
	public void initialize() {
		super.initialize();
		listPanel = new FlexTable();
		scrollPanel = new ScrollPanel(listPanel);
		((ConfigurationListModel)model).setReceiver(this);
	}

	@Override
	public void configure() {
		super.configure();
		listPanel.setStylePrimaryName(getListPanelPrimaryCss());
		scrollPanel.setStylePrimaryName(getListScrollPrimaryCss());
	}

	private String getListPanelPrimaryCss() {
		String primaryCss = "appops-ListScrollCss";
		if(viewConfiguration.getConfigurationValue(ListComponentConstant.LC_LISTCLS) != null) {
			primaryCss = viewConfiguration.getConfigurationValue(ListComponentConstant.LC_LISTCLS).toString();
		}
		return primaryCss;
	}

	private String getListScrollPrimaryCss() {
		String primaryCss = "appops-ListScrollCss";
		if(viewConfiguration.getConfigurationValue(ListComponentConstant.LC_LISTSCROLLCLS) != null) {
			primaryCss = viewConfiguration.getConfigurationValue(ListComponentConstant.LC_LISTSCROLLCLS).toString();
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

	private HTMLSnippetPresenter getChildSnippet() {
		HTMLSnippetPresenter snippetPres = mvpFactory.requestHTMLSnippet(((ConfigurationListModel)model).getSnippetType(), ((ConfigurationListModel)model).getSnippetInstance());
		return snippetPres;
	}

	@Override
	public void onEntityListReceived(EntityList entityList) {
		listPanel.clear();
		row = 0;
		for(Entity entity : entityList){
			HTMLSnippetPresenter snippetPres  = getChildSnippet();
			((ConfigurationModel)snippetPres.getModel()).setEntity(entity);

			Context componentContext = new Context();
			snippetPres.getModel().setContext(componentContext);

			snippetPres.configure();
			snippetPres.create();
			listPanel.setWidget(row, 0, snippetPres.getView());
			row++;
		}
	}

	@Override
	public void onEntityListUpdated() {}

	@Override
	public void updateCurrentView(Entity entity) {	}

	@Override
	public void noMoreData() {	}
}
