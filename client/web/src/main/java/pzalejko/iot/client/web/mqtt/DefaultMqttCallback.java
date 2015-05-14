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
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

import pzalejko.iot.client.web.ws.WebSocketSender;
import pzalejko.iot.common.home.api.TemperatureAlertEntity;
import pzalejko.iot.common.home.api.TemperatureEntity;
import pzalejko.iot.common.home.api.converter.EventTopicConverter;
import pzalejko.iot.common.home.api.converter.MessageToEventConverter;
import pzalejko.iot.common.home.api.event.EventType;

public class DefaultMqttCallback implements MqttCallback {

	private static final String DATE_FORMAT = "hh:mm:ss";

	@Autowired
	private EventTopicConverter eventTopicConverter;

	@Autowired
	private MessageToEventConverter messageToEventConverter;

	@Autowired
	private WebSocketSender wsSender;

	private SimpleDateFormat dateFormat;

	@PostConstruct
	public void init() {
		dateFormat = new SimpleDateFormat(DATE_FORMAT);
	}

	@Override
	public void connectionLost(Throwable cause) {

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		final EventType eventType = eventTopicConverter.getEventType(topic);
		if (eventType != null) {
			final Serializable serializable = messageToEventConverter.convertIn(eventType, message.getPayload());
			if (serializable instanceof TemperatureEntity) {
				final String date = dateFormat.format(((TemperatureEntity) serializable).getDate());
				final double temperature = ((TemperatureEntity) serializable).getTemperature();

				wsSender.sendTemperature(date, temperature);
			}
			if (serializable instanceof TemperatureAlertEntity) {
				final double temperature = ((TemperatureAlertEntity) serializable).getAlert();
				wsSender.sendTemperatureAlert(temperature);
			}
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {

	}

}
