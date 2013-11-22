package com.appops.gwtgenerator.client.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Inherited
@Retention(RetentionPolicy.CLASS)
public @interface FiresEvent {
	
	boolean inherited() default true;
	
	String eventName();
	
	String[] param();
}
