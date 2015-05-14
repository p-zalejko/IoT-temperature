/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.util;

public final class LogMessages {

	private LogMessages() {

	}

	public static final String CONNECTED_MQTT = "Connected to \'{}\'.";
	public static final String CONNECTING_MQTT = "Connecting to \'{}\' as \'{}\'.";
	public static final String DISCONNECTED_FROM = "Disconnected from \'{}\', as=\'{}\'.";
	public static final String DISCONNECTING_FROM = "Disconnecting from \'{}\', as=\'{}\'.";
	public static final String SENT_MQTT_MSQ = "Sent \'{}\' to \'{}\'.";
	public static final String COULD_NOT_COLLECT_TEMPERATURE_MISSING_SOURCE = "Could not collect a temperature. The \'{}\' does not exist.";
	public static final String COULD_NOT_COLLECT_A_TEMPERATURE = "Could not collect a temperature due to \'{}\'.";
	public static final String READ_TEMPERATURE = "Read a temperature: \'{}\'.";
	public static final String CONFIGURATION_DIRECTORY_HAS_NOT_BE_SPECIFIED = "Configuration directory has not be specified. The {0} property is missing.";
	public static final String FOUND_CONFIGURATION_DIRECTORY = "Found a configuration directory in \'{}\'.";
	public static final String PROPERTY_DOES_NOT_POINT_TO_ANY_DIRECTORY = "The {0} property does not point to any directory.";
	public static final String APPLICATION_NOT_CONFIGURED = "Application has not been configured properly. The {0} property is missing.";
	public static final String PROPERTY_FILE_IS_MISSING = "The {0} property file is missing.";
	public static final String COULD_NOT_LOAD_CONFIGURATION = "Could not load configuration.";
	public static final String COULD_NOT_LOAD_A_PROPERTY_FILE = "Could not load a property file due to \'{}\'.";
	public static final String STARTING_APPLICATION = "Starting the application.";
	public static final String TEMP_SERVICE_MISSING_PROPERTY = "Temperature service cannot be configured. {0} property is missing.";
	public static final String COULD_NOT_SEND_MESSAGE = "Could not send a message due to \'{}\'.";
	public static final String SHUT_DOWN_TASK_EXECUTOR = "Shutted down the task executor.";
	public static final String SCHEDULED_REPEATABLE_TASK = "Scheduled a repeatable task. It will be repeated in {}ms.";
	public static final String SHUTTING_DOWN_TASK_EXECUTOR = "Shutting down the task executor.";
	public static final String ERROR_HAS_OCCURRED = "An error has occurred: \'{}\'.";
	public static final String STARTED_SHUT_DOWN_TASK = "Started the ShutDownTask. The application will be closed at {}.";
	public static final String MESSAGE_COULD_NOT_BE_SENT = "Message could not be sent.";
	public static final String MQTT_DELIVERY_COMPLETE = "MQTT delivery complete: {}.";
	public static final String MQTT_CONNECTION_LOST = "MQTT connection lost due to {}.";
	public static final String COULD_NOT_UPDATE_LED = "Could not update a led's state.";
	public static final String COULD_NOT_CLOSE_LED = "Could not close a connection to the led due to {}.";
	public static final String COULD_NOT_GET_A_LED_STATE = "Could not get a led's state.";
	public static final String TURNED_OFF_LED = "Turned off \'{}\' led.";
	public static final String TURNED_ON_LED = "Turned on \'{}\' led.";
	public static final String CONNECTING_TO_LED = "Connecting to LED: {}.";
	public static final String COULD_NOT_CONNECT_TO_LED = "Could not connect to a LED.";
	public static final String RECEIVED_MESSAGE = "Received a message: topic=\'{}\',  message=\'{}\'.";
	public static final String PAYLOAD_NOT_SUPPORTED = "Payload is not supported: {}.";
	public static final String UPDATING_LED_FOR = "Updating a LED for {}.";
	public static final String COULD_NOT_UPDATE_TEMPERATURE_ALERT = "Could not update temperature alert due to {}.";
	public static final String STARTING_TASK = "Starting a task.";
	public static final String SHUT_DOWN_DELAY_VALUE = "Shut down delay value: {}.";
	public static final String ERROR_OCCURRED_WHILE_EXECUTING_TASK = "An error has occurred while executing a task: {}.";
	public static final String COULD_NOT_DISABLE_LED = "Could not disable a LED due to {}.";
	public static final String LED_CONNECTION_LOST = "Cannot update a led. LED connection is closed.";
	public static final String UPDATED_AN_ALERT_TEMPERATURE_TO = "Updated an alert temperature to {}.";
	public static final String ERROR_WHILE_HANDLING_EVENT = "An error has occurred while handing an event: {}.";
}
