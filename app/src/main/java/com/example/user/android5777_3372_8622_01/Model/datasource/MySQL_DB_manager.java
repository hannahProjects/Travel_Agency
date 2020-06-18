package com.example.user.android5777_3372_8622_01.Model.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;

import com.example.user.android5777_3372_8622_01.Model.backend.DB_manager;
import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract;
import com.example.user.android5777_3372_8622_01.Model.entities.Activity;
import com.example.user.android5777_3372_8622_01.Model.entities.Address;
import com.example.user.android5777_3372_8622_01.Model.entities.Business;
import com.example.user.android5777_3372_8622_01.Model.entities.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 19/01/2017.
 */

public class MySQL_DB_manager implements DB_manager
{
    private final String UserName="bugala";
    private final String web_url = "http://bugala.vlab.jct.ac.il/";
    public boolean isUpdate = false;

    @Override
    public long addUser(ContentValues user)
    {
        try {
            //String i = PHPtools.GET(web_url + "query_user.php");
            String result = PHPtools.POST(web_url + "add_user.php", user);
            int id = result.length();
            if (id > 0)
                isUpdate=true;
            //printLog("addUser:\n" + result);
            return (long)id;
        } catch (IOException e) {
            //printLog("addUser Exception:\n" + e);
            return -1;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int addBusiness(ContentValues business)
    {
        try {
            String result = PHPtools.POST(web_url + "add_business.php", business);
            String re = result;
            if (re != null)
                isUpdate=true;
            //printLog("addUser:\n" + result);
            return 1;
        } catch (IOException e) {
            //printLog("addUser Exception:\n" + e);
            return -1;
        }
    }

    @Override
    public int addActivity(ContentValues activity) throws ParseException
    {
        try {
            String result = PHPtools.POST(web_url + "add_activity.php", activity);
            //int id = Integer.parseInt(result);
            if (result != null)
                isUpdate = true;
            //printLog("addUser:\n" + result);
            return 1;
        } catch (IOException e) {
            //printLog("addUser Exception:\n" + e);
            return -1;
        }

    }

    @Override
    public boolean deleteUser(long id) {
        return false;
    }

    @Override
    public boolean deleteBusiness(long id) {
        return false;
    }

    @Override
    public boolean deleteActivity(int key) {
        return false;
    }

    @Override
    public boolean updateUser(long id, ContentValues user) {
        return false;
    }

    @Override
    public boolean updateBusiness(int id, ContentValues business) {
        return false;
    }

    @Override
    public boolean updateActivity(int key, ContentValues activity) throws ParseException {
        return false;
    }

    @Override
    public List<User> allTheUsers() {
        return null;
    }

    @Override
    public List<Business> allTheBusiness() {
        return null;
    }

    @Override
    public List<Activity> allTheActivity() {
        return null;
    }

    @Override
    public Cursor getBusiness() {
        try {
            MatrixCursor matrixCursor = new MatrixCursor(new String[]
                    {
                            SystemContract.BusinessContract.BUSINESS_ID,
                            SystemContract.BusinessContract.BUSINESS_NAME,
                            SystemContract.BusinessContract.BUSINESS_ADDRESS,
                            SystemContract.BusinessContract.BUSINESS_PHONE,
                            SystemContract.BusinessContract.BUSINESS_EMAIL,
                            SystemContract.BusinessContract.BUSINESS_WEBSITE

                    });

            String str = PHPtools.GET(web_url + "query_business.php");
            JSONArray array = new JSONObject(str).getJSONArray("Business`");

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = null;

                jsonObject = array.getJSONObject(i);
                matrixCursor.addRow(new Object[]{
                        jsonObject.getInt(SystemContract.BusinessContract.BUSINESS_ID),
                        jsonObject.getString(SystemContract.BusinessContract.BUSINESS_NAME),
                        jsonObject.getString(SystemContract.BusinessContract.BUSINESS_ADDRESS),
                        jsonObject.getString(SystemContract.BusinessContract.BUSINESS_PHONE),
                        jsonObject.getString(SystemContract.BusinessContract.BUSINESS_EMAIL),
                        jsonObject.getString(SystemContract.BusinessContract.BUSINESS_WEBSITE)
                });
            }         return matrixCursor;
        } catch (Exception e) {
            e.printStackTrace();
        }     return null;

    }

    @Override
    public Cursor getUsers() {
        try {
            MatrixCursor matrixCursor = new MatrixCursor(new String[]
                    {
                            SystemContract.UserContract.User_ID,
                            SystemContract.UserContract.USER_NAME,
                            SystemContract.UserContract.USER_PASSWORD
                    });

            String str = PHPtools.GET(web_url + "query_user.php");
            JSONArray array = new JSONObject(str).getJSONArray("users");

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = null;

                jsonObject = array.getJSONObject(i);
                matrixCursor.addRow(new Object[]{
                        jsonObject.getInt(SystemContract.UserContract.User_ID),
                        jsonObject.getString(SystemContract.UserContract.USER_NAME),
                        jsonObject.getString(SystemContract.UserContract.USER_PASSWORD)
                });
            }
            return matrixCursor;
        }
        catch (Exception e) {
            e.printStackTrace();
        }     return null;

    }

