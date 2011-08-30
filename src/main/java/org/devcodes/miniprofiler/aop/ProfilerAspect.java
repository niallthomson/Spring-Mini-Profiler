package org.devcodes.miniprofiler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.devcodes.miniprofiler.ProfilerManager;

/**
 * This AspectJ aspect defines pointcuts and advice for profiling a tiered 
 * Spring MVC application. It will profile:
 * 
 * <ul>
 * 	<li>Method calls to Spring stereotypes such as Component, Controller, Service, Repository etc.</li>
 * 	<li>The rendering of Spring Views</li>
 *  <li>Spring-managed beans annotated with the @Profile annotation</li>
 *  <li>JDBC queries is the DataSource has been proxied (see docs)</li>
 * </ul>
 * 
 * @author Niall Thomson
 */
@Aspect
public class ProfilerAspect {
	private ProfilerManager profilerManager = ProfilerManager.getDefaultInstance();
	
	@Pointcut("execution(@org.devcodes.miniprofiler.Profile * *(..))")
	public void profile() {
	}
	
	@Pointcut("within(@org.springframework.stereotype.Component *)")
	public void component() {
	}

	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void controller() {
	}
	
	@Pointcut("within(@org.springframework.stereotype.Service *)")
	public void service() {
	}
	
	@Pointcut("within(@org.springframework.stereotype.Repository *)")
	public void repository() {
	}
	
	@Pointcut("execution(* org.springframework.web.servlet.View+.render(..))")
	public void view() {
	}
	
	@Pointcut("within(@org.devcodes.miniprofiler.ProfilerIgnore *)")
	public void profilerIgnore() {
	}
	
	@Pointcut("execution(public * *(..))")
	public void method() {
	}
	
	@Around("component() && method() && !service() && !controller() && !repository()")
	public Object traceComponent(ProceedingJoinPoint pjp) throws Throwable {
		return getProfilerManager().profileMethodCall(pjp, "OTHER");
	}
	
	@Around("repository() && method()")
	public Object traceRepository(ProceedingJoinPoint pjp) throws Throwable {
		return getProfilerManager().profileMethodCall(pjp, "REPOSITORY");
	}
	
	@Around("service() && method()")
	public Object traceService(ProceedingJoinPoint pjp) throws Throwable {
		return getProfilerManager().profileMethodCall(pjp, "SERVICE");
	}
	
	@Around("controller() && method()")
	public Object traceController(ProceedingJoinPoint pjp) throws Throwable {
		return getProfilerManager().profileMethodCall(pjp, "CONTROLLER");
	}
	
	@Around("view()")
	public Object traceView(ProceedingJoinPoint pjp) throws Throwable {
		return getProfilerManager().profileViewRender(pjp, "");
	}

	public ProfilerManager getProfilerManager() {
		return this.profilerManager;
	}
	
	public void setProfilerManager(ProfilerManager profilerManager) {
		this.profilerManager = profilerManager;
	}
}
