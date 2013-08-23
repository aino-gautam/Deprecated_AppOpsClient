package in.appops.client.common.config.field;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.TextBox;

public class NumericTextbox extends TextBox implements KeyPressHandler {
		
	private Configuration configuration ;
	private TextField textField ;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public NumericTextbox() {
		
	}
	
	public NumericTextbox(TextField textField) {
		this.textField = textField;
	}
		
	private String getNegErrMsg(){
		
			String negativeValueText = "Field value cannot be -ve";
			try {
				logger.log(Level.INFO, "[NumericTextbox] ::In getNegErrMsg method ");
				if(getConfigurationValue(TextFieldConstant.NEGATIVE_VALUE_TEXT) != null) {
					
					negativeValueText = (String) getConfigurationValue(TextFieldConstant.NEGATIVE_VALUE_TEXT);
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "[NumericTextbox] ::Exception in getNegErrMsg method :"+e);
			}
			return negativeValueText;
	}

	private String getMaxErrMsg(){
		
			String maxErrMsg = "The maximum value for this field is "+ getMax();
			try {
				logger.log(Level.INFO, "[NumericTextbox] ::In getMaxErrMsg method ");
				if(getConfigurationValue(TextFieldConstant.MAX_VALUE_TEXT) != null) {
					
					maxErrMsg = (String) getConfigurationValue(TextFieldConstant.MAX_VALUE_TEXT);
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "[NumericTextbox] ::Exception in getMaxErrMsg method :"+e);
			}
			return maxErrMsg;
	}
	
	private String getMinErrMsg(){
		
			String minErrMsg = "The minimum value for this field is "+ getMin();
			try {
				logger.log(Level.INFO, "[NumericTextbox] ::In getMinErrMsg method ");
				if(getConfigurationValue(TextFieldConstant.MIN_VALUE_TEXT) != null) {
					
					minErrMsg = (String) getConfigurationValue(TextFieldConstant.MIN_VALUE_TEXT);
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "[NumericTextbox] ::Exception in getMinErrMsg method :"+e);
			}
			return minErrMsg;
	}
	
	private Float getMax() {
		Float max = Float.MAX_VALUE;
		try {
			logger.log(Level.INFO, "[NumericTextbox] ::In getMax method ");
			if(getConfigurationValue(TextFieldConstant.MAXVALUE) != null) {
				max = (Float) getConfigurationValue(TextFieldConstant.MAXVALUE);
			}
		} catch (Exception e) {
			// CONFIG ERROR -- If max value is anything other than a numeric value. 
			logger.log(Level.SEVERE, "[NumericTextbox] ::Exception in getMax method :"+e);
		}
		return max;
	}

	private Float getMin() {
		Float min = Float.MIN_VALUE;
		try {
			logger.log(Level.INFO, "[NumericTextbox] ::In getMin method ");
			if(getConfigurationValue(TextFieldConstant.MINVALUE) != null) {
				min = (Float) getConfigurationValue(TextFieldConstant.MINVALUE);
			}
		} catch (Exception e) {
			// CONFIG ERROR -- If min value is anything other than a numeric value. 
			logger.log(Level.SEVERE, "[NumericTextbox] ::Exception in getMin method :"+e);
		}
		return min;
	}
	
