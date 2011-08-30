package org.devcodes.miniprofiler.serialisation;

import org.devcodes.miniprofiler.ProfilerRequest;

/**
 * The interface which must be implemented by classes which want to persist profiler results.
 * 
 * @author Niall Thomson
 */
public interface IProfilerRequestSerialiser {
	public String serialise(ProfilerRequest request);
}
