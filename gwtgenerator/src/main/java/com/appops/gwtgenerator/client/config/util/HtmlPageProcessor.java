package com.appops.gwtgenerator.client.config.util;

import java.util.ArrayList;

import in.appops.platform.core.shared.Configuration;

import com.appops.gwtgenerator.client.component.factory.CoreComponentFactory;
import com.appops.gwtgenerator.client.component.presenter.Presenter;
import com.appops.gwtgenerator.client.config.ContextConfigurationManager;
import com.appops.gwtgenerator.client.config.cache.ConfigCache;
import com.appops.gwtgenerator.client.config.cache.SnippetCache;
import com.appops.gwtgenerator.client.generator.Dynamic;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class HtmlPageProcessor {
	Element	bodyElement;
	
	public HtmlPageProcessor() {
		bodyElement = RootPanel.getBodyElement();
	}
	
	public void processPageDescription() throws Exception {
		this.processHtmlDesc(bodyElement);
	}
	
	/**
	 * This method processes all the container span elements in the html page an substitute them with containers widgets.
	 */
	public void processHtmlDesc(Node element) throws Exception {
		try {
			NodeList<Node> nodeList = element.getChildNodes();
			
			int lengthOfNodes = nodeList.getLength();
			for (int i = lengthOfNodes - 1; i > -1; i--) { // Iterating through the all tags
				Node node = nodeList.getItem(i);
				System.out.println("Parent--->" + node.getNodeName());
				
				//check if its a custom node
				if (isCustomTag(node)) {
					processCustomTag(node);
				}
				else if (isSnippetTag(node)) {
					// check if its a snippet
					node = processSnippetTag(node);
				}
				if (node.hasChildNodes())
					processHtmlDesc(node);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private Node processSnippetTag(Node node) throws Exception {
		// custom attributes can snippetID
		String html = null;
		String snippetId = getSnippetId(node);
		if (snippetId != null) {
			html = SnippetCache.getSnippetDesc(snippetId);
		}
		else {
			html = ((Element) node).getInnerHTML();
		}
		DOM dom = new DOM();
		Element element = dom.createDiv();
		element.setInnerHTML(html);
		Node parentNode = node.getParentNode();
		parentNode.replaceChild(element, node);
		return element;
	}
	
	private Configuration getConfig(String configID) throws Exception {
		Configuration config = ConfigCache.getConfig(configID);
		return config;
	}
	
	private void processCustomTag(Node node) throws Exception {
		String name = node.getNodeName();
		
		if (name.matches("[A-Z]+:[A-Z]+")) {
			Dynamic dynamic = CoreComponentFactory.getTagInstance(node.getNodeName());
			Widget widget = (Widget) dynamic;
			Presenter presenter = GWT.create(Presenter.class);
			dynamic.setPresenter(presenter);
			presenter.setView(widget);
			
			String configID = getConfigId(node);
			if (configID != null) {
				Configuration configuration = getConfig(configID);
				presenter.setConfiguration(configuration);
				presenter.initialize();
			}
			// initialize would process the configuration object and update the view. also it will (de)register handlers for events.
			presenter.initialize();
			node.getParentNode().replaceChild(widget.getElement(), node);
			
			// temp for testing.
			ArrayList parameters = new ArrayList();
			parameters.add("My String");
			dynamic.im("setText", parameters);
		}
	}
	
	private String getConfigId(Node node) {
		Element element = Element.as(node);
		if (element.hasAttribute("configid")) {
			return element.getAttribute("configid");
		}
		return null;
	}
	
	private Boolean isCustomTag(Node node) {
		String name = node.getNodeName();
		if (name.matches("[A-Z]+:[A-Z]+"))
			return true;
		return false;
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
}
