package org.devcodes.miniprofiler;

import java.util.List;

import net.ttddyy.dsproxy.QueryInfo;

import org.devcodes.miniprofiler.entries.AdHocProfilerEntry;
import org.devcodes.miniprofiler.entries.IProfilerEntry;
import org.devcodes.miniprofiler.entries.MethodCallProfilerEntry;
import org.devcodes.miniprofiler.entries.AbstractProfilerEntry;
import org.devcodes.miniprofiler.entries.QueryProfilerEntry;
import org.devcodes.miniprofiler.entries.RequestProfilerEntry;
import org.devcodes.miniprofiler.entries.ViewRenderProfilerEntry;
import org.devcodes.miniprofiler.persistence.ProfilerPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the profiler sessions.
 * 
 * @author Niall Thomson
 */
public class ProfilerManager {
	/**
	 * The default ProfilerManager instance
	 */
	private static ProfilerManager defaultInstance;
	
	/**
	 * The persistence service instance
	 */
	private ProfilerPersistenceService tracePersistenceService;
	
	private ThreadLocal<ProfilerSession> session = new ThreadLocal<ProfilerSession>();
	
	private boolean logging = false;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Profile an arbitrary method call.
	 * 
	 * @param 	pjp			the AspectJ PJP
	 * @param 	tag			the tag to use for the profiler entry
	 * @return				the return value of the method that was called
	 * @throws 	Throwable
	 */
	public Object profileMethodCall(IAroundable aroundable, String tag, String simpleName, String methodName) throws Throwable {
		AbstractProfilerEntry newEntry = new MethodCallProfilerEntry(tag, simpleName, methodName);

		return this.doProfile(aroundable, newEntry);
	}
	
	/**
	 * Profile the rendering of a Spring View.
	 * 
	 * @param 	pjp		the AspectJ PJP
	 * @param 	view	the name of the View being rendered
	 * @return			the return value of the view being rendered
	 * @throws 	Throwable
	 */
	public Object profileViewRender(IAroundable aroundable, String view) throws Throwable {
		IProfilerEntry newEntry = new ViewRenderProfilerEntry(view);
		
		return this.doProfile(aroundable, newEntry);
	}
	
	public Object profileQueryExecution(IAroundable aroundable, List<QueryInfo> queryInfoList) throws Throwable {
		QueryProfilerEntry newEntry = new QueryProfilerEntry(queryInfoList);
		
		return this.doProfile(aroundable, newEntry);
	}
	
	private Object doProfile(IAroundable aroundable, IProfilerEntry entry) throws Throwable {
		ProfilerSession session = this.getProfilerSession();
		
		if((session != null) && (session.isActive())) {
			session.preProfile(entry);
			
			Object ret = aroundable.proceed();
			
			session.postProfile(entry);
			
			if(this.logging) {
				logger.trace("["+entry.getTag()+"] "+entry.getDisplayString());
			}
			
			return ret;
		}

		return aroundable.proceed();
	}
	
	private ProfilerSession getProfilerSession() {
		return this.session.get();
	}
	
	public ProfilerRequest getProfilerRequest() {
		return this.getProfilerSession().getRequest();
	}

	public void setProfilerPersistenceService(ProfilerPersistenceService tracePersistenceService) {
		this.tracePersistenceService = tracePersistenceService;
	}

	public ProfilerPersistenceService getProfilerPersistenceService() {
		return this.tracePersistenceService;
	}

	/**
	 * Start profiling an HTTP request. This starts a new profiler session.
	 * 
	 * @param fullUrl
	 * @param uri
	 * @param remoteHost
	 * @param sessionId
	 * @param isActive
	 * @return
	 */
	public ProfilerRequest startRequest(String fullUrl, String uri, String remoteHost, String sessionId, boolean isActive) {
		IProfilerEntry rootEntry = null;
		
		if(isActive) {
			rootEntry = new RequestProfilerEntry(fullUrl, System.currentTimeMillis());
		}
		
		ProfilerSession theSession = new ProfilerSession(uri, remoteHost, sessionId, isActive, rootEntry);
		
		this.session.set(theSession);
		
		return theSession.getRequest();
	}
	
	/**
	 * Finishing profiling an HTTP request
	 */
	public void requestComplete() {
		if(this.getProfilerSession().isActive()) {
			this.getProfilerSession().getRootEntry().completed(System.currentTimeMillis());
			
			if(this.tracePersistenceService != null) {
				this.tracePersistenceService.save(this.getProfilerRequest());
			}
		}
		
		this.session.remove();
	}
	
	/**
	 * Start profiling an arbitrary section of code.
	 * 
	 * @param 	description		a description of the code being profiled
	 * @return					the ProfilerEntry created for the code
	 */
	public IProfilerEntry startAdhocProfiling(String description) {
		AdHocProfilerEntry entry = new AdHocProfilerEntry(description);
		
		ProfilerSession session = this.getProfilerSession();
		
		if((session != null) && (session.isActive())) {
			session.preProfile(entry);
		}
		
		return entry;
	}
	
	/**
	 * Finishes profiling an arbitrary section of code started by using startAdhocProfiling.
	 * 
	 * @param 		entry	the ProfilerEntry that was returned by startAdhocProfiling
	 * @throws 		ProfilerException
	 */
	public void finishAdhocProfiling(IProfilerEntry entry) throws ProfilerException {
		ProfilerSession session = this.getProfilerSession();
		
		if((session != null) && (session.isActive())) {
			session.postProfile(entry);
		}
	}
	
	/**
	 * Starts profiling as arbitrary section of code, similar to startAdhocProfiling. However, 
	 * this method should be used which using a try-with-resources block.
	 * 
	 * @param 	description		a description of the code being profiled
	 * @return					the ProfilerResource which manages stopping the profiler for this section of code
	 */
	public ProfilerResource profileAsResource(String description) {
		return new ProfilerResource(this, this.startAdhocProfiling(description));
	}
	
	/**
	 * Gets the ProfilerManager instance. The profiler will automatically use this unless it is 
	 * provided with one, which cuts down on boilerplate when DI is not required.
	 * 
	 * @return	the default ProfilerManager instance
	 */
	public static ProfilerManager getDefaultInstance() {
		if(defaultInstance == null) {
			defaultInstance = new ProfilerManager();
		}
		
		return defaultInstance;
	}
}