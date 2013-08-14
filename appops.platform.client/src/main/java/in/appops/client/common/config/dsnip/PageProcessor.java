package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.util.Configurator;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class PageProcessor extends HTMLPanel {

	private ArrayList<Container> placeholders = new ArrayList<Container>();
	
	public PageProcessor(String htmlDesc) {
		super(htmlDesc);
	}
	
	public void processPageDescription() {
		try {
			// The root <span> element i.e. <span id="bookATableForm" widgetContainerType="form"/> 
			
				NodeList<Element> nodeList = this.getElement().getElementsByTagName("span");
				
				int lengthOfNodes = nodeList.getLength();
				for (int i = lengthOfNodes - 1; i > -1; i--) { // Iterating through the <span> elements
					Node node = nodeList.getItem(i);
					Element pageSpan = Element.as(node);
					pageSpan.setId(pageSpan.getId());
					if (pageSpan != null) {
						if (pageSpan.hasAttribute("widgetContainerType") && pageSpan.getAttribute("widgetContainerType").equals("placeholder")) {
							Container container = new Container();
							
							String dataConfig = pageSpan.getAttribute("data-config");
							Configuration configuration =  Configurator.getConfiguration(dataConfig);
							container.setConfiguration(configuration);
							container.configure();
							this.addAndReplaceElement(container, pageSpan);
							placeholders.add(container);
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
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
			toReplace.getParentNode().replaceChild(widget.getElement(),	toReplace);
		} else {
			toReplace.getParentNode().insertBefore(widget.getElement(),	toReplace);
			remove(toRemove);
		}

		adopt(widget);
	}
	
	public void loadPage() {
		for (Container container : placeholders) {
			container.onLoad();
		}
	}
	
}
