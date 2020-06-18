package com.example.user.android5777_3372_8622_01.Model.entities;

import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract;

/**
 * Created by user on 12/12/2016.
 *
 * class that represent a user
 */

public class User
{
    long id;// the id of the user is a serial number that the user have no control at
    String name;// the name of the user
    int password;//the password of the user for the program


    //constructors
    public User(String name, int password)
    {
        this.id = SystemContract.userKey;
        this.name = name;
        this.password = password;
        SystemContract.userKey++;
    }
    public User()
    {
        this.id = SystemContract.userKey;
        this.name = null;
        this.password = 0;

        SystemContract.userKey++;
    }

    //getters and setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPassword() {
        return password;
    }
    public void setPassword(int password) {
        this.password = password;
    }

}
