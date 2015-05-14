/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.api.led;

/**
 * The {@link Led} represents a LED connected to the device.
 *
 */
public interface Led {

	/**
	 * Turns on the led.
	 * 
	 * @throws IllegalStateException if the led is not reachable or the connection to the led has been already closed.
	 */
	void setOn();

	/**
	 * Turns off the led.
	 * 
	 * @throws IllegalStateException if the led is not reachable or the connection to the led has been already closed.
	 */
	void setOff();

	/**
	 * Determines whether the led is turned on.
	 * 
	 * @return true if the led is turned on.
	 * 
	 * @throws IllegalStateException if the led is not reachable or the connection to the led has been already closed.
	 */
	boolean isOn();

	/**
	 * Closes a connection to the LED.
	 * 
	 * @throws IllegalStateException if the led is not reachable.
	 */
	void close();
}
