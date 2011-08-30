package org.devcodes.miniprofiler.entries;

/**
 * Profiler entry for an arbitrary section of code.
 * 
 * @author Niall Thomson
 */
public class AdHocProfilerEntry extends AbstractProfilerEntry {
	private String description;

	public AdHocProfilerEntry(String description) {
		super("ADHOC");
		
		this.description = description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String getDisplayString() {
		return description;
	}
}
