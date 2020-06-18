package com.example.user.android5777_3372_8622_01.Model.backend;

import android.net.Uri;

/**
 * Created by user on 13/12/2016.
 *
 *class of all the declaration for a cleaner and nicer code view
 */

public class SystemContract
{
    //static parameters for the entities
    public static int activityKey = 1;
    public static int userKey = 1;
    /**
     * The authority for the contacts provider
     */
    public static final String AUTHORITY = "com.example.user.android5777_3372_8622_01";
    /**
     * A content:// style uri to the authority for the contacts provider
     */
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    //class for the user's contract
    public static class UserContract
    {
        public static final String User_ID = "UserId";
        public static final String USER_NAME = "UserName";
        public static final String USER_PASSWORD = "Password";
        /**
         * The content:// style URI for this table
         */
        public static final Uri User_URI = Uri.withAppendedPath(AUTHORITY_URI, "users");
    }

    //class for the business' contract
    public static class BusinessContract {
        public static final String BUSINESS_ID = "_id";
        public static final String BUSINESS_NAME = "Name";
        public static final String BUSINESS_ADDRESS = "BusinessAddress";
        public static final String BUSINESS_PHONE = "PhoneNumber";
        public static final String BUSINESS_EMAIL = "Email";
        public static final String BUSINESS_WEBSITE = "Website";
        /**
         * The content:// style URI for this table
         */
        public static final Uri BUSINESS_URI = Uri.withAppendedPath(AUTHORITY_URI, "business");
    }

    //class for the activity's contract
    public static class ActivityContract {
        public static final String Activity_KEY = "_id";
        public static final String Activity_NAME = "Name";
        public static final String Activity_KIND = "Kind";
        public static final String Activity_COUNTRY = "Country";
        public static final String Activity_START = "Start";
        public static final String Activity_END = "End";
        public static final String Activity_PRICE = "Price";
        public static final String Activity_DESCRIPTION = "Description";
        public static final String Activity_BUSINESS_ID = "BusinessId";
        /**
         * The content:// style URI for this table
         */
        public static final Uri ACTIVITY_URI = Uri.withAppendedPath(AUTHORITY_URI, "activities");
    }
}
