package com.appops.gwtgenerator.client.generator;

import in.appops.platform.core.shared.Configurable;

import java.io.Serializable;
import java.util.ArrayList;

import com.appops.gwtgenerator.client.config.util.Snippet;

/**
 * 
 * @author komal@ensarm.com This interface represents the components generated dynamically.
 * 
 * 
 */
public interface Dynamic extends Configurable {

	/**
	 * attaches the underlying component to the DOM
	 */
	public void attach();

	/**
	 * called once the configuration is set on the component. configurations at the component level are initialized or
	 * set using im methods.
	 */
	public void initialize();

	/**
	 * im-(invoke method), this will invoke method the intented method on the given instance.
	 * 
	 * @param methodName
	 * @param parameters
	 */
	public Object im(String methodName, ArrayList<Serializable> parameters) throws Exception;

	/**
	 * up-(update property), this is to update a property, invokes the setter method.
	 * 
	 * @param methodName
	 * @param parameters
	 */

	/*
	public void up(String methodName, Object[] parameters);
	
	*//**
	 * gp-(get property), this is to get the value of the property, invokes the getter method for property
	 * 
	 * @param methodName
	 * @param parameters
	 */
	/*
	public void gp(String methodName, Object[] parameters);*/

	/**
	 * The parent holds the event rule map it handles and executes all the events
	 * 
	 * @param snippet
	 */
	public void setParentSnippet(Snippet snippet);

	/**
	 * Returns parent of the dynamic component.
	 * 
	 * @return
	 */
	public Snippet getParentSnippet();

	public String getID();

	void setID(String iD);
}
