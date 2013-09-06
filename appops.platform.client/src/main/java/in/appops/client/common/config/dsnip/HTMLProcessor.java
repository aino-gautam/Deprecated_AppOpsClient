package in.appops.client.common.config.dsnip;

import java.util.ArrayList;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.HTML;

public class HTMLProcessor {

	public static NodeList<Element> getSpanElementsFromHTML(String htmlStr){
		NodeList<Element> nodeList = null;
		HTML html = new HTML(htmlStr);
		try {
			nodeList = html.getElement().getElementsByTagName("span");
			return nodeList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * method to filter all the appops field span elements from the list of nodes passed to this method.
	 * @param nodeList List of node elements (<span> elements that have been fetched from the html)
	 * @return
	 */
	public static ArrayList<Element> getAppopsFieldElements(NodeList<Element> nodeList){
		ArrayList<Element> appopsFieldNodeList = new ArrayList<Element>();
		if(nodeList != null) {
			int lengthOfNodes = nodeList.getLength();
			for (int i = lengthOfNodes - 1; i > -1; i--) { 
				Node node = nodeList.getItem(i);
				Element spanElement = Element.as(node); 
				if(spanElement != null) {
					if (spanElement.hasAttribute("appopsfield") && Boolean.valueOf(spanElement.getAttribute("appopsfield"))) 
						appopsFieldNodeList.add(spanElement);
				}
			}
			
		}
		return appopsFieldNodeList;
	}
	
	/**
	 * method to filter all the container span elements from the list of nodes passed to this method.
	 * @param nodeList List of node elements (<span> elements that have been fetched from the html)
	 * @return
	 */
	public static ArrayList<Element> getContainerElements(NodeList<Element> nodeList){
		ArrayList<Element> containerNodeList = new ArrayList<Element>();
		if(nodeList != null) {
			int lengthOfNodes = nodeList.getLength();
			for (int i = lengthOfNodes - 1; i > -1; i--) { 
				Node node = nodeList.getItem(i);
				Element spanElement = Element.as(node); 
				if(spanElement != null) {
					if (spanElement.hasAttribute("widgetContainerType") && spanElement.getAttribute("widgetContainerType").equals("placeholder"))
						containerNodeList.add(spanElement);
				}
			}
			
		}
		return containerNodeList;
	}
}
