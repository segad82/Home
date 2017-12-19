package com.jdk.qwerty.home.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jdk.qwerty.home.Adapter.RecSensorsAdapter;
import com.jdk.qwerty.home.MainActivity;
import com.jdk.qwerty.home.Objects.door;
import com.jdk.qwerty.home.Objects.temp;
import com.jdk.qwerty.home.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 02/12/2017.
 */

@SuppressLint("ValidFragment")
public class Temp extends Fragment {
    private static final String TAG = "Temp tab";
    private RecyclerView recSensors;
    private TextView txtTemp;
    private ImageView imageButton;
    private NumberPicker numberPicker;
    private LinearLayout manager;
    private LinearLayout contNumberPicker;
    private RelativeLayout backLayout;
    private ImageButton imageButtonOk;
    private ImageButton imageButtonCancel;
    private View view;
    private ArrayList<door> temps;
    private temp currentTemp;
    private int currentIndex;

    @SuppressLint("ValidFragment")
    public Temp(List<temp> list){
        temps = new ArrayList<>();
        temps.addAll(list);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.temp_tab, container, false);

        //We indentify ids of light_tab.xml objects
        txtTemp = view.findViewById(R.id.txtTemp);
        imageButton = (ImageButton)view.findViewById(R.id.imageButtonTemp);
        numberPicker = view.findViewById(R.id.numberPicker);
        manager = view.findViewById(R.id.managerTemp);
        contNumberPicker = view.findViewById(R.id.contNumberPicker);
        backLayout = view.findViewById(R.id.backLayoutTemp);
        imageButtonOk = view.findViewById(R.id.okButtonTemp);
        imageButtonCancel = view.findViewById(R.id.cancelButtonTemp);

        imageButton.setOnClickListener(this.imageButtoOnClick());
        imageButtonOk.setOnClickListener(this.imageButtonOkOnClick());
        imageButtonCancel.setOnClickListener(this.imageButtonCancelOnClick());

        //Build RecyclerView with adapter
        recSensors = (RecyclerView) view.findViewById(R.id.recSensorsTemp);
        recSensors.setLayoutManager(new GridLayoutManager(this.getContext(), 1));
        RecSensorsAdapter adapter = new RecSensorsAdapter(view.getContext(), temps);
        adapter.setOnClickListener(adapterOnItemClick());
        recSensors.setAdapter(adapter);

        this.Start();
        return view;
    }

    private void Start(){

        imageButton.setBackground(getResources().getDrawable(R.drawable.light_off));
        imageButton.setTag("off");

        changeShow(false);

    }

    public void ChangeTemp(String location, Integer _currentTemp){
        try{
            for(door data: temps)
                if(data.getLocation().equals(location)) {
                    ((temp)data).setCurrentTemp(_currentTemp);
                    if(currentTemp != null && currentTemp.getLocation().equals(location))
                        txtTemp.setText(_currentTemp.toString() + "°C");
                    break;
                }
            recSensors.getAdapter().notifyDataSetChanged();
        }catch(Exception ex){
            Log.d(TAG, "ChangeTemp: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void ChangeStatus(String location, String status, Integer maxTemp) {
        try{
            for(door data: temps)
                if(data.getLocation().equals(location)) {
                    data.setStatus(status);
                    ((temp)data).setMaxTemp(maxTemp);
                    if(currentTemp != null && currentTemp.getLocation().equals(location)){
                        currentTemp = (temp) data;
                        setForm(currentTemp);
                    }
                    break;
                }
            recSensors.getAdapter().notifyDataSetChanged();
        }catch(Exception ex){
            Log.d(TAG, "ChangeStatus: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private temp getForm(){

        TextView location = getActivity().findViewById(R.id.txtNameSensorTemp);
        int maxTemp = 0;
        int _currentTemp = Integer.parseInt(txtTemp.getText().toString().split("°")[0]);
        String status = imageButton.getTag().toString();
        if(status.equals("auto"))
            maxTemp = numberPicker.getValue();

        temp tempDefault = currentTemp;
        return new temp(tempDefault.getLocation(), location.getText().toString(), status, tempDefault.getImage(), _currentTemp, maxTemp);

    }

    private void setForm(temp data){

        ((TextView)getActivity().findViewById(R.id.txtNameSensorTemp)).setText(data.getDisplayName());
        txtTemp.setText(data.getCurrentTemp() + "°C");

        switch (data.getStatus()){
            case "auto":
                imageButton.setBackground(getResources().getDrawable(R.drawable.temp_auto));
                imageButton.setTag("auto");
                contNumberPicker.setVisibility(View.VISIBLE);
                numberPicker.setValue(data.getMaxTemp());
                break;
            case "on":
                imageButton.setBackground(getResources().getDrawable(R.drawable.temp_on));
                imageButton.setTag("on");
                contNumberPicker.setVisibility(View.GONE);
                numberPicker.setValue(18);
                break;
            case "off":
                imageButton.setBackground(getResources().getDrawable(R.drawable.temp_off));
                imageButton.setTag("off");
                contNumberPicker.setVisibility(View.GONE);
                numberPicker.setValue(18);
                break;
        }

    }

    private void changeShow(Boolean show){
        if (show) {
            manager.setVisibility(View.VISIBLE);
            backLayout.setVisibility(View.VISIBLE);
            recSensors.setVisibility(View.GONE);
        } else {
            manager.setVisibility(View.GONE);
            backLayout.setVisibility(View.GONE);
            recSensors.setVisibility(View.VISIBLE);
        }
    }

    /*######### START EVENT LISTENERS #########*/
    private View.OnClickListener imageButtoOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //If tags appears to be off change to on
                switch (imageButton.getTag().toString()){
                    case "on":
                        imageButton.setBackground(getResources().getDrawable(R.drawable.temp_off));
                        imageButton.setTag("off");
                        contNumberPicker.setVisibility(View.GONE);
                        break;
                    case "off":
                        imageButton.setBackground(getResources().getDrawable(R.drawable.temp_auto));
                        imageButton.setTag("auto");
                        contNumberPicker.setVisibility(View.VISIBLE);
                        break;
                    case "auto":
                        imageButton.setBackground(getResources().getDrawable(R.drawable.temp_on));
                        imageButton.setTag("on");
                        contNumberPicker.setVisibility(View.GONE);
                        break;
                }
            }
        };
    }

    private View.OnClickListener imageButtonOkOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                temp data = getForm();
                MainActivity.MqttClient.Public("temp$" + data.getLocation(), data.getStatus() + ":" + data.getMaxTemp());
                temps.set(currentIndex, data);
                recSensors.getAdapter().notifyDataSetChanged();
                Start();
            }
        };
    }

    private View.OnClickListener imageButtonCancelOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start();
            }
        };
    }

    private View.OnClickListener adapterOnItemClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeShow(true);
                currentIndex = recSensors.getChildAdapterPosition(view);
                currentTemp = (temp) temps.get(currentIndex);
                setForm(currentTemp);

            }
        };
    }
    /*######### END EVENT LISTENERS #########*/

}
