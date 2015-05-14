/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package mobile.client.iot.pzalejko.iothome.settings;

import mobile.client.iot.pzalejko.iothome.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * The {@link NetworkValidator} is responsible for checking whether a network connection is available or not.
 */
public class NetworkValidator {

    private final Activity activity;

    public NetworkValidator(Activity activity) {
        this.activity = activity;
    }

    /**
     * Checks whether a network connection is available.
     *
     * @return true if the connection is present, false if not. If false then the application can be killed.
     */
    public boolean validate() {
        if (!isNetworkAvailable()) {
            setUpNetworkConnection();
            return false;
        }

        return true;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void setUpNetworkConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(android.R.attr.alertDialogIcon);
        builder.setTitle(activity.getString(R.string.set_up_network_dialog_title));

        builder.setMessage(activity.getString(R.string.set_up_network_dialog_message))
                .setPositiveButton(activity.getString(R.string.set_up_network_dialog_ok_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        activity.startActivity(intent);
                    }
                }).setNegativeButton(activity.getString(R.string.set_up_network_dialog_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog. Turn off the application.
                activity.finish();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
