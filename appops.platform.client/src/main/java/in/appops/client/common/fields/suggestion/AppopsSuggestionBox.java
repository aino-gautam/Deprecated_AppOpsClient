package in.appops.client.common.fields.suggestion;

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
	
	public void createUi(){
		this.basePanel.add(suggestBox);
		suggestBox.addSelectionHandler(this);
		suggestBox.getTextBox().addClickHandler(this);
		suggestBox.getTextBox().addFocusHandler(this);
	}
	
	public void setQueryName(String queryName) {
		oracle.setQueryName(queryName);
	}
	
	public void setOperationName(String name) {
		oracle.setOperationName(name);
	}

	@Override
	public void onSelection(SelectionEvent<Suggestion> event) {
		AppopsSuggestion selectedSuggestion = (AppopsSuggestion) event.getSelectedItem();
		setSelectedSuggestion(selectedSuggestion);
		getSuggestBox().setText(selectedSuggestion.getDisplayString());
		getSuggestBox().getTextBox().setFocus(false);
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
	
	public void setMaxResult(int max) {
		oracle.setMaxResult(max);
	}

	public void setPropertyToDisplay(String propertyByName) {
		oracle.setDisplayText(propertyByName);
	}

	public void setQueryRestrictions(HashMap<String, Object> map) {
		oracle.setRestriction(map);
	}

	public void setIsSearchQuery(Boolean val) {
		oracle.IsSearchQuery(val);
	}

	public void setAutoSuggestion(Boolean val) {
		isAutoSuggestion = val;
	}
	
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

}