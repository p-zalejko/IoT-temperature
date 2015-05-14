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
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.TextView;

/**
 * The {@link MqttServiceConnection} is a MQTT connection listener which is notified when the connection to the MQTT broker is established.
 */
public class MqttServiceConnection implements ServiceConnection {

	private TextView connectingTextView;
	private Context context;

	/**
	 * Flag indicating whether we have called bind on the service.
	 */
	private boolean isBound;

	public MqttServiceConnection(Context context, TextView connectingTextView) {
		this.connectingTextView = connectingTextView;
		this.context = context;
	}

	public void onServiceConnected(ComponentName className, IBinder service) {
		isBound = true;
		connectingTextView.setText(context.getString(R.string.connected));
	}

	public void onServiceDisconnected(ComponentName className) {
		isBound = false;
		connectingTextView.setText(context.getString(R.string.disconnected));
	}

	/**
	 * Determines whether the connection is established or not.
	 * @return true if the connection is established, false if it isn't or when it has been lost.
	 */
	public boolean isBound() {
		return isBound;
	}
}