/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.task;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import pzalejko.iot.common.home.api.TemperatureEntity;
import pzalejko.iot.common.home.api.event.Event;
import pzalejko.iot.common.home.api.event.EventBus;
import pzalejko.iot.common.home.api.event.EventListener;
import pzalejko.iot.common.home.api.event.EventType;
import pzalejko.iot.hardware.home.api.config.ConfigurationService;
import pzalejko.iot.hardware.home.api.config.TemperatureSensorConfiguration;
import pzalejko.iot.hardware.home.api.temperature.TemperatureService;

/**
 * The {@link TemperatureTask} is responsible for reading a temperature value from the sensor.
 */
@Component
public class TemperatureTask implements Runnable, EventListener {

	private final TemperatureService service;
	private final EventBus eventBus;
	private final ConfigurationService configurationService;

	private long readFrequency;

	@Inject
	public TemperatureTask(TemperatureService service, EventBus eventBus, ConfigurationService configurationService) {
		this.service = service;
		this.eventBus = eventBus;
		this.configurationService = configurationService;
	}

	@PostConstruct
	public void init() {
		final TemperatureSensorConfiguration tempConfiguration = configurationService.getTemperatureConfiguration();
		readFrequency = tempConfiguration.getReadFrequency();
	}

	/**
	 * Gets a time value determining how often a temperature value should be read.
	 * 
	 * @return a time value in milliseconds.
	 */
	public long getReadFrequency() {
		return readFrequency;
	}

	@Override
	public void handle(Event event) {
		if (event.getType() == EventType.PUBLISH_TEMPERATURE_REQUEST) {
			run();
		}
	}

	@Override
	public void run() {
		final Optional<TemperatureEntity> temperatureEntity = service.getTemperatureEntity();
		temperatureEntity.ifPresent(t -> eventBus.postLocal(t, EventType.TEMPERATURE_VALUE));
	}
}
