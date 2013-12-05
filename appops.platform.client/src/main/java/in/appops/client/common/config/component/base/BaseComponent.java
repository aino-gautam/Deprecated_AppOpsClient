package in.appops.client.common.config.component.base;

import in.appops.client.common.config.dsnip.DynamicMvpFactory;
import in.appops.client.common.config.model.IsConfigurationModel;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;

public abstract class BaseComponent extends Composite implements HasClickHandlers {

	protected DockPanel basePanel;
	protected Configuration viewConfiguration;
	protected IsConfigurationModel model;
	protected SimpleEventBus localEventBus;

	protected AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
	protected DynamicMvpFactory mvpFactory = injector.getMVPFactory();

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
	
	public void clear() {	}
	
	public void reset() {	}
	
	public Object getValue() {
		return null;
	}
	
	public void setValue() {	}
	
	public boolean validate() { 
		return false;
	}

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
	
	public boolean isFormAction() {
		boolean isFormAction = true;
		try {
			if(viewConfiguration.getConfigurationValue(BaseComponentConstant.BC_ISFORMACTION) != null) {
				isFormAction = (Boolean) viewConfiguration.getConfigurationValue(BaseComponentConstant.BC_ISFORMACTION);
			}
		} catch (Exception e) {

		}
		return isFormAction;
	}
	
	public String getFormAction() {
		String formAction = null;
		try {
			if(viewConfiguration.getConfigurationValue(BaseComponentConstant.BC_FORMACTION) != null) {
				formAction = viewConfiguration.getConfigurationValue(BaseComponentConstant.BC_FORMACTION).toString();
			}
		} catch (Exception e) {

		}
		return formAction;
	}

	@Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

	public void setModel(IsConfigurationModel model) {
		this.model = model;
	}

	public IsConfigurationModel getModel() {
		return model;
	}

	public SimpleEventBus getLocalEventBus() {
		return localEventBus;
	}

	public void setLocalEventBus(SimpleEventBus localEventBus) {
		this.localEventBus = localEventBus;
	}

	@SuppressWarnings("rawtypes")
	protected void fireLocalEvent(GwtEvent event) {
		if(localEventBus == null) {
			AppUtils.EVENT_BUS.fireEvent(event);
		} else {
			localEventBus.fireEvent(event);
		}
	}

	public interface BaseComponentConstant {
		/** Style class primary for basepanel on which component is added . **/
		public static final String BC_BASEPANEL_PCLS = "basePanelPrimaryCss";

		/** Style class dependent for basepanel on which component is added . **/
		public static final String BC_BASEPANEL_DCLS = "basePanelDependentCss";

		/** Specifies whether the component should be visible or not **/
		public static final String BC_VISIBLE = "visible";
		
		/** Specifies whether to execute some form action or not **/
		public static final String BC_ISFORMACTION= "isFormAction";
		
		/** Specifies form action **/
		public static final String BC_FORMACTION= "formAction";
		
		/** Specifies whether field is inputField  **/
		public static final String BC_ISINPUTFIELD= "isInputField";
	}


}
