package com.example.user.theexmqtttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import mesin_iot.Helper_IoT;

public class MainActivity extends AppCompatActivity {

    EditText et_data;
    Button btn_kirim;
    TextView tv_data_masuk;
    Helper_IoT helper_iot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_data = findViewById(R.id.et_data);
        btn_kirim = findViewById(R.id.btn_kirim);
        tv_data_masuk = findViewById(R.id.tv_data_masuk);

        helper_iot = new Helper_IoT(getApplicationContext());
        helper_iot.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String list_payload = tv_data_masuk.getText().toString();
                list_payload += "\n\nTopic : "+topic+"\nPayload : "+message;
                tv_data_masuk.setText(list_payload);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payload = et_data.getText().toString();
                helper_iot.mqttPublish(payload);
                et_data.setText("");
            }
        });


    }
}
