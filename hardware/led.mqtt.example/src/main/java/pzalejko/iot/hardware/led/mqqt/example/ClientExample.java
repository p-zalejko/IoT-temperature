/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.led.mqqt.example;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ClientExample implements MqttCallback {
	public static void main(String[] args) throws MqttException, InterruptedException {
		final ClientExample main = new ClientExample();

		final MqttClient client = new MqttClient("tcp://iot.eclipse.org:1883", "pzalejko_client");
		client.connect();
		client.setCallback(main);
		client.subscribe("@pzalejko/#");

		for (int i = 0; i < 320; i++) {
			final MqttMessage message;
			if (i % 2 == 0) {
				message = new MqttMessage();
				message.setPayload("true".getBytes());
			} else {
				message = new MqttMessage();
				message.setPayload("false".getBytes());
			}

			client.publish("@pzalejko/pi/in/led/set", message);
			Thread.sleep(1500);
		}
		Thread.sleep(90 * 1000);

		client.disconnect();
	}

	@Override
	public void connectionLost(Throwable arg0) {
		System.out.println("connection lost:" + arg0);

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		System.out.println("Main.deliveryComplete()");
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("topic = " + topic + " " + message);
	}
}
