package com.example.jarida;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
 
public class UserFunctions {
     
    private JSONParser jsonParser;
     
    private static String loginURL = "http://192.168.42.1/";
    private static String registerURL = "http://192.168.42.1/";
    private static String login_tag = "login";
    private static String register_tag = "register";
   
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
     
 
    public JSONObject loginUser(String email, String password){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        return json;
    }

    public JSONObject registerUser(String name, String email, String password){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));      
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        return json;
    }

    public boolean isUserLoggedIn(Context context){
        int count = DataSource.getInstance(context).getLoginRowCount();       
        if(count > 0){
            return true;
        }
        return false;
    }
    public boolean logoutUser(Context context){
    	
    	DataSource.getInstance(context).resetLoginTable();
        return true;
    }
     
}