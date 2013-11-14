package in.appops.client.common.config.dsnip;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class EventParam {
	public interface EventParamConstant {
		String PARAM_NAME = "paramName";
		String PARAM_TYPE = "paramType";
		String PARAM_VALUE = "paramValue";
		String PARAM_DEFVAL = "paramDefaultValue";
		int PARAM_ENTITY = 1;
		int PARAM_FIELD = 2;
		int PARAM_CONTEXT = 3;
	}
	
	private String paramName;
	private int paramType;
	private String paramValue;
	private Object defaultValue;
	
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public int getParamType() {
		return paramType;
	}
	public void setParamType(int paramType) {
		this.paramType = paramType;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public Object getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public JSONObject toJson() {
		JSONObject eventParamJObj = new JSONObject();
		
		eventParamJObj.put("type", new JSONString("eventParam"));
		JSONString paramNameJson = new JSONString(this.paramName);
		eventParamJObj.put(EventParamConstant.PARAM_NAME, paramNameJson);
		
		JSONNumber paramTypeJson = new JSONNumber(paramType);
		eventParamJObj.put(EventParamConstant.PARAM_TYPE, paramTypeJson);

		JSONString paramValueJson = new JSONString(paramValue);
		eventParamJObj.put(EventParamConstant.PARAM_VALUE, paramValueJson);
		
		JSONValue paramDefValueJson = null;
		if(defaultValue instanceof Integer || defaultValue instanceof Long) {
			paramDefValueJson = new JSONNumber((Double) defaultValue);
		} else if(defaultValue instanceof String) {
			paramDefValueJson = new JSONString((String) defaultValue);
		} else if(defaultValue instanceof Boolean) {
			paramDefValueJson = JSONBoolean.getInstance((Boolean) defaultValue);
		}
		eventParamJObj.put(EventParamConstant.PARAM_DEFVAL, paramDefValueJson);
		
		return eventParamJObj;
		
	}

}
