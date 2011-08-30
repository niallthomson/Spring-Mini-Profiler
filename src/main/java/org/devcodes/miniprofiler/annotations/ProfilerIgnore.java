package org.devcodes.miniprofiler.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for indicating classes which should be ignore by the profiler.
 * 
 * @author Niall Thomson
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ProfilerIgnore {

}
