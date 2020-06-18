package com.example.user.android5777_3372_8622_01.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.android5777_3372_8622_01.Model.backend.DBManagerFactory;
import com.example.user.android5777_3372_8622_01.Model.backend.DB_manager;
import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract;
import com.example.user.android5777_3372_8622_01.Model.datasource.Tool;
import com.example.user.android5777_3372_8622_01.Model.entities.Address;
import com.example.user.android5777_3372_8622_01.Model.entities.Business;
import com.example.user.android5777_3372_8622_01.R;

public class AddBusiness extends AppCompatActivity implements View.OnClickListener
{

    DB_manager manager = DBManagerFactory.getInstance();
    String businessname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);
        Intent intent = getIntent();
        businessname = intent.getStringExtra(SystemContract.BusinessContract.BUSINESS_NAME);
        findViews();
    }

    private EditText businessId;
    private EditText businessName;
    private TextView businessAdrress;
    private EditText businessCountry;
    private EditText businessCity;
    private EditText businessStreet;
    private EditText businessNumber;
    private EditText businessPhone;
    private EditText businessEmail;
    private EditText businessWebsite;
    private Button saveButton;
    private ImageView imageView;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-12-21 17:04:02 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        businessId = (EditText)findViewById( R.id.businessId );
        businessName = (EditText)findViewById( R.id.businessName );
        businessAdrress = (TextView)findViewById( R.id.businessAddress );
        businessCountry = (EditText)findViewById( R.id.businessCountry );
        businessCity = (EditText)findViewById( R.id.businessCity );
        businessStreet = (EditText)findViewById( R.id.businessStreet );
        businessNumber = (EditText)findViewById( R.id.businessNumber );
        businessPhone = (EditText)findViewById( R.id.businessPhone );
        businessEmail = (EditText)findViewById( R.id.businessEmail );
        businessWebsite = (EditText)findViewById( R.id.businessWebsite );

        saveButton = (Button)findViewById( R.id.saveButton );
        //imageView = (ImageView)findViewById( R.id.imageView );

        saveButton.setOnClickListener( this );
        businessName.setText(businessname);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-12-21 17:04:02 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v)
    {
        if ( v == saveButton ) {
            if (    businessId.getText().toString().equals("") ||
                    businessName.getText().toString().equals("") ||
                    businessCountry.getText().toString().equals("") ||
                    businessCity.getText().toString().equals("") ||
                    businessStreet.getText().toString().equals("") ||
                    businessNumber.getText().toString().equals("") ||
                    businessPhone.getText().toString().equals("") ||
                    businessEmail.getText().toString().equals("") ||
                    businessWebsite.getText().toString().equals("") )
                Toast.makeText(this, "First Enter All The Details", Toast.LENGTH_SHORT).show();
            else
            {
                Address address = new Address(businessCountry.getText().toString(), businessCity.getText().toString(),
                        businessStreet.getText().toString(), Integer.parseInt(businessNumber.getText().toString()));
                Business business = new Business(Integer.parseInt(businessId.getText().toString()),
                        businessName.getText().toString(),
                        address,
                        businessPhone.getText().toString(),
                        businessEmail.getText().toString(),
                        businessWebsite.getText().toString() );
                final int tid = Integer.parseInt(businessId.getText().toString());
                final String tname = businessName.getText().toString();
                final Business[] b1 = new Business[1];
                final Business[] b2 = new Business[1];
                final int[] t = {0, 0};
                try {
                    new AsyncTask<Business, Void, Void>()
                    {

                        @Override
                        protected Void doInBackground(Business... params)
                        {
                            b1[0] = manager.findBusinessWithId(tid);
                            b2[0] = manager.findBusinessWithName(tname);
                            if (b1[0] == null && b2[0] == null)
                            {
                                t[1] = manager.addBusiness(Tool.BusinessToContentValues(params[0]));
                                t[0] = 1;
                                //return 1;
                            }
                            if (t[1] != 0)
                                t[0] = 1;
                            return null;
                        }
                    }.execute(business);
                }
                catch (Exception e) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

                }


                    //int t = manager.addBusiness(Tool.BusinessToContentValues(business));
                while (t[0] == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (t[1] != 0) {
                    Toast.makeText(this, "the business " + t[0] + " added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, UserLogIn.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this, "The business name or id you enter is already exist", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

}
