package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponentPresenter.BaseComponentConstant;
import in.appops.client.common.config.dsnip.Container.ContainerConstant;
import in.appops.client.common.config.model.EntityModel;
import in.appops.client.common.config.util.Store;
import in.appops.platform.core.shared.Configuration;

import java.util.HashMap;

public class HTMLSnippetModel extends EntityModel {

	private Configuration snippetConfiguration;
	
	public String getDescription(String snippetType) {
		String description = Store.getFromSnippetStore(snippetType);
		return description;
	}
	
	public Configuration getConfiguration(String snippetInstance) {
		snippetConfiguration = Store.getFromConfigurationStore(snippetInstance);
		return snippetConfiguration;
	}
	
	public EventActionSet getEventActionsRuleMap() {
		EventActionSet eventActions = null;
		
		if(snippetConfiguration.getConfigurationValue(ContainerConstant.CT_INTRSDEVNTS) != null) {
			eventActions = (EventActionSet) snippetConfiguration.getConfigurationValue(ContainerConstant.CT_INTRSDEVNTS); 
		}
		return eventActions;
	}
	
	protected Configuration getViewConfiguration() {
		if(snippetConfiguration.getConfigurationValue(BaseComponentConstant.BC_CONFIGVIEW) != null) {
			return (Configuration)snippetConfiguration.getConfigurationValue(BaseComponentConstant.BC_CONFIGVIEW);
		}
		return null;
	}

	protected Configuration getModelConfiguration() {
		if(snippetConfiguration.getConfigurationValue(BaseComponentConstant.BC_CONFIGMODEL) != null) {
			return (Configuration)snippetConfiguration.getConfigurationValue(BaseComponentConstant.BC_CONFIGMODEL);
		}
		return null;
	}
	

}
