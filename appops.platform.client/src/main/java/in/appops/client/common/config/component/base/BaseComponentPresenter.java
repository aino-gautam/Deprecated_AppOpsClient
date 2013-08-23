package in.appops.client.common.config.component.base;

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

	private HandlerRegistration historyRegistration;

	public BaseComponentPresenter() {	}
	
	public void init() { }
	
	public void configure() {
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
	
	public void updateConfiguration(String confProp) {
		
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		// TODO Auto-generated method stub
		
	}
	
	public void removeEventHandler() {
		historyRegistration.removeHandler();
	}
	
	public interface BaseComponentConstant {
		String BC_ID = "component.id";
		String BC_PCLS = "component.view.basePrimaryCss";
		String BC_DCLS = "component.view.baseDependentCss";
		String BC_CONFIGMODEL = "model";
		String BC_CONFIGVIEW = "view";
		String BC_INTERESTED_EVENTS = "interestedEvents";
	}
}
