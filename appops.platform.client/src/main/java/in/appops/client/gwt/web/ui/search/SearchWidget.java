package in.appops.client.gwt.web.ui.search;

import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author milind@ensarm.com
 * @modifiedby mahesh@ensarm.com
 */
public class SearchWidget extends Composite implements ClickHandler{

	private TextBox searchTxtBox = null;
	private VerticalPanel basePanel;
	private HorizontalPanel searchBoxPanel = null;
	private Button searchBtn = null; 

	private SearchListModel searchListModel= null; 

	public SearchWidget(SearchListModel searchListModel) {
		this.searchListModel = searchListModel;
		initializeComponent();
	}
	
	private void initializeComponent() {
		basePanel = new VerticalPanel();
		basePanel.setStylePrimaryName("fullWidth");
		searchBoxPanel = new HorizontalPanel();
		searchBtn = new Button("Search");
		searchBtn.setStylePrimaryName("searchBtn");
		searchTxtBox = new TextBox();
		searchTxtBox.addStyleName("searchSuggestionBox");
		
		
		searchBoxPanel.add(searchTxtBox);
		searchBoxPanel.add(searchBtn);
		
		searchBoxPanel.setCellHorizontalAlignment(searchTxtBox, HasHorizontalAlignment.ALIGN_CENTER);
		searchBoxPanel.setCellHorizontalAlignment(searchBtn, HasHorizontalAlignment.ALIGN_CENTER);
		
		basePanel.add(searchBoxPanel);
		basePanel.setCellHorizontalAlignment(searchBoxPanel, HasHorizontalAlignment.ALIGN_CENTER);

		searchBtn.addClickHandler(this);
		initWidget(basePanel);
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

	@Override
	public void onClick(ClickEvent event) {
		try{
			if(event.getSource().equals(searchBtn)){
				String searchText = searchTxtBox.getText().trim();
				if(!searchText.equals("")){
					searchTxtBox.setText("");
					HashMap<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("searchChar", searchText);
					
					searchListModel.getQueryToBind().setQueryParameterMap(paramMap);
					searchListModel.getEntityList(0);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
