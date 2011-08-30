package org.devcodes.miniprofiler;

import java.util.Date;

import org.devcodes.miniprofiler.entries.IProfilerEntry;

public /**
 * Stores stuff for the current session, as we need separate profiler data
 * per session.
 * 
 * @author Niall Thomson
 */
class ProfilerSession {
	private IProfilerEntry currentEntry;
	private IProfilerEntry parentEntry;
	
	private ProfilerRequest request;
	
	private boolean isActive;

	public ProfilerSession(String url, String remoteHost, String sessionId, boolean isActive, IProfilerEntry rootEntry) {
		request = new ProfilerRequest(new Date(), sessionId, url, remoteHost);
		
		this.setActive(isActive);
		this.setRootEntry(rootEntry);
		this.setCurrentEntry(rootEntry);
	}
	
	public void preProfile(IProfilerEntry entry) {
		this.initialiseTiming(entry);
		
		this.updateStack(entry);
	}

	private void initialiseTiming(IProfilerEntry entry) {
		long startTime = System.currentTimeMillis();
		entry.setStartTime(startTime);
		
		long fromStart = startTime - getRootEntry().getStartTime();
		entry.setFromStart(fromStart);
	}
	
	private void updateStack(IProfilerEntry entry) {
		this.parentEntry = getCurrentEntry();
		
		entry.setDepth(this.parentEntry.getDepth() + 1);
		
		this.setCurrentEntry(entry);
		
		this.parentEntry.addChild(entry);
	}

	public void postProfile(IProfilerEntry entry) throws ProfilerException {
		if(entry != currentEntry) {
			throw new ProfilerException("The profiler session appears to be out of sync");
		}
		
		this.finishTiming();
		
		this.setCurrentEntry(this.parentEntry);
	}
	
	private void finishTiming() {
		long endTime = System.currentTimeMillis();
		
		this.currentEntry.setDuration(endTime - currentEntry.getStartTime());
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
