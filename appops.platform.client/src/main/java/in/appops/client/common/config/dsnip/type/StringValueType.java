package in.appops.client.common.config.dsnip.type;

public class StringValueType extends ValueType {

	public static final String MAX_LENGTH = "Max_length";
	public static final String MIN_LENGTH = "Min_length";
	public static final String CHARSET = "Charset";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Integer getMinLength(){
		return getPropertyByName(MIN_LENGTH);
	}
	
	public Integer getMaxLength(){
		return getPropertyByName(MAX_LENGTH);
	}
	
	public String getCharset(){
		return getPropertyByName(CHARSET);
	}
	
	
	

}
