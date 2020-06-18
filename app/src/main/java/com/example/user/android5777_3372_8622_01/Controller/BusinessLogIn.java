package com.example.user.android5777_3372_8622_01.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.android5777_3372_8622_01.Model.backend.DBManagerFactory;
import com.example.user.android5777_3372_8622_01.Model.backend.DB_manager;
import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract;
import com.example.user.android5777_3372_8622_01.Model.entities.Activity;
import com.example.user.android5777_3372_8622_01.R;

public class BusinessLogIn extends AppCompatActivity implements View.OnClickListener {

    int businessId;
    DB_manager manager = DBManagerFactory.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_log_in);
        findViews();

        Intent intent = getIntent();
        businessId = intent.getIntExtra(SystemContract.BusinessContract.BUSINESS_ID, -1);
    }


    private EditText enterActivityName;
    private Button showActivityButton;
    private Button updateActivityButton;
    private Button deleteActivityButton;
    private Button addActivityButton;
    private Button allActivities;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-12-18 04:27:58 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        enterActivityName = (EditText)findViewById( R.id.enterActivityName );
        showActivityButton = (Button)findViewById( R.id.showActivityButton );
        updateActivityButton = (Button)findViewById( R.id.updateActivityButton );
        deleteActivityButton = (Button)findViewById( R.id.deleteActivityButton );
        addActivityButton = (Button)findViewById(R.id.addActivityButton);
        allActivities = (Button)findViewById(R.id.activityList);

        showActivityButton.setOnClickListener( this );
        updateActivityButton.setOnClickListener( this );
        deleteActivityButton.setOnClickListener( this );
        addActivityButton.setOnClickListener(this);
        allActivities.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-12-18 04:27:58 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v)
    {
        if (v == showActivityButton) {
            //Activity a = manager.findActivityWithName(enterActivityName.getText().toString());
            try {
                final String tmp = enterActivityName.getText().toString();
                final Boolean[] flag = {false, false};
                try {
                    new AsyncTask<String, Void, Void>() {

                        @Override
                        protected Void doInBackground(String... params) {
                            if (manager.isActivityExists(tmp) == true)
                                flag[0] = true;
                            else flag[0] = false;
                            flag[1] = true;
                            return null;
                        }
                    }.execute(tmp);
                }
                catch (Exception e)
                {Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();}

                while (flag[1] == false)
                {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (flag[0]) {
                    Intent intent = new Intent(this, ShowActivity.class);
                    intent.putExtra(SystemContract.ActivityContract.Activity_NAME, enterActivityName.getText().toString());
                    startActivity(intent);
                } else
                    Toast.makeText(this, "the activity does not exist", Toast.LENGTH_SHORT).show();

            }
            catch (Exception E)
            {
                Toast.makeText(this, (CharSequence) E, Toast.LENGTH_SHORT).show();

            }

        }
        else if ( v == updateActivityButton )
        {
            if(!enterActivityName.getText().toString().equals(""))
                {
                    String nameActivity = enterActivityName.getText().toString();
                    //if (manager.findActivityWithName(enterActivityName.getText().toString()) != null)
                    if(manager.isActivityExists(nameActivity))
                    {
                            //Activity a = manager.findActivityWithName(enterActivityName.getText().toString());
                            Intent intent = new Intent(this, UpdateActivity.class);
                            intent.putExtra(SystemContract.ActivityContract.Activity_NAME, enterActivityName.getText().toString());
                            startActivity(intent);
                    }
                    else
                        Toast.makeText(this, "The activity " + enterActivityName.getText().toString() + " doesnt exist", Toast.LENGTH_SHORT).show();
                }
            else
                Toast.makeText(this, "First enter the activity name", Toast.LENGTH_SHORT).show();

        }
        else if ( v == deleteActivityButton )
        {
            if (!enterActivityName.getText().toString().equals(""))
            {
                String nameActivity = enterActivityName.getText().toString();
                if (manager.isActivityExists(nameActivity)) // check if the activity doesnt exist
                {
                    int keyActivity = (manager.findActivityWithName(nameActivity)).getKey();
                    manager.deleteActivity(keyActivity);
                    Toast.makeText(this, "The activity " + enterActivityName.getText().toString() + " is deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, BusinessLogIn.class);
                    intent.putExtra(SystemContract.ActivityContract.Activity_NAME, enterActivityName.getText().toString());
                    startActivity(intent);
                }
                else
                    Toast.makeText(this, "The activity " + enterActivityName.getText().toString() + " doesnt exist", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "First enter the activity name", Toast.LENGTH_SHORT).show();
        }

        else if ( v == addActivityButton)
        {
            if(!enterActivityName.getText().toString().equals(""))
            {
                Intent intent = new Intent(this,AddActivity.class);
                intent.putExtra(SystemContract.ActivityContract.Activity_NAME, enterActivityName.getText().toString());
                startActivity(intent);
            }
            else{Toast.makeText(this, "First enter the activity name", Toast.LENGTH_SHORT).show();}
        }
        else if ( v == allActivities)
        {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);

        }


    }

}
