package org.devcodes.miniprofiler.entries;

import java.util.List;

public interface IProfilerEntry {

	public String getTag();

	public void setTag(String tag);

	public long getStartTime();

	public void setStartTime(long startTime);

	public long getDuration();

	public void setDuration(long duration);

	public List<IProfilerEntry> getChildren();

	public void setChildren(List<IProfilerEntry> children);

	public void addChild(IProfilerEntry entry);

	public void setId(long id);

	public long getId();

	public void completed(long endTime);

	public void setFromStart(long fromStart);

	public long getFromStart();

	public String getDisplayString();

	public void setDepth(int depth);

	public int getDepth();

	public void addQuery(QueryProfilerEntry query);

	public void setQueries(List<QueryProfilerEntry> queries);

	public List<QueryProfilerEntry> getQueries();

	public void setQueryTime(long queryTime);

	public long getQueryTime();

	public void open(long rootStartTime);
	
	public void close();

}