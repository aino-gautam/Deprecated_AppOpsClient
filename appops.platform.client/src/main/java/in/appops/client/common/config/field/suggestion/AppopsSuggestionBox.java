package in.appops.client.common.config.field.suggestion;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AppopsSuggestionBox extends Composite implements SelectionHandler<SuggestOracle.Suggestion>, ClickHandler, FocusHandler{
	
	private VerticalPanel basePanel;
	private AppopsSuggestion selectedSuggestion;
	private SuggestionOracle oracle = new SuggestionOracle();
	private SuggestBox suggestBox = new SuggestBox(oracle);
	private Boolean isAutoSuggestion = false;
	
	public AppopsSuggestionBox() {
		basePanel = new VerticalPanel();
		createUi();
		initWidget(basePanel);
	}
	
	/***************************************** *******************************/
	public void createUi(){
		this.basePanel.add(suggestBox);
		suggestBox.addSelectionHandler(this);
		suggestBox.getTextBox().addClickHandler(this);
		suggestBox.getTextBox().addFocusHandler(this);
	}
	
	
	public void setSelectedSuggestion(AppopsSuggestion selectedSuggestion) {
		this.selectedSuggestion = selectedSuggestion;
	}

	public SuggestBox getSuggestBox() {
		return suggestBox;
	}

	public AppopsSuggestion getSelectedSuggestion() {
		return selectedSuggestion;
	}
	
	
	/****************************** ************************************************************/
	
	/**
	 * Methods sets the query name to oracle..
	 * @param queryName
	 */
	public void setQueryName(String queryName) {
		oracle.setQueryName(queryName);
	}
	
	/**
	 * Sets the operation name to oracle.
	 * @param name
	 */
	public void setOperationName(String name) {
		oracle.setOperationName(name);
	}
	
	/**
	 * Sets the query restrictions to set to oracle.
	 * @param name
	 */
	public void setQueryRestrictions(HashMap<String, Object> map) {
		oracle.setRestriction(map);
	}

	/**
	 * Method sets the max result of the query result.
	 * @param max
	 */
	public void setMaxResult(int max) {
		oracle.setMaxResult(max);
	}

	/**
	 * Sets the property to display in suggestion box..
	 * @param propertyByName
	 */
	public void setPropertyToDisplay(String propertyByName) {
		oracle.setEntityPropToDisplay(propertyByName);
	}

	/**
	 * Sets if autosuggetsion is true or false.
	 * @param val
	 */
	public void setAutoSuggestion(Boolean val) {
		isAutoSuggestion = val;
	}
	
	/**
	 * Set if suggetion box works with static data or not.
	 * @param val
	 */
	public void setStaticSuggestionBox(Boolean val) {
		oracle.IsStaticSuggestionBox(val);
	}
	
	/**
	 * Sets the items to display in suggestion box.
	 * @param itemsToDisplay
	 */
	public void setItemsToDisplay(ArrayList<String> itemsToDisplay) {
		oracle.setItemsToDisplay(itemsToDisplay);
	}

	/**
	 * Sets the flag to oracle if suggestions to come using searchquery or not.
	 * @param val
	 */
	public void setIsSearchQuery(Boolean val) {
		oracle.IsSearchQuery(val);
	}
	
	/********************************** ***************************************/
	@Override
	public void onFocus(FocusEvent event) {
		if(isAutoSuggestion){
			if(getSuggestBox().getText().equals(""))
				getSuggestBox().setText(" ");
			getSuggestBox().showSuggestionList();
			if(getSuggestBox().getText().equals(" "))
				getSuggestBox().setText("");
		}
	}
	
	@Override
	public void onClick(ClickEvent event) {
		if(getSuggestBox().getText().equals("")){
			getSuggestBox().setText(" ");
		}
		getSuggestBox().showSuggestionList();
		if(getSuggestBox().getText().equals(" ")) {
			getSuggestBox().setText("");
		}
	}
	
	@Override
	public void onSelection(SelectionEvent<Suggestion> event) {
		
		AppopsSuggestion selectedSuggestion = (AppopsSuggestion) event.getSelectedItem();
		setSelectedSuggestion(selectedSuggestion);
		getSuggestBox().setText(selectedSuggestion.getDisplayString());
		getSuggestBox().getTextBox().setFocus(false);
		
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventData(selectedSuggestion);
		fieldEvent.setEventType(FieldEvent.SUGGESTION_SELECTED);
		AppUtils.EVENT_BUS.fireEvent(fieldEvent);
	}


}