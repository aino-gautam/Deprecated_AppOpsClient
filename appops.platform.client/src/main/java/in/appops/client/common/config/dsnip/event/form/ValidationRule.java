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
	
	public static final String ERRPOS = "errorPosition";
	public static final String TOP = "top";
	public static final String SIDE = "side";
	public static final String BOTTOM = "bottom";
	public static final String ERRINLINE = "errorInline";
	public static final String ERRMSGCLS = "errorMsgCls";
	public static final String ERRICON_BLOB = "errorIcon";
	
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
	
	public String getErrorPosition() {
		String errPos = ERRINLINE;
		try {
			if(getPropertyByName(ERRPOS) != null) {
				errPos = getPropertyByName(ERRPOS);
			}
		} catch (Exception e) {

		}
		return errPos;
	}
	
	public String getErrorMessageCss() {
		if(getPropertyByName(ERRMSGCLS)!=null){
			return getPropertyByName(ERRMSGCLS);
		}
		return null;
	}
	
	public String getErrorIconBlobId() {
		if(getPropertyByName(ERRICON_BLOB)!=null){
			return getPropertyByName(ERRICON_BLOB);
		}
		return null;
	}
}
