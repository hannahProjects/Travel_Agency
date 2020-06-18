package com.example.user.android5777_3372_8622_01.Controller;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.android5777_3372_8622_01.Model.backend.DBManagerFactory;
import com.example.user.android5777_3372_8622_01.Model.backend.DB_manager;
import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract;
import com.example.user.android5777_3372_8622_01.Model.datasource.Tool;
import com.example.user.android5777_3372_8622_01.Model.entities.Address;
import com.example.user.android5777_3372_8622_01.Model.entities.Business;
import com.example.user.android5777_3372_8622_01.R;

public class BusinessUpdate extends AppCompatActivity implements View.OnClickListener
{

    String businessName;
    DB_manager manager = DBManagerFactory.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_update);
        Intent intent = getIntent();
        businessName = intent.getStringExtra(SystemContract.BusinessContract.BUSINESS_NAME);
        findViews();
    }

    private EditText editTextId;
    private EditText editTextName;
    private EditText editTextCountry;
    private EditText editTextCity;
    private EditText editTextStreet;
    private EditText editTextNumber;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private EditText editTextWebsite;
    private Button updateButton;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-12-18 05:56:27 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {

        Business b = manager.findBusinessWithName(businessName);


        // was changing !!
        editTextId = (EditText)findViewById( R.id.editTextId );
        editTextId.setText(String.valueOf(b.getBusinessId()));

        editTextName = (EditText)findViewById( R.id.editTextName );
        editTextName.setText(b.getBusinessName());

        editTextCountry = (EditText)findViewById( R.id.editTextCountry );
        editTextCountry.setText(b.getBusinessAddress().getCountry());

        editTextCity = (EditText)findViewById( R.id.editTextCity );
        editTextCity.setText(b.getBusinessAddress().getCity());

        editTextStreet = (EditText)findViewById( R.id.editTextStreet );
        editTextStreet.setText(b.getBusinessAddress().getStreet());

        editTextNumber = (EditText)findViewById( R.id.editTextNumber );
        editTextNumber.setText(b.getBusinessAddress().getNumber() + "");

        editTextPhone = (EditText)findViewById( R.id.editTextPhone );
        editTextPhone.setText(b.getBusinessPhoneNumber());

        editTextEmail = (EditText)findViewById( R.id.editTextEmail );
        editTextEmail.setText(b.getBusinessEMail());

        editTextWebsite = (EditText)findViewById( R.id.editTextWebsite );
        editTextWebsite.setText(b.getBusinessWebsite());

        updateButton = (Button)findViewById( R.id.saveButton );


        updateButton.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-12-18 05:56:27 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == updateButton ) {
            Business b = new Business();
            editTextId = (EditText)findViewById( R.id.editTextId );
            b.setBusinessId(Integer.parseInt(editTextId.getText().toString()));

            editTextName = (EditText)findViewById( R.id.editTextName );
            b.setBusinessName(editTextName.getText().toString());

            Address a = new Address();
            editTextCountry = (EditText)findViewById( R.id.editTextCountry );
            a.setCountry(editTextCountry.getText().toString());

            editTextCity = (EditText)findViewById( R.id.editTextCity );
            a.setCity(editTextCity.getText().toString());
            editTextStreet = (EditText)findViewById( R.id.editTextStreet );
            a.setStreet(editTextStreet.getText().toString());
            editTextNumber = (EditText)findViewById( R.id.editTextNumber );
            a.setNumber(Integer.parseInt(editTextNumber.getText().toString()));
            b.setBusinessAddress(a);
            editTextPhone = (EditText)findViewById( R.id.editTextPhone );
            b.setBusinessPhoneNumber(editTextPhone.getText().toString());
            editTextEmail = (EditText)findViewById( R.id.editTextEmail );
            b.setBusinessEMail(editTextEmail.getText().toString());
            editTextWebsite = (EditText)findViewById( R.id.editTextWebsite );
            b.setBusinessWebsite(editTextWebsite.getText().toString());

            manager.updateBusiness(b.getBusinessId(), Tool.BusinessToContentValues(b));
            Toast.makeText(this, "The business is update", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, UserLogIn.class);
            startActivity(intent);
        }
    }

}
