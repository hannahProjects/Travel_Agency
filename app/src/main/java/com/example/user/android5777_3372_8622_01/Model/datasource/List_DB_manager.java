package com.example.user.android5777_3372_8622_01.Model.datasource;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.user.android5777_3372_8622_01.Model.backend.DB_manager;
import com.example.user.android5777_3372_8622_01.Model.entities.Activity;
import com.example.user.android5777_3372_8622_01.Model.entities.Business;
import com.example.user.android5777_3372_8622_01.Model.entities.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

/**
 * Created by user on 12/12/2016.
 *
 * class that inherits the DB_manager interface to handle the data base
 * the class also extends and using asyncTask to do short things in the background
 */

public class List_DB_manager extends AsyncTask<Double, Integer, Void> implements DB_manager
{
    private static final String TAG = "DB_manager";//TAG for the log massages
    //all the lists of the entities
    public static ArrayList<Business> businessList;
    public static ArrayList<User> userList;
    public static ArrayList<Activity> activityList;

    //boolean object to check if something had recently updated
    public boolean isUpdate = false;

    //constructor
    public List_DB_manager() {
        this.businessList = new ArrayList<Business>();
        this.userList = new ArrayList<User>();
        this.activityList = new ArrayList<Activity>();
    }

    @Override //the AsyncTask. function that activate when the asyncTask is activate. every second publish that 1 sec has past
    protected Void doInBackground(Double... doubles) {
        Double time = doubles[0];

        while (time < 0 && !isCancelled())//if the progress has not cancelled and still running
        {
            time--;
            try {Thread.sleep(1000);}
            catch (InterruptedException e) {e.printStackTrace();}
            publishProgress(1);
        }
        return  null;

    }

    @Override//gets a content value to add to the users list
    public long addUser(ContentValues user) {
        User user1 = Tool.ContentValuesToUser(user);//convert the content value back to user
        userList.add(user1);//add the user to the list
        isUpdate=true;  // informer about new user
        return user1.getId(); //returns the id of the user.
    }

    @Override//gets a content value to add to the business' list
    public int addBusiness(ContentValues business)
    {
        Business business1 = Tool.ContentValuesToBusiness(business);//convert the content value back to business
        businessList.add(business1);//add the business to the list
        isUpdate=true;  // informer about new business
        return business1.getBusinessId(); //returns the id of the business.
    }

    @Override //gets a content value to add to the activities list
    public int addActivity(ContentValues activity) throws ParseException
    {
        Activity Activity1 = Tool.ContentValuesToActivity(activity); //convert the content value back to activity
        activityList.add(Activity1);  //add the activity to the list
        isUpdate = true;  // informer about new activity
        return Activity1.getKey();
    }

    @Override //gets an id to delete this user from the list
    public boolean deleteUser(long id)
    {
        boolean deleted = false;//flag to check if the item was deleted correctly
        User userToRemove = new User();//will contain the item that we want to remove
        for (User item : userList) //loop that goes over the whole list to check if the item is there
            if (item.getId() == id)//if it is there
            {
                userToRemove = item;//this item will be remove
                isUpdate = true; //informer about some update
                deleted = true;
                break;//stop the loop
            }
        if (deleted)
            return userList.remove(userToRemove);//delete the item from the list
        return false;//if it is not deleted the function will return false
    }

    @Override  //gets an id to delete this business from the list
    public boolean deleteBusiness(long id)
    {
        boolean deleted = false;
        Business businessToRemove = null; //will contain the item that we want to remove
        for (Business item : businessList)  //loop that goes over the whole list to check if the item is there
            if (item.getBusinessId() == id)  //if it is there
            {
                businessToRemove = item;  //this item will be remove
                isUpdate = true;  //informer about some update
                deleted = true;
                break;  //stop the loop
            }
        if(deleted)
                return businessList.remove(businessToRemove);  //delete the item from the list
        return false;
    }

    @Override  //gets an id to delete this activity from the list
    public boolean deleteActivity(int key)
    {
        boolean deleted = false;
        Activity activityToRemove = null;  //will contain the item that we want to remove
        for (Activity item : activityList )   //loop that goes over the whole list to check if the item is there
            if (item.getKey() == key)  //if it is there
            {
                activityToRemove = item;  //this item will be remove
                isUpdate = true;   //informer about some update
                deleted = true;
                break;  //stop the loop
            }
        if(deleted)
         activityList.remove(activityToRemove);  //delete the item from the list
        return false;
    }

