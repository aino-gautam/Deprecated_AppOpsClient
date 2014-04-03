package com.appops.gwtgenerator.client.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Inherited
@Retention(RetentionPolicy.CLASS)
public @interface Attribute {
	
	/**Is the property inherited from parent*/
	boolean inherited() default true;
	
	/**Tells whether the property value has to be validated*/
	boolean restricted() default false;
	
	String defaultValue() default "";
	
	/**whether the property value can be changed?, if not the default value will be applied */
	boolean isImmutable() default true;
	
	String[] alternateValues() default {};
	
}
