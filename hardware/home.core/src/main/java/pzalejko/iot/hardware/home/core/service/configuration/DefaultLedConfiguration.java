/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.service.configuration;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Properties;

import pzalejko.iot.hardware.home.api.config.LedConfiguration;

public class DefaultLedConfiguration implements LedConfiguration {

	private static final String LED_ID_KEY = "led.id";
	private static final String LED_ALERT_MAX_KEY = "led.alert.temperature.max";

	private final int ledId;
	private final double maxTemperature;

	public DefaultLedConfiguration(Properties properties) {
		checkNotNull(properties);

		ledId = Integer.parseInt(properties.getProperty(LED_ID_KEY));
		maxTemperature = Double.parseDouble(properties.getProperty(LED_ALERT_MAX_KEY));

		checkState(ledId > 0);
		checkState(!Double.isNaN(maxTemperature));
	}

	@Override
	public int getLedId() {
		return ledId;
	}

	@Override
	public double getTemperatureAlert() {
		return maxTemperature;
	}

}
