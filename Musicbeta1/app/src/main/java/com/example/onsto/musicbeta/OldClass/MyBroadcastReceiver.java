package com.example.onsto.musicbeta.OldClass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by onsto on 30/03/2017.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    String path;
    @Override
    public void onReceive(Context context, Intent intent) {
        path = intent.getStringExtra("path");
//        Toast.makeText(context, "path:"+path, Toast.LENGTH_SHORT).show();
    }

}
