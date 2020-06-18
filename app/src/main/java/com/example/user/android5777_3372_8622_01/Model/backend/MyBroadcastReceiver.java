package com.example.user.android5777_3372_8622_01.Model.backend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by user on 30/12/2016.
 *
 * Broadcast Receiver
 * the class extends the broadcastReceiver class to get the broadcast massages.
 */

public class MyBroadcastReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent) //when the app receive something
    {
        CharSequence intentData = intent.getCharSequenceExtra("message");
        Toast.makeText(context, "received the Intent's message:  "+intentData,Toast.LENGTH_LONG).show();
        if (intent.getAction().matches("android.intent.action.TIME_TICK"))//if it is a clock change massage
            Toast.makeText(context, "TIME_TICK", Toast.LENGTH_LONG).show(); // send a toast.
    }
}
