/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.task;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import pzalejko.iot.common.home.api.event.EventBus;
import pzalejko.iot.common.home.api.event.EventType;
import pzalejko.iot.hardware.home.api.led.Led;
import pzalejko.iot.hardware.home.api.led.LedService;

/**
 * The AbstractLedTask provides a set of basic methods which allow for dealing with LEDs.
 */
public abstract class AbstractLedTask implements Runnable {

	protected final EventBus eventBus;
	protected final LedService ledService;

	private Led led;

	public AbstractLedTask(EventBus eventBus, LedService ledService) {
		this.eventBus = eventBus;
		this.ledService = ledService;
	}

	@PostConstruct
	public void init() {
		led = ledService.getLedById(getLedID());
	}

	@PreDestroy
	public void destroy() {
		led.close();
	}

	/**
	 * Gets the ID of a led to which a connection should be established to.
	 * 
	 * @return the ID of a LED.
	 */
	protected abstract int getLedID();

	/**
	 * Determines whether the LED is lighting or not.
	 * 
	 * @return true if the led is on.
	 */
	protected boolean isLedOn() {
		return led.isOn();
	}

	/**
	 * Updates the LED state.
	 * 
	 * @param value true if the LED should be turned on, false if turned off.
	 */
	protected void updateLed(boolean value) {
		if (value) {
			led.setOn();
		} else {
			led.setOff();
		}

		eventBus.postLocal(led.isOn(), EventType.LED_VALUE);
	}
}
