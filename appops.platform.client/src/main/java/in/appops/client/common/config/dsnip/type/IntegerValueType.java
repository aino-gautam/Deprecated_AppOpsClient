package in.appops.client.common.config.dsnip.type;

/**
 * Represents the Integer value type
 * @author nairutee
 *
 */
public class IntegerValueType extends ValueType {

	public static final String MAX_VALUE = "maxValue";
	public static final String MIN_VALUE = "minValue";
	public static final String IS_SIGNED = "isSigned";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The minimum value permitted for eg. 0 
	 * @return
	 */
	public Integer getMinValue(){
		return (Integer) (getPropertyByName(MIN_VALUE) != null ? getPropertyByName(MIN_VALUE) : Integer.MIN_VALUE);
	}
	
	/**
	 * The maximum value permitted for eg. 1000
	 * @return
	 */
	public Integer getMaxValue(){
		return (Integer) (getPropertyByName(MAX_VALUE) != null ? getPropertyByName(MAX_VALUE) : Integer.MAX_VALUE);
	}
	
	/**
	 * Whether negative values are allowed or not. 
	 * @return true if allowed, 0 if not.
	 */
	public Boolean isSigned(){
		return getPropertyByName(IS_SIGNED);
	}

}
