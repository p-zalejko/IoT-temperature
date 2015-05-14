/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.api.sender;

import pzalejko.iot.common.home.api.event.EventType;

/**
 * The {@link DataSenderService} is responsible for sending information through the network.
 */
public interface DataSenderService {

	/**
	 * Sends out the given message on the given topic.
	 * 
	 * @param object the object to be sent.
	 * @param eventType the event type which provides a topic on which the message must be sent.
	 * 
	 * @throws NullPointerException if the given message or topic is null.
	 * @throws IllegalStateException if the message cannot be set.
	 */
	void send(Object object, EventType eventType);

}
