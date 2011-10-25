package org.devcodes.miniprofiler.jdbc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import net.ttddyy.dsproxy.proxy.ProxyDataSource;

/**
 * An extension of the ProxyDataSource class from datasource-proxy which is used 
 * to wrap a JDBC DataSource object so we can intercept method calls. It adds a listener
 * which is used by the profiler to get timings etc.
 * 
 * @author Niall Thomson
 */
public class ProfilerProxyDataSource extends ProxyDataSource implements ApplicationContextAware {	
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		// 
	}
}
