package in.appops.client.common.config.dsnip.event.form;

import in.appops.client.common.config.dsnip.event.Rule;
import in.appops.platform.core.shared.Configuration;

public class ValidationRule extends Configuration implements Rule  {
	
	private static final long serialVersionUID = 1L;
	
	public static final String ISVALIDATE = "isValidate";
	public static final String VALIDATEON = "validateOn";
	public static final String VALIDATION_MODE = "validationMode";
	
	public static final String PREVALIDATION = "preValidation";
	public static final String POSTVALIDATION = "postValidation";
	public static final String INLINEVALIDATION = "inlineValidation";
	public static final String FIELDFOCUSVALIDATION = "fieldFocusValidation";
	
	
	public Boolean isValidate() {
		if(getPropertyByName(ISVALIDATE)!=null){
			return getPropertyByName(ISVALIDATE);
		}else{
			return false;
		}
	}
	
	public String getValidateOn() {
		if(getPropertyByName(VALIDATEON)!=null){
			return getPropertyByName(VALIDATEON);
		}
		return null;
	}
	
	public String getValidationMode() {
		if(getPropertyByName(VALIDATION_MODE)!=null){
			return getPropertyByName(VALIDATION_MODE);
		}
		return INLINEVALIDATION;
	}
}
