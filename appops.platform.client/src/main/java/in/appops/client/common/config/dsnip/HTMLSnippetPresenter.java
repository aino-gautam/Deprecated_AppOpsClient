package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponentPresenter.BaseComponentConstant;
import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.SelectedItem;
import in.appops.client.common.config.model.EntityModel;
import in.appops.client.common.config.util.Store;
import in.appops.client.common.core.EntityReceiver;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.util.EntityToJsonClientConvertor;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityGraphException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class HTMLSnippetPresenter implements Configurable, FieldEventHandler, EntityReceiver, ClickHandler {

	public interface HTMLSnippetConstant {
		String HS_FIELDEVENTS = "interestedFieldEvents";
		String HS_PCLS = "basePrimaryCss";
		String HS_DCLS = "baseDependentCss";
	}
	
	/**
	 * Configuration for snippet.
	 */
	protected Configuration configuration;
	
	/**
	 * View of the snippet.
	 */
	protected HTMLSnippet htmlSnippet;
	
	/**
	 * Model of the snippet.
	 */
	protected EntityModel model;

	/**
	 * Entity bound to a snippet.
	 */
	protected Entity entity;

	protected HandlerRegistration fieldEventRegistration; 
	
	/**
	 * This initialises a snippet w.r.t. the snippet type and instance.
	 * Applies configurations to view and model.
	 */
	public void init() {
		model = new EntityModel();

		if(getModelConfiguration() != null) {
			model.setConfiguration(getModelConfiguration());
			model.configure();
		}

		if(getViewConfiguration() != null) {
			htmlSnippet.setConfiguration(getViewConfiguration());
			htmlSnippet.configure();
		}
		
		model.setReceiver(this);
		htmlSnippet.addClickHandler(this);
		fieldEventRegistration = AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}

	public void load() {
		if(entity != null) {
			populateFields();
		} else {
			model.fetchEntity();
		}
	}
	
	private boolean hasConfiguration(String configKey) {
		if(configuration != null && configuration.getPropertyByName(configKey) != null) {
			return true;
		}
		return false;
	}
	
	private Serializable getConfigurationValue(String configKey) {
		if(hasConfiguration(configKey)) {
			return configuration.getPropertyByName(configKey);
		}
		return null;
	}
	
	protected Configuration getViewConfiguration() {
		if(getConfigurationValue(BaseComponentConstant.BC_CONFIGVIEW) != null) {
			return (Configuration)getConfigurationValue(BaseComponentConstant.BC_CONFIGVIEW);
		}
		return null;
	}

	protected Configuration getModelConfiguration() {
		if(getConfigurationValue(BaseComponentConstant.BC_CONFIGMODEL) != null) {
			return (Configuration)getConfigurationValue(BaseComponentConstant.BC_CONFIGMODEL);
		}
		return null;
	}
	
	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	public HTMLSnippet getHTMLSnippet() {
		return htmlSnippet;
	}

	public void create() {
		htmlSnippet.processSnippetDescription();
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
		htmlSnippet.setEntity(entity);
		
	}

	public void populateFields() {
		
/*		Configuration populateFieldsConfig = configuration.getPropertyByName("populateFields");
		
		//TODO Implement populatefields
		if(populateFieldsConfig != null) {

			Set<Entry<String, Property<? extends Serializable>>> confSet = populateFieldsConfig.getValue().entrySet();
	
			for(Entry<String, Property<? extends Serializable>> entry : confSet) {
				String key = entry.getKey();
	
				Serializable propvalue = entry.getValue().getValue();
				if(propvalue != null) {
					String propStrVal = propvalue.toString();
	
					if(propStrVal.indexOf(".") == -1) {
						propvalue = entry.getValue().getValue();
					}
					else if(propStrVal.startsWith(FldProp)){
						String fieldName = propStrVal.substring(propStrVal.indexOf(".") + 1);
						BaseField field = (BaseField) htmlSnippet.getSnippetElementMap().get(fieldName);
						propvalue = (Serializable) field.getValue();
					}
					entity.setPropertyByName(key, propvalue);
				}
			}
		}*/
		
		Map<String, Widget> snippetEle = htmlSnippet.getSnippetElementMap();
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
		try {
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
					Configuration appContextConfig = Store.getConfiguration("applicationContext");
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
		}
	}

	protected boolean isInterestedFieldEvent(String eventName) {
		HashMap<String, Configuration> interestedFieldEvents = getInterestedFieldEvents();
		Set<String> eventSet = interestedFieldEvents.keySet();
		
		if(!eventSet.isEmpty()) {
			if(eventSet.contains(eventName)) {
				return true;
			}
		}
		return false;
	}

	protected HashMap<String, Configuration> getInterestedFieldEvents() {
		HashMap<String, Configuration> interestedFieldEvents = new HashMap<String, Configuration>();
		if(getConfigurationValue(HTMLSnippetConstant.HS_FIELDEVENTS) != null) {
			interestedFieldEvents = (HashMap<String, Configuration>) getConfigurationValue(HTMLSnippetConstant.HS_FIELDEVENTS);
		}
		return interestedFieldEvents;
	}
	

	protected Configuration getFieldEventConfiguration(String event) {
		HashMap<String, Configuration> interestedFieldEvents = getInterestedFieldEvents();
		
		if(!interestedFieldEvents.isEmpty() && interestedFieldEvents.containsKey(event)) {
			return interestedFieldEvents.get(event);
		}
		return null;
	}

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
	

	public HTMLSnippet getHtmlSnippet() {
		return htmlSnippet;
	}

	public void setHtmlSnippet(HTMLSnippet htmlSnippet) {
		this.htmlSnippet = htmlSnippet;
	}
	
	public void removeFieldEventHandler() {
		fieldEventRegistration.removeHandler();
	}

	@Override
	public void onClick(ClickEvent event) {
		try {
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
		}
		
	}
}