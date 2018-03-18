package com.example.ali.project_fin.sec_sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.example.ali.project_fin.database.database;

/**
 * Created by ali on 4/20/17.
 */

public class MessageReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getExtras();
        SmsMessage[] messages;
        String str="";
        String sender="";
        if (bundle!=null){
            Object[] pdus=(Object[]) bundle.get("pdus");
            messages=new  SmsMessage[pdus!=null ? pdus.length:0];
            for(int i=0;i<messages.length;i++){

                messages[i]=SmsMessage.createFromPdu((byte[])(pdus!=null ? pdus[i]:null));
                str=messages[i].getMessageBody();
                sender=messages[i].getOriginatingAddress();

            }


            //Toast.makeText(context, str,Toast.LENGTH_SHORT).show();


            Intent bre=new Intent();
            bre.setAction("SMS_RECEIVED_ACTION");
            bre.putExtra("message",str);
            bre.putExtra("sender",sender);
            context.sendBroadcast(bre);

            // to save the sms in my own database ^_^


            database database=new database(context);
            database.saveSms(sender,str,"rec");

        }
    }
}
