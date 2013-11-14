package in.appops.client.common.config.dsnip;


import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public interface IForm {
	/**
	 * This method take the form element basically a <span> from the html description, add a new HTMLPanel with the
	 * innerhtml of the form element. This innerhtml contains the form fields that would be added into the HTMLPanel. 
	 * @param formElement
	 */
	void create(Element formElement);
	
	/**
	 * This method replaces the html element (a <span>) corresponding to the component in the html description
	 * with the actual gwt component
	 * @param field
	 * @param fieldElement
	 */
	void addAndReplaceFormFieldElement(Widget field, Element fieldElement);
}
