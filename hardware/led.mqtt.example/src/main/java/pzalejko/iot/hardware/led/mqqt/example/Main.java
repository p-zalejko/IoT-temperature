/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.led.mqqt.example;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

/**
 * 
 * This class shows a simple usage of the GPIO pins for controlling a LED.
 *
 */
public class Main implements MqttCallback {

	public static void main(String[] args) throws MqttException {
		List<MqttClient> clients = new ArrayList<MqttClient>();

		for (int i = 0; i < 10; i++) {
			Main main = new Main();
			MqttClient client = new MqttClient("tcp://iot.eclipse.org:1883", "Sending" + i);
			client.connect();
			client.setCallback(main);
			client.subscribe("pahodemo/test");

			clients.add(client);
		}

		doServer();

		for (MqttClient c : clients) {
			c.disconnect();
		}

	}

	private static void doServer() throws MqttException, MqttSecurityException, MqttPersistenceException {
		MqttClient client = new MqttClient("tcp://iot.eclipse.org:1883", "pahomqttpublish1");
		client.connect();
		MqttMessage message = new MqttMessage();
		message.setPayload("A single message".getBytes());
		client.publish("pahodemo/test", message);
		client.disconnect();
	}

	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		System.out.println("Main.deliveryComplete()");
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Main.messageArrived()" + arg1);
	}
}
