/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package pzalejko.iot.common.home.api.event;

import java.io.Serializable;

public class Event implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Serializable payload;
	private final EventType type;
	private final EventSource source;

	/**
	 * Creates a new {@link Event}.
	 * 
	 * @param payload the event's payload.
	 * @param source the source of the event.
	 * @param type the type of the event.
	 * 
	 * @throws NullPointerException if a parameter is null.
	 */
	public Event(Serializable payload, EventSource source, EventType type) {
		if (payload == null) {
			throw new NullPointerException("Payload cannot be null.");
		}
		if (source == null) {
			throw new NullPointerException("Source cannot be null.");
		}
		if (type == null) {
			throw new NullPointerException("Type cannot be null.");
		}

		this.payload = payload;
		this.source = source;
		this.type = type;
	}

	/**
	 * Gets the payload of the event.
	 * 
	 * @return the payload object.
	 */
	public Serializable getPayload() {
		return payload;
	}

	/**
	 * 
	 * @return the event type.
	 */
	public EventType getType() {
		return type;
	}

	/**
	 * Gets the source of the event.
	 * 
	 * @return
	 */
	public EventSource getSource() {
		return source;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("type: ");
		builder.append(type);
		builder.append(" source: ");
		builder.append(source);
		builder.append(" payload: ");
		builder.append(payload);

		return builder.toString();
	}
}