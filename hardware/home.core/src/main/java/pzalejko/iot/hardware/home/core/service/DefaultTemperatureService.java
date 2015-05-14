/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import pzalejko.iot.common.home.api.TemperatureEntity;
import pzalejko.iot.hardware.home.api.config.ConfigurationService;
import pzalejko.iot.hardware.home.api.config.TemperatureSensorConfiguration;
import pzalejko.iot.hardware.home.api.temperature.TemperatureService;
import pzalejko.iot.hardware.home.core.util.LogMessages;

import com.google.common.base.Function;

@Component
public class DefaultTemperatureService implements TemperatureService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultTemperatureService.class);

	private static final String TEMP_MARKER = "t=";

	private static final Function<String, Double> TEMPERATURE_VALUE_EXTRACTOR = input -> {
		final String[] strings = input.split(TEMP_MARKER);
		if (strings.length > 1) {
			final String tmp = strings[1];
			return Double.parseDouble(tmp) / 1000;
		}
		return Double.NaN;
	};

	private final ConfigurationService configurationService;
	private String sourceFilePath;
	private String fileName;

	@Inject
	public DefaultTemperatureService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@PostConstruct
	public void init() {
		final TemperatureSensorConfiguration configuration = configurationService.getTemperatureConfiguration();
		sourceFilePath = configuration.getSourceDirectoryPath();
		fileName = configuration.getSourceFileName();
	}

	@Override
	public Optional<TemperatureEntity> getTemperatureEntity() {
		final double temperature = loadTemperatureFromFile(sourceFilePath, fileName);
		if (!Double.isNaN(temperature)) {
			LOG.debug(LogMessages.READ_TEMPERATURE, temperature);
			return Optional.of(new TemperatureEntity(temperature, System.currentTimeMillis()));
		}

		return Optional.empty();
	}

	private double loadTemperatureFromFile(String directory, String fileName) {
		final Path path = Paths.get(directory, fileName);
		if (path.toFile().exists()) {
			try (Stream<String> lines = Files.lines(path)) {
				// @formatter:off
				return lines.filter(s -> s.contains(TEMP_MARKER))
						.map(TEMPERATURE_VALUE_EXTRACTOR::apply)
						.findFirst()
						.orElse(Double.NaN);
				// @formatter:on
			} catch (final IOException | NumberFormatException e) {
				LOG.error(LogMessages.COULD_NOT_COLLECT_A_TEMPERATURE, e.getMessage(), e);
			}
		} else {
			LOG.warn(LogMessages.COULD_NOT_COLLECT_TEMPERATURE_MISSING_SOURCE, path.toAbsolutePath());
		}

		return Double.NaN;
	}
}
