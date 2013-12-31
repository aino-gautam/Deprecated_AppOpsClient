package com.appops.gwtgenerator.client.component.presenter;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.appops.gwtgenerator.client.generator.Dynamic;
import com.google.gwt.user.client.ui.Widget;

// Sample Base presenter
// get and update event rule map
// update configuration
// register unregister events
// get and set view
// it implements configurable
public class Presenter implements Configurable {
	
	private final static String								EVENTRULEMAP	= "event_rule_map";
	
	private Dynamic											view;
	// holds the event rule set
	HashMap<String, Set<Configuration>>						eventRuleSet	= new HashMap<String, Set<Configuration>>();
	HashMap<String, HashMap<String, Set<Configuration>>>	eventRuleMAp	= new HashMap<String, HashMap<String, Set<Configuration>>>();
	private Configuration									configuration;
	
	public Presenter(Configuration configuration) {
		this.configuration = configuration;
		initialize();
	}
	
	public Presenter() {
		// TODO Auto-generated constructor stub
	}
	
	// initialization would be applying the configuration on the view
	// registering or deregisterations of events.
	public void initialize() {
		if (configuration != null) {
			Configuration viewConfig = configuration.getPropertyByName("view");
			for (Entry<String, Property<? extends Serializable>> entry : viewConfig.getValue().entrySet()) {
				String propName = (String) entry.getKey();
				Property<? extends Serializable> prop = entry.getValue();
				if (prop instanceof Entity) {
					
				}
				else {
					try {
						ArrayList<Serializable> parameters = new ArrayList<Serializable>();
						parameters.add(prop.getValue());
						view.im("set" + propName, parameters);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	// type vs event rule set map.
	public void populate() {
		
	}
	
	@Override
	public Configuration getConfiguration() {
		return configuration;
	}
	
	@Override
	public void setConfiguration(Configuration conf) {
		configuration = conf;
		updateEventRuleMap(conf);
	}
	
	private void updateEventRuleMap(Configuration conf) {
		//Configuration eventRuleMapConf = (Configuration) conf.getConfigurationValue(EVENTRULEMAP);
		
	}
	
	public Widget getView() {
		return (Widget) view;
	}
	
	public void setView(Widget view) {
		this.view = (Dynamic) view;
	}
	
}