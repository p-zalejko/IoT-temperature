/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import pzalejko.iot.common.home.api.converter.EventTopicConverter;
import pzalejko.iot.common.home.api.converter.JsonConverter;
import pzalejko.iot.common.home.api.converter.MessageToEventConverter;

/**
 * The {@link ApplicationConfigurator} is responsible for adding to the application context objects which cannot be managed by the Spring
 * Framework out of the box. In most cases these objects come from third-party libraries.
 */
@Configuration
@ComponentScan(value = { "pzalejko.iot.hardware.home.core" })
public class ApplicationConfigurator {

	@Bean
	public JsonConverter getJsonConverter() {
		return new JsonConverter();
	}

	@Bean
	public EventTopicConverter getEventTopicMapper() {
		return new EventTopicConverter();
	}

	@Bean
	public MessageToEventConverter getMessageToEventConverter() {
		return new MessageToEventConverter();
	}

}
