package damnvuln.exercise.lab.vulntestlab;

import android.arch.core.util.Function;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import damnvuln.exercise.lab.vulntestlab.VolleyCookieFix.JsonArrayRequestCookie;
import damnvuln.exercise.lab.vulntestlab.VolleyCookieFix.JsonObjectRequestCookie;

public class CallAPI {

    private static String sessionToken;
    private static EditText ipInput;
    private static String serverIp = "10.217.27.53";
    private static RequestQueue mRequestQueue;
    private static MainActivity activity;


    static void initCallApi(File aCachePath, MainActivity aActivity){

        activity = aActivity;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(aCachePath, 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();


    }

    static void launchRequest(String apiPath, final CallAPIComp callback){


        String url = "http://"+serverIp+":8080/"+ apiPath;

        JsonArrayRequestCookie jsonArrayRequestRequest = new JsonArrayRequestCookie
                (Request.Method.GET,  url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("REPONSE", response.toString());

                        callback.saveResponse(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());

                    }
                });

        if(sessionToken != null){

            ArrayList<String> cookie = new ArrayList<>();
            cookie.add("sessionToken="+ sessionToken);
            jsonArrayRequestRequest.setCookies(cookie);
        }

        mRequestQueue.add(jsonArrayRequestRequest);

    }

    static void launchRequest(String apiPath, Map<String, String> parameters, final CallAPIComp callback){

        JSONObject jsonObject = new JSONObject(parameters);

        String url = "http://"+serverIp+":8080/"+ apiPath;

        JsonObjectRequestCookie jsonObjectRequest = new JsonObjectRequestCookie
                (Request.Method.POST,  url, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("REPONSE:", response.toString());
                        callback.saveResponse(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());

                    }
                });

        if(sessionToken != null){

            ArrayList<String> cookie = new ArrayList<>();
            cookie.add("sessionToken="+ sessionToken);
            jsonObjectRequest.setCookies(cookie);
        }

        mRequestQueue.add(jsonObjectRequest);

    }

    public static String getSessionToken() {
        return sessionToken;
    }

    public static void setSessionToken(String sessionToken) {
        CallAPI.sessionToken = sessionToken;
    }

    public static void setIpServer(String ip ){
        serverIp = ip;
    }

    public static String getMd5(String psw){
        String password = "null";
        try{

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(psw.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();

            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            password = sb.toString();

        }catch(Exception e){
            e.printStackTrace();
        }

        return password;
    }
}

