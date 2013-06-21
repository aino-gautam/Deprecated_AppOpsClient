package in.appops.client.common.config.field.spinner;

import in.appops.client.common.config.field.BaseField;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
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
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
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
public class SpinnerField extends BaseField implements KeyDownHandler, MouseWheelHandler, MouseDownHandler, MouseUpHandler, MouseOutHandler, KeyUpHandler, KeyPressHandler, EventListener {
	
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
		public static final String SP_TYPENUMERIC = "spinnerNumeric";
		
		/** Configure Spinner to be of type List **/
		public static final String SP_TYPELIST = "spinnerList";
		
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

	}
	
	protected HorizontalPanel fieldPanel;
	protected TextBox spinnerBox;
	protected HorizontalPanel fieldSideErrorPanel ;
	
	/********************* Spinner Arrow Components     ****************************/
	protected VerticalPanel arrowPanel;
	protected ToggleButton spinUpArrow;
	protected ToggleButton spinDownArrow;
	
	
	private int initialSpeed = 7;
	private boolean enabled;
	private boolean spinUp;

	protected SpinnerField(){
		super();
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
		basePanel = new VerticalPanel();
	    fieldSideErrorPanel = new HorizontalPanel();
		fieldPanel = new HorizontalPanel();
	}

	@Override
	public void configure() {
		super.configure();
		/** Apply Css to the spinner box, if not configured value default css applied **/
		spinnerBox.setStylePrimaryName(getBoxPrimCss());
		spinnerBox.addStyleName(getBoxDepCss());
	}

	/**
	 * Returns the Spinner type. Defaults to {@link SpinnerFieldConstant#SP_TYPENUMERIC}
	 * Values {@link SpinnerFieldConstant#SP_TYPENUMERIC}, {@link SpinnerFieldConstant#SP_TYPELIST}
	 * @return
	 */
	protected String getSpinnerType() {
		String type = SpinnerFieldConstant.SP_TYPENUMERIC;
		if(getConfigurationValue(SpinnerFieldConstant.SP_TYPE) != null) {
			type = getConfigurationValue(SpinnerFieldConstant.SP_TYPE).toString();
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
	protected boolean isCircular() {
		boolean circular = false;
		if(getConfigurationValue(SpinnerFieldConstant.SP_CIRCULAR) != null) {
			circular = (Boolean)getConfigurationValue(SpinnerFieldConstant.SP_CIRCULAR);
		}
		return circular;
	}

	@Override
	protected void setErrorSide() {
		fieldSideErrorPanel.add(errorLabel);
		fieldSideErrorPanel.setCellVerticalAlignment(errorLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		errorLabel.setStylePrimaryName("appops-SpinnerErrorRightCls");
		errorLabel.setVisible(false);
	}
	
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
	public void create() {
		super.create();
		
		fieldSideErrorPanel.add(fieldPanel);
		basePanel.add(fieldSideErrorPanel);

		if(getErrorPosition().equals(BaseFieldConstant.BF_ERRPOS)) {
			setErrorTop();
		} else if(getErrorPosition().equals(BaseFieldConstant.BF_ERRBOTTOM)) {
			setErrorBottom();
		} else if(getErrorPosition().equals(SpinnerFieldConstant.BF_ERRSIDE)) {
			setErrorSide();
		}

		spinUpArrow.setStylePrimaryName("appops-SpinnerUpArrow");
		spinDownArrow.setStylePrimaryName("appops-SpinnerDownArrow");
		
		arrowPanel.add(spinUpArrow);
		arrowPanel.add(spinDownArrow);
		arrowPanel.setCellVerticalAlignment(spinUpArrow, HasVerticalAlignment.ALIGN_MIDDLE);
		arrowPanel.setCellVerticalAlignment(spinDownArrow, HasVerticalAlignment.ALIGN_MIDDLE);
		
		spinnerBox.addKeyDownHandler(this);
		spinnerBox.addKeyUpHandler(this);
		spinnerBox.addKeyPressHandler(this);
		
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

	    arrowPanel.setWidth("17px");
	   // Event.setEventListener(spinnerBox.getElement(), this);
	    sinkEvents(Event.ONPASTE);
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		int keyCode = event.getNativeKeyCode();
		if(keyCode == KeyCodes.KEY_UP){
			spinUp();
		} else if((keyCode == KeyCodes.KEY_DOWN)) {
			spinDown();
		}
	}

	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		if(event.isNorth()) {
			spinUp();
		} else if(event.isSouth()) {
			spinDown();
		}
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {

		if(event.getSource().equals(spinUpArrow)) {
			spinUp = true;
			spinUp();
			setEnabled(true);
		} else if(event.getSource().equals(spinDownArrow)) {
			spinUp = false;
			spinDown();
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
	
	public void setEnabled(boolean enabled) { 
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
					spinUp();
				} else {
					spinDown();
				}
			}

		}
	};

	@Override
	public void onMouseOut(MouseOutEvent arg0) {
		if(enabled) {
			timer.cancel();
			setEnabled(false);
		}		
	}

	@Override
	public void onKeyUp(KeyUpEvent event) { }

	@Override
	public void onKeyPress(KeyPressEvent event) { }
	

	@Override 
	public void onBrowserEvent(Event event) { }
	
	protected void spinUp() { }
	
	protected void spinDown() {	}

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
}