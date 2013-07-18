package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponentView.BaseComponentConstant;
import in.appops.client.common.config.dsnip.Container.ContainerConstant;
import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.model.EntityModel;
import in.appops.client.common.config.util.Configurator;
import in.appops.client.common.config.util.ReusableSnippetStore;
import in.appops.client.common.core.EntityReceiver;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class HTMLSnippetPresenter implements Configurable, ValueChangeHandler<String>{
	
	protected Configuration configuration;
	
	protected HTMLSnippet htmlSnippet;
	protected EntityModel model;

	private String snippetType;
	private String snippetInstance;
	
	private Entity entity;
	private Long entityId;
	private HandlerRegistration historyRegistration;
	
	public void init() {
		model = new EntityModel();

		String snippetDesc = ReusableSnippetStore.getSnippetDesc(snippetType);
		htmlSnippet = new HTMLSnippet(snippetDesc);

		configuration = Configurator.getConfiguration(snippetInstance);
		if(getModelConfiguration() != null) {
			model.setConfiguration(getModelConfiguration());
			model.configure();
		}

		if(getViewConfiguration() != null) {
			htmlSnippet.setConfiguration(getViewConfiguration());
		}
		historyRegistration = History.addValueChangeHandler(this);
	}

	public void load() {
		/*if(getModelConfiguration() != null) {
			if(entity != null) {
				populateFields();
			} else if(entity == null && entityId != null) {
				model.fetchEntity(entityId, this);
			} 
		}*/
		if(entity != null) {
			populateFields();
		} else if(entity == null && entityId != null) {
			
			Map<String, Serializable> parameterMap = new HashMap<String, Serializable>();
			parameterMap.put("articleId", entityId);
			if(isTypeInteresting("onLoad")) {
				Configuration eventConf = getEventConfiguration("onLoad");
				processEvent(eventConf, parameterMap);
			}
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

	public String getSnippetType() {
		return snippetType;
	}

	public void setSnippetType(String snippetType) {
		this.snippetType = snippetType;
	}

	public String getSnippetInstance() {
		return snippetInstance;
	}

	public void setSnippetInstance(String snippetInstance) {
		this.snippetInstance = snippetInstance;
	}

	public void create() {
		htmlSnippet.processSnippetDescription();
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	
	public void populateFields() {
		Map<String, Widget> snippetEle = htmlSnippet.getSnippetElement();
		for (Map.Entry<String, Widget> elementEntry : snippetEle.entrySet()) {
			Widget element = elementEntry.getValue();
	    	if(element instanceof BaseField) {
	    		BaseField baseField = (BaseField)element;
	    		
				Key key = (Key)entity.getPropertyByName("id");
				Long id = (Long)key.getKeyValue();
				baseField.setBindId(id);

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
	public void onValueChange(ValueChangeEvent<String> event) {
		String evntToken = event.getValue();
		String[] tokenSplitter = evntToken.split("&&");
		String eventName = tokenSplitter[0].substring(tokenSplitter[0].indexOf("=") + 1);
		// Should be some map of data.
		String entityIdVal = null;
		if(tokenSplitter.length > 1) { 
			entityIdVal = tokenSplitter[1].substring(tokenSplitter[1].indexOf("=") + 1);
		}
		
		if(isTypeInteresting(eventName)) {
			Configuration eventConf = getEventConfiguration(eventName);
			if(eventName.equalsIgnoreCase("onSubmit")) {
				onSubmit(eventConf);
			} else {
				processEvent(eventConf, entityIdVal);
			}
		}
		
 	}
	
	private void onSubmit(Configuration eventConf) {
		Map<String, Widget> snippetEle = htmlSnippet.getSnippetElement();
		
		String entType = eventConf.getPropertyByName(EventConstant.EVNT_ENTITYTYPE).toString();
		
		if(entity == null) {
			entity = new Entity();
		}
		
		entity.setType(new MetaType(entType));
		
		for (Map.Entry<String, Widget> elementEntry : snippetEle.entrySet()) {
			Widget element = elementEntry.getValue();
	    	if(element instanceof BaseField) {
	    		BaseField baseField = (BaseField)element;
	    		
				if(baseField.isFieldVisible()) {
				
		    		String bindProp = baseField.getBindProperty();
		    		Serializable value = (Serializable)baseField.getValue();
		    		if(bindProp != null && value != null) {
		    			entity.setPropertyByName(bindProp, value);
		    		}
				}
	    	}
		}
		Map<String, Serializable> parameterMap = new HashMap<String, Serializable>();
		parameterMap.put("articleEntity",entity);
		processEvent(eventConf, parameterMap);
		
	}

	@SuppressWarnings("unchecked")
	protected HashMap<String, Configuration> getInterestedEvents() {
		HashMap<String, Configuration> interestedEvents = new HashMap<String, Configuration>();
		if(getConfigurationValue(ContainerConstant.CT_INTRSDEVNTS) != null) {
			interestedEvents = (HashMap<String, Configuration>) getConfigurationValue(ContainerConstant.CT_INTRSDEVNTS);
		}
		return interestedEvents;
	}
	
	
	public boolean isTypeInteresting(String eventName) {
		HashMap<String, Configuration> interestedEvents = getInterestedEvents();
		Set<String> eventSet = interestedEvents.keySet();
		
		if(!eventSet.isEmpty()) {
			if(eventSet.contains(eventName)) {
				return true;
			}
		}
		return false;
	}
	
	private Configuration getEventConfiguration(String event) {
		HashMap<String, Configuration> interestedEvents = getInterestedEvents();
		
		if(!interestedEvents.isEmpty() && interestedEvents.containsKey(event)) {
			return interestedEvents.get(event);
		}
		return null;
	}
	
	private void processEvent(final Configuration conf, Object eventData) {
		Integer eventType = Integer.parseInt(conf.getPropertyByName(EventConstant.EVNT_TYPE).toString());
		
		if(eventType == EventConstant.EVNT_TRANSWGT) {

		} else if(eventType == EventConstant.EVNT_EXECOP) {
			String operation = conf.getPropertyByName(EventConstant.EVNT_OPERATION).toString();
			
			model.executeOperation(operation, (HashMap<String, Serializable>)eventData, new EntityReceiver() {

				@Override
				public void noMoreData() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onEntityReceived(Entity ent) {
					String afterEvent = null;
					if(conf.getPropertyByName(EventConstant.EVNT_AFTEREVENT) != null) {
						afterEvent = conf.getPropertyByName(EventConstant.EVNT_AFTEREVENT).toString();
						
						Key key = (Key)ent.getPropertyByName("id");
						Long id = (Long)key.getKeyValue();
						afterEvent = afterEvent + id.toString();
						History.newItem(afterEvent, true);
					} else {
						if(ent != null) {
							entity = ent;
							populateFields();
						}
					}
				}

				@Override
				public void onEntityUpdated(Entity entity) {
					// TODO Auto-generated method stub
					
				}
			} );
			
		}
	}

	public void removeEventHandler() {
		historyRegistration.removeHandler();
	}
	
	
}
