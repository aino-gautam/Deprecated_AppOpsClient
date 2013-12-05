package in.appops.client.common.config.dsnip.event.form;

import in.appops.platform.core.shared.Configuration;

import java.util.HashMap;

public class InvokeActionRule extends FormEventRule{

	private static final long serialVersionUID = 1L;
		
	public static final String HAS_OPERATION = "has_operation";
	public static final String OPERATION = "operationName";
	public static final String HAS_PARAMETER = "has_parameter";
	public static final String OPERATIONPARAM_MAP = "operationParam";
	public static final String QUERYPARAM_MAP = "queryParam";
	public static final String SUCCESS_MSG = "actionSuccessMsg";
	public static final String FAILURE_MSG = "actionFailureMsg";
	
	public static final String PROCESSED_PARAM_MAP = "processedParamMap";	
	
	public Boolean hasOperation() {
		
		if(getPropertyByName(HAS_OPERATION)!=null){
			return getPropertyByName(HAS_OPERATION);
		}else{
			return false;
		}
	}
	
	public Boolean hasParameter() {
		if(getPropertyByName(HAS_PARAMETER)!=null){
			return getPropertyByName(HAS_PARAMETER);
		}else{
			return false;
		}
	}
	
	public String getOperation() {
		return getPropertyByName(OPERATION);
	}

	public String getSuccessMsg() {
		return getPropertyByName(SUCCESS_MSG);
	}

	
	public String getFailureMsg() {
		return getPropertyByName(FAILURE_MSG);
	}

	public Configuration getParametermap() {
		
		if(hasConfiguration(OPERATIONPARAM_MAP)){
			return getPropertyByName(OPERATIONPARAM_MAP);
		}else{
			return getPropertyByName(QUERYPARAM_MAP);
		}
	}
	
	public String getParameterType(){
		if(hasConfiguration(OPERATIONPARAM_MAP)){
			return OPERATIONPARAM_MAP;
		}else{
			return QUERYPARAM_MAP;
		}
	}
	
	public void setProcessedParamerMap(HashMap<String, Object> map){
		setPropertyByName(PROCESSED_PARAM_MAP, map);
	}
	
	public HashMap<String, Object> getProcessedParamerMap(){
		return getPropertyByName(PROCESSED_PARAM_MAP);
	}
}
