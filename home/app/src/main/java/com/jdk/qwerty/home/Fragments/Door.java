package com.jdk.qwerty.home.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jdk.qwerty.home.Adapter.RecSensorsAdapter;
import com.jdk.qwerty.home.MainActivity;
import com.jdk.qwerty.home.Objects.door;
import com.jdk.qwerty.home.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 02/12/2017.
 */

@SuppressLint("ValidFragment")
public class Door extends Fragment {


    private static final String TAG = "Door tab";
    private RecyclerView recSensors;
    private ArrayList<door> doors;
    private door door = null;
    private View view;
    private RecSensorsAdapter adapter;

    @SuppressLint("ValidFragment")
    public Door(List<door> list){
        doors = new ArrayList<>();
        for(door data: list) {
            data.setImage(data.getStatus().equals("on") ? "door_on" : "door_off");
            doors.add(data);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Choose the Layout for my Fragment
        view = inflater.inflate(R.layout.door_tab, container, false);

        //Using door_tab.xml objects with view.
        recSensors = (RecyclerView) view.findViewById(R.id.recSensorsDoor);
        recSensors.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 1));
        adapter = new RecSensorsAdapter(view.getContext(), doors);
        adapter.setOnClickListener(RecSensorsItemClick());
        recSensors.setAdapter(adapter);

        return view;
    }

    private View.OnClickListener RecSensorsItemClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                door data = doors.get(recSensors.getChildAdapterPosition(view));
                switch (data.getStatus()){
                    case "on": data.setStatus("off"); data.setImage("door_off"); break;
                    case "off": data.setStatus("on"); data.setImage("door_on"); break;
                }
                MainActivity.MqttClient.Public("door$" + data.getLocation(), data.getStatus());
                //MainActivity.My_Controller.setMotorEstac(data, CallBackSet());
                recSensors.getAdapter().notifyDataSetChanged();
            }
        };
    }

    public void ChangeStatus(String location, String status) {
        for(door data: doors)
            if(data.getLocation().equals(location)) {
                data.setStatus(status);
                data.setImage(status.equals("on") ? "door_on" : "door_off");
                recSensors.getAdapter().notifyDataSetChanged();
            }
    }

}

