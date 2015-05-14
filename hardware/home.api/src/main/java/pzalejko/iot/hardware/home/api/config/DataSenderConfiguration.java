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
 * The {@link DataSenderConfiguration} provides a configuration which allows to establish a connection to a remove data server. The server
 * can be used to publish or receive information.
 *
 */
public interface DataSenderConfiguration {

	/**
	 * Gets the host address.
	 * 
	 * @return the host address.
	 */
	String getHost();

	/**
	 * Gets the client ID.
	 * 
	 * @return the client ID.
	 */
	String getClient();

}
