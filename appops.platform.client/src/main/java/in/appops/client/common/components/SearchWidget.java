package in.appops.client.common.components;

import in.appops.client.common.fields.suggestion.AppopsSuggestionBox;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

/**
 * @author milind@ensarm.com
 */
public class SearchWidget extends Composite{

	private AppopsSuggestionBox searchSuggestionBox = null;
	private HorizontalPanel searchBoxPanel = null;
	private Button searchBtn = null; 
	
	public SearchWidget() {
		initializeComponent();
	}
	
	private void initializeComponent() {
		searchBoxPanel = new HorizontalPanel();
		searchBoxPanel.setSpacing(3);
		searchBtn = new Button("Search");
		searchBtn.setStylePrimaryName("searchBtn");
		searchSuggestionBox = new AppopsSuggestionBox();
		searchSuggestionBox.getSuggestBox().addStyleName("searchSuggestionBox");
		searchBoxPanel.add(searchSuggestionBox);
		searchBoxPanel.add(searchBtn);
		searchBoxPanel.setCellHorizontalAlignment(searchSuggestionBox, HasHorizontalAlignment.ALIGN_CENTER);
		searchBoxPanel.setCellHorizontalAlignment(searchBtn, HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(searchBoxPanel);
	}

	public Button getSearchBtn() {
		return searchBtn;
	}

	public void setSearchBtn(Button searchBtn) {
		this.searchBtn = searchBtn;
	}

	public AppopsSuggestionBox getSearchSuggestionBox() {
		return searchSuggestionBox;
	}

	public void setSearchSuggestionBox(AppopsSuggestionBox searchSuggestionBox) {
		this.searchSuggestionBox = searchSuggestionBox;
	}
}
