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
 * 
 * The {@link LedConfiguration} provides a configuration which refers to LED.
 */
public interface LedConfiguration {

	/**
	 * Gets the ID of a led which should be managed.
	 * 
	 * @return the id of the LED to be managed.s
	 */
	int getLedId();

	/**
	 * Gets a temperature when the LED should be fired.
	 * 
	 * @return a temperature value.
	 */
	double getTemperatureAlert();
}
