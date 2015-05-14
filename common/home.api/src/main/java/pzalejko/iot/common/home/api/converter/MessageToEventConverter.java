/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.common.home.api.converter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import pzalejko.iot.common.home.api.TemperatureAlertEntity;
import pzalejko.iot.common.home.api.TemperatureEntity;
import pzalejko.iot.common.home.api.event.EventType;

/**
 * The {@link MessageToEventConverter} is responsible for converting messages to events.
 */
public class MessageToEventConverter {

	private final Map<EventType, Converter> converters;
	private JsonConverter jsonConverter;

	/**
	 * Creates and initializes {@link MessageToEventConverter}.
	 */
	public MessageToEventConverter() {
		converters = new HashMap<EventType, Converter>();
		jsonConverter = new JsonConverter();

		converters.put(EventType.PUBLISH_LED_STATUS_REQUEST, new Converter() {

			@Override
			public Serializable convert(Object data) {
				if (data instanceof byte[]) {
					return Boolean.TRUE;
				}

				return Boolean.TRUE.toString().getBytes();
			}
		});
		converters.put(EventType.PUBLISH_TEMPERATURE_REQUEST, new Converter() {

			@Override
			public Serializable convert(Object data) {
				if (data instanceof byte[]) {
					return Boolean.TRUE;
				}

				return Boolean.TRUE.toString().getBytes();
			}
		});

		converters.put(EventType.PUBLISH_TEMPERATURE_ALERT_REQUEST, new Converter() {

			@Override
			public Serializable convert(Object data) {
				if (data instanceof byte[]) {
					return Boolean.TRUE;
				}

				return Boolean.TRUE.toString().getBytes();
			}
		});

		converters.put(EventType.UPDATE_LED, new Converter() {

			@Override
			public Serializable convert(Object data) {
				if (data instanceof byte[]) {
					final byte[] bytes = (byte[]) data;
					return Boolean.parseBoolean(new String(bytes));
				} else if (data instanceof Boolean) {
					return data.toString().getBytes();
				} else {
					return null;
				}
			}
		});
		converters.put(EventType.UPDATE_TEMPERATURE_ALERT, new Converter() {

			@Override
			public Serializable convert(Object data) {
				if (data instanceof byte[]) {
					final byte[] bytes = (byte[]) data;
					return jsonConverter.parse(new String(bytes), TemperatureAlertEntity.class);
				}
				return jsonConverter.parse(data).getBytes();
			}
		});

		converters.put(EventType.TEMPERATURE_VALUE, new Converter() {

			@Override
			public Serializable convert(Object data) {
				if (data instanceof byte[]) {
					final byte[] bytes = (byte[]) data;
					return jsonConverter.parse(new String(bytes), TemperatureEntity.class);
				}
				return jsonConverter.parse(data).getBytes();
			}
		});

		converters.put(EventType.TEMPERATURE_ALERT_VALUE, new Converter() {

			@Override
			public Serializable convert(Object data) {
				if (data instanceof byte[]) {
					final byte[] bytes = (byte[]) data;
					return jsonConverter.parse(new String(bytes), TemperatureAlertEntity.class);
				}
				return jsonConverter.parse(data).getBytes();
			}
		});

		converters.put(EventType.LED_VALUE, new Converter() {

			@Override
			public Serializable convert(Object data) {
				if (data instanceof byte[]) {
					final byte[] bytes = (byte[]) data;
					return Boolean.parseBoolean(new String(bytes));
				} else if (data instanceof Boolean) {
					return Boolean.toString((Boolean) data).getBytes();
				}
				return null;
			}
		});
	}

	/**
	 * Converts an incoming message.
	 *
	 * @param type the type of an event.
	 * @param message the message to be converted.
	 * @return a converted event's payload or null.
	 * @throws NullPointerException if either type of message is null.
	 */
	public Serializable convertIn(EventType type, byte[] message) {
		if (type == null) {
			throw new NullPointerException("Topic cannot be null.");
		}
		if (message == null) {
			throw new NullPointerException("Message cannot be null");
		}

		final Converter converter = converters.get(type);
		if (converter != null) {
			final Object result = converter.convert(message);
			if (!(result instanceof Serializable)) {
				throw new IllegalStateException("Converter's result is not a Serializable objec.");
			}
			return (Serializable) result;
		}
		return null;
	}

	/**
	 * Converts an outgoing message.
	 *
	 * @param type the type of an event.
	 * @param message the object to be converted.
	 * @return a converted object.
	 * @throws NullPointerException if either type of message is null.
	 */
	public byte[] convertOut(EventType type, Object message) {
		if (type == null) {
			throw new NullPointerException("Topic cannot be null.");
		}
		if (message == null) {
			throw new NullPointerException("Message cannot be null");
		}

		final Converter converter = converters.get(type);
		if (converter != null) {
			final Object result = converter.convert(message);
			if (!(result instanceof byte[])) {
				throw new IllegalStateException("Converter's result is not an array of bytes.");
			}

			return (byte[]) result;
		}
		return null;
	}

	/**
	 * The {@link Converter} is an internal type representing a message converter.
	 */
	private interface Converter {

		/**
		 * Converts the given object.
		 *
		 * @param data the object to be converted.
		 * @return a converted object or null if the given object is not supported by the converter.
		 */
		Object convert(Object data);
	}
}
