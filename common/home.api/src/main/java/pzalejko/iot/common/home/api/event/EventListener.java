/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.common.home.api.event;

/**
 * The {@link EventListener} allows to receive events sent by the {@link EventBus}.
 *
 */
public interface EventListener {

	/**
	 * Handles an event.
	 * 
	 * @param event the event.
	 */
	void handle(Event event);
}
