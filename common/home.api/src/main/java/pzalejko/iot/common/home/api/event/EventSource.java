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
 * The {@link EventSource} indicates what is the source of the event.
 *
 */
public enum EventSource {

	/**
	 * Indicates that an event comes from outside of the application.
	 */
	REMOTE,

	/**
	 * Indicates that an event comes from inside of the application.
	 */
	LOCAL;
}
