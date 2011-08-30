package org.devcodes.miniprofiler;

import java.io.Closeable;
import java.io.IOException;

import org.devcodes.miniprofiler.entries.IProfilerEntry;

/**
 * A ProfilerResource instance is returned when profiling is start using a try-with-resources
 * block. It implements java.io.Closeable so that when the resource is closed the profiler will
 * consider profiling of that section completed.
 *  
 * @author Niall Thomson
 */
public class ProfilerResource implements Closeable {
	ProfilerManager profilerManager;
	IProfilerEntry entry;
	
	public ProfilerResource(ProfilerManager profilerManager, IProfilerEntry entry) {
		this.profilerManager = profilerManager;
		this.entry = entry;
	}
	
	@Override
	public void close() throws IOException {
		try {
			this.profilerManager.finishAdhocProfiling(this.entry);
		} catch (ProfilerException e) {
			throw new IOException("An exception occurred while profiling", e);
		}
	}
}
