/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package mobile.client.iot.pzalejko.iothome.mqtt.event;

import java.io.Serializable;

import android.util.Log;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import pzalejko.iot.common.home.api.converter.EventTopicConverter;
import pzalejko.iot.common.home.api.converter.MessageToEventConverter;
import pzalejko.iot.common.home.api.event.Event;
import pzalejko.iot.common.home.api.event.EventType;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * The {@link OutEventReceiver} is responsible for catching events sent from the android application. All events will be send to a remote
 * server(event broker).
 */
public class OutEventReceiver extends BroadcastReceiver {

	private static String LOG_TAG = "OutEventReceiver";

	private EventTopicConverter eventConverter;
	private MessageToEventConverter messageConverter;
	private MqttClient client;

	public OutEventReceiver(EventTopicConverter eventConverter, MessageToEventConverter messageConverter, MqttClient client) {
		this.eventConverter = eventConverter;
		this.messageConverter = messageConverter;
		this.client = client;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		final Serializable message = intent.getSerializableExtra(MqttEvent.PAYLOAD);
		if (message instanceof Event) {
			sendMessage((Event) message);
		}
	}

	private void sendMessage(Event event) {
		final Serializable payload = event.getPayload();
		final EventType type = event.getType();
		final String topic = eventConverter.getTopic(type);
		if (topic != null) {
			final byte[] bytes = messageConverter.convertOut(type, payload);
			if (bytes != null) {
				publishData(topic, bytes);
			}
		}
	}

	private void publishData(String topic, byte[] bytes) {
		MqttMessage mqttMsg = new MqttMessage();
		mqttMsg.setPayload(bytes);
		try {
            client.publish(topic, mqttMsg);
        } catch (MqttException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
	}
}