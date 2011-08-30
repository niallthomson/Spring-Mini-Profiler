package org.devcodes.miniprofiler.jstl;

import java.io.IOException;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * A custom JSTL tag which puts all the Javascript stuff for the profiler browser client.
 * 
 * This should be placed in the &laquo;head&raquo; of an HTML page.
 * 
 * @author Niall Thomson
 */
public class ProfilerClientTag extends TagSupport {
	private static final long serialVersionUID = -6548281178433830541L;

	private String localAssets;

	public int doStartTag() {
		try {
			if ((localAssets != null) && (!localAssets.isEmpty())) {
				pageContext.getOut().write(getLocalAssets(localAssets));
			}

			pageContext.getOut().write(getOnload(localAssets));

			// This will eventually be replaced with the profilers data for this
			// request
			// The reason we can't put the JSON straight in here is that the
			// view hasn't
			// finished rendering yet.
			pageContext.getOut().write("<!-- [Profiler] -->");
		} catch (IOException e) {
			System.err.println(e.toString());
		}

		return SKIP_BODY;
	}

	private String getOnload(String location) {
		return "<script language=\"javascript\" type=\"text/javascript\">$(document).ready(" + "function() {"
				+ "devcodes.profiler.init({\"location\": \""+location+"\"});" + "}" + ");</script>\n";
	}

	private String getLocalAssets(String location) {
		return createJSImport(location + "/jquery.tmpl.js")
				+ createJSImport("http://google-code-prettify.googlecode.com/svn/trunk/src/prettify.js")
				+ createJSImport("http://google-code-prettify.googlecode.com/svn/trunk/src/lang-sql.js")
				+ createCSSImport("http://google-code-prettify.googlecode.com/svn/trunk/src/prettify.css")
				+ createJSImport(location + "/profiler.js")
				+ createCSSImport(location + "/profiler.css");
	}

	private String createJSImport(String url) {
		return "<script language=\"javascript\" type=\"text/javascript\" src=\""
				+ url + "\"></script>\n";
	}

	private String createCSSImport(String url) {
		return "<link href=\"" + url
				+ "\" rel=\"stylesheet\" type=\"text/css\" />\n";
	}

	public void setLocalAssets(String localAssets) {
		this.localAssets = localAssets;
	}

	public String getLocalAssets() {
		return localAssets;
	}
}
