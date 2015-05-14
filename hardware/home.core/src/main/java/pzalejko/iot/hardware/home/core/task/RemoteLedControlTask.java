/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.task;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pzalejko.iot.common.home.api.event.Event;
import pzalejko.iot.common.home.api.event.EventBus;
import pzalejko.iot.common.home.api.event.EventListener;
import pzalejko.iot.common.home.api.event.EventType;
import pzalejko.iot.hardware.home.api.config.ConfigurationService;
import pzalejko.iot.hardware.home.api.config.LedConfiguration;
import pzalejko.iot.hardware.home.api.led.LedService;
import pzalejko.iot.hardware.home.core.util.LogMessages;

/**
 * The {@link RemoteLedControlTask} is responsible for handling events which come from remote applications and refer to the LED and its
 * state. For instance, it is responsible for publishing a current led's state.
 */
@Component
public class RemoteLedControlTask extends AbstractLedTask implements EventListener {

	private static final Logger LOG = LoggerFactory.getLogger(RemoteLedControlTask.class);

	private final ConfigurationService configurationService;

	@Inject
	public RemoteLedControlTask(EventBus eventBus, ConfigurationService configurationService, LedService ledService) {
		super(eventBus, ledService);
		this.configurationService = configurationService;
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
		if (isPublishLedEvent(event)) {
			eventBus.postLocal(isLedOn(), EventType.LED_VALUE);
		} else if (isLedToUpdateEvent(event)) {
			updateLed(event);
		}
	}

	@Override
	protected int getLedID() {
		final LedConfiguration ledSettings = configurationService.getLedSettings();
		return ledSettings.getLedId();
	}

	private void updateLed(Event event) {
		LOG.debug(LogMessages.UPDATING_LED_FOR, event);

		final Object payload = event.getPayload();
		if (payload instanceof Boolean) {
			updateLed((Boolean) payload);
		} else {
			LOG.error(LogMessages.PAYLOAD_NOT_SUPPORTED, payload);
		}
	}

	private boolean isPublishLedEvent(Event event) {
		return event.getType() == EventType.PUBLISH_LED_STATUS_REQUEST;
	}

	private boolean isLedToUpdateEvent(Event event) {
		return event.getType() == EventType.UPDATE_LED;
	}

}
