package org.devcodes.miniprofiler.jdbc;

import java.lang.reflect.Method;
import java.util.List;

import org.devcodes.miniprofiler.InvokeAroundable;
import org.devcodes.miniprofiler.ProfilerManager;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.AbstractQueryExecutionListener;

/**
 * An empty data source proxy listener which is used as a pointcut by the AOP stuff.
 * 
 * @author Niall Thomson
 */
public class ProfilerDataSourceListener extends AbstractQueryExecutionListener {
	
	private ProfilerManager profilerManager = ProfilerManager.getDefaultInstance();
	
	public Object aroundQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList, Object target, Method method, Object[] args) throws Throwable {
		return profilerManager.profileQueryExecution(new InvokeAroundable(method, target, args), queryInfoList);
    }

	public void setProfilerManager(ProfilerManager profilerManager) {
		this.profilerManager = profilerManager;
	}

	public ProfilerManager getProfilerManager() {
		return profilerManager;
	}

}