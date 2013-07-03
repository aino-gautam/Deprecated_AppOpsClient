package in.appops.client.common.config.field.rangeslider;

import in.appops.client.common.config.field.BaseField;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
Field class to represent a {@link RangeSliderField}
@author pallavi@ensarm.com

<p>
<h3>Configuration</h3>
<a href="RangeSliderField.RangeSliderFieldConstant.html">Available configurations</a>
</p>

<p>
<h3>Example</h3>

RangeSliderField rangeSliderField = new RangeSliderField();<br>
Configuration conf = new Configuration();<br>
conf.setPropertyByName(RangeSliderFieldConstant.SLIDER_MODE,RangeSliderFieldConstant.NUM_SLIDER);<br>
conf.setPropertyByName(RangeSliderFieldConstant.BF_PCLS,"sliderPanel");<br>
conf.setPropertyByName(RangeSliderFieldConstant.MINVAL,100d);<br>
conf.setPropertyByName(RangeSliderFieldConstant.MAXVAL,200d);<br>
conf.setPropertyByName(RangeSliderFieldConstant.STEPVAL,50d);<br>
rangeSliderField.setConfiguration(conf);<br>
rangeSliderField.configure();<br>
rangeSliderField.create();<br>

</p>*/
public class RangeSliderField extends BaseField{

	private VerticalPanel basePanel ;
	private NumericRangeSlider numericRngeSlider;
	private StringRangeSlider strRangeSlider;
	
	public RangeSliderField(){
		
	}
	
	/******************************** BaseField Overriden methods ****************************************/

	@Override
	public void create() {
		getBasePanel().add(basePanel, DockPanel.CENTER);
	}
	
	@Override
	public void configure() {
		
		basePanel = new VerticalPanel();
		
		String mode = getRangeSliderMode();
		
		if (mode.equals(RangeSliderFieldConstant.NUM_SLIDER)) {
			
			numericRngeSlider = new NumericRangeSlider(getMinValue(),getMaxValue());
			numericRngeSlider.setStepSize(getStepValue());
			numericRngeSlider.setCurrentValue(getMinValue());
			numericRngeSlider.setSliderBarLineCss(getSliderLineCss());
			numericRngeSlider.createUI();
			
			basePanel.add(numericRngeSlider);
		}else if(mode.equals(RangeSliderFieldConstant.STRING_SLIDER)) {
			strRangeSlider = new StringRangeSlider(1,getRangeSliderItemList().size());
			strRangeSlider.setListOfOption(getRangeSliderItemList());
			strRangeSlider.setSliderBarLineCss(getSliderLineCss());
			strRangeSlider.setStepSize(1);
			strRangeSlider.setCurrentValue(1);
			strRangeSlider.createUI();
			basePanel.add(strRangeSlider);
		}
		
		basePanel.setSpacing(10);
		
		if(getBaseFieldPrimCss() != null)
			basePanel.setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldCss() != null)
			basePanel.addStyleName(getBaseFieldCss());
	}
	
	@Override
	public Object getValue() {
		
		if(getRangeSliderMode().equals(RangeSliderFieldConstant.NUM_SLIDER)){
			return numericRngeSlider.getCurrentValue();
		}else if(getRangeSliderMode().equals(RangeSliderFieldConstant.STRING_SLIDER)){
			return getRangeSliderItemList().get(((int)strRangeSlider.getCurrentValue())-1);
		}
		return null;
	}
	
	/******************************  Configuration methods **************************/
	
	/**
	 * Method returns the range slider mode..
	 * @return
	 */
	private String getRangeSliderMode() {
		
		String mode = RangeSliderFieldConstant.NUM_SLIDER;
		
		if(getConfigurationValue(RangeSliderFieldConstant.SLIDER_MODE) != null) {
			
			mode = getConfigurationValue(RangeSliderFieldConstant.SLIDER_MODE).toString();
		}
		return mode;
	}
	
	/**
	 * Method returns the slider line css.
	 * @return
	 */
	private String getSliderLineCss() {
		
		String css = "gwt-SliderBar-line";
		
		if(getConfigurationValue(RangeSliderFieldConstant.SLIDER_LINE_CSS) != null) {
			
			css = getConfigurationValue(RangeSliderFieldConstant.SLIDER_LINE_CSS).toString();
		}
		return css;
	}
	
	/**
	 * Method returns the min value for range slider .
	 * @return
	 */
	private Double getMinValue() {
		
		Double minVal = 0d;
		
		if(getConfigurationValue(RangeSliderFieldConstant.MINVAL) != null) {
			
			minVal = (Double)getConfigurationValue(RangeSliderFieldConstant.MINVAL);
		}
		return minVal;
	}
	
	/**
	 * Method returns the max value for range slider .
	 * @return
	 */
	private Double getMaxValue() {
		
		Double maxVal = 100d;
		
		if(getConfigurationValue(RangeSliderFieldConstant.MAXVAL) != null) {
			
			maxVal = (Double)getConfigurationValue(RangeSliderFieldConstant.MAXVAL);
		}
		return maxVal;
	}
	
	/**
	 * Method returns the step value for range slider .
	 * @return
	 */
	private Double getStepValue() {
		
		Double stepVal = 20d;
		
		if(getConfigurationValue(RangeSliderFieldConstant.STEPVAL) != null) {
			
			stepVal = (Double)getConfigurationValue(RangeSliderFieldConstant.STEPVAL);
		}
		return stepVal;
	}
	
	/**
	 * Method returns the list of items for slider;
	 * @return
	 */
	private ArrayList<String> getRangeSliderItemList() {
		ArrayList<String> listOfItems = null;
		if(getConfigurationValue(RangeSliderFieldConstant.ITEMS_LIST) != null) {
			listOfItems = (ArrayList<String>) getConfigurationValue(RangeSliderFieldConstant.ITEMS_LIST);
		}
		return listOfItems;
	}
		
	/***************************** Range slider constants **************************************/
	
	public interface RangeSliderFieldConstant extends BaseFieldConstant{
		
		/** Specifies the slider mode **/
		public static final String SLIDER_MODE = "sliderMode";
		
		/** Specifies the string range slider mode **/
		public static final String STRING_SLIDER = "stringSlider";
		
		/** Specifies the numeric slider **/
		public static final String NUM_SLIDER = "numericSlider";
		
		/** Specifies the minimun value for  numeric range slider **/
		public static final String MINVAL = "minVal";
		
		/** Specifies the max value for  numeric range slider **/
		public static final String MAXVAL = "maxVal";
		
		/** Specifies the step value for numeric range slider **/
		public static final String STEPVAL = "stepval";
		
		/** Specifies the items list for string range slider **/
		public static final String ITEMS_LIST = "itemsList";
		
		/** Specifies the slider bar line css **/
		public static final String SLIDER_LINE_CSS = "sliderLineCss";
										
	}
}