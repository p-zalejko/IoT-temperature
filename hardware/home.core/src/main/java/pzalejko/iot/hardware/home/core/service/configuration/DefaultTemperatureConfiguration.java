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

import java.text.MessageFormat;
import java.util.Properties;

import pzalejko.iot.hardware.home.api.config.TemperatureSensorConfiguration;
import pzalejko.iot.hardware.home.core.util.LogMessages;

import com.google.common.base.Strings;

public class DefaultTemperatureConfiguration implements TemperatureSensorConfiguration {

	private static final String READ_FREQ_KEY = "read.frequency";
	private static final String SOURCE_FILE_NAME_KEY = "source.file.name";
	private static final String SOURCE_DICTIONARY_KEY = "source.file.dictionary";

	private final Long readFrequency;
	private final String sourceFilePath;
	private final String fileName;

	public DefaultTemperatureConfiguration(Properties properties) {
		checkNotNull(properties);
		readFrequency = Long.parseLong(Strings.nullToEmpty(properties.getProperty(READ_FREQ_KEY)));
		sourceFilePath = Strings.nullToEmpty(properties.getProperty(SOURCE_DICTIONARY_KEY));
		fileName = Strings.nullToEmpty(properties.getProperty(SOURCE_FILE_NAME_KEY));

		checkArgument(readFrequency > 0);
		checkArgument(!sourceFilePath.isEmpty(), MessageFormat.format(LogMessages.TEMP_SERVICE_MISSING_PROPERTY, SOURCE_DICTIONARY_KEY));
		checkArgument(!fileName.isEmpty(), MessageFormat.format(LogMessages.TEMP_SERVICE_MISSING_PROPERTY, SOURCE_FILE_NAME_KEY));
	}

	@Override
	public long getReadFrequency() {
		return readFrequency;
	}

	@Override
	public String getSourceDirectoryPath() {
		return sourceFilePath;
	}

	@Override
	public String getSourceFileName() {
		return fileName;
	}
}
