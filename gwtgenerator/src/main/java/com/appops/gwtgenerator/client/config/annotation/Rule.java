package com.appops.gwtgenerator.client.config.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface Rule {
	Attribute[] value() default {};
	
}
