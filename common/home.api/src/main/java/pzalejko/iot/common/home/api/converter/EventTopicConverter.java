/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.common.home.api.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import pzalejko.iot.common.home.api.event.EventType;
import pzalejko.iot.common.home.api.topic.CommunicationTopic;

/**
 * The {@link EventTopicConverter} is responsible for matching event types to corresponding event topics.
 */
public class EventTopicConverter {

	private final Map<String, EventType> mapping;

	/**
	 * Creates and initializes the {@link EventTopicConverter}.
	 */
	public EventTopicConverter() {
		mapping = new HashMap<String, EventType>();
		mapping.put(CommunicationTopic.HARDWARE_OUT_LED_VALUE.toString(), EventType.LED_VALUE);
		mapping.put(CommunicationTopic.HARDWARE_OUT_TEMPERATURE_VALUE.toString(), EventType.TEMPERATURE_VALUE);
		mapping.put(CommunicationTopic.HARDWARE_IN_LED_UPDATE.toString(), EventType.UPDATE_LED);
		mapping.put(CommunicationTopic.HARDWARE_IN_TEMPERATURE_ALERT.toString(), EventType.UPDATE_TEMPERATURE_ALERT);
		mapping.put(CommunicationTopic.HARDWARE_IN_REQUEST_TEMPERATURE.toString(), EventType.PUBLISH_TEMPERATURE_REQUEST);
		mapping.put(CommunicationTopic.HARDWARE_IN_REQUEST_LED.toString(), EventType.PUBLISH_LED_STATUS_REQUEST);
		mapping.put(CommunicationTopic.HARDWARE_IN_REQUEST_TEMPERATURE_ALERT.toString(), EventType.PUBLISH_TEMPERATURE_ALERT_REQUEST);
		mapping.put(CommunicationTopic.HARDWARE_OUT_TEMPERATURE_ALERT_VALUE.toString(), EventType.TEMPERATURE_ALERT_VALUE);
	}

	/**
	 * Gets a topic associated with the given event type.
	 *
	 * @param type the event type.
	 * @return a corresponding topic or null.
	 * @throws NullPointerException if the given type is null.
	 */
	public String getTopic(final EventType type) {
		if (type == null) {
			throw new NullPointerException("EventType cannot be null.");
		}

		final Set<String> keys = mapping.keySet();
		for (String key : keys) {
			if (mapping.get(key) == type) {
				return key;
			}
		}
		return null;
	}

	/**
	 * Gets an event type associated with the given topic.
	 *
	 * @param topic the topic.
	 * @return a corresponding event type or null.
	 * @throws NullPointerException if the given topic is null.
	 */
	public EventType getEventType(String topic) {
		if (topic == null) {
			throw new NullPointerException("Topic cannot be null.");
		}
		return mapping.get(topic);
	}
}
