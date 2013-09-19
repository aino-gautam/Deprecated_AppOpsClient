package in.appops.client.common.config.component.base;

import in.appops.client.common.config.component.base.BaseComponentPresenter.BaseComponentConstant;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;

public abstract class BaseComponentView extends Composite implements Configurable {

	protected DockPanel basePanel;
	
	protected Configuration configuration;
	
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
		if(getConfigurationValue(BaseComponentConstant.BC_PCLS) != null) {
			primaryCss = getConfigurationValue(BaseComponentConstant.BC_PCLS).toString();
		}
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

}
