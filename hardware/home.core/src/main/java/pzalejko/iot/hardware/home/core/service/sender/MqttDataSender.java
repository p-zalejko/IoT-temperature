/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.service.sender;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pzalejko.iot.common.home.api.converter.EventTopicConverter;
import pzalejko.iot.common.home.api.converter.MessageToEventConverter;
import pzalejko.iot.common.home.api.event.*;
import pzalejko.iot.common.home.api.topic.CommunicationTopic;
import pzalejko.iot.hardware.home.api.config.ConfigurationService;
import pzalejko.iot.hardware.home.api.config.DataSenderConfiguration;
import pzalejko.iot.hardware.home.api.sender.DataSenderService;
import pzalejko.iot.hardware.home.core.util.LogMessages;

@Component
public class MqttDataSender implements DataSenderService, EventListener {

	private static final Logger LOG = LoggerFactory.getLogger(MqttDataSender.class);

	private static final String WILDCARD_POSTFIX = "#";

	private final EventBus eventBus;
	private MqttClient mqttClient;
	private final MessageToEventConverter messageConverter;
	private final ConfigurationService configurationService;
	private final EventTopicConverter topicMapper;
	private final InternalMqttRemoteCallback callback;

	@Inject
	public MqttDataSender(EventBus eventBus, ConfigurationService configurationService, MessageToEventConverter messageConverter,
			EventTopicConverter topicMapper, InternalMqttRemoteCallback callback) {
		this.configurationService = configurationService;
		this.messageConverter = messageConverter;
		this.eventBus = eventBus;
		this.topicMapper = topicMapper;
		this.callback = callback;
	}

	@PostConstruct
	public void init() throws MqttException {
		final DataSenderConfiguration settings = configurationService.getMqttConfiguration();
		final String senderHost = settings.getHost();
		final String senderClientId = settings.getClient();

		final String baseInTopic = CommunicationTopic.HARDWARE_IN_BASE.toString();

		mqttClient = connect(senderHost, senderClientId);
		mqttClient.setCallback(callback);
		mqttClient.subscribe(baseInTopic + WILDCARD_POSTFIX);

		eventBus.register(this);
	}

	@PreDestroy
	public void destroy() throws MqttException {
		eventBus.unregister(this);

		LOG.debug(LogMessages.DISCONNECTING_FROM, mqttClient.getServerURI(), mqttClient.getClientId());
		mqttClient.disconnect();
		LOG.debug(LogMessages.DISCONNECTED_FROM, mqttClient.getServerURI(), mqttClient.getClientId());
	}

	@Override
	public void handle(Event event) {
		// send only these events which come form this application, are not events sent from remove devices/applications.
		if (event.getSource() == EventSource.LOCAL) {
			send(event.getPayload(), event.getType());
		}
	}

	@Override
	public void send(Object object, EventType type) {
		checkNotNull(object);
		checkNotNull(type);

		final Optional<String> topic = Optional.ofNullable(topicMapper.getTopic(type));
		topic.ifPresent(t -> sendEvent(object, type, t));
	}

	private void sendEvent(Object object, EventType type, String topic) {
		try {
			final byte[] bytes = messageConverter.convertOut(type, object);
			if (bytes != null) {
				final MqttMessage mqttMessage = new MqttMessage();
				mqttMessage.setPayload(bytes);

				mqttClient.publish(topic, mqttMessage);
				LOG.debug(LogMessages.SENT_MQTT_MSQ, mqttMessage, topic);
			}
		} catch (final MqttException e) {
			LOG.error(LogMessages.COULD_NOT_SEND_MESSAGE, e.getMessage(), e);
			throw new IllegalStateException(LogMessages.MESSAGE_COULD_NOT_BE_SENT, e);
		}
	}

	private MqttClient connect(String host, String clientId) throws MqttException {
		LOG.debug(LogMessages.CONNECTING_MQTT, host, clientId);
		final MqttClient client = new MqttClient(host, clientId);
		client.connect();
		LOG.debug(LogMessages.CONNECTED_MQTT, host, clientId);
		return client;
	}

}
