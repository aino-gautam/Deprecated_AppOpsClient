package com.appops.gwtgenerator.client.generator;

public interface Dynamic {
	
	/**
	 * im-(invoke method), this will invoke method the intented method on the given instance.
	 * @param methodName
	 * @param parameters
	 */
	public Object im(String methodName, Object[] parameters) throws Exception;
	
	/**
	 * up-(update property), this is to update a property, invokes the setter method.
	 * @param methodName
	 * @param parameters
	 *//*
	public void up(String methodName, Object[] parameters);
	
	*//**
	 * gp-(get property), this is to get the value of the property, invokes the getter method for property
	 * @param methodName
	 * @param parameters
	 *//*
	public void gp(String methodName, Object[] parameters);*/
	
}
