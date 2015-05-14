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
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pzalejko.iot.common.home.api.TemperatureAlertEntity;
import pzalejko.iot.common.home.api.TemperatureEntity;
import pzalejko.iot.common.home.api.event.Event;
import pzalejko.iot.common.home.api.event.EventBus;
import pzalejko.iot.common.home.api.event.EventListener;
import pzalejko.iot.common.home.api.event.EventType;
import pzalejko.iot.hardware.home.api.config.ConfigurationService;
import pzalejko.iot.hardware.home.api.config.LedConfiguration;
import pzalejko.iot.hardware.home.api.led.LedService;
import pzalejko.iot.hardware.home.core.util.LogMessages;

/**
 * The {@link LedAlertTask} is responsible for switching on the LED when a temperature value reaches the alert level.
 */
@Component
public class LedAlertTask extends AbstractLedTask implements EventListener {

	private static final Logger LOG = LoggerFactory.getLogger(LedAlertTask.class);

	private double maxTemperature;
	private final ConfigurationService configurationService;

	@Inject
	public LedAlertTask(EventBus eventBus, ConfigurationService configurationService, LedService ledService) {
		super(eventBus, ledService);
		this.configurationService = configurationService;
	}

	@Override
	@PostConstruct
	public void init() {
		super.init();

		final LedConfiguration ledSettings = configurationService.getLedSettings();
		maxTemperature = ledSettings.getTemperatureAlert();
	}

	@Override
	protected int getLedID() {
		final LedConfiguration ledSettings = configurationService.getLedSettings();
		return ledSettings.getLedId();
	}

	@Override
	public void run() {
		LOG.debug(LogMessages.STARTING_TASK);
		eventBus.register(this);
	}

	@Override
	@PreDestroy
	public void destroy() {
		eventBus.unregister(this);
		super.destroy();
	}

	@Override
	public void handle(Event event) {
		if (isLedToUpdateEvent(event)) {
			updateLed(event);
		} else if (isAlertTemperatureToUpdateEvent(event)) {
			updateAlertTemperature(event);
		} else if (isGetAlertTemperatureEvent(event)) {
			publishCurrentAlertValue();
		}
	}

	/**
	 * Updates a LED's state according to the payload provided by the given event. An expected payload is an instance of the
	 * {@link TemperatureEntity}.
	 * 
	 * @param event the event.
	 */
	private void updateLed(Event event) {
		LOG.debug(LogMessages.UPDATING_LED_FOR, event);

		final Object payload = event.getPayload();
		if (payload instanceof TemperatureEntity) {
			updateLed(((TemperatureEntity) payload).getTemperature() >= maxTemperature);
		} else {
			LOG.error(LogMessages.PAYLOAD_NOT_SUPPORTED, payload);
		}
	}

	/**
	 * Updates an alert value according to the payload provided by the given event. An expected payload is an instance of the
	 * {@link TemperatureAlertEntity}.
	 * 
	 * @param event the event.
	 */
	private void updateAlertTemperature(Event event) {
		final Object payload = event.getPayload();
		if (payload instanceof TemperatureAlertEntity) {
			try {
				maxTemperature = ((TemperatureAlertEntity) payload).getAlert();
				LOG.info(LogMessages.UPDATED_AN_ALERT_TEMPERATURE_TO, maxTemperature);
				publishCurrentAlertValue();
			} catch (final NumberFormatException e) {
				LOG.error(LogMessages.COULD_NOT_UPDATE_TEMPERATURE_ALERT, e.getMessage(), e);
			}
		} else {
			LOG.error(LogMessages.PAYLOAD_NOT_SUPPORTED, payload);
		}
	}


	private void publishCurrentAlertValue() {
		final TemperatureAlertEntity alertEntity = new TemperatureAlertEntity(maxTemperature);
		eventBus.postLocal(alertEntity, EventType.TEMPERATURE_ALERT_VALUE);
	}

	private boolean isLedToUpdateEvent(Event event) {
		return event.getType() == EventType.TEMPERATURE_VALUE;
	}

	private boolean isAlertTemperatureToUpdateEvent(Event event) {
		return event.getType() == EventType.UPDATE_TEMPERATURE_ALERT;
	}

	private boolean isGetAlertTemperatureEvent(Event event) {
		return event.getType() == EventType.PUBLISH_TEMPERATURE_ALERT_REQUEST;
	}
}
