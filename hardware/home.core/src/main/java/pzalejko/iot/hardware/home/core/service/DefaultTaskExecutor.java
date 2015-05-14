/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.concurrent.*;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pzalejko.iot.hardware.home.api.task.TaskExecutor;
import pzalejko.iot.hardware.home.core.util.LogMessages;

/**
 * The DefaultTaskExecutor is responsible for scheduling and executing tasks.
 */
@Component
public class DefaultTaskExecutor implements TaskExecutor {

	private static final int THREAD_POOL_SIZE = 10;

	private static final Logger LOG = LoggerFactory.getLogger(DefaultTaskExecutor.class);

	private final ScheduledExecutorService scheduleExecutor;
	private final ExecutorService cachedThreadPool;
	private final List<Future<?>> tasks;

	public DefaultTaskExecutor() {
		this(Executors.newScheduledThreadPool(THREAD_POOL_SIZE, new InternalThreadFactory()), Executors
				.newCachedThreadPool(new InternalThreadFactory()));

	}

	protected DefaultTaskExecutor(ScheduledExecutorService ex, ExecutorService cachedThreadPool) {
		this.scheduleExecutor = checkNotNull(ex);
		this.cachedThreadPool = checkNotNull(cachedThreadPool);
		tasks = new CopyOnWriteArrayList<>();
	}

	@PreDestroy
	public void destroy() {
		LOG.debug(LogMessages.SHUTTING_DOWN_TASK_EXECUTOR);
		tasks.stream().filter(t -> !t.isDone()).filter(t -> !t.isCancelled()).forEach(t -> t.cancel(true));
		scheduleExecutor.shutdownNow();
		tasks.clear();
		LOG.debug(LogMessages.SHUT_DOWN_TASK_EXECUTOR);
	}

	@Override
	public void executeRepeatable(Runnable task, long delay) {
		checkNotNull(task);
		checkArgument(delay > 0);

		final ScheduledFuture<?> schedule = scheduleExecutor.scheduleWithFixedDelay(task, 0, delay, TimeUnit.MILLISECONDS);
		tasks.add(schedule);
		LOG.debug(LogMessages.SCHEDULED_REPEATABLE_TASK, delay);
	}

	@Override
	public void execute(Runnable task) {
		checkNotNull(task);
		cachedThreadPool.execute(task);
	}

	/**
	 * The {@link InternalThreadFactory} is responsible for initializing thread and for adding extra uncaught exception handlers.
	 */
	static class InternalThreadFactory implements ThreadFactory {

		private final ThreadFactory factory;

		public InternalThreadFactory() {
			this(Executors.defaultThreadFactory());
		}

		public InternalThreadFactory(ThreadFactory factory) {
			this.factory = factory;
		}

		@Override
		public Thread newThread(Runnable r) {
			final Thread newThread = factory.newThread(r);
			newThread.setUncaughtExceptionHandler((t, e) -> LOG.error(LogMessages.ERROR_OCCURRED_WHILE_EXECUTING_TASK, e.getMessage(), e));
			return newThread;
		}

	}
}
