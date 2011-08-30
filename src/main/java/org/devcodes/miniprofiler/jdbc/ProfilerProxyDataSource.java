package org.devcodes.miniprofiler.jdbc;

import org.devcodes.miniprofiler.ProfilerManager;

import net.ttddyy.dsproxy.proxy.ProxyDataSource;

/**
 * An extension of the ProxyDataSource class from datasource-proxy which is used 
 * to wrap a JDBC DataSource object so we can intercept method calls. It adds a listener
 * which is used by the profiler to get timings etc.
 * 
 * @author Niall Thomson
 */
public class ProfilerProxyDataSource extends ProxyDataSource {	
	public ProfilerProxyDataSource() {
		setProfilerManager(ProfilerManager.getDefaultInstance());
	}
	
	public void setProfilerManager(ProfilerManager profilerManager) {
		ProfilerDataSourceListener listener = new ProfilerDataSourceListener();
		listener.setProfilerManager(profilerManager);
	}
}
