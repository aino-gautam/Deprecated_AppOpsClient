package in.appops.client.common.config.field.spinner;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import java.util.ArrayList;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * TODO Decide On basis of Spinner Type from configuration the type of spinner viz. Numeric or List
 * @author nitish@ensarm.com
 *
 */
public class SpinnerField extends BaseField implements KeyDownHandler, MouseWheelHandler, MouseDownHandler, MouseUpHandler, MouseOutHandler, KeyUpHandler, KeyPressHandler, EventListener, BlurHandler {
	
	/**
	 *	Spinner Configuration Property Constants 
	 */
	public interface SpinnerFieldConstant extends BaseFieldConstant {
		/** Style class primary for spinner text box  **/ 
		public static final String SP_BXPCLS = "boxPrimaryCss";
		
		/** Style class dependent for spinner text box. **/
		public static final String SP_BXDCLS = "boxDependentCss";
		
		/** Type of spinner. Defaults to {@link NumericSpinnerField} **/
		public static final String SP_TYPE = "spinnerType";
		
		/** Configure Spinner to be of type Numeric **/
		public static final int SP_TYPENUMERIC = 1;
		
		/** Configure Spinner to be of type List **/
		public static final int SP_TYPELIST = 2;
		
		/** Specifies a numeric interval by which the field's value will be incremented or decremented when the user invokes the spinner **/
		public static final String SP_STEP = "step";
		
		/**Specifies a unit qualifier for spinner e.g. "%", "cm", "tables" etc. **/
		public static final String SP_UNIT = "unit";
		
		/** The maximum allowed value. Will be used by the field's validation logic **/
		public static final String SP_MAXVAL = "max";
		
		/** The minimum allowed value. Will be used by the field's validation logic **/
		public static final String SP_MINVAL = "min";
		
		/** Specifies whether the spinner should continuing rolling value when the maxValue or minValue are reached. Applied only when maxValue and minValue are provided. Defaults to false  **/
		public static final String SP_CIRCULAR = "circular";

		/** Specifies whether the spinner should continuing rolling value when the maxValue or minValue are reached. Applied only when maxValue and minValue are provided. Defaults to false  **/
		public static final String SP_ALLOWBLNK = "allowBlank";
		
		/** Specifies whether decimal to allow decimal value. Defaults to false **/
		public static final String SP_ALLOWDEC = "allowDecimal";
		
		/** The maximum precision to display after the decimal separator **/
		public static final String SP_DECPRECSION = "decimalPrecision";
		
		/** Single Character to allow as the decimal separator **/
		public static final String SP_DECSEPARTOR = "decimalSeperator";
		
		/** Error text to display if the maximum value validation fails. {@link SpinnerFieldConstant#SP_MAXVAL}**/
		public static final String SP_ERRMSGMAX = "maxMsg";
		
		/** Error text to display if the minimum value validation fails. {@link SpinnerFieldConstant#SP_MINVAL} **/
		public static final String SP_ERRMSGMIN = "minMsg";
		
		/** Error text to display if the value is negative when minValue is set to > 0 **/
		public static final String SP_ERRMSGNEG = "negMsg";
		
		/**The error text to display if the allowBlank validation fails  **/
		public static final String SP_ERRMSGBLNK = "blankMsg";
		
		/** The error text to use when data is invalid **/
		public static final String SP_ERRMSGINVLD = "invalidMsg";
		
		/** ArrayList of static data to be provided to the List Spinner  **/
		public static final String SP_VALUELIST = "valueList";
		
		/** Index of default value to be shown from the {@link SpinnerFieldConstant#SP_VALUELIST}.  **/
		public static final String SP_VALUEIDX = "valueListIndex";

		/** Number of digits to follow the decimal  **/
		public static final String SP_DECPRECISION = "decimalPrecision";

	}
	
		
	protected HorizontalPanel fieldPanel;
	protected TextBox spinnerBox;
	protected HorizontalPanel fieldSideErrorPanel ;
	
	protected VerticalPanel arrowPanel;
	protected ToggleButton spinUpArrow;
	protected ToggleButton spinDownArrow;
	
	
	private int initialSpeed = 7;
	private boolean enabled;
	private boolean spinUp;
	
	private SpinnerModel model;
	
	/*********************************/

	public SpinnerField(){
		super();
	}


