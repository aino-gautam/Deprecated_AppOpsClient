/**
 * 
 */
package com.appops.gwtgenerator.client.config.core;

import java.io.Serializable;

import com.google.gwt.thirdparty.guava.common.annotations.GwtCompatible;


/**
 * @author Debasish Padhy Created it on 15-Aug-2012
 * 
 * This new style of Property creation e.g.
 * 
 * Property<String> str = new Property<String>() ;
 * 
 * str.getType(); // returns the Type instance representing String property type
 * setType is not provided since a property type is always determined from it value. 
 * 
 * This allows for any value types to be treated as properties.
 * 
 * Type of a single property instance is always set only once in its lifetime i.e. the first time you set the value to a valid value. 
 * 
 */
@SuppressWarnings("serial")
@GwtCompatible
public class Property<T extends Serializable> implements Serializable {
	
	private Type type = null ;
	protected T value = null ;
	private String name = null ;
	private boolean dirty = false ;
	//private Entity parent = null ;
	
	public Property(){
		
	}
	
	public Property(T vl){
		value = vl;
	}
	
	public Property(String nm, T vl){
		setName(nm);
		setValue(vl);
	}
	
	public Type getType() {
		return type ;
	}
	
	void setType(Type type) {
		this.type = type;
	}
	
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T val) {
		
		if(val == null)
			return;
		
		if ( this.value == null )
			this.value = val ;
			
		else if (!this.value.equals(val))
			this.value = val;
		
		else 
			return ;
		
		type = new MetaType(val.getClass());
		setDirty(true);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	/*public Entity getParent() {
		return parent;
	}
	
	public void setParent(Entity parent) {
		this.parent = parent;
	}*/
	
	/**
	 * example needs to be removed
	 */

	/*public void dummyTest(){
		
		Property<String> prop = new Property<String>();
		prop.setValue("some String");
		
		Property<Blob> prop1 = new Property<Blob>();
		
		Blob b = prop1.getValue() ;
		b.setMimeType("text/plain");
	}*/
}
