package org.devcodes.miniprofiler;

import java.lang.reflect.Method;

public class InvokeAroundable implements IAroundable {
	
	private Method method;
	
	private Object target;
	
	private Object[] args;

	public InvokeAroundable(Method method, Object target, Object[] args) {
		this.method = method;
		this.target = target;
		this.args = args;
	}

	@Override
	public Object proceed() throws Throwable {
		return this.method.invoke(this.target, this.args);
	}

}
