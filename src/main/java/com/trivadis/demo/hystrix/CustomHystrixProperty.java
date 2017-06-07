package com.trivadis.demo.hystrix;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

public class CustomHystrixProperty extends JsonDeserializer<List<HystrixProperty>> {

	@Override
	public List<HystrixProperty> deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		List<HystrixProperty> result = new ArrayList<>();

		JsonNode node = p.getCodec().readTree(p);
		Iterator<JsonNode> properties = node.elements();
		while (properties.hasNext()) {
			JsonNode n = properties.next();
			String key = n.get("key").asText();
			String value = n.get("value").asText();
			result.add(new HystrixProperty(key, value));
		}
		return result;
	}

}
