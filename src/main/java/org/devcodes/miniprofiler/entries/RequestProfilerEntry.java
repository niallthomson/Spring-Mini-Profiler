package org.devcodes.miniprofiler.entries;

/**
 * Profiler entry for an HTTP request.
 * 
 * @author Niall Thomson
 */
public class RequestProfilerEntry extends AbstractProfilerEntry {
	private String url;

	public RequestProfilerEntry(String url, long currentTimeMillis) {
		super("REQUEST");
		
		this.setUrl(url);
		this.setStartTime(currentTimeMillis);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	
	public String getDisplayString() {
		return "Requesting "+this.getUrl();
	}
}
