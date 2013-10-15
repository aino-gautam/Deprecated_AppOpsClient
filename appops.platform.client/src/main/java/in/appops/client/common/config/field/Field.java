package in.appops.client.common.config.field;

import java.util.ArrayList;

/**
 * This provides provides a common interface for the logical behaviour and state of fields + 
 * getting setting field values, validation triggering etc. Fields are configurable.
 * 
 * // TODO Define local event handling mechanism 
 * 
 * @author nitish@ensarm.com
 */
public interface Field {
	
	/**
	 * Sets data value to the field.
	 */
	void setValue(Object value);
	
	/**
	 * Returns the current data value of the field. Value would be specific to the field.
	 */
	Object getValue();
	
	/**
	 * Reset the current field value to the originally value while loading. Also clear any validation messages/errors
	 */
	void reset();
	
	/**
	 * Will do the field validation and return a list of error messages for validation failed. Would be called internally while by {@link #validate()}
	 */
	ArrayList<String> getErrors(String fieldValue);
	
	
	/**
	 * Returns whether or not the field value is currently valid by calling internally {@link #getErrors}. Display invalid markers.
	 */
	boolean validate();
	
	/**
	 * Returns whether or not the field value is currently valid by calling internally {@link #getErrors}. Does not display invalid markers
	 */
	boolean isValid();
	
	/**
	 * Associate one or more validation error messages with this field.
	 * Fields using this would use the method to manage the error displayer to display the messages.
	 */
	void markInvalid(ArrayList<String> errors);
	
	void markValid();
	
	/**
	 * Returns true if the value of this field has been changed from its originalValue..
	 * @return
	 */
	boolean isDirty();
	
	/**
	 * Reset originalValue to the current value.
	 */
	void resetOriginalValue();
	
	String getFieldValue();
	
	 void setFieldValue(String fieldValue);
	
}