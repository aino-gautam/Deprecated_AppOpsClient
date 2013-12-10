package com.appops.gwtgenerator.client.component.presenter;

import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.ui.Widget;

// Sample Base presenter
// get and update event rule map
// update configuration
// register unregister events
// get and set view
// it implements configurable
public class Presenter implements Configurable {
	
	private final static String								EVENTRULEMAP	= "event_rule_map";
	private Configuration									configuration;
	private Widget											view;
	// holds the event rule set
	HashMap<String, Set<Configuration>>						eventRuleSet	= new HashMap<String, Set<Configuration>>();
	HashMap<String, HashMap<String, Set<Configuration>>>	eventRuleMAp	= new HashMap<String, HashMap<String, Set<Configuration>>>();
	
	public Presenter() {
		
	}
	
	public Presenter(Configuration configuration) {
		this.configuration = configuration;
		initialize();
	}
	
	// initialization would be applying the configuration on the view
	// registering or deregisterations of events.
	private void initialize() {
		
	}
	
	// type vs event rule set map.
	public void populate() {
		// Event rule is configuration
		String className = null;
		Configuration configuration = null;
		HashSet someHashSet = new HashSet();
		someHashSet.add(configuration);
		eventRuleSet.put("someRule", someHashSet);
		eventRuleMAp.put(className, eventRuleSet);
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
		Configuration eventRuleMapConf = (Configuration) conf.getConfigurationValue(EVENTRULEMAP);
		
	}
	
	public Widget getView() {
		return view;
	}
	
	public void setView(Widget view) {
		this.view = view;
	}
	
}