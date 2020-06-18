package com.example.user.android5777_3372_8622_01.Controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.user.android5777_3372_8622_01.Model.backend.DBManagerFactory;
import com.example.user.android5777_3372_8622_01.Model.backend.DB_manager;
import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract;
import com.example.user.android5777_3372_8622_01.Model.datasource.List_DB_manager;
import com.example.user.android5777_3372_8622_01.Model.entities.Business;
import com.example.user.android5777_3372_8622_01.R;
import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract.BusinessContract;


public class UserLogIn extends AppCompatActivity implements View.OnClickListener {

    DB_manager manager = DBManagerFactory.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log_in);
        findViews();
    }



    //private EditText enterBusinessName;
    private EditText enterBusinessName;
    private Button enterButton;
    private Button deleteBusinessButton;
    private Button updateBusinessButton;
    private Button creatBusinessButton;
    private Button addActivityButton;
    private Button showAllBusiness;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-12-14 16:19:17 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        enterBusinessName = (EditText)findViewById( R.id.enterBusinessName );
        enterButton = (Button)findViewById( R.id.enterButton );
        deleteBusinessButton = (Button)findViewById( R.id.deleteBusinessButton );
        updateBusinessButton = (Button)findViewById( R.id.updateBusinessButton );
        creatBusinessButton = (Button)findViewById( R.id.creatBusinessButton );
        addActivityButton = (Button)findViewById( R.id.addActivcity );
        showAllBusiness = (Button)findViewById( R.id.showAllBusiness );

        enterButton.setOnClickListener( this );
        deleteBusinessButton.setOnClickListener( this );
        updateBusinessButton.setOnClickListener( this );
        creatBusinessButton.setOnClickListener( this );
        addActivityButton.setOnClickListener( this );
        showAllBusiness.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-12-14 16:19:17 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        //Business b = manager.findBusinessWithName(enterBusinessName.getText().toString());
        if ( v == enterButton )
        {
            final Business[] b = new Business[1];
            final boolean[] flag = {false};
            String tname = enterBusinessName.getText().toString();
            try {
                new AsyncTask<String, Void, Void>()
                {

                    @Override
                    protected Void doInBackground(String... params) {
                        b[0] = manager.findBusinessWithName(params[0]);
                        flag[0] = true;
                        return null;
                    }
                }.execute(tname);
                //Business b = manager.findBusinessWithName(enterBusinessName.getText().toString());
                while (flag[0] == false)
                {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

            }

            if (b[0] != null)
            {
                Intent intent = new Intent(this, BusinessLogIn.class);
                intent.putExtra(SystemContract.BusinessContract.BUSINESS_ID, b[0].getBusinessId());
                startActivity(intent);
            }
            else
                Toast.makeText(this, "There is no such a business", Toast.LENGTH_SHORT).show();
        }
        else if (v == deleteBusinessButton)
        {
            Business b = manager.findBusinessWithName(enterBusinessName.getText().toString());
            if(b != null && manager.isBusinessExists(b.getBusinessId(),b.getBusinessName())) // check if the business exists
            {
                manager.deleteBusiness(b.getBusinessId());
                Toast.makeText(this, "The business"+ b.getBusinessName()+"with id "+ b.getBusinessId()+" is deleted", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "There is no such a business", Toast.LENGTH_SHORT).show();
        }


        else if (v == updateBusinessButton)
        {
            Business b = manager.findBusinessWithName(enterBusinessName.getText().toString());
            if (b != null)
            {
                Intent intent = new Intent(this, BusinessUpdate.class);
                intent.putExtra(SystemContract.BusinessContract.BUSINESS_NAME, b.getBusinessName());
                startActivity(intent);
            }
            else
                Toast.makeText(this, "There is no such a business", Toast.LENGTH_SHORT).show();
        }
        else if (v == creatBusinessButton)
        {
            //Business b = manager.findBusinessWithName(enterBusinessName.getText().toString());
            if (!enterBusinessName.getText().toString().equals("")) {
                Intent intent = new Intent(this, AddBusiness.class);
                intent.putExtra(SystemContract.BusinessContract.BUSINESS_NAME, enterBusinessName.getText().toString());
                startActivity(intent);
            }
            else
                Toast.makeText(this, "First enter the business name", Toast.LENGTH_SHORT).show();

        }
        else if (v == addActivityButton)
        {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);

        }
        else if (v == showAllBusiness)
        {
            Intent intent = new Intent(this, BusinessList.class);
            startActivity(intent);


        }
    }

}