    @Override  //gets an id and a content value of the item to update this user in the list. return if it has been done correctly
    public boolean updateUser(long id, ContentValues user)
    {
        User user1 = Tool.ContentValuesToUser(user);  //convert the content value back to user
        for (int i = 0; i < userList.size(); i++)  //loop that goes over the whole list to check if the item is there
            if (userList.get(i).getId() == id)   //if it is there
            {
                userList.get(i).setName(user1.getName());  //update the name and the password
                userList.get(i).setPassword(user1.getPassword());
                isUpdate = true; //informer about some update
                return true;  //the update done!
            }
        return false;

    }

    @Override  //gets an id and a content value of the item to update this business in the list. return if it has been done correctly
    public boolean updateBusiness(int id, ContentValues business)
    {
        Business business1 = Tool.ContentValuesToBusiness(business);   //convert the content value back to business
        for (int i = 0; i < businessList.size(); i++)  //loop that goes over the whole list to check if the item is there
            if (businessList.get(i).getBusinessId() == id) //if it is there
            {
                //update all of the properties
                businessList.get(i).setBusinessName(business1.getBusinessName());
                businessList.get(i).setBusinessAddress(business1.getBusinessAddress());
                businessList.get(i).setBusinessAddress(business1.getBusinessAddress());
                businessList.get(i).setBusinessPhoneNumber(business1.getBusinessPhoneNumber());
                businessList.get(i).setBusinessEMail(business1.getBusinessEMail());
                businessList.get(i).setBusinessWebsite(business1.getBusinessWebsite());
                isUpdate = true; //informer about some update
                return true;  //the update done!
            }

        return false;  //if the update could't be done return that

    }


    @Override   //gets an key of the item to update this activity in the list. return if it has been done correctly
    public boolean updateActivity(int key, ContentValues activity) throws ParseException {
        Activity activity1 = Tool.ContentValuesToActivity(activity);   //convert the content value back to businessActivity activityToRemove = null;
        for (Activity item : activityList )    //loop that goes over the whole list to check if the item is there
            if (item.getKey() == key)   //if it is there
            {
                //update all of the properties
                item.setName(activity1.getName());
                item.setActivity(activity1.getActivity());
                item.setActivityCountry(activity1.getActivityCountry());
                item.setActivityStartDate(activity1.getActivityStartDate());
                item.setActivityEndDate(activity1.getActivityEndDate());
                item.setActivityPrice(activity1.getActivityPrice());
                item.setActivityDescription(activity1.getActivityDescription());
                item.setActivityBusinessId(activity1.getActivityBusinessId());
                isUpdate = true; //informer about some update
                return true; //the update done!
            }
        return false;   //if the update could't be done return that
    }


    @Override
    public List<User> allTheUsers() {
        return userList;
    }

    @Override
    public List<Business> allTheBusiness() {
        return businessList;
    }

    @Override
    public List<Activity> allTheActivity() {
        return activityList;
    }

    @Override
    public Cursor getBusiness() {
        return Tool.BusinessListToCursor(businessList);
    }

    @Override
    public Cursor getUsers() {
        return Tool.UserListToCursor(userList);
    }

    @Override
    public Cursor getActivities() {
        return Tool.ActivityListToCursor(activityList);
    }

    @Override
    public Activity findActivityWithName(String n)
    {
        for (Activity activity: activityList)
        {
            if (activity.getName().equals(n))
                return activity;
        }

        return null;
    }

    @Override
    public Business findBusinessWithId(int id)
    {
        for (Business business: businessList)
        {
            if (business.getBusinessId() == id)
                return business;
        }
        return null;
    }

    @Override
    public Business findBusinessWithName(String name)
    {
        for (Business business: businessList)
        {
            if (business.getBusinessName().equals(name))
                return business;
        }
        return null;
    }

    /*@Override
    public Activity findActivityByName(String name)
    {
        for (Activity activity: activityList)
        {
            if ( activity.getName().equals(name))
                return activity;
        }
        return null;
    }*/

    @Override
    public User findUserByNameAndPassword(String name, int password)
    {
        for (User u:userList)
        {
            System.out.println(u.getName() + " " + u.getPassword());
            System.out.println(name + " " + password);
            if (u.getPassword() == password)
                if (u.getName().equals(name.toString()))
                    return u;
        }
        return null;
    }

    @Override
    public boolean isBusinessExists(int id, String name)
    {
        for (Business business: businessList)
        {
            if (business.getBusinessId() == id && business.getBusinessName().equals(name))
                return true;
        }
        return false;
    }

    @Override
    public boolean isActivityExists(String name)
    {
        for (Activity activity: activityList)
            if (activity.getName().equals(name))
                return true;
        return false;
    }

    @Override
    public boolean ifUpdated() //function that checks if something has changed recently
    {
        if (isUpdate)
            return true;
        return false;
    }

    @Override
    public void toUpdate(boolean b) {isUpdate = b;} //function the set te update state

    @Override
    public ArrayList<String> allBusinessNames() {
        return null;
    }
}
