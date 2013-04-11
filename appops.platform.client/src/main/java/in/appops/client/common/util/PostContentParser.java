package in.appops.client.common.util;

import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.LinkField;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;


public class PostContentParser {
	
	FlowPanel flowPanel ;
		
	public FlowPanel getSelfMsgComponent(String xmlString){
		Document document = XMLParser.parse(xmlString);
		document.normalize();
		flowPanel = processXmlMessage(getSelfMsgNode(document));
		return flowPanel;
	}
	
	public FlowPanel getGeneralMsgComponent(String xmlString){
		Document document = XMLParser.parse(xmlString);
		document.normalize();
		flowPanel = processXmlMessage(getGeneralMsgNode(document));
		return flowPanel;
		
	}
	
	public FlowPanel getParticipantMsgComponent(String xmlString){
		Document document = XMLParser.parse(xmlString);
		document.normalize();
		flowPanel = processXmlMessage(getParticipantMsgNode(document));
		return flowPanel;
		
	}
	private FlowPanel processXmlMessage(Node msgNode){
		FlowPanel flowPanel = new FlowPanel();
		try {
			Element msgElement = (Element) msgNode;
		    NodeList nodeList = msgElement.getElementsByTagName("description");
	    	Node node = nodeList.item(0);
	    	NodeList childNodeList = node.getChildNodes();
	    	for(int nodeCnt = 0; nodeCnt < childNodeList.getLength(); nodeCnt++){
	    		Node childNode = childNodeList.item(nodeCnt);
	    		if (childNode.getNodeType() == Node.ELEMENT_NODE) {
				      Element ele = (Element) childNode;
				      String tagName = ele.getTagName();
				     
				      if(tagName.equalsIgnoreCase("link")){
				    	  String id = ele.getAttribute("id");
				    	  String type = ele.getAttribute("type");
				    	  String linkText = ele.getFirstChild().getNodeValue();
				    	  LinkField linkField = new LinkField();
				    	  linkField.setConfiguration(getLinkFieldConfiguration(LinkField.LINKFIELDTYPE_HYPERLINK, "postLink", null, null));
				    	  linkField.setFieldValue(linkText);
				    	  linkField.createField();
				    	  flowPanel.add(linkField);
				    	 
				      } else if(tagName.equalsIgnoreCase("text")){
				    	  String text = ele.getFirstChild().getNodeValue();
				    	  LabelField labelField = new LabelField();
				    	  labelField.setFieldValue(text);
				    	  labelField.setConfiguration(getLabelFieldConfiguration(true, "postLabel", null, null));
				    	  labelField.createField();
				    	  flowPanel.add(labelField);
				      }
		    	}
		    }
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		return flowPanel;
		
	}
	
	
	private Node getSelfMsgNode(Document xmlDom){
		NodeList nodeList = xmlDom.getElementsByTagName("selfmsg");
    	Node node = nodeList.item(0);
    	return node;
		
	}
	
	private Node getGeneralMsgNode(Document xmlDom){
		NodeList nodeList = xmlDom.getElementsByTagName("generalmsg");
    	Node node = nodeList.item(0);
    	return node;
		
	}
	
	private Node getParticipantMsgNode(Document xmlDom){
		NodeList nodeList = xmlDom.getElementsByTagName("participantmsg");
    	Node node = nodeList.item(0);
    	return node;
		
	}
	
	private Configuration getLinkFieldConfiguration(String linkFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LinkField.LINKFIELD_TYPE, linkFieldType);
		configuration.setPropertyByName(LinkField.LINKFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LinkField.LINKFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LinkField.LINKFIELD_DEBUGID, debugId);
		return configuration;
	}
	
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return configuration;
	}
	
	
}
