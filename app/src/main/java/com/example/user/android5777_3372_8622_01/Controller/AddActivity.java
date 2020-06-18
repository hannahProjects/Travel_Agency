package com.example.user.android5777_3372_8622_01.Controller;


import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.android5777_3372_8622_01.Model.backend.DBManagerFactory;
import com.example.user.android5777_3372_8622_01.Model.backend.DB_manager;
import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract;
import com.example.user.android5777_3372_8622_01.Model.datasource.Tool;
import com.example.user.android5777_3372_8622_01.Model.entities.Activity;
import com.example.user.android5777_3372_8622_01.Model.entities.ActivityKinds;
import com.example.user.android5777_3372_8622_01.Model.entities.Business;
import com.example.user.android5777_3372_8622_01.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    DB_manager manager = DBManagerFactory.getInstance();
    String _activityName;

    //String Activityname;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);
        Intent intent = getIntent();
        _activityName = intent.getStringExtra(SystemContract.ActivityContract.Activity_NAME);
        findViews();
    }

    private CustomDatePicker StartDateCustomDatePicker;
    private CustomDatePicker EndDateCustomDatePicker;
    private EditText activityName;
    private Spinner activitiesNameSpinner;
    private Spinner businessNameSpinner;
    private EditText activityCountry;
    private EditText editTextPrice;
    private EditText editTextDescription;
    //private EditText editTextBusiness;
    private TextView textViewBusiness;
    private Button saveButton;
    private ImageView imageView;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-12-21 16:59:58 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {

        StartDateCustomDatePicker = (CustomDatePicker) findViewById(R.id.StartDateCustomDatePicker);
        EndDateCustomDatePicker = (CustomDatePicker) findViewById(R.id.EndDateCustomDatePicker);
        activityName = (EditText) findViewById(R.id.activityName);
        activitiesNameSpinner = (Spinner) findViewById(R.id.activitiesNameSpinner);
        businessNameSpinner = (Spinner) findViewById(R.id.businessNameSpinner);
        activityCountry = (EditText) findViewById(R.id.activityCountry);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        textViewBusiness = (TextView) findViewById(R.id.textViewBusiness);

        //String t = businessNameSpinner.getSelectedItem().toString();
        //String businessName = (businessNameSpinner.getSelectedItem()).toString();
        //int BusinessId = manager.findBusinessWithName(businessName).getBusinessId();

        // textViewBusiness.setText(manager.findBusinessNameById(BusinessId));

        //String businessId = (businessNameSpinner.getSelectedItem()).toString();
        //int BusinessIdName = Integer.parseInt(businessId);
        //textViewBusiness.setText((CharSequence) manager.findBusinessWithId(BusinessIdName));


        saveButton = (Button) findViewById(R.id.saveButton);
        //imageView = (ImageView) findViewById(R.id.imageView);

        saveButton.setOnClickListener(this);
        activityName.setText(_activityName);

        final ArrayList<String>[] businessNames = new ArrayList[1];
        final boolean [] flag = {false};
        try {
            new AsyncTask<Void, Void, Void>()
            {

                @Override
                protected Void doInBackground(Void... voids) {
                    businessNames[0] = manager.allBusinessNames();
                    flag[0] = true;
                    return null;
                }

            }.execute();

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        while (!flag[0])
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, businessNames[0]);
        businessNameSpinner.setAdapter(adapter);
        //businessNameSpinner.setOnItemClickListener((AdapterView.OnItemClickListener) adapter);
        //final boolean[] f = {false};
        businessNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                try {
                    new AsyncTask<String,Void,Business>()
                    {

                        @Override
                        protected Business doInBackground(String... params) {
                            return manager.findBusinessWithName(params[0]);
                            //return null;
                        }
                        @Override
                        protected void onPostExecute(Business business) {
                            super.onPostExecute(business);
                            textViewBusiness.setText(business.getBusinessId()+"");
                            //f[0] = true;
                        }
                    }.execute(businessNameSpinner.getSelectedItem().toString());

                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textViewBusiness.setText("choose a business");
            }
        });


        String[] listOfActivities = {"HotelVacation", "TravelAgency", "Entertainment", "Airline"};
        ArrayAdapter enumAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listOfActivities);
        enumAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        activitiesNameSpinner.setAdapter(enumAdapter);
    }


 /* Handle button click events<br />
 * <br />
 * Auto-created on 2016-12-21 16:59:58 by Android Layout Finder
 * (http://www.buzzingandroid.com/tools/android-layout-finder)
 */

    @Override
    public void onClick(View v)
    {
        if ( v == saveButton )
        {
            if(    activityName.getText().toString().equals("")||
                   activityCountry.getText().toString().equals("")||
                   editTextPrice.getText().toString().equals("")||
                   editTextDescription.getText().toString().equals(""))
                Toast.makeText(this,"First Enter All The Details ", Toast.LENGTH_SHORT).show();
            else
            {
                String businessName = (businessNameSpinner.getSelectedItem()).toString();

                int BusinessId = Integer.parseInt(textViewBusiness.getText().toString());
                String tmpActivityName = activityName.getText().toString();
                String activityKinds = activitiesNameSpinner.getSelectedItem().toString();
                Activity activity = new Activity(tmpActivityName,
                                                 activityKinds,
                                                StartDateCustomDatePicker.getDate(),
                                                EndDateCustomDatePicker.getDate(),
                                                 activityCountry.getText().toString(),
                                                 Float.parseFloat(editTextPrice.getText().toString()),
                                                 editTextDescription.getText().toString(),
                                                 //Integer.parseInt(textViewBusiness.getText().toString())
                                                    BusinessId);
                final boolean[] f = {false, false};
                try {
                    new AsyncTask<String, Void, Void>()
                    {
                        @Override
                        protected Void doInBackground(String... params) {
                            if (manager.isActivityExists(params[0]))
                                f[0] = true;
                            f[1]=true;
                            return null;
                        }

                    }.execute(tmpActivityName);
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
                while (!f[1])
                {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(!f[0])   //check if the activity already exists and if the business is really exist
                {
                    try {
                        new AsyncTask<ContentValues, Void, Void>() {

                            @Override
                            protected Void doInBackground(ContentValues... params) {
                                try {
                                    manager.addActivity(params[0]);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                //Toast.makeText(, "The Activity added", Toast.LENGTH_SHORT).show();

                            }
                        }.execute(Tool.ActivityToContentValues(activity));
                        //manager.addActivity(Tool.ActivityToContentValues(activity));
                        //Toast.makeText(this, "The Activity " + activityName.getText().toString() + " added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, BusinessLogIn.class);
                        startActivity(intent);


                    } catch (Exception e) {
                        Toast.makeText(this, "Their is a problem  ", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else
                    Toast.makeText(this, "could not add this activity , this activity already exist", Toast.LENGTH_SHORT).show();

            }

        }
    }
}


