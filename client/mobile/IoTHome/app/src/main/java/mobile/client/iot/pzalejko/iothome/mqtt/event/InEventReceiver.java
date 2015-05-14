/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package mobile.client.iot.pzalejko.iothome.mqtt.event;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import mobile.client.iot.pzalejko.iothome.R;
import mobile.client.iot.pzalejko.iothome.mqtt.MqttServiceConnection;
import pzalejko.iot.common.home.api.TemperatureAlertEntity;
import pzalejko.iot.common.home.api.TemperatureEntity;
import pzalejko.iot.common.home.api.event.Event;
import pzalejko.iot.common.home.api.event.EventSource;
import pzalejko.iot.common.home.api.event.EventType;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The {@link InEventReceiver} is responsible for handling events sent from the remove server(broker) to the android application. Depending
 * on the event type and its payload UI components will updated respectively.
 */
public class InEventReceiver extends BroadcastReceiver {

    // TODO: it should be sent from the server, together with a temperature value.
	private static final String TEMPERATURE_FORMAT = "C";

	private final SimpleDateFormat dateFormat;

	private LocalBroadcastManager broadcaster;
	private MqttServiceConnection connection;

	private TextView temperatureView;
	private TextView temperatureAlertView;
	private TextView connectingTextView;
	private ImageView ledImageView;

	private boolean isAlertValueObtained;

	public InEventReceiver(Activity context, LocalBroadcastManager broadcaster, MqttServiceConnection connection) {
		this.connection = connection;
		this.broadcaster = broadcaster;

		temperatureView = (TextView) context.findViewById(R.id.temperatureTextView);
		connectingTextView = (TextView) context.findViewById(R.id.connectingTextView);
		ledImageView = (ImageView) context.findViewById(R.id.ledImageView);
		temperatureAlertView = (TextView) context.findViewById(R.id.tempAlertValueText);

		if (temperatureAlertView == null) {
			throw new IllegalStateException(context.getString(R.string.temperature_view_missing));
		}

		if (connectingTextView == null) {
			throw new IllegalStateException(context.getString(R.string.connecting_status_view_missing));
		}

		if (ledImageView == null) {
			throw new IllegalStateException(context.getString(R.string.led_status_view_missing));
		}

		if (temperatureAlertView == null) {
			throw new IllegalStateException(context.getString(R.string.temperature_alert_view_missing));
		}

		dateFormat = new SimpleDateFormat(context.getString(R.string.dateFormat));
		isAlertValueObtained = false;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (connection.isBound()) {
			final Serializable message = intent.getSerializableExtra(MqttEvent.PAYLOAD);
			if (message instanceof Event) {
				handleEvent(context, (Event) message);
			}

			if (!isAlertValueObtained) {
				// send a request which will get a current temperature alert value.
				Event event = new Event(Boolean.TRUE, EventSource.LOCAL, EventType.PUBLISH_TEMPERATURE_ALERT_REQUEST);
				Intent outIntent = new Intent(MqttEvent.EVENT_OUT);
				outIntent.putExtra(MqttEvent.PAYLOAD, event);
				broadcaster.sendBroadcast(outIntent);
			}
		}
	}

	private void handleEvent(Context context, Event message) {
		switch (message.getType()) {
		case TEMPERATURE_VALUE:
			updateTemperatureView(context, message);
			break;
		case LED_VALUE:
			updateLedView(message);
			break;
		case TEMPERATURE_ALERT_VALUE:
			updateTemperatureAlertView(context, message);
			break;
		}
	}

	private void updateTemperatureView(Context context, Event message) {
		TemperatureEntity entity = (TemperatureEntity) message.getPayload();

		final String tmpValue = Double.toString(entity.getTemperature());
		final String dateValue = dateFormat.format(entity.getDate());

		final String msgFormat = context.getString(R.string.temperatureFormat);
		temperatureView.setText(MessageFormat.format(msgFormat, tmpValue, TEMPERATURE_FORMAT));
		connectingTextView.setText(context.getString(R.string.updateOn) + dateValue);
	}

	private void updateLedView(Event message) {
		if ((Boolean) message.getPayload()) {
			ledImageView.setImageResource(R.drawable.led_on);
		} else {
			ledImageView.setImageResource(R.drawable.led_off);
		}
	}

	private void updateTemperatureAlertView(Context context, Event message) {
		isAlertValueObtained = true;

		TemperatureAlertEntity entity = (TemperatureAlertEntity) message.getPayload();
		final String msgFormat = context.getString(R.string.temperatureFormat);
		temperatureAlertView.setText(MessageFormat.format(msgFormat, entity.getAlert(), TEMPERATURE_FORMAT));
	}
}