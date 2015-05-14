/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.home.core.service.led;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pzalejko.iot.hardware.home.api.led.Led;
import pzalejko.iot.hardware.home.api.led.LedService;
import pzalejko.iot.hardware.home.core.util.LogMessages;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;

@Component
public class DefaultLedService implements LedService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultLedService.class);

	private static final int CACHE_SIZE = 5;

	private LoadingCache<Integer, Led> cache;

	@PostConstruct
	public void init() {
		// @formatter:off
		cache = CacheBuilder.newBuilder()
				.maximumSize(CACHE_SIZE)
				.removalListener((RemovalListener<Integer, Led>) (notification) -> notification.getValue().close())
				.build(new InternalLedCacheLoader());
		// @formatter:on
	}

	@PreDestroy
	public void destroy() {
		cache.cleanUp();
	}

	@Override
	public Led getLedById(int id) {
		checkArgument(id > 0);

		try {
			LOG.debug(LogMessages.CONNECTING_TO_LED, id);
			return cache.get(id);
		} catch (final ExecutionException e) {
			throw new IllegalStateException(LogMessages.COULD_NOT_CONNECT_TO_LED, e);
		}
	}
}
