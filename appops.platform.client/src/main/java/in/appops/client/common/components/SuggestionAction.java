package in.appops.client.common.components;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SuggestionAction extends Composite{
	private VerticalPanel basePanel;
	private List<String> suggestionList;
	private Map<String, List> suggestionMap = new HashMap<String, List>();
	
	
	public SuggestionAction(){
		initialize();
		initWidget(basePanel);
		createUI();
	}


	private void initialize() {
		basePanel = new VerticalPanel();
		
		suggestionList = new LinkedList<String>();
		suggestionList.add("Find a saloon");
		suggestionList.add("Fix An Appointment");
		suggestionMap.put("haircut", suggestionList);
		
		suggestionList = new LinkedList<String>();
		suggestionList.add("Book a table");
		suggestionList.add("Find Restaurant");
		suggestionMap.put("dinner", suggestionList);
		
	}


	private void createUI() {
		basePanel.setStylePrimaryName("suggestionLabel");
	}
	
	public void showActionSuggestion(String word){
		Set<String> keyWords = suggestionMap.keySet();
		
		for(String keyWord : keyWords){
			HorizontalPanel suggestionPanel = new HorizontalPanel();
			List<String> suggestionList  = suggestionMap.get(keyWord);
			
			for(String suggestion : suggestionList){
				if(keyWord.equalsIgnoreCase(word.trim())){
					Label suggestionLabel = new Label(suggestion);
					suggestionLabel.setStylePrimaryName("appops-intelliThought-Label");
					suggestionLabel.addStyleName("fadeInDown");
					suggestionPanel.add(suggestionLabel);
				}
			}
			basePanel.insert(suggestionPanel, 0);
		}
	}
}
