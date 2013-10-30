package in.appops.client.common.config.dsnip.type;

/**
 * Represents the Integer value type
 * @author nairutee
 *
 */
public class IntegerValueType extends ValueType {

	public static final String MAX_VALUE = "Max_value";
	public static final String MIN_VALUE = "Min_value";
	public static final String IS_SIGNED = "Is_signed";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The minimum value permitted for eg. 0 
	 * @return
	 */
	public Integer getMinValue(){
		return getPropertyByName(MIN_VALUE);
	}
	
	/**
	 * The maximum value permitted for eg. 1000
	 * @return
	 */
	public Integer getMaxValue(){
		return getPropertyByName(MAX_VALUE);
	}
	
	/**
	 * Whether negative values are allowed or not. 
	 * @return true if allowed, 0 if not.
	 */
	public Boolean isSigned(){
		return getPropertyByName(IS_SIGNED);
	}

}
