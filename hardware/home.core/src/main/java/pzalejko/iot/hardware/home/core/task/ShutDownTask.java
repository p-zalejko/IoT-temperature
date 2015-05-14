/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.task;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pzalejko.iot.hardware.home.api.config.ApplicationConfiguration;
import pzalejko.iot.hardware.home.api.config.ConfigurationService;
import pzalejko.iot.hardware.home.core.util.LogMessages;

/**
 * The {@link ShutDownTask} is responsible for managing an application lifecycle. It determines when the application should be closed.
 */
@Component
public class ShutDownTask implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ShutDownTask.class);

	private ConfigurationService configurationService;
	private int shutDownDelay;

	@Inject
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@PostConstruct
	public void init() {
		final ApplicationConfiguration configuration = configurationService.getApplicationConfiguration();
		shutDownDelay = configuration.getShutDownTime();
	}

	@Override
	public void run() {
		LOG.debug(LogMessages.SHUT_DOWN_DELAY_VALUE, shutDownDelay);

		final Calendar c = Calendar.getInstance();
		final Date dateNow = new Date();
		c.setTime(dateNow);
		c.add(Calendar.MINUTE, shutDownDelay);

		LOG.debug(LogMessages.STARTED_SHUT_DOWN_TASK, c.getTime());
		try {
			Thread.sleep(c.getTime().getTime() - dateNow.getTime());
		} catch (final InterruptedException e) {
			LOG.error(LogMessages.ERROR_HAS_OCCURRED, e.getMessage(), e);
		}
	}

}
