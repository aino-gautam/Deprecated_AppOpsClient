package in.appops.showcase.web.gwt.fields.client;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.fields.CheckboxGroupField;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GroupCheckboxWidget extends Composite implements ClickHandler{

	private VerticalPanel basePanel;
	private Button viewCheckboxticked;
	private CheckboxGroupField checkboxGroupField;
	private VerticalPanel groupCheckboxDisplayPanel;
	private Button viewRadiobuttonTicked;
	private CheckboxGroupField singleSelectCheckboxGroupField;
	private VerticalPanel radiobuttonDisplayPanel;
	private ListBox modeListbox;
	private ListBox basePanelListbox;
	private String multiSelectMode = "CheckboxGroupField - MultiSelect";
	private String singleSelectMode = "CheckboxGroupField - SingleSelect";
	private String verticalBase = "Vertical Basepanel";
	private String horizontalBase = "Horizontal Basepanel";
	private Button preview;
	
	public GroupCheckboxWidget() {
		initialize();
		createUI();
		initWidget(basePanel);
	}
	
	private void initialize() {
		basePanel = new VerticalPanel();
		viewCheckboxticked = new Button("View tick items");
		viewRadiobuttonTicked = new Button("View tick item");
		modeListbox = new ListBox();
		basePanelListbox = new ListBox();
		preview = new Button("Preview");
	}
	
	public void createUI() {
		HorizontalPanel selectModePanel = new HorizontalPanel();
		
		LabelField modeLabel = new LabelField();
		modeLabel.setFieldValue("Select mode");
		modeLabel.setConfiguration(getLabelFieldConfiguration(true, "groupCheckboxTitle", null, null));
		modeLabel.create();
		selectModePanel.add(modeLabel);
		
		modeListbox.addItem("--Select--");
		modeListbox.addItem(multiSelectMode);
		modeListbox.addItem(singleSelectMode);
		selectModePanel.add(modeListbox);
		modeListbox.setStylePrimaryName("selectModeListbox");
		basePanel.add(selectModePanel);
		selectModePanel.setWidth("100%");
		
		HorizontalPanel baseModePanel = new HorizontalPanel();
		
		LabelField selectBaseLabel = new LabelField();
		selectBaseLabel.setFieldValue("Select base");
		selectBaseLabel.setConfiguration(getLabelFieldConfiguration(true, "groupCheckboxTitle", null, null));
		selectBaseLabel.create();
		baseModePanel.add(selectBaseLabel);
		
		basePanelListbox.addItem("--Select--");
		basePanelListbox.addItem(verticalBase);
		basePanelListbox.addItem(horizontalBase);
		baseModePanel.add(basePanelListbox);
		basePanel.add(baseModePanel);
		baseModePanel.setWidth("100%");
		
		basePanel.add(preview);
		preview.setStylePrimaryName("appops-Button");
		preview.addStyleName("previewButton");
		preview.addClickHandler(this);
	}
	
	public void createMultiSelectCheckbox(Configuration configuration) {
		try {
			basePanel.clear();
			checkboxGroupField = new CheckboxGroupField();
			//Configuration configuration = getCheckboxGroupFieldConfiguration(CheckboxGroupField.CHECKBOX_MULTISELECT,CheckboxGroupField.CHECKBOX_VERTICALBASEPANEL);
			checkboxGroupField.setConfiguration(configuration);

			groupCheckboxDisplayPanel = new VerticalPanel();
			groupCheckboxDisplayPanel.setStylePrimaryName("groupCheckboxDisplayPanel");
			
			viewCheckboxticked.addClickHandler(this);
			viewCheckboxticked.setStylePrimaryName("appops-Button");
			viewCheckboxticked.addStyleName("viewTickCheckboxButtonAlignment");
			
			checkboxGroupField.create();
			
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
	
	public void createSingleSelectCheckbox(Configuration singleSelectionConfiguration) {
		try {
			basePanel.clear();
			singleSelectCheckboxGroupField = new CheckboxGroupField();
			//Configuration singleSelectionConfiguration = getCheckboxGroupFieldConfiguration(CheckboxGroupField.CHECKBOX_SINGLESELECT,CheckboxGroupField.CHECKBOX_VERTICALBASEPANEL);
			singleSelectCheckboxGroupField.setConfiguration(singleSelectionConfiguration);

			radiobuttonDisplayPanel = new VerticalPanel();
			radiobuttonDisplayPanel.setStylePrimaryName("groupCheckboxDisplayPanel");
			
			viewRadiobuttonTicked.addClickHandler(this);
			viewRadiobuttonTicked.setStylePrimaryName("appops-Button");
			viewRadiobuttonTicked.addStyleName("viewTickCheckboxButtonAlignment");
			
			singleSelectCheckboxGroupField.create();
			
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
		configuration.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		return configuration;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(viewCheckboxticked)) {
			groupCheckboxDisplayPanel.clear();
			boolean isAnyOneTick = false; 
			LabelField titleLabel = new LabelField();
			titleLabel.setFieldValue("Tick items");
			titleLabel.setConfiguration(getLabelFieldConfiguration(true, "groupCheckboxTitle", null, null));
			titleLabel.create();
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
					displayLabel.create();
					groupCheckboxDisplayPanel.add(displayLabel);
				}
			}
			if(!isAnyOneTick) {
				titleLabel.setFieldValue("No tick items");
				titleLabel.reset();
			}
		} else if(event.getSource().equals(viewRadiobuttonTicked)) {
			radiobuttonDisplayPanel.clear();
			boolean isAnyOneTick = false; 
			LabelField titleLabel = new LabelField();
			titleLabel.setFieldValue("Tick items");
			titleLabel.setConfiguration(getLabelFieldConfiguration(true, "groupCheckboxTitle", null, null));
			titleLabel.create();
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
					displayLabel.create();
					radiobuttonDisplayPanel.add(displayLabel);
				}
			}
			if(!isAnyOneTick) {
				titleLabel.setFieldValue("No tick items");
				titleLabel.reset();
			}
		} else if(event.getSource().equals(preview)) {
			int index = modeListbox.getSelectedIndex();
			String mode = modeListbox.getValue(index);
			
			int count = basePanelListbox.getSelectedIndex();
			String base = basePanelListbox.getValue(count);
			String basePanel = null;
			if(index > 0 && count > 0) {
				if(base.equals(verticalBase)) {
					basePanel = CheckboxGroupField.CHECKBOX_VERTICALBASEPANEL;
				} else if(base.equals(horizontalBase)) {
					basePanel = CheckboxGroupField.CHECKBOX_HORIZONTALBASEPANEL;
				}
				
				if(mode.equals(multiSelectMode)) {
					Configuration configuration = getCheckboxGroupFieldConfiguration(CheckboxGroupField.CHECKBOX_MULTISELECT, basePanel);
					createMultiSelectCheckbox(configuration);
				} else if(mode.equals(singleSelectMode)) {
					Configuration configuration = getCheckboxGroupFieldConfiguration(CheckboxGroupField.CHECKBOX_SINGLESELECT, basePanel);
					createSingleSelectCheckbox(configuration);
				}
			}
		}
	}
}
