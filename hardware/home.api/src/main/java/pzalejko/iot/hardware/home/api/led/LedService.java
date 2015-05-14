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
 * The {@link LedService} is responsible for finding and connecting to a LED.
 *
 */
public interface LedService {

	/**
	 * Gets the led by its ID.
	 * 
	 * @param id the ID of the led.
	 * @return a matching led.
	 * 
	 * @throws IllegalArgumentException if the given ID is less than 1.
	 * @throws IllegalStateException if the given ID does not match any led.
	 */
	Led getLedById(int id);
}
