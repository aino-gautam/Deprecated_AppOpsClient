package in.appops.client.common.config.component.base;

import in.appops.client.common.config.dsnip.ActionEvent;
import in.appops.client.common.config.dsnip.ActionEventHandler;
import in.appops.client.common.config.dsnip.ApplicationContext;
import in.appops.client.common.config.dsnip.DynamicMVPFactory;
import in.appops.client.common.config.dsnip.event.EventActionRule;
import in.appops.client.common.config.dsnip.event.EventActionRuleMap;
import in.appops.client.common.config.dsnip.event.EventActionRulesList;
import in.appops.client.common.config.dsnip.event.SnippetControllerRule;
import in.appops.client.common.config.dsnip.event.SubEventRule;
import in.appops.client.common.config.dsnip.event.SupportsEventActionRules;
import in.appops.client.common.config.dsnip.event.UpdateConfigurationRule;
import in.appops.client.common.config.dsnip.event.UpdateConfigurationRule.UpdateConfigurationRuleConstant;
import in.appops.client.common.config.model.IsConfigurationModel;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.util.EntityGraphException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;

public abstract class BaseComponentPresenter implements SupportsEventActionRules, FieldEventHandler, ActionEventHandler{
	
	protected BaseComponent view;
	protected IsConfigurationModel model;
	
	private List<HandlerRegistration> handlerRegistrationList;
	
	protected String type;
	
	protected AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
	protected DynamicMVPFactory dynamicFactory = (DynamicMVPFactory)injector.getMVPFactory();
	
	public BaseComponentPresenter() {}
	
	public BaseComponentPresenter(String type, String instance) {
		this(type, instance, null);
	}
	
	protected BaseComponentPresenter(String type, String instance, IsConfigurationModel model) {
		this.type = type;
		
		if(model != null) {
			this.model = model;
		}
		initialize();
		this.model.setInstance(instance);
		configure();
		registerHandlers();
	}
	
	protected abstract void initialize();

	protected void configure() {
		model.loadInstanceConfiguration();
		model.configure();
		view.configure();
	}

	public void create() {
		view.create();
	}
	
	public BaseComponent getView() {
		return view;
	}
	
	public IsConfigurationModel getModel() {
		return model;
	}
	
	protected void registerHandlers() {
		/**
		 * Register for Local Events
		 */
		if(handlerRegistrationList == null) {
			handlerRegistrationList = new ArrayList<HandlerRegistration>();
		}
		handlerRegistrationList.add(AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this));
		
		/**
		 * Register for Action Events
		 */
		EventActionRuleMap eventActionRuleMap = model.getEventActionRuleMap();
		if(eventActionRuleMap != null) {
			Set<String> events = eventActionRuleMap.getEventActionNames();
			if(!events.isEmpty()) {
				for(String event : events) {
					AppUtils.ACTIONEVENT_BUS.registerHandler(event, this);
				}
			}
		}
	}
	
	public void removeHandlers() {
		if(handlerRegistrationList != null && !handlerRegistrationList.isEmpty()) {
			for(HandlerRegistration handlerRegistration : handlerRegistrationList) {
				handlerRegistration.removeHandler();
			}
		}
	}
	
	@Override
	public void processEventActionRuleMap(String eventName, Object eventData) {
		EventActionRuleMap eventActionRuleMap =  model.getEventActionRuleMap();
		
		EventActionRulesList eventActionRulesList = eventActionRuleMap.getEventActionRules(eventName);
		
		if(eventActionRulesList != null && !eventActionRulesList.isEmpty()) {
			for(EventActionRule eventActionRule : eventActionRulesList) {
				if(eventActionRule instanceof SnippetControllerRule) {
					processSnippetControllerRule((SnippetControllerRule) eventActionRule);
				} else if(eventActionRule instanceof UpdateConfigurationRule) {
					processUpdateConfigurationRule((UpdateConfigurationRule) eventActionRule, eventData);
				} else if(eventActionRule instanceof SubEventRule) {
					
				}
			}
		}
	}
	
	@Override
	public void processUpdateConfigurationRule(UpdateConfigurationRule updateConfigurationRule, Object eventData) {
		boolean configUpdate = updateConfigurationRule.hasConfigurationUpdation();
		
		if(configUpdate) {
			 
			HashMap<String, Object> configToUpdateMap = updateConfigurationRule.getConfigurationToUpdateMap();
			HashMap<String, Object> preparedConfigMap = prepareConfigurationsToUpdate(configToUpdateMap, eventData);
			
			if(preparedConfigMap != null) {
				applyConfiguration(preparedConfigMap);
			}
			
		}
		
	}
	
	@Override
	public void processSubEventRule(SubEventRule subEventRule) {
		
	}
	
	@Override
	public void processSnippetControllerRule(SnippetControllerRule snippetControllerRule) {
		
	}
	
	private HashMap<String, Object> prepareConfigurationsToUpdate(HashMap<String, Object> configToUpdateMap, Object eventData) {
		try {
			for(Map.Entry<String, Object> configToUpdateEntry : configToUpdateMap.entrySet()) {
				String propertyToUpdate = configToUpdateEntry.getKey();
				Object propertyValue = configToUpdateEntry.getValue();
				
				if(propertyValue != null) {
					String propStrVal = propertyValue.toString();
					
					if(propStrVal.startsWith(UpdateConfigurationRuleConstant.EVENTDATA)) {
						
						if(propStrVal.indexOf(UpdateConfigurationRuleConstant.SEPERATOR) == -1) {
							propertyValue = (Serializable) eventData;
						} else {
							String entityProp = propStrVal.substring(propStrVal.indexOf(UpdateConfigurationRuleConstant.SEPERATOR) + 1);
							Entity entity = (Entity)eventData;
							propertyValue = entity.getGraphPropertyValue(entityProp, entity);
						}
					} else if(propStrVal.startsWith(UpdateConfigurationRuleConstant.APPCONTEXT)) {
						String appContextProp = propStrVal.substring(propStrVal.indexOf(".") + 1);
						propertyValue = ApplicationContext.getInstance().getGraphPropertyValue(appContextProp, null);
					}
					if(propertyValue instanceof Key) {
						Key keyVal = (Key)propertyValue;
						propertyValue = keyVal.getKeyValue();
					}
					configToUpdateMap.put(propertyToUpdate, propertyValue);
				}
				
			}
		} catch (EntityGraphException e) {
			e.printStackTrace();
			return null;
		}
		return configToUpdateMap;
	}
	
	protected void applyConfiguration(HashMap<String, Object> preparedConfigMap) {
		// TODO
	}
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		
	}
	
	@Override
	public void onActionEvent(ActionEvent event) {
		try {
			String eventName = event.getEventName();
			Object eventData = event.getEventData();

			processEventActionRuleMap(eventName, eventData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
