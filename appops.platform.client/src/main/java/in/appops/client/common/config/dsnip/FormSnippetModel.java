package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.field.BaseField;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * The model class for the {@link FormSnippetView}
 * @author nairutee
 *
 */
public class FormSnippetModel extends HTMLSnippetModel {

/*	*//**
	 * gets the html description of a form element which could be a {@link BaseField} or an {@link HTMLSnippetView}
	 *//*
	@Override
	public String getDescription(String snippetType) {
		Element formElement = RootPanel.get(snippetType).getElement();
		String formElementDesc = formElement.getInnerHTML();
		return formElementDesc;
	}*/
}
