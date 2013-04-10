package in.appops.client.common.components;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SuggestionAction extends Composite{
	private FlexTable basePanel;
	private List<String> suggestionList;
	private Map<String, List> suggestionMap = new HashMap<String, List>();
	private List<WidgetManagement> suggestionActionWidgetList = new LinkedList<WidgetManagement>();
	
	public SuggestionAction(){
		initialize();
		initWidget(basePanel);
		createUI();
	}

	private void initialize() {
		basePanel = new FlexTable();
		
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
	
	public void addSuggestionAction(ActionLabelImpl actionLabel){
//		Label suggestionLabel = new Label(suggestionAction);
		actionLabel.setStylePrimaryName("appops-intelliThought-Label");
		actionLabel.addStyleName("fadeInLeft");
		
		WidgetManagement widgetPlacement = new WidgetManagement(0, 0, actionLabel);

		manageSuggestionActionPlacement();
		placeWidget(widgetPlacement);
		suggestionActionWidgetList.add(widgetPlacement);
	}
	
	private void manageSuggestionActionPlacement() {
		for(WidgetManagement management : suggestionActionWidgetList){
			int row = management.getRow();
			int col = management.getColumn();
			
			if(col + 1 >= 2){
				row = row + 1;
				if(row < 4){
					management.setRow(row);
					management.setColumn(0);
				}
			} else{
				management.setColumn(++col);
			}
			placeWidget(management);
		}
	}

	private void placeWidget(WidgetManagement management) {
		Label suggestionAction = (Label)management.getWidget();
		basePanel.setWidget(management.getRow(), management.getColumn(), suggestionAction);
		basePanel.getCellFormatter().setHorizontalAlignment(management.getRow(), management.getColumn(), HasHorizontalAlignment.ALIGN_LEFT);
	}

	final class WidgetManagement{
		int row = 0;
		int column = 0;
		Widget widget = null;
		
		WidgetManagement(int row, int column, Widget widget) {
			this.row = row;
			this.column = column;
			this.widget = widget;
		}
		
		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getColumn() {
			return column;
		}

		public void setColumn(int column) {
			this.column = column;
		}

		public Widget getWidget() {
			return widget;
		}
				
		public void setWidget(Widget widget) {
			this.widget = widget;
		}
	}
	
}
