/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.common.home.api.topic;

/**
 * The {@link CommunicationTopic} provides topics on which certain information can be sent.
 *
 */
public enum CommunicationTopic {

    /**
     * Represents a base topic.
     */
    BASE(Topics.BASE),

	/**
	 * Represents a base of all hardware-related topics.
	 */
	HARDWARE_BASE(Topics.H_BASE),

	/**
	 * Represents a base topic for all incoming messages.
	 */
	HARDWARE_IN_BASE(Topics.H_IN_BASE),

	/**
	 * Represents a base topic for all outgoing messages.
	 */
	HARDWARE_OUT_BASE(Topics.H_OUT_BASE),

	/**
     * Represents a topic on which a current temperature value can be sent.
     */
    HARDWARE_OUT_TEMPERATURE_VALUE(Topics.H_OUT_TEMPERATURE_VALUE),

    /**
     * Represents a topic on which a current temperature alert value can be sent.
     */
    HARDWARE_OUT_TEMPERATURE_ALERT_VALUE(Topics.H_OUT_TEMPERATURE_ALERT_VALUE),

	/**
	 * Represents a topic on which a current led status can be sent.
	 */
	HARDWARE_OUT_LED_VALUE(Topics.H_OUT_LED_VALUE),

	/**
	 * Represents a topic on which a new temperature alert value can be sent.
	 */
	HARDWARE_IN_TEMPERATURE_ALERT(Topics.H_IN_TEMPERATURE_ALERT),

    /**
     * Represents a topic on which a new led value can be sent.
     */
    HARDWARE_IN_LED_UPDATE(Topics.H_IN_LED_UPDATE),

	/**
	 * Represents a topic on which a request for a current temperature value can be sent.
	 */
	HARDWARE_IN_REQUEST_TEMPERATURE(Topics.H_IN_REQUEST_TEMPERATURE),

    /**
     * Represents a topic on which a request for a current temperature alert value can be sent.
     */
    HARDWARE_IN_REQUEST_TEMPERATURE_ALERT(Topics.H_IN_REQUEST_TEMPERATURE_ALERT),

    /**
	 * Represents a topic on which a request for a current led value can be sent.
	 */
	HARDWARE_IN_REQUEST_LED(Topics.H_IN_REQUEST_LED);


	private String topic;

	private CommunicationTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return topic;
	}

	/**
	 * The {@link Topics} class is an internal type which contains all topics.
	 *
	 */
	private static final class Topics {
        private static String BASE = "@pzalejko/";

		private static String H_BASE = "@pzalejko/pi/";

		private static String H_IN_BASE = H_BASE + "in/";
		private static String H_OUT_BASE = H_BASE + "out/";

        private static String H_OUT_TEMPERATURE_ALERT_VALUE= H_OUT_BASE + "temperature/alert/status";
		private static String H_OUT_TEMPERATURE_VALUE = H_OUT_BASE + "temperature/status";
		private static String H_OUT_LED_VALUE = H_OUT_BASE + "led/status";

		private static String H_IN_TEMPERATURE_ALERT = H_IN_BASE + "temperature/alert/set";
        private static String H_IN_REQUEST_TEMPERATURE_ALERT = H_IN_BASE + "temperature/alert/getCurrent";
        private static String H_IN_REQUEST_TEMPERATURE = H_IN_BASE + "temperature/getCurrent";
		private static String H_IN_REQUEST_LED = H_IN_BASE + "led/getCurrent";
		private static String H_IN_LED_UPDATE = H_IN_BASE + "led/set";

		private Topics() {

		}
	}
}
