package org.devcodes.miniprofiler;

import java.util.Date;
import java.util.Stack;

import org.devcodes.miniprofiler.entries.IProfilerEntry;
import org.devcodes.miniprofiler.entries.QueryProfilerEntry;

/**
 * Stores stuff for the current session, as we need separate profiler data
 * per session.
 * 
 * @author Niall Thomson
 */
public class ProfilerSession {
	private IProfilerEntry currentEntry;
	
	private Stack<IProfilerEntry> stack;
	
	private ProfilerRequest request;
	
	private boolean isActive;

	public ProfilerSession(String url, String remoteHost, String sessionId, boolean isActive, IProfilerEntry rootEntry) {
		request = new ProfilerRequest(new Date(), sessionId, url, remoteHost);
		
		stack = new Stack<IProfilerEntry>();
		
		this.setActive(isActive);
		this.setRootEntry(rootEntry);
		this.setCurrentEntry(rootEntry);
	}
	
	public void preProfile(IProfilerEntry entry) {
		// TODO: This is dirty dirty, find a better way
		if(entry instanceof QueryProfilerEntry) {
			this.currentEntry.addQuery((QueryProfilerEntry)entry);
		}
		
		this.initialiseTiming(entry);
		
		this.updateStack(entry);
	}

	private void initialiseTiming(IProfilerEntry entry) {
		entry.open(this.getRootEntry().getStartTime());
	}
	
	private void updateStack(IProfilerEntry entry) {
		IProfilerEntry currentEntry = getCurrentEntry();
		currentEntry.addChild(entry);
		
		this.stack.push(currentEntry);
		
		entry.setDepth(currentEntry.getDepth() + 1);
		
		this.setCurrentEntry(entry);
	}

	public void postProfile(IProfilerEntry entry) throws ProfilerException {
		if(entry != currentEntry) {
			throw new ProfilerException("The profiler session appears to be out of sync");
		}
		
		this.finishTiming();
		
		this.setCurrentEntry(this.stack.pop());
	}
	
	private void finishTiming() {
		this.currentEntry.close();
	}

	public void setCurrentEntry(IProfilerEntry entry) {
		this.currentEntry = entry;
	}

	public IProfilerEntry getCurrentEntry() {
		return this.currentEntry;
	}

	public void setRootEntry(IProfilerEntry rootEntry) {
		request.setRootEntry(rootEntry);
	}

	public IProfilerEntry getRootEntry() {
		return request.getRootEntry();
	}

	public ProfilerRequest getRequest() {
		return request;
	}

	public void setRequest(ProfilerRequest request) {
		this.request = request;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}
}
