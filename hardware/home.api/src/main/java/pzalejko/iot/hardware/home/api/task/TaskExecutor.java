/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.api.task;

/**
 * The {@link TaskExecutor} is responsible for managing and executing tasks. It determines how many tasks can be executed in parallel.
 *
 */
public interface TaskExecutor {

	/**
	 * Executes the given repeatable task.
	 * 
	 * @param task the task to be executed.
	 * @param delay the delay between executions, in milliseconds.
	 * 
	 * @throws NullPointerException if the given task is null.
	 * @throws IllegalArgumentException if the given delay is less than 1.
	 */
	void executeRepeatable(Runnable task, long delay);

	/**
	 * Executes the given task.
	 * 
	 * @param task the task to be executed.
	 * 
	 * @throws NullPointerException if the given task is null.
	 */
	void execute(Runnable task);
}
