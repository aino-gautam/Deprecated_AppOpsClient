package in.appops.client.common.fields;

import in.appops.client.common.config.field.spinner.SpinnerField.SpinnerFieldConstant;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.TextBox;

public class NumericTextbox extends TextBox implements KeyPressHandler {
	
		
	private Configuration configuration ;
	
	private TextField textField ;
	
	public NumericTextbox() {
		
	}
	
	public NumericTextbox(TextField textField) {
		this.textField = textField;
	}
		
	private String getNegErrMsg(){
		
			String negativeValueText = "Field value cannot be -ve";
			if(getConfigurationValue(TextFieldConstant.NEGATIVE_VALUE_TEXT) != null) {
				
				negativeValueText = (String) getConfigurationValue(TextFieldConstant.NEGATIVE_VALUE_TEXT);
			}
			return negativeValueText;
	}

	private String getMaxErrMsg(){
		
			String maxErrMsg = "The maximum value for this field is "+ getMax();
			if(getConfigurationValue(TextFieldConstant.MAX_VALUE_TEXT) != null) {
				
				maxErrMsg = (String) getConfigurationValue(TextFieldConstant.MAX_VALUE_TEXT);
			}
			return maxErrMsg;
	}
	
	private String getMinErrMsg(){
		
			String minErrMsg = "The minimum value for this field is "+ getMin();
			if(getConfigurationValue(TextFieldConstant.MIN_VALUE_TEXT) != null) {
				
				minErrMsg = (String) getConfigurationValue(TextFieldConstant.MIN_VALUE_TEXT);
			}
			return minErrMsg;
	}
	
	private Float getMax() {
		Float max = Float.MAX_VALUE;
		try {
			if(getConfigurationValue(TextFieldConstant.MAXVALUE) != null) {
				max = (Float) getConfigurationValue(TextFieldConstant.MAXVALUE);
			}
		} catch (Exception e) {
			// CONFIG ERROR -- If max value is anything other than a numeric value. 
		}
		return max;
	}

	private Float getMin() {
		Float min = Float.MIN_VALUE;
		try {
			if(getConfigurationValue(TextFieldConstant.MINVALUE) != null) {
				min = (Float) getConfigurationValue(TextFieldConstant.MINVALUE);
			}
		} catch (Exception e) {
			// CONFIG ERROR -- If min value is anything other than a numeric value. 
		}
		return min;
	}
	
	boolean isAllowDecimal() {
		boolean allowDec = false;
		if(getConfiguration().getPropertyByName(TextFieldConstant.ALLOWDEC) != null) {
			allowDec = (Boolean)getConfiguration().getPropertyByName(TextFieldConstant.ALLOWDEC);
		}
		return allowDec;
	}
	
	private Integer getPrecision() {
		int precision = 2;
		try {
			if(getConfiguration().getPropertyByName(TextFieldConstant.DEC_PRECISION) != null) {
				precision = (Integer) getConfiguration().getPropertyByName(TextFieldConstant.DEC_PRECISION);
			}
			if(precision < 0) {
				precision = - precision;
			}
			return precision;
		} catch (Exception e) {
			return 0;
		}
	}
			
	private String getBlankErrMsg(){
		
			String blankFieldText = "Field is required"; 
			if(getConfigurationValue(TextFieldConstant.BF_BLANK_TEXT) != null) {
				
				blankFieldText = (String) getConfigurationValue(TextFieldConstant.BF_BLANK_TEXT);
			}
			return blankFieldText;
	}
	
	private String getInvalidErrMsg() {
						
		String invalidMsg = "Invalid input - not a number";
		if(getConfigurationValue(TextFieldConstant.BF_INVLDMSG) != null) {
			
			invalidMsg = (String) getConfigurationValue(TextFieldConstant.BF_INVLDMSG);
		}
		return invalidMsg;
	}
	
	
	/**
	 * Returns true if the configuration is provided.
	 * @param configKey - The configuration to check
	 * @return
	 */
	protected boolean hasConfiguration(String configKey) {
		if(configuration != null && configuration.getPropertyByName(configKey) != null) {
			return true;
		}
		return false;
	}
	
	protected Serializable getConfigurationValue(String configKey) {
		if(hasConfiguration(configKey)) {
			return configuration.getPropertyByName(configKey);
		}
		return null;
	}
	
	public String fixPrecision() {
	    BigDecimal bd = new BigDecimal(getText());
	    bd.stripTrailingZeros();
	    BigDecimal rounded = bd.setScale(getPrecision(), BigDecimal.ROUND_HALF_UP);
	    return rounded.toString();
	}

	/**
	 * Method validates the number.
	 * @return
	 */
	public ArrayList<String> validate(){
						
		ArrayList<String> errors = new ArrayList<String>();
		errors.clear();
		boolean valid = true;
		String value = getText();
		
		if(value != null) {
			if(!textField.isAllowBlank() && value.toString().trim().equals("")) {
				errors.add(getBlankErrMsg());
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
	
	public Float parseValue(String value) {
		if(isNumeric(value)) {
			return Float.parseFloat(value);
		} 
		return null;
	}
	
	public boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional // '-' and decimal.
	}
	
	private String formatDoubleValue(Double value, String formatter) { 
		return NumberFormat.getFormat(formatter).format(value);
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
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
				  
				  if(textField.isValidateOnChange()){
					  textField.validate();
				  }
		}
		});
		
	}
	
	
}
