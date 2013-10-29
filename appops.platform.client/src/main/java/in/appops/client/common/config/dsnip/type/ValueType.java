package in.appops.client.common.config.dsnip.type;

import java.io.Serializable;

import in.appops.platform.core.shared.Configuration;

public abstract class ValueType extends Configuration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_VALUE = "Default_value";
	public static final String REGEX_VALIDATOR = "Regex_validator";

	public Object getDefaultValue(){
		return getPropertyByName(DEFAULT_VALUE);
	}
	
	public String getRegexValidator(){
		return getPropertyByName(REGEX_VALIDATOR);
	}
	
	public void addValueTypeProp(String propName, Object value){
		this.setPropertyByName(propName, (Serializable)value);
	}

}
