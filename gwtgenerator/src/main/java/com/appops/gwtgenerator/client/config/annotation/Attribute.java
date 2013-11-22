package com.appops.gwtgenerator.client.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Inherited
@Retention(RetentionPolicy.CLASS)
public @interface Attribute {
	
	String name();
	
	/**Is the property inherited from parent*/
	boolean inherited() default true;
	
	/**Tells whether the property value has to be validated*/
	boolean restricted() default false;
	
	Class type();
	
}
