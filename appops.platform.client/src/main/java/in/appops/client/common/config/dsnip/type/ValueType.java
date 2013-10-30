package in.appops.client.common.config.dsnip.type;

import java.io.Serializable;

import in.appops.platform.core.shared.Configuration;

/**
 * Represents the basic value types that can be used by fields
 * @author nairutee
 *
 */
public abstract class ValueType extends Configuration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_VALUE = "Default_value";
	public static final String REGEX_VALIDATOR = "Regex_validator";

	/**
	 * gets the default value
	 * @return
	 */
	public Object getDefaultValue(){
		return getPropertyByName(DEFAULT_VALUE);
	}
	
	/**
	 * gets the regex validator to be used for validations in a field
	 * @return
	 */
	public String getRegexValidator(){
		return getPropertyByName(REGEX_VALIDATOR);
	}
	
	/**
	 * 
	 * @param propName
	 * @param value
	 */
	public void addValueTypeProp(String propName, Object value){
		this.setPropertyByName(propName, (Serializable)value);
	}

}
