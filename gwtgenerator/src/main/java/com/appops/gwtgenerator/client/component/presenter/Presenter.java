package com.appops.gwtgenerator.client.component.presenter;

import java.util.HashMap;
import java.util.Set;

import com.appops.gwtgenerator.client.config.core.Configurable;
import com.appops.gwtgenerator.client.config.core.Configuration;
import com.google.gwt.user.client.ui.Widget;

// Sample Base presenter
// get and update event rule map
// update configuration
// register unregister events
// get and set view
// it implements configurable
public class Presenter implements Configurable {
	
	private final static String								EVENTRULEMAP	= "event_rule_map";
	
	private Widget											view;
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
	private void initialize() {
		
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
		return view;
	}
	
	public void setView(Widget view) {
		this.view = view;
	}
	
}