/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package pzalejko.iot.client.web.controller;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pzalejko.iot.client.web.entity.LoginInput;
import pzalejko.iot.client.web.entity.loginResult;
import pzalejko.iot.client.web.mqtt.MqttHandler;
import pzalejko.iot.client.web.ws.WebSocketConfig;

//@RestController
@Controller
@RequestMapping("/")
public class ItemController {

	private static final String LOG_FORMAT = "Connected to {0} as {1}.";

	@Value("${mqtt.host}")
	private String mqttHostName;

	@Autowired
	private MqttHandler mqttHandler;

	@MessageMapping(WebSocketConfig.APP_GET_TOPIC)
	@SendTo(WebSocketConfig.CONNECTION_PUSH_TOPIC)
	public loginResult logIn(LoginInput input) {
		try {
			mqttHandler.connect(input.getName(), mqttHostName);
			return new loginResult(MessageFormat.format(LOG_FORMAT, mqttHostName, input.getName()));
		} catch (Exception e) {
			return new loginResult(e.getMessage());
		}
	}

}