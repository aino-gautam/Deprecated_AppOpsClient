package com.appops.gwtgenerator.client.components.presenter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.web.bindery.autobean.vm.Configuration;
//Sample Base presenter 
public class BasePresenter {
	// holds the event rule set
	HashMap<String, Set<Configuration>> eventRuleSet = new HashMap<String, Set<Configuration>>();
	HashMap<String, HashMap<String, Set<Configuration>>> eventRuleMAp = new HashMap<String, HashMap<String, Set<Configuration>>>();

	public BasePresenter() {
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
}
