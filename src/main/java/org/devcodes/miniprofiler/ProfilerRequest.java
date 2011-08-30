package org.devcodes.miniprofiler;

import java.util.Date;

import org.devcodes.miniprofiler.entries.IProfilerEntry;

/**
 * Profile data for a single HTTP request
 * 
 * @author Niall Thomson
 */
public class ProfilerRequest {
	private long id;
	
	private String url;
	
	private Date date;
	
	private String sessionId;
	
	private String remoteHost;
	
	private IProfilerEntry rootEntry;
	
	public ProfilerRequest(Date date, String sessionId, String url,
			String remoteHost) {
		this.date = date;
		this.sessionId = sessionId;
		this.url = url;
		this.remoteHost = remoteHost;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public IProfilerEntry getRootEntry() {
		return rootEntry;
	}

	public void setRootEntry(IProfilerEntry rootEntry) {
		this.rootEntry = rootEntry;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public String getRemoteHost() {
		return remoteHost;
	}
}
