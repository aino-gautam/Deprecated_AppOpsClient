package in.appops.client.common.fields.slider.field;

import java.util.ArrayList;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.fields.Field;
import in.appops.client.common.fields.slider.StringRangeSlider;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StringRangeSliderField extends Composite implements Field{

	private Configuration configuration;
	private String fieldValue;
	private String optionListStr = null;
	private String knobImgUrl = null;
	public static final String STRING_RANGESLIDER_OPTIONLIST = "optionList";
	public static final String STRING_RANGESLIDER_KNOB_IMAGE = "knobImage";
	
	public StringRangeSliderField(){
		
	}
	
	@Override
	public Configuration getConfiguration() {
		return this.configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	@Override
	public void createField() throws AppOpsException {
		
		final VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		final ArrayList<String> listOfOption = getOptionListFromConfig();
		
		final StringRangeSlider stringRangeSlider = new StringRangeSlider(1, listOfOption.size());
		
		stringRangeSlider.setListOfOption(listOfOption);
		stringRangeSlider.setStepSize(1);
		stringRangeSlider.setCurrentValue(1);
		stringRangeSlider.setNumLabels(listOfOption.size()-1);

		int widhtMultiplier = listOfOption.size();
		if(widhtMultiplier>5){
			widhtMultiplier = 100 + (widhtMultiplier * 8);
			DOM.setElementAttribute(getElement(), "width", widhtMultiplier+"%");
		}else
			setStylePrimaryName("mainPanel");
		stringRangeSlider.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				String selectedSettingStr = listOfOption.get(((int)stringRangeSlider.getCurrentValue())-1);
				System.out.println("Selected Mode: "+selectedSettingStr);
				
				FieldEvent fieldEvent = new FieldEvent();
				fieldEvent.setEventType(FieldEvent.EDITINITIATED);
				fieldEvent.setEventData(selectedSettingStr);
				
				AppUtils.EVENT_BUS.fireEvent(fieldEvent);
			}
		});
		
		stringRangeSlider.addMouseWheelHandler(new MouseWheelHandler() {
			@Override
			public void onMouseWheel(MouseWheelEvent arg0) {
				String selectedSettingStr = listOfOption.get(((int)stringRangeSlider.getCurrentValue())-1);
				System.out.println("Selected Mode: "+selectedSettingStr);
				
				FieldEvent fieldEvent = new FieldEvent();
				fieldEvent.setEventType(FieldEvent.EDITINITIATED);
				fieldEvent.setEventData(selectedSettingStr);
				AppUtils.EVENT_BUS.fireEvent(fieldEvent);
			}
		});

		verticalPanel.setSpacing(10);
		verticalPanel.add(stringRangeSlider);
	}

	@Override
	public void clearField() {
		
	}
	
	private ArrayList<String> getOptionListFromConfig() {
		ArrayList<String> optionList = new ArrayList<String>();
		this.optionListStr = (String)configuration.getPropertyByName(STRING_RANGESLIDER_OPTIONLIST);
		if(optionList!=null){
			String[] strArr = optionListStr.split(",");
			for(String optionStr : strArr)
				optionList.add(optionStr);
		}
		return optionList;
	}

	@Override
	public void resetField() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFieldValue() {
		return this.fieldValue;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}
}
