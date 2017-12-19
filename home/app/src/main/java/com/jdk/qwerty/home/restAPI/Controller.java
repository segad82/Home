package com.jdk.qwerty.home.restAPI;

import android.content.SharedPreferences;
import com.jdk.qwerty.home.Objects.door;
import com.jdk.qwerty.home.Objects.light;
import com.jdk.qwerty.home.Objects.mqttBroker;
import com.jdk.qwerty.home.Objects.temp;
import com.jdk.qwerty.home.Objects.user;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 03/12/2017.
 */

public class Controller {

    public static SharedPreferences Settings;
    public static SharedPreferences.Editor EditSettings;
    private Retrofit retrofit;
    private RestClient restClient;

    public Controller(SharedPreferences sharedPreferences){

        Settings = sharedPreferences;
        EditSettings = Settings.edit();

        retrofit = new Retrofit.Builder()
                .baseUrl(RestClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restClient = retrofit.create(RestClient.class);

    }

    public void getMqttBroker(Callback<mqttBroker> callback){
        Call<mqttBroker> call = restClient.getMqttBroker(this.getToken());
        call.enqueue(callback);
    }

    public void signUp(user _user, Callback<user> callback){
        Call<user> call = this.restClient.signUp(_user);
        call.enqueue(callback);
    }

    public void signIn(user _user, Callback<String> callback){
        Call<String> call = this.restClient.signIn(_user);
        call.enqueue(callback);
    }

    public void setToken(String value){
        EditSettings.putString("token", value);
        EditSettings.commit();
    }

    private String getToken(){
        return "Bearer " + Settings.getString("token", "none");
    }

    public void getLightAll(Callback<List<light>> callback){
        Call<List<light>> call = this.restClient.getLights(this.getToken());
        call.enqueue(callback);
    }

    public void getTempAll(Callback<List<temp>> callback){
        Call<List<temp>> call = this.restClient.getTemps(this.getToken());
        call.enqueue(callback);
    }

    public void getDoorAll(Callback<List<door>> callback){
        Call<List<door>> call = this.restClient.getDoors(this.getToken());
        call.enqueue(callback);
    }

}
