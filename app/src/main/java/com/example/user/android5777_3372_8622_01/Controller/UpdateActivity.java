package com.example.user.android5777_3372_8622_01.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.android5777_3372_8622_01.Model.backend.DBManagerFactory;
import com.example.user.android5777_3372_8622_01.Model.backend.DB_manager;
import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract;
import com.example.user.android5777_3372_8622_01.Model.datasource.Tool;
import com.example.user.android5777_3372_8622_01.Model.entities.Activity;
import com.example.user.android5777_3372_8622_01.Model.entities.Business;
import com.example.user.android5777_3372_8622_01.R;

import java.text.ParseException;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener
{

    String acivityName;
    DB_manager manager = DBManagerFactory.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent intent = getIntent();
        acivityName = intent.getStringExtra(SystemContract.ActivityContract.Activity_NAME);
        findViews();

    }


    private CustomDatePicker StartDateCustomDatePicker;
    private CustomDatePicker EndDateCustomDatePicker;
    private EditText activityName;
    private Spinner activitiesNameSpinner;
    private EditText activityCountry;
    private EditText editTextPrice;
    private EditText editTextDescription;
    private EditText editTextBusiness;
    private Button updateButton;
    private ImageView imageView;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-01-01 07:30:34 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews()
    {
        Activity b = manager.findActivityWithName(acivityName);


        activityName = (EditText)findViewById( R.id.activityName );
        activityName.setText(b.getName());

        String [] listOfActivities = {"HotelVacation", "TravelAgency", "Entertainment", "Airline"};
        ArrayAdapter enumAdapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listOfActivities);
        enumAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ///activitiesNameSpinner.setAdapter(enumAdapter);
        activitiesNameSpinner = (Spinner)findViewById( R.id.activitiesNameSpinner );  // need to add
        activitiesNameSpinner.setAdapter(enumAdapter);

        activityCountry = (EditText)findViewById( R.id.activityCountry );
        activityCountry.setText(b.getActivityCountry());

        StartDateCustomDatePicker = (CustomDatePicker)findViewById( R.id.StartDateCustomDatePicker );
        StartDateCustomDatePicker.setText(String.valueOf(b.getActivityStartDate()));

        EndDateCustomDatePicker = (CustomDatePicker)findViewById( R.id.EndDateCustomDatePicker );
        EndDateCustomDatePicker.setText(String.valueOf(b.getActivityEndDate()));

        editTextPrice = (EditText)findViewById( R.id.editTextPrice );
        editTextPrice.setText(String.valueOf(b.getActivityPrice()));

        editTextDescription = (EditText)findViewById( R.id.editTextDescription );
        editTextDescription.setText(b.getActivityDescription());

        editTextBusiness = (EditText)findViewById( R.id.editTextBusiness );
        editTextBusiness.setText(String.valueOf(b.getActivityBusinessId()));

        updateButton = (Button)findViewById( R.id.updateButton );
        //imageView = (ImageView)findViewById( R.id.imageView );

        updateButton.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-01-01 07:30:34 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == updateButton )
        {
            Activity b = new Activity();

            activityName = (EditText)findViewById( R.id.activityName );
            b.setName(activityName.getText().toString());

           /* activitiesNameSpinner = (Spinner)findViewById( R.id.activitiesNameSpinner );
            b.setActivity(activitiesNameSpinner.getAdapter(enumAdapter));*/

            StartDateCustomDatePicker = (CustomDatePicker)findViewById( R.id.StartDateCustomDatePicker );
            b.setActivityStartDate(StartDateCustomDatePicker.getDate());

            EndDateCustomDatePicker = (CustomDatePicker)findViewById( R.id.EndDateCustomDatePicker );
            b.setActivityEndDate(EndDateCustomDatePicker.getDate());

            activityCountry = (EditText)findViewById( R.id.activityCountry );
            b.setActivityCountry(activityCountry.getText().toString());

            editTextPrice = (EditText)findViewById( R.id.editTextPrice );
            b.setActivityPrice(Float.parseFloat(editTextPrice.getText().toString()));


            editTextDescription = (EditText)findViewById( R.id.editTextDescription );
            b.setActivityDescription(editTextDescription.getText().toString());

            editTextBusiness = (EditText)findViewById( R.id.editTextBusiness );
            b.setActivityBusinessId(Integer.parseInt(editTextBusiness.getText().toString()));

            try
            {
                if(!manager.isActivityExists(b.getName()) /*&& manager.findBusinessWithId(b.getActivityBusinessId()) != null*/)
                {
                    manager.updateActivity(b.getKey(), Tool.ActivityToContentValues(b));
                    Toast.makeText(this, "The activity " + b.getName() + " is update", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, BusinessLogIn.class);
                    startActivity(intent);
                }
            }
            catch (ParseException e)
            {
                Toast.makeText(this, "The activity " + b.getName() + " is already exist", Toast.LENGTH_SHORT).show();
                //e.printStackTrace();
            }
           /* Toast.makeText(this, "The activity " + b.getName() + " is not update", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, UserLogIn.class);
            startActivity(intent);*/
        }
    }

}
