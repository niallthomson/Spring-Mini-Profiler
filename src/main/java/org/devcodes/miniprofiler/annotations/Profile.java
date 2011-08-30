package org.devcodes.miniprofiler.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates a method which should be profiled. This need only be used on Spring-managed beans
 * which are not annotated as a Spring stereotype (ie. @Service, @Controller).
 * 
 * Note: This can ONLY be used on Spring-managed beans.
 * 
 * @author Niall Thomson
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Profile {
	/**
	 * @return A description of the method being profiled.
	 */
	public String description() default "";
}
