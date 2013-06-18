package in.appops.client.common.fields;

public class NumericSpinnerField  extends SpinnerField{
	
	public NumericSpinnerField() {
		super();
	}
	
	@Override
	public void configure() {
		super.configure();
		
		if(getMin() != null && getMax() != null && getMin() > getMax()) {
			errorMap.put("errorMinMax", "Min cannot be greater than max");
		}

		if(getStep() != null){
			if(getStep() < 1) {
				errorMap.put("errorNegativeStep", "Step Cannot be negative");
			}
			if(getMin() != null && getMax() != null && (getStep() > (getMax() - getMin()))) {
				errorMap.put("errorStepMaxMin", "Step cannot be greater than difference of max and min");
			}
		}
		
		if(getDefaultValue() != null) {
			if(getMin() != null && getMax() != null && ((Long)getDefaultValue() < getMin() || (Long)getDefaultValue() > getMax())) {
				errorMap.put("errorOutOfRangeDefault", "Default Value should not be less than min and greater than max");
			} else {
				textbox.setText((Long)getDefaultValue() + " " +  getUnit());
			}
		}
		
		if(getMax() == null && getMin() == null && isCircular()) {
			errorMap.put("errorInapplicableRoll", "Roll effect applicable when max and min are provided");
		}

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
				return (Long)retVal;
			}
		} catch(Exception e) {
			errorMap.put("errorInvalidDefault", "Invalid default value");
			return null;
		}
	}
	
	private String getUnit() {
		String unit = "";
		if(getConfigurationValue(SpinnerConfigurationConstant.UNIT) != null) {
			unit = getConfigurationValue(SpinnerConfigurationConstant.UNIT).toString();
		}
		return unit;
	}

	private Integer getStep() {
		int step = 1;
		try {
			if(getConfigurationValue(SpinnerConfigurationConstant.STEP) != null) {
				step = (Integer) getConfigurationValue(SpinnerConfigurationConstant.STEP);
			}
			return step;
		} catch (Exception e) {
			errorMap.put("errorInvalidStep", "Invalid step value");
			return null;
		}
	}
	
	private Long getMax() {
		Long max = null;
		try {
			if(getConfigurationValue(SpinnerConfigurationConstant.MAX) != null) {
				max = (Long) getConfigurationValue(SpinnerConfigurationConstant.MAX);
			}
		} catch (Exception e) {
			errorMap.put("errorInvalidMax", "Invalid max value");
		}
		return max;
	}

	private Long getMin() {
		Long min = null;
		try {
			if(getConfigurationValue(SpinnerConfigurationConstant.MIN) != null) {
				min = (Long) getConfigurationValue(SpinnerConfigurationConstant.MIN);
			}
		} catch (Exception e) {
			errorMap.put("errorInvalidMax", "Invalid min value");
		}
		return min;
	}

	
	
	protected Long getValue() {
		int indexOfUnit = textbox.getText().indexOf(getUnit());
		String valueStr = null;
		if(indexOfUnit != -1) {
			valueStr = textbox.getText().substring(0, textbox.getText().indexOf(getUnit())).trim();
		} else {
			valueStr = textbox.getText().trim();
		}
		Long value = valueStr == null || valueStr.equals("") ? null : Long.parseLong(valueStr);
		
		return value;
	}
	
	protected void spinUp() {
		try {
			Long value = getValue();

			if(getMax() == null) {
				value = value + getStep();
			} else if(getMax() != null) {
				if(value >= getMin() && value <= getMax()) {
					if((value + getStep() > getMax())) { //
						if(isCircular()) { 
							value = ((getStep() - (getMax() - value)) - 1) + getMin();
						} else {
							value = getMax();
						}
					} else {
						value = value + getStep();
					}
				}
			}
			textbox.setText(String.valueOf(value) + " " + getUnit());
		} catch (NumberFormatException e) {
			if(getMin() != null) {
				textbox.setText(getMin() + " " + getUnit());
			} else{
				textbox.setText(0 + " " + getUnit());
			}
		}
	}
	
	protected void spinDown() {
		try {
			Long value = getValue();
			if(getMin() == null) {
				value = value - getStep();
			} else if(getMin() != null) {
				if(value >= getMin() && value <= getMax()) {
					if((value - getStep() < getMin())) {
						if(isCircular()) {
							value = (getMax() - (getStep() - (value - getMin()))) + 1;
						} else {
							value = getMin();
						}
					} else {
						value = value - getStep();
					}
				}
			}
			textbox.setText(String.valueOf(value) + " " + getUnit());
		} catch (NumberFormatException e) {
			if(getMin() != null) {
				textbox.setText(getMin() + " " + getUnit());
			} else{
				textbox.setText(0 + " " + getUnit());
			}
		}
	}
	
}
