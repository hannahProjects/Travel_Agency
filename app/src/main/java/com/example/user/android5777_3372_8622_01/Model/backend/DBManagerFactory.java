package com.example.user.android5777_3372_8622_01.Model.backend;

import com.example.user.android5777_3372_8622_01.Model.datasource.List_DB_manager;
import com.example.user.android5777_3372_8622_01.Model.datasource.MySQL_DB_manager;

/**
 * Created by user on 12/12/2016.
 *
 * factory method for the DB_manager
 */

public class DBManagerFactory
{
    private static DB_manager instance = null;
    public static DB_manager getInstance()
    {
        if (instance == null) //if the bata base has not created yet
            instance = new MySQL_DB_manager();// create it and return it
        return instance;  //if it is was created so return the created one
    }
}
