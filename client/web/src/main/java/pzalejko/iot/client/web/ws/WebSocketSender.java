/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package pzalejko.iot.client.web.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pzalejko.iot.client.web.entity.Item;

@Component
public class WebSocketSender {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendTemperature(String date, double temperature) {
        final Item item = new Item();
        item.setTemperature(temperature);
        item.setDate(date);
        simpMessagingTemplate.convertAndSend(WebSocketConfig.TEMPERATURE_PUSH_TOPIC, item);
    }

    public void sendTemperatureAlert(double temperature) {
        final Item item = new Item();
        item.setTemperature(temperature);
        simpMessagingTemplate.convertAndSend(WebSocketConfig.TEMPERATURE_ALERT_PUSH_TOPIC, item);
    }
}
