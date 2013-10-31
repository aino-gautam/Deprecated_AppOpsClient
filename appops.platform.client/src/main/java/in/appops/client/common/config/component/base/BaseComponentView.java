package in.appops.client.common.config.component.base;

import in.appops.client.common.config.component.base.BaseComponent.BaseComponentConstant;
import in.appops.client.common.config.dsnip.DynamicMvpFactory;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;

@Deprecated
public abstract class BaseComponentView extends Composite implements Configurable, HasClickHandlers{

	protected DockPanel basePanel;
	
	protected Configuration configuration;
	
	protected AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
	protected DynamicMvpFactory snippetGenerator = (DynamicMvpFactory)injector.getMVPFactory();
	
	public BaseComponentView() {
		initialize();
	}

	public void configure() {
		basePanel.setStylePrimaryName(getBaseComponentPrimCss());
	}

	protected void initialize() {
		basePanel = new DockPanel();
	}

	public void create() {
		initWidget(basePanel);
	}
	
	protected boolean hasConfiguration(String configKey) {
		if(configuration != null && configuration.getPropertyByName(configKey) != null) {
			return true;
		}
		return false;
	}
	
	protected Serializable getConfigurationValue(String configKey) {
		if(hasConfiguration(configKey)) {
			return configuration.getPropertyByName(configKey);
		}
		return null;
	}
	
	private String getBaseComponentPrimCss() {
		String primaryCss = "defaultcss";
	/*	if(getConfigurationValue(BaseComponentConstant.BC_PCLS) != null) {
			primaryCss = getConfigurationValue(BaseComponentConstant.BC_PCLS).toString();
		}*/
		return primaryCss;
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
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }
	
	
}
