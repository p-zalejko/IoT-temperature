/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.api.temperature;

import java.util.Optional;

import pzalejko.iot.common.home.api.TemperatureEntity;

/**
 * The {@link TemperatureService} is responsible for obtaining a temperature value.
 *
 */
public interface TemperatureService {

	/**
	 * Gets the current temperature value.
	 * 
	 * @return a temperature or {@link Optional#empty()} if it cannot be obtained.
	 */
	Optional<TemperatureEntity> getTemperatureEntity();
}
