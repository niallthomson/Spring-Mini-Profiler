package org.devcodes.miniprofiler.aop;

import java.util.List;

import net.ttddyy.dsproxy.QueryInfo;

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
	
	@Pointcut("execution(@org.devcodes.miniprofiler.annotations.Profile * *(..))")
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
	
	@Pointcut("execution(* org.devcodes.miniprofiler.jdbc.ProfilerDataSourceListener+.aroundQuery(..))")
	public void query() {
	}
	
	@Pointcut("within(@org.devcodes.miniprofiler.annotations.ProfilerIgnore *)")
	public void profilerIgnore() {
	}
	
	@Pointcut("execution(public * *(..))")
	public void method() {
	}
	
	@Around("component() && method() && !service() && !controller() && !repository()")
	public Object aroundComponentMethod(ProceedingJoinPoint pjp) throws Throwable {
		return this.profileMethod(pjp, "OTHER");
	}
	
	@Around("repository() && method()")
	public Object aroundRepositoryMethod(ProceedingJoinPoint pjp) throws Throwable {
		return this.profileMethod(pjp, "REPOSITORY");
	}
	
	@Around("service() && method()")
	public Object aroundServiceMethod(ProceedingJoinPoint pjp) throws Throwable {
		return this.profileMethod(pjp, "SERVICE");
	}
	
	@Around("controller() && method()")
	public Object aroundControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
		return this.profileMethod(pjp, "CONTROLLER");
	}
	
	private Object profileMethod(ProceedingJoinPoint pjp, String tag) throws Throwable {
		return getProfilerManager().profileMethodCall(new AspectJAroundable(pjp), tag, pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName());
	}
	
	@Around("query()")
	public Object aroundQueryExecution(ProceedingJoinPoint pjp) throws Throwable {
		List<QueryInfo> queryInfoList = (List<QueryInfo>) pjp.getArgs()[1];
		return getProfilerManager().profileQueryExecution(new AspectJAroundable(pjp), queryInfoList);
	}
	
	@Around("view()")
	public Object aroundViewRender(ProceedingJoinPoint pjp) throws Throwable {
		return getProfilerManager().profileViewRender(new AspectJAroundable(pjp), "");
	}

	public ProfilerManager getProfilerManager() {
		return this.profilerManager;
	}
	
	public void setProfilerManager(ProfilerManager profilerManager) {
		this.profilerManager = profilerManager;
	}
}
