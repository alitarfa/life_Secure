package com.example.ali.project_fin.historique;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ali on 4/19/17.
 */

public class ToulsHisto {

    public static String getDate(){

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss ");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }
}
