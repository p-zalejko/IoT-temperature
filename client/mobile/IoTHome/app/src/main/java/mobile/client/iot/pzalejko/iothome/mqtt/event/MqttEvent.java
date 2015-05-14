/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package mobile.client.iot.pzalejko.iothome.mqtt.event;

/**
 * The MqttEvent is a helper type which allows to determine what is the event source(in,out).
 */
public interface MqttEvent {

	String PAYLOAD = "eventPayload";
	String EVENT_IN = "inEvent";
	String EVENT_OUT = "outEvent";
}
