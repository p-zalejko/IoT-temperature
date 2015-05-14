/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package pzalejko.iot.hardware.home.core.service.sender;

import java.io.Serializable;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pzalejko.iot.common.home.api.converter.EventTopicConverter;
import pzalejko.iot.common.home.api.converter.MessageToEventConverter;
import pzalejko.iot.common.home.api.event.EventBus;
import pzalejko.iot.common.home.api.event.EventType;
import pzalejko.iot.hardware.home.core.util.LogMessages;

/**
 * The {@link InternalMqttRemoteCallback} is responsible for catching remote messages sent on certain events. All messages will be
 * propagated as {@link pzalejko.iot.common.home.api.event.EventSource#REMOTE remote} events.
 *
 */
@Component
class InternalMqttRemoteCallback implements MqttCallback {

	private static final Logger LOG = LoggerFactory.getLogger(InternalMqttRemoteCallback.class);

	private final EventBus eventBus;
	private final EventTopicConverter eventTopicMapper;
	private final MessageToEventConverter eventConverter;

	@Inject
	public InternalMqttRemoteCallback(EventBus eventBus, EventTopicConverter eventTopicMapper, MessageToEventConverter eventConverter) {
		this.eventBus = eventBus;
		this.eventTopicMapper = eventTopicMapper;
		this.eventConverter = eventConverter;
	}

	@Override
	public void connectionLost(Throwable cause) {
		LOG.warn(LogMessages.MQTT_CONNECTION_LOST, cause.getMessage(), cause);
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		LOG.debug(LogMessages.RECEIVED_MESSAGE, topic, message);

		final Optional<EventType> eventType = Optional.ofNullable(eventTopicMapper.getEventType(topic));
		eventType.ifPresent(t -> {
			final Serializable data = eventConverter.convertIn(t, message.getPayload());
			eventBus.postRemote(data, t);
		});
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		LOG.warn(LogMessages.MQTT_DELIVERY_COMPLETE, token);
	}
}