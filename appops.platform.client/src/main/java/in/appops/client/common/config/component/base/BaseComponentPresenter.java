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
import in.appops.client.common.config.model.EntityModel;
import in.appops.client.common.config.model.IsConfigurationModel;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.util.EntityGraphException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;

public abstract class BaseComponentPresenter implements SupportsEventActionRules, FieldEventHandler, ActionEventHandler{

	protected BaseComponent view;
	protected IsConfigurationModel model;

	protected List<HandlerRegistration> handlerRegistrationList;

	protected String type;

	protected AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
	protected DynamicMVPFactory dynamicFactory = injector.getMVPFactory();

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
	}

	protected abstract void initialize();

	public void onContextLoad() {
		processEventActionRuleMap("onContextLoad", null);
	}

	public void configure() {
		model.loadInstanceConfiguration();
		model.configure();
		view.configure();
	}

	public void create() {
		view.create();
		registerHandlers();
	}

	public BaseComponent getView() {
		return view;
	}

	public IsConfigurationModel getModel() {
		return model;
	}

	protected void registerHandlers() {
		if(handlerRegistrationList == null) {
			handlerRegistrationList = new ArrayList<HandlerRegistration>();
		}
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

			HashMap<String, Property<? extends Serializable>> configToUpdateMap = updateConfigurationRule.getConfigurationToUpdateMap();
			HashMap<String, Property<? extends Serializable>> preparedConfigMap = prepareConfigurationsToUpdate(configToUpdateMap, eventData);

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HashMap<String, Property<? extends Serializable>> prepareConfigurationsToUpdate(HashMap<String, Property<? extends Serializable>> configToUpdateMap, Object eventData) {
		try {
			for(Entry<String, Property<? extends Serializable>> configToUpdateEntry : configToUpdateMap.entrySet()) {
				String propertyToUpdate = configToUpdateEntry.getKey();
				Property<Serializable> propertyValue = (Property<Serializable>) configToUpdateEntry.getValue();

				if(propertyValue.toString() != null) {
					String propStrVal = propertyValue.getValue().toString();

					if(propStrVal.startsWith(UpdateConfigurationRuleConstant.EVENTDATA)) {

						if(propStrVal.indexOf(UpdateConfigurationRuleConstant.SEPERATOR) == -1) {
							propertyValue.setValue((Serializable) eventData);
						} else {
							String entityProp = propStrVal.substring(propStrVal.indexOf(UpdateConfigurationRuleConstant.SEPERATOR) + 1);
							Entity entity = (Entity)eventData;
							propertyValue.setValue(entity.getGraphPropertyValue(entityProp, entity));
						}
					} else if(propStrVal.startsWith(UpdateConfigurationRuleConstant.APPCONTEXT)) {
						String appContextProp = propStrVal.substring(propStrVal.indexOf(UpdateConfigurationRuleConstant.SEPERATOR) + 1);
						propertyValue.setValue(ApplicationContext.getInstance().getGraphPropertyValue(appContextProp, null));
					} else if(propStrVal.startsWith(UpdateConfigurationRuleConstant.CURRENTENTITY)) {

						if(propStrVal.indexOf(UpdateConfigurationRuleConstant.SEPERATOR) == -1) {
							propertyValue.setValue(((EntityModel)model).getEntity());
						} else {
							String currentEntityProp = propStrVal.substring(propStrVal.indexOf(UpdateConfigurationRuleConstant.SEPERATOR) + 1);
							Entity currentEntity = ((EntityModel)model).getEntity();
							propertyValue.setValue(currentEntity.getGraphPropertyValue(currentEntityProp, currentEntity));
						}
					} else if(propStrVal.startsWith(UpdateConfigurationRuleConstant.PARENTENTITY)) {

						if(propStrVal.indexOf(UpdateConfigurationRuleConstant.SEPERATOR) == -1) {
							propertyValue.setValue(model.getContext().getParentEntity());
						} else {
							String parentEntityProp = propStrVal.substring(propStrVal.indexOf(UpdateConfigurationRuleConstant.SEPERATOR) + 1);
							Entity parentEntity = model.getContext().getParentEntity();
							propertyValue.setValue(parentEntity.getGraphPropertyValue(parentEntityProp, parentEntity));
						}
					}
					if(propertyValue.getValue() instanceof Key) {
						Key keyVal = (Key)propertyValue.getValue();
						propertyValue.setValue(keyVal.getKeyValue());
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

	protected	void applyConfiguration(HashMap<String, Property<? extends Serializable>> preparedConfigMap) {
		try {
			for(Entry<String, Property<? extends Serializable>> configToUpdateEntry : preparedConfigMap.entrySet()) {
				String propertyToUpdate = configToUpdateEntry.getKey(); // model.query.param1
				Serializable propertyValue = configToUpdateEntry.getValue().getValue(); // <value>
				getModel().updateConfiguration(propertyToUpdate, propertyValue);
			}
			configure();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			final String SEPARATOR = "##";
			String eventType = Integer.toString(event.getEventType());
			BaseComponent eventSource = (BaseComponent) event.getEventSource();
			String eventSourceInstance = eventSource.getModel().getInstance();
			String eventName = eventType + SEPARATOR + eventSourceInstance;

			if(model.getEventActionRuleMap() != null && model.getEventActionRuleMap().getEventActionRules(eventName) != null ) {
				Object eventData = event.getEventData();
				processEventActionRuleMap(eventName, eventData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
