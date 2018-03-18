package com.example.ali.project_fin.sec_sms;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ali.project_fin.R;
import com.example.ali.project_fin.database.database;

import ru.bullyboo.encoder.Encoder;
import ru.bullyboo.encoder.methods.AES;

public class SendSms extends AppCompatActivity {
    EditText messageinput,numberInput;
    database db;


    IntentFilter intentFilter;
    private BroadcastReceiver intenteReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {

                Log.e("mess from reci",intent.getExtras().getString("message"));


                db.saveSms(intent.getExtras().getString("sender"),Encoder.BuilderAES()
                        .message(intent.getExtras().getString("message"))
                        .method(AES.Method.AES_CBC_PKCS5PADDING)
                        .decrypt(),"rec");


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarhi);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });
        messageinput= (EditText) findViewById(R.id.messageInput);
        numberInput= (EditText) findViewById(R.id.numberInput);
        intentFilter=new IntentFilter();
        db=new database(this);
        intentFilter.addAction("SMS_RECEIVED_ACTION");


    }

    public void sendSms(View view){

        String mes=messageinput.getText().toString().trim();
        String numb=numberInput.getText().toString().trim();
        if (mes.equals("")||numb.equals("")){
            Toast.makeText(this, "Write Number and Message Problem !", Toast.LENGTH_SHORT).show();

        }else {

            String SENT="Message sent";
            String DELIVERD="Message Delivered";
            PendingIntent sentPi=PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
            PendingIntent deliverPI=PendingIntent.getBroadcast(this,0,new Intent(DELIVERD),0);
            SmsManager sms=SmsManager.getDefault();
            sms.sendTextMessage(numb,null,Encoder.BuilderAES()
                    .message(mes)
                    .method(AES.Method.AES_CBC_PKCS5PADDING)
                    .encrypt(),sentPi,deliverPI);

            db.saveSms("Me",mes,"env");

            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();

        }


    }





    @Override
    protected  void onResume(){
        registerReceiver(intenteReceiver,intentFilter);
        super.onResume();
    }

    @Override
    protected  void onPause(){
        unregisterReceiver(intenteReceiver);
        super.onPause();
    }

}
