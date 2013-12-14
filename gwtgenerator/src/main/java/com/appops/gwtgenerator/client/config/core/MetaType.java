package com.appops.gwtgenerator.client.config.core;

import java.io.Serializable;

import com.google.gwt.thirdparty.guava.common.annotations.GwtCompatible;


/**
 * @author Debasish - deba@ensarm.com Created on Feb 7, 2012 - 7:57:01 PM
 * 
 *         Standard implementation for Types. As a matter of practice use only
 *         the overridden constructor. Default constructor should not be used. 
 *         This class has special wiring to instantiate an blank
 *         entity dynamically. thus AppTypes.CONTACT.newInstance() will return
 *         you a blank entity of the destination POJO i.e Contact or else if
 *         Contact is not a client side pojo class it will return you an
 *         instance of Entity class itself. Pojo instantiation will be handled by wiring a gin injector 
 *         
 *         This is used to bypass the dynamic instantiation limitation in GWT.
 *         
 *         Id's are generated automatically and an unique id is fetched always however these typeId's may not be persistent 
 *         unless provided externally. So make sure you do not depend on the value across sessions
 *         
 *         TypeName's provided will be internally matched to return an existing type i.. 
 *         
 *         MetaType t = new MetaType(String.class) ;
 *         MetaType t1 = new MetaType(String.class) ;
 *         
 *         assert (t.equals(t1)) -> must succeed under all circumstances. 
 *         
 *         To make this happen we need to calculate a hash code based on the type string. 
 *         
 *         MetaType is immutable 
 */
@GwtCompatible
public final class MetaType implements Type {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String typeName = null; 
	private Long typeId = null;
	private Long serviceId = null;

	public MetaType() {

	}

	public MetaType(String tp) {
		this(-1, tp);
	}
	
	public <T extends Serializable> MetaType(Class<T> cls){
		this(-1, cls.getName());
	}

	public <T extends Serializable> MetaType(Long typeId, Long serviceId, Class<T> cls){
		this(typeId, serviceId, cls.getName());
	}
	
	/**
	 * Write code for id = -1 here. It should auto select a non colliding id in
	 * this case.
	 * 
	 */
	public MetaType(int id, String tp) {
		setTypeName(tp);
	}
	
	public MetaType(Long typeid, Long serviceId, String tp) {
		setTypeId(typeid);
		setTypeName(tp);
		setServiceId(serviceId);
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String str) {
			typeName = str ;
	}

	public boolean equals(Type t) {
		
		return typeName.equals(t.getTypeName());
	}



	public String getDisplayName() {
		return typeName;
	}

	/**
	 * TODO implement this through a Type instantiation provider
	 */
	/* 
	public Entity newInstance() {
		return null;
	}
*/
	/* (non-Javadoc)
	 * @see in.appops.platform.core.entity.type.Type#getTypeId()
	 */
	
	public Long getTypeId() {
		return typeId;
	}

	@Override
	public void setTypeId(Long id) {
		this.typeId = id;
	}

	@Override
	public Long getServiceId() {
		return serviceId;
	}

	@Override
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public Serializable newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
