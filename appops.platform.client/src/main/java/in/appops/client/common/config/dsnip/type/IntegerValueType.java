package in.appops.client.common.config.dsnip.type;

public class IntegerValueType extends ValueType {

	public static final String MAX_VALUE = "Max_value";
	public static final String MIN_VALUE = "Min_value";
	public static final String IS_SIGNED = "Is_signed";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int getMinValue(){
		return getPropertyByName(MIN_VALUE);
	}
	
	public int getMaxValue(){
		return getPropertyByName(MAX_VALUE);
	}
	
	public String getIsSigned(){
		return getPropertyByName(IS_SIGNED);
	}

}
