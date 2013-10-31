package in.appops.client.common.config.field.spinner;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NumericModel implements SpinnerModel {

	private Float value, max, min;
	private Integer step;
	private boolean circular;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public void setStep(Integer step) {
		this.step = step;
	}
	
	public void setMax(Float max) {
		this.max = (Float)max;
	}
	
	public void setMin(Float min) {
		this.min = (Float)min;
	}
	
	@Override
	public void setValue(Object value) {
		this.value = (Float)value;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void spinUp() {
		try {
			logger.log(Level.INFO, "[NumericModel] ::In spinUp method ");
			if((value + step > max)) { //
				if(isCircular()) { 
					value = ((step - (max - value)) - 1) + min;
				} else {
					value = max;
				}
			} else {
				value = value + step;
			}
		} catch (NumberFormatException e) {
//			System.out.println("Spin up");
			logger.log(Level.SEVERE, "[NumericModel] ::Exception in spinUp method :"+e);

		}
	}
	
	public boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional // '-' and decimal.
	}
	
	public String fixPrecision(Float value, int precision) {
	    BigDecimal rounded = null;
		try {
			logger.log(Level.INFO, "[NumericModel] ::In fixPrecision method ");
			BigDecimal bd = new BigDecimal(value);
			bd.stripTrailingZeros();
			rounded = bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[NumericModel] ::Exception in fixPrecision method :"+e);

		}
	    return rounded.toString();
	}
	
	public Float parseValue(String value) {
		try {
			logger.log(Level.INFO, "[NumericModel] ::In parseValue method ");

			if(isNumeric(value)) {
				return Float.parseFloat(value);
			}
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, "[NumericModel] ::Exception in parseValue method :"+e);

		} 
		return null;
	}

	@Override
	public void spinDown() {
		try {
			logger.log(Level.INFO, "[NumericModel] ::In spinDown method ");

			if(value - step < min) {
				if(isCircular()) {
					value = (max - (step - (value - min))) + 1;
				} else {
					value = min;
				}
			} else {
				value = value - step;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[NumericModel] ::Exception in spinDown method :"+e);

		}		
	}

	@Override
	public boolean isCircular() {
		return circular;
	}

	@Override
	public void setCircular(boolean circular) {
		this.circular = circular;
	}
	
	
}
