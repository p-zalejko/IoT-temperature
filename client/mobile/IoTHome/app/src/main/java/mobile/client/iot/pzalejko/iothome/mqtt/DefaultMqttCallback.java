/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package mobile.client.iot.pzalejko.iothome.mqtt;

import java.io.Serializable;

import mobile.client.iot.pzalejko.iothome.mqtt.event.MqttEvent;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import pzalejko.iot.common.home.api.converter.EventTopicConverter;
import pzalejko.iot.common.home.api.converter.MessageToEventConverter;
import pzalejko.iot.common.home.api.event.Event;
import pzalejko.iot.common.home.api.event.EventSource;
import pzalejko.iot.common.home.api.event.EventType;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * The {@link DefaultMqttCallback} is a callback which is responsible for catching and handling events sent by the MQTT broker.
 */
class DefaultMqttCallback implements MqttCallback {

	private MessageToEventConverter messageConverter;
	private EventTopicConverter eventConverter;
	private LocalBroadcastManager broadcaster;

	DefaultMqttCallback(MessageToEventConverter messageConverter, EventTopicConverter eventConverter, LocalBroadcastManager broadcaster) {
		this.messageConverter = messageConverter;
		this.eventConverter = eventConverter;
		this.broadcaster = broadcaster;
	}

	@Override
	public void connectionLost(Throwable throwable) {

	}

	@Override
	public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
		final byte[] payload = mqttMessage.getPayload();

		final EventType eventType = eventConverter.getEventType(s);
		if (eventType != null) {
			final Serializable serializable = messageConverter.convertIn(eventType, payload);
			if (serializable != null) {
				sendLocalEvent(new Event(serializable, EventSource.REMOTE, eventType));
			}
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

	}

	/**
	 * Sends a local event with a payload from the remove event.
	 * @param event the remote event.
	 */
	private void sendLocalEvent(Event event) {
		Intent intent = new Intent(MqttEvent.EVENT_IN);
		if (event != null) {
			intent.putExtra(MqttEvent.PAYLOAD, event);
		}
		broadcaster.sendBroadcast(intent);
	}

}