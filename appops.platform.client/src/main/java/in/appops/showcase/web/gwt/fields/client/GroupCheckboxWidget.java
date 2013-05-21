package in.appops.showcase.web.gwt.fields.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import in.appops.client.common.fields.CheckboxGroupField;
import in.appops.client.common.fields.LabelField;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GroupCheckboxWidget extends Composite implements ClickHandler{

	private VerticalPanel basePanel;
	private Button viewCheckboxticked;
	private CheckboxGroupField checkboxGroupField;
	private VerticalPanel groupCheckboxDisplayPanel;
	private Button viewRadiobuttonTicked;
	private CheckboxGroupField singleSelectCheckboxGroupField;
	private VerticalPanel radiobuttonDisplayPanel;
	
	public GroupCheckboxWidget() {
		initialize();
		initWidget(basePanel);
	}
	
	private void initialize() {
		basePanel = new VerticalPanel();
		viewCheckboxticked = new Button("View tick items");
		viewRadiobuttonTicked = new Button("View tick item");
	}
	
	public void createMultiSelectCheckbox() {
		try {
			checkboxGroupField = new CheckboxGroupField();
			Configuration configuration = getCheckboxGroupFieldConfiguration(CheckboxGroupField.CHECKBOX_MULTISELECT,CheckboxGroupField.CHECKBOX_VERTICALBASEPANEL);
			checkboxGroupField.setConfiguration(configuration);

			groupCheckboxDisplayPanel = new VerticalPanel();
			groupCheckboxDisplayPanel.setStylePrimaryName("groupCheckboxDisplayPanel");
			
			viewCheckboxticked.addClickHandler(this);
			viewCheckboxticked.setStylePrimaryName("appops-Button");
			viewCheckboxticked.addStyleName("viewTickCheckboxButtonAlignment");
			
			checkboxGroupField.createField();
			
			checkboxGroupField.addCheckItem("Red",false);
			checkboxGroupField.addCheckItem("Green",false);
			checkboxGroupField.addCheckItem("Blue",false);

			basePanel.add(checkboxGroupField);
			basePanel.add(viewCheckboxticked);
			basePanel.add(groupCheckboxDisplayPanel);
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
	}
	
	public void createSingleSelectCheckbox() {
		try {
			singleSelectCheckboxGroupField = new CheckboxGroupField();
			Configuration singleSelectionConfiguration = getCheckboxGroupFieldConfiguration(CheckboxGroupField.CHECKBOX_SINGLESELECT,CheckboxGroupField.CHECKBOX_VERTICALBASEPANEL);
			singleSelectCheckboxGroupField.setConfiguration(singleSelectionConfiguration);

			radiobuttonDisplayPanel = new VerticalPanel();
			radiobuttonDisplayPanel.setStylePrimaryName("groupCheckboxDisplayPanel");
			
			viewRadiobuttonTicked.addClickHandler(this);
			viewRadiobuttonTicked.setStylePrimaryName("appops-Button");
			viewRadiobuttonTicked.addStyleName("viewTickCheckboxButtonAlignment");
			
			singleSelectCheckboxGroupField.createField();
			
			singleSelectCheckboxGroupField.addCheckItem("Red",false);
			singleSelectCheckboxGroupField.addCheckItem("Green",false);
			singleSelectCheckboxGroupField.addCheckItem("Blue",false);
			
			basePanel.add(singleSelectCheckboxGroupField);
			basePanel.add(viewRadiobuttonTicked);
			basePanel.add(radiobuttonDisplayPanel);
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
	}

	private Configuration getCheckboxGroupFieldConfiguration(String selectMode, String basePanel) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(CheckboxGroupField.CHECKBOX_SELECT_MODE, selectMode);
		configuration.setPropertyByName(CheckboxGroupField.CHECKBOX_BASEPANEL, basePanel);
		return configuration;
	}
	
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return configuration;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(viewCheckboxticked)) {
			groupCheckboxDisplayPanel.clear();
			boolean isAnyOneTick = false; 
			try {
				
				LabelField titleLabel = new LabelField();
				titleLabel.setFieldValue("Tick items");
				titleLabel.setConfiguration(getLabelFieldConfiguration(true, "groupCheckboxTitle", null, null));
				titleLabel.createField();
				groupCheckboxDisplayPanel.add(titleLabel);
				
				HashMap<String, Boolean> map = checkboxGroupField.getValue();
				Set<String> keySet = map.keySet();
				Iterator<String> iterator = keySet.iterator();
				while(iterator.hasNext()) {
					String key = iterator.next();
					boolean isTick = map.get(key);
					if(isTick) {
						isAnyOneTick = true;
						LabelField displayLabel = new LabelField();
						displayLabel.setFieldValue(key);
						displayLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
						displayLabel.createField();
						groupCheckboxDisplayPanel.add(displayLabel);
					}
				}
				if(!isAnyOneTick) {
					titleLabel.setFieldValue("No tick items");
					titleLabel.resetField();
				}
			} catch (AppOpsException e) {
				e.printStackTrace();
			}
		} else if(event.getSource().equals(viewRadiobuttonTicked)) {
			radiobuttonDisplayPanel.clear();
			boolean isAnyOneTick = false; 
			try {
				
				LabelField titleLabel = new LabelField();
				titleLabel.setFieldValue("Tick items");
				titleLabel.setConfiguration(getLabelFieldConfiguration(true, "groupCheckboxTitle", null, null));
				titleLabel.createField();
				radiobuttonDisplayPanel.add(titleLabel);
				
				HashMap<String, Boolean> map = singleSelectCheckboxGroupField.getValue();
				Set<String> keySet = map.keySet();
				Iterator<String> iterator = keySet.iterator();
				while(iterator.hasNext()) {
					String key = iterator.next();
					boolean isTick = map.get(key);
					if(isTick) {
						isAnyOneTick = true;
						LabelField displayLabel = new LabelField();
						displayLabel.setFieldValue(key);
						displayLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
						displayLabel.createField();
						radiobuttonDisplayPanel.add(displayLabel);
					}
				}
				if(!isAnyOneTick) {
					titleLabel.setFieldValue("No tick items");
					titleLabel.resetField();
				}
			} catch (AppOpsException e) {
				e.printStackTrace();
			}
		}
	}
}
