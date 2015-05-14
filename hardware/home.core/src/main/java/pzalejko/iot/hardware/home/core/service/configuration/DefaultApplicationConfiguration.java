/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.service.configuration;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Properties;

import pzalejko.iot.hardware.home.api.config.ApplicationConfiguration;

import com.google.common.base.Strings;

public class DefaultApplicationConfiguration implements ApplicationConfiguration {

	private static final String SHUTDOWN_KEY = "shutdown.after";

	private final int shutDownDelay;

	public DefaultApplicationConfiguration(Properties properties) {
		checkNotNull(properties);

		final String value = Strings.nullToEmpty(properties.getProperty(SHUTDOWN_KEY));
		checkArgument(!value.isEmpty());

		shutDownDelay = Integer.parseInt(value);
	}

	@Override
	public int getShutDownTime() {
		return shutDownDelay;
	}

}
