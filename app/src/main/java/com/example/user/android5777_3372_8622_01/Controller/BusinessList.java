package com.example.user.android5777_3372_8622_01.Controller;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.user.android5777_3372_8622_01.Model.backend.DBManagerFactory;
import com.example.user.android5777_3372_8622_01.Model.backend.DB_manager;
import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract.BusinessContract;
import com.example.user.android5777_3372_8622_01.R;

public class BusinessList extends ListActivity
{

    DB_manager manager = DBManagerFactory.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_business_list);

        final Cursor[] cursor = new Cursor[1];
        final boolean[] f = {false};
        try
        {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    cursor[0] = getContentResolver().query(BusinessContract.BUSINESS_URI, null, null, null, null);
                    //cursor[0] = manager.getBusiness();
                    //adapter.changeCursor(cursor[0]);
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
                            R.layout.item_row,
                            null,
                            new String[]{BusinessContract.BUSINESS_ID, BusinessContract.BUSINESS_NAME, BusinessContract.BUSINESS_ADDRESS,
                                    BusinessContract.BUSINESS_PHONE, BusinessContract.BUSINESS_EMAIL, BusinessContract.BUSINESS_WEBSITE},
                            new int[]{R.id.itemId, R.id.itemName, R.id.itemAddress, R.id.itemPhone, R.id.itemEmail, R.id.itemWebsite}
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
