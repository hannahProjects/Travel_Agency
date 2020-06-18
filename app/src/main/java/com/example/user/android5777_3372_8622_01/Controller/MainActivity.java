package com.example.user.android5777_3372_8622_01.Controller;

import android.app.*;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.android5777_3372_8622_01.Model.backend.DBManagerFactory;
import com.example.user.android5777_3372_8622_01.Model.backend.DB_manager;
import com.example.user.android5777_3372_8622_01.Model.backend.MyBroadcastReceiver;
import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract;
import com.example.user.android5777_3372_8622_01.Model.datasource.Tool;
import com.example.user.android5777_3372_8622_01.Model.entities.User;
import com.example.user.android5777_3372_8622_01.R;


public class MainActivity extends Activity implements View.OnClickListener
{
    DB_manager manager = DBManagerFactory.getInstance();
    Cursor cursor;

    private Button save;
    private Button load;
    private Button delete;
    private EditText nameEditText;
    private EditText passwordEditText;
    private Button log;
    private Button clear;

    private void findViews()
    {
        delete = (Button)findViewById( R.id.delete );
        save = (Button)findViewById( R.id.save );
        load = (Button)findViewById( R.id.load );
        log = (Button)findViewById(R.id.LogIn);
        clear = (Button)findViewById(R.id.clear);
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);

        save.setOnClickListener( this );
        load.setOnClickListener( this );
        delete.setOnClickListener( this );
        log.setOnClickListener( this );
        clear.setOnClickListener( this );
        //Toast.makeText(this, "CCC", Toast.LENGTH_SHORT).show();

        nameEditText.setText("");
        passwordEditText.setText("");
    }

    @Override
    public void onClick(View v)
    {
        if ( v == save )
        {
            saveSharedPreferences();
        } else if ( v == load ) {
            loadSharedPreferences();
        } else if ( v == delete ) {
            deleteSharedPreferences();
        } else if ( v == log ) {
            try {
                logSharedPreferences();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if ( v == clear)
            clearSharedPreferences();
    }

    private void clearSharedPreferences() {

        nameEditText.setText("");
        passwordEditText.setText("");
        Toast.makeText(this, "cleared", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver( new MyBroadcastReceiver(), new IntentFilter(Intent.ACTION_TIME_TICK));
        findViews();
    }


    private void logSharedPreferences() throws InterruptedException {
            final String name = nameEditText.getText().toString();
            final String password = passwordEditText.getText().toString();
            if (name.equals("") || password.equals("")) {
                Toast.makeText(this, "You should enter name and password first", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ListActivity.class);
                startActivity(intent);
            }
            else {

                final boolean[] f = {false};
                //User check = manager.findUserByNameAndPassword(nameEditText.getText().toString(), Integer.parseInt(passwordEditText.getText().toString()));
                try {
                    new AsyncTask<Object, Object, Void>() {
                        @Override
                        protected Void doInBackground(Object... voids) {
                            cursor = manager.getUsers();
                            f[0] = true;
                            return null;
                        }
                    }.execute();

                    while (f[0] == false)
                    {
                        Thread.sleep(1000);
                    }
                    cursor.moveToFirst();
                    int cName = cursor.getColumnIndex(SystemContract.UserContract.USER_NAME);
                    int cPassword = cursor.getColumnIndex(SystemContract.UserContract.USER_PASSWORD);
                    while (cursor.isAfterLast() == false)
                    {
                        if (cursor.getString(cName).equals(name) && cursor.getString(cPassword).equals(password))
                        {
                            Intent intent = new Intent(this, UserLogIn.class);
                            startActivity(intent);
                            break;
                        }
                        cursor.moveToNext();
                    }
                    if (cursor.isAfterLast() == true)
                        Toast.makeText(this, "the user doesn't exit", Toast.LENGTH_SHORT).show();


                }
                catch (Exception e)
                {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

                }
            }

    }

    private void saveSharedPreferences()  {
        if (nameEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals(""))
        {
            Toast.makeText(this, "You should enter name and password first", Toast.LENGTH_SHORT).show();
        }
        else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("UserName", nameEditText.getText().toString());
            editor.putString("UserPassword", passwordEditText.getText().toString());
            editor.commit();

            try
            {
                Integer.parseInt(passwordEditText.getText().toString());
                final User user = new User(nameEditText.getText().toString(), Integer.parseInt(passwordEditText.getText().toString()));
                new AsyncTask<Object, Object, Void>()
                {
                    @Override
                    protected Void doInBackground(Object... voids) {
                        manager.addUser(Tool.UserToContentValues(user));
                        return null;
                    }
                }.execute();
                Toast.makeText(this, "The user name was successfully saved", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void deleteSharedPreferences() {
        if (nameEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals("")) {
            Toast.makeText(this, "You should enter name and password first", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();

            User u = manager.findUserByNameAndPassword(nameEditText.getText().toString(), Integer.parseInt(passwordEditText.getText().toString()));
            if (u != null) {
                manager.deleteUser(u.getId());
            } else
                Toast.makeText(this, "The User Is Not Exist", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.contains("UserName"))
        {
            //not putting the string back in the nameEditText
            nameEditText.setText(sharedPreferences.getString("UserName", null));
            Toast.makeText(this, "load name", Toast.LENGTH_SHORT).show();
        }
        if (sharedPreferences.contains("UserPassword"))
        {
            String tmp = sharedPreferences.getString("UserPassword", null);
            passwordEditText.setText(tmp);//problem whit the "int" getting
            Toast.makeText(this, "load password", Toast.LENGTH_SHORT).show();

            //Integer.valueOf(editText.getText())
            //Toast.makeText(this, temp, Toast.LENGTH_SHORT).show();
        }


    }



}
