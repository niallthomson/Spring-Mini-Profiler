package org.devcodes.miniprofiler.entries;

/**
 * A profiler entry for a standard method call.
 * 
 * @author Niall Thomson
 */
public class MethodCallProfilerEntry extends AbstractProfilerEntry {
	private String className;
	private String methodName;

	public MethodCallProfilerEntry(String tag, String className, String methodName) {
		super(tag);
		
		this.className = className;
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public String getDisplayString() {
		return "Calling "+className+"."+methodName+"()";
	}
}