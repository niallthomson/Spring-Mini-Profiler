package org.devcodes.miniprofiler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.devcodes.miniprofiler.IAroundable;

public class AspectJAroundable implements IAroundable {
	
	private ProceedingJoinPoint pjp;
	
	public AspectJAroundable(ProceedingJoinPoint pjp) {
		this.pjp = pjp;
	}

	@Override
	public Object proceed() throws Throwable {
		return pjp.proceed();
	}
}
