package com.sytecso.component;

import java.io.IOException;
import java.util.Collections;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.sytecso.dto.usuarioacceso.UserAccess;

@Component
public class CustomUserDeserializer extends StdDeserializer<UserAccess> {

	private static final long serialVersionUID = -2325581404709561475L;

	public CustomUserDeserializer() {
		this(null);
	}

	public CustomUserDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public UserAccess deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		String username = node.get("username").asText();
		String password = node.get("password").asText();
		return new UserAccess(username, password, Collections.emptyList());
	}

}
