package com.jdk.qwerty.home.restAPI;

import com.jdk.qwerty.home.Objects.door;
import com.jdk.qwerty.home.Objects.light;
import com.jdk.qwerty.home.Objects.mqttBroker;
import com.jdk.qwerty.home.Objects.temp;
import com.jdk.qwerty.home.Objects.user;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Administrador on 09/12/2017.
 */

public interface RestClient {

    public final static String BASE_URL = "https://api-rest-mqtt.herokuapp.com"; //"http://192.168.0.19:3010/";

    @POST("api/signin")
    Call<String> signIn(@Body user _user);

    @POST("SignUp")
    Call<user> signUp(@Body user _user);

    @GET("api/mqttServer")
    Call<mqttBroker> getMqttBroker(@Header("authorization") String token);

    @GET("api/lights")
    Call<List<light>> getLights(@Header("authorization") String token);

    @GET("api/temps")
    Call<List<temp>> getTemps(@Header("authorization") String token);

    @GET("api/doors")
    Call<List<door>> getDoors(@Header("authorization") String token);

}
