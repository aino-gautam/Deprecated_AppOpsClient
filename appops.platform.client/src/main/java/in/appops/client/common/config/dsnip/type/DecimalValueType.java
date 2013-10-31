package in.appops.client.common.config.dsnip.type;

@SuppressWarnings("serial")
public class DecimalValueType extends ValueType {

	public static final String MAX_VALUE = "maxValue";
	public static final String MIN_VALUE = "minValue";
	public static final String IS_SIGNED = "isSigned";
	public static final String PRECISION = "precision";
	
	/**
	 * The minimum value permitted for eg. 0 
	 * @return
	 */
	public Float getMinValue() {
		return (Float) (getPropertyByName(MIN_VALUE) != null ? getPropertyByName(MIN_VALUE) : Float.MIN_VALUE);
	}
	
	/**
	 * The maximum value permitted for eg. 1000
	 * @return
	 */
	public Float getMaxValue() {
		return (Float) (getPropertyByName(MAX_VALUE) != null ? getPropertyByName(MAX_VALUE) : Float.MAX_VALUE);
	}
	
	/**
	 * Whether negative values are allowed or not. 
	 * @return true if allowed, 0 if not.
	 */
	public Boolean isSigned() {
		return getPropertyByName(IS_SIGNED);
	}
	
	public Integer getPrecision() {
		return  (Integer) (getPropertyByName(PRECISION) != null ? getPropertyByName(PRECISION) : 0);
	}

}
