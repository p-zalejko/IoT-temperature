/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.app;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pzalejko.iot.hardware.home.api.task.TaskExecutor;
import pzalejko.iot.hardware.home.core.task.LedAlertTask;
import pzalejko.iot.hardware.home.core.task.RemoteLedControlTask;
import pzalejko.iot.hardware.home.core.task.ShutDownTask;
import pzalejko.iot.hardware.home.core.task.TemperatureTask;
import pzalejko.iot.hardware.home.core.util.LogMessages;

/**
 * The {@link Application} is responsible for initializing and setting up all tasks and services. For instance, it connects to the LED and a
 * temperature sensor and activates the task which reads a temperature and send it out.
 */
@Component
public class Application {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	private TaskExecutor taskExecutor;

	private TemperatureTask temperatureTask;
	private RemoteLedControlTask remoteLedControlTask;
	private LedAlertTask ledAlertTask;
	private ShutDownTask shutDownTask;

	@Inject
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	@Inject
	public void setTemperatureTask(TemperatureTask temperatureTask) {
		this.temperatureTask = temperatureTask;
	}

	@Inject
	public void setRemoteLedControlTask(RemoteLedControlTask remoteLedControlTask) {
		this.remoteLedControlTask = remoteLedControlTask;
	}

	@Inject
	public void setLedAlertTask(LedAlertTask ledAlertTask) {
		this.ledAlertTask = ledAlertTask;
	}

	@Inject
	public void setShutDownTask(ShutDownTask shutDownTask) {
		this.shutDownTask = shutDownTask;
	}

	/**
	 * Starts all services.
	 * <p>
	 * NOTE: When this method is done then the application can be closed.
	 *
	 * @throws ExecutionException if a task cannot be started.
	 * @throws InterruptedException if a task has been interrupted.
	 */
	public void start() throws ExecutionException, InterruptedException {
		LOG.debug(LogMessages.STARTING_APPLICATION);

		taskExecutor.executeRepeatable(temperatureTask, temperatureTask.getReadFrequency());
		taskExecutor.execute(remoteLedControlTask);
		taskExecutor.execute(ledAlertTask);

		final ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(shutDownTask).get();
	}
}
