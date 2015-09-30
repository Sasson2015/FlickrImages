package com.sassonsoft.flickrimages;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sasson on 26/09/2015.
 */
public class FlickrImageDetails {
    private static List<String> farmID;
    private static List<String> serverID;
    private static List<String> imageID;
    private static List<String> secret;
    private static List<String> url;

    public static void setImagesDetails(String jsonImagesDetails) {

        farmID=new ArrayList<>();
        serverID=new ArrayList<>();
        imageID=new ArrayList<>();
        secret=new ArrayList<>();
        url=new ArrayList<>();

        try {
            JSONObject jsonObject=new JSONObject(jsonImagesDetails);
            jsonObject=new JSONObject(jsonObject.get("photos").toString());
            JSONArray jsonArray=new JSONArray(jsonObject.get("photo").toString());

            for(int i=0;i<jsonArray.length();i++) {
                jsonObject=jsonArray.getJSONObject(i);
                farmID.add(jsonObject.get("farm").toString());
                serverID.add(jsonObject.get("server").toString());
                imageID.add(jsonObject.get("id").toString());
                secret.add(jsonObject.get("secret").toString());
                url.add("https://farm"+farmID.get(i)+".staticflickr.com/"+serverID.get(i)+"/"+imageID.get(i)+"_"+secret.get(i)+"_q.jpg");
            }


        } catch (JSONException e) {
            Log.w("FlickrImages", e.getMessage().toString());
        }

    }
    public static List<String> getUrl() {
        return url;
    }

}