    @Override
    public Cursor getActivities() {
        try {
            MatrixCursor matrixCursor = new MatrixCursor(new String[]
                    {
                            SystemContract.ActivityContract.Activity_KEY,
                            SystemContract.ActivityContract.Activity_NAME,
                            SystemContract.ActivityContract.Activity_KIND,
                            SystemContract.ActivityContract.Activity_COUNTRY,
                            SystemContract.ActivityContract.Activity_START,
                            SystemContract.ActivityContract.Activity_END,
                            SystemContract.ActivityContract.Activity_PRICE,
                            SystemContract.ActivityContract.Activity_DESCRIPTION,
                            SystemContract.ActivityContract.Activity_BUSINESS_ID
                    });
            String str = PHPtools.GET(web_url + "query_activity.php");
            JSONArray array = new JSONObject(str).getJSONArray("activities");

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = null;

                jsonObject = array.getJSONObject(i);
                matrixCursor.addRow(new Object[]{
                        jsonObject.getInt(SystemContract.ActivityContract.Activity_KEY),
                        jsonObject.getString(SystemContract.ActivityContract.Activity_NAME),
                        jsonObject.getString(SystemContract.ActivityContract.Activity_KIND),
                        jsonObject.getString(SystemContract.ActivityContract.Activity_COUNTRY),
                        jsonObject.getString(SystemContract.ActivityContract.Activity_START),
                        jsonObject.getString(SystemContract.ActivityContract.Activity_END),
                        jsonObject.getInt(SystemContract.ActivityContract.Activity_PRICE),
                        jsonObject.getString(SystemContract.ActivityContract.Activity_DESCRIPTION),
                        jsonObject.getInt(SystemContract.ActivityContract.Activity_BUSINESS_ID)
                });
            }
            return matrixCursor;
        } catch (Exception e) {
            e.printStackTrace();
        }     return null;

    }

    @Override
    public Activity findActivityWithName(String n) {
        Cursor cursor = getActivities();
        try {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                if (cursor.getString(cursor.getColumnIndex(SystemContract.ActivityContract.Activity_NAME)).equals(n)) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    return new Activity(cursor.getString(cursor.getColumnIndex(SystemContract.ActivityContract.Activity_NAME)),
                            cursor.getString(cursor.getColumnIndex(SystemContract.ActivityContract.Activity_KIND)),
                            dateFormat.parse(cursor.getString(cursor.getColumnIndex(SystemContract.ActivityContract.Activity_START))),
                            dateFormat.parse(cursor.getString(cursor.getColumnIndex(SystemContract.ActivityContract.Activity_END))),
                            cursor.getString(cursor.getColumnIndex(SystemContract.ActivityContract.Activity_COUNTRY)),
                            cursor.getFloat(cursor.getColumnIndex(SystemContract.ActivityContract.Activity_PRICE)),
                            cursor.getString(cursor.getColumnIndex(SystemContract.ActivityContract.Activity_DESCRIPTION)),
                            cursor.getInt(cursor.getColumnIndex(SystemContract.ActivityContract.Activity_BUSINESS_ID)));

                }
                cursor.moveToNext();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Business findBusinessWithId(int id) {
        Cursor cursor = getBusiness();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_ID)).equals(id) )
            {
                String []tmp = cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_ADDRESS)).split(" ");
                return new Business(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_ID))),
                        cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_NAME)),
                        new Address (tmp[0], tmp[1], tmp[2], Integer.parseInt(tmp[3])),
                        cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_PHONE)),
                        cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_WEBSITE)));
            }
            cursor.moveToNext();
        }
        return null;
    }

    @Override
    public Business findBusinessWithName(String name) {

        Cursor cursor = getBusiness();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_NAME)).equals(name) )
            {
                String []tmp = cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_ADDRESS)).split(" ");
                return new Business(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_ID))),
                        cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_NAME)),
                        new Address (tmp[0], tmp[1], tmp[2], Integer.parseInt(tmp[3])),
                        cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_PHONE)),
                        cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_WEBSITE)));
            }
            cursor.moveToNext();
        }
        return null;
    }

    @Override
    public User findUserByNameAndPassword(String name, int password)
    {

        Cursor cursor = getUsers();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (cursor.getString(cursor.getColumnIndex(SystemContract.UserContract.USER_NAME)).equals(name) &&
                    cursor.getString(cursor.getColumnIndex(SystemContract.UserContract.USER_PASSWORD)).equals(password + ""))
                return new User(name, password);
            cursor.moveToNext();
        }
        return null;
    }

    @Override
    public boolean isBusinessExists(int id, String name) {
        return false;
    }

    @Override
    public boolean ifUpdated() {
        return false;
    }

    @Override
    public boolean isActivityExists(String name) {
        Cursor cursor = getActivities();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (cursor.getString(cursor.getColumnIndex(SystemContract.ActivityContract.Activity_NAME)).equals(name))
                return true;
            cursor.moveToNext();
        }
        return false;
    }

    @Override
    public void toUpdate(boolean b) {    }

    @Override
    public ArrayList<String> allBusinessNames()
    {
        Cursor cursor = getBusiness();
        ArrayList<String> arry = new ArrayList();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false)
        {
            arry.add(cursor.getString(cursor.getColumnIndex(SystemContract.BusinessContract.BUSINESS_NAME)));
            cursor.moveToNext();
        }
        return arry;
    }


}
