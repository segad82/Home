package com.jdk.qwerty.home.restAPI;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.jdk.qwerty.home.Objects.mqttBroker;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Administrador on 16/12/2017.
 */

public class ClientMQTT {

    private static final String TAG = "ClientMQTT";
    public MqttAndroidClient mqttAndroidClient;
    private mqttBroker MqttBroker;
    private String ClientId;
    private Context MyContext;
    private IMqttMessageListener MessageListener;

    public ClientMQTT(Context context, String clientId, mqttBroker mqttBroker, IMqttMessageListener messageListener){

        this.MyContext = context;
        this.ClientId = clientId;
        this.MqttBroker = mqttBroker;
        this.MessageListener = messageListener;

        mqttAndroidClient = new MqttAndroidClient(this.MyContext,this.MqttBroker.getFullURL(),this.ClientId);
        mqttAndroidClient.setCallback(this.ClientMqttCallback());
        this.connect();

    }

    private void connect(){

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(this.MqttBroker.getUsername());
        mqttConnectOptions.setPassword(this.MqttBroker.getPassword().toCharArray());

        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MyContext, "No pudo conectarse a servidor MQTT.", Toast.LENGTH_LONG).show();
                    Log.w(TAG, "Failed to connect to: " + MqttBroker.getFullURL() + exception.toString());
                }
            });
        }
        catch (MqttException ex){ ex.printStackTrace(); }
    }

    public void Subscribe(String topic){
        try {
            mqttAndroidClient.subscribe(topic, 0, null, SuscribeListener());
        }catch (MqttException ex) {
            Log.w(TAG, "Exceptionst subscribing: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void Public(String topic, String message){
        try{
            MqttMessage myMessage = new MqttMessage();
            myMessage.setPayload(message.getBytes());
            this.mqttAndroidClient.publish(topic,myMessage);
        }catch(MqttException ex){
            Log.w(TAG, "Exceptionst publishing " + topic + ": " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private IMqttActionListener SuscribeListener(){
        return new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.w(TAG,"Subscribed!");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.w(TAG, "Subscribed fail!");
            }
        };
    }

    private MqttCallback ClientMqttCallback(){
        return new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                MessageListener.messageArrived(topic, message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        };
    }

}
