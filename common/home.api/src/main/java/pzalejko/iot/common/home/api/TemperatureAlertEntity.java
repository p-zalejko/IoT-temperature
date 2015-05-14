/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package pzalejko.iot.common.home.api;

import java.io.Serializable;

/**
 * The TemperatureAlertEntity contains a value which determines when a temperature alert should go off.
 */
public class TemperatureAlertEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private double alert;

	public TemperatureAlertEntity() {

	}

	public TemperatureAlertEntity(double alert) {
		this.alert = alert;
	}

	public double getAlert() {
		return alert;
	}

	public void setAlert(double alert) {
		this.alert = alert;
	}
}
