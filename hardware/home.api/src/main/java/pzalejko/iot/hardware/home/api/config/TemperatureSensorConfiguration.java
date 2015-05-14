/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.api.config;

/**
 * The {@link TemperatureSensorConfiguration} provides a configuration which is needed to read a temperature value from the 1-wire sensor
 * connected to the Raspberry PI.
 *
 */
public interface TemperatureSensorConfiguration {

	/**
	 * Determines how often a temperature should be read.
	 * 
	 * @return a time in milliseconds.
	 */
	long getReadFrequency();

	/**
	 * Gets a path to the dictionary where PI dumps a temperatyre read from the 1-wire sensor.
	 *
	 * @return an absolute path to the dictionary.
	 */
	String getSourceDirectoryPath();

	/**
	 * Gets a name of the file which contains a temperature value read from the 1-wire sensor.
	 * @return a name of the file.
	 */
	String getSourceFileName();
}
