/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package pzalejko.iot.common.home.api.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The {@link JsonConverter} is responsible for parsing a JSON {@link String} message to an object.
 *
 */
public class JsonConverter {

	private final Gson gson;

	/**
	 * Creates an initializes the {@link JsonConverter}.
	 */
	public JsonConverter() {
		gson = new GsonBuilder().create();
	}

	/**
	 * Parses the given message to the given type.
	 * 
	 * @param msg the message to be parsed.
	 * @param clazz the type to which a given message must be pursed.
	 * @param <T> the type of object to which a message must be parsed.
	 * 
	 * @return an object created from the given message.
	 * 
	 * @throws NullPointerException if either a message or class is null.
	 */
	public <T> T parse(String msg, Class<T> clazz) {
		if (msg == null) {
			throw new NullPointerException("Message cannot be null.");
		}

		if (clazz == null) {
			throw new NullPointerException("Class cannot be null");
		}
		return gson.fromJson(msg, clazz);
	}

	/**
	 * Parses the given object to Json.
	 * 
	 * @param object the object to be parsed.
	 * @return the Json message.
	 * 
	 * @throws NullPointerException if the given object is null.
	 */
	public String parse(Object object) {
		if (object == null) {
			throw new NullPointerException("Object cannot be null.");
		}

		return gson.toJson(object);
	}
}