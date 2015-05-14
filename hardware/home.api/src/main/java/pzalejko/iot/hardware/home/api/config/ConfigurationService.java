/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.api.config;

import java.util.Properties;

/**
 * The {@link ConfigurationService} is responsible for loading and providing configuration for all main components in the application.
 */
public interface ConfigurationService {

	/**
	 * Gets a configuration providing general settings which define the application's lifecycle.
	 * 
	 * @return a {@link Properties} object that contains a configuration.
	 * 
	 * @throws IllegalStateException if the configuration has not been provided or is not reachable.
	 */
	ApplicationConfiguration getApplicationConfiguration();

	/**
	 * Gets a configuration that defines access to LEDs.
	 * 
	 * @return a {@link Properties} object that contains a configuration.
	 * 
	 * @throws IllegalStateException if the configuration has not been provided or is not reachable.
	 */
	LedConfiguration getLedSettings();

	/**
	 * Gets a configuration which defines how to connect to a <i>data</i> broker.
	 * 
	 * @return a {@link Properties} object that contains a configuration.
	 * 
	 * @throws IllegalStateException if the configuration has not been provided or is not reachable.
	 */
	DataSenderConfiguration getMqttConfiguration();

	/**
	 * Gets a configuration which defines how to obtain information about the current temperature value.
	 * 
	 * @return a {@link Properties} object that contains a configuration.
	 * 
	 * @throws IllegalStateException if the configuration has not been provided or is not reachable.
	 */
	TemperatureSensorConfiguration getTemperatureConfiguration();
}
