package in.appops.client.common.config.component.grid;

import in.appops.client.common.config.component.base.BaseComponentView;
import in.appops.client.common.config.component.grid.GridComponentPresenter.GridComponentConstant;
import in.appops.client.common.config.dsnip.HTMLSnippetPresenter;
import in.appops.client.common.config.dsnip.SnippetGenerator;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;

public class GridComponentView extends BaseComponentView implements ScrollHandler  {
	
	private ScrollPanel gridScrollPanel;
	protected FlowPanel gridFlowPanel;
	private String instanceType;
	private String snippetType;
	private EntityList entityList;

	public GridComponentView() {
		super();
	}

	@Override
	public void initialize() {
		super.initialize();
		gridFlowPanel = new FlowPanel();
		gridScrollPanel = new ScrollPanel(gridFlowPanel);
	}
	
	@Override
	public void configure() {
		super.configure();
		if(getGridPanelPrimCss()!=null)
			gridFlowPanel.setStylePrimaryName(getGridPanelPrimCss());

		if(getScrollPanelCss() != null) {
			gridScrollPanel.setStylePrimaryName(getScrollPanelCss());
		}
		
		if(getScrollPanelWidth() != null)
			gridScrollPanel.setWidth(getScrollPanelWidth() + "px");
		else{
			int width = Window.getClientWidth() - 100;
			gridScrollPanel.setWidth(width + "px");
		}
	
		if(getScrollPanelHeight() != null)
			gridScrollPanel.setHeight(getScrollPanelHeight() + "px");
		else{
			int height = Window.getClientHeight() - 120;
			gridScrollPanel.setHeight(height + "px");
		}
		
		setInstanceType(getInstanceType());
		setSnippetType(getSnippetType());
	}
	
	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}


	public void setSnippetType(String snippetType) {
		this.snippetType = snippetType;
	}

	/**
	 * Method takes grid panel css from configuration and return.
	 * @return grid panel css.
	 */
	private String getGridPanelPrimCss() {
		String primaryCss = "gridPanelCss";
		if(getConfigurationValue(GridComponentConstant.GC_PANEL_CSS) != null) {
			primaryCss = getConfigurationValue(GridComponentConstant.GC_PANEL_CSS).toString();
		}
		return primaryCss;
	}
	
	/**
	 * Method takes grid alignment from configuration and return.
	 * @return alignment.
	 */
	private String getGridAlignmemt() {
		String align = "vertical";
		if(getConfigurationValue(GridComponentConstant.GC_ALIGN) != null) {
			align = getConfigurationValue(GridComponentConstant.GC_ALIGN).toString();
		}
		return align;
	}
	
	/**
	 * Method takes scroll panel css from configuration and return.
	 * @return scroll panel css.
	 */
	private String getScrollPanelCss() {
		String scrollPanelCss = "scrollPanelCss";
		if(getConfigurationValue(GridComponentConstant.SCROLL_PANEL_CSS) != null) {
			scrollPanelCss = getConfigurationValue(GridComponentConstant.SCROLL_PANEL_CSS).toString();
		}
		return scrollPanelCss;
	}
	
	/**
	 * Method takes scroll panel width from configuration and return.
	 * @return scroll panel width.
	 */
	private Integer getScrollPanelWidth() {
		Integer width = null;
		if(getConfigurationValue(GridComponentConstant.SCROLL_PANEL_WIDTH) != null) {
			width = (Integer) getConfigurationValue(GridComponentConstant.SCROLL_PANEL_WIDTH);
		}
		return width;
	}
	
	/**
	 * Method takes scroll panel height from configuration and return.
	 * @return scroll panel height.
	 */
	private Integer getScrollPanelHeight() {
		Integer height = null;
		if(getConfigurationValue(GridComponentConstant.SCROLL_PANEL_HEIGHT) != null) {
			height = (Integer) getConfigurationValue(GridComponentConstant.SCROLL_PANEL_HEIGHT);
		}
		return height;
	}

	@Override
	public void create() {
		super.create();
		basePanel.add(gridScrollPanel, DockPanel.CENTER);
		basePanel.setCellHorizontalAlignment(gridScrollPanel, HasAlignment.ALIGN_CENTER);
		gridScrollPanel.addScrollHandler(this);
	}

	
	/**
	 * Method populates the grid snippet.
	 */
	protected void populate() {
		String align = getGridAlignmemt();
		if(align.equals("vertical")){
			alignVertically();
		}
	}
	
	/**
	 * Method aligns the entityList vertically.
	 */
	private void alignVertically(){
		gridFlowPanel.clear();
		
		/*String str = gridScrollPanel.getElement().getStyle().getProperty("width");
		String substr = "px";
		String width = str.substring(0, str.indexOf(substr));
		
		int gridPanelWidth = 500;//Integer.parseInt(width);
									
		int row = 0, col = 0;
		int snippetWidth = Integer.parseInt(width);
		int snippetheight = 0;//Integer.parseInt( getChildSnippet().getHTMLSnippet().getElement().getStyle().getProperty("height"));
		int widthToChk = gridPanelWidth;
		
		while(index < entityList.size()){
			col = 0;
			while(snippetWidth< widthToChk){
				if (index < entityList.size()) {
					Entity entity = entityList.get(index);
					HTMLSnippetPresenter snippetPres  = getChildSnippet();
					gridPanel.setWidget(row ,col ,snippetPres.getHTMLSnippet());
					snippetPres.setEntity(entity);
					snippetPres.load();
					
					Element ele = getChildSnippet().getHTMLSnippet().getElement();
					Style style = ele.getStyle();
					str = style.getHeight();
					
					index++;
					widthToChk = widthToChk-snippetWidth;
					col++;
				}else{
					break;
				}
			}
			row++;
			widthToChk  =  gridPanelWidth;
		}*/
		
		for (int index = 0; index < entityList.size(); index++) {
			Entity entity = entityList.get(index);
			HTMLSnippetPresenter snippetPres = getChildSnippet();
			snippetPres.setEntity(entity);
			snippetPres.load();
			gridFlowPanel.add(snippetPres.getHTMLSnippet());
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
		if(getConfigurationValue(GridComponentConstant.GC_INSTANCETYPE) != null) {
			instanceType = getConfigurationValue(GridComponentConstant.GC_INSTANCETYPE).toString();
		}
		return instanceType;
	}


	private String getSnippetType() {
		String snippetType = null;
		if(getConfigurationValue(GridComponentConstant.GC_SNIPPETTYPE) != null) {
			snippetType = getConfigurationValue(GridComponentConstant.GC_SNIPPETTYPE).toString();
		}
		return snippetType;
	}
	
	/**
	 * Method returns no of columns to display in grid. 
	 * @return No of columns to display.
	 */
	private Integer getNoOfColumns() {
		Integer noOfColumns = 0;
		if(getConfigurationValue(GridComponentConstant.GC_NO_OF_COLS) != null) {
			noOfColumns = (Integer) getConfigurationValue(GridComponentConstant.GC_NO_OF_COLS);
		}
		return noOfColumns;
	}
	

	public EntityList getEntityList() {
		return entityList;
	}


	public void setEntityList(EntityList entityList) {
		this.entityList = entityList;
	}


	@Override
	public void onScroll(ScrollEvent event) {
		
		// @TODO Fire some event so that ComponentPresenter will listen and fetch the next entity list then 
		//call componentview method to add entities in the gridpanel. 
		
	}
}
