package org.devcodes.miniprofiler.entries;

import java.util.ArrayList;
import java.util.List;
/**
 * Base class for profiler entries, each of which represents a distinct section of code being profiled.
 * Examples of profiler entries include:
 * 
 * <ul>
 *  <li>HTTP requests</li>
 * 	<li>Method calls</li>
 * 	<li>Arbitrary sections of code</li>
 *  <li>JDBC queries</li>
 *  <li>View rendering</li>
 * <ul>
 * 
 * @author Niall Thomson
 */
public abstract class AbstractProfilerEntry implements IProfilerEntry {
	private long id;
	
	private String tag;

	private long startTime;
	
	private long duration;
	
	private long fromStart;
	
	private int depth;
	
	private List<QueryProfilerEntry> queries;
	
	private List<IProfilerEntry> children;
	
	private long queryTime;
	
	public AbstractProfilerEntry(String tag) {
		this.tag = tag;
		this.children = new ArrayList<IProfilerEntry>();
		this.setQueries(new ArrayList<QueryProfilerEntry>());
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public List<IProfilerEntry> getChildren() {
		return children;
	}

	public void setChildren(List<IProfilerEntry> children) {
		this.children = children;
	}
	
	public void addChild(IProfilerEntry entry) {
		this.children.add(entry);
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void completed(long endTime) {
		this.duration = endTime - this.startTime;
	}

	public void setFromStart(long fromStart) {
		this.fromStart = fromStart;
	}

	public long getFromStart() {
		return fromStart;
	}
	
	public abstract String getDisplayString();

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getDepth() {
		return depth;
	}
	
	public void addQuery(QueryProfilerEntry query) {
		this.queries.add(query);
		
		this.queryTime += query.getDuration();
	}

	public void setQueries(List<QueryProfilerEntry> queries) {
		this.queries = queries;
	}

	public List<QueryProfilerEntry> getQueries() {
		return queries;
	}

	public void setQueryTime(long queryTime) {
		this.queryTime = queryTime;
	}

	public long getQueryTime() {
		return queryTime;
	}
	
	public void open(long rootStartTime) {
		long startTime = System.currentTimeMillis();
		this.setStartTime(startTime);
		
		long fromStart = startTime - rootStartTime;
		this.setFromStart(fromStart);
	}
	
	public void close() {
		long endTime = System.currentTimeMillis();
		
		this.setDuration(endTime - getStartTime());
	}
}
