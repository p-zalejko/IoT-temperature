/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.common.home.api.event;

import java.io.Serializable;

/**
 * The {@link EventBus} is responsible for sending events and propagating them to all registered listeners.
 *
 */
public interface EventBus {

	/**
	 * Registers a new event listener.
	 * 
	 * @param listener an event listener to be registered.
	 * 
	 * @throws NullPointerException if the given listener is null.
	 *
	 */
	void register(EventListener listener);

	/**
	 * Registers an event listener.
	 * 
	 * @param listener an event listener to be unregistered.
	 * 
	 * @throws NullPointerException if the given listener is null.
	 *
	 */
	void unregister(EventListener listener);

	/**
	 * Sends out a local event.
	 * 
	 * @param data the data to be sent.
	 * @param type the event type.
	 * 
	 * @throws NullPointerException if either data or type is null.
	 */
	void postLocal(Serializable data, EventType type);

	/**
	 * Sends out a received remote event.
	 * 
	 * @param data the data to be sent.
	 * @param type the event type.
	 * 
	 * @throws NullPointerException if either data or type is null.
	 */
	void postRemote(Serializable data, EventType type);
}
