package in.appops.client.common.config.dsnip;


import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.component.base.BaseComponentView;
import in.appops.client.common.config.util.Store;
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
 * Container - Acts as a place holder for components and snippets.
 * Listens to Application events.
 */
public class Container extends SimplePanel implements Configurable, ValueChangeHandler<String> {
	
	public interface ContainerConstant {
		/**
		 * 
		 * The value is a map (event Vs. event configuration) of events that the container is interested to listen and handle
		 */
		String CT_INTRSDEVNTS = "interestedEvents";
	}
	
	/** Map containing the snippet instance and the actual snippet added to the container **/
	private Map<String, HTMLSnippetPresenter> snippetMap	= new HashMap<String, HTMLSnippetPresenter>();
	
	/** Map containing the component instance and the actual component added to the container**/
	private Map<String, BaseComponentPresenter> componentMap = new HashMap<String, BaseComponentPresenter>();
	
	/** Container identifier **/
	private String id;
	
	/** Container configuration **/
	private Configuration configuration;
	
	private AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);

	/**
	 * Applies any configuration applicable to the container.
	 */
	public void configure() {
		
		/** Register to listen application events **/
		if(getInterestedEvents() != null && !getInterestedEvents().isEmpty()) {
			History.addValueChangeHandler(this);
		}
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
	
	/**
	 * Called when the container is created and added. Executes the "onLoad" event.
	 */
	@Override
	protected void onLoad() {
		super.onLoad();

		if(isTypeInteresting("onLoad")) {
			Configuration eventConf = getEventConfiguration("onLoad");
			processEvent(eventConf, null);
		}
	}
	
	/**
	 * Processes the event.
	 * An event can optionally transform to a widget and update configuration of a component/snippet and refresh it.
	 *  
	 * @param conf
	 * @param eventData
	 */
	@SuppressWarnings("unchecked")
	private void processEvent(Configuration conf, Object eventData) {
		
		try {
			Boolean isTransformWidget = (Boolean)conf.getPropertyByName("isTransformWidget");
			
			if(isTransformWidget) {
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
			
			// TODO need to do clean up here. After configuration editior is implemented.
			
			if(isUpdateConf != null && isUpdateConf) {
				Configuration updateConfiguration = (Configuration)conf.getPropertyByName("updateConfiguration");

				Set<Entry<String, Property<? extends Serializable>>> updateConfigurationSet = updateConfiguration.getValue().entrySet();
				HashMap<String, Configuration> updateValueMap = new HashMap<String, Configuration>();
				
				for(Entry<String, Property<? extends Serializable>> entry : updateConfigurationSet) {
					String key = entry.getKey();
					Serializable propvalue = entry.getValue().getValue();
					
					String instanceStr = key.substring(0, key.indexOf("."));
					String propertyToUpdate = key.substring(key.indexOf(".") + 1);
					
					Configuration configurationForPropToUpdate = null;
					if(updateValueMap.containsKey(instanceStr)) {
						configurationForPropToUpdate = updateValueMap.get(instanceStr);
						configurationForPropToUpdate.setPropertyByName(propertyToUpdate, propvalue);
					} else {
						configurationForPropToUpdate = new Configuration();
						configurationForPropToUpdate.setPropertyByName(propertyToUpdate, propvalue);
						updateValueMap.put(instanceStr, configurationForPropToUpdate);
					}
				}
				
				Set<Entry<String, Configuration>> updateValueMapSet = updateValueMap.entrySet();
				
				for(Entry<String, Configuration> entry : updateValueMapSet) {

					String configInstance = entry.getKey();
					Configuration updateConfig = entry.getValue();
					
					Configuration configToUpdate = null;
					if(componentMap.get(configInstance) != null) {
						BaseComponentPresenter componentPresenter = componentMap.get(configInstance);
						configToUpdate = componentPresenter.getConfiguration();
						updateConfig(configToUpdate, updateConfig, eventData);
						componentPresenter.load();

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

	private void createAddSnippet(String transformTo, String transFormInstance) {
		try {
			SnippetGenerator snippetGenerator = (SnippetGenerator)injector.getSnippetGenerator();

			if(!snippetMap.isEmpty()) {
				HTMLSnippetPresenter snippetPres = snippetMap.get(transFormInstance);
				if(snippetPres != null) {
					snippetPres.removeFieldEventHandler();;
				}
			}
			
			final HTMLSnippetPresenter snippetPres = snippetGenerator.generateSnippet(transformTo, transFormInstance);

			this.clear();
			this.add(snippetPres.getHTMLSnippet());
			snippetMap.put(transFormInstance, snippetPres);
			
			snippetPres.load();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private void createAddComponent(String transformTo, String transFormInstance) {
		try {
			BaseComponentPresenter compPres = null;
			
			//if(!componentMap.isEmpty() && componentMap.containsKey(transFormInstance)) {
			//	compPres = componentMap.get(transFormInstance);
			//} else {
				ComponentFactory componentFactory = injector.getComponentFactory();
				compPres = componentFactory.getComponent(transformTo);
			//}
			
			Configuration componentConfig = Store.getConfiguration(transFormInstance);
			compPres.setConfiguration(componentConfig);
			compPres.initialize();
			compPres.configure();
			
			compPres.load();
			BaseComponentView component = compPres.getView();
			this.clear();

			this.add(component);
			componentMap.put(transFormInstance, compPres);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int getTransformType() {
		int transformType = EventConstant.EVNT_SNIPPET;
		if(getConfigurationValue(EventConstant.EVNT_TRANSTYPE) != null) {
			transformType = Integer.parseInt(getConfigurationValue(EventConstant.EVNT_TRANSTYPE).toString());
		}
		return transformType;
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
	
	@SuppressWarnings({ "rawtypes" })
	protected HashMap getInterestedEvents() {
		HashMap interestedEvents = new HashMap();
		if(getConfigurationValue(ContainerConstant.CT_INTRSDEVNTS) != null) {
			Configuration interestedEventConfig = (Configuration) getConfigurationValue(ContainerConstant.CT_INTRSDEVNTS); 
			interestedEvents = (HashMap) interestedEventConfig.getValue();
		}
		return interestedEvents;
	}
	
	
	@SuppressWarnings({"rawtypes" })
	public boolean isTypeInteresting(String eventName) {
		HashMap interestedEvents = getInterestedEvents();
		@SuppressWarnings("unchecked")
		Set<String> eventSet = interestedEvents.keySet();
		
		if(!eventSet.isEmpty()) {
			if(eventSet.contains(eventName)) {
				return true;
			}
		}
		return false;
	}
	
	private Configuration getEventConfiguration(String event) {
		@SuppressWarnings("rawtypes")
		HashMap interestedEvents = getInterestedEvents();
		
		if(!interestedEvents.isEmpty() && interestedEvents.containsKey(event)) {
			return (Configuration) interestedEvents.get(event);
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