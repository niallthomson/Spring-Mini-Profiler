package org.devcodes.miniprofiler.serialisation;

import org.devcodes.miniprofiler.ProfilerRequest;

public class ProfilerRequestSerialiser {
	private static JSONProfilerRequestSerialiser jsonSerialiser;
	
	static {
		jsonSerialiser = new JSONProfilerRequestSerialiser();
	}
	
	public static String toJSON(ProfilerRequest request) {
		return jsonSerialiser.serialise(request);
	}
}
