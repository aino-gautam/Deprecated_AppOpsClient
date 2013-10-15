package in.appops.client.common.config.field.spinner;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private Logger logger = Logger.getLogger(getClass().getName());

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
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getSpinnerType method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_TYPE) != null) {
				type = (Integer)viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_TYPE);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getSpinnerType method :"+e);
		}
		return type;
	}
	

	@Override
	protected String getBaseFieldPrimCss() {
		String primaryCss = super.getBaseFieldPrimCss();
		
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getBaseFieldPrimCss method ");
			if(primaryCss == null) {
				return "appops-SpinnerFieldPrimary";
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getBaseFieldPrimCss method :"+e);

		}
		return primaryCss;
	}

	protected String getBaseFieldDependentCss() {
		String depCss = super.getBaseFieldDependentCss();
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getBaseFieldCss method ");
			if(depCss == null) {
				return "appops-SpinnerFieldDependent";
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getBaseFieldCss method :"+e);

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
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getBoxPrimCss method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_BXPCLS) != null) {
				primaryCss = viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_BXPCLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getBoxPrimCss method :"+e);

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
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getBoxDepCss method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_BXDCLS) != null) {
				dependentCss = viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_BXDCLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getBoxDepCss method :"+e);

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
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In isCircular method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_CIRCULAR) != null) {
				circular = (Boolean)viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_CIRCULAR);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in isCircular method :"+e);

		}
		return circular;
	}
	
	private String getUnit() {
		String unit = "";
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getUnit method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_UNIT) != null) {
				unit = viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_UNIT).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getUnit method :"+e);

		}
		return unit;
	}
	
	private String getBlankErrMsg() {
		String blnkMsg = "This field is required";
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getBlankErrMsg method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGBLNK) != null) {
				blnkMsg = viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGBLNK).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getBlankErrMsg method :"+e);

		}
		return blnkMsg;
	}

	private String getInvalidErrMsg() {
		String invalidMsg = "Invalid input - not a number";
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getInvalidErrMsg method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGINVLD) != null) {
				invalidMsg = viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGINVLD).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getInvalidErrMsg method :"+e);

		}
		return invalidMsg;
	}

	private String getMaxErrMsg() {
		String maxMsg = "Maximum value for this field is " + ((NumericModel)getModel()).fixPrecision(getMax(), getPrecision());
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getMaxErrMsg method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGMAX) != null) {
				maxMsg = viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGMAX).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getMaxErrMsg method :"+e);

		}
		return maxMsg;
	}

	private String getMinErrMsg() {
		String minMsg = "Minimum value for this field is " + ((NumericModel)getModel()).fixPrecision(getMin(), getPrecision());
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getMinErrMsg method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGMIN) != null) {
				minMsg = viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGMIN).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getMinErrMsg method :"+e);

		}
		return minMsg;
	}
	
	private String getNegErrMsg() {
		String negMsg = "The value cannot be negative";
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getNegErrMsg method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGNEG) != null) {
				negMsg = viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGNEG).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getNegErrMsg method :"+e);

		}
		return negMsg;
	}
	
	private Integer getStep() {
		int step = 1;
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getStep method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_STEP) != null) {
				step = (Integer) viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_STEP);
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
			logger.log(Level.INFO, "[SpinnerField] ::In getMax method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_MAXVAL) != null) {
				max = (Float) viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_MAXVAL);
			}
		} catch (Exception e) {
			// CONFIG ERROR -- If max value is anything other than a numeric value. 
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getMax method :"+e);

		}
		return max;
	}

	private Float getMin() {
		Float min = Float.MIN_VALUE;
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getMin method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_MINVAL) != null) {
				min = (Float) viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_MINVAL);
			}
		} catch (Exception e) {
			// CONFIG ERROR -- If min value is anything other than a numeric value. 
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getMin method :"+e);

		}
		return min;
	}

	private boolean isAllowDecimal() {
		boolean allowDec = false;
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In isAllowDecimal method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_ALLOWDEC) != null) {
				allowDec = (Boolean)viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_ALLOWDEC);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in isAllowDecimal method :"+e);

		}
		return allowDec;
	}
	
	private Integer getPrecision() {
		int precision = 0;
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getPrecision method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_DECPRECISION) != null) {
				precision = (Integer) viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_DECPRECISION);
			}
			if(precision < 0) {
				precision = - precision;
			}
			return precision;
		} catch (Exception e) {
			return 0;
//			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getPrecision method :"+e);

		}
	}
	
	private int getDefaultValueIndex() {
		int index = 0;
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getDefaultValueIndex method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_VALUEIDX) != null) {
				int val = (Integer)viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_VALUEIDX);
				if(val < getValueList().size()) {
					index = val;
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getDefaultValueIndex method :"+e);

		}
		return index;
	}
	
	private ArrayList getValueList() {
		ArrayList valueList = null;
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getValueList method ");
			if(viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_VALUELIST) != null) {
				valueList = (ArrayList)viewConfiguration.getConfigurationValue(SpinnerFieldConstant.SP_VALUELIST);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getValueList method :"+e);

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
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In setValue method ");
			if(value.toString() != null) {
				spinnerBox.setText(value.toString());
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in setValue method :"+e);

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
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In getErrors method ");
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
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in getErrors method :"+e);

		}
		return errors;
	}
	
	/**
	 * Instantiates the member variables
	 */
	@Override
	public void initialize() {
		
		try {
			super.initialize();
			logger.log(Level.INFO, "[SpinnerField] ::In initialize method ");
			spinnerBox = new TextBox();
			arrowPanel = new VerticalPanel();
			spinUpArrow = new ToggleButton();
			spinDownArrow = new ToggleButton();
			fieldSideErrorPanel = new HorizontalPanel();
			fieldPanel = new HorizontalPanel();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in initialize method :"+e);

		}
	}

	@Override
	public void configure() {
		
		/** Apply Css to the spinner box, if not configured value default css applied **/
		try {
			super.configure();
			logger.log(Level.INFO, "[SpinnerField] ::In configure method ");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in configure method :"+e);

		}
	}

	@Override
	public void create() {
		
		
		try {
			super.create();
			logger.log(Level.INFO, "[SpinnerField] ::In create method ");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in create method :"+e);

		}
	    
	}

	/************************************* -- END --**************************************************/

	private void configureForNumber() {
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In configureForNumber method ");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in configureForNumber method :"+e);

		}
	}
	
	private void configureForList() {
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In configureForList method ");
			ListModel listModel = (ListModel)getModel();
			listModel.setValueList(getValueList());
			listModel.setCurrentIndex(getDefaultValueIndex());
			listModel.setCircular(isCircular());
			spinnerBox.setText(listModel.getValue().toString());
			spinnerBox.setReadOnly(true);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in configureForList method :"+e);

		}
	}

	private void configureModel() {
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In configureModel method ");
			if(getModel() instanceof NumericModel) {
				NumericModel numModel = (NumericModel)getModel();
				numModel.setMax(getMax());
				numModel.setMin(getMin());
				numModel.setStep(getStep());
				numModel.setValue(numModel.parseValue(getValue().toString()));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in configureModel method :"+e);

		}
	}

	private void setUnit() {
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In setUnit method ");
			NodeList<com.google.gwt.dom.client.Element> nodeList = fieldPanel.getElement().getElementsByTagName("td");
			Node td1Node = nodeList.getItem(0);
			Element td1Element = (Element) Element.as(td1Node);
			Element span = DOM.createSpan();
			span.setInnerText(getUnit());
			span.setClassName("appops-SpinnerUnit");
			td1Element.appendChild(span);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in setUnit method :"+e);

		}
	}
	
	public void setSpinnerValue() {
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In setSpinnerValue method ");
			if(getModel() instanceof NumericModel) {
				NumericModel numModel = (NumericModel)getModel();
				setValue(numModel.fixPrecision(numModel.parseValue(getModel().getValue().toString()), getPrecision()));
			} else if(getModel() instanceof ListModel) {
				ListModel listModel = (ListModel)getModel();
				setValue(listModel.getValue().toString());
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in setSpinnerValue method :"+e);

		}
	}
	private void onSpinUp() {
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In onSpinUp method ");
			getModel().spinUp();
			setSpinnerValue();

			if(isValidateOnChange()) {
				validate();
			}
			
			FieldEvent spinUpEvent = new FieldEvent(FieldEvent.SPN_SPINUP, null); 
			
			AppUtils.EVENT_BUS.fireEventFromSource(spinUpEvent, this);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in onSpinUp method :"+e);

		}
	}
	
	private void onSpinDown() {
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In onSpinDown method ");
			getModel().spinDown();
			setSpinnerValue();

			if(isValidateOnChange()) {
				validate();
			}
			
			FieldEvent spinDownEvent = new FieldEvent(FieldEvent.SPN_SPINDOWN, null); 
			
			AppUtils.EVENT_BUS.fireEventFromSource(spinDownEvent, this);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in onSpinDown method :"+e);

		}
	}
	
	
	@Override
	public void onKeyUp(KeyUpEvent event) {
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In onKeyUp method ");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in onKeyUp method :"+e);

		}		
	}
	
	@Override
	public void onKeyDown(KeyDownEvent event) {
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In onKeyDown method ");
			int keyCode = event.getNativeKeyCode();
			if(keyCode == KeyCodes.KEY_UP) {
				onSpinUp();
			} else if((keyCode == KeyCodes.KEY_DOWN)) {
				onSpinDown();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in onKeyDown method :"+e);

		}
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In onKeyPress method ");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in onKeyPress method :"+e);

		}
	}
	
	@Override
	public void onBrowserEvent(Event event) { 
	    super.onBrowserEvent(event); 
	    try {
	    	logger.log(Level.INFO, "[SpinnerField] ::In onBrowserEvent method ");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in onBrowserEvent method :"+e);

		} 
	}
	
	public void updateModel() {
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In updateModel method ");
			if(getModel() instanceof NumericModel) {
				NumericModel numModel = (NumericModel)getModel();
				getModel().setValue(numModel.parseValue(getValue().toString()));
			} else if(getModel() instanceof ListModel) {
				ListModel listModel = (ListModel)getModel();
				int indx = listModel.getValueList().indexOf(getValue().toString());
				listModel.setCurrentIndex(indx);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in updateModel method :"+e);

		}
	}


	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In onMouseWheel method ");
			if(event.isNorth()) {
				onSpinUp();
			} else if(event.isSouth()) {
				onSpinDown();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in onMouseWheel method :"+e);

		}
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {

		try {
			logger.log(Level.INFO, "[SpinnerField] ::In onMouseDown method ");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in onMouseDown method :"+e);

		}
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In onMouseUp method ");
			if(enabled) {
				timer.cancel();
				setEnabled(false);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in onMouseUp method :"+e);

		}
	}
	
	@Override
	public void onMouseOut(MouseOutEvent arg0) {
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In onMouseOut method ");
			if(enabled) {
				timer.cancel();
				setEnabled(false);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in onMouseOut method :"+e);

		}		
	}
	
	private void setEnabled(boolean enabled) { 
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In setEnabled method ");
			this.enabled = enabled;
			if(!enabled) {
				timer.cancel();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in setEnabled method :"+e);

		}
	}
	
	private final Timer timer = new Timer() {
		private int counter = 0;
		private int speed = initialSpeed;

		@Override
		public void cancel() {
			try {
				logger.log(Level.INFO, "[SpinnerField] ::In cancel method ");
				super.cancel();
				speed = initialSpeed;
				counter = 0;
			} catch (Exception e) {
				logger.log(Level.SEVERE, "[SpinnerField] ::Exception in cancel method :"+e);

			}
		}

		@Override
		public void run() {
			try {
				logger.log(Level.INFO, "[SpinnerField] ::In run method ");
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
			} catch (Exception e) {
				logger.log(Level.SEVERE, "[SpinnerField] ::Exception in run method :"+e);

			}

		}
	};

	@Override
	public void onBlur(BlurEvent event) {
		try {
			logger.log(Level.INFO, "[SpinnerField] ::In onBlur method ");
			if(isValidateOnBlur()) {
				if(validate()) {
					updateModel();
					setSpinnerValue();
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SpinnerField] ::Exception in onBlur method :"+e);

		}		
	}


	public SpinnerModel getModel() {
		return model;
	}


	public void setModel(SpinnerModel model) {
		this.model = model;
	}

}