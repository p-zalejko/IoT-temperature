/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package pzalejko.iot.client.web.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	private static final String TOPIC_PREFIX = "/topic";

	public static final String APP_GET_TOPIC = "/iot";
	public static final String CONNECTION_PUSH_TOPIC = TOPIC_PREFIX + "/connectionStatus";
	public static final String TEMPERATURE_PUSH_TOPIC = TOPIC_PREFIX + "/temperature";
	public static final String TEMPERATURE_ALERT_PUSH_TOPIC = TOPIC_PREFIX + "/temperatureAlert";

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker(TOPIC_PREFIX);
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(APP_GET_TOPIC).withSockJS();
	}
}
