/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package mobile.client.iot.pzalejko.iothome;

import mobile.client.iot.pzalejko.iothome.mqtt.MqttService;
import mobile.client.iot.pzalejko.iothome.mqtt.MqttServiceConnection;
import mobile.client.iot.pzalejko.iothome.mqtt.event.InEventReceiver;
import mobile.client.iot.pzalejko.iothome.mqtt.event.MqttEvent;
import mobile.client.iot.pzalejko.iothome.settings.NetworkValidator;
import mobile.client.iot.pzalejko.iothome.settings.SettingsActivity;
import pzalejko.iot.common.home.api.TemperatureAlertEntity;
import pzalejko.iot.common.home.api.event.Event;
import pzalejko.iot.common.home.api.event.EventSource;
import pzalejko.iot.common.home.api.event.EventType;
import android.app.AlertDialog;
import android.content.*;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private static final int RESULT_SETTINGS = 1;

	private MqttServiceConnection connection;
	private BroadcastReceiver receiver;
	private TextView connectingTextView;
	private TextView tempAlertValueText;

	private LocalBroadcastManager broadcaster;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// load default preferences
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

		connectingTextView = (TextView) findViewById(R.id.connectingTextView);
		tempAlertValueText = (TextView) findViewById(R.id.tempAlertValueText);
		connection = new MqttServiceConnection(getApplicationContext(), connectingTextView);
		broadcaster = LocalBroadcastManager.getInstance(this);

		receiver = new InEventReceiver(this, broadcaster, connection);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.action_setTempAlert) {
			askForNewTemperatureAlert();
		}
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivityForResult(i, RESULT_SETTINGS);
			return true;
		}
		if (id == R.id.action_about) {
			showAbout();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!new NetworkValidator(this).validate()) {
			return;
		}

		// start a MQTT service.
		final Intent intent = new Intent(this, MqttService.class);
		startService(intent);
		bindService(intent, connection, Context.BIND_AUTO_CREATE);
		broadcaster.registerReceiver((receiver), new IntentFilter(MqttEvent.EVENT_IN));
	}

	@Override
	protected void onStop() {
		broadcaster.unregisterReceiver(receiver);
		super.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SETTINGS:
			break;
		}
	}

	private void showAbout() {
		View messageView = getLayoutInflater().inflate(R.layout.about, null, false);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.iot_app_icon);
		builder.setTitle(getString(R.string.app_name) + " " + getString(R.string.app_version));
		builder.setView(messageView);
		builder.create();
		builder.show();
	}

	private void askForNewTemperatureAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.alert_dialog_title_text));

		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		builder.setView(input);

		builder.setPositiveButton(getString(R.string.alert_dialog_update_button_text), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				tempAlertValueText.setText(R.string.empty_text_marker);

				Double alertValue = Double.parseDouble(input.getText().toString());
				TemperatureAlertEntity entity = new TemperatureAlertEntity(alertValue);

				// send a request which will set a new temperature alert value.
				Event event = new Event(entity, EventSource.LOCAL, EventType.UPDATE_TEMPERATURE_ALERT);
				Intent outIntent = new Intent(MqttEvent.EVENT_OUT);
				outIntent.putExtra(MqttEvent.PAYLOAD, event);
				broadcaster.sendBroadcast(outIntent);
			}
		});
		builder.setNegativeButton(getString(R.string.alert_dialog_cancel_button_text), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		builder.show();
	}

}
