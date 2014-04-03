package com.appops.gwtgenerator.client.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Inherited
@Retention(RetentionPolicy.CLASS)
public @interface Tag {
	/**
	 * 
	 * Name of the tag
	 * @return	
	 */
	String tagname();
	
	/**
	 * Canonical class name the tag represents
	 * @return
	 */
	String classname() default "";
	
	/**
	 * Name of the library the class representing the tag belongs to.
	 * @return
	 */
	String library();
	
}
