package com.example.ali.project_fin.auth.blurpass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ali.project_fin.Main_page;
import com.example.ali.project_fin.R;

public class BlurPassNew extends AppCompatActivity {
   EditText  password;
    EditText confPass;
    Button saveBtn;
    public static final String MyPREFERENCES="myprefre";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_blur_pass_new);

        password= (EditText) findViewById(R.id.passBlur);
        confPass= (EditText) findViewById(R.id.confPassBlur);
        saveBtn= (Button) findViewById(R.id.saveNewPass);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activePassBlur();
            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });



    }

    public  void activePassBlur(){

           if(validPass()){
               SharedPreferences preferences=getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
               SharedPreferences.Editor editor=preferences.edit();
               editor.putString("start_type_sec","blur");
               editor.putString("pasword_blur",password.getText().toString().trim());
               editor.commit();


              // if(getIntent().getExtras().getString("fromForget").equals("tomain")){
    
               
                      // // TODO: 4/16/17 find solution for this when the user come form the forget

                     startActivity(new Intent(this, Main_page.class));

             
           }
           else {
               Toast.makeText(getApplicationContext(), "Password problem !", Toast.LENGTH_SHORT).show();
           }

    }


    public boolean validPass(){

     if(password.getText().toString().equals(confPass.getText().toString())&&password.getText()
             .toString().length()>4){
           return  true;
     }
     else {

         return false;
     }

    }


}
