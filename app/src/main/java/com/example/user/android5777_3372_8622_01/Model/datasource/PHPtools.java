package com.example.user.android5777_3372_8622_01.Model.datasource;

import android.content.ContentValues;
import android.os.AsyncTask;

import com.example.user.android5777_3372_8622_01.Model.entities.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by user on 19/01/2017.
 *
 * class to help us get into the sql data base to get information or add information into that
 */

public class PHPtools
{
    //function to get information from the DB
    public static String GET(String url) throws Exception
    {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }
            in.close();
            // print result
            return response.toString();
        }
        else
        {
            return "";
        }
    }
    //function to add data to the sql
    public static String POST(String url, ContentValues params) throws IOException {
        //Convert Map<String,Object> into key=value&key=value pairs.
        StringBuilder postData = new StringBuilder();
        for (String param : params.keySet()) {
            if (postData.length() != 0)
                postData.append('&');
            postData.append(URLEncoder.encode(param, "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(params.get(param)), "UTF-8"));
        }
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        //writing
        os.write(postData.toString().getBytes("UTF-8"));
        os.flush();
        os.close();
        // For POST only - END
        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else return "";
    }
}
