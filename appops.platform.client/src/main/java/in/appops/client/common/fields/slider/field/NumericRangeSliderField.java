package in.appops.client.common.fields.slider.field;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.fields.Field;
import in.appops.client.common.fields.slider.NumericRangeSlider;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NumericRangeSliderField extends Composite implements Field{

	private Configuration configuration;
	private String fieldValue;
	
	private double maxValue = 0;
	private double minValue = 0;
	private double stepValue = 0;
	public static final String NUMERIC_RANGESLIDER_MINVALUE = "minValue";
	public static final String NUMERIC_RANGESLIDER_MAXVALUE = "maxValue";
	public static final String NUMERIC_RANGESLIDER_STEPVALUE = "stepValue";
	
	private String minPosiotionImageUrl = null;
	private String maxPosiotionImageUrl = null;
	private String knobImgUrl = null;
	public static final String NUMERIC_RANGESLIDER_MINPOSITION_IMAGE = "minPosiotionImage";
	public static final String NUMERIC_RANGESLIDER_MAXPOSITION_IMAGE = "maxPosiotionImage";
	public static final String NUMERIC_RANGESLIDER_KNOB_IMAGE = "knobImage";
	
	public NumericRangeSliderField() {
		
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
		
		initializeConfiguration();
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(10);
		initWidget(verticalPanel);
		
		final NumericRangeSlider numericRngeSlider = new NumericRangeSlider(minValue, maxValue);
		numericRngeSlider.setStepSize(stepValue);
		numericRngeSlider.setCurrentValue(minValue);
		int numLabels = (int)((maxValue-minValue)/stepValue);
		numericRngeSlider.setNumLabels(numLabels);

		int widhtMultiplier = (int) (maxValue * 3)/( (int) (stepValue/5));
		if(widhtMultiplier>100)
			DOM.setElementAttribute(getElement(), "width", widhtMultiplier+"%");
		else
			setStylePrimaryName("mainPanel");
		
		numericRngeSlider.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				System.out.println("Selected Range: "+numericRngeSlider.getCurrentValue());
				
				FieldEvent fieldEvent = new FieldEvent();
				fieldEvent.setEventType(FieldEvent.EDITINITIATED);
				fieldEvent.setEventData(numericRngeSlider.getCurrentValue());
				
				AppUtils.EVENT_BUS.fireEvent(fieldEvent);
			}
		});
		
		numericRngeSlider.addMouseWheelHandler(new MouseWheelHandler() {
			@Override
			public void onMouseWheel(MouseWheelEvent arg0) {
				System.out.println("Selected Range: "+numericRngeSlider.getCurrentValue());
				FieldEvent fieldEvent = new FieldEvent();
				fieldEvent.setEventType(FieldEvent.EDITINITIATED);
				fieldEvent.setEventData(numericRngeSlider.getCurrentValue());
				
				AppUtils.EVENT_BUS.fireEvent(fieldEvent);
			}
		});
		
		verticalPanel.setSpacing(10);
		verticalPanel.add(numericRngeSlider);
	}

	private void initializeConfiguration() {
		this.maxValue = (Double)configuration.getPropertyByName(NUMERIC_RANGESLIDER_MAXVALUE);
		this.minValue = (Double)configuration.getPropertyByName(NUMERIC_RANGESLIDER_MINVALUE);
		this.stepValue = (Double)configuration.getPropertyByName(NUMERIC_RANGESLIDER_STEPVALUE);
		//TODO more to come
	}

	@Override
	public void clearField() {
		// TODO Auto-generated method stub
		
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
