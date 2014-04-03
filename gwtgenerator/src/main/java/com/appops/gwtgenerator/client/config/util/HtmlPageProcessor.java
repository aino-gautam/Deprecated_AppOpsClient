package com.appops.gwtgenerator.client.config.util;

import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.appops.gwtgenerator.client.component.factory.CoreComponentFactory;
import com.appops.gwtgenerator.client.config.cache.ConfigCache;
import com.appops.gwtgenerator.client.config.cache.SnippetCache;
import com.appops.gwtgenerator.client.generator.Driver;
import com.appops.gwtgenerator.client.generator.Dynamic;
import com.appops.gwtgenerator.client.generator.DynamicInstantiator;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class HtmlPageProcessor {

	private static final String DRIVER = "driver";
	private static final String NODE = "Node";
	private static final String SNIPPET = "HtmlPanel";
	Logger logger = Logger.getLogger("HTMLPROCESSOR");
	Element bodyElement;

	public HtmlPageProcessor() {
		bodyElement = RootPanel.getBodyElement();
	}

	public void processPageDescription() throws Exception {
		this.processHtmlDesc(null, bodyElement, null);
	}

	/**
	 * This method processes all the container span elements in the html page an substitute them with containers
	 * widgets.
	 */
	public void processHtmlDesc(Snippet parent, Node element, Driver parentDriver) throws Exception {
		try {
			NodeList<Node> nodeList = element.getChildNodes();
			Window.alert("Node list received");

			int lengthOfNodes = nodeList.getLength();
			for (int i = lengthOfNodes - 1; i > -1; i--) {
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				Snippet parent1 = null;
				Driver driver = null;

				// Iterating through the all tags
				Node node = nodeList.getItem(i);
				System.out.println("Parent--->" + node.getNodeName());

				//check if its a custom node
				if (isCustomTag(node)) {
					processCustomTag(parent, element, node, parentDriver);
					logger.log(Level.SEVERE, node.getNodeName()+"--tag proccessed");
				}
				else if (isSnippetTag(node)) {
					// check if its a snippet
					hashMap = processSnippetTag(node);
					parent1 = (Snippet) hashMap.get(SNIPPET);
					//System.out.println("basepanel is attached?=> " + parent1.isAttached());
					node = (Node) parent1.getElement();
					Window.alert("Snippet proccessed");

				}
				if (node.hasChildNodes()) {
					driver = (Driver) hashMap.get(DRIVER);
					if (driver == null)
						driver = parentDriver;

					if (parent1 == null)
						parent1 = parent;

					processHtmlDesc(parent1, node, (Driver) driver);
				}
				if (driver != null && driver != parentDriver) {
					driver.initialize();
				}
				if (parent1 != null) {
					parent1.isAttached();
				}
			}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "processor excep", e);
			e.printStackTrace();
			throw e;
		}
	}

	private HashMap<String, Object> processSnippetTag(Node node) throws Exception {
		// custom attributes can snippetID
		System.out.println("Snippet processing:" + node.getNodeName());

		String html = null;
		String snippetId = getSnippetId(node);

		if (snippetId != null) {
			html = SnippetCache.getSnippetDesc(snippetId);
			if (html == null)
				html = ((Element) node).getInnerHTML();
		}
		else {
			html = ((Element) node).getInnerHTML();
		}

		Snippet snippetHtmlPanel = new Snippet(html, null);
		// replaces the custom html with the newly created html panel on the page, this is done in order to have a reference to snippet instance.
		replaceChild(node, snippetHtmlPanel);
		// attaches the new blank snippet to the DOM
		snippetHtmlPanel.attach();
		String componentID = getId(node);
		snippetHtmlPanel.setID(componentID);

		String configID = getConfigId(node);
		if (configID != null) {
			Configuration configuration = getConfig(configID);
			if (configuration != null) {
				snippetHtmlPanel.setConfiguration(configuration);
				snippetHtmlPanel.initialize();
			}
		}

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put(NODE, node);
		hashMap.put(SNIPPET, snippetHtmlPanel);

		String driverClass = getSnippetDriverClassName(node);

		if (driverClass != null) {
			DynamicInstantiator dynamicInstantiator = GWT.create(DynamicInstantiator.class);
			Driver driver = dynamicInstantiator.getDriverInstance(driverClass);
			if (driver != null)
				hashMap.put(DRIVER, driver);
		}
		return hashMap;
	}

	private void replaceChild(Node node, Dynamic dynamic) {
		Node parentNode = node.getParentNode();
		Widget widget = (Widget) dynamic;
		parentNode.replaceChild(widget.getElement(), node);
	}

	private Configuration getConfig(String configID) throws Exception {
		Configuration config = ConfigCache.getConfig(configID);
		return config;
	}

	private void processCustomTag(Snippet parentSnippet, Node parentnode, Node node, Driver driver) throws Exception {
		String name = node.getNodeName();

		if (name.matches(/*"[A-Z]+:*/"[A-Z]+")) {
			Dynamic dynamic = CoreComponentFactory.getTagInstance("core:" + node.getNodeName());
			Widget widget = (Widget) dynamic;
			String id = getTagId(node);
			if (id != null && driver != null) {
				driver.updateReference(id, widget);
			}

			String configID = getConfigId(node);
			if (configID != null) {
				Configuration configuration = getConfig(configID);
				if (configuration != null) {
					dynamic.setConfiguration(configuration);
					dynamic.initialize();
				}
			}
			// initialize would process the configuration object and update the view. also it will (de)register handlers for events.

			if (parentSnippet != null) {
				parentSnippet.addAndReplaceElement(widget, (Element) node);
			}
			else replaceChild(node, dynamic);

			dynamic.attach();
			dynamic.setParentSnippet(parentSnippet);

			String componentID = getId(node);
			dynamic.setID(componentID);
			parentSnippet.addChild(componentID, dynamic);

			// temp for testing.
			ArrayList parameters = new ArrayList();
			parameters.add("My String");
			dynamic.im("setText", parameters);

		}
	}

	private String getId(Node node) {
		Element element = Element.as(node);
		if (element.hasAttribute("id")) {
			return element.getAttribute("id");
		}
		return null;
	}

	private String getTagId(Node node) {
		Element element = (Element) node;
		if (element.hasAttribute("id")) {
			return element.getAttribute("id");
		}
		return null;
	}

	private String getConfigId(Node node) {
		Element element = Element.as(node);
		if (element.hasAttribute("configid")) {
			return element.getAttribute("configid");
		}
		return null;
	}

	private Boolean isCustomTag(Node node) {
		try {
			Element element = (Element) node;
			if (element.hasAttribute("isCustom"))
				return true;
			return false;
		}
		catch (Exception e) {
			return false;
		}

		/*
		String name = node.getNodeName();
		logger.log(Level.SEVERE, name);
		if (name.matches("[A-Z]+:[A-Z]+"))
			return true;
		return false;*/
	}

	private Boolean isSnippetTag(Node node) {
		String name = node.getNodeName();
		if (name.equalsIgnoreCase("Snippet"))
			return true;
		return false;
	}

	private String getSnippetId(Node node) {
		Element element = (Element) node;
		if (element.hasAttribute("snippetid")) {
			return element.getAttribute("snippetid");
		}
		return null;
	}

	private String getSnippetDriverClassName(Node node) {
		Element element = (Element) node;
		if (element.hasAttribute("driver")) {
			return element.getAttribute("driver");
		}
		return null;
	}
}
