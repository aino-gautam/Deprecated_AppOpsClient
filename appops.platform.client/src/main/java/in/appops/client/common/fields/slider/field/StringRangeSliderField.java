package in.appops.client.common.fields.slider.field;

import java.util.ArrayList;

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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
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
		
		Label headerLbl = new Label("Selected Value: ");
		final TextBox curBox = new TextBox();
		final VerticalPanel verticalPanel = new VerticalPanel();
		
		final ArrayList<String> listOfOption = getOptionListFromConfig();
		
		final StringRangeSlider stringRangeSlider = new StringRangeSlider(1, listOfOption.size());
		
		stringRangeSlider.setListOfOption(listOfOption);
		stringRangeSlider.setStepSize(1);
		stringRangeSlider.setCurrentValue(1);
		stringRangeSlider.setNumLabels(listOfOption.size()-1);

		stringRangeSlider.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				String str = listOfOption.get(((int)stringRangeSlider.getCurrentValue())-1);
				curBox.setText(str);
			}
		});
		
		stringRangeSlider.addMouseWheelHandler(new MouseWheelHandler() {
			@Override
			public void onMouseWheel(MouseWheelEvent arg0) {
				String str = listOfOption.get(((int)stringRangeSlider.getCurrentValue())-1);
				curBox.setText(str);
			}
		});

		stringRangeSlider.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent arg0) {
				String str = listOfOption.get(((int)stringRangeSlider.getCurrentValue())-1);
				curBox.setText(str);
			}
		});

		verticalPanel.setSpacing(10);
		verticalPanel.add(stringRangeSlider);
		verticalPanel.add(new HTML("<BR>"));
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.add(headerLbl);
		horizontalPanel.add(curBox);
		
		verticalPanel.add(horizontalPanel);
		
		verticalPanel.add(curBox);
		verticalPanel.setStylePrimaryName("stringRangeSliderPanel");
		initWidget(verticalPanel);
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
