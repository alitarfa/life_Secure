package com.example.ali.project_fin.auth.Pattern;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


import com.google.gson.Gson;

import java.util.ArrayList;


public class SetPattern extends AppCompatActivity {

    public static final String MyPREFERENCES="myprefre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



          SharedPreferences preferences=getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
          SharedPreferences.Editor editor=preferences.edit();
          editor.putString("start_type_sec","pattern");
          editor.commit();


        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        FragmentCreateNewPattern  fragmentConfirmPattern=new FragmentCreateNewPattern(getApplicationContext());
        fragmentTransaction.replace(android.R.id.content,fragmentConfirmPattern);
        fragmentTransaction.commit();


    }

    public  void contuner(ArrayList<Integer> patternCode){

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        FragmentConfirmPattern  fragmentConfirmPattern=new FragmentConfirmPattern(getApplicationContext(),patternCode);
        fragmentTransaction.replace(android.R.id.content,fragmentConfirmPattern);
        fragmentTransaction.commit();

    }

    public  void saveCodePaternIntoPre(ArrayList<Integer> codePattern){
        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String jsonText=gson.toJson(codePattern);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("codePattern",jsonText);
        editor.commit();

    }
}
