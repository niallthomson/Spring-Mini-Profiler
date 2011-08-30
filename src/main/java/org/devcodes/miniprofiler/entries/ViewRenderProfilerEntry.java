package org.devcodes.miniprofiler.entries;

/**
 * Profiler entry for the rendering of a Spring View.
 * 
 * @author Niall Thomson
 */
public class ViewRenderProfilerEntry extends AbstractProfilerEntry {
	private String viewName;

	public ViewRenderProfilerEntry(String viewName) {
		super("VIEW");
		this.viewName = viewName;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@Override
	public String getDisplayString() {
		return "Rendering View";
	}
}
