package in.appops.client.common.config.component.base;

import in.appops.client.common.config.model.AppopsBaseModel;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;

public abstract class BaseComponentPresenter implements Configurable{
	
	protected Configuration configuration;
	
	protected BaseComponentView view;
	protected AppopsBaseModel model;

	public BaseComponentPresenter() {	

	}
	
	public abstract void initialize();

	public abstract void load();
	
	public void configure() {
		model.setConfiguration(getModelConfiguration());
		model.configure();
		
		view.setConfiguration(getViewConfiguration());
		view.configure();
		
		view.create();
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
	
	protected Configuration getViewConfiguration() {
		if(getConfigurationValue(BaseComponentConstant.BC_CONFIGVIEW) != null) {
			return (Configuration)getConfigurationValue(BaseComponentConstant.BC_CONFIGVIEW);
		}
		return new Configuration();
	}

	protected Configuration getModelConfiguration() {
		if(getConfigurationValue(BaseComponentConstant.BC_CONFIGMODEL) != null) {
			return (Configuration)getConfigurationValue(BaseComponentConstant.BC_CONFIGMODEL);
		}
		return new Configuration();
	}
	
	protected Boolean isConfigDriven() {
		if(getConfigurationValue(BaseComponentConstant.BC_CONFIG_DRIVEN) != null) {
			return (Boolean)getConfigurationValue(BaseComponentConstant.BC_CONFIG_DRIVEN);
		}
		return false;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}
	
	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	public interface BaseComponentConstant {
		String BC_PCLS = "basePrimaryCss";
		String BC_DCLS = "baseDependentCss";
		String BC_CONFIGMODEL = "model";
		String BC_CONFIGVIEW = "view";
		String BC_CONFIG_DRIVEN = "configDriven";
	}

	public BaseComponentView getView() {
		return view;
	}
	
}
