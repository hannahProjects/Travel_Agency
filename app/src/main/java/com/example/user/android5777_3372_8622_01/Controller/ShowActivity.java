package com.example.user.android5777_3372_8622_01.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.user.android5777_3372_8622_01.Model.backend.DBManagerFactory;
import com.example.user.android5777_3372_8622_01.Model.backend.DB_manager;
import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract;
import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract.ActivityContract;
import com.example.user.android5777_3372_8622_01.Model.entities.Activity;
import com.example.user.android5777_3372_8622_01.R;

import static com.example.user.android5777_3372_8622_01.Model.backend.SystemContract.ActivityContract.*;

/*
* class that shows all the properties of activity that the user chose in the previous activity
 */

public class ShowActivity extends AppCompatActivity
{
    String n; // for the extra of the intent
    DB_manager manager = DBManagerFactory.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the intent that called the activity and get the data from it
        setContentView(R.layout.activity_show_activity);
        Intent intent = getIntent();
        n = intent.getStringExtra(Activity_NAME);

        findViews();//function to initialize the view

    }

    //display components
    private TextView name;
    private TextView kind;
    private TextView country;
    private TextView start;
    private TextView end;
    private TextView price;
    private TextView description;
    private TextView businessId;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-12-18 05:05:05 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews()
    {
        final Activity[] activity = new Activity[1];
        final boolean[] flag = {false};
        try {
            new AsyncTask<String, Void, Void>() {

                @Override
                protected Void doInBackground(String... params) {
                    activity[0] = manager.findActivityWithName(params[0]);
                    flag[0] = true;
                    return null;
                }
            }.execute(n);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        while (flag[0] == false)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Activity activity = manager.findActivityWithName(n);
        name = (TextView)findViewById( R.id.name );
        name.setText(activity[0].getName());
        kind = (TextView)findViewById( R.id.kind );
        kind.setText(activity[0].getActivity().toString());
        country = (TextView)findViewById( R.id.country );
        country.setText(activity[0].getActivityCountry());
        start = (TextView)findViewById( R.id.start );
        start.setText(activity[0].getActivityStartDate().toString());
        end = (TextView)findViewById( R.id.end );
        end.setText(activity[0].getActivityEndDate().toString());
        price = (TextView)findViewById( R.id.price );
        price.setText(String.valueOf(activity[0].getActivityPrice()));
        description = (TextView)findViewById( R.id.description );
        description.setText(activity[0].getActivityDescription());
        businessId = (TextView)findViewById( R.id.businessId );
        businessId.setText((activity[0].getActivityBusinessId())+ "");
        //System.out.println("sdfghjk");
    }

}
