package in.appops.client.common.fields.date;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.user.datepicker.client.MonthSelector;

public class AppopsDatePicker extends DatePicker {
	
	public AppopsDatePicker(){
		MonthSelector monthSelector = this.getMonthSelector();
		monthSelector.setStylePrimaryName("appops-dtPickHeader");

		
		NodeList<com.google.gwt.dom.client.Element> nodeList = monthSelector.getElement().getElementsByTagName("td");
	    Node td1Node = nodeList.getItem(1);
	    Element td1Element = (Element) Element.as(td1Node);
	    td1Element.setClassName("appops-dtPickHeaderMonth");
	    
	    Node td2Node = nodeList.getItem(0);
	    Element td2Element = (Element) Element.as(td2Node);
	    
	    NodeList<com.google.gwt.dom.client.Element> divList = td2Element.getElementsByTagName("div");
	    Node divNode = divList.getItem(0);
	    Element divElement = (Element) Element.as(divNode);
	    divElement.setClassName("appops-dtPickHeaderMonthPrevMext");

	    Node td3Node = nodeList.getItem(2);
	    Element td3Element = (Element) Element.as(td3Node);
	    NodeList<com.google.gwt.dom.client.Element> div1List = td3Element.getElementsByTagName("div");
	    Node div1Node = div1List.getItem(0);
	    Element div1Element = (Element) Element.as(div1Node);
	    div1Element.setClassName("appops-dtPickHeaderMonthPrevMext");
		
		setMonthSelectorWidth();
		

	}
	
	public void setMonthSelectorWidth() {
		this.getMonthSelector().asWidget().setWidth("128px");
	}

}
