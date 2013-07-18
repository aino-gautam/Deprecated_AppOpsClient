package in.appops.client.common.config.dsnip;


import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.component.base.BaseComponentView;
import in.appops.client.common.config.util.Configurator;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The form is the base container of all the snippets. All components will be added to the form.
 * Form will have the reference of all the components that are added.
 * The events that will be fired by the components will be listened to by the form
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
	
	
/*	public void create(Element seedElement) {
renderedFieldMap.clear();
		
		String formDesc = seedElement.getInnerHTML();
		seedElement.setInnerHTML("");

		this.getElement().setId(seedElement.getId());
		id = seedElement.getId();
		HTMLPanel hp = new HTMLPanel(formDesc);
		this.add(hp);
	}*/
	
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
			processEvent(eventConf, "1");
		}
		
	}
	
	private void processEvent(Configuration conf, Object eventData) {
		Integer eventType = Integer.parseInt(conf.getPropertyByName(EventConstant.EVNT_TYPE).toString());
		if(eventType == EventConstant.EVNT_TRANSWGT) {
			AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
			
			String transformTo = conf.getPropertyByName(EventConstant.EVNT_TRANSTO).toString();
			String transFormInstance = conf.getPropertyByName(EventConstant.EVNT_TRANSINS).toString();
			
			Integer transFormType = Integer.parseInt(conf.getPropertyByName(EventConstant.EVNT_TRANSTYPE).toString());
			if(transFormType == EventConstant.EVNT_COMPONENT) {
				ComponentFactory componentFactory = injector.getComponentFactory();
				
				if(!componentMap.isEmpty()) {
					BaseComponentPresenter prevComponent = componentMap.get(transformTo);
					if(prevComponent != null) {
						prevComponent.removeEventHandler();
					}
				}
				
				BaseComponentPresenter compPres = componentFactory.getComponent(transformTo);

				Configuration compConfig = Configurator.getConfiguration(transFormInstance);
				compPres.setConfiguration(compConfig);
				
				compPres.init();
				BaseComponentView component = compPres.getView();
				this.clear();

				this.add(component);
				componentMap.put(transformTo, compPres);

				compPres.load();
				
			} else if(getTransformType() == EventConstant.EVNT_SNIPPET) {
				SnippetGenerator snippetGenerator = (SnippetGenerator)injector.getSnippetGenerator();
				
				if(!snippetMap.isEmpty()) {
					HTMLSnippetPresenter prevSnippet = snippetMap.get(transformTo);
					if(prevSnippet != null) {
						prevSnippet.removeEventHandler();
					}
				}
				
				final HTMLSnippetPresenter snippetPres = snippetGenerator.generateSnippet(transformTo, transFormInstance);
				if(eventData != null) {
					snippetPres.setEntityId(Long.parseLong(eventData.toString()));
				}
				this.clear();
				this.add(snippetPres.getHTMLSnippet());
				snippetMap.put(transformTo, snippetPres);
				
				snippetPres.load();
			}

		} else if(getEventType() == EventConstant.EVNT_EXECOP) {
			
		}
	}

	private String getTransformInstance() {
		String transformInstance = null;
		if(getConfigurationValue(EventConstant.EVNT_TRANSINS) != null) {
			transformInstance = getConfigurationValue(EventConstant.EVNT_TRANSINS).toString();
		}
		return transformInstance;
	}

	private String getTransformTo() {
		String transformTo = null;
		if(getConfigurationValue(EventConstant.EVNT_TRANSTO) != null) {
			transformTo = getConfigurationValue(EventConstant.EVNT_TRANSTO).toString();
		}
		return transformTo;
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
			processEvent(eventConf, entityIdVal);
		}
		
 	}
	
}
