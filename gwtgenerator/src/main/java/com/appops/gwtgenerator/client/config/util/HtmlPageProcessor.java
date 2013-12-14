package com.appops.gwtgenerator.client.config.util;

import com.appops.gwtgenerator.client.component.factory.CoreComponentFactory;
import com.appops.gwtgenerator.client.generator.Dynamic;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class HtmlPageProcessor {
	String	bodyHtml;
	Element	bodyElement;
	
	public HtmlPageProcessor() {
		bodyElement = RootPanel.getBodyElement();
		bodyHtml = bodyElement.getInnerHTML();
	}
	
	/**
	 * This method processes all the container span elements in the html page an substitute them with containers widgets.
	 */
	public void processPageDescription() {
		try {
			NodeList<Node> nodeList = bodyElement.getChildNodes();
			
			int lengthOfNodes = nodeList.getLength();
			for (int i = lengthOfNodes - 1; i > -1; i--) { // Iterating through the all tags
				Node node = nodeList.getItem(i);
				System.out.println("Parent--->" + node.getNodeName());
				String name = node.getNodeName();
				if (name.matches("[A-Z]+:[A-Z]+")) {
					Dynamic dynamic = CoreComponentFactory.getTagInstance(node.getNodeName());
					Widget widget = (Widget) dynamic;
					node.getParentNode().replaceChild(widget.getElement(), node);
					Object[] parameters = { "My String" };
					dynamic.im("setText", parameters);
				}
				
				if (node.hasChildNodes()) {
					
					NodeList<Node> childnodeList = node.getChildNodes();
					int length = childnodeList.getLength();
					for (int j = length - 1; j > -1; j--) { // Iterating through the all tags
						Node node1 = nodeList.getItem(i);
						System.out.println(node1.getNodeName());
					}
					//Element tags = Element.as(node);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*@Override
	public void addAndReplaceElement(Widget widget, com.google.gwt.user.client.Element toReplace) {
		if (toReplace == widget.getElement()) {
			return;
		}
		widget.removeFromParent();
		Widget toRemove = null;
		Iterator<Widget> children = getChildren().iterator();
		while (children.hasNext()) {
			Widget next = children.next();
			if (toReplace.isOrHasChild(next.getElement())) {
				if (next.getElement() == toReplace) {
					toRemove = next;
					break;
				}
				children.remove();
			}
		}
		getChildren().add(widget);
		if (toRemove == null) {
			toReplace.getParentNode().replaceChild(widget.getElement(), toReplace);
		}
		else {
			toReplace.getParentNode().insertBefore(widget.getElement(), toReplace);
			remove(toRemove);
		}
		adopt(widget);
	}*/
}
