package com.example.user.android5777_3372_8622_01.Controller;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract;
import com.example.user.android5777_3372_8622_01.R;

public class ListActivity extends android.app.ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list2);

        final Cursor[] cursor = new Cursor[1];
        final boolean[] f = {false};
        try
        {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    cursor[0] = getContentResolver().query(SystemContract.ActivityContract.ACTIVITY_URI, null, null, null, null);
                    f[0] = true;
                    return null;
                }
            }.execute();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        while (!f[0])
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Cursor c = cursor[0];
        try {
            final SimpleCursorAdapter adapter = new SimpleCursorAdapter
                    (
                            this,
                            R.layout.activity_list,
                            null,
                            new String[]{SystemContract.ActivityContract.Activity_KEY, SystemContract.ActivityContract.Activity_NAME, SystemContract.ActivityContract.Activity_DESCRIPTION,
                                    SystemContract.ActivityContract.Activity_PRICE},
                            new int[]{R.id.itemKey, R.id.itemName, R.id.itemDescription, R.id.itemPrice}
                    );
            adapter.swapCursor(c);
            this.setListAdapter(adapter);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}