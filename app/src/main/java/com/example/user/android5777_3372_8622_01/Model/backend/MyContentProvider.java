package com.example.user.android5777_3372_8622_01.Model.backend;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.user.android5777_3372_8622_01.Model.datasource.Tool;
import com.example.user.android5777_3372_8622_01.Model.entities.Activity;
import com.example.user.android5777_3372_8622_01.Model.entities.Business;
import com.example.user.android5777_3372_8622_01.Model.entities.User;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by user on 12/12/2016.
 *
 * this class is the content provider of the project/ its wraps every value with "content provider wrapping" so the value can be protected
 */

public class MyContentProvider extends ContentProvider
{
    DB_manager manager;//helps us control the values
    public MyContentProvider() //constructor
    {
        manager = DBManagerFactory.getInstance();
    }

    final String TAG = "travelAgencyContent";//TAG for the log massages

    @Override// function that delete an item from the content provider. the funtion gets the uri of the key that we want to remove
    //and the strings that we want to remove.
    //the function returns how many items was removed from the content provider
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        Log.d(TAG, "delete " + uri.toString());

        String listName = uri.getLastPathSegment();// takes the last path segmant to see which kind of item to remove
        long id = ContentUris.parseId(uri);//  the id of the item
        switch (listName) {
            case "business": //if its a business
                if (manager.deleteBusiness(id))
                    return 1;
                break;

            case "users":  //if its a user
                if (manager.deleteUser(id))
                    return 1;
                break;
            case "activities":  //if its a activity
                if (manager.deleteActivity((int) id))
                    return 1;
                break;
        }
        return 0;
    }


    @Override  //function that used to ask ask about the entities of the uri
    public String getType(Uri uri)
    {
        Log.d(TAG, "getType " + uri.toString());
        return null;
    }

    @Override  // function that adds another content value to the given uri
    public Uri insert(Uri uri, ContentValues values)
    {

        Log.d(TAG, "insert " + uri.toString());

        String listName = uri.getLastPathSegment();
        final long[] id = {-1};
        switch (listName) {
            case "Business":  // case that is a business
                new AsyncTask<ContentValues, Void, Integer>()
                {
                    @Override
                    protected Integer doInBackground(ContentValues... params) {
                       return manager.addBusiness(params[0]);
                    }

                    @Override
                    protected void onPostExecute(Integer integer) {
                        super.onPostExecute(integer);
                        id[0] = integer;
                    }
                }.execute(values);

                return ContentUris.withAppendedId(uri, id[0]);

            case "User":  // case that is an user

                new AsyncTask<ContentValues, Void, Long>()
                {
                    @Override
                    protected Long doInBackground(ContentValues... params) {
                        return manager.addUser(params[0]);
                    }

                    @Override
                    protected void onPostExecute(Long aLong) {
                        super.onPostExecute(aLong);
                        id[0] = aLong;
                    }
                }.execute(values);

                return ContentUris.withAppendedId(uri, id[0]);

            case "Activity":  // case that is an activity
                    new AsyncTask<ContentValues, Void, Integer>()
                    {
                        @Override
                        protected Integer doInBackground(ContentValues... params)
                        {
                            try
                            {
                                return manager.addActivity(params[0]);
                            }
                            catch (ParseException e)
                            {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Integer integer) {
                            super.onPostExecute(integer);
                            id[0] = integer;
                        }
                    }.execute(values);
                    return ContentUris.withAppendedId(uri, id[0]);
                //return ContentUris.withAppendedId(uri, id[0]);

        }
        return null;
    }

    @Override  //when the content value class create
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        return false;
    }

    @Override  //querying the content provider about the type that we want
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Log.d(TAG, "query " + uri.toString());

        String listName = uri.getLastPathSegment();
        switch (listName) {
            case "business"://if it is business
                return manager.getBusiness();//returns all the business' as cursor

            case "users":  //if it is users
                return manager.getUsers();  //returns all the users as cursor

            case "activities":  //if it is activities
                return manager.getActivities();  //returns all the activities as cursor
        }
        return null;

    }

    @Override   // function that updates a content value to the given uri
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update " + uri.toString());

        String listName = uri.getLastPathSegment();
        long id = ContentUris.parseId(uri);
        switch (listName) {
            case "business":  // case that is a business
                if (manager.updateBusiness((int)id, values))
                    return 1;
                break;

            case "users":  // case that is a user
                if (manager.updateUser(id, values))
                    return 1;
                break;


            case "activities":  // case that is a avtivity
                try
                {
                    if (manager.updateActivity((int) id, values))
                        return 1;
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                break;
        }
        return 0;  //if something went wrong
    }
}
