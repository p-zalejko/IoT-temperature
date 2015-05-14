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

import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import pzalejko.iot.hardware.home.api.led.Led;

import com.google.common.cache.CacheLoader;

class InternalLedCacheLoader extends CacheLoader<Integer, Led> {

	@Override
	public Led load(Integer key) throws IOException {
		return connectToLed(key);
	}

	private Led connectToLed(int id) throws IOException {
		final GPIOPin pin = (GPIOPin) DeviceManager.open(id);
		return new pzalejko.iot.hardware.home.core.service.led.DefaultLed(pin);
	}
}