package com.utility.json;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

//import javax.inject.Inject;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonEncoder {
	//@Inject
	private Gson gson;
	
	public void toJson(Object entity, Class<?> type, OutputStream entityStream) throws IOException  {
		JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream, StandardCharsets.UTF_8));
		gson.toJson(entity, type, writer);
		writer.flush();
	}
	
	public Object fromJson(Class<Object> type, InputStream entityStream) {
		JsonReader reader = new JsonReader(new InputStreamReader(entityStream));
		return gson.fromJson(reader, type);
	}
}
