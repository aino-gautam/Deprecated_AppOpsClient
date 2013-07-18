package in.appops.client.common.config.component.base;

import in.appops.client.common.config.component.list.ListComponentView.ListComponentConstant;
import in.appops.client.common.config.model.AppopsBaseModel;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;

public class BaseComponentView extends Composite implements Component {
	
	public interface BaseComponentConstant {
		String BC_ID = "component.id";
		String BC_PCLS = "component.view.basePrimaryCss";
		String BC_DCLS = "component.view.baseDependentCss";
		String BC_HEADER = "compponent.view.header";
		String BC_HEADERVAL = "compponent.view.headervalue";
		String BC_CONFIGMODEL = "component.config.m";
		String BC_CONFIGVIEW = "component.config.v";
		String BC_MODELQUERY = "component.model.query";
		String BC_MODELOP = "component.model.operation";
		String BC_INTERESTED_EVENTS = "interestedEvents";
	
	}

	protected DockPanel basePanel;
	
	private Configuration configuration;
	
	private AppopsBaseModel model;
	
	public BaseComponentView() {
		initialize();
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
	public void configure() {
		basePanel.setStylePrimaryName(getBaseComponentPrimCss());
	}

	protected void initialize() {
		basePanel = new DockPanel();
	}

	@Override
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

	public AppopsBaseModel getModel() {
		return model;
	}

	public void setModel(AppopsBaseModel model) {
		this.model = model;
	}
	
	@SuppressWarnings("unchecked")
	protected ArrayList<String> getInterestedEvents() {
		ArrayList<String> interestedEvents = new ArrayList<String>();
		if(getConfigurationValue(ListComponentConstant.BC_INTERESTED_EVENTS) != null) {
			interestedEvents = (ArrayList<String>) getConfigurationValue(ListComponentConstant.BC_INTERESTED_EVENTS);
		}
		return interestedEvents;
	}
	
	
	public boolean isTypeInteresting(String eventName) {
		ArrayList<String> interestedEvents = getInterestedEvents();
		
		if(!interestedEvents.isEmpty()) {
			if(interestedEvents.contains(eventName)) {
					return true;
			}
		}
		return false;
	}


}
