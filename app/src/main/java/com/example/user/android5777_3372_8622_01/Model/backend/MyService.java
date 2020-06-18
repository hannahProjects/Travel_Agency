package com.example.user.android5777_3372_8622_01.Model.backend;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.user.android5777_3372_8622_01.Model.datasource.List_DB_manager;

import static android.content.ContentValues.TAG;

/**
 * Created by Oshrat Akala on 29/12/2016.
 *
 * service for long threads that we can't use asynctask to handle
 * our service extends the service class and it is for activities that we want to do in background threads that we
 * wand to do parallel to our main activity
 */

public class MyService extends Service
{

    private static final String TAG = "MyService";
    DB_manager manager = DBManagerFactory.getInstance();

    private boolean isRunning  = false;  //boolean that tells us if the service is still running.

    @Override  //function that called when the service started to run
    public void onCreate()
    {
        Log.i(TAG, "Service onCreate");
        isRunning = true;  // update the running flag
    }

    @Override  //function that operate the thread that we want to do parallel to the main activity.
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service onStartCommand");

        //the thread, the logic of the service
        //we want to check every ten second if the DB has changed and if so we want to sent an intent to send a broadcast
        new Thread(new Runnable() {
            @Override
            public void run() {


                while (isRunning) {
                    try
                    {
                        Thread.sleep(10000);//waite ten second
                        if (manager.ifUpdated())//check for any updates
                        {
                            manager.toUpdate(false);
                            Log.d(TAG, "isUpdate run ..");
                            Intent intent1 = new Intent("com.example.user.android5777_3372_8622_01.");
                            MyService.this.sendBroadcast(intent1);//send a broadcast
                        }

                    }
                    catch (Exception e) {
                    }

                    if(isRunning){
                        Log.i(TAG, "Service running");
                    }
                }

                //Stop service once it finishes its task
                stopSelf();
            }
        }).start();

        return Service.START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override  //function for when the thread is over
    public void onDestroy() {

        isRunning = false;
        Log.i(TAG, "Service onDestroy");
    }
}
