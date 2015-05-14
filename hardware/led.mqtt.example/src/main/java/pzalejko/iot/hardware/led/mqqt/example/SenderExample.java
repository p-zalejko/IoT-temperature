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

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SenderExample {

	MqttClient client;

	public static void main(String[] args) throws Exception {

		new SenderExample().gogo();
	}

	void gogo() throws Exception {
		client = new MqttClient("tcp://iot.eclipse.org:1883", "pzalejko_sender");
		client.connect();
		final int thCount = 2;

		final List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < thCount; i++) {
			threads.add(new Thread(this::sendData));
		}

		threads.forEach(Thread::start);

		for (final Thread t : threads) {
			if (t.isAlive()) {
				t.join();
			}
		}

		client.disconnect();
	}

	public void sendData() {
		try {
			final String msg = "A single message" + Thread.currentThread().getName() + "";

			for (int i = 0; i < 100; i++) {
				final MqttMessage message = new MqttMessage();
				message.setPayload(msg.getBytes());
				client.publish("@pzalejko/test", message);
				client.publish("@pzalejko/test2", message);
				client.publish("@pzalejko/demo/demo2", message);

				System.out.println("sent");
				Thread.sleep(1000);
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
