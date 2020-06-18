package com.example.user.android5777_3372_8622_01.Model.datasource;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;

import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract;
import com.example.user.android5777_3372_8622_01.Model.entities.Activity;
import com.example.user.android5777_3372_8622_01.Model.entities.Address;
import com.example.user.android5777_3372_8622_01.Model.entities.Business;
import com.example.user.android5777_3372_8622_01.Model.entities.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by user on 12/12/2016.
 *
 * class for the data base to convert between content value, entities, cursor exc.
 **/

public class Tool
{

    // convert entity to contentValue
    public static ContentValues UserToContentValues(User user) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("Id", user.getId());
        contentValues.put("UserName", user.getName());
        contentValues.put("Password", user.getPassword());

        return contentValues;
    }

    public static ContentValues BusinessToContentValues(Business business) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("Id", business.getBusinessId());
        contentValues.put("Name", business.getBusinessName());
        contentValues.put("BusinessAddress", business.getBusinessAddress().toString());
        contentValues.put("PhoneNumber", business.getBusinessPhoneNumber());
        contentValues.put("Email", business.getBusinessEMail());
        contentValues.put("Website", business.getBusinessWebsite());

        return contentValues;
    }

    public static ContentValues ActivityToContentValues (Activity _activity)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDateFormat = dateFormat.format(_activity.getActivityStartDate());
        String endDateFormat = dateFormat.format(_activity.getActivityEndDate());

        ContentValues contentValues = new ContentValues();
        contentValues.put(SystemContract.ActivityContract.Activity_NAME, _activity.getName());
        contentValues.put(SystemContract.ActivityContract.Activity_KIND, _activity.getActivity().toString());
        contentValues.put(SystemContract.ActivityContract.Activity_COUNTRY, _activity.getActivityCountry());
        contentValues.put(SystemContract.ActivityContract.Activity_START, startDateFormat);
        contentValues.put(SystemContract.ActivityContract.Activity_END, endDateFormat);
        contentValues.put(SystemContract.ActivityContract.Activity_PRICE, _activity.getActivityPrice());
        contentValues.put(SystemContract.ActivityContract.Activity_DESCRIPTION, _activity.getActivityDescription());
        contentValues.put(SystemContract.ActivityContract.Activity_BUSINESS_ID, _activity.getActivityBusinessId());

        return contentValues;
    }

    //convert content value to entity
    public static User ContentValuesToUser(ContentValues contentValues) {

        User user = new User();
        user.setId(contentValues.getAsLong("Id"));
        user.setName(contentValues.getAsString("Name"));
        user.setPassword(contentValues.getAsInteger("Password"));

        return user;
    }

    public static Business ContentValuesToBusiness(ContentValues values) {
        Business business = new Business();
        business.setBusinessId(values.getAsInteger("Id"));
        business.setBusinessName(values.getAsString("Name"));
        business.setFromStringToAddress(values.getAsString("Address"));
        business.setBusinessPhoneNumber(values.getAsString("PhoneNumber"));
        business.setBusinessEMail(values.getAsString("Email"));
        business.setBusinessWebsite(values.getAsString("Website"));

        return business;
    }

    public static Activity ContentValuesToActivity(ContentValues values) throws ParseException {
        Activity activity = new Activity();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = values.getAsString(SystemContract.ActivityContract.Activity_START);
        String endDateString = values.getAsString(SystemContract.ActivityContract.Activity_END);

        activity.setName(values.getAsString(SystemContract.ActivityContract.Activity_NAME));
        activity.setActivityStartDate(dateFormat.parse(startDateString));
        activity.setActivityEndDate(dateFormat.parse(endDateString));
        activity.setFromStringToActivityKind(values.getAsString(SystemContract.ActivityContract.Activity_KIND));
        activity.setActivityCountry(values.getAsString(SystemContract.ActivityContract.Activity_COUNTRY));
        activity.setActivityDescription(values.getAsString(SystemContract.ActivityContract.Activity_DESCRIPTION));
        activity.setActivityPrice(values.getAsFloat(SystemContract.ActivityContract.Activity_PRICE));
        activity.setActivityBusinessId(values.getAsInteger(SystemContract.ActivityContract.Activity_BUSINESS_ID));

        return activity;


    }

    //convert lists to cursor
    public static Cursor UserListToCursor(ArrayList<User> users) {
        String[] columns = new String[]{"_id", "name", "password"};

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (User u : users) {
            matrixCursor.addRow(new Object[]{u.getId(), u.getName(), u.getPassword()});
        }

        return matrixCursor;
    }

    public static Cursor BusinessListToCursor(ArrayList<Business> businesses) {
        String[] columns = new String[]{"_id", "name", "address", "phoneNumber", "email", "website"};

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Business b : businesses) {
            matrixCursor.addRow(new Object[]{b.getBusinessId(), b.getBusinessName(),
                    b.getBusinessAddress(), b.getBusinessPhoneNumber(),
                    b.getBusinessEMail(), b.getBusinessWebsite()});
        }

        return matrixCursor;
    }

    public static Cursor ActivityListToCursor(ArrayList<Activity> activities) {
        String[] columns = new String[]{"Kind", "Country", "Start", "End", "Price", "Description", "BusinessId"};

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Activity a : activities) {
            matrixCursor.addRow(new Object[]{a.getActivity(), a.getActivityCountry(),
                    a.getActivityStartDate(), a.getActivityEndDate(),
                    a.getActivityPrice(), a.getActivityDescription(), a.getActivityBusinessId()});
        }

        return matrixCursor;
    }


}
