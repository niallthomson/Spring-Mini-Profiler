package org.devcodes.miniprofiler.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.devcodes.miniprofiler.ProfilerManager;
import org.devcodes.miniprofiler.ProfilerRequest;
import org.devcodes.miniprofiler.serialisation.ProfilerRequestSerialiser;

public class ProfilerResponseFilter implements Filter {

	private ProfilerManager profilerManager = ProfilerManager.getDefaultInstance();

	/**
	 * The parameter name which enables profiling for a request
	 */
	private final static String PROFILE_PARAM_NAME = "profile";

	@Override
	public void init(FilterConfig config) throws ServletException {
		// Nothing
	}

	@Override
	public void destroy() {
		// Nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		String profileParam = request.getParameter(PROFILE_PARAM_NAME);

		boolean isActive = (profileParam != null);

		if (!isActive) {
			chain.doFilter(request, response);
			return;
		}

		HttpServletRequest servletRequest = ((HttpServletRequest) request);
		HttpSession session = servletRequest.getSession();
		PrintWriter out = response.getWriter();
		CharResponseWrapper wrapper = new CharResponseWrapper(
				(HttpServletResponse) response);

		ProfilerRequest profilerRequest = profilerManager.startRequest(
				getUrl(servletRequest), servletRequest.getRequestURI(),
				servletRequest.getRemoteHost(), session.getId(), isActive);

		try {
			chain.doFilter(request, wrapper);
		} finally {
			profilerManager.requestComplete();
		}

		String output = wrapper.toString();

		String json = ProfilerRequestSerialiser.toJSON(profilerRequest);

		output = output
				.replace(
						"<!-- [Profiler] -->",
						"<script language=\"javascript\" type=\"text/javascript\">var profilerOutput = "
								+ json + "</script>");

		response.setContentLength(output.length());

		out.write(output);
		out.close();
	}

	public void setProfilerManager(ProfilerManager profilerManager) {
		this.profilerManager = profilerManager;
	}

	public ProfilerManager getProfilerManager() {
		return profilerManager;
	}

	public String getUrl(HttpServletRequest req) {
		String reqUrl = req.getRequestURL().toString();
		String queryString = req.getQueryString();

		if (queryString != null) {
			queryString = queryString.replace(PROFILE_PARAM_NAME, "");

			if (queryString.length() > 0) {
				reqUrl += "?" + queryString;
			}
		}

		return reqUrl;
	}
}
