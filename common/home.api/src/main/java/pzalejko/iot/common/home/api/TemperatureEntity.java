/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.common.home.api;

import java.io.Serializable;
import java.util.Date;

/**
 * The {@link TemperatureEntity} contains information about the temperature.
 */
public class TemperatureEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private double temperature;
	private long date;

	/**
	 * Creates a {@link TemperatureEntity}
	 */
	public TemperatureEntity() {

	}

	/**
	 * Creates a {@link TemperatureEntity} initialized with the given parameters.
	 * 
	 * @param temperature the temperature value.
	 * @param date the date when it was collected.
	 * 
	 * @throws IllegalArgumentException if the given temperature is not a valid double number.
	 */
	public TemperatureEntity(double temperature, long date) {
		if (Double.isNaN(temperature)) {
			throw new IllegalArgumentException("A given temperature is not a valid double value.");
		}
		this.temperature = temperature;
		this.date = date;
	}

	/**
	 * Gets the date when the temperature value was collected.
	 * 
	 * @return the date.
	 */
	public Date getDate() {
		return new Date(date);
	}

	/**
	 * 
	 * @return the temperature value.
	 */
	public double getTemperature() {
		return temperature;
	}

	@Override
	public String toString() {
		return "Temperature: " + temperature;
	}

}
