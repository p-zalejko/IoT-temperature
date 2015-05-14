/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package pzalejko.iot.client.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;

import pzalejko.iot.client.web.mqtt.DefaultMqttCallback;
import pzalejko.iot.common.home.api.converter.EventTopicConverter;
import pzalejko.iot.common.home.api.converter.MessageToEventConverter;

//same as @Configuration @EnableAutoConfiguration @ComponentScan
@SpringBootApplication
@PropertySource("classpath:mqtt.properties")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public DefaultMqttPahoClientFactory clientFactory() {
		return new DefaultMqttPahoClientFactory();
	}

	@Bean
	public EventTopicConverter getEventTopicConverter() {
		return new EventTopicConverter();
	}

	@Bean
	public MessageToEventConverter getMessageToEventConverter() {
		return new MessageToEventConverter();
	}

	@Bean
	public DefaultMqttCallback getDefaultMqttCallback() {
		return new DefaultMqttCallback();
	}
}