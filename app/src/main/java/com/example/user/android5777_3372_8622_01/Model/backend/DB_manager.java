package com.example.user.android5777_3372_8622_01.Model.backend;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.user.android5777_3372_8622_01.Model.entities.Activity;
import com.example.user.android5777_3372_8622_01.Model.entities.Business;
import com.example.user.android5777_3372_8622_01.Model.entities.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12/12/2016.
 *
 * interface that will contain all the function to handle the data base
 */

public interface DB_manager
{
    //function to add an entity: user, business or activity. the function returns the key of the added object
    public long addUser(ContentValues user);
    public int addBusiness(ContentValues business);
    public int addActivity(ContentValues activity) throws ParseException;

    //function to delete an entity: user, business or activity. the function returns if the object deleted succesfuly
    public boolean deleteUser(long id);
    public boolean deleteBusiness(long id);
    public boolean deleteActivity(int key);

    //function to update an entity: user, business or activity. the function returns if the object updated succesfuly
    public boolean updateUser(long id, ContentValues user);
    public boolean updateBusiness(int id, ContentValues business);
    public boolean updateActivity(int key, ContentValues activity) throws ParseException;

    public List<User> allTheUsers();
    public List<Business> allTheBusiness ();
    public List<Activity> allTheActivity();

    //function that returns all the lists as cursors
    public Cursor getBusiness();
    public Cursor getUsers();
    public Cursor getActivities();

    //funtions to find single item with property
    public Activity findActivityWithName(String n);
    public Business findBusinessWithId(int id);
    public Business findBusinessWithName(String name);
    public User findUserByNameAndPassword (String name, int password);

    public boolean isBusinessExists(int id,String name); // check if the business exists by name & id return boolean answer

    public boolean ifUpdated();//checks something updated lately

    public boolean isActivityExists(String name); // check if the activity exists by name return boolean answer

    public void toUpdate(boolean b);//update the status of the boolean update value

    public ArrayList<String> allBusinessNames ();
}
