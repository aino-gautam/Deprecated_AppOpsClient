package in.appops.client.common.config.dsnip;


import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.component.base.BaseComponentView;
import in.appops.client.common.config.util.Configurator;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.client.common.util.JsonToEntityConverter;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityGraphException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * TODO
 */
public class Container extends SimplePanel implements Configurable, ValueChangeHandler<String> {
	
	public interface ContainerConstant {
		String CT_INTRSDEVNTS = "interestedEvents";
	}
	
	private Map<String, HTMLSnippetPresenter> snippetMap	= new HashMap<String, HTMLSnippetPresenter>();
	private Map<String, BaseComponentPresenter> componentMap = new HashMap<String, BaseComponentPresenter>();
	private String id;
	private Configuration configuration;
	private AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);

	public void configure() {
	    History.addValueChangeHandler(this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	protected void onLoad() {
		super.onLoad();

		if(isTypeInteresting("onLoad")) {
			Configuration eventConf = getEventConfiguration("onLoad");
			processEvent(eventConf, null);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processEvent(Configuration conf, Object eventData) {
		
		try {
			Boolean isTransformWidget = (Boolean)conf.getPropertyByName("isTransformWidget");
			Configuration configuration = null;
			
			if(isTransformWidget) {
				AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
				String transformTo = conf.getPropertyByName(EventConstant.EVNT_TRANSTO).toString();
				String transFormInstance = conf.getPropertyByName(EventConstant.EVNT_TRANSINS).toString();
				Integer transFormType = Integer.parseInt(conf.getPropertyByName(EventConstant.EVNT_TRANSTYPE).toString());
				
				if(transFormType == EventConstant.EVNT_COMPONENT) {
					createAddComponent(transformTo, transFormInstance);
				} else if(getTransformType() == EventConstant.EVNT_SNIPPET) {
					createAddSnippet(transformTo, transFormInstance);
				}
			}
			
			Boolean isUpdateConf = (Boolean)conf.getPropertyByName("isUpdateConfiguration");
			
			if(isUpdateConf != null && isUpdateConf) {
				HashMap<String, Configuration> configToUpdateMap = (HashMap<String, Configuration>)conf.getPropertyByName("configToUpdate");
				
				Set<Entry<String, Configuration>> confSet = configToUpdateMap.entrySet();
				
				for(Entry<String, Configuration> entry : confSet) {
					String configInstance = entry.getKey();
					Configuration updateConfig = entry.getValue();
					
					Configuration configToUpdate = null;
					if(componentMap.get(configInstance) != null) {
						BaseComponentPresenter componentPresenter = componentMap.get(configInstance);
						configToUpdate = componentPresenter.getConfiguration();
						updateConfig(configToUpdate, updateConfig, eventData);
						componentPresenter.init();

					} else if(snippetMap.get(configInstance) != null) {
						HTMLSnippetPresenter snippetPresenter = snippetMap.get(configInstance);
						configToUpdate = snippetPresenter.getConfiguration();
						updateConfig(configToUpdate, updateConfig, eventData);
						snippetPresenter.load();
						
					}
					
				}
			}
			
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
					String propStrVal = entry.getValue().getValue().toString();
					
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

	private void createAddSnippet(String transformTo, String transFormInstance) {
		SnippetGenerator snippetGenerator = (SnippetGenerator)injector.getSnippetGenerator();

		if(!snippetMap.isEmpty()) {
			HTMLSnippetPresenter prevSnippet = snippetMap.get(transformTo);
			if(prevSnippet != null) {
				prevSnippet.removeEventHandler();
			}
		}
		
		final HTMLSnippetPresenter snippetPres = snippetGenerator.generateSnippet(transformTo, transFormInstance);

		this.clear();
		this.add(snippetPres.getHTMLSnippet());
		snippetMap.put(transformTo, snippetPres);
		
		snippetPres.load();		
	}

	private void createAddComponent(String transformTo, String transFormInstance) {
		ComponentFactory componentFactory = injector.getComponentFactory();
		
		if(!componentMap.isEmpty()) {
			BaseComponentPresenter prevComponent = componentMap.get(transformTo);
			if(prevComponent != null) {
				prevComponent.removeEventHandler();
			}
		}
		
		BaseComponentPresenter compPres = componentFactory.getComponent(transformTo);

		configuration = Configurator.getConfiguration(transFormInstance);
		compPres.setConfiguration(configuration);
		compPres.configure();
		
		compPres.init();
		BaseComponentView component = compPres.getView();
		this.clear();

		this.add(component);
		componentMap.put(transformTo, compPres);
	}

	private int getTransformType() {
		int transformType = EventConstant.EVNT_SNIPPET;
		if(getConfigurationValue(EventConstant.EVNT_TRANSTYPE) != null) {
			transformType = Integer.parseInt(getConfigurationValue(EventConstant.EVNT_TRANSTYPE).toString());
		}
		return transformType;
	}

	private int getEventType() {
		int eventType = EventConstant.EVNT_TRANSWGT;
		if(getConfigurationValue(EventConstant.EVNT_TYPE) != null) {
			eventType = Integer.parseInt(getConfigurationValue(EventConstant.EVNT_TYPE).toString());
		}
		return eventType;
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
	
	public void onValueChange(ValueChangeEvent<String> event) {
		String appEventJson = event.getValue();
		
		Entity appEvent = new JsonToEntityConverter().convertjsonStringToEntity(appEventJson);
		
		String eventName = appEvent.getPropertyByName(EventConstant.EVNT_NAME);
		// Should be some map of data.
		Object eventData = appEvent.getPropertyByName(EventConstant.EVNT_DATA);
		
		if(isTypeInteresting(eventName)) {
			Configuration eventConf = getEventConfiguration(eventName);
			
			processEvent(eventConf, eventData);
		}
 	}
	
}
