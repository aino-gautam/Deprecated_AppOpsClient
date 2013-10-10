package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.dsnip.event.EventActionRuleMap;
import in.appops.client.common.config.dsnip.event.EventActionRulesList;
import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.core.EntityReceiver;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityGraphException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class HTMLSnippetPresenter implements FieldEventHandler, EntityReceiver, ClickHandler, ActionEventHandler  {

	public interface HTMLSnippetConstant {
		String HS_FIELDEVENTS = "interestedFieldEvents";
		String HS_PCLS = "basePrimaryCss";
		String HS_DCLS = "baseDependentCss";
		String CONTAINERSNIPPET = "containerSnippet";
		String HTMLSNIPPET = "htmlSnippet";
		String EVENTDATA = "evt";
		String SEPERATOR = ".";
		String APPCONTEXT = "ac";
		String EVENTACTIONRULEMAP = "earm";
	}

	protected HTMLSnippetView view;
	protected HTMLSnippetModel model;
	protected Entity entity;
	private String snippetType;
	private String snippetInstance;
	
	protected EventActionRuleMap eventActionRuleMap;
	
	protected AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
	protected SnippetGenerator snippetGenerator = (SnippetGenerator)injector.getSnippetGenerator();
	private Map<String, HTMLSnippetPresenter> snippetMap	= new HashMap<String, HTMLSnippetPresenter>();
	protected HandlerRegistration fieldEventRegistration;

	public HTMLSnippetPresenter(String snippetType, String snippetInstance) {
		this.setSnippetType(snippetType);
		this.setSnippetInstance(snippetInstance);
		init();
		configure();
	}

	protected void init() {
		model = new HTMLSnippetModel();
		view = new HTMLSnippetView();
		
		String snippetDescription = model.getDescription(getSnippetType());
		view.setSnippetDescription(snippetDescription);
		view.addClickHandler(this); // shift to basepresenter
		
		fieldEventRegistration = AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this); // shift to basepresenter
	}
	
	/**
	 * loads the initial configuration and based on it configures the model and view
	 */
	protected void configure() {
		model.loadInstanceConfiguration(getSnippetInstance());
		model.configure();
		view.configure();
		
		eventActionRuleMap =  model.getEventActionRuleMap(); // variable to be defined in basepresenter
		// shift to pagepresenter
		Set<String> eventNamesSet = eventActionRuleMap.getEventActionNames();
		for(String eventName : eventNamesSet) {
			AppUtils.ACTIONEVENT_BUS.registerHandler(eventName, this);
		}

	}
	
	public void load() {
		if(entity != null) {
			populateFields();
		} else {
			model.fetchEntity();
		}
	}

	protected void create() {
		view.processSnippetDescription();
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public void populateFields() {
		Map<String, Widget> snippetEle = view.getSnippetElementMap();
		for (Map.Entry<String, Widget> elementEntry : snippetEle.entrySet()) {
			Widget element = elementEntry.getValue();
	    	if(element instanceof BaseField) {
	    		BaseField baseField = (BaseField)element;
	    		
	    		if(entity.getPropertyByName("id") != null) {
					Key key = (Key)entity.getPropertyByName("id");
					Long id = (Long)key.getKeyValue();
					baseField.setBindId(id);
	    		}

				if(baseField.isFieldVisible()) {
				
		    		String bindProp = baseField.getBindProperty();
		    		if(bindProp != null) {
		    			Serializable bindValue = entity.getPropertyByName(bindProp);
		    			baseField.setValue(bindValue);
		    		}
				}
	    	}
		}
	}


	@Override
	public void onFieldEvent(FieldEvent event) {
		/*try {
			String eventType = Integer.toString(event.getEventType());
			Object eventSource = event.getEventSource();
			
			String eventSourceId = null;
			
			Object eventData = event.getEventData();
			if(eventSource instanceof BaseField) {
				BaseField field = (BaseField)eventSource;
				eventSourceId = field.getBaseFieldId();

				if(field instanceof ListBoxField) {
					SelectedItem selectedItem = (SelectedItem)eventData;
					eventData = selectedItem.getAssociatedEntity();
				}
			}

			String eventName = eventType + "##" + eventSourceId;
			
			if(isInterestedFieldEvent(eventName)) {
				Configuration fieldEventConf = getFieldEventConfiguration(eventName);
				
				// TODO Check if application context has to be updated (T/F).
				String appContextParam = fieldEventConf.getPropertyByName(FieldEventConstant.UP_AC_PROP);
				
				if(appContextParam != null) {
					Configuration appContextConfig = Store.getFromConfigurationStore("applicationContext");
					ArrayList<String> contextParamList = appContextConfig.getPropertyByName("contextparam");
					
					if(contextParamList.contains(appContextParam)) {
						if(eventData instanceof Entity) {
							ApplicationContext.getInstance().setProperty(appContextParam, (Entity)eventData);
						} else {
							ApplicationContext.getInstance().setPropertyByName(appContextParam, (Serializable)eventData);
						}
					}
				}
				
				// TODO Check if fire app event (T/F).
				String appEventToFire = fieldEventConf.getPropertyByName(FieldEventConstant.EVENT_NAME);

				Entity appEvent = new Entity();
				appEvent.setType(new MetaType("EventData"));
				appEvent.setPropertyByName(EventConstant.EVNT_NAME, appEventToFire);
				
				if(eventData instanceof Entity) {
					appEvent.setProperty(EventConstant.EVNT_DATA, (Entity)eventData);
				}
				else {
					appEvent.setPropertyByName(EventConstant.EVNT_DATA, (Serializable)eventData);
				}
				
				JSONObject appEventJson = EntityToJsonClientConvertor.createJsonFromEntity(appEvent);
				
				History.newItem(appEventJson.toString(), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

/*	protected boolean isInterestedFieldEvent(String eventName) {
		HashMap<String, Configuration> interestedFieldEvents = getInterestedFieldEvents();
		Set<String> eventSet = interestedFieldEvents.keySet();
		
		if(!eventSet.isEmpty()) {
			if(eventSet.contains(eventName)) {
				return true;
			}
		}
		return false;
	}*/

	/*protected HashMap<String, Configuration> getInterestedFieldEvents() {
		HashMap<String, Configuration> interestedFieldEvents = new HashMap<String, Configuration>();
		if(getConfigurationValue(HTMLSnippetConstant.HS_FIELDEVENTS) != null) {
			interestedFieldEvents = (HashMap<String, Configuration>) getConfigurationValue(HTMLSnippetConstant.HS_FIELDEVENTS);
		}
		return interestedFieldEvents;
	}*/
	

	/*protected Configuration getFieldEventConfiguration(String event) {
		HashMap<String, Configuration> interestedFieldEvents = getInterestedFieldEvents();
		
		if(!interestedFieldEvents.isEmpty() && interestedFieldEvents.containsKey(event)) {
			return interestedFieldEvents.get(event);
		}
		return null;
	}*/

	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityReceived(Entity entity) {
		if(entity != null) {
			this.entity = entity;
			populateFields();
		}
	}

	@Override
	public void onEntityUpdated(Entity entity) {
		// TODO Auto-generated method stub
		
	}
	

	public HTMLSnippetView getView() {
		return view;
	}

	public void setView(HTMLSnippetView view) {
		this.view = view;
	}
	
	public void removeFieldEventHandler() {
		fieldEventRegistration.removeHandler();
	}

	@Override
	public void onClick(ClickEvent event) {
		/*try {
			Configuration clickConfig = (Configuration) configuration.getProperty("onClick");
			if(clickConfig != null) {
				String pageValue = clickConfig.getPropertyByName("page");
				
				// TODO String localActionValue = clickConfig.getPropertyByName("localAction");
				// String appActionValue = clickConfig.getPropertyByName("appAction");
				
				if(pageValue != null) {
					String page = null;
					if(pageValue.toString().indexOf(".") == -1) {
						page = pageValue;
					} else if(pageValue.toString().startsWith("ent")){
						String entityProp = pageValue.toString().substring(pageValue.toString().indexOf(".") + 1);
						page = entity.getGraphPropertyValue(entityProp, entity);
					}
					if(page != null) {
						String moduleUrl = GWT.getHostPageBaseURL();
						String pageUrl = moduleUrl + page; // + "?gwt.codesvr=127.0.0.1:9997";
						Window.open(pageUrl, "_self", "");
					}
				}
			}
		} catch (EntityGraphException e) {
			e.printStackTrace();
		}*/
		
	}

	public String getType() {
		return getSnippetType();
	}

	public void setType(String type) {
		this.setSnippetType(type);
	}

	public String getInstance() {
		return getSnippetInstance();
	}

	public void setInstance(String instance) {
		this.setSnippetInstance(instance);
	}
	
	public HTMLSnippetModel getModel() {
		return model;
	}


	public void setModel(HTMLSnippetModel model) {
		this.model = model;
	}

	// shift to pagepresenter
	@Override
	public void onActionEvent(ActionEvent event) {
		/*try {
			String eventName = event.getName();
			Object eventData = event.getEventData();
			
			if(eventActionSet != null) {
				EventAction eventAction = eventActionSet.getEventAction(eventName);
				
				processEventAction(eventAction, eventData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	//shift to basepresenter
	private void processEventAction(EventAction eventAction, Object eventData) {
		try {
			boolean transformation = eventAction.hasTransformation();
			
			if(transformation) {
				String transformSnippet = eventAction.getTransformSnippet();
				String snippetInstance = eventAction.getSnippetInstance();

				createAddSnippet(transformSnippet, snippetInstance);
			}
			
			boolean configUpdate = eventAction.hasConfigurationUpdation();
			
			if(configUpdate) {
				 
				HashMap<String, Object> configToUpdateMap = eventAction.getConfigurationToUpdateMap();
				HashMap<String, Object> preparedConfigMap = prepareConfigurationsToUpdate(configToUpdateMap, eventData);
				
				if(preparedConfigMap != null) {
					applyConfiguration(preparedConfigMap);
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void applyConfiguration(HashMap<String, Object> preparedConfigMap) {
		// implementation needs enhancement to handle snippets, components or fields
		/*try {
			
			HTMLSnippetPresenter snippetToUpdate = null;
			
			while(!preparedConfigMap.isEmpty()) {
				for(Map.Entry<String, Object> configToUpdateEntry : preparedConfigMap.entrySet()) {
					String qualifiedProperty = configToUpdateEntry.getKey();
					Object propertyValue = configToUpdateEntry.getValue();
					
					String snippetInstance = qualifiedProperty.substring(0, qualifiedProperty.indexOf("."));
					String propertyToUpdate = qualifiedProperty.substring(qualifiedProperty.indexOf(".") + 1);
					
					if((snippetToUpdate == null && (snippetToUpdate = snippetMap.get(snippetInstance)) != null ) || 
							(snippetToUpdate != null && snippetInstance.equals(snippetToUpdate.getSnippetInstance()))) {
						Configuration configToUpdate = snippetToUpdate.getModel().getConfiguration(snippetInstance);
						configToUpdate.setGraphPropertyValue(propertyToUpdate, (Serializable)propertyValue, null);
						preparedConfigMap.remove(propertyToUpdate);
					}
				}
				snippetToUpdate.load();
				snippetToUpdate = null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	private HashMap<String, Object> prepareConfigurationsToUpdate(HashMap<String, Object> configToUpdateMap, Object eventData) {
		try {
			for(Map.Entry<String, Object> configToUpdateEntry : configToUpdateMap.entrySet()) {
				String propertyToUpdate = configToUpdateEntry.getKey();
				Object propertyValue = configToUpdateEntry.getValue();
				
				if(propertyValue != null) {
					String propStrVal = propertyValue.toString();
					
					if(propStrVal.startsWith(HTMLSnippetConstant.EVENTDATA)) {
						
						if(propStrVal.indexOf(HTMLSnippetConstant.SEPERATOR) == -1) {
							propertyValue = (Serializable) eventData;
						} else {
							String entityProp = propStrVal.substring(propStrVal.indexOf(HTMLSnippetConstant.SEPERATOR) + 1);
							Entity entity = (Entity)eventData;
							propertyValue = entity.getGraphPropertyValue(entityProp, entity);
						}
					} else if(propStrVal.startsWith(HTMLSnippetConstant.APPCONTEXT)) {
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

	private void createAddSnippet(String transformTo, String transFormInstance) {
		try {
	
			if(!snippetMap.isEmpty()) {
				HTMLSnippetPresenter snippetPres = snippetMap.get(transFormInstance);
				if(snippetPres != null) {
					snippetPres.removeFieldEventHandler();;
				}
			}
			
			final HTMLSnippetPresenter snippetPres = snippetGenerator.requestHTMLSnippet(transformTo, transFormInstance);
			snippetPres.create();
			//view.clear();
			//view.add(snippetPres.getView());
			snippetMap.put(transFormInstance, snippetPres);
			snippetPres.load();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	

	private void updateConfig(Configuration configToUpdate, Configuration updateConf, Object eventData) {
		try {
			Set<Entry<String, Property<? extends Serializable>>> confSet = updateConf.getValue().entrySet();
			
			for(Entry<String, Property<? extends Serializable>> entry : confSet) {
				String key = entry.getKey();
				
				Serializable propvalue = entry.getValue().getValue();
				
				if(propvalue != null) {
					String propStrVal = propvalue.toString();
					
					if(propStrVal.startsWith("evt")) {
						
						if(propStrVal.indexOf(".") == -1) {
							propvalue = (Serializable) eventData;
						} else {
							String entityProp = propStrVal.substring(propStrVal.indexOf(".") + 1);
							Entity entity = (Entity)eventData;
							propvalue = entity.getGraphPropertyValue(entityProp, entity);
						}
					} else if(propStrVal.startsWith("ac")) {
						String appContextProp = propStrVal.substring(propStrVal.indexOf(".") + 1);
						propvalue = ApplicationContext.getInstance().getGraphPropertyValue(appContextProp, null);
					}
					if(propvalue instanceof Key) {
						Key keyVal = (Key)propvalue;
						propvalue = keyVal.getKeyValue();
					}
				}
				configToUpdate.setGraphPropertyValue(key, propvalue, null);
			}
		} catch (EntityGraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getSnippetInstance() {
		return snippetInstance;
	}

	public void setSnippetInstance(String snippetInstance) {
		this.snippetInstance = snippetInstance;
	}

	public String getSnippetType() {
		return snippetType;
	}

	public void setSnippetType(String snippetType) {
		this.snippetType = snippetType;
	}
	
	public void refresh() {
		
	}

	
}