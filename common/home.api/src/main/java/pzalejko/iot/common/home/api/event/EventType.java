/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.common.home.api.event;

/**
 * The {@link EventType} indicates what is the type of operation made or which must be performed.
 */
public enum EventType {

	/**
	 * Represents an event which contains a temperature value.
	 */
	TEMPERATURE_VALUE,

	/**
	 * Represents an event which contains a temperature alert value.
	 */
	TEMPERATURE_ALERT_VALUE,

	/**
	 * Represents an event which contains a led status(on/off).
	 */
	LED_VALUE,

	/**
	 * Represents an event which contains a new led status which should be set.
	 */
	UPDATE_LED,

	/**
	 * Represents an event which contains a new value which should be set for the temperature alert.
	 */
	UPDATE_TEMPERATURE_ALERT,

	/**
	 * Represents an event which indicates that a current led status should be obtained and published. As a result of handing this event the
	 * {@link #LED_VALUE} event should be set.
	 */
	PUBLISH_LED_STATUS_REQUEST,

	/**
	 * Represents an event which indicates that a current temperature value should be obtained and published. As a result of handling this
	 * event the {@link #TEMPERATURE_VALUE} event should be set.
	 */
	PUBLISH_TEMPERATURE_REQUEST,

	/**
	 * Represents an event which indicates that a current temperature alert value should be obtained and published. As a result of handling
	 * this event the {@link #TEMPERATURE_ALERT_VALUE} event should be set.
	 */
	PUBLISH_TEMPERATURE_ALERT_REQUEST;

}
