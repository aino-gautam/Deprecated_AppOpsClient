package in.appops.client.common.config.component.base;

import in.appops.client.common.config.component.base.BaseComponentView.BaseComponentConstant;
import in.appops.client.common.config.model.AppopsBaseModel;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;

public class BaseComponentPresenter implements Configurable, ValueChangeHandler<String>{
	
	protected Configuration configuration;
	
	protected BaseComponentView view;
	protected AppopsBaseModel model;

	private String componentType;
	private HandlerRegistration historyRegistration;

	public void init() {
		historyRegistration = History.addValueChangeHandler(this);
	}
	
	public void load() {
		
	}


	private boolean hasConfiguration(String configKey) {
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
	
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	
	public String getComponentType() {
		return componentType;
	}
	
	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	public BaseComponentView getView() {
		return view;
	}

	public void setView(BaseComponentView view) {
		this.view = view;
	}

	public AppopsBaseModel getModel() {
		return model;
	}

	public void setModel(AppopsBaseModel model) {
		this.model = model;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		// TODO Auto-generated method stub
		
	}
	
	public void removeEventHandler() {
		historyRegistration.removeHandler();
	}
}
