/**
 * 
 */
package com.appops.gwtgenerator.client.config.core;


import java.io.Serializable;

/**
 * @author Debasish Padhy Created it on 15-Aug-2012
 * All type instances are created from this 
 */

public interface Type extends Serializable {

	/**
	 * Name of the actual type as represented on client side. 
	 * @return
	 */
	public String getTypeName() ;
		
	/**
	 * @return display name of the entity to be used for user interaction purpose
	 */
	public String getDisplayName() ;
	
	/**
	 * this method returns an instance of the actual Type this Type represents. Critical to overcome 
	 * GWT / JavaScript limitations. how ? Makes a server call gets an instance from the server 
	 * 
	 * Or will need a client side wiring for pojos available through GWT.create(class) and if not creates an instance of 
	 * Entity with this type set
	 * @return 
	 */
	public Serializable newInstance() ;
	
	//@Deprecated
	public Long getTypeId();
	
	public void setTypeId(Long id);
	
	public Long getServiceId();
	
	public void setServiceId(Long serviceId);
}

