package com.sassonsoft.flickrimages;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sasson on 26/09/2015.
 */
public class FlickrServerAction {
    public static String getImageDetails(String urlString) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            if (con.getResponseCode() == 200) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                con.disconnect();
                reader.close();
                return sb.toString();
            }

            else {
                con.disconnect();
                Log.w("FlickrImages", "Flickr Server error #" + Integer.toString(con.getResponseCode()));
                return "Error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("FlickrImages", e.toString());
            return null;
        }
    }
}
