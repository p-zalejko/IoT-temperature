/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pzalejko.iot.hardware.home.core.app.Application;
import pzalejko.iot.hardware.home.core.app.ApplicationConfigurator;

/**
 * The main class which is responsible for initializing and launching the application.
 */
public class Main {

	public static void main(String[] args) throws Exception {
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigurator.class);
		final Application app = context.getBean(Application.class);
		app.start();
		context.close();
	}
}
