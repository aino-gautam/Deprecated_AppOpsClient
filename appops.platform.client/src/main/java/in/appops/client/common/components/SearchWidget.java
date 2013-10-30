package in.appops.client.common.components;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
/**
 * Not Used
 */
/**
 * @author milind@ensarm.com
 */
public class SearchWidget extends Composite{

	private TextBox searchTxtBox = null;
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
		searchTxtBox = new TextBox();
		searchTxtBox.addStyleName("searchSuggestionBox");
		searchBoxPanel.add(searchTxtBox);
		searchBoxPanel.add(searchBtn);
		searchBoxPanel.setCellHorizontalAlignment(searchTxtBox, HasHorizontalAlignment.ALIGN_CENTER);
		searchBoxPanel.setCellHorizontalAlignment(searchBtn, HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(searchBoxPanel);
	}

	public Button getSearchBtn() {
		return searchBtn;
	}

	public void setSearchBtn(Button searchBtn) {
		this.searchBtn = searchBtn;
	}

	public TextBox getSearchTxtBox() {
		return searchTxtBox;
	}

	public void setSearchTxtBox(TextBox searchTxtBox) {
		this.searchTxtBox = searchTxtBox;
	}
}
