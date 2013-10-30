package in.appops.client.common.fields.slider;

import in.appops.client.common.fields.StateField;
import in.appops.client.common.fields.slider.field.NumericRangeSliderField;
import in.appops.client.common.fields.slider.field.StringRangeSliderField;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
/**
 * This class is responsible for UI creation of Numeric Range Slider Field component
 * @author milind@ensarm.com
 */
public class RangeSliderFieldComponent extends Composite implements ClickHandler{

	private VerticalPanel mainPanel = null;
	
	private VerticalPanel mainPanelForNumericSlider = null;
	private VerticalPanel mainPanelForStringSlider = null;
	
	private SimplePanel numericSliderComponentPanel = null;
	private SimplePanel stringSliderComponentPanel = null;
	
	private Button goButton = null;
	private Button resetBtn = null;
	
	private Button goButtonForStringSlider = null;
	private Button resetBtnForStringSlider = null;
	
	private TextBox stringSliderValuesTxtBox = null;
	private TextBox stepValueTxtBox = null;
	private TextBox minValueTxtBox = null;
	private TextBox maxValueTxtBox = null;
	
	public RangeSliderFieldComponent() {
		initializeCompoenent();
	}

	private void initializeCompoenent(){
		mainPanel = new VerticalPanel();
		
		/***** For Numeric Slider *****/
		mainPanelForNumericSlider = new VerticalPanel();
		numericSliderComponentPanel = new SimplePanel();
		
		goButton= new Button("Preview");
		goButton.addClickHandler(this);
		
		resetBtn = new Button("Reset");
		resetBtn.addClickHandler(this);
		
		stepValueTxtBox = new TextBox();
		minValueTxtBox = new TextBox();
		maxValueTxtBox = new TextBox();
		
		/*****   Default Configurations *****/
		stepValueTxtBox.setText(100+"");
		minValueTxtBox.setText(0+"");
		maxValueTxtBox.setText(500+"");
		
		/***** For String Slider *****/
		mainPanelForStringSlider = new VerticalPanel();
		stringSliderComponentPanel = new SimplePanel();
		
		goButtonForStringSlider= new Button("Preview");
		goButtonForStringSlider.addClickHandler(this);
		
		resetBtnForStringSlider = new Button("Reset");
		resetBtnForStringSlider.addClickHandler(this);
		
		stringSliderValuesTxtBox = new TextBox();
		stringSliderValuesTxtBox.setStylePrimaryName("appops-TextField");
		/*****   Default Configurations *****/
		stringSliderValuesTxtBox.setText("Me, Private, Restricted, Public");
		
		initWidget(mainPanel);
	}
	
	public void creatUI() {
		mainPanel.setSpacing(20);
		
		Label headerLbl = new Label("i) Numeric Range Slider Field");
		headerLbl.setStylePrimaryName("sliderbarHeaderlabel");
		mainPanelForNumericSlider.add(headerLbl);
		
		FlexTable configurationFlexTable = new FlexTable();
		configurationFlexTable.setCellSpacing(3);
		
		Label minValueLbl = new Label("Minimum range value:");
		configurationFlexTable.setWidget(0, 0, minValueLbl);
		configurationFlexTable.setWidget(0, 1, minValueTxtBox);
		
		Label maxValueLbl = new Label("Maximum range value:");
		configurationFlexTable.setWidget(2, 0, maxValueLbl);
		configurationFlexTable.setWidget(2, 1, maxValueTxtBox);
		
		Label stepValueLbl = new Label("Step value:");
		configurationFlexTable.setWidget(4, 0, stepValueLbl);
		configurationFlexTable.setWidget(4, 1, stepValueTxtBox);
		
		mainPanelForNumericSlider.add(configurationFlexTable);
		
		HorizontalPanel btnPanel = new HorizontalPanel();
		btnPanel.setSpacing(3);
		btnPanel.add(resetBtn);
		btnPanel.add(goButton);
		mainPanelForNumericSlider.add(btnPanel);
		
		mainPanelForNumericSlider.add(numericSliderComponentPanel);
		
		mainPanel.add(mainPanelForNumericSlider);
		
		mainPanel.add(new HTML("<hr style=\"color: #BFBFBF; background-color: #BFBFBF; width: 100%; height: 2px;\"></hr>"));
		
		Label headerLbl1 = new Label("ii) String Range Slider Field");
		headerLbl1.setStylePrimaryName("sliderbarHeaderlabel");
		mainPanelForStringSlider.add(headerLbl1);

		HorizontalPanel configPanel = new HorizontalPanel();
		btnPanel.setSpacing(10);
		
		Label stringValuseLbl = new Label("Please enter the ',' seperated values :");
		configPanel.add(stringValuseLbl);
		configPanel.add(stringSliderValuesTxtBox);
		
		mainPanelForStringSlider.add(configPanel);
		
		HorizontalPanel btnPanelForStringSlider = new HorizontalPanel();
		btnPanelForStringSlider.setSpacing(10);
		btnPanelForStringSlider.add(resetBtnForStringSlider);
		btnPanelForStringSlider.add(goButtonForStringSlider);
		mainPanelForStringSlider.add(btnPanelForStringSlider);
		
		mainPanelForStringSlider.add(stringSliderComponentPanel);
		
		mainPanel.add(mainPanelForStringSlider);
		
		createNumericSliderField();
		
		createStringSliderField();
	}
	
