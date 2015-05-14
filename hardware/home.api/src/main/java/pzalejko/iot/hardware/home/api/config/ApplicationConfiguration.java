/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.api.config;

/**
 * The {@link ApplicationConfiguration} provides general configuration for the application and its lifecycle.
 *
 */
public interface ApplicationConfiguration {

	/**
	 * Gets time when the application should be closed.
	 * 
	 * @return time in minutes.
	 */
	int getShutDownTime();
}
