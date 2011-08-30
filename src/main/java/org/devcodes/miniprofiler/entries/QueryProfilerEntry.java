package org.devcodes.miniprofiler.entries;

import java.util.List;

import net.ttddyy.dsproxy.QueryInfo;

public class QueryProfilerEntry extends AbstractProfilerEntry {
	private String query;
	
	public QueryProfilerEntry() {
		super("SQL");
	}

	public QueryProfilerEntry(String query) {
		this();
		
		this.setQuery(query);
	}

	public QueryProfilerEntry(List<QueryInfo> queryInfoList) {
		this(createQueryString(queryInfoList));
	}
	
	private static String createQueryString(List<QueryInfo> queryInfoList) {
		String query = "";
		
		for(QueryInfo queryInfo : queryInfoList) {
			query += queryInfo.buildQueryString();
		}
		
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	@Override
	public String getDisplayString() {
		return query;
	}
}
