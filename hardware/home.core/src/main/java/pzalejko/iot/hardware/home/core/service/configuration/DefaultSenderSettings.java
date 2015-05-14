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

import pzalejko.iot.hardware.home.api.config.DataSenderConfiguration;

import com.google.common.base.Strings;

public class DefaultSenderSettings implements DataSenderConfiguration {

	private static final String SENDER_CLIENT_ID_KEY = "sender.client";
	private static final String SENDER_HOST_KEY = "sender.host";

	private final String senderHost;
	private final String senderClientId;

	public DefaultSenderSettings(Properties properties) {
		checkNotNull(properties);
		senderHost = Strings.nullToEmpty(properties.getProperty(SENDER_HOST_KEY));
		senderClientId = Strings.nullToEmpty(properties.getProperty(SENDER_CLIENT_ID_KEY));

		checkArgument(!senderHost.isEmpty());
		checkArgument(!senderClientId.isEmpty());
	}

	@Override
	public String getHost() {
		return senderHost;
	}

	@Override
	public String getClient() {
		return senderClientId;
	}

}
