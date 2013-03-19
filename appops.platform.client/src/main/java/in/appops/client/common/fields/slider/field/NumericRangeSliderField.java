package in.appops.client.common.fields.slider.field;

import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.fields.Field;
import in.appops.client.common.fields.slider.NumericRangeSlider;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NumericRangeSliderField extends Composite implements Field{

	private Configuration configuration;
	private String fieldValue;
	
	private int maxValue = 0;
	private int minValue = 0;
	private int stepValue = 0;
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
		
		final NumericRangeSlider numericRngeSlider = new NumericRangeSlider(minValue, maxValue);
		numericRngeSlider.setStepSize(stepValue);
		numericRngeSlider.setCurrentValue(minValue);
		numericRngeSlider.setNumLabels(4);

		numericRngeSlider.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				System.out.println("Selected Range: "+numericRngeSlider.getCurrentValue());
			}
		});
		
		numericRngeSlider.addMouseWheelHandler(new MouseWheelHandler() {
			@Override
			public void onMouseWheel(MouseWheelEvent arg0) {
				System.out.println("Selected Range: "+numericRngeSlider.getCurrentValue());
			}
		});

		numericRngeSlider.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent arg0) {
				System.out.println("Selected Range: "+numericRngeSlider.getCurrentValue());
			}
		});
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(10);
		verticalPanel.add(numericRngeSlider);
		verticalPanel.setStylePrimaryName("numericRangeSliderPanel");
		
		initWidget(verticalPanel);
	}

	private void initializeConfiguration() {
		this.maxValue = (Integer)configuration.getPropertyByName(NUMERIC_RANGESLIDER_MAXVALUE);
		this.minValue = (Integer)configuration.getPropertyByName(NUMERIC_RANGESLIDER_MINVALUE);
		this.stepValue = (Integer)configuration.getPropertyByName(NUMERIC_RANGESLIDER_STEPVALUE);
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