	public boolean isAllowDecimal() {
		boolean allowDec = false;
		try {
			logger.log(Level.INFO, "[NumericTextbox] ::In isAllowDecimal method ");
			if(getConfiguration().getPropertyByName(TextFieldConstant.ALLOWDEC) != null) {
				allowDec = (Boolean)getConfiguration().getPropertyByName(TextFieldConstant.ALLOWDEC);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[NumericTextbox] ::Exception in isAllowDecimal method :"+e);
		}
		return allowDec;
	}
	
	private Integer getPrecision() {
		int precision = 2;
		try {
			logger.log(Level.INFO, "[NumericTextbox] ::In getPrecision method ");
			if(getConfiguration().getPropertyByName(TextFieldConstant.DEC_PRECISION) != null) {
				precision = (Integer) getConfiguration().getPropertyByName(TextFieldConstant.DEC_PRECISION);
			}
			if(precision < 0) {
				precision = - precision;
			}
			return precision;
		} catch (Exception e) {
			return 0;
//			logger.log(Level.SEVERE, "[NumericTextbox] ::Exception in getNegErrMsg method :"+e);
		}
	}
			
	private String getBlankErrMsg(){
		
			String blankFieldText = "Field is required"; 
			try {
				logger.log(Level.INFO, "[NumericTextbox] ::In getBlankErrMsg method ");
				if(getConfigurationValue(TextFieldConstant.BF_BLANK_TEXT) != null) {
					
					blankFieldText = (String) getConfigurationValue(TextFieldConstant.BF_BLANK_TEXT);
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "[NumericTextbox] ::Exception in getBlankErrMsg method :"+e);
			}
			return blankFieldText;
	}
	
	private String getInvalidErrMsg() {
						
		String invalidMsg = "Invalid input - not a number";
		try {
			logger.log(Level.INFO, "[NumericTextbox] ::In getInvalidErrMsg method ");
			if(getConfigurationValue(TextFieldConstant.BF_INVLDMSG) != null) {
				
				invalidMsg = (String) getConfigurationValue(TextFieldConstant.BF_INVLDMSG);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[NumericTextbox] ::Exception in getInvalidErrMsg method :"+e);
		}
		return invalidMsg;
	}
	
	
	/**
	 * Returns true if the configuration is provided.
	 * @param configKey - The configuration to check
	 * @return
	 */
	protected boolean hasConfiguration(String configKey) {
		try {
			logger.log(Level.INFO, "[NumericTextbox] ::In hasConfiguration method ");
			if(configuration != null && configuration.getPropertyByName(configKey) != null) {
				return true;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[NumericTextbox] ::Exception in hasConfiguration method :"+e);
		}
		return false;
	}
	
	protected Serializable getConfigurationValue(String configKey) {
		try {
			logger.log(Level.INFO, "[NumericTextbox] ::In getConfigurationValue method ");
			if(hasConfiguration(configKey)) {
				return configuration.getPropertyByName(configKey);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[NumericTextbox] ::Exception in getConfigurationValue method :"+e);
		}
		return null;
	}
	
	public String fixPrecision() {
	    BigDecimal bd = new BigDecimal(getText());
	    BigDecimal rounded = null ;
	    try {
	    	logger.log(Level.INFO, "[NumericTextbox] ::In fixPrecision method ");
			bd.stripTrailingZeros();
			rounded = bd.setScale(getPrecision(), BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[NumericTextbox] ::Exception in fixPrecision method :"+e);
		}
	    return rounded.toString();
	}

	/**
	 * Method validates the number.
	 * @return
	 */
	public ArrayList<String> validate(){
						
		ArrayList<String> errors = new ArrayList<String>();
		try {
			logger.log(Level.INFO, "[NumericTextbox] ::In validate method ");
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
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, "[NumericTextbox] ::Exception in validate method :"+e);
		}
		return errors;
		
	}
	
	public Float parseValue(String value) {
		try {
			logger.log(Level.INFO, "[NumericTextbox] ::In parseValue method ");
			if(isNumeric(value)) {
				return Float.parseFloat(value);
			}
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, "[NumericTextbox] ::Exception in parseValue method :"+e);
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
		
		final int charCode = event.getUnicodeCharCode();

		try {
			logger.log(Level.INFO, "[NumericTextbox] ::In onKeyPress method ");
			if (!Character.isDigit(event.getCharCode())&& event.getCharCode() != '-' && event.getCharCode() != '.') {
				event.preventDefault();
				return;
			}
			if (getMin() > 0 && event.getCharCode() == '-') {
				event.preventDefault();
				return;
			}
			if (!isAllowDecimal() && event.getCharCode() == '.') {
				event.preventDefault();
				return;
			}

			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {

					if (textField.isValidateOnChange()) {
						textField.validate();
					}

					if (textField.isDirty()) {
						if (charCode == KeyCodes.KEY_ENTER) {
							FieldEvent fieldEvent = new FieldEvent();
							fieldEvent.setEventSource(textField);
							fieldEvent.setEventType(FieldEvent.ENTERED_HIT);
							fieldEvent.setEventData(getValue());
							AppUtils.EVENT_BUS.fireEvent(fieldEvent);
						} else {
							FieldEvent fieldEvent = new FieldEvent();
							fieldEvent.setEventType(FieldEvent.EDITINPROGRESS);
							fieldEvent.setEventSource(textField);
							fieldEvent.setEventData(getValue());
							AppUtils.EVENT_BUS.fireEvent(fieldEvent);
						}
					}
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[NumericTextbox] ::Exception in onKeyPress method :" + e);
		}
	}
}
