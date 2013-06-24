package in.appops.client.common.config.field.spinner;

import java.math.BigDecimal;

public class NumericModel implements SpinnerModel {

	private Float value, max, min;
	private Integer step;
	private boolean circular;
	
	
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
			if((value + step > max)) { //
				if(isCircular()) { 
					value = ((value - (max - value)) - 1) + min;
				} else {
					value = max;
				}
			} else {
				value = value + step;
			}
		} catch (NumberFormatException e) {
			System.out.println("Spin up");
		}
	}
	
	public boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional // '-' and decimal.
	}
	
	public String fixPrecision(Float value, int precision) {
	    BigDecimal bd = new BigDecimal(value);
	    bd.stripTrailingZeros();
	    BigDecimal rounded = bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
	    return rounded.toString();
	}
	
	public Float parseValue(String value) {
		if(isNumeric(value)) {
			return Float.parseFloat(value);
		} 
		return null;
	}

	@Override
	public void spinDown() {
		if(value - step < min) {
			if(isCircular()) {
				value = (max - (step - (value - min))) + 1;
			} else {
				value = min;
			}
		} else {
			value = value - step;
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
