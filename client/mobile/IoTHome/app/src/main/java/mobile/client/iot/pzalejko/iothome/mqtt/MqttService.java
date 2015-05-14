/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package mobile.client.iot.pzalejko.iothome.mqtt;

import mobile.client.iot.pzalejko.iothome.R;
import mobile.client.iot.pzalejko.iothome.mqtt.event.MqttEvent;
import mobile.client.iot.pzalejko.iothome.mqtt.event.OutEventReceiver;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import pzalejko.iot.common.home.api.converter.EventTopicConverter;
import pzalejko.iot.common.home.api.converter.MessageToEventConverter;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

/**
 * The {@link MqttService} is responsible for establishing a connection to the MQTT broker. It also registers a callback which will be
 * receiving all events sent out by the broker.
 */
public class MqttService extends Service implements SharedPreferences.OnSharedPreferenceChangeListener {

	private static String LOG_TAG = "MqttService";

	private static final String HOST_KEY = "mqttHost";
	private static final String CLIENT_ID_KEY = "mqttClientID";
	private static final String TOPIC_KEY = "mqttBasicTopic";

	private IBinder mBinder = new LocalBinder();

	private MemoryPersistence persistence = new MemoryPersistence();

	private MqttClient mqttClient;
	private LocalBroadcastManager broadcaster;
	private SharedPreferences sharedPref;

	private MessageToEventConverter messageToEventConverter;
	private EventTopicConverter eventTopicConverter;
	private OutEventReceiver outEventReceiver;

	@Override
	public void onCreate() {
		super.onCreate();

		messageToEventConverter = new MessageToEventConverter();
		eventTopicConverter = new EventTopicConverter();
		sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

		broadcaster = LocalBroadcastManager.getInstance(this);
		sharedPref.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			final String broker = sharedPref.getString(HOST_KEY, "");
			final String clientId = sharedPref.getString(CLIENT_ID_KEY, "");
			final String topic = sharedPref.getString(TOPIC_KEY, "");

			mqttClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			Log.d(LOG_TAG, String.format("Connecting to {} as {}.", broker, clientId));
			mqttClient.connect(connOpts);

			// register an event receiver
			outEventReceiver = new OutEventReceiver(eventTopicConverter, messageToEventConverter, mqttClient);
			broadcaster.registerReceiver((outEventReceiver), new IntentFilter(MqttEvent.EVENT_OUT));

			Log.d(LOG_TAG, String.format("Connected to {}.", broker));
			MqttCallback callback = new DefaultMqttCallback(messageToEventConverter, eventTopicConverter, broadcaster);
			mqttClient.setCallback(callback);
			mqttClient.subscribe(topic);
			Log.d(LOG_TAG, String.format("Subscribed to {}.", topic));
		} catch (MqttException me) {
			Log.e(LOG_TAG, getString(R.string.could_not_connect_to_mqtt), me);
			Toast.makeText(getApplicationContext(), me.getMessage(), Toast.LENGTH_SHORT);
			mqttClient = null;
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		sharedPref.unregisterOnSharedPreferenceChangeListener(this);
		if (outEventReceiver != null) {
			broadcaster.unregisterReceiver(outEventReceiver);
		}
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.v(LOG_TAG, "in onBind");
		return mBinder;
	}

	@Override
	public void onRebind(Intent intent) {
		Log.v(LOG_TAG, "in onRebind");
		super.onRebind(intent);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.v(LOG_TAG, "in onUnbind");
		try {
			if (mqttClient != null) {
				mqttClient.close();
			}
		} catch (MqttException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
		}
		return true;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

	}

	public class LocalBinder extends Binder {

		public MqttService getServer() {
			return MqttService.this;
		}
	}
}