	/************************** ****************************************/
	/**
	 * Returns the Spinner type. Defaults to {@link SpinnerFieldConstant#SP_TYPENUMERIC}
	 * Values {@link SpinnerFieldConstant#SP_TYPENUMERIC}, {@link SpinnerFieldConstant#SP_TYPELIST}
	 * @return
	 */
	protected int getSpinnerType() {
		int type = SpinnerFieldConstant.SP_TYPENUMERIC;
		if(getConfigurationValue(SpinnerFieldConstant.SP_TYPE) != null) {
			type = (Integer)getConfigurationValue(SpinnerFieldConstant.SP_TYPE);
		}
		return type;
	}
	

	@Override
	protected String getBaseFieldPrimCss() {
		String primaryCss = super.getBaseFieldPrimCss();
		
		if(primaryCss == null) {
			return "appops-SpinnerFieldPrimary";
		}
		return primaryCss;
	}

	protected String getBaseFieldCss() {
		String depCss = super.getBaseFieldCss();
		if(depCss == null) {
			return "appops-SpinnerFieldDependent";
		}	
		return depCss;
	}

	/**
	 * Returns the primary style to be applied to the textbox of the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getBoxPrimCss() {
		String primaryCss = "appops-SpinnerBoxPrimary";
		if(getConfigurationValue(SpinnerFieldConstant.SP_BXPCLS) != null) {
			primaryCss = getConfigurationValue(SpinnerFieldConstant.SP_BXPCLS).toString();
		}
		return primaryCss;
	}
	
	/**
	 * Returns the dependent style to be applied to the textbox of the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getBoxDepCss() {
		String dependentCss = "appops-SpinnerBoxDependent";
		if(getConfigurationValue(SpinnerFieldConstant.SP_BXDCLS) != null) {
			dependentCss = getConfigurationValue(SpinnerFieldConstant.SP_BXDCLS).toString();
		}
		return dependentCss;
	}
	
	/**
	 * Returns whether the spinner is to be operated in a cirular manner.
	 * If max value is reached, restarts from the min value and vice versa. Defaults to false
	 * @return
	 */
	private boolean isCircular() {
		boolean circular = false;
		if(getConfigurationValue(SpinnerFieldConstant.SP_CIRCULAR) != null) {
			circular = (Boolean)getConfigurationValue(SpinnerFieldConstant.SP_CIRCULAR);
		}
		return circular;
	}
	
	private String getUnit() {
		String unit = "";
		if(getConfigurationValue(SpinnerFieldConstant.SP_UNIT) != null) {
			unit = getConfigurationValue(SpinnerFieldConstant.SP_UNIT).toString();
		}
		return unit;
	}
	
