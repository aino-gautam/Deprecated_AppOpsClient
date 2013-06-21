package in.appops.client.common.config.field.spinner;

import java.util.ArrayList;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;

public class NumericSpinnerField  extends SpinnerField {
	
	public NumericSpinnerField() {
		super();
	}
	
	@Override
	public void configure() {
		super.configure();
		
		if(getMin() != null && getMax() != null && getMin() > getMax()) {
			// CONFIG EXCEPTION -- If min value is > than max value
		}

		if(getStep() != null){
			if(getStep() < 1) {
				// CONFIG EXCEPTION -- Step should be a +ve no. But can Step be -ve ????
			}
			if(getMin() != null && getMax() != null && (getStep() > (getMax() - getMin()))) {
				// CONFIG EXCEPTION -- Step should not be > than the difference of max and min.
				// Shall exception be thrown in this case???
			}
		}
		
		if(getDefaultValue() != null) {
			Double defVal = (Double) getDefaultValue();
			if(getMin() != null && getMax() != null && (defVal < getMin() || defVal > getMax())) {
				// CONFIG EXCEPTION -- If default value is < min or > max
				// Shall exception be thrown in this case???
			} else {
				
				if(!isAllowBlank()) {
					setValue(formatDoubleValue(defVal, "###"));
				} else {
					setValue(defVal.toString());
				}
			}
		}
		
		if(getMax() == null && getMin() == null && isCircular()) {
			// CONFIG EXCEPTION -- If circular is applied when max and min value are not set. 
		}

	}
	
	@Override
	public void create() {
		super.create();
		
		NodeList<com.google.gwt.dom.client.Element> nodeList = fieldPanel.getElement().getElementsByTagName("td");
	    Node td1Node = nodeList.getItem(0);
	    Element td1Element = (Element) Element.as(td1Node);
	    Element span = DOM.createSpan();
	    span.setInnerText(getUnit());
	    span.setClassName("appops-SpinnerUnit");
	    td1Element.appendChild(span);
	}
	
	/**** Configuration get() methods ****/

	/**
	 * Overriden
	 */
	@Override
	protected Object getDefaultValue() {
		Object retVal =  super.getDefaultValue();
		try {
			if(retVal == null || retVal.toString().equals("")) {
				return getMin() != null ? getMin() : 0L;
			} else {
				return (Double)retVal;
			}
		} catch(Exception e) {
			// CONFIG ERROR -- If default Value is anything other than a numeric value. 
			return null;
		}
	}
	
	private String formatDoubleValue(Double value, String formatter) { 
		return NumberFormat.getFormat(formatter).format(value);
	}
	
	private String getUnit() {
		String unit = "";
		if(getConfigurationValue(SpinnerFieldConstant.SP_UNIT) != null) {
			unit = getConfigurationValue(SpinnerFieldConstant.SP_UNIT).toString();
		}
		return unit;
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
	
	private Double getMax() {
		Double max = null;
		try {
			if(getConfigurationValue(SpinnerFieldConstant.SP_MAXVAL) != null) {
				max = (Double) getConfigurationValue(SpinnerFieldConstant.SP_MAXVAL);
			}
		} catch (Exception e) {
			// CONFIG ERROR -- If max value is anything other than a numeric value. 
		}
		return max;
	}

	private Double getMin() {
		Double min = null;
		try {
			if(getConfigurationValue(SpinnerFieldConstant.SP_MINVAL) != null) {
				min = (Double) getConfigurationValue(SpinnerFieldConstant.SP_MINVAL);
			}
		} catch (Exception e) {
			// CONFIG ERROR -- If min value is anything other than a numeric value. 
		}
		return min;
	}
	
	protected void spinUp() {
		try {
			Double newVal = null;
			if(!getErrors(getValue().toString()).isEmpty()) {
				if(getErrors(getValue().toString()).contains(getBlankErrMsg()) || getErrors(getValue().toString()).contains(getMinErrMsg())) {
					newVal = getMin();
				}
			} else {
				Double value = Double.parseDouble(getValue().toString());
				if(getMax() == null) {
					newVal = value + getStep();
				} else if(getMax() != null) {
					if((value + getStep() > getMax())) { //
						if(isCircular()) { 
							newVal = ((getStep() - (getMax() - value)) - 1) + getMin();
						} else {
							newVal = getMax();
						}
					} else {
						newVal = value + getStep();
					}
				}
			}
			if(newVal != null && !newVal.toString().equals(getValue().toString())) {
				setValue(String.valueOf(newVal));
				onValueChange();
				fireSpinUpEvent();
			}
		} catch (NumberFormatException e) {
/*			if(getMin() != null) {
				spinnerBox.setText(getMin().toString());
			} else{
				spinnerBox.setText(0+ "");
			}*/
			System.out.println("Spin up");
		}
	}
	private void fireSpinUpEvent() {
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(FieldEvent.SPN_SPINUP);
		fieldEvent.setEventData(spinnerBox.getText());
		AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, this);
	}

