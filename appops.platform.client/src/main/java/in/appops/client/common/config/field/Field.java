package in.appops.client.common.config.field;

import in.appops.platform.core.shared.Configurable;

import java.util.ArrayList;

/**
 * This provides provides a common interface for the logical behaviour and state of fields + 
 * getting setting field values, validation triggering etc. These are configurable
 * .
 * // TODO Define local event handling mechanism 
 * 
 * @author nitish@ensarm.com
 */
public interface Field extends Configurable {
	
	/**
	 * Configure a field, apply possible configurations before creating it.
	 */
	void configure();
	
	/**
	 *  Create a field.
	 */
	void create();
	
	/**
	 * Sets a data value into the field.
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
	 * Will do the field validation and return a list of error messages for validation failed. Would be called internally 
	 */
	ArrayList<String> getErrors(Object fieldValue);
	
	
	/**
	 * Returns whether or not the field value is currently valid by calling internally {@link #getErrors}
	 */
	boolean validate();
	
	/**
	 * Associate one or more validation error messages with this field.
	 * Composite fields using this should would this method to update the component error displayer to display the messages.
	 * Would be called internally by validate().
	 */
	void markInvalid(ArrayList<String> errors);
	
}
