/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package pzalejko.iot.client.web.mqtt;

import java.io.Serializable;

import javax.annotation.PreDestroy;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.stereotype.Component;

import pzalejko.iot.common.home.api.converter.EventTopicConverter;
import pzalejko.iot.common.home.api.converter.MessageToEventConverter;
import pzalejko.iot.common.home.api.event.Event;
import pzalejko.iot.common.home.api.event.EventSource;
import pzalejko.iot.common.home.api.event.EventType;
import pzalejko.iot.common.home.api.topic.CommunicationTopic;

@Component
public class MqttHandler {

	@Autowired
	private DefaultMqttPahoClientFactory defaultMqttPahoClientFactory;
	@Autowired
	private DefaultMqttCallback mqttCallback;
	@Autowired
	private EventTopicConverter eventConverter;
	@Autowired
	private MessageToEventConverter messageConverter;

	private MqttClient mqttClient;

	@PreDestroy
	public void destroy() throws MqttException {
		if (mqttClient != null) {
			mqttClient.close();
		}
	}

	public void connect(String login, String hostName) throws MqttException {
		if (mqttClient != null) {
			mqttClient.disconnect();
		}

		mqttClient = defaultMqttPahoClientFactory.getClientInstance(hostName, login);
		mqttClient.connect();

		mqttClient.subscribe(CommunicationTopic.BASE + "#");
		mqttClient.setCallback(mqttCallback);

		// get a temperature alert value
		sendMessage(new Event(Boolean.TRUE, EventSource.LOCAL, EventType.PUBLISH_TEMPERATURE_ALERT_REQUEST));
	}

	private void sendMessage(Event event) {
		final Serializable payload = event.getPayload();
		final EventType type = event.getType();
		final String topic = eventConverter.getTopic(type);
		if (topic != null) {
			final byte[] bytes = messageConverter.convertOut(type, payload);
			if (bytes != null) {
				MqttMessage mqttMsg = new MqttMessage();
				mqttMsg.setPayload(bytes);
				try {
					mqttClient.publish(topic, mqttMsg);
				} catch (MqttException e) {
					throw new IllegalStateException("Could not sent a mqtt message.", e);
				}
			}
		}
	}
}
