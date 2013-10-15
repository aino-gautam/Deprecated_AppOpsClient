package in.appops.client.common.config.component.base;

import in.appops.client.common.config.dsnip.DynamicMVPFactory;
import in.appops.client.common.config.model.IsConfigurationModel;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;

public abstract class BaseComponent extends Composite implements HasClickHandlers {

	protected DockPanel basePanel;
	protected Configuration viewConfiguration;
	protected IsConfigurationModel model;
	
	protected AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
	protected DynamicMVPFactory mvpFactory = (DynamicMVPFactory)injector.getMVPFactory();

	public void initialize() {
		basePanel = new DockPanel();
		initWidget(basePanel);
	}
	
	public void configure() {
		try {
			viewConfiguration = model.getViewConfiguration();
			if(getBasePanelPrimCss() != null) {
				basePanel.setStylePrimaryName(getBasePanelPrimCss());
			}
			
			if(getBasePanelDependentCss() != null) {
				basePanel.addStyleName(getBasePanelDependentCss());
			}
			basePanel.setVisible(isFieldVisible());
		} catch (Exception e) {

		}
	}

	public void create() {	}
	
	/**
	 * Returns the primary style to be applied to the component basepanel.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getBasePanelPrimCss() {
		String primaryCss = null;
		try {
			if(viewConfiguration.getConfigurationValue(BaseComponentConstant.BC_BASEPANEL_PCLS) != null) {
				primaryCss = viewConfiguration.getConfigurationValue(BaseComponentConstant.BC_BASEPANEL_PCLS).toString();
			}
		} catch (Exception e) {

		}
		return primaryCss;
	}
	

	/**
	 * Returns the dependent style to be applied to the component basepanel.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getBasePanelDependentCss() {
		String depCss = null;
		try {
			if(viewConfiguration.getConfigurationValue(BaseComponentConstant.BC_BASEPANEL_DCLS) != null) {
				depCss = viewConfiguration.getConfigurationValue(BaseComponentConstant.BC_BASEPANEL_DCLS).toString();
			}
		} catch (Exception e) {

		}
		return depCss;
	}
	
	public boolean isFieldVisible() {
		boolean visible = true;
		try {
			if(viewConfiguration.getConfigurationValue(BaseComponentConstant.BC_VISIBLE) != null) {
				visible = (Boolean) viewConfiguration.getConfigurationValue(BaseComponentConstant.BC_VISIBLE);
			}
		} catch (Exception e) {

		}
		return visible;
	}
	
	@Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

	public void setModel(IsConfigurationModel model) {
		this.model = model;
	}

	public interface BaseComponentConstant {
		/** Style class primary for basepanel on which component is added . **/
		public static final String BC_BASEPANEL_PCLS = "basePanelPrimaryCss";
		
		/** Style class dependent for basepanel on which component is added . **/
		public static final String BC_BASEPANEL_DCLS = "basePanelDependentCss";
		
		/** Specifies whether the component should be visible or not **/
		public static final String BC_VISIBLE = "visible";
	}
	
	
}
