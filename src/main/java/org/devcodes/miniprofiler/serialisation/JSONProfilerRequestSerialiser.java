package org.devcodes.miniprofiler.serialisation;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.devcodes.miniprofiler.ProfilerRequest;

/**
 * A JSON implementation of the IProfilerRequestSerialiser interface which uses Jackson to 
 * map the ProfilerRequest object to a JSON string.
 * 
 * @author Niall Thomson
 */
public class JSONProfilerRequestSerialiser implements IProfilerRequestSerialiser {
	@Override
	public String serialise(ProfilerRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.defaultPrettyPrintingWriter();
		
		String result = "";
		
		try {
			result = writer.writeValueAsString(request);
		} catch (Exception e) {
			// LOG
		}
		
		return result;
	}
}
