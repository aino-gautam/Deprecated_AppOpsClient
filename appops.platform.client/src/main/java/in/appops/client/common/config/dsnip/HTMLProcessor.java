package in.appops.client.common.config.dsnip;

import java.util.ArrayList;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.HTML;

public class HTMLProcessor {

	/*private HTMLProcessor htmlProcessor;
	
	*//**
	 * private constructor
	 *//*
	private HTMLProcessor(){
		
	}
	
	*//**
	 * returns the singleton isntance of the htmlprocessor
	 * @return
	 *//*
	public HTMLProcessor getInsance(){
		if(htmlProcessor == null)
			htmlProcessor = new HTMLProcessor();
		
		return htmlProcessor;
	}
	*/
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
}