	private String getBlankErrMsg() {
		String blnkMsg = "This field is required";
		if(getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGBLNK) != null) {
			blnkMsg = getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGBLNK).toString();
		}
		return blnkMsg;
	}

	private String getInvalidErrMsg() {
		String invalidMsg = "Invalid input - not a number";
		if(getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGINVLD) != null) {
			invalidMsg = getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGINVLD).toString();
		}
		return invalidMsg;
	}

	private String getMaxErrMsg() {
		String maxMsg = "Maximum value for this field is " + ((NumericModel)getModel()).fixPrecision(getMax(), getPrecision());
		if(getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGMAX) != null) {
			maxMsg = getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGMAX).toString();
		}
		return maxMsg;
	}

	private String getMinErrMsg() {
		String minMsg = "Minimum value for this field is " + ((NumericModel)getModel()).fixPrecision(getMin(), getPrecision());
		if(getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGMIN) != null) {
			minMsg = getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGMIN).toString();
		}
		return minMsg;
	}
	
	private String getNegErrMsg() {
		String negMsg = "The value cannot be negative";
		if(getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGNEG) != null) {
			negMsg = getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGNEG).toString();
		}
		return negMsg;
	}
	
	private Integer getStep() {
		int step = 1;
		try {
			if(getConfigurationValue(SpinnerFieldConstant.SP_STEP) != null) {
				step = (Integer) getConfigurationValue(SpinnerFieldConstant.SP_STEP);
			}
			return step;
		} catch (Exception e) {
			// CONFIG ERROR -- If step value is anything other than a numeric value. 
			return null;
		}
	}
	
	private Float getMax() {
		Float max = Float.MAX_VALUE;
		try {
			if(getConfigurationValue(SpinnerFieldConstant.SP_MAXVAL) != null) {
				max = (Float) getConfigurationValue(SpinnerFieldConstant.SP_MAXVAL);
			}
		} catch (Exception e) {
			// CONFIG ERROR -- If max value is anything other than a numeric value. 
		}
		return max;
	}

	private Float getMin() {
		Float min = Float.MIN_VALUE;
		try {
			if(getConfigurationValue(SpinnerFieldConstant.SP_MINVAL) != null) {
				min = (Float) getConfigurationValue(SpinnerFieldConstant.SP_MINVAL);
			}
		} catch (Exception e) {
			// CONFIG ERROR -- If min value is anything other than a numeric value. 
		}
		return min;
	}

	private boolean isAllowDecimal() {
		boolean allowDec = false;
		if(getConfigurationValue(SpinnerFieldConstant.SP_ALLOWDEC) != null) {
			allowDec = (Boolean)getConfigurationValue(SpinnerFieldConstant.SP_ALLOWDEC);
		}
		return allowDec;
	}
	
	private Integer getPrecision() {
		int precision = 0;
		try {
			if(getConfigurationValue(SpinnerFieldConstant.SP_DECPRECISION) != null) {
				precision = (Integer) getConfigurationValue(SpinnerFieldConstant.SP_DECPRECISION);
			}
			if(precision < 0) {
				precision = - precision;
			}
			return precision;
		} catch (Exception e) {
			return 0;
		}
	}
	
	private int getDefaultValueIndex() {
		int index = 0;
		if(getConfigurationValue(SpinnerFieldConstant.SP_VALUEIDX) != null) {
			int val = (Integer)getConfigurationValue(SpinnerFieldConstant.SP_VALUEIDX);
			if(val < getValueList().size()) {
				index = val;
			}
		}
		return index;
	}
	
	private ArrayList getValueList() {
		ArrayList valueList = null;
		if(getConfigurationValue(SpinnerFieldConstant.SP_VALUELIST) != null) {
			valueList = (ArrayList)getConfigurationValue(SpinnerFieldConstant.SP_VALUELIST);
		}
		return valueList;
	}
	
	
	
	/******************************** ****************************************/
	
	@Override
	protected void setErrorInline () {
		spinnerBox.addStyleName("appops-SpinnerFieldInvalidInline");
	}
	
	@Override
	protected void clearInvalidMarkers() {
		super.clearInvalidMarkers();
		spinnerBox.removeStyleName("appops-SpinnerFieldInvalidInline");
	}
	
	@Override
	public void setValue(Object value) {
		if(value.toString() != null) {
			spinnerBox.setText(value.toString());
		}
	}
	
	@Override
	public Object getValue() {
		String fieldValue = spinnerBox.getText().trim();
		return fieldValue;
	}

	@Override
	public String getFieldValue() {
		String fieldValue = spinnerBox.getText().trim();
		return fieldValue;
	}
	
	@Override
	public ArrayList<String> getErrors(String value) {
		ArrayList<String> errors = new ArrayList<String>();
		boolean valid = true;
		errors.clear();
		if(value != null) {
			if(!isAllowBlank() && value.toString().trim().equals("")) {
				errors.add(getBlankErrMsg());
				return errors;
			}
			if(!value.toString().matches("-?\\d+(\\.\\d+)?")) {
				errors.add(getInvalidErrMsg());
				return errors;
			}
			if(getMin() > 0 && Float.parseFloat(value.toString()) < 0) {
				errors.add(getNegErrMsg());
				valid = false;
			}
			if(getMax() != null && Float.parseFloat(value.toString()) > getMax()) {
				errors.add(getMaxErrMsg());
				valid = false;
			} 
			if(getMin() != null && Double.parseDouble(value.toString()) < getMin()) {
				errors.add(getMinErrMsg());
				valid = false;
			}
		}
		return errors;
	}
	
	/**
	 * Instantiates the member variables
	 */
	@Override
	public void initialize() {
		super.initialize();
		spinnerBox = new TextBox();
		arrowPanel = new VerticalPanel();
		spinUpArrow = new ToggleButton();
		spinDownArrow = new ToggleButton();
	    fieldSideErrorPanel = new HorizontalPanel();
		fieldPanel = new HorizontalPanel();
	}

	@Override
	public void configure() {
		super.configure();
		/** Apply Css to the spinner box, if not configured value default css applied **/
		spinnerBox.setStylePrimaryName(getBoxPrimCss());
		spinnerBox.addStyleName(getBoxDepCss());
		
		
		if(getSpinnerType() == SpinnerFieldConstant.SP_TYPENUMERIC) {
			setModel(new NumericModel());
			configureForNumber();
		} else {
			setModel(new ListModel());
			configureForList();
		}
		configureModel();
	}

	@Override
	public void create() {
		super.create();
		
		fieldSideErrorPanel.add(fieldPanel);
		basePanel.add(fieldSideErrorPanel,DockPanel.CENTER);

		spinUpArrow.setStylePrimaryName("appops-SpinnerUpArrow");
		spinDownArrow.setStylePrimaryName("appops-SpinnerDownArrow");
		
		arrowPanel.add(spinUpArrow);
		arrowPanel.add(spinDownArrow);
		arrowPanel.setCellVerticalAlignment(spinUpArrow, HasVerticalAlignment.ALIGN_MIDDLE);
		arrowPanel.setCellVerticalAlignment(spinDownArrow, HasVerticalAlignment.ALIGN_MIDDLE);
		
		spinnerBox.addKeyDownHandler(this);
		spinnerBox.addKeyUpHandler(this);
		spinnerBox.addKeyPressHandler(this);
		spinnerBox.addBlurHandler(this);
		
		spinUpArrow.addMouseDownHandler(this);
		spinUpArrow.addMouseUpHandler(this);
		spinUpArrow.addMouseWheelHandler(this);
		spinUpArrow.addMouseOutHandler(this);
		
		spinDownArrow.addMouseDownHandler(this);
		spinDownArrow.addMouseUpHandler(this);
		spinDownArrow.addMouseWheelHandler(this);
		spinDownArrow.addMouseOutHandler(this);

		spinUpArrow.addKeyDownHandler(this);
		spinDownArrow.addKeyDownHandler(this);
				
		fieldPanel.add(spinnerBox);
		fieldPanel.add(arrowPanel);
	    
	    NodeList<com.google.gwt.dom.client.Element> nodeList = fieldPanel.getElement().getElementsByTagName("td");
	    Node td1Node = nodeList.getItem(0);
	    Element td1Element = (Element) Element.as(td1Node);
	    td1Element.setClassName("appops-spinner-border-box");

	    if(!getUnit().equals("")) {
	    	setUnit();
	    }
	    
	    arrowPanel.setWidth("17px");
	   // Event.setEventListener(spinnerBox.getElement(), this);
	    sinkEvents(Event.ONPASTE);
	    
	}

	/************************************* -- END --**************************************************/

	private void configureForNumber() {
		NumericModel numModel = (NumericModel)getModel();
		if(getMin() > getMax()) {
			// CONFIG EXCEPTION -- If min value is > than max value
		}

		if(getStep() < 1) {
			// CONFIG EXCEPTION -- Step should be a +ve no. But can Step be -ve ????
		}
		
		if(getStep() > (getMax() - getMin())) {
			// CONFIG EXCEPTION -- Step should not be > than the difference of max and min.
			// Shall exception be thrown in this case???
		}
		
		if(getDefaultValue() == null || (numModel.parseValue(getDefaultValue().toString()) == null)){
			setValue("");
		} else {
			String value = numModel.fixPrecision(numModel.parseValue(getDefaultValue().toString()), getPrecision());
			setValue(value);
		}
	}
	
	private void configureForList() {
		ListModel listModel = (ListModel)getModel();
		listModel.setValueList(getValueList());
		listModel.setCurrentIndex(getDefaultValueIndex());
		listModel.setCircular(isCircular());
		spinnerBox.setText(listModel.getValue().toString());
		spinnerBox.setReadOnly(true);
	}

	private void configureModel() {
		if(getModel() instanceof NumericModel) {
			NumericModel numModel = (NumericModel)getModel();
			numModel.setMax(getMax());
			numModel.setMin(getMin());
			numModel.setStep(getStep());
			numModel.setValue(numModel.parseValue(getValue().toString()));
		}
	}

	private void setUnit() {
		NodeList<com.google.gwt.dom.client.Element> nodeList = fieldPanel.getElement().getElementsByTagName("td");
	    Node td1Node = nodeList.getItem(0);
	    Element td1Element = (Element) Element.as(td1Node);
	    Element span = DOM.createSpan();
	    span.setInnerText(getUnit());
	    span.setClassName("appops-SpinnerUnit");
	    td1Element.appendChild(span);
	}
	
	public void setSpinnerValue() {
		if(getModel() instanceof NumericModel) {
			NumericModel numModel = (NumericModel)getModel();
			setValue(numModel.fixPrecision(numModel.parseValue(getModel().getValue().toString()), getPrecision()));
		} else if(getModel() instanceof ListModel) {
			ListModel listModel = (ListModel)getModel();
			setValue(listModel.getValue().toString());
		}
	}
	private void onSpinUp() {
		getModel().spinUp();
		setSpinnerValue();

		if(isValidateOnChange()) {
			validate();
		}
		
		FieldEvent spinUpEvent = new FieldEvent(FieldEvent.SPN_SPINUP, null); 
		
		AppUtils.EVENT_BUS.fireEventFromSource(spinUpEvent, this);
	}
	
	private void onSpinDown() {
		getModel().spinDown();
		setSpinnerValue();

		if(isValidateOnChange()) {
			validate();
		}
		
		FieldEvent spinDownEvent = new FieldEvent(FieldEvent.SPN_SPINDOWN, null); 
		
		AppUtils.EVENT_BUS.fireEventFromSource(spinDownEvent, this);
	}
	
	
	@Override
	public void onKeyUp(KeyUpEvent event) {
		int keyCode = event.getNativeKeyCode();

		switch (keyCode) {
			case KeyCodes.KEY_LEFT:
			case KeyCodes.KEY_RIGHT:
			case KeyCodes.KEY_BACKSPACE:
			case KeyCodes.KEY_DELETE:
			case KeyCodes.KEY_TAB:
				if(isValidateOnChange()) {
					if(validate()) {
						updateModel();
					}
				}
				return;
		}		
	}
	
	@Override
	public void onKeyDown(KeyDownEvent event) {
		int keyCode = event.getNativeKeyCode();
		if(keyCode == KeyCodes.KEY_UP) {
			onSpinUp();
		} else if((keyCode == KeyCodes.KEY_DOWN)) {
			onSpinDown();
		}
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		
		if(!Character.isDigit(event.getCharCode()) && event.getCharCode() != '-' && event.getCharCode() != '.') {
			event.preventDefault();
			return;
		}
		if(getMin() > 0 && event.getCharCode() == '-') {
			event.preventDefault();
			return;
		}
		if(!isAllowDecimal() && event.getCharCode() == '.') {
			event.preventDefault();
			return;
		}
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
            	if(isValidateOnChange()) {
        			if(validate()) {
        				updateModel();
        				FieldEvent valueEvent = new FieldEvent(FieldEvent.VALUECHANGED, model.getValue()); 
        				AppUtils.EVENT_BUS.fireEventFromSource(valueEvent, SpinnerField.this);
        			}
            	}
            }
        });
	}
	
	@Override
	public void onBrowserEvent(Event event) { 
	    super.onBrowserEvent(event); 
	    switch (event.getTypeInt()) { 
	    case Event.ONPASTE: 
	    	Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	            @Override
	            public void execute() {
	            	if(isValidateOnChange()) {
	            		if(validate()) {
	            			updateModel();
	            		}
	            	}
	            }
	        });
	    } 
	}
	
	public void updateModel() {
		if(getModel() instanceof NumericModel) {
			NumericModel numModel = (NumericModel)getModel();
			getModel().setValue(numModel.parseValue(getValue().toString()));
		} else if(getModel() instanceof ListModel) {
			ListModel listModel = (ListModel)getModel();
			int indx = listModel.getValueList().indexOf(getValue().toString());
			listModel.setCurrentIndex(indx);
		}
	}


	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		if(event.isNorth()) {
			onSpinUp();
		} else if(event.isSouth()) {
			onSpinDown();
		}
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {

		if(event.getSource().equals(spinUpArrow)) {
			spinUp = true;
			onSpinUp();
			setEnabled(true);
		} else if(event.getSource().equals(spinDownArrow)) {
			spinUp = false;
			onSpinDown();
			setEnabled(true);
		}
		if(enabled) {
			timer.cancel();
			timer.scheduleRepeating(30);
		}
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		if(enabled) {
			timer.cancel();
			setEnabled(false);
		}
	}
	
	@Override
	public void onMouseOut(MouseOutEvent arg0) {
		if(enabled) {
			timer.cancel();
			setEnabled(false);
		}		
	}
	
	private void setEnabled(boolean enabled) { 
		this.enabled = enabled;
		if(!enabled) {
			timer.cancel();
		}
	}
	
	private final Timer timer = new Timer() {
		private int counter = 0;
		private int speed = initialSpeed;

		@Override
		public void cancel() {
			super.cancel();
			speed = initialSpeed;
			counter = 0;
		}

		@Override
		public void run() {
			counter++;
			if (speed <= 0 || counter % speed == 0) {
				speed--;
				counter = 0;
				if (spinUp) {
					onSpinUp();
				} else {
					onSpinDown();
				}
			}

		}
	};

	@Override
	public void onBlur(BlurEvent event) {
		if(isValidateOnBlur()) {
			if(validate()) {
				updateModel();
				setSpinnerValue();
			}
		}		
	}


	public SpinnerModel getModel() {
		return model;
	}


	public void setModel(SpinnerModel model) {
		this.model = model;
	}

}