	private void createNumericSliderField(){
		numericSliderComponentPanel.clear();
		StateField numericRangeSlider = new StateField();
		numericRangeSlider.setConfiguration(getNumericSliderFieldConfiguration());
		try {
			numericRangeSlider.create();
			numericSliderComponentPanel.add(numericRangeSlider);
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
	}
	
	private void createStringSliderField(){
		stringSliderComponentPanel.clear();
		StateField stringRangeSlider = new StateField();
		stringRangeSlider.setConfiguration(getStringSliderFieldConfiguration());
		try {
			stringRangeSlider.create();
			stringSliderComponentPanel.add(stringRangeSlider);
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource() instanceof Button){
			Button btn = (Button) event.getSource();
			if(btn.equals(resetBtn)){
				stepValueTxtBox.setText(100+"");
				minValueTxtBox.setText(0+"");
				maxValueTxtBox.setText(500+"");
				createNumericSliderField();
			}else if(btn.equals(goButton))
				createNumericSliderField();
			if(btn.equals(resetBtnForStringSlider)){
				stringSliderValuesTxtBox.setText("Me, Private, Restricted, Public");
				createStringSliderField();
			}else if(btn.equals(goButtonForStringSlider))
				createStringSliderField();
		}
	}

	private Configuration getNumericSliderFieldConfiguration() {
		double maxValue = Double.parseDouble(maxValueTxtBox.getText());
		double minValue = Double.parseDouble(minValueTxtBox.getText());
		double stepValue = Double.parseDouble(stepValueTxtBox.getText());
		
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(StateField.STATEFIELD_MODE, StateField.STATEFIELDMODE_ENUM);
		configuration.setPropertyByName(StateField.STATEFIELD_TYPE, StateField.STATEFIELDTYPE_NUMERICRANGE);
		configuration.setPropertyByName(NumericRangeSliderField.NUMERIC_RANGESLIDER_MAXVALUE, maxValue);
		configuration.setPropertyByName(NumericRangeSliderField.NUMERIC_RANGESLIDER_MINVALUE, minValue);
		configuration.setPropertyByName(NumericRangeSliderField.NUMERIC_RANGESLIDER_STEPVALUE, stepValue);
		return configuration;
	}
	
	private Configuration getStringSliderFieldConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(StateField.STATEFIELD_MODE, StateField.STATEFIELDMODE_ENUM);
		configuration.setPropertyByName(StateField.STATEFIELD_TYPE, StateField.STATEFIELDTYPE_STRINGRANGE);
		configuration.setPropertyByName(StringRangeSliderField.STRING_RANGESLIDER_OPTIONLIST, stringSliderValuesTxtBox.getText());
		return configuration;
	}
}
