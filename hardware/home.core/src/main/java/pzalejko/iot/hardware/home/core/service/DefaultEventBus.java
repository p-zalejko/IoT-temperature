/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pzalejko.iot.common.home.api.event.*;
import pzalejko.iot.hardware.home.api.task.TaskExecutor;
import pzalejko.iot.hardware.home.core.util.LogMessages;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

@Component
public class DefaultEventBus implements EventBus {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultEventBus.class);

	private com.google.common.eventbus.EventBus bus;
	private final TaskExecutor executor;
	private final List<EventListenerWrapper> listeners;

	@Inject
	public DefaultEventBus(TaskExecutor executor) {
		this.executor = executor;
		listeners = new CopyOnWriteArrayList<>();
	}

	@PostConstruct
	public void init() {
		bus = new com.google.common.eventbus.EventBus();
	}

	@PreDestroy
	public void destroy() {
		listeners.forEach(bus::unregister);
		listeners.clear();
	}

	@Override
	public void register(EventListener listener) {
		final EventListenerWrapper wrapper = new EventListenerWrapper(checkNotNull(listener));
		listeners.add(wrapper);

		bus.register(wrapper);
	}

	@Override
	public void unregister(EventListener listener) {
		checkNotNull(listener);

		listeners.stream().filter(w -> w.listener == listener).findFirst().ifPresent(w -> {
			bus.unregister(w);
			listeners.remove(w);
		});
	}

	@Override
	public void postLocal(Serializable data, EventType type) {
		postEvent(new Event(data, EventSource.LOCAL, type));
	}

	@Override
	public void postRemote(Serializable data, EventType type) {
		postEvent(new Event(data, EventSource.REMOTE, type));
	}

	/**
	 * Adds the given event to the queue. It will be sent as soon as it's possible.
	 * 
	 * @param event the event to be sent.
	 */
	private void postEvent(Event event) {
		executor.execute(() -> bus.post(checkNotNull(event)));
	}

	/**
	 * The {@link EventListenerWrapper} is a helper object which is responsible for wrapping a given {@link EventListener}. The
	 * {@link EventListenerWrapper} provides a method which is annotated by guava's annotations that are used by the guava event bus to find
	 * and fire methods.
	 */
	static class EventListenerWrapper {

		private final EventListener listener;

		public EventListenerWrapper(EventListener listener) {
			this.listener = checkNotNull(listener);
		}

		@Subscribe
		@AllowConcurrentEvents
		public void fire(Event event) {
			try {
				listener.handle(event);
			} catch (Exception e) {
				LOG.error(LogMessages.ERROR_WHILE_HANDLING_EVENT, e.getMessage(), e);
			}
		}
	}

}
