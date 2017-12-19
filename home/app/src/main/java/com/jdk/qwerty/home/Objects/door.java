package com.jdk.qwerty.home.Objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrador on 02/12/2017.
 */

public class door {

    private String _id;
    private String location;
    private String displayName;
    private String status;
    private String image;

    public door(String location, String displayName, String status, String image){
        this.setLocation(location);
        this.setDisplayName(displayName);
        this.setStatus(status);
        this.setImage(image);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    protected JSONObject getObjectJSON(){
        try {
            JSONObject obj = new JSONObject();
            obj.put("location", this.getLocation());
            obj.put("displayName", this.getDisplayName());
            obj.put("status", this.getStatus());
            obj.put("image", this.getImage());
            return obj;
        } catch (JSONException e) {
            return null;
        }
    }

    public String toJSON(){
        JSONObject obj = this.getObjectJSON();
        if(obj != null)
            return obj.toString();
        else
            return "none";
    }

}