	private void fireSpinDownEvent() {
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(FieldEvent.SPN_SPINDOWN);
		fieldEvent.setEventData(spinnerBox.getText());
		AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, this);
	}
	
	protected void spinDown() {
		try {
			Double newVal = null;
			
			if(!getErrors(getValue().toString()).isEmpty()) {
				if(getErrors(getValue().toString()).contains(getBlankErrMsg())) {
					newVal = getMin();
				} else if(getErrors(getValue().toString()).contains(getMaxErrMsg())) {
					newVal = getMax();
				}
			} else {
			
				Double value = Double.parseDouble(getValue().toString());
				if(getMin() == null) {
					newVal = value - getStep();
				} else if(getMin() != null) {
						if((value - getStep() < getMin())) {
							if(isCircular()) {
								newVal = (getMax() - (getStep() - (value - getMin()))) + 1;
							} else {
								newVal = getMin();
							}
						} else {
							newVal = value - getStep();
						}
				}
			}
			if(newVal != null && !newVal.toString().equals(getValue())) {
				setValue(String.valueOf(newVal));
				onValueChange();
				fireSpinUpEvent();
			}
		} catch (NumberFormatException e) {
			
		}
	}

	public void changeValue(String formattedYear) {
		spinnerBox.setText(formattedYear);
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
					onValueChange();
					return;
			case KeyCodes.KEY_UP:

				break;
			case KeyCodes.KEY_DOWN:

				break;
		}
	}
	
	@Override
	public void onKeyPress(KeyPressEvent event) {
		
		if(!Character.isDigit(event.getCharCode())) {
			event.preventDefault();
			return;
		}
		if(getMax() != null && getMin() > 0 && event.getCharCode() == '-') {
			event.preventDefault();
			return;
		}
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            @Override
            public void execute() {
            	onValueChange();
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
	            	onValueChange();
	            }

	        });
	       
	    } 
	} 
	protected void onValueChange() {
    	validate();	
	}

	private boolean isAllowDecimal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> getErrors(Object value) {
		ArrayList<String> errors = new ArrayList<String>();
		boolean valid = true;
		errors.clear();
		if(value != null) {
			if(!isAllowBlank() && value.toString().trim().equals("")) {
				errors.add(getBlankErrMsg());
				return errors;
			}
			if(!value.toString().matches("-?[0-9]\\d*(.\\d+)?")) {
				errors.add(getInvalidErrMsg());
				return errors;
			}
			if(getMin() > 0 && Double.parseDouble(value.toString()) < 0) {
				errors.add(getNegErrMsg());
				valid = false;
			}
			if(getMax() != null && Double.parseDouble(value.toString()) > getMax()) {
				errors.add(getMaxErrMsg());
				valid = false;
			} 
			if(getMin() != null && Double.parseDouble(value.toString()) < getMin()) {
				errors.add(getMinErrMsg());
				valid = false;
			}
			if(valid) {
				if(!isAllowDecimal()) {
					setValue(formatDoubleValue(Double.parseDouble(value.toString()), "###"));
				}
			}
		}
		return errors;
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
		String maxMsg = "Maximum value for this field is " + formatDoubleValue(getMax(), "###.#");
		if(getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGMAX) != null) {
			maxMsg = getConfigurationValue(SpinnerFieldConstant.SP_ERRMSGMAX).toString();
		}
		return maxMsg;
	}

	private String getMinErrMsg() {
		String minMsg = "Minimum value for this field is " + formatDoubleValue(getMin(), "###.#");
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
	
	protected boolean isAllowBlank() {
		boolean allowBlank = false;
		if(getConfigurationValue(SpinnerFieldConstant.SP_ALLOWBLNK) != null) {
			allowBlank = (Boolean)getConfigurationValue(SpinnerFieldConstant.SP_ALLOWBLNK);
		}
		return allowBlank;
	}
	
	protected boolean isCircular() {
		boolean allowDecimal = false;
		if(getConfigurationValue(SpinnerFieldConstant.SP_ALLOWDEC) != null) {
			allowDecimal = (Boolean)getConfigurationValue(SpinnerFieldConstant.SP_ALLOWDEC);
		}
		return allowDecimal;
	}
	
}