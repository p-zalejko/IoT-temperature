/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package pzalejko.iot.hardware.home.core.service.led;

import java.io.IOException;

import jdk.dio.gpio.GPIOPin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pzalejko.iot.hardware.home.api.led.Led;
import pzalejko.iot.hardware.home.core.util.LogMessages;

class DefaultLed implements Led {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultLed.class);

	private final GPIOPin pin;

	public DefaultLed(GPIOPin pin) {
		this.pin = pin;
	}

	@Override
	public void setOn() {
		toggleLed(true);
		LOG.debug(LogMessages.TURNED_ON_LED, pin);
	}

	@Override
	public void setOff() {
		toggleLed(false);
		LOG.debug(LogMessages.TURNED_OFF_LED, pin);
	}

	@Override
	public boolean isOn() {
		try {
			return pin.getValue();
		} catch (final IOException e) {
			throw new IllegalStateException(LogMessages.COULD_NOT_GET_A_LED_STATE, e);
		}
	}

	@Override
	public void close() {
		try {
			try {
				if (isOn()) {
					toggleLed(false);
				}
			} catch (final Exception e) {
				LOG.error(LogMessages.COULD_NOT_DISABLE_LED, e.getMessage(), e);
			}

			pin.close();
		} catch (final IOException e) {
			LOG.error(LogMessages.COULD_NOT_CLOSE_LED, e.getMessage(), e);
		}
	}

	private void toggleLed(boolean value) {
		if (!pin.isOpen()) {
			LOG.warn(LogMessages.LED_CONNECTION_LOST);
		}
		try {
			if (pin.getValue() != value) {
				pin.setValue(value);
			}
		} catch (final IOException e) {
			throw new IllegalStateException(LogMessages.COULD_NOT_UPDATE_LED, e);
		}
	}

}