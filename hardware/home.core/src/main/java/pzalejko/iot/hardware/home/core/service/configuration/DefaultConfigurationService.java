/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.service.configuration;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pzalejko.iot.hardware.home.api.config.*;
import pzalejko.iot.hardware.home.core.util.LogMessages;

@Component
public class DefaultConfigurationService implements ConfigurationService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultConfigurationService.class);

	private static final String CONFIGURATION_DIRECTORY_KEY = "home.core.settings";
	private static final String APP_CONFIG_FILE_NAME = "application.properties";
	private static final String LED_CONFIG_FILE_NAME = "led.properties";
	private static final String MQTT_CONFIG_FILE_NAME = "mqtt.properties";
	private static final String TEMPERATURE_CONFIG_FILE_NAME = "temperature.properties";

	private File confDirectory;

	@PostConstruct
	public void init() {
		final String property = System.getProperty(CONFIGURATION_DIRECTORY_KEY);
		if (property == null) {
			final String msg = MessageFormat.format(LogMessages.CONFIGURATION_DIRECTORY_HAS_NOT_BE_SPECIFIED, CONFIGURATION_DIRECTORY_KEY);
			throw new IllegalStateException(msg);
		}

		final File file = new File(property);
		if (file.isDirectory()) {
			this.confDirectory = file;
			LOG.debug(LogMessages.FOUND_CONFIGURATION_DIRECTORY, file.getAbsoluteFile());
		} else {
			final String msg = MessageFormat.format(LogMessages.PROPERTY_DOES_NOT_POINT_TO_ANY_DIRECTORY, CONFIGURATION_DIRECTORY_KEY);
			throw new IllegalStateException(msg);
		}
	}

	@Override
	public ApplicationConfiguration getApplicationConfiguration() {
		checkState(confDirectory != null, MessageFormat.format(LogMessages.APPLICATION_NOT_CONFIGURED, CONFIGURATION_DIRECTORY_KEY));
		return new DefaultApplicationConfiguration(getProperties(APP_CONFIG_FILE_NAME));
	}

	@Override
	public LedConfiguration getLedSettings() {
		checkState(confDirectory != null, MessageFormat.format(LogMessages.APPLICATION_NOT_CONFIGURED, CONFIGURATION_DIRECTORY_KEY));
		return new DefaultLedConfiguration(getProperties(LED_CONFIG_FILE_NAME));
	}

	@Override
	public DataSenderConfiguration getMqttConfiguration() {
		checkState(confDirectory != null, MessageFormat.format(LogMessages.APPLICATION_NOT_CONFIGURED, CONFIGURATION_DIRECTORY_KEY));
		return new DefaultSenderSettings(getProperties(MQTT_CONFIG_FILE_NAME));
	}

	@Override
	public TemperatureSensorConfiguration getTemperatureConfiguration() {
		checkState(confDirectory != null, MessageFormat.format(LogMessages.APPLICATION_NOT_CONFIGURED, CONFIGURATION_DIRECTORY_KEY));
		return new DefaultTemperatureConfiguration(getProperties(TEMPERATURE_CONFIG_FILE_NAME));
	}

	private Properties getProperties(String key) {
		final File file = new File(confDirectory, key);
		checkState(file.exists(), MessageFormat.format(LogMessages.PROPERTY_FILE_IS_MISSING, key));
		return loadProperties(file);
	}

	private Properties loadProperties(File file) {
		final Properties properties = new Properties();
		try (FileInputStream inStream = new FileInputStream(file)) {
			properties.load(inStream);
		} catch (final IOException e) {
			LOG.error(LogMessages.COULD_NOT_LOAD_A_PROPERTY_FILE, e.getMessage(), e);
			throw new IllegalStateException(LogMessages.COULD_NOT_LOAD_CONFIGURATION);
		}
		return properties;
	}

}
