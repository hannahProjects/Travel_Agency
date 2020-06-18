package com.example.user.android5777_3372_8622_01.Model.entities;

import android.os.Bundle;

import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract;
import com.example.user.android5777_3372_8622_01.Model.datasource.List_DB_manager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.user.android5777_3372_8622_01.Model.datasource.List_DB_manager.activityList;

/**
 * Created by user on 12/12/2016.
 *
 * class that represent activity of a business.
 */

public class Activity
{
    int key;  //serial number that the user have no control at. it's a key
    String name; // the name of the activity. it is also a key.
    ActivityKinds activity; // the activity can be only from an enum kind.
    String activityCountry;  //the county of the activity
    Date activityStartDate;  //the starting date of the activity
    Date activityEndDate;  //the ending date of the activity
    float activityPrice;  // the price of the activity
    String activityDescription;  // short description about the activity
    int activityBusinessId;  // the business' id that the activity belong to.

    //constructors
    public Activity()
    {
        key = SystemContract.activityKey;
        name = null;
        activity = ActivityKinds.HotelVacation;
        activityCountry = null;
        activityPrice = 0;
        activityDescription = null;
        activityBusinessId = 0;
        SystemContract.activityKey++;
    }
    public Activity(String _name, String _activity,Date _activityStartDate , Date _activityEndDate
            ,String _activityCountry, float _activityPrice, String _activityDescription, int _activityBusinessId)
    {
        key = SystemContract.activityKey;
        name = _name;
        setFromStringToActivityKind(_activity);
        activityStartDate = _activityStartDate;
        activityEndDate = _activityEndDate;
        activityCountry = _activityCountry;
        activityPrice = _activityPrice;
        activityDescription = _activityDescription;
        activityBusinessId = _activityBusinessId;
        SystemContract.activityKey++;
    }

    //setters and getters
    public String getName() { return name; }
    public void setName(String n) { name=n; }

    public ActivityKinds getActivity() {
        return activity;
    }
    public void setActivity(ActivityKinds _activity) {
        this.activity = _activity;
    }
    public void setFromStringToActivityKind(String activity)
    {
        if(activity.equals("HotelVacation"))
            this.activity = ActivityKinds.HotelVacation;
        if(activity.equals("TravelAgency"))
            this.activity = ActivityKinds.TravelAgency;
        if(activity.equals("Entertainment"))
            this.activity = ActivityKinds.Entertainment;
        if(activity.equals("Airline"))
            this.activity = ActivityKinds.Airline;
    }

    public String getActivityCountry() {
        return activityCountry;
    }
    public void setActivityCountry(String _activityCountry) {activityCountry = _activityCountry;}

    public Date getActivityStartDate() {
        return activityStartDate;
    }
    public void setActivityStartDate(Date _activityStartDate) {activityStartDate = _activityStartDate;}

    public Date getActivityEndDate() {
        return activityEndDate;
    }
    public void setActivityEndDate(Date _activityEndDate) {
        activityEndDate = _activityEndDate;
    }

    public float getActivityPrice() {
        return activityPrice;
    }
    public void setActivityPrice(float _activityPrice) {
        activityPrice = _activityPrice;
    }

    public String getActivityDescription() {
        return activityDescription;
    }
    public void setActivityDescription(String _activityDescription) {activityDescription = _activityDescription;}

    public int getActivityBusinessId() {
        return activityBusinessId;
    }
    public void setActivityBusinessId(int _activityBusinessId) {activityBusinessId = _activityBusinessId;}


    // functions
    public int convertNameToKey(String n)
    {
        for (Activity activity: activityList) {
            if (activity.name.equals(n))
                return activity.key;
        }
        return -1;
    }


    public int getKey() {
        return key;
    }


}


/*
 public void setActivityStartDateFromString(String activityStartDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = formatter.parse(activityStartDate);
        activityStartDate = date;
    }
    public void setActivityEndDateFromString(String activityEndDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = formatter.parse(activityEndDate);
        activityStartDate = date;
    }*/
