package in.appops.client.common.fields.slider;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.StateField;
import in.appops.client.common.fields.slider.field.StringRangeSliderField;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
/**
 * This class is responsible for UI creation of String Range Slider Field component
 * @author milind@ensarm.com
 */
public class StringRangeSliderFieldComponent extends Composite implements ClickHandler, FieldEventHandler{

	private VerticalPanel mainPanel = null;
	private VerticalPanel mainPanelForStringSlider = null;
	private SimplePanel stringSliderComponentPanel = null;
	
	private Button goButtonForStringSlider = null;
	private Button resetBtnForStringSlider = null;
	private TextBox stringSliderValuesTxtBox = null;
	
	public StringRangeSliderFieldComponent() {
		initializeCompoenent();
	}

	private void initializeCompoenent(){
		mainPanel = new VerticalPanel();
		
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
		
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		
		HorizontalPanel btnPanel = new HorizontalPanel();
		btnPanel.setSpacing(10);
		
		Label headerLbl1 = new Label("String Range Slider Field");
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
		
		createStringSliderField();
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
			if(btn.equals(resetBtnForStringSlider)){
				stringSliderValuesTxtBox.setText("Me, Private, Restricted, Public");
				createStringSliderField();
			}else if(btn.equals(goButtonForStringSlider))
				createStringSliderField();
		}
	}

	private Configuration getStringSliderFieldConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(StateField.STATEFIELD_MODE, StateField.STATEFIELDMODE_ENUM);
		configuration.setPropertyByName(StateField.STATEFIELD_TYPE, StateField.STATEFIELDTYPE_STRINGRANGE);
		configuration.setPropertyByName(StringRangeSliderField.STRING_RANGESLIDER_OPTIONLIST, stringSliderValuesTxtBox.getText());
		return configuration;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		if(event.getEventType() == FieldEvent.EDITINITIATED){
			String data = (String) event.getEventData();
			System.out.println("You have selected:" + data);
			//Window.alert("Selected value: "+data);
		}
	}
}
